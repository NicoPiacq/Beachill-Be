package it.beachill.model.services.abstraction;

import it.beachill.model.entities.Tournament;

import java.util.List;

public interface TournamentsService {
    List<Tournament> findAllTournaments();
}
