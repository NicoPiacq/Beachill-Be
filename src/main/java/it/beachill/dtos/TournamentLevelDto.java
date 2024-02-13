package it.beachill.dtos;

import it.beachill.model.entities.tournament.TournamentLevel;

public class TournamentLevelDto {
    private String levelName;

    public TournamentLevelDto() {
    }

    public TournamentLevelDto(String levelName) {
        this.levelName = levelName;
    }

    public TournamentLevelDto(TournamentLevel tournamentLevel) {
        this.levelName = tournamentLevel.getLevelName();
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }
}
