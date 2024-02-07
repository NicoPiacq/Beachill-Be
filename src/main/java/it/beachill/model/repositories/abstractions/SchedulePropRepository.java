package it.beachill.model.repositories.abstractions;

import it.beachill.model.entities.reservation.ScheduleProp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface SchedulePropRepository extends JpaRepository<ScheduleProp, Long> {
    List<ScheduleProp> findByPlaceEquals(Long placeId);

    Optional<ScheduleProp> findByReservationPlaceAndStartTimeAndEndTimeAndDayNumber(Long placeId, LocalTime startTime, LocalTime endTime, int dayNumber);
}
