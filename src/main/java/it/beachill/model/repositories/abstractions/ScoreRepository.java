package it.beachill.model.repositories.abstractions;

import it.beachill.model.entities.tournament.Player;
import it.beachill.model.entities.tournament.Score;
import it.beachill.model.entities.tournament.ScoreType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ScoreRepository extends JpaRepository<Score, Long> {
    Optional<Score> findByPlayerAndScoreType(Player p, ScoreType matchLevel);
}
