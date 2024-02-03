package it.beachill.model.services.abstraction;

import it.beachill.model.entities.Tournament;
import it.beachill.model.entities.TournamentPlace;
import it.beachill.model.entities.TournamentType;
import it.beachill.model.entities.User;
import it.beachill.model.exceptions.TournamentCheckFailedException;

import java.util.List;
import java.util.Optional;

public interface TournamentsService {
    List<Tournament> findAllTournaments();
    public Optional<Tournament> findTournamentById(Long tournamentId);
    List<TournamentType> findAllTournamentsTypes();
    List<TournamentPlace> findAllPlaces();
	
	void enrolledTeam(User user, Long tournamentId, Long teamId) throws TournamentCheckFailedException;
}
