package it.beachill.model.entities.tournament;

import it.beachill.model.entities.user.User;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "player", schema = "tournament")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(mappedBy = "teamLeader")
    private List<Team> teamsCaptainedBy;
    @OneToMany(mappedBy = "player")
    private List<TeamComponent> teams;
    @OneToMany(mappedBy = "player")
    private List<PizzaOrderLine> pizzaOrderLines;
    @OneToMany(mappedBy = "player")
    List<Score> scoreList;
    @OneToOne(mappedBy = "player")
    private User user;

    public Player() {}

    public Player(User user) {
        this.user = user;
    }

    public Player(Long id, Long score) {
        this.id = id;
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

    public List<Score> getScoreList() {
        return scoreList;
    }

    public void setScoreList(List<Score> scoreList) {
        this.scoreList = scoreList;
    }
}
