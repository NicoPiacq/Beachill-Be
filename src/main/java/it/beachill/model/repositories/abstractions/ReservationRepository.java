package it.beachill.model.repositories.abstractions;

import it.beachill.model.entities.reservation.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository <Reservation, Long> {
}
