package it.beachill.model.repositories.abstractions;

import it.beachill.model.entities.reservation.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository <Reservation, Long> {
    List<Reservation> findByDateEquals(LocalDate date);
}
