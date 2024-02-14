package it.beachill.model.services.abstraction;

import it.beachill.model.entities.tournament.Player;
import it.beachill.model.entities.tournament.TeamComponent;
import it.beachill.model.exceptions.PlayerChecksFailedException;
import it.beachill.model.entities.user.User;

import java.util.List;

public interface PlayersService {
    List<Player> findAllPlayers();

    List<TeamComponent> findAllPlayerByTeamId(Long id) throws PlayerChecksFailedException;
	
	List<User> searchPlayerByString(String toFind);
}
