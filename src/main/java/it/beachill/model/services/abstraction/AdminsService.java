package it.beachill.model.services.abstraction;

import it.beachill.model.entities.Tournament;

import java.util.List;
import java.util.Optional;

public interface AdminsService {

    List<Tournament> findAllTournaments();
    public boolean generateMatchTournament(Long tournamentId);
    public Optional<Tournament> findTournamentById(Long tournamentId);


    boolean calculateGroupStageStanding(Long id);

    boolean addRandomResultToGroupPhaseMatches(Long id);
}
