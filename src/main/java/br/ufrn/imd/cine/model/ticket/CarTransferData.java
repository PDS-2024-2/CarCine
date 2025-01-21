package br.ufrn.imd.cine.model.ticket;

import java.util.List;

import br.ufrn.imd.cineframework.models.ticket.TransferData;

public class CarTransferData implements TransferData {
	public Long idTicket;
	public List<String> usersToRemove;
	public List<String> userToAdd;
}
