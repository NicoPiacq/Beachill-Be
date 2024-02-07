package it.beachill.model.entities.reservation;

import it.beachill.model.entities.user.User;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "reservation", schema = "reservation")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="place_id")
    private ReservationPlace reservationPlace;
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
    @Column(name = "reservation_date")
    private LocalDate date;
    @Column(name =  "reservation_start")
    private LocalTime start;
    @Column(name =  "reservation_end")
    private LocalTime end;

    public Reservation() {
    }

    public Reservation(long id, ReservationPlace reservationPlace, User user, LocalDate date, LocalTime start, LocalTime end) {
        this.id = id;
        this.reservationPlace = reservationPlace;
        this.user = user;
        this.date = date;
        this.start = start;
        this.end = end;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ReservationPlace getPlace() {
        return reservationPlace;
    }

    public void setPlace(ReservationPlace reservationPlace) {
        this.reservationPlace = reservationPlace;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStart() {
        return start;
    }

    public void setStart(LocalTime start) {
        this.start = start;
    }

    public LocalTime getEnd() {
        return end;
    }

    public void setEnd(LocalTime end) {
        this.end = end;
    }
}

