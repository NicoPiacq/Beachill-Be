package it.beachill.model.services.implementation;

import it.beachill.dtos.ReservationPlaceDto;
import it.beachill.dtos.SchedulePropDto;
import it.beachill.model.entities.reservation.Field;
import it.beachill.model.entities.reservation.ReservationPlace;
import it.beachill.model.entities.reservation.ScheduleProp;
import it.beachill.model.entities.user.User;
import it.beachill.model.exceptions.CheckFailedException;
import it.beachill.model.exceptions.ReservationChecksFailedException;
import it.beachill.model.repositories.abstractions.ReservationPlaceRepository;
import it.beachill.model.repositories.abstractions.ReservationRepository;
import it.beachill.model.repositories.abstractions.SchedulePropRepository;
import it.beachill.model.services.abstraction.ManagersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class JPAManagersService implements ManagersService {
    private final ReservationRepository reservationRepository;
    private final ReservationPlaceRepository reservationPlaceRepository;
    private final SchedulePropRepository schedulePropRepository;

    @Autowired
    public JPAManagersService(ReservationRepository reservationRepository, ReservationPlaceRepository reservationPlaceRepository, SchedulePropRepository schedulePropRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationPlaceRepository = reservationPlaceRepository;
        this.schedulePropRepository = schedulePropRepository;
    }

    @Override
    public void createNewPlace(ReservationPlace reservationPlace) throws ReservationChecksFailedException {
        Optional<ReservationPlace> reservationPlaceOptional = reservationPlaceRepository.findByNameAndAddressAndCity(reservationPlace.getName(), reservationPlace.getAddress(), reservationPlace.getCity());
        if(reservationPlaceOptional.isPresent()){
            throw new ReservationChecksFailedException("Il campo che si vuole inserire è gia presente!");
        }
        this.reservationPlaceRepository.save(reservationPlace);
    }

    @Override
    public void createNewScheduleProperties(User user, SchedulePropDto schedulePropDto) throws ReservationChecksFailedException {
        Optional<ReservationPlace> reservationPlaceOptional = reservationPlaceRepository.findById(schedulePropDto.getFieldId());
        if(reservationPlaceOptional.isEmpty()){
            throw new ReservationChecksFailedException("Il campo non è presente.");
        }

        Optional<ScheduleProp> schedulePropOptional = schedulePropRepository.findByFieldAndStartTimeAndEndTimeAndDayNumber(new Field(schedulePropDto.getFieldId()), schedulePropDto.getStartTime(), schedulePropDto.getEndTime(), schedulePropDto.getDayNumber());

        if(schedulePropOptional.isPresent()){
            throw new ReservationChecksFailedException("E' già presente una prop come qulla che vuoi inserire.");
        }

        ScheduleProp scheduleProp = schedulePropDto.fromDto();
        schedulePropRepository.save(scheduleProp);
    }

    @Override
    public void deleteReservationPlace(User user, Long id) throws CheckFailedException {
        Optional<ReservationPlace> reservationPlaceOptional = reservationPlaceRepository.findById(id);
        if(reservationPlaceOptional.isEmpty()){
            throw new CheckFailedException("La struttura non esiste");
        }
        if(!Objects.equals(reservationPlaceOptional.get().getManager().getId(), user.getId())){
            throw new CheckFailedException("Non sei il manager della struttura");
        }
        reservationPlaceRepository.deleteById(id);
    }

    @Override
    public void changeReservationPlaceDetails(User user, ReservationPlace reservationPlace) throws CheckFailedException {
        Optional<ReservationPlace> reservationPlaceOptional = reservationPlaceRepository.findById(reservationPlace.getId());
        if(reservationPlaceOptional.isEmpty()){
            throw new CheckFailedException("La struttura non esiste");
        }
        if(!Objects.equals(reservationPlaceOptional.get().getManager().getId(), user.getId())){
            throw new CheckFailedException("Non sei il manager della struttura");
        }
        reservationPlaceRepository.save(reservationPlace);
    }
}
