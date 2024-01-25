package it.beachill.api.restcontrollers;

import it.beachill.dtos.EnrolledTeamDto;
import it.beachill.model.entities.TeamInTournament;
import it.beachill.model.repositories.abstractions.TeamInTournamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enrolled-team")
@CrossOrigin
public class TeamInTournamentRestController {
    private final TeamInTournamentRepository teamInTournamentRepository;

    @Autowired
    public TeamInTournamentRestController(TeamInTournamentRepository teamInTournamentRepository){
        this.teamInTournamentRepository = teamInTournamentRepository;
    }

    @GetMapping("/tournament/{tournamentId}")
    public ResponseEntity<List<EnrolledTeamDto>> getAllEnrolledTeamsByTournamentId(@PathVariable Long tournamentId){
        List<TeamInTournament> teams = teamInTournamentRepository.findByTournamentId(tournamentId);
        List<EnrolledTeamDto> result = teams.stream().map(EnrolledTeamDto::new).toList();
        return ResponseEntity.ok(result);
    }
}