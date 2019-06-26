package org.jeavio.meetin.backend.dao;

import java.util.List;

import org.jeavio.meetin.backend.dto.EventDetails;
import org.jeavio.meetin.backend.model.Event;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends MongoRepository<Event,String> {

	List<Event> findAllByRoomName(String roomName);

	boolean existsByEventId(String eventId);

	@Query(value ="{'roomName': ?0}")
	List<EventDetails> findEventDetailsByRoomName(String roomName);

	@Query(value ="{'members.empId':?0}")
	List<EventDetails> findEventDetailsByEmpId(String empId);
}
