package it.beachill.api.restcontrollers;

import it.beachill.dtos.AuthenticationResponseDto;
import it.beachill.dtos.LoginDto;
import it.beachill.dtos.RegistrationDto;
import it.beachill.model.entities.user.User;
import it.beachill.model.exceptions.CheckFailedException;
import it.beachill.model.exceptions.LoginChecksFailedExceptions;
import it.beachill.model.exceptions.RegistrationChecksFailedException;
import it.beachill.model.services.abstraction.UsersService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class UserRestController {

    private final UsersService usersService;

    public UserRestController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/role")
    public ResponseEntity<?> getUserRole(@AuthenticationPrincipal User user){
        String result;
        try {
            result = usersService.getUserRole(user);
        } catch (CheckFailedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto request) {
        AuthenticationResponseDto auth;
        try {
            auth = usersService.login(request);
        } catch (LoginChecksFailedExceptions e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(auth);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegistrationDto request) {
        AuthenticationResponseDto auth;
        try {
            auth = usersService.register(request);
        } catch (RegistrationChecksFailedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(auth);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<AuthenticationResponseDto> refresh(HttpServletRequest request, HttpServletResponse response) {
        return ResponseEntity.ok(usersService.refreshToken(request, response));
    }

}
