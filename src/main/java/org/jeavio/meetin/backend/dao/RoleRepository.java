package org.jeavio.meetin.backend.dao;

import org.jeavio.meetin.backend.model.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role,Integer> {

	@Query("SELECT r.id from Role r where role = :role")
	Integer findRoleId(@Param("role")String role);

	@Query("SELECT r from Role r where role = :role")
	Role findByRole(@Param("role")String role);
	
}
