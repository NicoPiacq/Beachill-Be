package it.beachill.model.services.implementation;

import it.beachill.dtos.ReservationDto;
import it.beachill.model.entities.reservation.Reservation;
import it.beachill.model.entities.reservation.ReservationPlace;
import it.beachill.model.entities.reservation.ScheduleProp;
import it.beachill.model.entities.user.User;
import it.beachill.model.exceptions.ReservationChecksFailedException;
import it.beachill.model.repositories.abstractions.ReservationPlaceRepository;
import it.beachill.model.repositories.abstractions.ReservationRepository;
import it.beachill.model.repositories.abstractions.SchedulePropRepository;
import it.beachill.model.services.abstraction.ReservationsService;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class JPAReservationsService implements ReservationsService {

    private final ReservationRepository reservationRepository;
    private final SchedulePropRepository schedulePropRepository;
    private final ReservationPlaceRepository reservationPlaceRepository;
    public JPAReservationsService(ReservationRepository reservationRepository, SchedulePropRepository schedulePropRepository, ReservationPlaceRepository reservationPlaceRepository){
        this.reservationRepository = reservationRepository;
        this.schedulePropRepository = schedulePropRepository;
        this.reservationPlaceRepository = reservationPlaceRepository;
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

        Optional<Reservation> reservationOptional = reservationRepository.findByReservationPlaceAndDateAndStartAndEnd(new ReservationPlace(reservationDto.getPlaceId()),
                reservationDto.getDate(), reservationDto.getStart(), reservationDto.getEnd());
        if(reservationOptional.isPresent()){
            throw new ReservationChecksFailedException("Esiste già una prenotazione in questo slot!");
        }

        List<ScheduleProp> schedulePropList = schedulePropRepository.findByReservationPlaceEquals(new ReservationPlace(reservationDto.getPlaceId()));
        ScheduleProp myScheduleProp = getScheduleProp(reservationDto, schedulePropList);


        if(reservationDto.getStart().until(reservationDto.getEnd(), ChronoUnit.MINUTES) != myScheduleProp.getDuration()){
            throw new ReservationChecksFailedException("La durata della prenotazione non è corretta!");
        }

        Reservation reservation = reservationDto.fromDto();
        this.reservationRepository.save(reservation);
    }

    @Override
    public List<ReservationPlace> getAllReservationPlaces() {
        return reservationPlaceRepository.findAll();
    }

    @Override
    public List<ReservationPlace> getAllReservationPlacesGroupByName() {
        return reservationPlaceRepository.findAllReservationPlacesGroupByName();
    }

    private static ScheduleProp getScheduleProp(ReservationDto reservationDto, List<ScheduleProp> schedulePropList) throws ReservationChecksFailedException {
        if(schedulePropList.isEmpty()){
            throw new ReservationChecksFailedException("Il campo selezionato non è presente!");
        }
        int dayNumber = reservationDto.getDate().getDayOfWeek().getValue();
        ScheduleProp myScheduleProp = null;
        for(ScheduleProp scheduleProp : schedulePropList){
            if(scheduleProp.getDayNumber() == dayNumber && (!reservationDto.getStart().isBefore(scheduleProp.getStartTime()) &&  !reservationDto.getEnd().isAfter(scheduleProp.getEndTime()))){
                myScheduleProp = scheduleProp;
            }
        }
        if(myScheduleProp == null){
            throw new ReservationChecksFailedException("Non è possibile prenotare a questo orario");
        }
        return myScheduleProp;
    }
}
