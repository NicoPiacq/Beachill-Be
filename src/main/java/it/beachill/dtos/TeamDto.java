package it.beachill.dtos;

import it.beachill.model.entities.Team;

public class TeamDto {
    private Long id;
    private String teamName;

    private String teamLeader;
    private Integer score;

    public TeamDto() {}

    public TeamDto(Team team) {
        this.id = team.getId();
        this.teamName = team.getTeamName();
        this.teamLeader = team.getTeamLeader().getNickname();
        this.score = team.getScore();
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

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
