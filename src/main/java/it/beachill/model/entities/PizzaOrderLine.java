package it.beachill.model.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "pizza_order_line", schema = "tournament")
public class PizzaOrderLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "pizza_order_id", nullable = false)
    private PizzaOrder pizzaOrder;
    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;
    @Column(name = "pizza_type", nullable = false)
    private String pizzaType;
    private int quantity;

    public PizzaOrderLine() {}

    public PizzaOrderLine(Long id, PizzaOrder pizzaOrder, Player player, String pizzaType, int quantity) {
        this.id = id;
        this.pizzaOrder = pizzaOrder;
        this.player = player;
        this.pizzaType = pizzaType;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PizzaOrder getPizzaOrder() {
        return pizzaOrder;
    }

    public void setPizzaOrder(PizzaOrder pizzaOrder) {
        this.pizzaOrder = pizzaOrder;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public String getPizzaType() {
        return pizzaType;
    }

    public void setPizzaType(String pizzaType) {
        this.pizzaType = pizzaType;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


}
