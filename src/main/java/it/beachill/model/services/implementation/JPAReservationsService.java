package it.beachill.model.services.implementation;

import it.beachill.dtos.ReservationDto;
import it.beachill.dtos.ReservationRequestDto;
import it.beachill.dtos.ReservationSlotsDto;
import it.beachill.model.entities.reservation.Field;
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
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.LinkedList;
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
    public List<ReservationPlace> getAllReservationPlaces() {
        return reservationPlaceRepository.findAll();
    }

    @Override
    public Optional<ReservationPlace> getReservationPlace(Long id) {
        return reservationPlaceRepository.findById(id);
    }

    @Override
    public List<ReservationSlotsDto> getAllSlotsPerDate(Long fieldId, LocalDate date) {
        List<ScheduleProp> props = schedulePropRepository.findByFieldAndDayNumber(new Field(fieldId), date.getDayOfWeek().getValue());
        List<Reservation> reservationList = reservationRepository.findByFieldAndDate(new Field(fieldId), date);
        List<ReservationSlotsDto> slots = new ArrayList<>();

        for(ScheduleProp p : props) {
            for(LocalTime i = p.getStartTime(); i.isBefore(p.getEndTime()); i = i.plusMinutes(p.getDuration())) {
                boolean isInsert = false;
                for(int j = 0; j < reservationList.size(); j++) {
                    if(reservationList.get(j).getStart() == i && reservationList.get(j).getEnd() == i.plusMinutes(p.getDuration())) {
                        ReservationSlotsDto dto = new ReservationSlotsDto(fieldId, i, i.plusMinutes(p.getDuration()), true, date);
                        dto.setUserId(reservationList.get(j).getId());
                        dto.setUserName(reservationList.get(j).getUser().getName());
                        dto.setUserSurname(reservationList.get(j).getUser().getSurname());
                        slots.add(dto);
                        reservationList.remove(j);
                        isInsert = true;
                        break;
                    }
                }
                if(!isInsert) {
                    slots.add(new ReservationSlotsDto(fieldId, i, i.plusMinutes(p.getDuration()), false, date));
                }
            }
        }
        return slots;
    }

    @Override
    public void createNewReservation(ReservationRequestDto reservationRequestDto, User user) throws ReservationChecksFailedException {

        Optional<Reservation> reservationOptional = reservationRepository.findByFieldAndDateAndStartAndEnd(new Field(reservationRequestDto.getFieldId()),
                reservationRequestDto.getDate(), reservationRequestDto.getStart(), reservationRequestDto.getEnd());
        if(reservationOptional.isPresent()){
            throw new ReservationChecksFailedException("Esiste già una prenotazione in questo slot!");
        }

        List<ScheduleProp> schedulePropList = schedulePropRepository.findByFieldEquals(new Field(reservationRequestDto.getFieldId()));
        ScheduleProp myScheduleProp = getScheduleProp(reservationRequestDto, schedulePropList);


        if(reservationRequestDto.getStart().until(reservationRequestDto.getEnd(), ChronoUnit.MINUTES) != myScheduleProp.getDuration()){
            throw new ReservationChecksFailedException("La durata della prenotazione non è corretta!");
        }

        Reservation reservation = new Reservation();
        reservation.setField(new Field(reservationRequestDto.getFieldId()));
        reservation.setUser(user);
        reservation.setDate(reservationRequestDto.getDate());
        reservation.setStart(reservationRequestDto.getStart());
        reservation.setEnd(reservationRequestDto.getEnd());

        this.reservationRepository.save(reservation);
    }

    private static ScheduleProp getScheduleProp(ReservationRequestDto reservationRequestDto, List<ScheduleProp> schedulePropList) throws ReservationChecksFailedException {
        if(schedulePropList.isEmpty()){
            throw new ReservationChecksFailedException("Il campo selezionato non è presente!");
        }
        int dayNumber = reservationRequestDto.getDate().getDayOfWeek().getValue();
        ScheduleProp myScheduleProp = null;
        for(ScheduleProp scheduleProp : schedulePropList){
            if(scheduleProp.getDayNumber() == dayNumber && (!reservationRequestDto.getStart().isBefore(scheduleProp.getStartTime()) &&  !reservationRequestDto.getEnd().isAfter(scheduleProp.getEndTime()))){
                myScheduleProp = scheduleProp;
            }
        }
        if(myScheduleProp == null){
            throw new ReservationChecksFailedException("Non è possibile prenotare a questo orario");
        }
        return myScheduleProp;
    }

    @Override
    public List<Reservation> getAllReservationByUser(User user) {
        return reservationRepository.findByUser(user);
    }

}
