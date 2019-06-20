package org.jeavio.meetin.backend.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.jeavio.meetin.backend.dto.TeamDetails;
import org.jeavio.meetin.backend.model.Team;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends CrudRepository<Team, Integer> {

	@Query("SELECT new org.jeavio.meetin.backend.dto.TeamDetails(t.id,t.teamName) from Team t")
	public List<TeamDetails> findAllTeams();

	@Query("DELETE FROM Team t WHERE t.id = :teamId")
	@Modifying
	@Transactional
	public void removeTeam(@Param("teamId") Integer teamId);
	
}
