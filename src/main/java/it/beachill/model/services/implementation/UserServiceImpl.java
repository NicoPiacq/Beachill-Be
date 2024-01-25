package it.beachill.model.services.implementation;

import it.beachill.dtos.AuthenticationResponseDto;
import it.beachill.dtos.RegistrationDto;
import it.beachill.model.entities.Token;
import it.beachill.model.entities.User;
import it.beachill.model.repositories.abstractions.TokenRepository;
import it.beachill.model.repositories.abstractions.UserRepository;
import it.beachill.model.services.abstraction.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, TokenRepository tokenRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    private void saveUserToken(User user, String jwtToken) {
        Token token = new Token(user, jwtToken);
        tokenRepository.save(token);
    }

    @Override
    public AuthenticationResponseDto register(RegistrationDto request) {
        User newUser = new User(request, passwordEncoder.encode(request.getPassword()));
        var savedUser = userRepository.save(newUser);
        var jwtToken = jwtService.generateToken(savedUser);
        saveUserToken(savedUser, jwtToken);
        return new AuthenticationResponseDto(jwtToken);
    }
}
