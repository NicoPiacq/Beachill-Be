package it.beachill.dtos;

import it.beachill.model.entities.TournamentType;

public class TournamentTypeDto {

    private String tournamentTypeName;
    private String tournamentDescription;

    public TournamentTypeDto() {}

    public TournamentTypeDto(String tournamentTypeName, String tournamentDescription) {
        this.tournamentTypeName = tournamentTypeName;
        this.tournamentDescription = tournamentDescription;
    }
    public TournamentTypeDto(TournamentType tournamentType){
        this.tournamentTypeName = tournamentType.getTournamentTypeName();
        this.tournamentDescription = tournamentType.getTournamentDescription();
    }

    public String getTournamentTypeName() {
        return tournamentTypeName;
    }

    public void setTournamentTypeName(String tournamentTypeName) {
        this.tournamentTypeName = tournamentTypeName;
    }

    public String getTournamentDescription() {
        return tournamentDescription;
    }

    public void setTournamentDescription(String tournamentDescription) {
        this.tournamentDescription = tournamentDescription;
    }
}
