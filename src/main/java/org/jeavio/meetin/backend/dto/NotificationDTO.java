package org.jeavio.meetin.backend.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NotificationDTO {

	private String subject;
	private String agenda;
	private String organizer;
	private String roomName;
	private String roomSpecifications;
	private List<String> members = new ArrayList<String>();
	private Date startDate;
	private Date endDate;
	private String repeat;
	
	public NotificationDTO() { 		}

	
	public NotificationDTO(String subject, String agenda, String organizer, String roomName,String roomSpecifications, List<String> members,
			Date startDate, Date endDate,String repeat) {

		this.subject = subject;
		this.agenda = agenda;
		this.organizer = organizer;
		this.roomName = roomName;
		this.members = members;
		this.startDate = startDate;
		this.endDate = endDate;
		this.repeat = repeat;
		this.roomSpecifications =roomSpecifications;
	}


	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getAgenda() {
		return agenda;
	}

	public void setAgenda(String agenda) {
		this.agenda = agenda;
	}

	public String getOrganizer() {
		return organizer;
	}

	public void setOrganizer(String organizer) {
		this.organizer = organizer;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	
	public String getRoomSpecifications() {
		return roomSpecifications;
	}


	public void setRoomSpecifications(String roomSpecifications) {
		this.roomSpecifications = roomSpecifications;
	}


	public List<String> getMembers() {
		return members;
	}

	public void setMembers(List<String> members) {
		this.members = members;
	}

	public Date getStartDate() {
		return startDate;

	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}


	public String getRepeat() {
		return repeat;
	}


	public void setRepeat(String repeat) {
		this.repeat = repeat;
	}
	
	
}
