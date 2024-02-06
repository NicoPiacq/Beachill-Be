package it.beachill.model.repositories.abstractions;

import it.beachill.model.entities.tournament.TournamentType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TournamentTypeRepository extends JpaRepository<TournamentType, String> {
}
