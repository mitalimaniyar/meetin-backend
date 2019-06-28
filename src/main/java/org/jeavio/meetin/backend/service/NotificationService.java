package org.jeavio.meetin.backend.service;

import org.jeavio.meetin.backend.dto.EventDetails;

public interface NotificationService {

	public void notifyAll(EventDetails event, String type, String repeat);

}
