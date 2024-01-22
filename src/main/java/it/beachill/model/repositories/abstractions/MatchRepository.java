package it.beachill.model.repositories.abstractions;

import it.beachill.model.entities.Match;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepository extends JpaRepository<Match, Long> {
}
