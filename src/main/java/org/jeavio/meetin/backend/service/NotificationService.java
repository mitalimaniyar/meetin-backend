package org.jeavio.meetin.backend.service;

import java.util.List;

import org.jeavio.meetin.backend.dto.MemberInfo;

public interface NotificationService {

	public void notify(MemberInfo participants, String string,String repeat);

	public void notifyAll(List<MemberInfo> participants, String string, String repeat);

}
