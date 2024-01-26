package it.beachill.model.services.abstraction;

import it.beachill.model.entities.Tournament;

import java.util.List;
import java.util.Optional;

public interface TournamentsService {
    List<Tournament> findAllTournaments();
    public Optional<Tournament> findTournamentById(Long tournamentId);

}
