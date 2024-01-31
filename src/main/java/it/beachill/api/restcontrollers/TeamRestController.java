package it.beachill.api.restcontrollers;

import it.beachill.dtos.TeamDto;
import it.beachill.dtos.TournamentDto;
import it.beachill.model.entities.Player;
import it.beachill.model.entities.Team;
import it.beachill.model.entities.Tournament;
import it.beachill.model.repositories.abstractions.TeamRepository;
import it.beachill.model.services.abstraction.TeamsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api/team")
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
    
    @GetMapping("/all")
    public ResponseEntity<List<TeamDto>> getAllTeams(){
        List<Team> teams = teamsService.findAllTeams();
        List<TeamDto> result = teams.stream().map(TeamDto::new).toList();
        return ResponseEntity.ok(result);
    }
    @GetMapping("/all/{id}")
    public ResponseEntity<List<TeamDto>> getAllTeamsByPlayerId(Long id){
        List<Team> teams = teamsService.findAllTeamsByPlayerId(id);
        List<TeamDto> result = teams.stream().map(TeamDto::new).toList();
        return ResponseEntity.ok(result);
    }
    
    @PostMapping("/create")
    public ResponseEntity<TeamDto> createTeam(@RequestBody TeamDto teamDto) throws URISyntaxException {
        Team team = teamDto.fromDto();
        teamsService.createTeam(team);
        TeamDto result = new TeamDto(team);
        URI location = new URI("/api/team/" + result.getId());
        
        return ResponseEntity.created(location).body(result);
    }
    @PostMapping("/{teamId}/add-player/{id}")
    public ResponseEntity<TeamDto> addPlayerToTeam(@PathVariable Long teamId,@PathVariable Long playerId, @RequestBody Player player) {
        try {
            teamsService.addPlayerToTeam(teamId, player);
            return ResponseEntity.ok("Giocatore aggiunto al team con successo!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errore durante l'aggiunta al team");
        }
    }
}
