package it.beachill.model.repositories.abstractions;

import it.beachill.model.entities.tournament.ScoreType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScoreTypeRepository extends JpaRepository <ScoreType, String> {
}
