package it.beachill.model.repositories.abstractions;

import it.beachill.model.entities.Match;
import it.beachill.model.entities.TeamInTournament;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamInTournamentRepository extends JpaRepository<TeamInTournament, Long> {
}
