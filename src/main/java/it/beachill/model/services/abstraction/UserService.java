package it.beachill.model.services.abstraction;

import it.beachill.dtos.AuthenticationResponseDto;
import it.beachill.dtos.LoginDto;
import it.beachill.dtos.RegistrationDto;
import it.beachill.model.exceptions.LoginChecksFailedExceptions;
import it.beachill.model.exceptions.RegistrationChecksFailedException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public interface UserService {

    // int è usato come segnalatore al momento
    AuthenticationResponseDto register(RegistrationDto request) throws RegistrationChecksFailedException;

    AuthenticationResponseDto login(LoginDto request) throws LoginChecksFailedExceptions;

    AuthenticationResponseDto refreshToken(HttpServletRequest request, HttpServletResponse response);
}
