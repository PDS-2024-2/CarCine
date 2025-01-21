package br.ufrn.imd.cine.model.seat;

import br.ufrn.imd.cineframework.models.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "spot_type")
public class SpotType extends AbstractEntity {
	@Column(name = "price_increase")
	private Double priceIncrease;

	@Column(name = "code")
	private String code;

	@Column(name = "description")
	private String description;

	@Column(name = "vehicle")
	private String vehicle;

	@Column(name = "heigh")
	private Integer heigh;

	@Column(name = "weight")
	private Integer weight;

	public SpotType() {
	}

	public SpotType(Double priceIncrease, String code, String description, String vehicle, Integer heigh,
			Integer weight) {
		super();
		this.priceIncrease = priceIncrease;
		this.code = code;
		this.description = description;
		this.vehicle = vehicle;
		this.heigh = heigh;
		this.weight = weight;
	}

	public Double getPriceIncrease() {
		return priceIncrease;
	}

	public void setPriceIncrease(Double priceIncrease) {
		this.priceIncrease = priceIncrease;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getVehicle() {
		return vehicle;
	}

	public void setVehicle(String vehicle) {
		this.vehicle = vehicle;
	}

	public Integer getHeigh() {
		return heigh;
	}

	public void setHeigh(Integer heigh) {
		this.heigh = heigh;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

}
