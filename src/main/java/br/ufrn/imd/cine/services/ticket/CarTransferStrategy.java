package br.ufrn.imd.cine.services.ticket;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.ufrn.imd.cine.model.ticket.CarTicket;
import br.ufrn.imd.cine.model.ticket.CarTransferData;
import br.ufrn.imd.cine.repositories.CarTicketRepository;
import br.ufrn.imd.cineframework.models.ticket.AbstractTicket;
import br.ufrn.imd.cineframework.models.ticket.TransferData;
import br.ufrn.imd.cineframework.models.ticket.TransferStrategy;
import br.ufrn.imd.cineframework.models.user.User;
import br.ufrn.imd.cineframework.repositories.user.UserRepository;

@Component
public class CarTransferStrategy implements TransferStrategy {

	@Autowired
	private CarTicketRepository ticketRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	public AbstractTicket transfer(TransferData data) {
		CarTransferData carTransferData = (CarTransferData) data;

		CarTicket existingTicket = ticketRepository.findById(carTransferData.idTicket).orElseThrow(
				() -> new IllegalArgumentException("Ticket not found with id: " + carTransferData.idTicket));

		List<User> usersToRemove = new ArrayList<>();

		for (String username : carTransferData.usersToRemove) {
			User userToRemove = userRepository.findByUsername(username)
					.orElseThrow(() -> new IllegalArgumentException("User not found with id: " + username));
			usersToRemove.add(userToRemove);
		}

		existingTicket.getOwners().removeIf(user -> usersToRemove.contains(user));

		List<User> usersToAdd = new ArrayList<>();

		for (String username : carTransferData.userToAdd) {
			User userToAdd = userRepository.findByUsername(username)
					.orElseThrow(() -> new IllegalArgumentException("User not found with id: " + username));
			usersToAdd.add(userToAdd);
		}

		existingTicket.getOwners().addAll(usersToAdd);

		return existingTicket;
	}

}
