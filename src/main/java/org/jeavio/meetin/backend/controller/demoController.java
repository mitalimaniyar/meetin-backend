package org.jeavio.meetin.backend.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collector;

import org.jeavio.meetin.backend.dao.RoleRepository;
import org.jeavio.meetin.backend.dao.RoomRepository;
import org.jeavio.meetin.backend.dao.UserRepository;
import org.jeavio.meetin.backend.dao.UserTeamRoleRepository;
import org.jeavio.meetin.backend.dto.RoomDetails;
import org.jeavio.meetin.backend.dto.TeamDetails;
import org.jeavio.meetin.backend.dto.UserInfo;
import org.jeavio.meetin.backend.model.Role;
import org.jeavio.meetin.backend.model.Team;
import org.jeavio.meetin.backend.model.User;
import org.jeavio.meetin.backend.model.UserTeamRole;
import org.jeavio.meetin.backend.security.AppUser;
import org.jeavio.meetin.backend.service.AdminService;
import org.jeavio.meetin.backend.service.RoomService;
import org.jeavio.meetin.backend.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class demoController {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoomRepository roomRepository;
	
	@Autowired
	RoomService roomservice;
	
	@Autowired
	UserTeamRoleRepository userTeamRoleRepository;

	@Autowired
	TeamService teamService;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	AdminService adminService;
	
	
	
	@GetMapping("/")
	public Object load(){
//		String username="mitali@gmail.com";
//		Map<Integer,String> map1=new LinkedHashMap<Integer, String>();
//		Map<Integer,String> map2=new LinkedHashMap<Integer, String>();
//		User user = userRepository.findByUsername(username)
//				.orElseThrow(() -> new UsernameNotFoundException(username + " not found."));
//
//		Set<UserTeamRole> utrs=user.getUserTeamRole();
//		int count=0;
//		for(UserTeamRole utr:utrs) {
//			if(utr.getRole()!=null)
//			map1.put(++count, utr.getRole().getRole());
//			if(utr.getTeam()!=null)
//			map2.put(count, utr.getTeam().getTeamName());
//		}
//		System.out.println(map1);
//		System.out.println(map2);
//		return map1;
//		return user.getUserTeamRole().stream().map(utr->utr.getRole().getRole()).toArray(String[]::new);
		
//		userTeamRoleRepository.deleteByUserId(1,2);
//		teamService.promoteTeamAdmin(1,"MANMI94");
//		List<String> empIds = new ArrayList<String>();
//		empIds.add("MANMI94");
//		teamService.addTeamMembers(4,empIds);
//		return userRepository.findByEmpId("MANMI94");
//		adminService.promoteTeamAdmin(4, "MANMI94");
//		roleRepository.save(new Role(7,"super"));
		return new BCryptPasswordEncoder().encode("9722224433");
	}
	
}
