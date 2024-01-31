package it.beachill.model.services.implementation;

import it.beachill.model.entities.Team;
import it.beachill.model.entities.TeamInTournament;
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

    @Autowired
    public JPATeamsService(TeamRepository teamRepository, TeamInTournamentRepository teamInTournamentRepository){
        this.teamRepository = teamRepository;
        this.teamInTournamentRepository = teamInTournamentRepository;
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
}
