package it.beachill.api.restcontrollers;


import it.beachill.dtos.TournamentLevelDto;
import it.beachill.dtos.TournamentTypeDto;
import it.beachill.model.entities.tournament.TournamentLevel;
import it.beachill.model.entities.tournament.TournamentType;
import it.beachill.model.services.abstraction.TournamentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tournament-type")
@CrossOrigin
public class TournamentTypeRestController {
    private final TournamentsService tournamentsService;

    @Autowired
    public TournamentTypeRestController(TournamentsService tournamentsService){
        this.tournamentsService = tournamentsService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<TournamentTypeDto>> getAllTournamentsTypes(){
        List<TournamentType> tournamentsTypes = tournamentsService.findAllTournamentsTypes();
        List<TournamentTypeDto> result = tournamentsTypes.stream().map(TournamentTypeDto::new).toList();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/levels")
    public ResponseEntity<?> getAllTournamentLevel(){
        List<TournamentLevel> tournamentLevelList = tournamentsService.findAllLevels();
        List<TournamentLevelDto> result = tournamentLevelList.stream().map(TournamentLevelDto::new).toList();
        return ResponseEntity.ok(result);
    }
}
