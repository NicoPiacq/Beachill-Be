package it.beachill.api.restcontrollers;

import it.beachill.dtos.TeamDto;
import it.beachill.model.entities.Team;
import it.beachill.model.repositories.abstractions.TeamRepository;
import it.beachill.model.services.abstraction.TeamsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/Team")
@CrossOrigin
public class TeamRestController {

    private final TeamsService teamsService;

    @Autowired
    public TeamRestController(TeamsService teamsService){
        this.teamsService = teamsService;
    }
// sbagliataaa
//    @GetMapping()
//    public ResponseEntity<List<TeamDto>> getAllEnrolledTeamsByTournamentId(@PathVariable Long tournamentId){
//        List<Team> teams = teamRepository.findByTournamentId(tournamentId);
//        List<TeamDto> result = teams.stream().map(TeamDto::new).toList();
//        return ResponseEntity.ok(result);
//    }
}
