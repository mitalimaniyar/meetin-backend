package org.jeavio.meetin.backend.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.jeavio.meetin.backend.api.NotificationClient;
import org.jeavio.meetin.backend.dto.ApiResponse;
import org.jeavio.meetin.backend.dto.EventDetails;
import org.jeavio.meetin.backend.dto.NotificationDTO;
import org.jeavio.meetin.backend.dto.NotificationRequest;
import org.jeavio.meetin.backend.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {

	@Autowired
	NotificationClient notificationClient;

	private static Logger log = LoggerFactory.getLogger(NotificationServiceImpl.class);

	@Override
	public void notifyAll(EventDetails event, String type, String repeat) {
		
		NotificationRequest requestBody = prepareRequestBody(event, type, repeat);
		log.info("Notification type : {} : Sending notifications to : {}", type,requestBody.getEmailIds());
		ApiResponse response = notificationClient.sendNotification(requestBody);
		if (response.getCode() != 200)
			log.info("Sending Notification failed.");
	}

	private NotificationRequest prepareRequestBody(EventDetails event, String type, String repeat) {
		NotificationDTO notificationEvent = getNotification(event, repeat);
		List<String> emailIds = getNotifiers(event);
		NotificationRequest requestBody = new NotificationRequest(type, emailIds, notificationEvent);
		return requestBody;
	}
	
	private List<String> getNotifiers(EventDetails event) {
		List<String> emailIds = new ArrayList<String>();
		emailIds.add(event.getOrganizer().getEmail());
		event.getMembers().forEach(member -> emailIds.add(member.getEmail()));
		return emailIds;
	}

	private NotificationDTO getNotification(EventDetails event, String repeat) {
		List<String> members = new ArrayList<String>();
		event.getMembers().forEach(member -> members.add(member.getName()));
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		Date start = null;
		Date end = null;
		try {
			start = format.parse(event.getStart());
			end = format.parse(event.getEnd());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		NotificationDTO notification = new NotificationDTO(event.getTitle(), event.getAgenda(),
				event.getOrganizer().getName(), event.getRoomName(), event.getRoomSpecifications(), members, start, end,
				repeat);
		return notification;

	}

	@Override
	public void notifyAll(EventDetails event, String type, String repeat,List<String> membersEmails) {
		NotificationRequest requestBody = prepareRequestBody(event, type, repeat);
		requestBody.setEmailIds(membersEmails);
		log.info("Notification type : {} : Sending notifications to : {}", type,requestBody.getEmailIds());
		ApiResponse response = notificationClient.sendNotification(requestBody);
		if (response.getCode() != 200)
			log.info("Sending Notification failed.");
	}

}
