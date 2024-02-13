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
    @ManyToOne
    @JoinColumn(name = "player_id")
    Player player;

    public Score() {
    }

    public Score(ScoreType scoreType, Player player) {
        this.scoreType = scoreType;
        this.player = player;
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
    
    
}
