package it.beachill.api.restcontrollers;

import it.beachill.dtos.ReservationPlaceDto;
import it.beachill.model.entities.reservation.ReservationPlace;
import it.beachill.model.services.abstraction.ReservationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reservation-place")
@CrossOrigin
public class ReservationPlaceRestController {
    private final ReservationsService reservationsService;

    @Autowired
    public ReservationPlaceRestController(ReservationsService reservationsService) {
        this.reservationsService = reservationsService;
    }

    @GetMapping("")
    public ResponseEntity<?> getAllReservationPlaces(){
        List<ReservationPlace> reservationPlaces = reservationsService.getAllReservationPlaces();
        List<ReservationPlaceDto> result = reservationPlaces.stream().map(ReservationPlaceDto::new).toList();
        return ResponseEntity.ok(result);
    }
}
