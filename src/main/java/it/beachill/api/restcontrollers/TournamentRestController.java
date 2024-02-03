package it.beachill.api.restcontrollers;

import it.beachill.dtos.TeamComponentDto;
import it.beachill.dtos.TournamentDto;
import it.beachill.model.entities.Tournament;
import it.beachill.model.entities.User;
import it.beachill.model.exceptions.TournamentCheckFailedException;
import it.beachill.model.services.abstraction.TournamentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/tournament")
@CrossOrigin
public class TournamentRestController {
    private final TournamentsService tournamentsService;

    @Autowired
    public TournamentRestController(TournamentsService tournamentsService){
        this.tournamentsService = tournamentsService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<TournamentDto>> getAllTournaments(){
        List<Tournament> tournaments = tournamentsService.findAllTournaments();
        List<TournamentDto> result = tournaments.stream().map(TournamentDto::new).toList();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TournamentDto> getTournamentDetails(@PathVariable Long id){
        Optional<Tournament> tournament = tournamentsService.findTournamentById(id);
        if(tournament.isPresent()){
            TournamentDto tournamentDto = new TournamentDto(tournament.get());
            return ResponseEntity.ok(tournamentDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping("/{tournamentId}")
    public ResponseEntity<?> enrolledTeamByTournamentId(@AuthenticationPrincipal User user, @PathVariable Long tournamentId, @RequestParam Long teamId ) {
        try {
            tournamentsService.enrolledTeam(user, tournamentId, teamId);
        }
        catch (TournamentCheckFailedException e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }
}
