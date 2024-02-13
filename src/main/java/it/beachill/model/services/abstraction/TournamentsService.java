package it.beachill.model.services.abstraction;

import it.beachill.model.entities.tournament.Tournament;
import it.beachill.model.entities.tournament.TournamentLevel;
import it.beachill.model.entities.tournament.TournamentPlace;
import it.beachill.model.entities.tournament.TournamentType;
import it.beachill.model.entities.user.User;
import it.beachill.model.exceptions.TournamentCheckFailedException;

import java.util.List;
import java.util.Optional;

public interface TournamentsService {
    List<Tournament> findAllTournaments();
    public Optional<Tournament> findTournamentById(Long tournamentId);
    List<TournamentType> findAllTournamentsTypes();
    List<TournamentPlace> findAllPlaces();
	
	void enrolledTeam(User user, Long tournamentId, Long teamId) throws TournamentCheckFailedException;

    List<TournamentLevel> findAllLevels();
}
