package it.beachill.model.entities.tournament;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "team", schema = "tournament")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "team_name", nullable = false, unique = true)
    private String teamName;
    @ManyToOne
    @JoinColumn(name = "team_leader", nullable = false)
    private Player teamLeader;
    @OneToMany(mappedBy = "team")
    private List<GroupStageStanding> groupStageStandings;
    @OneToMany(mappedBy = "team")
    private List<TeamComponent> teamComponents;
    @OneToMany(mappedBy = "homeTeam")
    private List<Match> matchesAsHome;
    @OneToMany(mappedBy = "awayTeam")
    private List<Match> matchesAsAway;
    @OneToMany(mappedBy = "winnerTeam")
    private List<Match> matchesWon;
    @OneToMany(mappedBy = "team")
    private List<TeamInTournament> enrolledTournaments;

    public Team() {}
    
    public Team(Long id) {
        this.id = id;
    }
    
    public Team(Long id, String teamName, Player teamLeader) {
        this.id = id;
        this.teamName = teamName;
        this.teamLeader = teamLeader;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Player getTeamLeader() {
        return teamLeader;
    }

    public void setTeamLeader(Player teamLeader) {
        this.teamLeader = teamLeader;
    }


    public List<TeamComponent> getTeamComponents() {
        return teamComponents;
    }

    public void setTeamComponents(List<TeamComponent> teamComponents) {
        this.teamComponents = teamComponents;
    }

    public List<Match> getMatchesAsHome() {
        return matchesAsHome;
    }

    public void setMatchesAsHome(List<Match> matchesAsHome) {
        this.matchesAsHome = matchesAsHome;
    }

    public List<Match> getMatchesAsAway() {
        return matchesAsAway;
    }

    public void setMatchesAsAway(List<Match> matchesAsAway) {
        this.matchesAsAway = matchesAsAway;
    }

    public List<Match> getMatchesWon() {
        return matchesWon;
    }

    public void setMatchesWon(List<Match> matchesWon) {
        this.matchesWon = matchesWon;
    }

    public List<TeamInTournament> getEnrolledTournaments() {
        return enrolledTournaments;
    }

    public void setEnrolledTournaments(List<TeamInTournament> enrolledTournaments) {
        this.enrolledTournaments = enrolledTournaments;
    }

    public List<GroupStageStanding> getGroupStageStandings() {
        return groupStageStandings;
    }

    public void setGroupStageStandings(List<GroupStageStanding> groupStageStandings) {
        this.groupStageStandings = groupStageStandings;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        return Objects.equals(id, team.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
