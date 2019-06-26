package org.jeavio.meetin.backend.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Id;

import org.jeavio.meetin.backend.dto.MemberInfo;
import org.jeavio.meetin.backend.dto.UserInfo;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "events")
public class Event {

	@Id
	private String id;
	@Indexed(unique = true)
	private String eventId;
	private String title;
	private String agenda;
	private String roomName;
	private Date start;
	private Date end;
	private UserInfo organizer;
	private List<MemberInfo> members;

	public Event() {
	}

	public Event(String eventId, String title, String agenda, String roomName, Date start, Date end,
			UserInfo organizer, List<MemberInfo> members) {
		this.eventId = eventId;
		this.title = title;
		this.agenda = agenda;
		this.roomName = roomName;
		this.start = start;
		this.end = end;
		this.organizer = organizer;
		this.members = members;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAgenda() {
		return agenda;
	}

	public void setAgenda(String agenda) {
		this.agenda = agenda;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public UserInfo getOrganizer() {
		return organizer;
	}

	public void setOrganizer(UserInfo organizer) {
		this.organizer = organizer;
	}

	public List<MemberInfo> getMembers() {
		return members;
	}

	public void setMembers(List<MemberInfo> members) {
		this.members = members;
	}

}
