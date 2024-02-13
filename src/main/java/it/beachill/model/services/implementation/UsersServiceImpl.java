package it.beachill.model.services.implementation;

import it.beachill.dtos.AuthenticationResponseDto;
import it.beachill.dtos.LoginDto;
import it.beachill.dtos.RegistrationDto;
import it.beachill.model.entities.tournament.Player;
import it.beachill.model.entities.tournament.Score;
import it.beachill.model.entities.tournament.ScoreType;
import it.beachill.model.entities.user.Role;
import it.beachill.model.entities.user.Token;
import it.beachill.model.entities.user.User;
import it.beachill.model.exceptions.LoginChecksFailedExceptions;
import it.beachill.model.exceptions.RegistrationChecksFailedException;
import it.beachill.model.repositories.abstractions.*;
import it.beachill.model.services.abstraction.UsersService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UsersServiceImpl implements UsersService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PlayerRepository playerRepository;
    private final ScoreTypeRepository scoreTypeRepository;
    private final ScoreRepository scoreRepository;


    @Autowired
    public UsersServiceImpl(UserRepository userRepository, TokenRepository tokenRepository,
                            PasswordEncoder passwordEncoder, JwtService jwtService,
                            AuthenticationManager authenticationManager, PlayerRepository playerRepository, ScoreTypeRepository scoreTypeRepository, ScoreRepository scoreRepository) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.playerRepository = playerRepository;
        this.scoreTypeRepository = scoreTypeRepository;
        this.scoreRepository = scoreRepository;
    }

    private void saveUserToken(User user, String jwtToken) {
        Token token = new Token(user, jwtToken);
        tokenRepository.save(token);
    }

    @Override
    public AuthenticationResponseDto login(LoginDto request) throws LoginChecksFailedExceptions {
        Optional<User> userOpt = userRepository.findByEmail(request.getEmail());
        if(userOpt.isEmpty()) {
            throw new LoginChecksFailedExceptions("CREDENTIALS_NOT_VALID"); // Gli errori sono uguali solo per sicurezza.
        }
        User user = userOpt.get();
        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new LoginChecksFailedExceptions("CREDENTIALS_NOT_VALID"); // In questo modo un attaccante non capisce se è sbagliata email o password.
        }
        user.setLastLogin(LocalDate.now());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(), request.getPassword()));
        String newJwtToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, newJwtToken);
        return new AuthenticationResponseDto(newJwtToken, user);
    }

    @Transactional
    public AuthenticationResponseDto register(RegistrationDto request) throws RegistrationChecksFailedException {
        // TESTA SE L'UTENTE GIA' ESISTE: SE VERO, LANCIA UNA EXCEPTION CUSTOM
        if(checkUserAlreadyRegistered(request.getEmail())) {
            throw new RegistrationChecksFailedException("EMAIL_EXISTS");
        }
        //DA CAMBIARE!!!!
        request.setRole(Role.ADMIN);
        //FINE

        User newUser = new User(request, passwordEncoder.encode(request.getPassword()));

        Player newPlayer = playerRepository.save((new Player()));
        newUser.setPlayer(newPlayer);
        newUser.setRegistrationDate(LocalDate.now());

        userRepository.save(newUser);

        newPlayer.setUser(newUser);

        //AGGIUNGE TUTTE LE RIGHE NELLA TABELLA SCORE
        List<ScoreType> scoreTypeList = scoreTypeRepository.findAll();
        for (ScoreType scoreType : scoreTypeList){
            scoreRepository.save(new Score(scoreType, newPlayer, 1000));
        }

        String jwtToken = jwtService.generateToken(newUser);
        saveUserToken(newUser, jwtToken);
        return new AuthenticationResponseDto(jwtToken, newUser);
    }

    private boolean checkUserAlreadyRegistered(String email) {
        Optional<User> checkUser = userRepository.findByEmail(email);
        return checkUser.isPresent();
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
