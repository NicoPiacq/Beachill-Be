package it.beachill.model.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "player", schema = "tournament")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 64)
    private String nickname;
    private Long score;
    @OneToMany(mappedBy = "teamLeader")
    private List<Team> teamsCaptainedBy;
    @OneToMany(mappedBy = "player")
    private List<TeamComponent> teams;
    @OneToMany(mappedBy = "player")
    private List<PizzaOrderLine> pizzaOrderLines;
    @OneToOne(mappedBy = "player")
    private User user;

    public Player() {}

    public Player(Long id, String nickname, Long score) {
        this.id = id;
        this.nickname = nickname;
        this.score = score;
    }
    
    public Player(Long id) {
        this.id = id;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    public List<Team> getTeamsCaptainedBy() {
        return teamsCaptainedBy;
    }

    public void setTeamsCaptainedBy(List<Team> teamsCaptainedBy) {
        this.teamsCaptainedBy = teamsCaptainedBy;
    }

    public List<TeamComponent> getTeams() {
        return teams;
    }

    public void setTeams(List<TeamComponent> teams) {
        this.teams = teams;
    }

    public List<PizzaOrderLine> getPizzaOrderLines() {
        return pizzaOrderLines;
    }

    public void setPizzaOrderLines(List<PizzaOrderLine> pizzaOrderLines) {
        this.pizzaOrderLines = pizzaOrderLines;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
