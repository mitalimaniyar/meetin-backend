package org.jeavio.meetin.backend.service;

import java.util.List;

import org.jeavio.meetin.backend.dto.EventDetails;

public interface NotificationService {

	public void notifyAll(EventDetails event, String type, String repeat);

	public void notifyAll(EventDetails event, String type, String repeat,List<String> membersEmails);

}
