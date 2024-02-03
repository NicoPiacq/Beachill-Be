package it.beachill.dtos;

import it.beachill.model.entities.Tournament;
import it.beachill.model.entities.TournamentPlace;
import it.beachill.model.entities.TournamentType;
import it.beachill.model.entities.User;

import java.sql.Timestamp;

public class TournamentDto {
    private Long id;
    private String tournamentName;
    private Timestamp startDate;
    private Timestamp endDate;
    private String tournamentTypeName;
    private String place;
    private Integer status;
    private UserDto userDto;


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
            this.endDate = tournament.getStartDate();
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
        this.status= tournament.getStatus();
        this.userDto= new UserDto(tournament.getUser());
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
        if(this.status==null){
            tournament.setStatus(null);
        } else {
            tournament.setStatus(this.status);
        }
        if(this.userDto==null){
            tournament.setUser(null);
        } else {
            tournament.setUser(new User(this.userDto.getId()));
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
    
    public Integer getStatus() {
        return status;
    }
    
    public void setStatus(Integer status) {
        this.status = status;
    }
    
    public UserDto getUserDto() {
        return userDto;
    }
    
    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }
}
