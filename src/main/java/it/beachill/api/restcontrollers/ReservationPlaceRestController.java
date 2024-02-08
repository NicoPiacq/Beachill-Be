package it.beachill.api.restcontrollers;

import it.beachill.dtos.ReservationPlaceDto;
import it.beachill.model.entities.reservation.ReservationPlace;
import it.beachill.model.services.abstraction.ReservationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @GetMapping("/{id}")
    public ResponseEntity<?> getReservationPlace(@PathVariable Long id) {
        Optional<ReservationPlace> reservationPlaceOptional = reservationsService.getReservationPlace(id);
        if(reservationPlaceOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        ReservationPlaceDto reservationPlaceDto = new ReservationPlaceDto(reservationPlaceOptional.get());
        return ResponseEntity.ok(reservationPlaceDto);
    }
}
