package it.beachill.model.services.abstraction;

import it.beachill.model.entities.Tournament;

import java.util.List;
import java.util.Optional;

public interface AdminsService {

    List<Tournament> findAllTournaments();
    public Optional<Tournament> findTournamentById(Long tournamentId);
    void createTournament(Tournament tournament);
    Optional<Tournament> deleteTournament(Long id);
    boolean generateMatchTournament(Long tournamentId);
    boolean calculateGroupStageStanding(Long id);
    boolean addRandomResultToGroupPhaseMatches(Long id);

}
