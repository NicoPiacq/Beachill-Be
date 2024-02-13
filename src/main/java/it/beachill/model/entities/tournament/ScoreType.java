package it.beachill.model.entities.tournament;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "domain_type_score", schema = "tournament")
public class ScoreType {
    @Id
    private String name;
    private String description;
    @Column(name = "base_win_score")
    private int baseWinScore;

    @OneToMany(mappedBy = "scoreType")
    List<Score> scoreList;

    public ScoreType() {
    }

    public ScoreType(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Score> getScoreList() {
        return scoreList;
    }

    public void setScoreList(List<Score> scoreList) {
        this.scoreList = scoreList;
    }
    
    public int getBaseWinScore() {
        return baseWinScore;
    }
}
