package it.beachill.model.repositories.abstractions;

import it.beachill.model.entities.reservation.Field;
import it.beachill.model.entities.reservation.Reservation;
import it.beachill.model.entities.reservation.ReservationPlace;
import it.beachill.model.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository <Reservation, Long> {
    List<Reservation> findByDateEquals(LocalDate date);

    Optional<Reservation> findByFieldAndDateAndStartAndEnd(Field field, LocalDate date, LocalTime start, LocalTime end);

    List<Reservation> findByFieldAndDate(Field field, LocalDate date);

    List<Reservation> findByUser(User user);
}
