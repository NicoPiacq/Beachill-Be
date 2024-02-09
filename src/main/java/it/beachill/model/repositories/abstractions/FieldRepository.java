package it.beachill.model.repositories.abstractions;

import it.beachill.model.entities.reservation.Field;
import it.beachill.model.entities.reservation.ReservationPlace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FieldRepository extends JpaRepository<Field, Long> {

    List<Field> findByReservationPlace(ReservationPlace place);
}
