package it.beachill.model.repositories.abstractions;

import it.beachill.model.entities.PizzaOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PizzaOrderRepository extends JpaRepository<PizzaOrder, Long> {
}
