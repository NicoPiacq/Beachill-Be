package it.beachill.model.entities.reservation;

import jakarta.persistence.*;

@Entity
@Table(name = "reservation", schema = "reservation")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

}
