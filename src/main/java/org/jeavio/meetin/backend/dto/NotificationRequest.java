package org.jeavio.meetin.backend.dto;

import java.util.ArrayList;
import java.util.List;

public class NotificationRequest {

	private String triggerType;
	private List<String> emailIds = new ArrayList<String>();
	private NotificationDTO event;

	public NotificationRequest() {
	}

	public NotificationRequest(String triggerType, List<String> emailIds, NotificationDTO event) {
		super();
		this.triggerType = triggerType;
		this.emailIds = emailIds;
		this.event = event;
	}

	public String getTriggerType() {
		return triggerType;
	}

	public void setTriggerType(String triggerType) {
		this.triggerType = triggerType;
	}

	public List<String> getEmailIds() {
		return emailIds;
	}

	public void setEmailIds(List<String> emailIds) {
		this.emailIds = emailIds;
	}

	public NotificationDTO getEvent() {
		return event;
	}

	public void setEvent(NotificationDTO event) {
		this.event = event;
	}

}
