package it.beachill.model.repositories.abstractions;

import it.beachill.model.entities.Player;
import it.beachill.model.entities.Team;
import it.beachill.model.entities.TeamComponent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TeamComponentRepository extends JpaRepository<TeamComponent, Long> {
	Optional<TeamComponent> findByPlayerIdAndTeamId(Player player, Team team);
	
	List<TeamComponent> findByPlayerId(Long id);
}
