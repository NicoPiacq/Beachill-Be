package it.beachill.dtos;

import it.beachill.model.entities.tournament.Tournament;
import it.beachill.model.entities.tournament.TournamentPlace;
import it.beachill.model.entities.tournament.TournamentType;

import java.sql.Timestamp;
import java.time.LocalDate;

public class TournamentDto {
    private Long id;
    private String tournamentName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String tournamentTypeName;
    private String place;


    public TournamentDto() {}

    public TournamentDto(Tournament tournament){
        this.id = tournament.getId();
        this.tournamentName = tournament.getTournamentName();
        if(tournament.getStartDate() == null) {
            this.startDate = null;
        } else {
            this.startDate = tournament.getStartDate();
        }
        if(tournament.getEndDate() == null) {
            this.endDate = null;
        } else {
            this.endDate = tournament.getEndDate();
        }

        if(tournament.getTournamentType() == null){
            this.tournamentTypeName = "No Type";
        } else {
            this.tournamentTypeName = tournament.getTournamentType().getTournamentTypeName();
        }
        if(tournament.getPlace() == null){
            this.place = "No Place";
        } else {
            this.place = tournament.getPlace().getPlace();
        }
    }

    public Tournament fromDto(){
        Tournament tournament = new Tournament();
        tournament.setTournamentName(this.tournamentName);
        // da implementare la creazione di un place da stringa(forse devo solo cercarlo sul db
        // o magari creare un dto apposta solo per place e uno per add tournament with place)
        // new oggetto place
        if(this.place == ""){       //se gli passo la stringa vuota (non ha selezionato il place) allora mi mette null
            tournament.setPlace(null);
        } else {
            tournament.setPlace(new TournamentPlace(this.place));
        }
        if(this.tournamentTypeName == ""){
            tournament.setTournamentType(null);
        } else {
            tournament.setTournamentType(new TournamentType(this.tournamentTypeName));
        }
        tournament.setStartDate(this.startDate);
        tournament.setEndDate(this.endDate);
        return tournament;
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

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
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
