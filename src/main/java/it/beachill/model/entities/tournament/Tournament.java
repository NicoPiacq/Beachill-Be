package it.beachill.model.entities.tournament;

import it.beachill.model.entities.user.User;
import jakarta.persistence.*;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "tournament", schema = "tournament")
public class Tournament {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "tournament_name", nullable = false)
    private String tournamentName;
    @Column(name = "start_date")
    private LocalDate startDate;
    @Column(name = "end_date")
    private LocalDate endDate;
    @ManyToOne
    @JoinColumn(name = "tournament_type")
    private TournamentType tournamentType;
    @ManyToOne
    @JoinColumn(name = "place")
    private TournamentPlace place;
    @ManyToOne
    @JoinColumn(name = "tournament_level")
    private TournamentLevel tournamentLevel;
    @ManyToOne
    @JoinColumn(name="_user")
    private User manager;
    private Integer status;

    @OneToMany(mappedBy = "tournament")
    private List<GroupStageStanding> groupStageStandingList;
    @OneToMany(mappedBy = "tournament")
    private List<Match> matches;
    @OneToMany(mappedBy = "tournament")
    private List<PizzaOrder> pizzaOrders;
    @OneToMany(mappedBy = "tournament")
    private List<TeamInTournament> enrolledTeams;



    public Tournament() {}

    public Tournament(Long id) {
        this.id = id;
    }

    public Tournament(Long id, String tournamentName, LocalDate startDate,
                      LocalDate endDate, TournamentType tournamentType, TournamentPlace place) {
        this.id = id;
        this.tournamentName = tournamentName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.tournamentType = tournamentType;
        this.place = place;
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

    public TournamentType getTournamentType() {
        return tournamentType;
    }

    public void setTournamentType(TournamentType tournamentType) {
        this.tournamentType = tournamentType;
    }

    public TournamentPlace getPlace() {
        return place;
    }

    public void setPlace(TournamentPlace place) {
        this.place = place;
    }

    public List<Match> getMatches() {
        return matches;
    }

    public void setMatches(List<Match> matches) {
        this.matches = matches;
    }

    public List<PizzaOrder> getPizzaOrders() {
        return pizzaOrders;
    }

    public void setPizzaOrders(List<PizzaOrder> pizzaOrders) {
        this.pizzaOrders = pizzaOrders;
    }

    public List<TeamInTournament> getEnrolledTeams() {
        return enrolledTeams;
    }

    public void setEnrolledTeams(List<TeamInTournament> enrolledTeams) {
        this.enrolledTeams = enrolledTeams;
    }

    public List<GroupStageStanding> getGroupStageStandingList() {
        return groupStageStandingList;
    }

    public void setGroupStageStandingList(List<GroupStageStanding> groupStageStandingList) {
        this.groupStageStandingList = groupStageStandingList;
    }
    
    public User getManager() {
        return manager;
    }
    
    public void setManager(User manager) {
        this.manager = manager;
    }
    
    public Integer getStatus() {
        return status;
    }
    
    public void setStatus(Integer status) {
        this.status = status;
    }

    public TournamentLevel getTournamentLevel() {
        return tournamentLevel;
    }

    public void setTournamentLevel(TournamentLevel tournamentLevel) {
        this.tournamentLevel = tournamentLevel;
    }

}
