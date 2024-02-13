package it.beachill.model.repositories.abstractions;

import it.beachill.model.entities.tournament.TournamentLevel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TournamentLevelRepository extends JpaRepository<TournamentLevel, String> {
}
