package it.beachill.model.services.abstraction;

import it.beachill.model.entities.Player;
import it.beachill.model.entities.TeamComponent;
import it.beachill.model.exceptions.PlayerChecksFailedException;

import java.util.List;

public interface PlayersService {
    List<Player> findAllPlayers();

    List<TeamComponent> findAllPlayerByTeamId(Long id) throws PlayerChecksFailedException;
}
