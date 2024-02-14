package it.beachill.dtos;

public class InvitePlayerRequestDto {
    private Long teamId;
    private Long playerToAddId;
    private Long requestingPlayerId;

    public InvitePlayerRequestDto() {
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public Long getPlayerToAddId() {
        return playerToAddId;
    }

    public void setPlayerToAddId(Long playerToAddId) {
        this.playerToAddId = playerToAddId;
    }

    public Long getRequestingPlayerId() {
        return requestingPlayerId;
    }

    public void setRequestingPlayerId(Long requestingPlayerId) {
        this.requestingPlayerId = requestingPlayerId;
    }
}
