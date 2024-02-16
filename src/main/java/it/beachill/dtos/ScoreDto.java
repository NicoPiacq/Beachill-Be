package it.beachill.dtos;

import it.beachill.model.entities.tournament.Score;

public class ScoreDto {
    private Long id;
    private int score;
    private String scoreType;

    private int matchWin;
    private int matchLose;

    private int pointScored;

    private int pointConceded;

    private Long playerId;
    private String playerName;
    private String playerSurname;

    public ScoreDto() {
    }

    public ScoreDto(Score score) {
        this.id = score.getId();
        this.score = score.getScore();
        this.scoreType = score.getScoreType().getName();
        this.playerId = score.getPlayer().getId();
        this.playerName = score.getPlayer().getUser().getName();
        this.playerSurname = score.getPlayer().getUser().getSurname();
        this.matchWin = score.getMatchWin();
        this.matchLose = score.getMatchLose();
        this.pointScored = score.getPointScored();
        this.pointConceded = score.getPointConceded();
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
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    public int getMatchWin() {
        return matchWin;
    }

    public void setMatchWin(int matchWin) {
        this.matchWin = matchWin;
    }

    public int getMatchLose() {
        return matchLose;
    }

    public void setMatchLose(int matchLose) {
        this.matchLose = matchLose;
    }

    public int getPointScored() {
        return pointScored;
    }

    public void setPointScored(int pointScored) {
        this.pointScored = pointScored;
    }

    public int getPointConceded() {
        return pointConceded;
    }

    public void setPointConceded(int pointConceded) {
        this.pointConceded = pointConceded;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerSurname() {
        return playerSurname;
    }

    public void setPlayerSurname(String playerSurname) {
        this.playerSurname = playerSurname;
    }
}
