package it.beachill.model.repositories.abstractions;

import it.beachill.model.entities.tournament.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TournamentRepository extends JpaRepository<Tournament, Long> {
    @Query("SELECT t FROM Tournament t LEFT JOIN FETCH t.place p LEFT JOIN FETCH t.tournamentType tt WHERE t.id = :tournamentId")
    Optional<Tournament> findById(Long tournamentId);
}
