package it.beachill.model.services.abstraction;

import it.beachill.dtos.UserDto;
import it.beachill.model.entities.tournament.Team;
import it.beachill.model.entities.user.User;
import it.beachill.model.exceptions.CheckFailedException;
import it.beachill.model.exceptions.TeamCheckFailedException;
import it.beachill.model.exceptions.TournamentCheckFailedException;

import java.util.List;
import java.util.Optional;

public interface SuperAdminService {
	List<User> getAllUser();
	
	void setUserRole(UserDto userDto) throws CheckFailedException;
	
	void deleteUser(Long userId) throws CheckFailedException;
	
	void deleteTournament(Long id) throws TournamentCheckFailedException;
	
	void generateMatchTournament(Long id) throws TournamentCheckFailedException;
	
	void calculateGroupStageStandingAndAssignMatches(Long id) throws TournamentCheckFailedException;
	
	Optional<Team> deleteTeam(Long teamId) throws TeamCheckFailedException;
	
	Optional<Team> deleteEnrolledPlayer(Long teamComponentId, Long teamId) throws TeamCheckFailedException;
}
