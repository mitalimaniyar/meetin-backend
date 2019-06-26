package org.jeavio.meetin.backend.service;

import org.jeavio.meetin.backend.model.Event;

public interface NotificationService {

	public void notifyAll(Event event, String type, String repeat);

}
