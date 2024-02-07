package it.beachill.model.repositories.abstractions;

import it.beachill.model.entities.reservation.ScheduleProp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SchedulePropRepository extends JpaRepository<ScheduleProp, Long> {
    List<ScheduleProp> findByPlaceEquals(Long placeId);
}
