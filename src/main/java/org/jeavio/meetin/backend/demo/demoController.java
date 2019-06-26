package org.jeavio.meetin.backend.demo;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collector;

import javax.validation.Valid;

import org.jeavio.meetin.backend.dao.RoleRepository;
import org.jeavio.meetin.backend.dao.RoomRepository;
import org.jeavio.meetin.backend.dao.UserRepository;
import org.jeavio.meetin.backend.dao.UserTeamRoleRepository;
import org.jeavio.meetin.backend.dto.EventDTO;
import org.jeavio.meetin.backend.dto.RoomDetails;
import org.jeavio.meetin.backend.dto.TeamDetails;
import org.jeavio.meetin.backend.dto.UserInfo;
import org.jeavio.meetin.backend.model.Role;
import org.jeavio.meetin.backend.model.Team;
import org.jeavio.meetin.backend.model.User;
import org.jeavio.meetin.backend.model.UserTeamRole;
import org.jeavio.meetin.backend.security.AppUser;
import org.jeavio.meetin.backend.service.AdminService;
import org.jeavio.meetin.backend.service.EventService;
import org.jeavio.meetin.backend.service.RoomService;
import org.jeavio.meetin.backend.service.TeamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.MongoClient;

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
	
	@Autowired
	CustomerRepository repository;
	
	@Autowired
	EventService eventService;
	@PostMapping("/")
	public Object load(@RequestBody EventDTO newEvent) throws ParseException{
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
//		repository.deleteAll();
//
//		// save a couple of customers
//		repository.save(new Customer("Alice", "Smith"));
//		repository.save(new Customer("Bob", "Smith"));
//		repository.save(new Customer(new Date()));
//		// fetch all customers
//		System.out.println("Customers found with findAll():");
//		System.out.println("-------------------------------");
//		for (Customer customer : repository.findAll()) {
//			System.out.println(customer);
//		}
//		System.out.println();
//
//		// fetch an individual customer
//		System.out.println("Customer found with findByFirstName('Alice'):");
//		System.out.println("--------------------------------");
//		System.out.println(repository.findByFirstName("Alice"));
//
//		System.out.println("Customers found with findByLastName('Smith'):");
//		System.out.println("--------------------------------");
//		for (Customer customer : repository.findByLastName("Smith")) {
//			System.out.println(customer);
//		}
		eventService.addEvent(newEvent,"MANMI94");
		return eventService.findEventByEmpId("MANMI94");
		
		
		
		
	}
	private static Logger log = LoggerFactory.getLogger(demoController.class);
	@GetMapping("/")
	public Object addRoom(){
		MongoOperations mongoOps = new MongoTemplate(new SimpleMongoDbFactory(new MongoClient(), "meetin"));

	    Person p = new Person("Joe", 34);

	    // Insert is used to initially store the object into the database.
	    mongoOps.insert(p);
	    log.info("Insert: " + p);

	    // Find
	    p = mongoOps.findById(p.getId(), Person.class);
	    log.info("Found: " + p);

	    // Update
	    mongoOps.updateFirst(new Query(Criteria.where("name").is("Joe")), Update.update("age", 35), Person.class);
	    p = mongoOps.findOne(new Query(Criteria.where("name").is("Joe")),Person.class);
	    
	    
		List<Person> p1 = mongoOps.find(new Query(), Person.class);
	    log.info("Updated: " + p);
	    log.info(p1.toString());
	    // Delete
//	    mongoOps.remove(p);

	    // Check that deletion worked
	    List<Person> people =  mongoOps.findAll(Person.class);
	    log.info("Number of people = : " + people.size());


//	    mongoOps.dropCollection(Person.class);
	 
		return null;
	}
	
//	@DeleteMapping("/")
//	public String test(@RequestParam("roomName") String roomName) {
//		return roomName;
//	}
	
	public static void main(String[] args) {
		System.out.println("Hey");
		Date d1 = new Date();
		Date d2 = new Date();
		System.out.println(d1);
		System.out.println(d2);
		System.out.println(d1.before(d1) || d1.equals(d1));
	}
}
