package org.jeavio.meetin.backend.service.impl;

import org.jeavio.meetin.backend.dao.UserRepository;
import org.jeavio.meetin.backend.model.User;
import org.jeavio.meetin.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;
	
	@Override
	public User findUserByEmpId(String empId) {
		return userRepository.findByEmpId(empId).orElse(new User());
	}
	
	@Override
	public User findUserById(Integer id) {
		return userRepository.findById(id).orElse(new User());
	}
	
	@Override
	public Integer findIdByEmpId(String empId) {
		return userRepository.findUserIdByEmpId(empId);
	}
}
