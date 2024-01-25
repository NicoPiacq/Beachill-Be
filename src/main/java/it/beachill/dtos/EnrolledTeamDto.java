package it.beachill.dtos;

import it.beachill.model.entities.Team;
import it.beachill.model.entities.TeamInTournament;

public class EnrolledTeamDto {
    private Long id;
    private Integer round;
    private Long teamId;
    private String teamName;
    private Long tournamentId;
    private String tournamentName;

    public EnrolledTeamDto() {}

    public EnrolledTeamDto(TeamInTournament teamInTournament) {
        this.id = teamInTournament.getId();
        if(teamInTournament.getRound() != null) {
            this.round = teamInTournament.getRound();
        } else {
            this.round=null;
        }
        
        /*this.teacherSummary = editionModule.getTeacher() == null ?
                new TeacherSummaryDto()
                : new TeacherSummaryDto(editionModule.getTeacher());*/
        this.teamId = teamInTournament.getTeam().getId();
        this.teamName = teamInTournament.getTeam().getTeamName();
        this.tournamentId = teamInTournament.getTournament().getId();
        this.tournamentName = teamInTournament.getTournament().getTournamentName();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRound() {
        return round;
    }

    public void setRound(Integer round) {
        this.round = round;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
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
}
