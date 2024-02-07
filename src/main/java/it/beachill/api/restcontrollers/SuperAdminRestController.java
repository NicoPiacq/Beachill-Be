package it.beachill.api.restcontrollers;

import it.beachill.dtos.TournamentAdminDto;
import it.beachill.model.entities.tournament.Tournament;
import it.beachill.model.entities.user.User;
import it.beachill.model.services.abstraction.SuperAdminsService;
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
@RequestMapping("/api/superadmin")
@CrossOrigin
public class SuperAdminRestController {
    private final SuperAdminsService superAdminsService;

    @Autowired
    public SuperAdminRestController(SuperAdminsService superAdminsService){
        this.superAdminsService = superAdminsService;
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
    public ResponseEntity<List<TournamentAdminDto>> getAllTournaments(){
        List<Tournament> tournaments = superAdminsService.findAllTournaments();
        List<TournamentAdminDto> result = tournaments.stream().map(TournamentAdminDto::new).toList();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/tournament/{id}")
    public ResponseEntity<TournamentAdminDto> getTournamentDetails(@PathVariable Long id){
        Optional<Tournament> tournament = superAdminsService.findTournamentById(id);
        if(tournament.isPresent()){
            TournamentAdminDto tournamentAdminDto = new TournamentAdminDto(tournament.get());
            return ResponseEntity.ok(tournamentAdminDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/tournament/create")
    public ResponseEntity<TournamentAdminDto> createTournament(@AuthenticationPrincipal User user,@RequestBody TournamentAdminDto tournamentDto) throws URISyntaxException {
        Tournament tournament = tournamentDto.fromDto();
        tournament.setStatus(1);
        tournament.setAdmin(user);
        superAdminsService.createTournament(tournament);
        TournamentAdminDto result = new TournamentAdminDto(tournament);
        URI location = new URI("/api/tournament/" + result.getId());

        return ResponseEntity.created(location).body(result);
    }

    @DeleteMapping("/tournament/delete/{id}")
    public ResponseEntity<Object> deleteTournament(@PathVariable long id){
        Optional<Tournament> result = superAdminsService.deleteTournament(id);

        return result.stream()
                .map(c -> ResponseEntity.noContent().build())
                .findFirst()
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/tournament/generate/{id}")
    public ResponseEntity<Object> generateMatchTournament(@PathVariable Long id){
        boolean result = superAdminsService.generateMatchTournament(id);
        if(result){
            return ResponseEntity.ok(true);
        }
        else return ResponseEntity.notFound().build();
    }
    @PostMapping("/tournament/calculate-group-phase-standing/{id}")
    public ResponseEntity<Object> calculateGroupStageStanding(@PathVariable Long id){
        boolean result = superAdminsService.calculateGroupStageStanding(id);
        if(result){
            return ResponseEntity.ok(true);
        }
        else return ResponseEntity.notFound().build();
    }

    @PostMapping("/tournament/add-random-result-to-group-phase-matches/{id}")
    public ResponseEntity<Object> addRandomResultToGroupPhaseMatches(@PathVariable Long id){
        boolean result = superAdminsService.addRandomResultToGroupPhaseMatches(id);
        if(result){
            return ResponseEntity.ok(true);
        }
        else return ResponseEntity.notFound().build();
    }
    
    @PostMapping("tournament/second-phase-matches/{id}")
    public ResponseEntity<Object> assignSecondPhaseMatches(@PathVariable Long id){
        boolean result = superAdminsService.calculateGroupStageStandingAndAssignMatches(id);
        if(result){
            return ResponseEntity.ok(true);
        }
        else return ResponseEntity.notFound().build();
    }

    //PORCA MADONNA
    @PostMapping("insert-script")
    public ResponseEntity<Object> insertScript(){
        boolean result = superAdminsService.insertScript();
        if(result){
            return ResponseEntity.ok(true);
        }
        else return ResponseEntity.notFound().build();
    }

    // ----------------------- FINE ADMIN FEAT TOURNAMENTS ----------------------------------------------

}