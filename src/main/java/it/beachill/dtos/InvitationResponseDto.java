package it.beachill.dtos;

public class InvitationResponseDto {
    private Long teamId;
    private Long teamComponentId;
    private Integer status;

    public InvitationResponseDto() {
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
