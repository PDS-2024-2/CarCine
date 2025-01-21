package br.ufrn.imd.cine.services.ticket;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufrn.imd.cine.model.ticket.BuyTicketRecord;
import br.ufrn.imd.cine.model.ticket.CarTicket;
import br.ufrn.imd.cine.model.ticket.CarTransferData;
import br.ufrn.imd.cine.repositories.CarTicketRepository;
import br.ufrn.imd.cine.repositories.TicketTypeRepository;
import br.ufrn.imd.cineframework.models.session.Session;
import br.ufrn.imd.cineframework.models.user.User;
import br.ufrn.imd.cineframework.repositories.GenericRepository;
import br.ufrn.imd.cineframework.repositories.session.SessionRepository;
import br.ufrn.imd.cineframework.repositories.user.UserRepository;
import br.ufrn.imd.cineframework.services.GenericService;

@Service
public class TicketService extends GenericService<CarTicket> {
	private static final long serialVersionUID = 1L;

	@Override
	public GenericRepository<CarTicket> getRepository() {
		return ticketRepository;
	}

	@Autowired
	private CarTicketRepository ticketRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private SessionRepository sessionRepository;

	@Autowired
	private TicketTypeRepository ticketTypeRepository;

	@Autowired
	private CarTransferStrategy carTransferStrategy;

	public List<CarTicket> findByOwner(User userLogged) {
		if (userRepository.findById(userLogged.getId()).isEmpty()) {
			throw new IllegalArgumentException("User not found");
		} else {
			return ticketRepository.findByOwnersContaining(userRepository.findById(userLogged.getId()).get()).get();
		}
	}

	// buy
	public CarTicket buyTicket(BuyTicketRecord buyTicket, User userLogged) {

		Session s = sessionRepository.findById(buyTicket.idSession())
				.orElseThrow(() -> new IllegalArgumentException("Session not found"));

		if (s.getAvailableSeats() == 0) {
			throw new IllegalArgumentException("Session full");
		}

		CarTicket t = new CarTicket();

		t.setSession(s);
		t.setTicketTypes(Set.of(ticketTypeRepository.findById(buyTicket.type()).get()));
		t.setParkinSpot(buyTicket.parkinSpot());
		t.setPrice(buyTicket.price());
		t.setLicensePlate(buyTicket.licensePlate());
		t.setQuantity(buyTicket.quantity());
		t.setToken(UUID.randomUUID().toString());

		List<User> owners = new ArrayList<>();

		for (String username : buyTicket.owners()) {
			User owner = userRepository.findByUsername(username)
					.orElseThrow(() -> new IllegalArgumentException("User not found with id: " + username));
			owners.add(owner);
		}

		t.setOwners(owners);

		s.setAvailableSeats(s.getAvailableSeats() - 1);

		sessionRepository.save(s);

		return insert(t);
	}

	public CarTicket transferTicket(CarTransferData transferTicket, User userLogged) {

		CarTicket existingTicket = ticketRepository.findById(transferTicket.idTicket).orElseThrow(
				() -> new IllegalArgumentException("Ticket not found with id: " + transferTicket.idTicket));

		if (!existingTicket.getOwners().contains(userLogged)) {
			throw new IllegalArgumentException(
					"Logged user are not owner from the ticket with id " + transferTicket.idTicket);
		}

		existingTicket.setTranferStrategy(carTransferStrategy);
		return update((CarTicket) existingTicket.transfer(transferTicket));
	}

	public List<CarTicket> findBySession(Long sessionId) {
		Session s = sessionRepository.findById(sessionId)
				.orElseThrow(() -> new IllegalArgumentException("Session not found"));

		return ticketRepository.findBySession(s);
	}

}