package it.beachill.api.restcontrollers;

import it.beachill.dtos.TournamentDto;
import it.beachill.model.entities.Tournament;
import it.beachill.model.entities.User;
import it.beachill.model.services.abstraction.AdminsService;
import it.beachill.model.services.abstraction.TournamentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin
public class AdminRestController {
    private final AdminsService adminsService;

    @Autowired
    public AdminRestController(AdminsService adminsService){
        this.adminsService = adminsService;
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
    //---------------------------------- ADMIN FEAT TOURNAMENT -------------------------------------
    @GetMapping("/tournament/all")
    public ResponseEntity<List<TournamentDto>> getAllTournaments(){
        List<Tournament> tournaments = adminsService.findAllTournaments();
        List<TournamentDto> result = tournaments.stream().map(TournamentDto::new).toList();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/tournament/{id}")
    public ResponseEntity<TournamentDto> getTournamentDetails(@PathVariable Long id){
        Optional<Tournament> tournament = adminsService.findTournamentById(id);
        if(tournament.isPresent()){
            TournamentDto tournamentDto = new TournamentDto(tournament.get());
            return ResponseEntity.ok(tournamentDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/tournament/create")
    public ResponseEntity<TournamentDto> createTournament(@AuthenticationPrincipal User user,@RequestBody TournamentDto tournamentDto) throws URISyntaxException {
        Tournament tournament = tournamentDto.fromDto();
        tournament.setStatus(1);
        tournament.setUser(user);
        adminsService.createTournament(tournament);
        TournamentDto result = new TournamentDto(tournament);
        URI location = new URI("/api/tournament/" + result.getId());

        return ResponseEntity.created(location).body(result);
    }

    @DeleteMapping("/tournament/delete/{id}")
    public ResponseEntity<Object> deleteTournament(@PathVariable long id){
        Optional<Tournament> result = adminsService.deleteTournament(id);

        return result.stream()
                .map(c -> ResponseEntity.noContent().build())
                .findFirst()
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/tournament/generate/{id}")
    public ResponseEntity<Object> generateMatchTournament(@PathVariable Long id){
        boolean result = adminsService.generateMatchTournament(id);
        if(result){
            return ResponseEntity.ok(true);
        }
        else return ResponseEntity.notFound().build();
    }
    @PostMapping("/tournament/calculate-group-phase-standing/{id}")
    public ResponseEntity<Object> calculateGroupStageStanding(@PathVariable Long id){
        boolean result = adminsService.calculateGroupStageStanding(id);
        if(result){
            return ResponseEntity.ok(true);
        }
        else return ResponseEntity.notFound().build();
    }

    @PostMapping("/tournament/add-random-result-to-group-phase-matches/{id}")
    public ResponseEntity<Object> addRandomResultToGroupPhaseMatches(@PathVariable Long id){
        boolean result = adminsService.addRandomResultToGroupPhaseMatches(id);
        if(result){
            return ResponseEntity.ok(true);
        }
        else return ResponseEntity.notFound().build();
    }
    
    @PostMapping("tournament/assign-second-phase-matches/{id}")
    public ResponseEntity<Object> assignSecondPhaseMatches(@PathVariable Long id){
        boolean result = adminsService.calculateGroupStageStandingAndAssignMatches(id);
        if(result){
            return ResponseEntity.ok(true);
        }
        else return ResponseEntity.notFound().build();
    }

    //PORCA MADONNA
    @PostMapping("insert-script")
    public ResponseEntity<Object> insertScript(){
        boolean result = adminsService.insertScript();
        if(result){
            return ResponseEntity.ok(true);
        }
        else return ResponseEntity.notFound().build();
    }

    // ----------------------- FINE ADMIN FEAT TOURNAMENTS ----------------------------------------------

}
