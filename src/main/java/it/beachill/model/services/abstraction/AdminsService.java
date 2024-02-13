package it.beachill.model.services.abstraction;

import it.beachill.dtos.TournamentAdminDto;
import it.beachill.dtos.TournamentDto;
import it.beachill.model.entities.tournament.GroupStageStanding;
import it.beachill.model.entities.tournament.Match;
import it.beachill.model.entities.tournament.Tournament;
import it.beachill.model.entities.user.User;
import it.beachill.model.exceptions.TournamentCheckFailedException;

import java.util.List;
import java.util.Optional;

public interface AdminsService {

    List<Tournament> findAllTournaments();
    public Tournament findTournamentById(Long tournamentId) throws TournamentCheckFailedException;
    void createTournament(User user, TournamentAdminDto tournamentAdminDto) throws TournamentCheckFailedException;
    void deleteTournament(User user,Long id) throws TournamentCheckFailedException;
    void generateMatchTournament(User user,Long tournamentId) throws TournamentCheckFailedException;
    
    boolean addRandomResultToGroupPhaseMatches(Long id);
    
    void calculateGroupStageStandingAndAssignMatches(User user,Long id) throws TournamentCheckFailedException;
    boolean generateMatchTournament10Long(Tournament tournament);
    boolean generateMatchTournament10Short(Tournament tournament);
    boolean calculateGroupStageStanding(Long id);
    int[][] assignSchemaFromTournament(Tournament tournament);
    List<Match> assignTeamsToSecondPhaseMatches(List<GroupStageStanding> roundTeams, int[][] secondPhaseTournamentSchema, List<Match> matches);
    boolean insertScript();
}
