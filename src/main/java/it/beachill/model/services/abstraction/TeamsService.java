package it.beachill.model.services.abstraction;

import it.beachill.model.entities.tournament.Team;
import it.beachill.model.entities.tournament.TeamComponent;
import it.beachill.model.entities.user.User;
import it.beachill.model.exceptions.TeamCheckFailedException;

import java.util.List;
import java.util.Optional;

public interface TeamsService {
	
	List<Team> findAllTeams();
	List<Team> findAllTeamsByTeamLeader(Long id);

	List<Team> findAllTeamsByPlayer(Long id);
	Team createTeam(Team team) throws TeamCheckFailedException;
	
	
	Optional<Team> addCaptainToTeam(Long teamId, Long playerId);
	Optional<Team> invitePlayerToTeam(Long teamId, Long playerToAddId, Long requestingPlayerId) throws TeamCheckFailedException;
	
	Optional<Team> deleteEnrolledPlayer(Long teamComponentId, Long teamId, User requestingUser) throws TeamCheckFailedException;


	Optional<Team> updateStatusInvitation(Long teamComponentId, Long teamId, User user, Integer status) throws TeamCheckFailedException;

	Optional<Team> deleteTeam(Long teamId, User user) throws TeamCheckFailedException;

	Optional<Team> findTeamById(Long id);

	List<TeamComponent> getAllInvite(User user);
	
	List<Team> searchTeamByString(String toFind);
}
