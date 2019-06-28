package org.jeavio.meetin.backend.dao;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.jeavio.meetin.backend.dto.UserInfo;
import org.jeavio.meetin.backend.model.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
	
    Optional<User> findByUsername(String username);

    List<User> findByIdIn(List<String> userIds);

    Optional<User> findById(Integer id);
    
    @Query("SELECT u.id from User u WHERE u.empId = :empId")
	Integer findUserIdByEmpId(@Param("empId")String empId);
    
    Optional<User> findByEmpId(String empId);

	boolean existsByEmpId(String empId);

	@Query("SELECT new org.jeavio.meetin.backend.dto.UserInfo(u.id,u.empId,u.firstName,u.lastName,u.email) FROM User u WHERE u.empId = :empId")
	UserInfo findProfileByEmpId(@Param("empId")String empId);

	@Modifying
	@Transactional
	void deleteByEmpId(String empId);

	boolean existsByUsername(String username);

	@Query("SELECT new org.jeavio.meetin.backend.dto.UserInfo(u.id,u.empId,u.firstName,u.lastName,u.email) FROM User u")
	List<UserInfo> findAllUsers();
    
       
}
