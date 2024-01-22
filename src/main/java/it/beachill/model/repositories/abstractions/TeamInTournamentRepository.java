package it.beachill.model.repositories.abstractions;

import it.beachill.model.entities.TeamInTournament;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamInTournamentRepository extends JpaRepository<TeamInTournament, Long> {
    List<TeamInTournament> findByTournamentId(long tournamentId);
}
