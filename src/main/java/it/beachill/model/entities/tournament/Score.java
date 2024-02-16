package it.beachill.model.entities.tournament;

import jakarta.persistence.*;

@Entity
@Table(name = "score", schema = "tournament")
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int score;
    @ManyToOne
    @JoinColumn(name = "score_type")
    ScoreType scoreType;
    @Column(name = "match_win")
    private int matchWin;
    @Column(name = "match_lose")
    private int matchLose;
    @Column(name = "point_scored")
    private int pointScored;
    @Column(name = "point_conceded")
    private int pointConceded;
    @ManyToOne
    @JoinColumn(name = "player_id")
    Player player;

    public Score() {
    }

    public Score(ScoreType scoreType, Player player, int score) {
        this.scoreType = scoreType;
        this.player = player;
        this.score = score;
    }

    public Score(Long id, int score, ScoreType scoreType, int matchWin, int matchLose, int pointScored, int pointConceded, Player player) {
        this.id = id;
        this.score = score;
        this.scoreType = scoreType;
        this.matchWin = matchWin;
        this.matchLose = matchLose;
        this.pointScored = pointScored;
        this.pointConceded = pointConceded;
        this.player = player;
    }

    public int addMatchWin(){
        this.matchWin++;
        return matchWin;
    }

    public int addMatchLose(){
        this.matchLose++;
        return matchLose;
    }

    public int addPointScored(int points){
        this.pointScored += points;
        return this.pointScored;
    }

    public int addPointConced(int points){
        this.pointConceded += points;
        return this.pointConceded;
    }

    public int addScore(int score){
        this.score+=score;
        return this.score;
    }
    public int subtractScore(int score){
        this.score-=score;
        return this.score;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ScoreType getScoreType() {
        return scoreType;
    }

    public void setScoreType(ScoreType scoreType) {
        this.scoreType = scoreType;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
    
    public int getScore() {
        return score;
    }
    
    public void setScore(int score) {
        this.score = score;
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
}
