package org.jeavio.meetin.backend.dto;

import java.util.Date;
import java.util.List;

//import org.jeavio.meetin.backend.config.MongoDateConverter;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

public class EventDetails {

	private String id;
	private String title;
	private String agenda;
	private String roomName;
	private String roomSpecifications;
//	@JsonDeserialize(using = MongoDateConverter.class)
	private Date start;
//	@JsonDeserialize(using = MongoDateConverter.class)
	private Date end;
	private UserInfo organizer;
	private List<MemberInfo> members;

	public EventDetails() {
	}

	public EventDetails(String title, String agenda, String roomName, String roomSpecifications, Date start, Date end,
			UserInfo organizer, List<MemberInfo> members) {
		super();
		this.title = title;
		this.agenda = agenda;
		this.roomName = roomName;
		this.roomSpecifications = roomSpecifications;
		this.start = start;
		this.end = end;
		this.organizer = organizer;
		this.members = members;
	}

	public EventDetails(String id, String title, String agenda, String roomName, String roomSpecifications,
			Date start, Date end, UserInfo organizer, List<MemberInfo> members) {
		this.id = id;
		this.title = title;
		this.agenda = agenda;
		this.roomName = roomName;
		this.roomSpecifications = roomSpecifications;
		this.organizer = organizer;
		this.members = members;
		this.start = start;
		this.end = end;
	}

	public String getId() {
		return id;
	}

	public void setId(String eventId) {
		this.id = eventId;
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

	public String getRoomSpecifications() {
		return roomSpecifications;
	}

	public void setRoomSpecifications(String roomSpecifications) {
		this.roomSpecifications = roomSpecifications;
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("{\"id\":\"");
		builder.append(id);
		builder.append("\", \"title\":\"");
		builder.append(title);
		builder.append("\", \"agenda\":\"");
		builder.append(agenda);
		builder.append("\", \"roomName\":\"");
		builder.append(roomName);
		builder.append("\", \"roomSpecifications\":\"");
		builder.append(roomSpecifications);
		builder.append("\", \"start\":\"");
		builder.append(start);
		builder.append("\", \"end\":\"");
		builder.append(end);
		builder.append("\", \"organizer\":\"");
		builder.append(organizer);
		builder.append("\", \"members\":\"");
		builder.append(members);
		builder.append("\"}");
		return builder.toString();
	}

	
}
