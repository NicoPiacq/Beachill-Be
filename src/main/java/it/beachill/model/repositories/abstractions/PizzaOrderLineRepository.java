package it.beachill.model.repositories.abstractions;

import it.beachill.model.entities.tournament.PizzaOrderLine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PizzaOrderLineRepository extends JpaRepository<PizzaOrderLine, Long> {
}
