package it.beachill.api.restcontrollers;

import it.beachill.dtos.TournamentDto;
import it.beachill.model.entities.Tournament;
import it.beachill.model.services.abstraction.TournamentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tournament")
@CrossOrigin
public class TournamentRestController {
    private final TournamentsService tournamentsService;

    @Autowired
    public TournamentRestController(TournamentsService tournamentsService){
        this.tournamentsService = tournamentsService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<TournamentDto>> getAllTournaments(){
        List<Tournament> tournaments = tournamentsService.findAllTournaments();
        List<TournamentDto> result = tournaments.stream().map(TournamentDto::new).toList();
        return ResponseEntity.ok(result);
    }
}
