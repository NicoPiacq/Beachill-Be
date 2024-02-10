package it.beachill.model.entities.tournament;

import jakarta.persistence.*;

import java.util.List;

// 4.corto -> 4 squadre un girone corto
// 4.lungo -> 4 squadre un girone lungo
@Entity
@Table(name = "domain_type_tournament", schema = "tournament")
public class TournamentType {
    @Id
    @Column(name = "tournament_type_name")
    private String tournamentTypeName;

    @Column(name = "tournament_description")
    private String tournamentDescription;

    @OneToMany(mappedBy = "tournamentType")
    private List<Tournament> tournaments;

    public TournamentType(){}

    public TournamentType(String tournamentTypeName) {
        this.tournamentTypeName = tournamentTypeName;
    }

    public TournamentType(String tournamentType, String tournamentDescription) {
        this.tournamentTypeName = tournamentType;
        this.tournamentDescription = tournamentDescription;
    }

    public String getTournamentTypeName() {
        return tournamentTypeName;
    }

    public void setTournamentTypeName(String tournamentType) {
        this.tournamentTypeName = tournamentType;
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
