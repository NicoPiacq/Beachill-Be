package it.beachill.model.services.abstraction;

import it.beachill.model.entities.Team;

import java.util.List;
import java.util.Optional;

public interface TeamsService {
	
	List<Team> findAllTeams();
	List<Team> findAllTeamsByTeamLeader(Long id);

	List<Team> findAllTeamsByPlayer(Long id);
	Team createTeam(Team team);
	
	
	Optional<Team> addCaptainToTeam(Long teamId, Long playerId);
	Optional<Team> addPlayerToTeam(Long teamId, Long playerToAddId, Long requestingPlayerId);
	
	Optional<Team> deleteEnrolledPlayer(Long playerId, Long teamId);
	

}
