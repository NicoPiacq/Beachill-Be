package it.beachill.model.services.abstraction;

import it.beachill.model.entities.reservation.Reservation;

import java.time.LocalDate;
import java.util.List;

public interface ReservationsService {

    List<Reservation> getAllReservationsPerDate(LocalDate date);
}
