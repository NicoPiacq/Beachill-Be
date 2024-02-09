package it.beachill.model.services.implementation;

import it.beachill.model.entities.reservation.Field;
import it.beachill.model.entities.reservation.ReservationPlace;
import it.beachill.model.entities.reservation.ScheduleProp;
import it.beachill.model.repositories.abstractions.FieldRepository;
import it.beachill.model.repositories.abstractions.SchedulePropRepository;
import it.beachill.model.services.abstraction.FieldService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class JPAFieldService implements FieldService {

    private final FieldRepository fieldRepository;
    private final SchedulePropRepository schedulePropRepository;

    public JPAFieldService(FieldRepository fieldRepository, SchedulePropRepository schedulePropRepository) {
        this.fieldRepository = fieldRepository;
        this.schedulePropRepository = schedulePropRepository;
    }

    @Override
    public List<Field> getAllFieldsFromPlaceId(Long id) {
        return fieldRepository.findByReservationPlace(new ReservationPlace(id));
    }

    @Override
    public List<ScheduleProp> getAllPropertiesPerDate(Long fieldId, LocalDate date) {
        int dayNumber = date.getDayOfWeek().getValue();
        return schedulePropRepository.findByFieldAndDayNumber(new Field(fieldId), dayNumber);
    }
}
