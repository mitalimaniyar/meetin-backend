package org.jeavio.meetin.backend.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RoomDetails {

	private Integer roomId;
	private String roomName;
	private Integer roomCapacity;
	private List<String> roomSpecifications = new ArrayList<String>();

	
	public RoomDetails() {  	}

	public RoomDetails(Integer integer, String roomName, Integer capacity, String specifications) {
		this.roomId = integer;
		this.roomName = roomName;
		this.roomCapacity = capacity;
		if (specifications != null && !specifications.equals("")) {
			String[] specs = specifications.split(",");
			roomSpecifications.addAll(Arrays.asList(specs));
		}
	}

	public Integer getRoomId() {
		return roomId;
	}

	public String getRoomName() {
		return roomName;
	}

	public Integer getCapacity() {
		return roomCapacity;
	}

	public List<String> getRoomSpecifications() {
		return roomSpecifications;
	}
}
