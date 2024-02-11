package it.beachill.api.restcontrollers;

import it.beachill.dtos.*;
import it.beachill.model.entities.reservation.Reservation;
import it.beachill.model.entities.reservation.ScheduleProp;
import it.beachill.model.entities.user.User;
import it.beachill.model.exceptions.ReservationChecksFailedException;
import it.beachill.model.services.abstraction.FieldService;
import it.beachill.model.services.abstraction.ReservationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/reservation")
@CrossOrigin
public class ReservationRestController {
    private final ReservationsService reservationsService;
    private final FieldService fieldService;

    @Autowired
    public ReservationRestController(ReservationsService reservationsService, FieldService fieldService){
        this.reservationsService = reservationsService;
        this.fieldService = fieldService;
    }

    @GetMapping("/all/date")
    public ResponseEntity<?> getAllReservationsPerDate(@RequestParam LocalDate date){
        List<Reservation> reservationList = this.reservationsService.getAllReservationsPerDate(date);
        List<ReservationDto> reservationDtoList = reservationList.stream().map(ReservationDto::new).toList();
        return ResponseEntity.ok(reservationList);
    }

    @GetMapping("/reservation-details")
    public ResponseEntity<?> getFieldProperties(@RequestParam Long fieldId, @RequestParam LocalDate date) {
        List<ReservationSlotsDto> slots = reservationsService.getAllSlotsPerDate(fieldId, date);
        return ResponseEntity.ok(slots);
    }

    @GetMapping("")
    public ResponseEntity<?> getAllReservationByUser(@AuthenticationPrincipal User user){
        List<Reservation> reservations = reservationsService.getAllReservationByUser(user);
        List<ReservationDto> reservationDtoList = reservations.stream().map(ReservationDto::new).toList();
        return ResponseEntity.ok(reservationDtoList);
    }

    @PostMapping("")
    public ResponseEntity<?> createNewReservation(@RequestBody ReservationRequestDto reservationRequestDto, @AuthenticationPrincipal User user){
        try{
            this.reservationsService.createNewReservation(reservationRequestDto, user);
        }
        catch(ReservationChecksFailedException e){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }
}
