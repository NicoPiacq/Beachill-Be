package it.beachill.dtos;

import it.beachill.model.entities.tournament.Score;

public class ScoreDto {
    private Long id;
    private int score;
    private String scoreType;
    private Long PlayerId;

    public ScoreDto() {
    }

    public ScoreDto(Score score) {
        this.id = score.getId();
        this.score = score.getScore();
        this.scoreType = score.getScoreType().getName();
        PlayerId = score.getPlayer().getId();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getScoreType() {
        return scoreType;
    }

    public void setScoreType(String scoreType) {
        this.scoreType = scoreType;
    }

    public Long getPlayerId() {
        return PlayerId;
    }

    public void setPlayerId(Long playerId) {
        PlayerId = playerId;
    }
}
