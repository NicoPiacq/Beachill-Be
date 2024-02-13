package it.beachill.dtos;

import it.beachill.model.entities.tournament.Match;
import it.beachill.model.entities.tournament.MatchType;
import it.beachill.model.entities.tournament.Team;
import it.beachill.model.entities.tournament.Tournament;
import it.beachill.model.entities.user.User;

import java.sql.Timestamp;

public class MatchDto {
    private Long id;
    private Integer matchNumber;
    private Integer groupStage;
    private String matchType;
    private Long tournamentId;
    private String tournamentName;
    private Long homeTeamId;
    private String homeTeamName;
    private Long awayTeamId;
    private String awayTeamName;
    private Integer fieldNumber;
    private Timestamp startDate;
    private Long winnerTeamId;
    private Long adminId;
    private int setNumber;

    public MatchDto() {}

    public MatchDto(Match match) {
        this.id = match.getId();
        if(match.getTournament() != null) {
            this.matchNumber = match.getMatchNumber();
            this.groupStage = match.getGroupStage();
            this.tournamentId = match.getTournament().getId();
            this.tournamentName = match.getTournament().getTournamentName();
            this.fieldNumber = match.getFieldNumber();
        }else{
            this.matchNumber = null;
            this.groupStage = null;
            this.tournamentId = null;
            this.tournamentName = null;
            this.fieldNumber = null;
        }
        if(match.getHomeTeam() != null){
            this.homeTeamId = match.getHomeTeam().getId();
            this.homeTeamName = match.getHomeTeam().getTeamName();
        } else {
            this.homeTeamId = null;
            this.homeTeamName = null;
        }
        if(match.getAwayTeam() != null){
            this.awayTeamId = match.getAwayTeam().getId();
            this.awayTeamName = match.getAwayTeam().getTeamName();
        } else {
            this.awayTeamId = null;
            this.awayTeamName = null;
        }
        this.adminId = match.getMatchAdmin().getId();
        this.matchType = match.getMatchType().getType();
        this.startDate = match.getStartDate();
        this.winnerTeamId = match.getWinnerTeam().getId();
        this.setNumber = match.getSets().size();
    }

    public Match fromDto(){
        Match match = new Match();
        match.setId(this.id);
        if(this.tournamentId != null){
            match.setMatchNumber(this.matchNumber);
            match.setGroupStage(this.groupStage);
            match.setTournament(new Tournament(this.tournamentId));
            match.setFieldNumber(this.fieldNumber);
        }

        match.setMatchType(new MatchType(this.matchType));

        match.setHomeTeam(new Team(this.homeTeamId));
        match.setAwayTeam(new Team(this.awayTeamId));

        if(this.winnerTeamId != null){
            match.setWinnerTeam(new Team(this.winnerTeamId));
        }

        match.setMatchAdmin(new User(this.adminId));
        return match;
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

    public void setMatchNumber(Integer matchNumber) {
        this.matchNumber = matchNumber;
    }

    public Integer getGroupStage() {
        return groupStage;
    }

    public void setGroupStage(Integer groupStage) {
        this.groupStage = groupStage;
    }

    public String getMatchType() {
        return matchType;
    }

    public void setMatchType(String matchType) {
        this.matchType = matchType;
    }

    public Long getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(Long tournamentId) {
        this.tournamentId = tournamentId;
    }

    public String getTournamentName() {
        return tournamentName;
    }

    public void setTournamentName(String tournamentName) {
        this.tournamentName = tournamentName;
    }

    public Long getHomeTeamId() {
        return homeTeamId;
    }

    public void setHomeTeamId(Long homeTeamId) {
        this.homeTeamId = homeTeamId;
    }

    public String getHomeTeamName() {
        return homeTeamName;
    }

    public void setHomeTeamName(String homeTeamName) {
        this.homeTeamName = homeTeamName;
    }

    public Long getAwayTeamId() {
        return awayTeamId;
    }

    public void setAwayTeamId(Long awayTeamId) {
        this.awayTeamId = awayTeamId;
    }

    public String getAwayTeamName() {
        return awayTeamName;
    }

    public void setAwayTeamName(String awayTeamName) {
        this.awayTeamName = awayTeamName;
    }

    public Integer getFieldNumber() {
        return fieldNumber;
    }

    public void setFieldNumber(Integer fieldNumber) {
        this.fieldNumber = fieldNumber;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Long getWinnerTeamId() {
        return winnerTeamId;
    }

    public void setWinnerTeamId(Long winnerTeamId) {
        this.winnerTeamId = winnerTeamId;
    }

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public int getSetNumber() {
        return setNumber;
    }

    public void setSetNumber(int setNumber) {
        this.setNumber = setNumber;
    }
}
