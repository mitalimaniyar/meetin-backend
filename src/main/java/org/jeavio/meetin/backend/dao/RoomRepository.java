package org.jeavio.meetin.backend.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.jeavio.meetin.backend.dto.RoomDetails;
import org.jeavio.meetin.backend.model.Room;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends CrudRepository<Room, Integer> {

	@Query("SELECT new org.jeavio.meetin.backend.dto.RoomDetails(r.id,r.roomName,r.capacity,r.specifications) from Room r ORDER BY r.roomName ASC")
	List<RoomDetails> getRooms();

	boolean existsByRoomName(String roomName);

	@Modifying
	@Transactional
	void deleteByRoomName(String roomName);
}
