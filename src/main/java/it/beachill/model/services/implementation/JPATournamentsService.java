package it.beachill.model.services.implementation;

import it.beachill.model.entities.Tournament;
import it.beachill.model.repositories.abstractions.TournamentRepository;
import it.beachill.model.services.abstraction.TournamentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JPATournamentsService implements TournamentsService {

    private final TournamentRepository tournamentRepository;

    @Autowired
    public JPATournamentsService(TournamentRepository tournamentRepository){
        this.tournamentRepository = tournamentRepository;
    }
    @Override
    public List<Tournament> findAllTournaments() {
        return tournamentRepository.findAll();
    }
}