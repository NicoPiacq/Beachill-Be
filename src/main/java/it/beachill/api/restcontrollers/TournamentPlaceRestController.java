package it.beachill.api.restcontrollers;

import it.beachill.dtos.TournamentPlaceDto;
import it.beachill.model.entities.tournament.TournamentPlace;
import it.beachill.model.services.abstraction.TournamentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tournament-place")
@CrossOrigin
public class TournamentPlaceRestController {
    private final TournamentsService tournamentsService;

    @Autowired
    public TournamentPlaceRestController(TournamentsService tournamentsService){
        this.tournamentsService = tournamentsService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<TournamentPlaceDto>> getAllPlaces(){
        List<TournamentPlace> places = tournamentsService.findAllPlaces();
        List<TournamentPlaceDto> result = places.stream().map(TournamentPlaceDto::new).toList();
        return ResponseEntity.ok(result);
    }
}
