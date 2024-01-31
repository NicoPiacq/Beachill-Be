package it.beachill.model.services.abstraction;

import it.beachill.model.entities.Team;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public interface TeamsService {
	
	List<Team> findAllTeams();
	List<Team> findAllTeamsByPlayerId(Long id);
	
	Team createTeam(Team team);
	
	
	Optional<Team> addPlayerToTeam(Long teamId, Long playerId);
}
