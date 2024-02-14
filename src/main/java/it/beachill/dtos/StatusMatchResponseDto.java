package it.beachill.dtos;

public class StatusMatchResponseDto {
    private Long matchId;
    private Integer status;

    public StatusMatchResponseDto() {
    }

    public Long getMatchId() {
        return matchId;
    }

    public void setMatchId(Long matchId) {
        this.matchId = matchId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
