package it.beachill.model.repositories.abstractions;

import it.beachill.model.entities.reservation.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository <Reservation, Long> {
    List<Reservation> findByDateEquals(LocalDate date);

    List<Reservation> findByDateEqualsAndStartBetween(LocalDate date, LocalTime start, LocalTime end);

    List<Reservation> findByDateEqualsAndEndBetween(LocalDate date, LocalTime start, LocalTime end);
}
