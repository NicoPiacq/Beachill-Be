package it.beachill.dtos;

import it.beachill.model.entities.tournament.Tournament;
import it.beachill.model.entities.tournament.TournamentLevel;
import it.beachill.model.entities.tournament.TournamentPlace;
import it.beachill.model.entities.tournament.TournamentType;
import it.beachill.model.entities.user.User;

import java.sql.Timestamp;
import java.util.Objects;

public class TournamentAdminDto {
    private Long id;
    private String tournamentName;
    private Timestamp startDate;
    private Timestamp endDate;
    private String tournamentTypeName;
    private String place;
    private Integer status;
    private UserDto userDto;
    private String levelName;


    public TournamentAdminDto() {}

    public TournamentAdminDto(Tournament tournament){
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
        if(tournament.getTournamentLevel() != null){
            this.levelName = tournament.getTournamentLevel().getLevelName();
        }
        this.status= tournament.getStatus();
        this.userDto= new UserDto(tournament.getManager());
    }

    public Tournament fromDto(){
        Tournament tournament = new Tournament();
        tournament.setTournamentName(this.tournamentName);

        if(Objects.equals(this.place, "")){       //se gli passo la stringa vuota (non ha selezionato il place) allora mi mette null
            tournament.setPlace(null);
        } else {
            tournament.setPlace(new TournamentPlace(this.place));
        }
        if(Objects.equals(this.tournamentTypeName, "")){
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
            tournament.setManager(null);
        } else {
            tournament.setManager(new User(this.userDto.getId()));
        }
        tournament.setStartDate(this.startDate);
        tournament.setEndDate(this.endDate);

        tournament.setTournamentLevel(new TournamentLevel(this.levelName));
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

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }
}
