package it.beachill.model.services.implementation;

import it.beachill.dtos.SchedulePropDto;
import it.beachill.model.entities.reservation.ReservationPlace;
import it.beachill.model.entities.reservation.ScheduleProp;
import it.beachill.model.entities.user.User;
import it.beachill.model.exceptions.ReservationChecksFailedException;
import it.beachill.model.repositories.abstractions.ReservationPlaceRepository;
import it.beachill.model.repositories.abstractions.ReservationRepository;
import it.beachill.model.repositories.abstractions.SchedulePropRepository;
import it.beachill.model.services.abstraction.AdminsService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class JPAAdminsService implements AdminsService {
    private final ReservationRepository reservationRepository;
    private final ReservationPlaceRepository reservationPlaceRepository;
    private final SchedulePropRepository schedulePropRepository;

    @Autowired
    public JPAAdminsService(ReservationRepository reservationRepository, ReservationPlaceRepository reservationPlaceRepository, SchedulePropRepository schedulePropRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationPlaceRepository = reservationPlaceRepository;
        this.schedulePropRepository = schedulePropRepository;
    }

    @Override
    public void createNewPlace(ReservationPlace reservationPlace) throws ReservationChecksFailedException {
        Optional<ReservationPlace> reservationPlaceOptional = reservationPlaceRepository.findByNameAndSportAndFieldNumber(reservationPlace.getName(), reservationPlace.getSport().getSportName(), reservationPlace.getFieldNumber());
        if(reservationPlaceOptional.isPresent()){
            throw new ReservationChecksFailedException("Il campo che si vuole inserire è gia presente!");
        }
        this.reservationPlaceRepository.save(reservationPlace);
    }

    @Override
    public void createNewScheduleProperties(User user, SchedulePropDto schedulePropDto) throws ReservationChecksFailedException {
        Optional<ReservationPlace> reservationPlaceOptional = reservationPlaceRepository.findById(schedulePropDto.getPlaceId());
        if(reservationPlaceOptional.isEmpty()){
            throw new ReservationChecksFailedException("Il campo non è presente.");
        }

        Optional<ScheduleProp> schedulePropOptional = schedulePropRepository.findByReservationPlaceAndStartTimeAndEndTimeAndDayNumber(schedulePropDto.getPlaceId(), schedulePropDto.getStartTime(), schedulePropDto.getEndTime(), schedulePropDto.getDayNumber());

        if(schedulePropOptional.isPresent()){
            throw new ReservationChecksFailedException("E' già presente una prop come qulla che vuoi inserire.");
        }

        ScheduleProp scheduleProp = schedulePropDto.fromDto();
        schedulePropRepository.save(scheduleProp);
    }
}
