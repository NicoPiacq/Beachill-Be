package it.beachill.api.restcontrollers;

import it.beachill.model.entities.reservation.Reservation;
import it.beachill.model.services.abstraction.ReservationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/reservation")
@CrossOrigin
public class ReservationRestController {
    private final ReservationsService reservationsService;
    @Autowired
    public ReservationRestController(ReservationsService reservationsService){
        this.reservationsService = reservationsService;
    }

    @GetMapping("")
    public ResponseEntity<?> getAllReservationsPerDate(@RequestParam LocalDate date){
        List< Reservation> reservationList = this.reservationsService.getAllReservationsPerDate(date);
        return ResponseEntity.ok(reservationList);
    }
}
