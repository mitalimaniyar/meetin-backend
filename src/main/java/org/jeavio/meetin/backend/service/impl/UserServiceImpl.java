package org.jeavio.meetin.backend.service.impl;

import org.jeavio.meetin.backend.dao.UserRepository;
import org.jeavio.meetin.backend.dto.UserDTO;
import org.jeavio.meetin.backend.dto.UserInfo;
import org.jeavio.meetin.backend.model.User;
import org.jeavio.meetin.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Override
	public User findByEmpId(String empId) {
		return userRepository.findByEmpId(empId).orElse(null);
	}
	
	@Override
	public User findUserById(Integer id) {
		return userRepository.findById(id).orElse(null);
	}
	
	@Override
	public Integer findIdByEmpId(String empId) {
		if(existsByEmpId(empId))
			return userRepository.findUserIdByEmpId(empId);
		else
			return -1;
	}

	@Override
	public boolean existsByEmpId(String empId) {
		return userRepository.existsByEmpId(empId);
	}

	@Override
	public UserInfo findProfileByEmpId(String empId) {
		if(existsByEmpId(empId))
			return userRepository.findProfileByEmpId(empId);
		else
			return null;
	}

	@Override
	public boolean existsById(Integer userId) {
		return userRepository.existsById(userId);
	}

	@Override
	public void addUser(UserDTO user) {
		if(!existsByEmpId(user.getEmpId()) && !existsByUsername(user.getUsername())) {
			String password = passwordEncoder.encode(user.getPassword());
			User newUser = new User(user.getEmpId(),user.getFirstName(),user.getLastName(),user.getUsername(),password,user.getEmail());
			userRepository.save(newUser);
		}
	}

	@Override
	public void removeUser(String empId) {
		if(existsByEmpId(empId)) {
			userRepository.deleteByEmpId(empId);
		}
	}

	@Override
	public void modifyUserDetails(UserDTO modifiedUser) {
		if(!existsById(modifiedUser.getId())) {
			return;
		}
		Integer userId = modifiedUser.getId();
		String modifiedEmpId = modifiedUser.getEmpId();
		String modifiedUsername = modifiedUser.getUsername();
		if(!(findUserById(userId).getEmpId()).equals(modifiedEmpId)
				&& existsByEmpId(modifiedEmpId)) {
			return;
		}
		if(!(findUserById(userId).getUsername()).equals(modifiedUsername)
				&& existsByUsername(modifiedUsername)) {
			return;
		}
		User user = findUserById(userId);
		user.setEmpId(modifiedEmpId);
		user.setFirstName(modifiedUser.getFirstName());
		user.setLastName(modifiedUser.getLastName());
		user.setUsername(modifiedUsername);
		user.setEmail(modifiedUser.getEmail());
		
		userRepository.save(user);
		
	}

	@Override
	public boolean existsByUsername(String username) {
		return userRepository.existsByUsername(username);
	}
}
