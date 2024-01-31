package it.beachill.model.repositories.abstractions;

import it.beachill.model.entities.Player;
import it.beachill.model.entities.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {
	List<Team> findAllByTeamLeader(Player player);
	
	Optional<Team> findByTeamName(String teamName);
	//List<Team> findByTournamentId(Long id);
}
