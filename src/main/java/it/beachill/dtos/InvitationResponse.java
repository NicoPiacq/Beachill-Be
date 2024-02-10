package it.beachill.dtos;

public class InvitationResponse {
    private Long teamId;
    private Long teamComponentId;
    private Integer status;

    public InvitationResponse() {
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public Long getTeamComponentId() {
        return teamComponentId;
    }

    public void setTeamComponentId(Long teamComponentId) {
        this.teamComponentId = teamComponentId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
