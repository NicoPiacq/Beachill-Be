package it.beachill.model.repositories.abstractions;

import it.beachill.model.entities.TeamComponent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamComponentRepository extends JpaRepository<TeamComponent, Long> {
}
