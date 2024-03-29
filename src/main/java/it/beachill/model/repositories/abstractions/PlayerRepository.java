package it.beachill.model.repositories.abstractions;

import it.beachill.model.entities.tournament.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Long> {
}
