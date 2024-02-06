package it.beachill.api.restcontrollers;

import it.beachill.dtos.PizzaOrderLineDto;
import it.beachill.model.entities.tournament.PizzaOrderLine;
import it.beachill.model.services.abstraction.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/pizza-order")
@CrossOrigin
public class PizzaOrderLineRestController {

  private final PizzaService pizzaService;

  @Autowired
  public PizzaOrderLineRestController(PizzaService pizzaService){
    this.pizzaService = pizzaService;
  }

@PostMapping("/create")
  public ResponseEntity<PizzaOrderLineDto> createPizzaOrderLine(@RequestBody PizzaOrderLineDto pizzaOrderLineDto) throws URISyntaxException {
  PizzaOrderLine pizzaOrderLine = pizzaOrderLineDto.fromDto();
  pizzaService.createPizzaOrderLine(pizzaOrderLine);
  PizzaOrderLineDto result = new PizzaOrderLineDto(pizzaOrderLine);
  //da cambiare URI
  return ResponseEntity.ok(result);
}



}
