package it.beachill.api.restcontrollers;

import it.beachill.dtos.TournamentDto;
import it.beachill.model.entities.Tournament;
import it.beachill.model.services.abstraction.TournamentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin")
public class AdminRestController {
    private final TournamentsService tournamentsService;

    @Autowired
    public AdminRestController(TournamentsService tournamentsService){
        this.tournamentsService = tournamentsService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<String> pippo() {
        return ResponseEntity.ok("SI SONO AUTORIZZATO PER PIPPO()");
    }

    @GetMapping("/delete")
    @PreAuthorize("hasAuthority('superadmin:delete')")
    public ResponseEntity<String> delete() {
        return ResponseEntity.ok("SI SONO AUTORIZZATO PER DELETE()");
    }

    //////////// aggiunto per prova admin iniziale //////////////////
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

    @PostMapping("/generate/{id}")
    public ResponseEntity<Object> generateMatchTournament(@PathVariable Long id){
        boolean result = tournamentsService.generateMatchTournament(id);
        if(result){
            return ResponseEntity.ok(true);
        }
        else return ResponseEntity.notFound().build();
    }
    @PostMapping("/calculate-group-phase-standing/{id}")
    public ResponseEntity<Object> calculateGroupStageStanding(@PathVariable Long id){
        boolean result = tournamentsService.calculateGroupStageStanding(id);
        if(result){
            return ResponseEntity.ok(true);
        }
        else return ResponseEntity.notFound().build();
    }

    @PostMapping("/add-random-result-to-group-phase-matches/{id}")
    public ResponseEntity<Object> addRandomResultToGroupPhaseMatches(@PathVariable Long id){
        boolean result = tournamentsService.addRandomResultToGroupPhaseMatches(id);
        if(result){
            return ResponseEntity.ok(true);
        }
        else return ResponseEntity.notFound().build();
    }

}
