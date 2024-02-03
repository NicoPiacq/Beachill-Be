package it.beachill.model.services.abstraction;

import it.beachill.model.entities.Player;
import it.beachill.model.exceptions.PlayerChecksFailedException;

import java.util.List;

public interface PlayersService {
    List<Player> findAllPlayers();

    List<Player> findAllPlayerByTeamId(Long id) throws PlayerChecksFailedException;
}
