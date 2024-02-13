package it.beachill.api.restcontrollers;

import it.beachill.dtos.TournamentAdminDto;
import it.beachill.dtos.TournamentDto;
import it.beachill.model.entities.tournament.Tournament;
import it.beachill.model.entities.user.User;
import it.beachill.model.exceptions.CheckFailedException;
import it.beachill.model.exceptions.TournamentCheckFailedException;
import it.beachill.model.services.abstraction.AdminsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

//    @GetMapping
//    @PreAuthorize("hasAuthority('admin:read')")
//    public ResponseEntity<String> pippo() {
//        return ResponseEntity.ok("SI SONO AUTORIZZATO PER PIPPO()");
//    }
//
//    @GetMapping("/delete")
//    @PreAuthorize("hasAuthority('superadmin:delete')")
//    public ResponseEntity<String> delete() {
//        return ResponseEntity.ok("SI SONO AUTORIZZATO PER DELETE()");
//    }

    //////////// aggiunto per prova admin iniziale //////////////////
    //---------------------------------- ADMIN FEAT TOURNAMENT -------------------------------------
    @GetMapping("/tournament/all")
    public ResponseEntity<?> getAllTournaments(){
        List<Tournament> tournaments = adminsService.findAllTournaments();
        List<TournamentAdminDto> result = tournaments.stream().map(TournamentAdminDto::new).toList();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/tournament/{id}")
    public ResponseEntity<?> getTournamentDetails(@PathVariable Long id){
        Tournament tournament;
        try{
            tournament= adminsService.findTournamentById(id);
        } catch(TournamentCheckFailedException e){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
        return ResponseEntity.ok(new TournamentAdminDto(tournament));
    }

    @PostMapping("/tournament")
    public ResponseEntity<?> createTournament(@AuthenticationPrincipal User user,@RequestBody TournamentAdminDto tournamentAdminDto) throws URISyntaxException {
        try {
            adminsService.createTournament(user,tournamentAdminDto);
        } catch (TournamentCheckFailedException e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/tournament/{id}")
    public ResponseEntity<?> deleteTournament(@AuthenticationPrincipal User user, @PathVariable Long id){
        try {
            adminsService.deleteTournament(user,id);
        } catch (TournamentCheckFailedException e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/tournament/match/{id}")
    public ResponseEntity<?> generateMatchTournament(@AuthenticationPrincipal User user,@PathVariable Long id){
        try {
            adminsService.generateMatchTournament(user,id);
        } catch (TournamentCheckFailedException e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("/tournament/group-phase-standing/{id}")
    public ResponseEntity<Object> calculateGroupStageStanding(@AuthenticationPrincipal User user,@PathVariable Long id){
        try {
            adminsService.calculateGroupStageStandingAndAssignMatches(user,id);
        } catch (TournamentCheckFailedException e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/tournament/add-random-result-to-group-phase-matches/{id}")
    public ResponseEntity<Object> addRandomResultToGroupPhaseMatches(@PathVariable Long id){
        boolean result = adminsService.addRandomResultToGroupPhaseMatches(id);
        if(result){
            return ResponseEntity.ok(true);
        }
        else return ResponseEntity.notFound().build();
    }

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
