package it.beachill.model.services.abstraction;

import it.beachill.dtos.MatchDto;
import it.beachill.dtos.SetMatchDto;
import it.beachill.model.entities.tournament.*;
import it.beachill.model.entities.user.User;
import it.beachill.model.exceptions.CheckFailedException;

import java.time.LocalDate;
import java.util.List;

public interface MatchsService {
    Match createMatchAndSets(int matchNumber, MatchType matchType, int groupStage,
                                    Tournament tournament, Team homeTeam, Team awayTeam,
                                    int fieldNumber, int setsnumber, User matchAdmin);

    Match createMatchAndSets(int matchNumber, MatchType matchType, Tournament tournament,
                             int fieldNumber, int setsnumber, User matchAdmin);

    List<Match> getAllMatchesByTournament(Long tournamentId);
    
    void updateMatchResultAndPlayersScore(User user, Long matchId) throws CheckFailedException;
    void updateMatchSetResult(User user,  SetMatchDto setMatchDto) throws CheckFailedException;
    
    List<SetMatch> getAllSetsByMatchId(Long setMatchId) throws CheckFailedException;
    
    List<Match> getAllMatchesByAdmin(User user);

    List<Match> getAllMatchesByTeam(Long teamId);


    void createMatch(User user, Long homeTeamId, Long awayTeamId, int matchNumber, LocalDate date) throws CheckFailedException;

    List<Match> getAllMatchesInvite(User user);

    void updateStatusMatch(User user, Long matchId, Integer status) throws CheckFailedException;
    
    List<Match> getAllMatchesByPlayer(User user);

    List<Match> getAllMatchesByUserId(Long id) throws CheckFailedException;

    Match getMatchDetails(Long id) throws CheckFailedException;
}
