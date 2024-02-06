package it.beachill.dtos;

import it.beachill.model.entities.tournament.PizzaOrder;
import it.beachill.model.entities.tournament.PizzaOrderLine;
import it.beachill.model.entities.tournament.Player;

public class PizzaOrderLineDto {
  private Long id;

  private Long pizzaOrderId;

  private Long playerId;

  private String pizzaType;

  private int quantity;

  public PizzaOrderLineDto(){}

  public PizzaOrderLineDto(PizzaOrderLine pizzaOrderLine){
    this.id = pizzaOrderLine.getId();
    this.pizzaOrderId = pizzaOrderLine.getPizzaOrder().getId();
    this.playerId = pizzaOrderLine.getPlayer().getId();
    this.pizzaType = pizzaOrderLine.getPizzaType();
    this.quantity = pizzaOrderLine.getQuantity();
  }

  public PizzaOrderLine fromDto(){
    PizzaOrderLine pizzaOrderLine = new PizzaOrderLine();
    pizzaOrderLine.setId(id);
    pizzaOrderLine.setPizzaOrder(new PizzaOrder(pizzaOrderId));
    pizzaOrderLine.setPlayer(new Player(playerId));
    pizzaOrderLine.setQuantity(quantity);
    return pizzaOrderLine;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getPizzaOrderId() {
    return pizzaOrderId;
  }

  public void setPizzaOrderId(Long pizzaOrderId) {
    this.pizzaOrderId = pizzaOrderId;
  }

  public Long getPlayerId() {
    return playerId;
  }

  public void setPlayerId(Long playerId) {
    this.playerId = playerId;
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
