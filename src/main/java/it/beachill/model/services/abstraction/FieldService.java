package it.beachill.model.services.abstraction;

import it.beachill.model.entities.reservation.Field;

import java.util.List;

public interface FieldService {
    List<Field> getAllFieldsFromPlaceId(Long id);
}
