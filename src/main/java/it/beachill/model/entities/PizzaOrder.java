package it.beachill.model.entities;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "pizza_order", schema = "tournament")
public class PizzaOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "tournament_id")
    private Tournament tournament;
    @Column(name = "order_date")
    private Timestamp orderDate;
    @OneToMany(mappedBy = "pizzaOrder")
    private List<PizzaOrderLine> pizzaOrderLines;

    public PizzaOrder() {}

    public PizzaOrder(Long id, Tournament tournament, Timestamp orderDate) {
        this.id = id;
        this.tournament = tournament;
        this.orderDate = orderDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public Timestamp getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    public List<PizzaOrderLine> getPizzaOrderLines() {
        return pizzaOrderLines;
    }

    public void setPizzaOrderLines(List<PizzaOrderLine> pizzaOrderLines) {
        this.pizzaOrderLines = pizzaOrderLines;
    }


}
