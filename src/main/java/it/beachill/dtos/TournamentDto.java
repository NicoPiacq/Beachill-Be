package it.beachill.dtos;

import it.beachill.model.entities.Tournament;

import java.sql.Timestamp;

public class TournamentDto {
    private Long id;
    private String tournamentName;
    private Timestamp startDate;
    private Timestamp endDate;
    private String tournamentTypeName;
    private String place;


    public TournamentDto() {}

    public TournamentDto(Tournament tournament){
        this.id = tournament.getId();
        this.tournamentName = tournament.getTournamentName();
        this.startDate = tournament.getStartDate();
        this.endDate = tournament.getEndDate();
        this.tournamentTypeName = tournament.getTournamentType().getTournamentTypeName();
        if(tournament.getPlace() == null){
            this.place = null;
        } else {
            this.place = tournament.getPlace().getPlace();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTournamentName() {
        return tournamentName;
    }

    public void setTournamentName(String tournamentName) {
        this.tournamentName = tournamentName;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    public String getTournamentTypeName() {
        return tournamentTypeName;
    }

    public void setTournamentTypeName(String tournamentTypeName) {
        this.tournamentTypeName = tournamentTypeName;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}
