package it.beachill.api.restcontrollers;

import it.beachill.dtos.MatchDto;
import it.beachill.model.entities.Match;
import it.beachill.model.repositories.abstractions.MatchRepository;
import it.beachill.model.services.abstraction.MatchsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/match")
@CrossOrigin
public class MatchRestController {

    private final MatchsService matchsService;
    @Autowired
    public  MatchRestController(MatchsService matchsService){
        this.matchsService = matchsService;
    }

    @GetMapping("/all/tournament/{TournamentId}")
    public ResponseEntity<List<MatchDto>> getAllMatchesByTournament(@PathVariable Long tournamentId){
        List<Match> matches = matchsService.getAllMatchesByTournament(tournamentId);
        List<MatchDto> result = matches.stream().map(MatchDto::new).toList();
        return ResponseEntity.ok(result);
    }
}
