package it.beachill.model.entities.reservation;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "domain_sport", schema = "reservation")
public class Sport {

    @Id
    private String sportName;

    @OneToMany(mappedBy = "sport")
    private List<ReservationPlace> places;

    public Sport() {
    }

    public Sport(String sportName) {
        this.sportName = sportName;
    }

    public String getSportName() {
        return sportName;
    }

    public void setSportName(String sportName) {
        this.sportName = sportName;
    }

    public List<ReservationPlace> getPlaces() {
        return places;
    }

    public void setPlaces(List<ReservationPlace> places) {
        this.places = places;
    }
}
