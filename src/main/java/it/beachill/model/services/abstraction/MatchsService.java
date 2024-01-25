package it.beachill.model.services.abstraction;

import it.beachill.model.entities.Match;
import it.beachill.model.entities.MatchType;
import it.beachill.model.entities.Team;
import it.beachill.model.entities.Tournament;

import java.util.List;

public interface MatchsService {
    Match createMatchAndSets(int matchNumber, MatchType matchType, int groupStage,
                                    Tournament tournament, Team homeTeam, Team awayTeam,
                                    int fieldNumber, int setsnumber);

    Match createMatchAndSets(int matchNumber, MatchType matchType, Tournament tournament,
                             int fieldNumber, int setsnumber);

    public List<Match> getAllMatchesByTournament(Long tournamentId);

}
