package it.beachill.api.restcontrollers;

import it.beachill.dtos.TeamComponentDto;
import it.beachill.dtos.TeamDto;
import it.beachill.model.entities.tournament.Player;
import it.beachill.model.entities.tournament.Team;
import it.beachill.model.entities.tournament.TeamComponent;
import it.beachill.model.entities.user.User;
import it.beachill.model.exceptions.TeamCheckFailedException;
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
    
    @GetMapping("/all")
    public ResponseEntity<List<TeamDto>> getAllTeams(){
        List<Team> teams = teamsService.findAllTeams();
        List<TeamDto> result = teams.stream().map(TeamDto::new).toList();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamDto> getTeamDetails(@PathVariable Long id){
        Optional<Team> team = teamsService.findTeamById(id);
        if(team.isPresent()){
            TeamDto teamDto = new TeamDto(team.get());
            return ResponseEntity.ok(teamDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/captained/{id}")
    public ResponseEntity<List<TeamDto>> getAllTeamsByTeamLeaderId(@PathVariable Long id){
        List<Team> teams = teamsService.findAllTeamsByTeamLeader(id);
        List<TeamDto> result = teams.stream().map(TeamDto::new).toList();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/my-captained/{id}")
    public ResponseEntity<List<TeamDto>> manageAllTeamsByTeamLeaderId(@AuthenticationPrincipal User user, @PathVariable Long id){
        Player requestPlayer = user.getPlayer();
        if(!Objects.equals(id, requestPlayer.getId())){
            return ResponseEntity.badRequest().build();
        }
        List<Team> teams = teamsService.findAllTeamsByTeamLeader(id);
        List<TeamDto> result = teams.stream().map(TeamDto::new).toList();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/enrolled/{id}")
    public ResponseEntity<List<TeamDto>> getAllTeamsByPlayerId(@PathVariable Long id){
        List<Team> teams= teamsService.findAllTeamsByPlayer(id);
        List<TeamDto> result = teams.stream().map(TeamDto::new).toList();
        return ResponseEntity.ok(result);
    }
    @GetMapping("/my-enrolled/{id}")
    public ResponseEntity<List<TeamDto>> manageAllTeamsByPlayerId(@AuthenticationPrincipal User user, @PathVariable Long id){
        Player requestPlayer = user.getPlayer();
        if(!Objects.equals(id, requestPlayer.getId())){
            return ResponseEntity.badRequest().build();
        }
        List<Team> teams= teamsService.findAllTeamsByPlayer(id);
        List<TeamDto> result = teams.stream().map(TeamDto::new).toList();
        return ResponseEntity.ok(result);
    }

    //TODO:implementare assegnazione sicura del capitano alla squadra
    //FIXME:questa cosa funziona male (IO)
    @PostMapping("/")
    public ResponseEntity<?> createTeam(@AuthenticationPrincipal User user, @RequestBody TeamDto teamDto) throws URISyntaxException {
        Team team = teamDto.fromDto();
        Player requestPlayer = user.getPlayer();
        team.setTeamLeader(requestPlayer);
        if(!Objects.equals(team.getTeamLeader().getId(), requestPlayer.getId())){
            return ResponseEntity.badRequest().build();
        }
        Team savedTeam;
        try {
            savedTeam = teamsService.createTeam(team);
        } catch (TeamCheckFailedException e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
        
        TeamDto result = new TeamDto(team);
        URI location = new URI("/api/team/" + result.getId());
        
        return ResponseEntity.created(location).body(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTeam(@AuthenticationPrincipal User user, @PathVariable Long id) {
        Optional<Team> result;
        try {
           result = teamsService.deleteTeam(id, user);
        } catch (TeamCheckFailedException e){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
        return result.stream()
                .map(c -> ResponseEntity.noContent().build())
                .findFirst()
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/invite")
    public ResponseEntity<?> getAllInvite(@AuthenticationPrincipal User user){
        List<TeamComponent> teamComponentList = teamsService.getAllInvite(user);
        List<TeamComponentDto> result = teamComponentList.stream().map(TeamComponentDto::new).toList();
        return ResponseEntity.ok(result);
    }

    @PostMapping("/{id}/players")
    public ResponseEntity<?> invitePlayerToTeam(@AuthenticationPrincipal User user,@RequestBody TeamComponentDto teamComponentDto, @PathVariable Long id) {
        if(!Objects.equals(id, teamComponentDto.getTeamDto().getId())){
            return ResponseEntity.badRequest().build();
        }
        Player player = user.getPlayer();
        Optional<Team> teamOptional;
        try {
            teamOptional = teamsService.addPlayerToTeam(
                    teamComponentDto.getTeamDto().getId(),
                    teamComponentDto.getPlayerId(),
                    user.getPlayer().getId());
        } catch(TeamCheckFailedException e){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }

        //return ResponseEntity.ok(new TeamDto(teamOptional.get()));
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{teamId}/player/{teamComponentId}")
    public ResponseEntity<?> updateStatusInvitation(@AuthenticationPrincipal User user, @PathVariable Long teamId, @PathVariable Long teamComponentId, @RequestParam Integer status){
        try {
            teamsService.updateStatusInvitation(teamComponentId, teamId, user, status);
        } catch (TeamCheckFailedException e){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{teamId}/player/{teamComponentId}")
    public ResponseEntity<Object> deletePlayerFromTeam(@AuthenticationPrincipal User user, @PathVariable Long teamId, @PathVariable Long teamComponentId){
        Optional<Team> result;
        try {
            result = teamsService.deleteEnrolledPlayer(teamComponentId, teamId, user);
        } catch (TeamCheckFailedException e){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
        return result.stream()
                .map(c -> ResponseEntity.noContent().build())
                .findFirst()
                .orElse(ResponseEntity.notFound().build());
    }
}
