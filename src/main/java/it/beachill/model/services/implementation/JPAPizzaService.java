package it.beachill.model.services.implementation;

import it.beachill.model.entities.PizzaOrderLine;
import it.beachill.model.repositories.abstractions.PizzaOrderLineRepository;
import it.beachill.model.repositories.abstractions.PizzaOrderRepository;
import it.beachill.model.services.abstraction.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JPAPizzaService implements PizzaService {
  private final PizzaOrderLineRepository pizzaOrderLineRepository;


  @Autowired
  public JPAPizzaService(PizzaOrderLineRepository pizzaOrderLineRepository){
    this.pizzaOrderLineRepository = pizzaOrderLineRepository;
  }

  @Override
  public void createPizzaOrderLine(PizzaOrderLine pizzaOrderLine) {
    //devo controllare che il player che fa l'ordine sia realmente iscritto al torneo
    //per il quale vuole fare l'ordine.

    pizzaOrderLineRepository.save(pizzaOrderLine);
  }
}
