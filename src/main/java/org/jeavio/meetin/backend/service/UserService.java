package org.jeavio.meetin.backend.service;

import java.util.List;
import java.util.Map;

import org.jeavio.meetin.backend.dto.UserDTO;
import org.jeavio.meetin.backend.dto.UserInfo;
import org.jeavio.meetin.backend.model.User;

public interface UserService {

	public User findByEmpId(String empId);

	public User findUserById(Integer id);

	public Integer findIdByEmpId(String empId);

	public boolean existsByEmpId(String empId);

	public UserInfo findProfileByEmpId(String empId);

	public boolean existsById(Integer userId);

	public void addUser(UserDTO user);

	public void removeUser(String empId);

	public void modifyUserDetails(UserDTO modifiedUser);

	public boolean existsByUsername(String modifiedUsername);

	public int changePassword(String empId, Map<String, String> body);

	public List<UserInfo> findAll();
}