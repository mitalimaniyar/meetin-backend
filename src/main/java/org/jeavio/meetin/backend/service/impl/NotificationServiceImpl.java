package org.jeavio.meetin.backend.service.impl;

import java.util.List;

import org.jeavio.meetin.backend.dto.MemberInfo;
import org.jeavio.meetin.backend.service.NotificationService;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {

	@Override
	public void notify(MemberInfo participants, String string, String repeat) {
		// TODO Auto-generated method stub

	}

	@Override
	public void notifyAll(List<MemberInfo> participants, String string, String repeat) {
		// TODO Auto-generated method stub

	}

}
