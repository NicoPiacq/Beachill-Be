package it.beachill.api.restcontrollers;

import it.beachill.dtos.FieldDto;
import it.beachill.model.entities.reservation.Field;
import it.beachill.model.services.abstraction.FieldService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/field")
public class FieldRestController {

    private final FieldService fieldService;

    public FieldRestController(FieldService fieldService) {
        this.fieldService = fieldService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<FieldDto>> getAllFieldsFromPlaceId(@PathVariable Long id) {
        List<Field> fields = fieldService.getAllFieldsFromPlaceId(id);
        List<FieldDto> result = fields.stream().map(FieldDto::new).toList();
        return ResponseEntity.ok(result);
    }

}
