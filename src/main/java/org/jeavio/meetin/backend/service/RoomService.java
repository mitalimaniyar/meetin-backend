package org.jeavio.meetin.backend.service;

import java.util.List;

import org.jeavio.meetin.backend.dto.RoomDetails;

public interface RoomService {

	public List<RoomDetails> fetchRooms();

	public void addRoom(RoomDetails room);
	
	public void removeRoom(String roomName);
	
	public void modifyRoom(RoomDetails modifiedRoomDetails);

	public boolean existsByRoomName(String roomName);
	
	public boolean existsByRoomId(Integer roomId);

	public List<String> getRoomNames();

	public String getRoomSpecifications(String roomName);
}