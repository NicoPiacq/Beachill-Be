package it.beachill.model.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "team_in_tournament", schema = "tournament",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"team_id", "tournament_id"})})
public class TeamInTournament {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer round;
    @ManyToOne
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;
    @ManyToOne
    @JoinColumn(name = "tournament_id", nullable = false)
    private Tournament tournament;
    private Integer status;

    public TeamInTournament() {}

    public TeamInTournament(Long id, Integer round, Team team, Tournament tournament) {
        this.id = id;
        this.round = round;
        this.team = team;
        this.tournament = tournament;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public void setRound(Integer round) {
        this.round = round;
    }
    
    public Integer getStatus() {
        return status;
    }
    
    public void setStatus(Integer status) {
        this.status = status;
    }
}
