package it.beachill.model.repositories.abstractions;

import it.beachill.model.entities.reservation.ReservationPlace;
import it.beachill.model.entities.reservation.Sport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReservationPlaceRepository extends JpaRepository<ReservationPlace, Long> {
    Optional<ReservationPlace> findByNameAndSportAndFieldNumber(String name, Sport sport, int fieldNumber);
}
