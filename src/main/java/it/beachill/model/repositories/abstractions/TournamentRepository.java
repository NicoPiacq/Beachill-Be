package it.beachill.model.repositories.abstractions;

import it.beachill.model.entities.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TournamentRepository extends JpaRepository<Tournament, Long> {
}
