package it.beachill.model.services.implementation;

import it.beachill.model.entities.Team;
import it.beachill.model.entities.TeamInTournament;
import it.beachill.model.repositories.abstractions.TeamInTournamentRepository;
import it.beachill.model.repositories.abstractions.TeamRepository;
import it.beachill.model.services.abstraction.TeamsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
