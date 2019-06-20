package org.jeavio.meetin.backend.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.jeavio.meetin.backend.model.audit.UserDateAudit;
import org.springframework.lang.NonNull;

@Entity
@Table(name = "rooms")
public class Room extends UserDateAudit {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1262214367221359569L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NonNull
	@Column(name = "room_name")
	@Size(max = 50)
	private String roomName;

	@NonNull
	private Integer capacity;

	private String specifications;

	public Room() { 	}

	public Room(@Size(max = 50) String roomName,Integer capacity) {
		this.roomName=roomName;
		this.capacity=capacity;
	}

	public Room(@Size(max = 50) String roomName, Integer capacity, String specifications) {
		this.roomName = roomName;
		this.capacity = capacity;
		this.specifications = specifications;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public Integer getCapacity() {
		return capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}

	public String getSpecifications() {
		return specifications;
	}

	public void setSpecifications(String specifications) {
		this.specifications = specifications;
	}
}
