package it.beachill.model.services.implementation;

import it.beachill.dtos.AuthenticationResponseDto;
import it.beachill.dtos.LoginDto;
import it.beachill.dtos.RegistrationDto;
import it.beachill.model.entities.Player;
import it.beachill.model.entities.Token;
import it.beachill.model.entities.User;
import it.beachill.model.repositories.abstractions.PlayerRepository;
import it.beachill.model.repositories.abstractions.TokenRepository;
import it.beachill.model.repositories.abstractions.UserRepository;
import it.beachill.model.services.abstraction.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PlayerRepository playerRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, TokenRepository tokenRepository,
                           PasswordEncoder passwordEncoder, JwtService jwtService,
                           AuthenticationManager authenticationManager, PlayerRepository playerRepository) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.playerRepository = playerRepository;
    }

    private void saveUserToken(User user, String jwtToken) {
        Token token = new Token(user, jwtToken);
        tokenRepository.save(token);
    }

    @Override
    public AuthenticationResponseDto register(RegistrationDto request) {
        Optional<User> checkUser = userRepository.findByEmail(request.getEmail());
        if(checkUser.isPresent()) {
            return new AuthenticationResponseDto(null, null);
        }
        User newUser = new User(request, passwordEncoder.encode(request.getPassword()));
        Player newPlayer = playerRepository.save(new Player());
        newUser.setPlayer(newPlayer);
        var savedUser = userRepository.save(newUser);
        var jwtToken = jwtService.generateToken(savedUser);
        saveUserToken(savedUser, jwtToken);
        newPlayer.setUser(savedUser);
        return new AuthenticationResponseDto(jwtToken, savedUser);
    }

    @Override
    public AuthenticationResponseDto login(LoginDto request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        Optional<User> userOpt = userRepository.findByEmail(request.getEmail());
        if(userOpt.isEmpty()) {
            throw new UsernameNotFoundException("EMAIL_NOT_FOUND");
        }
        User user = userOpt.get();
        String newJwtToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, newJwtToken);
        return new AuthenticationResponseDto(newJwtToken, user);
    }

    @Override
    public AuthenticationResponseDto refreshToken(HttpServletRequest request, HttpServletResponse response) {
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String token;
        final String email;
        final String newToken;

        // CHECK SE L'HEADER DELLA RICHIESTA E' CORRETTO DA CUI PRELEVARE IL TOKEN
        if(header == null || !header.startsWith("Bearer ")) {
            try {
                response.sendError(400, "RICHIESTA NON VALIDA: TOKEN NON VALIDO");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return new AuthenticationResponseDto(null, null);
        }
        token = header.substring(7);

        // CHECK SE TROVA UN USERNAME NEL TOKEN TROVATO
        email = jwtService.extractUsername(token);
        if(email == null) {
            try {
                response.sendError(400, "RICHIESTA NON VALIDA: UTENTE NON VALIDO");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return new AuthenticationResponseDto(null, null);
        }

        // CHECK SE TROVA UN USER ASSOCIATO ALL'EMAIL ESTRATTA DAL TOKEN
        Optional<User> userOpt = userRepository.findByEmail(email);
        if(userOpt.isEmpty()) {
            try {
                response.sendError(400, "RICHIESTA NON VALIDA: UTENTE NON TROVATO");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return new AuthenticationResponseDto(null, null);
        }

        // CHECK SE IL TOKEN ASSOCIATO A QUELL'USER E' VALIDO/NON SCADUTO
        User user = userOpt.get();
        if(!jwtService.isTokenValid(token, user)) {
            try {
                response.sendError(400, "RICHIESTA NON VALIDA: TOKEN NON VALIDO O SCADUTO");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return new AuthenticationResponseDto(null, null);
        }

        newToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, newToken);
        return new AuthenticationResponseDto(newToken, user);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }
}
