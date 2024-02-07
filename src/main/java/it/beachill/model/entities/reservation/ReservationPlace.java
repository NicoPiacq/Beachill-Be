package it.beachill.model.entities.reservation;

import it.beachill.model.entities.user.User;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "place", schema = "reservation")
public class ReservationPlace {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String address;
    @ManyToOne
    @JoinColumn(name="manager_id")
    private User manager;
    @ManyToOne
    @JoinColumn(name = "sport")
    private Sport sport;
    @Column (name="field_number")
    private int fieldNumber;

    @OneToMany(mappedBy = "place")
    private List<ScheduleProp> schedulePropList;
    @OneToMany(mappedBy = "place")
    private List<Reservation> reservations;

    public ReservationPlace() {
    }

    public ReservationPlace(Long id) {
        this.id = id;
    }


    public ReservationPlace(Long id, String name, String address, User manager, Sport sport, int fieldNumber) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.manager = manager;
        this.sport = sport;
        this.fieldNumber = fieldNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public User getManager() {
        return manager;
    }

    public void setManager(User manager) {
        this.manager = manager;
    }


    public int getFieldNumber() {
        return fieldNumber;
    }

    public void setFieldNumber(int fieldNumber) {
        this.fieldNumber = fieldNumber;
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

    public Sport getSport() {
        return sport;
    }

    public void setSport(Sport sport) {
        this.sport = sport;
    }
}
