package it.beachill.api.restcontrollers;

import it.beachill.dtos.TournamentDto;
import it.beachill.model.entities.Tournament;
import it.beachill.model.services.abstraction.TournamentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

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

    @PostMapping("/generate/{id}")
    public ResponseEntity<Object> generateMatchTournament(@PathVariable Long id){
        boolean result = tournamentsService.generateMatchTournament(id);
        if(result){
            return ResponseEntity.ok(true);
        }
        else return ResponseEntity.notFound().build();
    }
}
