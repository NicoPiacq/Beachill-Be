package it.beachill.api.restcontrollers;

import it.beachill.dtos.AuthenticationResponseDto;
import it.beachill.dtos.RegistrationDto;
import it.beachill.model.entities.User;
import it.beachill.model.services.abstraction.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserRestController {

    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponseDto> register(@RequestBody RegistrationDto request) {
        return ResponseEntity.ok(userService.register(request));
    }
}
