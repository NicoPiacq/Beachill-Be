package it.beachill.model.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "team_component", schema = "tournament",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"team_id", "player_id"})})
public class TeamComponent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;
    @ManyToOne
    @JoinColumn(name = "player_id", nullable = false)
    private Player player;
    private Integer status;

    public TeamComponent() {}

    public TeamComponent(Long id, Team team, Player player) {
        this.id = id;
        this.team = team;
        this.player = player;
    }
    
    public TeamComponent(Team team, Player player) {
        this.team = team;
        this.player = player;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
    
    public Integer getStatus() {
        return status;
    }
    
    public void setStatus(Integer status) {
        this.status = status;
    }
}
