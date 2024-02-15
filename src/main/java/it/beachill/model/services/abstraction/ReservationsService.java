package it.beachill.model.services.abstraction;

import it.beachill.dtos.ReservationDto;
import it.beachill.dtos.ReservationRequestDto;
import it.beachill.dtos.ReservationSlotsDto;
import it.beachill.model.entities.reservation.Reservation;
import it.beachill.model.entities.reservation.ReservationPlace;
import it.beachill.model.entities.user.User;
import it.beachill.model.exceptions.ReservationChecksFailedException;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ReservationsService {

    List<Reservation> getAllReservationsPerDate(LocalDate date);

    void createNewReservation(ReservationRequestDto reservationRequestDto, User user) throws ReservationChecksFailedException;

    List<ReservationPlace> getAllReservationPlaces();

    Optional<ReservationPlace> getReservationPlace(Long id);

    List<ReservationSlotsDto> getAllSlotsPerDate(Long fieldId, LocalDate date);

    List<Reservation> getAllReservationByUser(User user);

    List<ReservationPlace> searchPlaceByString(String toFind);

}
