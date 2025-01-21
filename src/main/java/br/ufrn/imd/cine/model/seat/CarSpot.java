package br.ufrn.imd.cine.model.seat;

import br.ufrn.imd.cineframework.models.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "car_spot")
public class CarSpot extends AbstractEntity {
	@NotNull
	@NotBlank
	@Column(name = "seat_code")
	private String code;
	@NotNull
	@Column(name = "latitude")
	private String latitude;
	@NotNull
	@Column(name = "longitude")
	private String longitude;

	@NotNull
	@Column(name = "spot_type_id")
	private SpotType seatType;

	public CarSpot() {

	}

	public CarSpot(@NotNull @NotBlank String code, @NotNull @Min(1) @Max(50) String latitude,
			@NotNull @Min(1) @Max(50) String longitude, @NotNull SpotType seatType) {
		super();
		this.code = code;
		this.latitude = latitude;
		this.longitude = longitude;
		this.seatType = seatType;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public SpotType getSeatType() {
		return seatType;
	}

	public void setSeatType(SpotType seatType) {
		this.seatType = seatType;
	}

}
