package org.jeavio.meetin.backend.dao;

import java.util.List;
import java.util.Optional;

import org.jeavio.meetin.backend.model.User;
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
    
//    Change pwd query pending

       
}
