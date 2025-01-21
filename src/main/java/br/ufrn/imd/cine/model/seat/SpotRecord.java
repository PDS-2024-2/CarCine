package br.ufrn.imd.cine.model.seat;

import jakarta.validation.constraints.NotNull;

public record SpotRecord(@NotNull CarSpot seatType) {
}
