package it.beachill.model.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
@Table(name = "group_stage_standing", schema = "tournament")
public class GroupStageStanding implements Comparable<GroupStageStanding>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "int default 0")
    int standing;
    @Column(columnDefinition = "int default 0")
    private int points;
    @Column(name = "group_stage")
    int groupStage;
    @Column(name = "point_scored")
    int pointScored;
    @Column(name = "point_conceded")
    int pointConceded;
    @OneToOne()
    @JoinColumn(name = "tournament_id")
    private Tournament tournament;
    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    public GroupStageStanding() {}

    public GroupStageStanding(Long id, int standing, Tournament tournament, Team team) {
        this.id = id;
        this.standing = standing;
        this.tournament = tournament;
        this.team = team;
    }

    public GroupStageStanding(int groupStage, Tournament tournament, Team team) {
        this.groupStage = groupStage;
        this.tournament = tournament;
        this.team = team;
    }

    //ho fatto una cosa brutta, ho chiesto a chatGPT e ho copiaincollato oops
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupStageStanding that = (GroupStageStanding) o;
        return standing == that.standing &&
                points == that.points &&
                groupStage == that.groupStage &&
                Objects.equals(id, that.id) &&
                Objects.equals(tournament, that.tournament) &&
                Objects.equals(team, that.team);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, standing, points, groupStage, tournament, team);
    }

    @Override
    public int compareTo(GroupStageStanding other) {
        // Ordina in modo decrescente in base ai punti
        return Integer.compare(other.points, this.points);
    }
    //--------------------------- FINE COSTRUTTORI --------------------------

    public void addWinPoints(){
        this.points += 3;
    }

    public static Map<Integer, List<GroupStageStanding>> groupByGroupStage(List<GroupStageStanding> standings) {
        return standings.stream()
                .collect(Collectors.groupingBy(GroupStageStanding::getGroupStage));
    }

    public void addPointScoredAndPointConceded(int pointScored, int pointConceded){
        this.pointScored += pointScored;
        this.pointConceded += pointConceded;
    }

    //----------------------- GETTER E SETTER -------------------------------

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getStanding() {
        return standing;
    }

    public void setStanding(int standing) {
        this.standing = standing;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getGroupStage() {
        return groupStage;
    }

    public void setGroupStage(int groupStage) {
        this.groupStage = groupStage;
    }

    public int getPointScored() {
        return pointScored;
    }

    public void setPointScored(int pointScored) {
        this.pointScored = pointScored;
    }

    public int getPointConceded() {
        return pointConceded;
    }

    public void setPointConceded(int pointConceded) {
        this.pointConceded = pointConceded;
    }
}
