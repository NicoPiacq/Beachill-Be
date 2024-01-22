package it.beachill.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

// 4.corto -> 4 squadre un girone corto
// 4.lungo -> 4 squadre un girone lungo
@Entity
@Table(name = "domain_type_tournament", schema = "tournament")
public class TournamentType {
    @Id
    @Column(name = "tournament_type", length = 20)
    private String tournamentType;

    @Column(name = "tournament_description")
    private String tournamentDescription;

    @OneToMany(mappedBy = "tournamentType")
    private List<Tournament> tournaments;

    public TournamentType(){}

    public TournamentType(String tournamentType, String tournamentDescription) {
        this.tournamentType = tournamentType;
        this.tournamentDescription = tournamentDescription;
    }

    public String getTournamentType() {
        return tournamentType;
    }

    public void setTournamentType(String tournamentType) {
        this.tournamentType = tournamentType;
    }

    public String getTournamentDescription() {
        return tournamentDescription;
    }

    public void setTournamentDescription(String tournamentDescription) {
        this.tournamentDescription = tournamentDescription;
    }

    public List<Tournament> getTournaments() {
        return tournaments;
    }

    public void setTournaments(List<Tournament> tournaments) {
        this.tournaments = tournaments;
    }
}
