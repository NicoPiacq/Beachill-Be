package it.beachill.api.restcontrollers;

import it.beachill.dtos.ReservationPlaceDto;
import it.beachill.dtos.SchedulePropDto;
import it.beachill.dtos.SetMatchDto;
import it.beachill.model.entities.reservation.ReservationPlace;
import it.beachill.model.entities.tournament.Tournament;
import it.beachill.model.entities.user.User;
import it.beachill.model.exceptions.CheckFailedException;
import it.beachill.model.exceptions.ReservationChecksFailedException;
import it.beachill.model.services.abstraction.ManagersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api/manager")
@CrossOrigin
public class ManagerRestController {
    private final ManagersService managersService;

    @Autowired
    public ManagerRestController(ManagersService managersService) {
        this.managersService = managersService;
    }

    @PostMapping("/place")
    public ResponseEntity<?> createNewPlace(@AuthenticationPrincipal User user, @RequestBody ReservationPlaceDto reservationPlaceDto) {
        if (!Objects.equals(user.getId(), reservationPlaceDto.getManagerId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        ReservationPlace reservationPlace = reservationPlaceDto.fromDto();
        try {
            managersService.createNewPlace(reservationPlace);
        } catch (ReservationChecksFailedException e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/properties")
    public ResponseEntity<?> createNewScheduleProperties(@AuthenticationPrincipal User user, @RequestBody SchedulePropDto schedulePropDto){
        try {
            managersService.createNewScheduleProperties(user, schedulePropDto);
        }catch(ReservationChecksFailedException e){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/reservation-place/{id}")
    public ResponseEntity<?> deleteReservationPlace(@AuthenticationPrincipal User user, @PathVariable Long id){
        try {
            managersService.deleteReservationPlace(user, id);
        } catch (CheckFailedException e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/reservation-place/{id}")
    public ResponseEntity<?> changeReservationPLaceDetails(@AuthenticationPrincipal User user, @RequestBody ReservationPlaceDto reservationPlaceDto, @PathVariable Long id) {
        if(!Objects.equals(id, reservationPlaceDto.getId())){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("I dati dei place non sono coerenti");
        }
        try {
            managersService.changeReservationPlaceDetails(user, reservationPlaceDto.fromDto());
        } catch (CheckFailedException e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }

   // @GetMapping("/reservation/")
}
