package it.beachill.model.services.implementation;

import it.beachill.model.entities.tournament.Player;
import it.beachill.model.entities.tournament.Score;
import it.beachill.model.entities.tournament.ScoreType;
import it.beachill.model.repositories.abstractions.ScoreRepository;
import it.beachill.model.services.abstraction.ScoresService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JPAScoresService implements ScoresService {
    private final ScoreRepository scoreRepository;

    public JPAScoresService(ScoreRepository scoreRepository) {
        this.scoreRepository = scoreRepository;
    }

    @Override
    public List<Score> getRankingByScoreType(ScoreType scoreType) {
        return scoreRepository.findByScoreTypeOrderByScoreDesc(scoreType);
    }

    @Override
    public List<Score> getRankingByPlayerId(Long playerId) {
        return scoreRepository.findByPlayer(new Player(playerId));
    }
}
