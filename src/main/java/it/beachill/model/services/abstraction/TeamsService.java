package it.beachill.model.services.abstraction;

import it.beachill.model.entities.Team;

import java.util.List;

public interface TeamsService {
	
	List<Team> findAllTeams();
	List<Team> findAllTeamsByPlayerId(Long id);
	
	Team createTeam(Team team);
	
	
}
