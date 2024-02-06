package it.beachill.model.services.abstraction;

import it.beachill.model.entities.tournament.Match;
import it.beachill.model.entities.tournament.MatchType;
import it.beachill.model.entities.tournament.Team;
import it.beachill.model.entities.tournament.Tournament;

import java.util.List;

public interface MatchsService {
    Match createMatchAndSets(int matchNumber, MatchType matchType, int groupStage,
                                    Tournament tournament, Team homeTeam, Team awayTeam,
                                    int fieldNumber, int setsnumber);

    Match createMatchAndSets(int matchNumber, MatchType matchType, Tournament tournament,
                             int fieldNumber, int setsnumber);

    public List<Match> getAllMatchesByTournament(Long tournamentId);

}
