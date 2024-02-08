package it.beachill.model.entities.reservation;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "field", schema = "reservation")
public class Field {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sport")
    private Sport sport;

    @ManyToOne
    @JoinColumn(name = "id_place")
    private ReservationPlace reservationPlace;

    @OneToMany(mappedBy = "field")
    private List<ScheduleProp> schedulePropList;
    @OneToMany(mappedBy = "field")
    private List<Reservation> reservations;

    public Field() {
    }

    public Field(Long id) {
        this.id = id;
    }

    public Field(Long id, Sport sport, ReservationPlace reservationPlace) {
        this.id = id;
        this.sport = sport;
        this.reservationPlace = reservationPlace;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Sport getSport() {
        return sport;
    }

    public void setSport(Sport sport) {
        this.sport = sport;
    }

    public ReservationPlace getReservationPlace() {
        return reservationPlace;
    }

    public void setReservationPlace(ReservationPlace reservationPlace) {
        this.reservationPlace = reservationPlace;
    }

    public List<ScheduleProp> getSchedulePropList() {
        return schedulePropList;
    }

    public void setSchedulePropList(List<ScheduleProp> schedulePropList) {
        this.schedulePropList = schedulePropList;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }
}
