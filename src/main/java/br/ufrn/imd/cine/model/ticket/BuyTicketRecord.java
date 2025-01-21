package br.ufrn.imd.cine.model.ticket;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record BuyTicketRecord(
		@NotNull
		Long type,
		@NotNull
		String parkinSpot,
		@NotNull
		String licensePlate,
		@NotNull
		Integer quantity,
		@NotNull
		@Positive
		Double price,
		@NotNull
		Long idSession,
		List<String> owners) {
}
