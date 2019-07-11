package org.jeavio.meetin.backend.dto;

import java.util.Date;

public class DateDTO {

	Date start, end;

	public DateDTO(Date start, Date end) {
		this.start = start;
		this.end = end;
	}

	public DateDTO() {
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("{\"start\":\"");
		builder.append(start);
		builder.append("\", \"end\":\"");
		builder.append(end);
		builder.append("\"}");
		return builder.toString();
	}

}
