package br.ufrn.imd.cine.services.layout;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufrn.imd.cine.model.layout.CarLayout;
import br.ufrn.imd.cineframework.services.layout.LayoutService;

@Service
public class CarLayoutService {

	@Autowired
	private CarLayoutCreator carLayoutCreator;

	public CarLayout createLayout(Object data) {
		LayoutService layoutService = new LayoutService(carLayoutCreator);
		CarLayout simpleLayout = (CarLayout) layoutService.createLayout(data);
		return simpleLayout;
	}
}
