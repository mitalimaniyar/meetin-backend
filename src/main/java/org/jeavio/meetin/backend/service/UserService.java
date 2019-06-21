package org.jeavio.meetin.backend.service;

import org.jeavio.meetin.backend.model.User;

public interface UserService {

	User findUserByEmpId(String empId);

	User findUserById(Integer id);

	Integer findIdByEmpId(String empId);

}