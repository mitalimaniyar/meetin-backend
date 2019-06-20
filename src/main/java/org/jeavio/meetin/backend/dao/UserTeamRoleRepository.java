package org.jeavio.meetin.backend.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.jeavio.meetin.backend.dto.TeamDetails;
import org.jeavio.meetin.backend.dto.UserInfo;
import org.jeavio.meetin.backend.model.Role;
import org.jeavio.meetin.backend.model.Team;
import org.jeavio.meetin.backend.model.User;
import org.jeavio.meetin.backend.model.UserTeamRole;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTeamRoleRepository extends CrudRepository<UserTeamRole, Integer> {

	@Query("SELECT DISTINCT new org.jeavio.meetin.backend.dto.UserInfo(utr.user.id,utr.user.empId,utr.user.firstName,utr.user.lastName,utr.user.email) FROM UserTeamRole utr WHERE utr.team.id = :teamId")
	public List<UserInfo> findMembersByTeamId(@Param("teamId") Integer teamId);

	@Query("SELECT DISTINCT new org.jeavio.meetin.backend.dto.TeamDetails(utr.team.id,utr.team.teamName) from UserTeamRole utr WHERE utr.team IS NOT NULL AND utr.user.id = :userId")
	public List<TeamDetails> findTeamsByUserId(@Param("userId") Integer userId);

	@Query("SELECT CASE WHEN count(utr)> 0 THEN true ELSE false END FROM UserTeamRole utr WHERE utr.user.id = :userId AND utr.team.id = :teamId AND utr.role.role = 'team_admin' ")
	public Boolean isUserTeamAdmin(@Param("userId") Integer userId, @Param("teamId") Integer teamId);

	@Query("SELECT DISTINCT new org.jeavio.meetin.backend.dto.UserInfo(utr.user.id,utr.user.empId,utr.user.firstName,utr.user.lastName,utr.user.email) FROM UserTeamRole utr WHERE utr.team.id = :teamId AND utr.role.role = 'team_admin' ")
	public List<UserInfo> findTeamAdminsByTeamId(@Param("teamId") Integer teamId);

	@Query("SELECT DISTINCT new org.jeavio.meetin.backend.dto.UserInfo(utr.user.id,utr.user.empId,utr.user.firstName,utr.user.lastName,utr.user.email) FROM UserTeamRole utr "
			+ "WHERE utr.user.id NOT IN (SELECT DISTINCT utr.user.id FROM UserTeamRole utr WHERE utr.team.id = :teamId)")
	public List<UserInfo> finNonTeamMembers(@Param("teamId") Integer teamId);

	@Query("SELECT CASE WHEN count(utr)> 0 THEN true ELSE false END FROM UserTeamRole utr WHERE utr.user = :user AND utr.team = :team AND utr.role = :role ")
	public boolean existRecord(@Param("user") User user, @Param("team") Team team, @Param("role") Role role);

	@Modifying
	@Query("DELETE FROM UserTeamRole utr where utr.user.id = :userId AND utr.team.id = :teamId ")
	@Transactional
	public void deleteByUserId(@Param("userId") Integer userId, @Param("teamId") Integer teamId);

	@Query("SELECT CASE WHEN count(utr)> 0 THEN true ELSE false END FROM UserTeamRole utr WHERE utr.user = :user AND utr.role = :role AND utr.team IS NULL")
	public boolean existAdmin(@Param("user") User user, @Param("role") Role role);

	public UserTeamRole findByUserAndTeamAndRole(User user, Team team, Role memberRole);

}
