package it.beachill.model.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "set_match", schema = "tournament",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"team_id", "player_id"})})
public class SetMatch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "match_id")
    private Match match;
    @Column(name = "set_number")
    private int setNumber;
    @Column(name = "home_team_score")
    private int homeTeamScore;
    @Column(name = "away_team_score")
    private int awayTeamScore;

    public SetMatch() {}

    public SetMatch(Long id, Match match, int setNumber, int homeTeamScore, int awayTeamScore) {
        this.id = id;
        this.match = match;
        this.setNumber = setNumber;
        this.homeTeamScore = homeTeamScore;
        this.awayTeamScore = awayTeamScore;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public int getSetNumber() {
        return setNumber;
    }

    public void setSetNumber(int setNumber) {
        this.setNumber = setNumber;
    }

    public int getHomeTeamScore() {
        return homeTeamScore;
    }

    public void setHomeTeamScore(int homeTeamScore) {
        this.homeTeamScore = homeTeamScore;
    }

    public int getAwayTeamScore() {
        return awayTeamScore;
    }

    public void setAwayTeamScore(int awayTeamScore) {
        this.awayTeamScore = awayTeamScore;
    }
}
