package it.beachill.dtos;

import it.beachill.model.entities.*;

public class TeamDto {
    private Long id;
    private String teamName;
    private String teamLeader;
    private Long idTeamLeader;
    private Long score;

    public TeamDto() {}

    public TeamDto(Team team) {
        this.id = team.getId();
        this.teamName = team.getTeamName();
        this.teamLeader = team.getTeamLeader().getNickname();
        this.idTeamLeader=team.getTeamLeader().getId();
        this.score = team.getScore();
    }
    
    public Team fromDto(){
        Team team = new Team();
        team.setTeamName(this.teamName);
        team.setTeamLeader(new Player(this.idTeamLeader));
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

    public String getTeamLeader() {
        return teamLeader;
    }

    public void setTeamLeader(String teamLeader) {
        this.teamLeader = teamLeader;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }
}
