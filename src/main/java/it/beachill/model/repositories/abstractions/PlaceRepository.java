package it.beachill.model.repositories.abstractions;

import it.beachill.model.entities.reservation.ReservationPlace;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRepository extends JpaRepository<ReservationPlace, Long> {
}
