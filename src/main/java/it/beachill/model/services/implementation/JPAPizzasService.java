package it.beachill.model.services.implementation;

import it.beachill.model.entities.tournament.PizzaOrderLine;
import it.beachill.model.repositories.abstractions.PizzaOrderLineRepository;
import it.beachill.model.services.abstraction.PizzasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JPAPizzasService implements PizzasService {
  private final PizzaOrderLineRepository pizzaOrderLineRepository;


  @Autowired
  public JPAPizzasService(PizzaOrderLineRepository pizzaOrderLineRepository){
    this.pizzaOrderLineRepository = pizzaOrderLineRepository;
  }

  @Override
  public void createPizzaOrderLine(PizzaOrderLine pizzaOrderLine) {
    //devo controllare che il player che fa l'ordine sia realmente iscritto al torneo
    //per il quale vuole fare l'ordine.

    pizzaOrderLineRepository.save(pizzaOrderLine);
  }
}
