package org.jeavio.meetin.backend.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.lang.NonNull;

public class RoomDetails {

    @NonNull
	private Integer roomId;
	
    @NonNull
	private String name;
	
    @NonNull
	private Integer capacity;
	
	private List<String> specifications = new ArrayList<String>();

	
	public RoomDetails() {  	}

	public RoomDetails(Integer integer, String roomName, Integer capacity, String roomSpecifications) {
		this.roomId = integer;
		this.name = roomName;
		this.capacity = capacity;
		if (roomSpecifications != null && !roomSpecifications.equals("")) {
			String[] specs = roomSpecifications.split(",");
			specifications.addAll(Arrays.asList(specs));
		}
	}

	public Integer getRoomId() {
		return roomId;
	}

	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCapacity() {
		return capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}

	public List<String> getSpecifications() {
		return specifications;
	}

	public void setSpecifications(List<String> specifications) {
		this.specifications = specifications;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("{\"roomId\":\"");
		builder.append(roomId);
		builder.append("\", \"name\":\"");
		builder.append(name);
		builder.append("\", \"capacity\":\"");
		builder.append(capacity);
		builder.append("\", \"specifications\":\"");
		builder.append(specifications);
		builder.append("\"}");
		return builder.toString();
	}

}
