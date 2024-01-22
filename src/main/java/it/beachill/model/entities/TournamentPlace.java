package it.beachill.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "domain_place_tournament", schema = "tournament")
public class TournamentPlace {
    @Id
    private String place;

    private String adress;

    @Column(name = "field_number")
    private int fieldNumber;

    @OneToMany(mappedBy = "place")
    @JsonIgnore
    private List<Tournament> tournaments;

    public TournamentPlace(){}

    public TournamentPlace(String place, String adress, int fieldNumber) {
        this.place = place;
        this.adress = adress;
        this.fieldNumber = fieldNumber;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public int getFieldNumber() {
        return fieldNumber;
    }

    public void setFieldNumber(int fieldNumber) {
        this.fieldNumber = fieldNumber;
    }

    public List<Tournament> getTournaments() {
        return tournaments;
    }

    public void setTournaments(List<Tournament> tournaments) {
        this.tournaments = tournaments;
    }
}
