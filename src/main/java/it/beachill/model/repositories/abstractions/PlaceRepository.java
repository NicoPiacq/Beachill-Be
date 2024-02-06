package it.beachill.model.repositories.abstractions;

import it.beachill.model.entities.reservation.Place;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRepository extends JpaRepository<Place, Long> {
}
