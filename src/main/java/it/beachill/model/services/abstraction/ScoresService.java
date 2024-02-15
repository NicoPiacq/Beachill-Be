package it.beachill.model.services.abstraction;

import it.beachill.model.entities.tournament.Score;
import it.beachill.model.entities.tournament.ScoreType;

import java.util.List;

public interface ScoresService {
    List<Score> getRankingByScoreType(ScoreType scoreType);

    List<Score> getRankingByPlayerId(Long playerId);
}
