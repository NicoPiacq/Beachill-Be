package it.beachill.model.entities.reservation;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "domain_sport", schema = "reservation")
public class Sport {

    @Id
    @Column(name = "sport")
    private String sportName;

    @OneToMany(mappedBy = "sport")
    private List<Field> fields;

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

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }
}
