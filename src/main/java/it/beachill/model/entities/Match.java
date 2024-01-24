package it.beachill.model.entities;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "match", schema = "tournament")
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "match_number")
    private int matchNumber;
    @Column(name = "group_stage")
    private int groupStage;
    @ManyToOne
    @JoinColumn(name = "match_type")
    private MatchType matchType;
    @ManyToOne
    @JoinColumn(name = "tournament_id")
    private Tournament tournament;
    @ManyToOne
    @JoinColumn(name = "home_team_id")
    private Team homeTeam;
    @ManyToOne
    @JoinColumn(name = "away_team_id")
    private Team awayTeam;
    @Column(name = "field_number")
    private int fieldNumber;
    @Column(name = "start_date")
    private Timestamp startDate;
    @ManyToOne
    @JoinColumn(name = "winner_team_id")
    private Team winnerTeam;
    @OneToMany(mappedBy = "match")
    private List<SetMatch> sets;

    public Match() {}

    public Match(int matchNumber, MatchType matchType, int groupStage, Tournament tournament, Team homeTeam, Team awayTeam, int fieldNumber) {
        this.matchNumber = matchNumber;
        this.matchType = matchType;
        this.groupStage = groupStage;
        this.tournament = tournament;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.fieldNumber = fieldNumber;
    }

    public Match(int matchNumber, MatchType matchType, Tournament tournament, int fieldNumber) {
        this.matchNumber = matchNumber;
        this.matchType = matchType;
        this.tournament = tournament;
        this.fieldNumber = fieldNumber;
    }

    public Match(Long id, Tournament tournament, Team homeTeam, Team awayTeam, int fieldNumber, Timestamp startDate, Team winnerTeam) {
        this.id = id;
        this.tournament = tournament;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.fieldNumber = fieldNumber;
        this.startDate = startDate;
        this.winnerTeam = winnerTeam;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getMatchNumber() {
        return matchNumber;
    }

    public void setMatchNumber(int matchNumber) {
        this.matchNumber = matchNumber;
    }

    public int getGroupStage() {
        return groupStage;
    }

    public void setGroupStage(int groupStage) {
        this.groupStage = groupStage;
    }

    public MatchType getMatchType() {
        return matchType;
    }

    public void setMatchType(MatchType matchType) {
        this.matchType = matchType;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(Team homeTeam) {
        this.homeTeam = homeTeam;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(Team awayTeam) {
        this.awayTeam = awayTeam;
    }

    public int getFieldNumber() {
        return fieldNumber;
    }

    public void setFieldNumber(int fieldNumber) {
        this.fieldNumber = fieldNumber;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Team getWinnerTeam() {
        return winnerTeam;
    }

    public void setWinnerTeam(Team winnerTeam) {
        this.winnerTeam = winnerTeam;
    }

    public List<SetMatch> getSets() {
        return sets;
    }

    public void setSets(List<SetMatch> sets) {
        this.sets = sets;
    }


}
