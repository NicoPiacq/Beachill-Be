package it.beachill.model.services.abstraction;

import it.beachill.dtos.SetMatchDto;
import it.beachill.model.entities.tournament.Match;
import it.beachill.model.entities.tournament.MatchType;
import it.beachill.model.entities.tournament.Team;
import it.beachill.model.entities.tournament.Tournament;
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
    
    void setMatchSetResult(User user, Long setMatchId, SetMatchDto setMatchDto) throws CheckFailedException;
}
