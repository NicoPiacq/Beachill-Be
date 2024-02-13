package it.beachill.model.services.implementation;

import it.beachill.dtos.UserDto;
import it.beachill.model.entities.tournament.*;
import it.beachill.model.entities.user.User;
import it.beachill.model.exceptions.CheckFailedException;
import it.beachill.model.exceptions.TeamCheckFailedException;
import it.beachill.model.exceptions.TournamentCheckFailedException;
import it.beachill.model.repositories.abstractions.*;
import it.beachill.model.services.abstraction.AdminsService;
import it.beachill.model.services.abstraction.SuperAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import it.beachill.model.entities.user.Role;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
public class JPASuperAdminService implements SuperAdminService {
	private final UserRepository userRepository;
	private final TournamentRepository tournamentRepository;
	private final TeamInTournamentRepository teamInTournamentRepository;
	private final AdminsService adminsService;
	private final MatchRepository matchRepository;
	private final GroupStageStandingRepository groupStageStandingRepository;
	private final TeamRepository teamRepository;
	private final TeamComponentRepository teamComponentRepository;
	@Autowired
	public JPASuperAdminService(UserRepository userRepository, TournamentRepository tournamentRepository, TeamInTournamentRepository teamInTournamentRepository, AdminsService adminsService, MatchRepository matchRepository, GroupStageStandingRepository groupStageStandingRepository, TeamRepository teamRepository, TeamComponentRepository teamComponentRepository) {
		this.userRepository = userRepository;
		this.tournamentRepository = tournamentRepository;
		this.teamInTournamentRepository = teamInTournamentRepository;
		this.adminsService = adminsService;
		this.matchRepository = matchRepository;
		this.groupStageStandingRepository = groupStageStandingRepository;
		this.teamRepository = teamRepository;
		this.teamComponentRepository = teamComponentRepository;
	}
	
	@Override
	public List<User> getAllUser() {
		return userRepository.findAll();
	}
	
	@Override
	public void setUserRole(UserDto userDto) throws CheckFailedException {
		Optional<User> userOptional=userRepository.findById(userDto.getId());
		if(userOptional.isEmpty()){
			throw new CheckFailedException("L'utente non esiste");
		}
		User user= userOptional.get();
		switch (userDto.getRole()){
			case "SUPERADMIN":
				user.setRole(Role.SUPERADMIN);
				break;
			case "ADMIN":
				user.setRole(Role.ADMIN);
				break;
			case "MANAGER":
				user.setRole(Role.MANAGER);
				break;
			case "USER":
				user.setRole(Role.USER);
				break;
			default:
				break;
		}
		userRepository.save(user);
	}
	
	@Override
	public void deleteUser(Long userId) throws CheckFailedException {
		Optional<User> userOptional=userRepository.findById(userId);
		if(userOptional.isEmpty()){
			throw new CheckFailedException("L'utente non esiste");
		}
		userRepository.deleteById(userId);
	}
	@Override
	public void deleteTournament(Long id) throws TournamentCheckFailedException {
		Optional<Tournament> tournamentOptional = tournamentRepository.findById(id);
		if(tournamentOptional.isEmpty()){
			throw new TournamentCheckFailedException("Il torneo non esiste");
		}
		tournamentRepository.delete(tournamentOptional.get());
	}
	
	@Override
	public void generateMatchTournament(Long id) throws TournamentCheckFailedException {
		Optional<Tournament> tournamentOptional = tournamentRepository.findById(id);
		if(tournamentOptional.isEmpty()){
			throw new TournamentCheckFailedException("Il torneo non esiste");
		}
		List<TeamInTournament> enrolledTeams = teamInTournamentRepository.findByTournamentId(id);
		if(enrolledTeams.isEmpty()){
			throw new TournamentCheckFailedException("Non ci sono team iscritti al torneo");
		}
		
		Tournament tournament = tournamentOptional.get();
		switch (tournament.getTournamentType().getTournamentTypeName()) {
			case "10-corto":
				adminsService.generateMatchTournament10Short(tournament);
			case "10-lungo":
				adminsService.generateMatchTournament10Long(tournament);
			default:
				throw new TournamentCheckFailedException("Tipo di torneo non valido");
		}
	}
	@Override
	public void calculateGroupStageStandingAndAssignMatches(Long id) throws TournamentCheckFailedException {
		Optional<Tournament> tournamentOptional = tournamentRepository.findById(id);
		if(tournamentOptional.isEmpty()){
			throw new TournamentCheckFailedException("Il torneo non esiste");
		}
		List<Match> matches = matchRepository.findByTournamentIdAndMatchTypeNot(id, new MatchType("GIRONE"));
		Tournament tournament = tournamentOptional.get();
		if(!adminsService.calculateGroupStageStanding(id)){
			throw new TournamentCheckFailedException("Errore nella creazione della classifica");
		}
		int numberOfGroupStage;
		Optional<GroupStageStanding> groupStageStandingOptional = groupStageStandingRepository.findFirstByTournamentIdOrderByGroupStageDesc(id);
		if(groupStageStandingOptional.isPresent()){
			numberOfGroupStage =  groupStageStandingOptional.get().getGroupStage();
		} else {
			throw new TournamentCheckFailedException("Non esiste la tabella della classifica");
		}
		List<GroupStageStanding> groupStageStandingList = new ArrayList<>();
		for(int i = 0; i < numberOfGroupStage; i++) {
			int groupStageId=i+1;
			groupStageStandingList.addAll(groupStageStandingRepository.findByTournamentIdAndGroupStageEqualsOrderByStandingAsc(id, groupStageId));
		}
		int[][] assignSchema= adminsService.assignSchemaFromTournament(tournament);
		
		List<Match> assignedMatches=adminsService.assignTeamsToSecondPhaseMatches(groupStageStandingList,assignSchema,matches);
		matchRepository.saveAll(assignedMatches);
	}
	
	@Override
	public Optional<Team> deleteTeam(Long teamId) throws TeamCheckFailedException {
		Optional<Team> optionalTeam = teamRepository.findById(teamId);
		if(optionalTeam.isEmpty()){
			throw new TeamCheckFailedException("Il team non è presente");
		}
		teamRepository.delete(optionalTeam.get());
		return optionalTeam;
	}
	
	@Override
	public Optional<Team> deleteEnrolledPlayer(Long teamComponentId, Long teamId) throws TeamCheckFailedException {
		
		Optional<Team> optionalTeam = teamRepository.findById(teamId);
		Optional<TeamComponent> optionalTeamComponent = teamComponentRepository.findById(teamComponentId);
		if (optionalTeam.isPresent() && optionalTeamComponent.isPresent()) {
			teamComponentRepository.delete(optionalTeamComponent.get());
			return optionalTeam;
			
		}
		throw new TeamCheckFailedException("Il team o il player non sono presenti!");
	}

	@Override
	public User getUserDetails(Long id) throws CheckFailedException {
		Optional<User> userOptional = userRepository.findById(id);
		if(userOptional.isEmpty()){
			throw new CheckFailedException("L' utente non esiste");
		}
		return userOptional.get();
	}
}
