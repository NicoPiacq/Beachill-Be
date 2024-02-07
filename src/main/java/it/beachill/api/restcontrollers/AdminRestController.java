package it.beachill.api.restcontrollers;

import it.beachill.dtos.ReservationPlaceDto;
import it.beachill.model.services.abstraction.AdminsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> createNewPlace(@RequestBody ReservationPlaceDto reservationPlaceDto){

    }

}
