package it.beachill.model.services.implementation;

import it.beachill.dtos.EnrolledTeamDto;
import it.beachill.model.entities.*;
import it.beachill.model.exceptions.TeamCheckFailedException;
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
    public List<Team> findAllTeamsByPlayer(Long playerId) {
        List<TeamComponent> teams = teamComponentRepository.findByPlayerId(playerId);
        List<Team> result=new ArrayList<>();
        for(int i=0; i<teams.size();i++){
            result.add(teams.get(i).getTeam());
        }
        return result;
    }
    
    @Override
    public Team createTeam(Team team) throws TeamCheckFailedException {
        Optional<Team> existingTeam = teamRepository.findByTeamName(team.getTeamName());
        
        if (existingTeam.isPresent()) {
            // Il team esiste già, quindi non può essere creato di nuovo
            throw new TeamCheckFailedException("Il team esiste già!");
        }
            // Il team non esiste, l' utente è il capitano, quindi può essere creato
        return teamRepository.save(team);
    }

    @Override
    public Optional<Team> deleteTeam(Long teamId, User user) throws TeamCheckFailedException {
        Optional<Team> optionalTeam = teamRepository.findById(teamId);
        if(optionalTeam.isEmpty()){
            throw new TeamCheckFailedException("Il team non è presente");
        }
        if(!Objects.equals(optionalTeam.get().getTeamLeader().getId(), user.getPlayer().getId())){
            throw new TeamCheckFailedException("Non sei il capitano del team!");
        }
        teamRepository.delete(optionalTeam.get());
        return optionalTeam;
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
    public Optional<Team> addPlayerToTeam(Long teamId, Long playerToAddId, Long requestingPlayerId) throws TeamCheckFailedException {
        Optional<Team> team=teamRepository.findById(teamId);
        if(team.isEmpty()){
            throw new TeamCheckFailedException("Il team non è presente!");
        }
        if(!Objects.equals(team.get().getTeamLeader().getId(), requestingPlayerId)){
            throw new TeamCheckFailedException("Non sei il capitano del team!");
        }
        Optional<Player> player=playerRepository.findById(playerToAddId);
        if(player.isEmpty()){
            throw new TeamCheckFailedException("Il player da invitare non è presente!");
        }
        
        TeamComponent teamComponent= new TeamComponent(team.get(),player.get());
        teamComponent.setStatus(2);
        teamComponentRepository.save(teamComponent);
        return team;
    }
    
    @Override
    public Optional<Team> deleteEnrolledPlayer(Long teamComponentId, Long teamId, User requestingUser) throws TeamCheckFailedException {

        Optional<Team> optionalTeam = teamRepository.findById(teamId);
        Optional<TeamComponent> optionalTeamComponent = teamComponentRepository.findById(teamComponentId);
        if (optionalTeam.isPresent() && optionalTeamComponent.isPresent()) {
            if(!Objects.equals(optionalTeam.get().getTeamLeader().getId(), requestingUser.getPlayer().getId())){
                throw new TeamCheckFailedException("Non sei il capitano del team!");
            }
            teamComponentRepository.delete(optionalTeamComponent.get());
            return optionalTeam;

        }
        throw new TeamCheckFailedException("Il team o il player non sono presenti!");
    }

    @Override
    public Optional<Team> updateStatusInvitation(Long teamComponentId, Long teamId, User user, Integer status) throws TeamCheckFailedException {

        Optional<Team> optionalTeam = teamRepository.findById(teamId);
        Optional<TeamComponent> optionalTeamComponent = teamComponentRepository.findById(teamComponentId);
        if (optionalTeam.isPresent() && optionalTeamComponent.isPresent()){
            TeamComponent teamComponent = optionalTeamComponent.get();
            if(Objects.equals(user.getPlayer().getId(), teamComponent.getPlayer().getId())){
                teamComponent.setStatus(status);
                teamComponentRepository.save(teamComponent);
            }
            throw new TeamCheckFailedException("Questo invito non ti appartiene!");
        }

        throw new TeamCheckFailedException("Il team o l' invito non sono presenti!");
    }

}
