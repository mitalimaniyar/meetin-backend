package org.jeavio.meetin.backend.dto;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

public class ConflictReportDTO {

	private String roomName;
	private Set<DateDTO> dates = new LinkedHashSet<DateDTO>();

	public ConflictReportDTO() {
	}

	public ConflictReportDTO(String roomName, Date start, Date end) {
		this.roomName = roomName;
		if (start != null && end != null)
			this.dates.add(new DateDTO(start, end));
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public Set<DateDTO> getDates() {
		return dates;
	}

	public void addDate(Date start, Date end) {
		this.dates.add(new DateDTO(start, end));
	}

}
