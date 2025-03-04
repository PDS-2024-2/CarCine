package br.ufrn.imd.cine.services.room;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufrn.imd.cine.model.layout.CarLayout;
import br.ufrn.imd.cine.model.room.CreateRoomRecord;
import br.ufrn.imd.cine.services.layout.CarLayoutService;
import br.ufrn.imd.cineframework.models.room.Room;
import br.ufrn.imd.cineframework.repositories.GenericRepository;
import br.ufrn.imd.cineframework.repositories.room.RoomRepository;
import br.ufrn.imd.cineframework.services.GenericService;
import jakarta.validation.Valid;

@Service
public class RoomService extends GenericService<Room> {
	private static final long serialVersionUID = 1L;

	@Autowired
	private RoomRepository repository;

	@Autowired
	private CarLayoutService layoutService;

	@Override
	public GenericRepository<Room> getRepository() {
		return repository;
	}

	public Room createRoom(@Valid CreateRoomRecord roomRecord) {
		Room r = new Room();
		r.setCode(roomRecord.code());
		r.setRoomType(roomRecord.type());

		CarLayout layout = layoutService.createLayout(roomRecord.seatingLayout());

		r.setCapacity(layout.getSpots().size());
		r.setLayout(layout);

		return insert(r);
	}

}
