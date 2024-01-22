package it.beachill.model.repositories.abstractions;

import it.beachill.model.entities.TournamentPlace;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TournamentPlaceRepository extends JpaRepository<TournamentPlace, Long> {
}
