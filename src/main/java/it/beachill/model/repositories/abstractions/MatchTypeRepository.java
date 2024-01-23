package it.beachill.model.repositories.abstractions;

import it.beachill.model.entities.MatchType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchTypeRepository extends JpaRepository<MatchType, String> {
}
