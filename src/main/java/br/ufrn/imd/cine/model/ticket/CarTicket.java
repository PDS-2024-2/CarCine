package br.ufrn.imd.cine.model.ticket;

import java.util.List;
import java.util.Set;

import br.ufrn.imd.cineframework.models.combo.Combo;
import br.ufrn.imd.cineframework.models.session.Session;
import br.ufrn.imd.cineframework.models.ticket.AbstractTicket;
import br.ufrn.imd.cineframework.models.ticket.TicketType;
import br.ufrn.imd.cineframework.models.ticket.TransferStrategy;
import br.ufrn.imd.cineframework.models.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "car_ticket")
public class CarTicket extends AbstractTicket {

	@Column(name = "license_plate")
	private String licensePlate;

	@Column(name = "quantity")
	private Integer quantity;

	@Column(name = "parkin_spot")
	private String parkinSpot;

	@NotNull
	@NotEmpty
	@ManyToMany
	@JoinTable(name = "layout_has_owner", joinColumns = @JoinColumn(name = "layout_id"), inverseJoinColumns = @JoinColumn(name = "owner_id"))
	private List<User> owners;

	@Column(name = "is_session")
	private Session session;

	public CarTicket() {
	}

	public CarTicket(Double price, String token, Set<TicketType> ticketTypes, Set<Combo> combos,
			TransferStrategy tranferStrategy, String licensePlate, Integer quantity, String parkinSpot, List<User> owners,
			Session session) {
		super(price, token, ticketTypes, combos, tranferStrategy);
		this.licensePlate = licensePlate;
		this.quantity = quantity;
		this.parkinSpot = parkinSpot;
		this.owners = owners;
		this.session = session;
	}

	public String getLicensePlate() {
		return licensePlate;
	}

	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getParkinSpot() {
		return parkinSpot;
	}

	public void setParkinSpot(String parkinSpot) {
		this.parkinSpot = parkinSpot;
	}

	public List<User> getOwners() {
		return owners;
	}

	public void setOwners(List<User> owners) {
		this.owners = owners;
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

}
