package br.ufrn.imd.cine.services.layout;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.ufrn.imd.cine.model.layout.CarLayout;
import br.ufrn.imd.cine.model.seat.CarSpot;
import br.ufrn.imd.cine.model.seat.SpotRecord;
import br.ufrn.imd.cine.repositories.CarLayoutRepository;
import br.ufrn.imd.cine.repositories.CarSpotRepository;
import br.ufrn.imd.cineframework.models.layout.AbstractLayout;
import br.ufrn.imd.cineframework.services.layout.LayoutCreator;
import jakarta.validation.constraints.NotNull;

@Component
public class CarLayoutCreator extends LayoutCreator {

	@Autowired
	private CarSpotRepository seatRepository;

	@Autowired
	private CarLayoutRepository layoutRepository;

	@Override
	public AbstractLayout createLayout(Object data) {

		SpotRecord[][] seatingLayout = (SpotRecord[][]) data;

		CarLayout carLayout = new CarLayout();

		carLayout.setSpots(extractSpots(seatingLayout));
		carLayout.setSize(carLayout.getSpots().size() + 0.0);
		carLayout.onCreate();

		CarLayout savedLayout = layoutRepository.save(carLayout);

		return savedLayout;
	}

	private List<CarSpot> extractSpots(@NotNull SpotRecord[][] seatingLayout) {

		List<CarSpot> seats = new ArrayList<>();

		for (int rowi = 1; rowi <= seatingLayout.length; rowi++) {
			SpotRecord[] seatRow = seatingLayout[rowi - 1];

			for (int columnj = 1; columnj <= seatRow.length; columnj++) {
				CarSpot st = seatRow[columnj - 1].seatType();

				if (st == null)
					continue;

				String code = numberToLetters(rowi) + columnj;

				st.setCode(code);

				CarSpot savedSeat = seatRepository.save(st);
				seats.add(savedSeat);
			}
		}

		return seats;
	}

	private static String numberToLetters(int n) {
		StringBuilder sb = new StringBuilder();
		while (n > 0) {
			n--;
			char letter = (char) ('A' + (n % 26));
			sb.append(letter);
			n /= 26;
		}
		return sb.reverse().toString();
	}

}
