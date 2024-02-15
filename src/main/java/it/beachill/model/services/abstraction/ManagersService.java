package it.beachill.model.services.abstraction;

import it.beachill.dtos.ReservationPlaceDto;
import it.beachill.dtos.SchedulePropDto;
import it.beachill.model.entities.reservation.ReservationPlace;
import it.beachill.model.entities.user.User;
import it.beachill.model.exceptions.CheckFailedException;
import it.beachill.model.exceptions.ReservationChecksFailedException;

public interface ManagersService {
    void createNewPlace(ReservationPlace reservationPlace) throws ReservationChecksFailedException;

    void createNewScheduleProperties(User user, SchedulePropDto schedulePropDto) throws ReservationChecksFailedException;

    void deleteReservationPlace(User user, Long id) throws CheckFailedException;

    void changeReservationPlaceDetails(User user, ReservationPlace reservationPlace) throws CheckFailedException;
}
