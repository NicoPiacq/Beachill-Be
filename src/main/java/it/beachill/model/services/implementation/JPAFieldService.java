package it.beachill.model.services.implementation;

import it.beachill.model.entities.reservation.Field;
import it.beachill.model.entities.reservation.ReservationPlace;
import it.beachill.model.repositories.abstractions.FieldRepository;
import it.beachill.model.services.abstraction.FieldService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JPAFieldService implements FieldService {

    private final FieldRepository fieldRepository;

    public JPAFieldService(FieldRepository fieldRepository) {
        this.fieldRepository = fieldRepository;
    }

    @Override
    public List<Field> getAllFieldsFromPlaceId(Long id) {
        return fieldRepository.findByReservationPlace(new ReservationPlace(id));
    }
}
