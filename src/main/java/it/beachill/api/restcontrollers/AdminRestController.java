package it.beachill.api.restcontrollers;

import it.beachill.dtos.ReservationPlaceDto;
import it.beachill.dtos.SchedulePropDto;
import it.beachill.model.entities.reservation.ReservationPlace;
import it.beachill.model.entities.user.User;
import it.beachill.model.exceptions.RegistrationChecksFailedException;
import it.beachill.model.exceptions.ReservationChecksFailedException;
import it.beachill.model.services.abstraction.AdminsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin
public class AdminRestController {
    private final AdminsService adminsService;

    @Autowired
    public AdminRestController(AdminsService adminsService) {
        this.adminsService = adminsService;
    }

    @PostMapping("/place")
    public ResponseEntity<?> createNewPlace(@AuthenticationPrincipal User user, @RequestBody ReservationPlaceDto reservationPlaceDto) {
        if (user.getId() != reservationPlaceDto.getManagerId()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        ReservationPlace reservationPlace = reservationPlaceDto.fromDto();
        try {
            adminsService.createNewPlace(reservationPlace);
        } catch (ReservationChecksFailedException e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/properties")
    public ResponseEntity<?> createNewScheduleProperties(@AuthenticationPrincipal User user, @RequestBody SchedulePropDto schedulePropDto){
        try {
            adminsService.createNewScheduleProperties(user, schedulePropDto);
        }catch(ReservationChecksFailedException e){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }



}
