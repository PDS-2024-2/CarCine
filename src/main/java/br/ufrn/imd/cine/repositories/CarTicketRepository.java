package br.ufrn.imd.cine.repositories;

import java.util.List;
import java.util.Optional;

import br.ufrn.imd.cine.model.ticket.CarTicket;
import br.ufrn.imd.cineframework.models.session.Session;
import br.ufrn.imd.cineframework.models.user.User;
import br.ufrn.imd.cineframework.repositories.GenericRepository;

public interface CarTicketRepository extends GenericRepository<CarTicket> {
	Optional<List<CarTicket>> findByOwnersContaining(User user);

	List<CarTicket> findBySession(Session session);
}
