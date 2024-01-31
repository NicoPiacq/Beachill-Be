package it.beachill.model.services.implementation;

import it.beachill.model.entities.Player;
import it.beachill.model.entities.Team;
import it.beachill.model.entities.TeamComponent;
import it.beachill.model.entities.TeamInTournament;
import it.beachill.model.repositories.abstractions.PlayerRepository;
import it.beachill.model.repositories.abstractions.TeamComponentRepository;
import it.beachill.model.repositories.abstractions.TeamInTournamentRepository;
import it.beachill.model.repositories.abstractions.TeamRepository;
import it.beachill.model.services.abstraction.TeamsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JPATeamsService implements TeamsService {
    private final TeamRepository teamRepository;
    private final TeamInTournamentRepository teamInTournamentRepository;
    private final PlayerRepository playerRepository;
    private final TeamComponentRepository teamComponentRepository;
    @Autowired
    public JPATeamsService(TeamRepository teamRepository, TeamInTournamentRepository teamInTournamentRepository, PlayerRepository playerRepository, TeamComponentRepository teamComponentRepository){
        this.teamRepository = teamRepository;
        this.teamInTournamentRepository = teamInTournamentRepository;
        this.playerRepository = playerRepository;
        this.teamComponentRepository = teamComponentRepository;
    }

    public List<TeamInTournament> getAllEnrolledTeamsByTournament(Long id){
        return teamInTournamentRepository.findByTournamentId(id);
    }
    
    @Override
    public List<Team> findAllTeams() {
        return teamRepository.findAll();
    }
    public List<Team> findAllTeamsByPlayerId(Long playerId) {
        return teamRepository.findAllByPlayerId(playerId);
    }
    
    @Override
    public Team createTeam(Team team) {
        Optional<Team> existingTeam = teamRepository.findByTeamName(team.getTeamName());
        
        if (existingTeam.isPresent()) {
            // Il team esiste già, quindi non può essere creato di nuovo
            throw new IllegalArgumentException("Il team esiste già!");
        } else {
            // Il team non esiste, quindi può essere creato
            return teamRepository.save(team);
        }
    }
    
    @Override
    public Optional<Team> addPlayerToTeam(Long teamId, Long playerId) {
        Optional<Team> team=teamRepository.findById(teamId);
        Optional<Player> player=playerRepository.findById(playerId);
        if(team.isPresent() && player.isPresent()){
            TeamComponent teamComponent= new TeamComponent(team.get(),player.get());
            teamComponentRepository.save(teamComponent);
            return team;
        }
        return Optional.empty();
    }
}
