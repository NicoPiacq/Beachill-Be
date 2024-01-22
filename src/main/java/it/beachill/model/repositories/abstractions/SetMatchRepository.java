package it.beachill.model.repositories.abstractions;

import it.beachill.model.entities.SetMatch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SetMatchRepository extends JpaRepository<SetMatch, Long> {
}
