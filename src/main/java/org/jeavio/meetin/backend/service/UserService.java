package org.jeavio.meetin.backend.service;

import org.jeavio.meetin.backend.dao.UserRepository;
import org.jeavio.meetin.backend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;
	
	public User findUserByEmpId(String empId) {
		return userRepository.findByEmpId(empId).orElse(new User());
	}
	
	public User findUserById(Integer id) {
		return userRepository.findById(id).orElse(new User());
	}
	
	public Integer findIdByEmpId(String empId) {
		return userRepository.findUserIdByEmpId(empId);
	}
}
