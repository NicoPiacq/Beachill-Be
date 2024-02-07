package it.beachill.model.services.abstraction;

import it.beachill.model.entities.tournament.Tournament;

import java.util.List;
import java.util.Optional;

public interface SuperAdminsService {

    List<Tournament> findAllTournaments();
    public Optional<Tournament> findTournamentById(Long tournamentId);
    void createTournament(Tournament tournament);
    Optional<Tournament> deleteTournament(Long id);
    boolean generateMatchTournament(Long tournamentId);
    boolean calculateGroupStageStanding(Long id);
    boolean addRandomResultToGroupPhaseMatches(Long id);
    
    boolean calculateGroupStageStandingAndAssignMatches(Long id);

    boolean insertScript();
}
