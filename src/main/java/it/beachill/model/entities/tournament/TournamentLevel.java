package it.beachill.model.entities.tournament;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "domain_tournament_level", schema = "tournament")
public class TournamentLevel {
    @Id
    @Column(name = "tournament_level_name")
    private String levelName;

    @OneToMany(mappedBy = "tournamentLevel")
    private List<Tournament> tournaments;

    public TournamentLevel() {
    }

    public TournamentLevel(String levelName) {
        this.levelName = levelName;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public List<Tournament> getTournaments() {
        return tournaments;
    }

    public void setTournaments(List<Tournament> tournaments) {
        this.tournaments = tournaments;
    }
}
