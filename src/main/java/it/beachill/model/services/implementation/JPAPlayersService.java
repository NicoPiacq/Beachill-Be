package it.beachill.model.services.implementation;

import it.beachill.model.entities.tournament.Player;
import it.beachill.model.entities.tournament.Team;
import it.beachill.model.entities.tournament.TeamComponent;
import it.beachill.model.entities.user.User;
import it.beachill.model.exceptions.PlayerChecksFailedException;
import it.beachill.model.repositories.abstractions.PlayerRepository;
import it.beachill.model.repositories.abstractions.TeamRepository;
import it.beachill.model.repositories.abstractions.UserRepository;
import it.beachill.model.services.abstraction.PlayersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class JPAPlayersService implements PlayersService {
    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;

    @Autowired
    public JPAPlayersService(PlayerRepository playerRepository, TeamRepository teamRepository, UserRepository userRepository){
        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;
        this.userRepository = userRepository;
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
    
    @Override
    public List<User> searchPlayerByString(String toFind) {
		Set<User> playersFound = new HashSet<>(userRepository.findByEmailContaining(toFind));
        playersFound.addAll(userRepository.findByNameContainingIgnoreCase(toFind));
        playersFound.addAll(userRepository.findBySurnameContainingIgnoreCase(toFind));
        return playersFound.stream().toList();
    }
}
