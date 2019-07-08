package org.jeavio.meetin.backend.dto;

import java.util.List;

public class EventDTO {

	private String id;
	private String roomName;
	private String title;
	private String agenda;
	private String start;
	private String end;
	private String repeat;
	private List<String> members;
	private List<String> teams;
	
	public EventDTO() { }
	
	public String getId() {
		return id;
	}

	public void setId(String eventId) {
		this.id = eventId;
	}

	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
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
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	public String getRepeat() {
		return repeat;
	}
	public void setRepeat(String repeat) {
		this.repeat = repeat;
	}
	public List<String> getMembers() {
		return members;
	}
	public void setMembers(List<String> members) {
		this.members = members;
	}
	public List<String> getTeams() {
		return teams;
	}
	public void setTeams(List<String> teams) {
		this.teams = teams;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("{\"id\":\"");
		builder.append(id);
		builder.append("\", \"roomName\":\"");
		builder.append(roomName);
		builder.append("\", \"title\":\"");
		builder.append(title);
		builder.append("\", \"agenda\":\"");
		builder.append(agenda);
		builder.append("\", \"start\":\"");
		builder.append(start);
		builder.append("\", \"end\":\"");
		builder.append(end);
		builder.append("\", \"repeat\":\"");
		builder.append(repeat);
		builder.append("\", \"members\":\"");
		builder.append(members);
		builder.append("\", \"teams\":\"");
		builder.append(teams);
		builder.append("\"}");
		return builder.toString();
	}

	

}
