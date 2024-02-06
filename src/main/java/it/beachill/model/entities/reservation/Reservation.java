package it.beachill.model.entities.reservation;

import it.beachill.model.entities.user.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "reservation", schema = "reservation")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name="place_id")
    private Place place;
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
    @Column(name =  "reservation_start")
    private LocalDateTime start;
    @Column(name =  "reservation_end")
    private LocalDateTime end;

    public Reservation() {
    }

    public Reservation(long id, Place place, User user, LocalDateTime start, LocalDateTime end) {
        this.id = id;
        this.place = place;
        this.user = user;
        this.start = start;
        this.end = end;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }
}

