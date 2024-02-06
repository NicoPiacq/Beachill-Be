package it.beachill.model.services.implementation;

import it.beachill.dtos.ReservationDto;
import it.beachill.model.entities.reservation.Reservation;
import it.beachill.model.entities.user.User;
import it.beachill.model.exceptions.ReservationChecksFailedException;
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
        return reservationRepository.findByDateEquals(date);
    }

    @Override
    public void createNewReservation(ReservationDto reservationDto, User user) throws ReservationChecksFailedException {
        if(user.getId() != reservationDto.getUserId()){
            throw new ReservationChecksFailedException("Non sei l' utente al quale vuoi associare la prenotazione");
        }
        Reservation reservation = reservationDto.fromDto();
        this.reservationRepository.save(reservation);
    }
}
