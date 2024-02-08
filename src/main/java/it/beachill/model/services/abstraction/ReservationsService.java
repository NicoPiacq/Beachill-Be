package it.beachill.model.services.abstraction;

import it.beachill.dtos.ReservationDto;
import it.beachill.model.entities.reservation.Reservation;
import it.beachill.model.entities.reservation.ReservationPlace;
import it.beachill.model.entities.user.User;
import it.beachill.model.exceptions.ReservationChecksFailedException;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ReservationsService {

    List<Reservation> getAllReservationsPerDate(LocalDate date);

    void createNewReservation(ReservationDto reservationDto, User user) throws ReservationChecksFailedException;

    List<ReservationPlace> getAllReservationPlaces();

    Optional<ReservationPlace> getReservationPlace(Long id);
}
