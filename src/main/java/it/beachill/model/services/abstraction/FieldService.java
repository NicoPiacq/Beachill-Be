package it.beachill.model.services.abstraction;

import it.beachill.model.entities.reservation.Field;
import it.beachill.model.entities.reservation.ScheduleProp;

import java.time.LocalDate;
import java.util.List;

public interface FieldService {
    List<Field> getAllFieldsFromPlaceId(Long id);

    List<ScheduleProp> getAllPropertiesPerDate(Long fieldId, LocalDate date);
}
