package it.beachill.api.restcontrollers;

import it.beachill.dtos.PlaceDto;
import it.beachill.model.entities.TournamentPlace;
import it.beachill.model.services.abstraction.TournamentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/place")
@CrossOrigin
public class PlaceRestController {
    private final TournamentsService tournamentsService;

    @Autowired
    public PlaceRestController(TournamentsService tournamentsService){
        this.tournamentsService = tournamentsService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<PlaceDto>> getAllPlaces(){
        List<TournamentPlace> places = tournamentsService.findAllPlaces();
        List<PlaceDto> result = places.stream().map(PlaceDto::new).toList();
        return ResponseEntity.ok(result);
    }
}
