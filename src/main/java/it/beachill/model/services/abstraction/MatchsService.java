package it.beachill.model.services.abstraction;

import it.beachill.dtos.SetMatchDto;
import it.beachill.model.entities.tournament.*;
import it.beachill.model.entities.user.User;
import it.beachill.model.exceptions.CheckFailedException;

import java.util.List;

public interface MatchsService {
    Match createMatchAndSets(int matchNumber, MatchType matchType, int groupStage,
                                    Tournament tournament, Team homeTeam, Team awayTeam,
                                    int fieldNumber, int setsnumber, User matchAdmin);

    Match createMatchAndSets(int matchNumber, MatchType matchType, Tournament tournament,
                             int fieldNumber, int setsnumber, User matchAdmin);

    List<Match> getAllMatchesByTournament(Long tournamentId);
    
    void updateMatchSetResult(User user, Long setMatchId, SetMatchDto setMatchDto) throws CheckFailedException;

    List<SetMatch> getAllSetsByMatchId(Long setMatchId) throws CheckFailedException;

    void updateMatchResult(User user, Long matchId) throws CheckFailedException;

    List<Match> getAllMatchesByAdmin(User user);

    List<Match> getAllMatchesByTeam(Long teamId);
}
