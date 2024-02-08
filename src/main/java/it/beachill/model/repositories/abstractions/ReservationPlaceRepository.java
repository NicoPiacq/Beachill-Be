package it.beachill.model.repositories.abstractions;

import it.beachill.model.entities.reservation.ReservationPlace;
import it.beachill.model.entities.reservation.Sport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ReservationPlaceRepository extends JpaRepository<ReservationPlace, Long> {
    Optional<ReservationPlace> findByNameAndSportAndFieldNumber(String name, Sport sport, int fieldNumber);

    @Query("SELECT p, count(p.fieldNumber) FROM ReservationPlace p GROUP BY p.id, p.name")
    List<ReservationPlace> findAllReservationPlacesGroupByName();
}
