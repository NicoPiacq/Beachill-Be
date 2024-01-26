package it.beachill.model.services.abstraction;

import it.beachill.dtos.AuthenticationResponseDto;
import it.beachill.dtos.LoginDto;
import it.beachill.dtos.RegistrationDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface UserService {

    AuthenticationResponseDto register(RegistrationDto request);

    AuthenticationResponseDto login(LoginDto request);

    AuthenticationResponseDto refreshToken(HttpServletRequest request, HttpServletResponse response);
}
