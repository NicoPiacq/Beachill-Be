package it.beachill.api.restcontrollers;

import it.beachill.dtos.MatchDto;
import it.beachill.dtos.SetMatchDto;
import it.beachill.model.entities.tournament.Match;
import it.beachill.model.entities.tournament.SetMatch;
import it.beachill.model.entities.user.User;
import it.beachill.model.exceptions.CheckFailedException;
import it.beachill.model.services.abstraction.MatchsService;
import org.apache.catalina.mbeans.SparseUserDatabaseMBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/match")
@CrossOrigin
public class MatchRestController {
    
    private final MatchsService matchsService;
    
    @Autowired
    public MatchRestController(MatchsService matchsService) {
        this.matchsService = matchsService;
    }

    @GetMapping("")
    public ResponseEntity<?> getAllMatchesByAdmin(@AuthenticationPrincipal User user){
        List<Match> matches = matchsService.getAllMatchesByAdmin(user);
        List<MatchDto> result = matches.stream().map(MatchDto::new).toList();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/team/{teamId}")
    public ResponseEntity<?> getAllMatchesByTeam(@PathVariable Long teamId){
        List<Match> matches = matchsService.getAllMatchesByTeam(teamId);
        List<MatchDto> result = matches.stream().map(MatchDto::new).toList();
        return ResponseEntity.ok(result);
    }


    
    @GetMapping("/all/tournament/{tournamentId}")
    public ResponseEntity<List<MatchDto>> getAllMatchesByTournament(@PathVariable Long tournamentId) {
        List<Match> matches = matchsService.getAllMatchesByTournament(tournamentId);
        List<MatchDto> result = matches.stream().map(MatchDto::new).toList();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{matchId}/set")
    public ResponseEntity<?> getAllSetsByMatchId(@PathVariable Long matchId){
        List<SetMatch> setMatchList;
        try {
           setMatchList = matchsService.getAllSetsByMatchId(matchId);
        } catch (CheckFailedException e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
        return ResponseEntity.ok(setMatchList);
    }
    
    @PatchMapping("/{setMatchId}/set")
    public ResponseEntity<?> updateMatchSetResult(@AuthenticationPrincipal User user, @PathVariable Long setMatchId, @RequestBody SetMatchDto setMatchDto) {
        try {
            matchsService.updateMatchSetResult(user, setMatchId, setMatchDto);
        } catch (CheckFailedException e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }

    //Da implementare una funzione che controlli i risultati dei set di un match ed assegni il giusto vincitore al match
    @PatchMapping("/{matchId}")
    public ResponseEntity<?> checkSetResultAndUpdateMatch(@AuthenticationPrincipal User user, @PathVariable Long matchId){
        try{
            matchsService.checkSetResultAndUpdateMatch(user, matchId);
        } catch (CheckFailedException e){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }
}
