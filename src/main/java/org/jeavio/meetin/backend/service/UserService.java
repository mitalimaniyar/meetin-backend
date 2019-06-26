package org.jeavio.meetin.backend.service;

import org.jeavio.meetin.backend.dto.UserDTO;
import org.jeavio.meetin.backend.dto.UserInfo;
import org.jeavio.meetin.backend.model.User;

public interface UserService {

	User findByEmpId(String empId);

	User findUserById(Integer id);

	Integer findIdByEmpId(String empId);

	boolean existsByEmpId(String empId);

	UserInfo findProfileByEmpId(String empId);

	boolean existsById(Integer userId);

	void addUser(UserDTO user);

	void removeUser(String empId);

	void modifyUserDetails(UserDTO modifiedUser);

	boolean existsByUsername(String modifiedUsername);
}