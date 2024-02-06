package it.beachill.model.repositories.abstractions;

import it.beachill.model.entities.tournament.SetMatch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SetMatchRepository extends JpaRepository<SetMatch, Long> {
    List<SetMatch> findByMatchId(Long id);
}
