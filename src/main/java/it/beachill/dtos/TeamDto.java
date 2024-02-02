package it.beachill.dtos;

import it.beachill.model.entities.*;

public class TeamDto {
    private Long id;
    private String teamName;
    private String teamLeaderName;
    private String teamLeaderSurname;
    private Long idTeamLeader;
    private Long score;

    public TeamDto() {}

    public TeamDto(Team team) {
        this.id = team.getId();
        this.teamName = team.getTeamName();
        this.teamLeaderName = team.getTeamLeader().getUser().getName();
        this.teamLeaderSurname = team.getTeamLeader().getUser().getSurname();
        this.idTeamLeader=team.getTeamLeader().getId();
        this.score = team.getScore();
    }
    
    public Team fromDto(){
        Team team = new Team();
        team.setTeamLeader(new Player(this.idTeamLeader));
        team.setTeamName(this.teamName);
        team.setScore(this.score);
        return team;
    }
    
    public Long getIdTeamLeader() {
        return idTeamLeader;
    }
    
    public void setIdTeamLeader(Long idTeamLeader) {
        this.idTeamLeader = idTeamLeader;
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

    public String getTeamLeaderName() {
        return teamLeaderName;
    }

    public void setTeamLeaderName(String teamLeaderName) {
        this.teamLeaderName = teamLeaderName;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    public String getTeamLeaderSurname() {
        return teamLeaderSurname;
    }

    public void setTeamLeaderSurname(String teamLeaderSurname) {
        this.teamLeaderSurname = teamLeaderSurname;
    }
}
