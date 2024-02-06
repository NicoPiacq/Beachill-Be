package it.beachill.model.repositories.abstractions;

import it.beachill.model.entities.reservation.ScheduleProp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchedulePropRepository extends JpaRepository<ScheduleProp, Long> {
}
