package org.jeavio.meetin.backend.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.jeavio.meetin.backend.dao.RoomRepository;
import org.jeavio.meetin.backend.dto.RoomDetails;
import org.jeavio.meetin.backend.model.Room;
import org.jeavio.meetin.backend.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomServiceImpl implements RoomService {

	@Autowired
	RoomRepository roomRepository;
	
	@Override
	public List<RoomDetails> fetchRooms(){
		
		List<RoomDetails> rooms=roomRepository.getRooms();
		return rooms;
	}

	@Override
	public void addRoom(RoomDetails roomDetails) {
		if(existsByRoomName(roomDetails.getName()))
			return;
		String specifications = null;
		if (roomDetails.getSpecifications() != null && !roomDetails.getSpecifications().isEmpty()) {
			specifications = String.join(",", roomDetails.getSpecifications());
		}
		Room room=new Room(roomDetails.getName(), roomDetails.getCapacity(),specifications);
		roomRepository.save(room);
	}

	@Override
	public void removeRoom(String roomName) {
		if(existsByRoomName(roomName))
			roomRepository.deleteByRoomName(roomName);
	}

	@Override
	public void modifyRoom(RoomDetails modifiedRoomDetails) {
		if(existsByRoomName(modifiedRoomDetails.getName()) || !existsByRoomId(modifiedRoomDetails.getRoomId()))
			return;
		Room room = roomRepository.findById(modifiedRoomDetails.getRoomId()).get();
		room.setRoomName(modifiedRoomDetails.getName());
		room.setCapacity(modifiedRoomDetails.getCapacity());
		String specifications=null;
		if (modifiedRoomDetails.getSpecifications() != null && !modifiedRoomDetails.getSpecifications().isEmpty()) {
			specifications = String.join(",", modifiedRoomDetails.getSpecifications());
		}
		room.setSpecifications(specifications);
		
		roomRepository.save(room);
	}

	@Override
	public boolean existsByRoomName(String roomName) {
		return roomRepository.existsByRoomName(roomName);
	}

	@Override
	public boolean existsByRoomId(Integer roomId) {
		return roomRepository.existsById(roomId);
	}

	@Override
	public List<String> getRoomNames() {
		List<String> rooms = new ArrayList<String>();
		roomRepository.getRooms().stream().forEach( room -> rooms.add(room.getName()));
		return rooms;
	}

	@Override
	public String getRoomSpecifications(String roomName) {
		return roomRepository.findByRoomName(roomName).getSpecifications();
	}
}
