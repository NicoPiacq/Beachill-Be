package it.beachill.api.restcontrollers;

import it.beachill.dtos.TeamDto;
import it.beachill.dtos.TournamentAdminDto;
import it.beachill.dtos.UserDto;
import it.beachill.model.entities.tournament.Team;
import it.beachill.model.entities.tournament.Tournament;
import it.beachill.model.entities.user.User;
import it.beachill.model.exceptions.CheckFailedException;
import it.beachill.model.exceptions.TeamCheckFailedException;
import it.beachill.model.exceptions.TournamentCheckFailedException;
import it.beachill.model.services.abstraction.SuperAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/super-admin")
@CrossOrigin
public class SuperAdminRestController {
	
	private final SuperAdminService superAdminService;
	
	@Autowired
	public SuperAdminRestController(SuperAdminService superAdminService) {
		this.superAdminService = superAdminService;
	}
	//------------------------TOURNAMENT---------------------------
	@DeleteMapping("/tournament/{id}")
	public ResponseEntity<?> deleteTournament(@AuthenticationPrincipal User user, @PathVariable Long id){
		try {
			superAdminService.deleteTournament(id);
		} catch (TournamentCheckFailedException e) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
		}
		return ResponseEntity.ok().build();
	}
	
	@PostMapping("/tournament/match/{id}")
	public ResponseEntity<?> generateMatchTournament(@PathVariable Long id){
		try {
			superAdminService.generateMatchTournament(id);
		} catch (TournamentCheckFailedException e) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
		}
		return ResponseEntity.ok().build();
	}
	
	@PostMapping("/tournament/group-phase-standing/{id}")
	public ResponseEntity<Object> calculateGroupStageStanding(@PathVariable Long id){
		try {
			superAdminService.calculateGroupStageStandingAndAssignMatches(id);
		} catch (TournamentCheckFailedException e) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
		}
		return ResponseEntity.ok().build();
	}
	
	//------------------------USER---------------------------
	@GetMapping("/users")
	public ResponseEntity<?> getAllUser(){
		List<User> users= superAdminService.getAllUser();
		List<UserDto> result= users.stream().map(UserDto::new).toList();
		return ResponseEntity.ok(result);
	}

	@GetMapping("/user/{id}")
	public ResponseEntity<?> getUserDetails(@PathVariable Long id){
		User user;
		try {
			user = superAdminService.getUserDetails(id);
		} catch (CheckFailedException e) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
		}
		return ResponseEntity.ok(new UserDto(user));
	}
	
	@PostMapping("/user/role")
	public ResponseEntity<?> setUserRole(@RequestBody UserDto userDto){
		try{
			superAdminService.setUserRole(userDto);
		} catch (CheckFailedException e){
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
		}
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping("/user/{userId}")
	public ResponseEntity<?> deleteUser(@PathVariable Long userId){
		try{
			superAdminService.deleteUser(userId);
		} catch(CheckFailedException e){
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
		}
		return ResponseEntity.ok().build();
	}
	
	//------------------------TEAM---------------------------
	
	@DeleteMapping("/{teamId}")
	public ResponseEntity<?> deleteTeam(@PathVariable Long teamId) {
		Optional<Team> result;
		try {
			result = superAdminService.deleteTeam(teamId);
		} catch (TeamCheckFailedException e){
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
		}
		return result.stream()
				.map(c -> ResponseEntity.noContent().build())
				.findFirst()
				.orElse(ResponseEntity.notFound().build());
	}
	
	@DeleteMapping("/{teamId}/player/{teamComponentId}")
	public ResponseEntity<Object> deletePlayerFromTeam( @PathVariable Long teamId, @PathVariable Long teamComponentId){
		Optional<Team> result;
		try {
			result = superAdminService.deleteEnrolledPlayer(teamComponentId, teamId);
		} catch (TeamCheckFailedException e){
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
		}
		return result.stream()
				.map(c -> ResponseEntity.noContent().build())
				.findFirst()
				.orElse(ResponseEntity.notFound().build());
	}
}
