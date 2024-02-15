package it.beachill.api.restcontrollers;

import it.beachill.dtos.EnrolledTeamDto;
import it.beachill.model.entities.tournament.TeamInTournament;
import it.beachill.model.entities.user.User;
import it.beachill.model.repositories.abstractions.TeamInTournamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public ResponseEntity<?> getAllEnrolledTeamsByTournamentId(@PathVariable Long tournamentId){
        List<TeamInTournament> teams = teamInTournamentRepository.findByTournamentId(tournamentId);
        List<EnrolledTeamDto> result = teams.stream().map(EnrolledTeamDto::new).toList();
        return ResponseEntity.ok(result);
    }

}
