package it.beachill.api.restcontrollers;

import it.beachill.dtos.ReservationDto;
import it.beachill.model.entities.reservation.Reservation;
import it.beachill.model.entities.user.User;
import it.beachill.model.exceptions.ReservationChecksFailedException;
import it.beachill.model.services.abstraction.ReservationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

    @PostMapping("")
    public ResponseEntity<?> createNewReservation(@RequestBody ReservationDto reservationDto, @AuthenticationPrincipal User user){
        try{
            this.reservationsService.createNewReservation(reservationDto, user);
        }
        catch(ReservationChecksFailedException e){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }
}
