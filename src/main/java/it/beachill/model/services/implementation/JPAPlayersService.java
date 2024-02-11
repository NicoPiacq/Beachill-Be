package it.beachill.model.services.implementation;

import it.beachill.model.entities.tournament.Player;
import it.beachill.model.entities.tournament.Team;
import it.beachill.model.entities.tournament.TeamComponent;
import it.beachill.model.exceptions.PlayerChecksFailedException;
import it.beachill.model.repositories.abstractions.PlayerRepository;
import it.beachill.model.repositories.abstractions.TeamRepository;
import it.beachill.model.services.abstraction.PlayersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JPAPlayersService implements PlayersService {
    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;

    @Autowired
    public JPAPlayersService(PlayerRepository playerRepository, TeamRepository teamRepository){
        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;
    }

    @Override
    public List<Player> findAllPlayers() {
        return playerRepository.findAll();
    }

    @Override
    public List<TeamComponent> findAllPlayerByTeamId(Long id) throws PlayerChecksFailedException {
        Optional<Team> optionalTeam = teamRepository.findById(id);
        if(optionalTeam.isEmpty()){
            throw new PlayerChecksFailedException("Il team non è stato trovato!");
        }
        return optionalTeam.get().getTeamComponents();
    }
}
