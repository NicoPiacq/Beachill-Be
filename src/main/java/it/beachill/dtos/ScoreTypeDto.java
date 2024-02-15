package it.beachill.dtos;

import it.beachill.model.entities.tournament.ScoreType;

public class ScoreTypeDto {
    private String name;
    private String description;

    public ScoreTypeDto() {
    }

    public ScoreTypeDto(ScoreType scoreType) {
        this.name = scoreType.getName();
        this.description = scoreType.getDescription();
    }

    public ScoreType fromDto(){
        return new ScoreType(this.name);
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
}
