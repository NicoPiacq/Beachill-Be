package it.beachill.model.services.abstraction;

import it.beachill.dtos.AuthenticationResponseDto;
import it.beachill.dtos.RegistrationDto;

public interface UserService {

    AuthenticationResponseDto register(RegistrationDto request);

}
