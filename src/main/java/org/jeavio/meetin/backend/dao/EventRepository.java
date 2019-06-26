package org.jeavio.meetin.backend.dao;

import java.util.Date;
import java.util.List;

import org.jeavio.meetin.backend.dto.EventDetails;
import org.jeavio.meetin.backend.model.Event;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends MongoRepository<Event, String> {

	@Query(sort = "{'start':1}")
	List<Event> findAllByRoomName(String roomName);

	boolean existsByEventId(String eventId);

	@Query(value = "{'roomName': ?0}", sort = "{'start':1}")
	List<EventDetails> findEventDetailsByRoomName(String roomName);

	@Query(value = "{'members.empId':?0}", sort = "{'start':1}")
	List<EventDetails> findEventDetailsByEmpId(String empId);

	@Query(value = "{'members.empId':?0,'start':{ '$lte' : ?1 } }", sort = "{'start':1}")
	List<EventDetails> findPastEvents(String empId, Date date);
	
	@Query(value = "{'members.empId':?0,'start':{ '$gte' : ?1 } }", sort = "{'start':1}")
	List<EventDetails> findFutureEvents(String empId, Date date);

	void deleteByEventId(String eventId);

	Event findByEventId(String eventId);
	
}
