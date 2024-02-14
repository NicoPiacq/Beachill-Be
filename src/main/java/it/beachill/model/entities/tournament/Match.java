package it.beachill.model.entities.tournament;

import it.beachill.model.entities.user.User;
import jakarta.persistence.*;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "match", schema = "tournament")
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "match_number")
    private Integer matchNumber;
    @Column(name = "group_stage")
    private Integer groupStage;
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
    private Integer fieldNumber;
    @Column(name = "start_date")
    private LocalDate startDate;
    @ManyToOne
    @JoinColumn(name = "winner_team_id")
    private Team winnerTeam;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User matchAdmin;
    private Integer status;
    @OneToMany(mappedBy = "match", cascade = CascadeType.ALL)
    private List<SetMatch> sets = new ArrayList<>();
    public Match() {}
    
    public Match(Long id) {
        this.id = id;
    }

    public Match(Team homeTeam, Team awayTeam, LocalDate startDate, User matchAdmin, Integer status, MatchType matchType) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.startDate = startDate;
        this.matchAdmin = matchAdmin;
        this.status = status;
        this.matchType = matchType;
    }

    public Match(Integer matchNumber, MatchType matchType, Integer groupStage, Tournament tournament, Team homeTeam, Team awayTeam, Integer fieldNumber, User matchAdmin) {
        this.matchNumber = matchNumber;
        this.matchType = matchType;
        this.groupStage = groupStage;
        this.tournament = tournament;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.fieldNumber = fieldNumber;
        this.matchAdmin= matchAdmin;
    }

    public Match(Integer matchNumber, MatchType matchType, Tournament tournament, Integer fieldNumber, User matchAdmin) {
        this.matchNumber = matchNumber;
        this.matchType = matchType;
        this.tournament = tournament;
        this.fieldNumber = fieldNumber;
        this.matchAdmin= matchAdmin;
    }

    public Match(Long id, Tournament tournament, Team homeTeam, Team awayTeam, Integer fieldNumber, LocalDate startDate, Team winnerTeam) {
        this.id = id;
        this.tournament = tournament;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.fieldNumber = fieldNumber;
        this.startDate = startDate;
        this.winnerTeam = winnerTeam;
    }
    
    public Match(Long id, Integer matchNumber, Integer groupStage, MatchType matchType, Tournament tournament, Team homeTeam, Team awayTeam, Integer fieldNumber, LocalDate startDate, Team winnerTeam, User matchAdmin) {
        this.id = id;
        this.matchNumber = matchNumber;
        this.groupStage = groupStage;
        this.matchType = matchType;
        this.tournament = tournament;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.fieldNumber = fieldNumber;
        this.startDate = startDate;
        this.winnerTeam = winnerTeam;
        this.matchAdmin = matchAdmin;
    }
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMatchNumber() {
        return matchNumber;
    }

    public void setMatchNumber(int matchNumber) {
        this.matchNumber = matchNumber;
    }

    public void setFieldNumber(Integer fieldNumber) {
        this.fieldNumber = fieldNumber;
    }

    public Integer getGroupStage() {
        return groupStage;
    }

    public void setGroupStage(Integer groupStage) {
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

    public Integer getFieldNumber() {
        return fieldNumber;
    }


    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public List<SetMatch> getSets() {
        return sets;
    }

    public void setSets(List<SetMatch> sets) {
        this.sets = sets;
    }


    
    public User getMatchAdmin() {
        return matchAdmin;
    }
    
    public void setMatchAdmin(User matchAdmin) {
        this.matchAdmin = matchAdmin;
    }

    public void setMatchNumber(Integer matchNumber) {
        this.matchNumber = matchNumber;
    }

    public Team getWinnerTeam() {
        return winnerTeam;
    }

    public void setWinnerTeam(Team winnerTeam) {
        this.winnerTeam = winnerTeam;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
