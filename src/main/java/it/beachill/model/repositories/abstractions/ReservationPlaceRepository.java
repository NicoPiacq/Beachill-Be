package it.beachill.model.repositories.abstractions;

import it.beachill.model.entities.reservation.ReservationPlace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReservationPlaceRepository extends JpaRepository<ReservationPlace, Long> {

    Optional<ReservationPlace> findByNameAndAddressAndCity(String name, String address, String city);

    List<ReservationPlace> findByNameContainingIgnoreCase(String toFind);
}
