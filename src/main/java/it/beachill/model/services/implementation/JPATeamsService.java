package it.beachill.model.services.implementation;

import it.beachill.dtos.EnrolledTeamDto;
import it.beachill.model.entities.*;
import it.beachill.model.repositories.abstractions.PlayerRepository;
import it.beachill.model.repositories.abstractions.TeamComponentRepository;
import it.beachill.model.repositories.abstractions.TeamInTournamentRepository;
import it.beachill.model.repositories.abstractions.TeamRepository;
import it.beachill.model.services.abstraction.TeamsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
    @Override
    public List<Team> findAllTeamsByTeamLeader(Long playerId) {
        return teamRepository.findAllByTeamLeader(new Player(playerId));
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
    
    //durante la fase di creazione del team questa funzione aggiunge il capitano ai componenti del team (non ha controlli)
    @Override
    public Optional<Team> addCaptainToTeam(Long teamId, Long playerId) {
        Optional<Team> team=teamRepository.findById(teamId);
        Optional<Player> player=playerRepository.findById(playerId);
        if(team.isPresent() && player.isPresent()){
            TeamComponent teamComponent= new TeamComponent(team.get(),player.get());
            teamComponent.setStatus(1);
            teamComponentRepository.save(teamComponent);
            return team;
        }
        return Optional.empty();
    }
    
    @Override
    public Optional<Team> addPlayerToTeam(Long teamId, Long playerToAddId, Long requestingPlayerId) {
        Optional<Team> team=teamRepository.findById(teamId);
        if(team.isEmpty()){
            return Optional.empty();
        }
        //FIXME:Questa troia bastarda non vuole cooperare. MY EYES!!!
        if(!Objects.equals(team.get().getTeamLeader().getId(), requestingPlayerId)){
            return Optional.empty();
        }
        Optional<Player> player=playerRepository.findById(playerToAddId);
        if(player.isEmpty()){
            return Optional.empty();
        }
        
        TeamComponent teamComponent= new TeamComponent(team.get(),player.get());
        teamComponent.setStatus(2);
        teamComponentRepository.save(teamComponent);
        return team;
    }
    
    @Override
    public Optional<Team> deleteEnrolledPlayer(Long playerId, Long teamId) {
        
        Optional<Team> optionalTeam = teamRepository.findById(teamId);
        Optional<Player> optionalPlayer = playerRepository.findById(playerId);
        if (optionalTeam.isPresent() && optionalPlayer.isPresent()) {
            Optional<TeamComponent> optionalTeamComponent = teamComponentRepository.findByPlayerIdAndTeamId(new Player(playerId), new Team(teamId));
            if (optionalTeamComponent.isPresent()) {
                teamComponentRepository.delete(optionalTeamComponent.get());
                return optionalTeam;
            }
        }
        return Optional.empty();
    }
    
    @Override
    public List<Team> findAllTeamsByPlayer(Long id) {
        List<TeamComponent> teams = teamComponentRepository.findByPlayerId(id);
        List<Team> result=new ArrayList<>();
        for(int i=0; i<teams.size();i++){
            result.add(teams.get(i).getTeam());
        }
        return result;
    }
}
