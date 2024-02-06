package it.beachill.api.restcontrollers;

import it.beachill.dtos.PlayerDto;
import it.beachill.dtos.TeamComponentDto;
import it.beachill.model.entities.tournament.Player;
import it.beachill.model.entities.tournament.TeamComponent;
import it.beachill.model.exceptions.PlayerChecksFailedException;
import it.beachill.model.services.abstraction.PlayersService;
import it.beachill.model.services.implementation.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/player")
@CrossOrigin
public class PlayerRestController {
    private final PlayersService playersService;
    private final JwtService jwtService;

    @Autowired
    public PlayerRestController(PlayersService playersService, JwtService jwtService){
        this.playersService = playersService;
        this.jwtService = jwtService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<PlayerDto>> getAllPlayers(){
        List<Player> players = playersService.findAllPlayers();
        List<PlayerDto> result = players.stream().map(PlayerDto::new).toList();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/enrolled/{id}")
    public ResponseEntity<?> getAllPlayerByTeamId(@PathVariable Long id){
        List<TeamComponent> players;
        try {
            players = playersService.findAllPlayerByTeamId(id);
        } catch (PlayerChecksFailedException e){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
        List<TeamComponentDto> result = players.stream().map(TeamComponentDto::new).toList();
        return ResponseEntity.ok(result);
    }
}
