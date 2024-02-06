package it.beachill.model.services.implementation;

import it.beachill.model.entities.reservation.Reservation;
import it.beachill.model.repositories.abstractions.ReservationRepository;
import it.beachill.model.services.abstraction.ReservationsService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class JPAReservationsService implements ReservationsService {

    private final ReservationRepository reservationRepository;
    public JPAReservationsService(ReservationRepository reservationRepository){
        this.reservationRepository = reservationRepository;
    }
    @Override
    public List<Reservation> getAllReservationsPerDate(LocalDate date) {
        return null;
    }
}
