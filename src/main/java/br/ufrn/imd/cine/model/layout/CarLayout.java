package br.ufrn.imd.cine.model.layout;

import java.util.List;

import br.ufrn.imd.cine.model.seat.CarSpot;
import br.ufrn.imd.cineframework.models.layout.AbstractLayout;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "car_layout")
public class CarLayout extends AbstractLayout {

	@Column(name = "layout_address")
	private String address;

	@NotNull
	@NotEmpty
	@ManyToMany
	@JoinTable(name = "layout_has_spot", joinColumns = @JoinColumn(name = "layout_id"), inverseJoinColumns = @JoinColumn(name = "spot_id"))
	private List<CarSpot> spots;

	public CarLayout() {
	}

	public CarLayout(Double size, String address, @NotNull @NotEmpty List<CarSpot> spots) {
		super(size);
		this.address = address;
		this.spots = spots;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<CarSpot> getSpots() {
		return spots;
	}

	public void setSpots(List<CarSpot> spots) {
		this.spots = spots;
	}

}
