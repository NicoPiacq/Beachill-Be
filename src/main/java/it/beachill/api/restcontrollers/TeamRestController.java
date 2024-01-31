package it.beachill.api.restcontrollers;

import it.beachill.dtos.TeamComponentDto;
import it.beachill.dtos.TeamDto;
import it.beachill.model.entities.Player;
import it.beachill.model.entities.Team;
import it.beachill.model.entities.User;
import it.beachill.model.services.abstraction.TeamsService;
import it.beachill.model.services.implementation.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/team")
@CrossOrigin
public class TeamRestController {

    private final TeamsService teamsService;
    private final JwtService jwtService;

    @Autowired
    public TeamRestController(TeamsService teamsService, JwtService jwtService){
        this.teamsService = teamsService;
        this.jwtService = jwtService;
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
    @GetMapping("/all-teams-captained/{id}")
    public ResponseEntity<List<TeamDto>> getAllTeamsByTeamLeaderId(Long id){
        List<Team> teams = teamsService.findAllTeamsByTeamLeader(id);
        List<TeamDto> result = teams.stream().map(TeamDto::new).toList();
        return ResponseEntity.ok(result);
    }
    @GetMapping("/player-enrolled-teams/{id}")
    public ResponseEntity<List<TeamDto>> getAllTeamsByPlayerId(Long id){
        List<Team> teams= teamsService.findAllTeamsByPlayer(id);
        List<TeamDto> result = teams.stream().map(TeamDto::new).toList();
        return ResponseEntity.ok(result);
    }
    
    //TODO:implementare assegnazione sicura del capitano alla squadra
    //FIXME:questa cosa funziona male (IO)
    @PostMapping("/create")
    public ResponseEntity<TeamDto> createTeam(@RequestBody TeamDto teamDto) throws URISyntaxException {
        Team team = teamDto.fromDto();
        Team savedTeam=teamsService.createTeam(team);
        teamsService.addCaptainToTeam(savedTeam.getId(),savedTeam.getTeamLeader().getId());
        
        TeamDto result = new TeamDto(team);
        URI location = new URI("/api/team/" + result.getId());
        
        return ResponseEntity.created(location).body(result);
    }
    /*fede vuole roba sofisticata:
    - uno fa una richiesta, che deve venir accettata: od il capitano manda la richiesta al player o viceversa;
    - il team manda una richiesta di prenotazione al torneo, che l'organizzatore può accettare o rifiutare.
    
    */
    
    // verificare se l'user è corretto, altrimenti manda unauthorized
    //
    @PostMapping("/{teamId}/players/")
    public ResponseEntity<?> addPlayerToTeam(@AuthenticationPrincipal User user,@RequestBody TeamComponentDto teamComponentDto, @PathVariable Long teamId) {
        if(!Objects.equals(teamId, teamComponentDto.getTeamId())){
            return ResponseEntity.badRequest().build();
        }
        Player player = user.getPlayer();
        Optional<Team> teamOptional=teamsService.addPlayerToTeam(
                teamComponentDto.getTeamId(),
                teamComponentDto.getRecipientPlayerId(),
                user.getId());
        if(teamOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invio invito fallito");
        }
        return ResponseEntity.ok(teamOptional.get());
    }

    @DeleteMapping("/{teamId}/delete-player/{id}")
    public ResponseEntity<Object> deletePlayerFromTeam(@PathVariable Long teamId, @PathVariable Long playerId){
        Optional<Team> result = teamsService.deleteEnrolledPlayer(playerId,teamId);
        
        return result.stream()
                .map(c -> ResponseEntity.noContent().build())
                .findFirst()
                .orElse(ResponseEntity.notFound().build());
    }
}
