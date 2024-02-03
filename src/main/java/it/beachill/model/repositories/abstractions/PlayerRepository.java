package it.beachill.model.repositories.abstractions;

import it.beachill.model.entities.Player;
import it.beachill.model.entities.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayerRepository extends JpaRepository<Player, Long> {
}
