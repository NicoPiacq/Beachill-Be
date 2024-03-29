package it.beachill.model.entities.user;

import it.beachill.dtos.RegistrationDto;
import it.beachill.dtos.UserDto;
import it.beachill.model.entities.reservation.ReservationPlace;
import it.beachill.model.entities.reservation.Reservation;
import it.beachill.model.entities.tournament.Match;
import it.beachill.model.entities.tournament.Player;
import it.beachill.model.entities.tournament.Tournament;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "_user", schema = "user_util")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "registration_date")
    private LocalDate registrationDate;
    @Column(name = "last_login")
    private LocalDate lastLogin;
    @OneToOne
    @JoinColumn(name = "player_id")
    private Player player;
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToMany(mappedBy = "manager")
    private List<Tournament> tournamentsManagerList;
    @OneToMany(mappedBy = "manager")
    private List<ReservationPlace> managerReservationPlaces;
    @OneToMany(mappedBy = "user")
    private List<Reservation> reservations;
    @OneToMany(mappedBy = "matchAdmin")
    private List<Match> matchesAdminBy;

    public User() {}
    
    public User(Long id) {
        this.id = id;
    }
    
    public User(RegistrationDto data, String encryptedPsw) {
        this.name = data.getName();
        this.surname = data.getSurname();
        this.email = data.getEmail();
        this.password = encryptedPsw;
        this.role = data.getRole();
    }
    public User(UserDto userDto){
        this.id=userDto.getId();
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public LocalDate getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDate lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
    
    public List<Tournament> getTournamentsManagerList() {
        return tournamentsManagerList;
    }
    
    public void setTournamentsManagerList(List<Tournament> tournamentsManagerList) {
        this.tournamentsManagerList = tournamentsManagerList;
    }

    public List<ReservationPlace> getManagerPlaces() {
        return managerReservationPlaces;
    }

    public void setManagerPlaces(List<ReservationPlace> managerReservationPlaces) {
        this.managerReservationPlaces = managerReservationPlaces;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }
    
    public List<ReservationPlace> getManagerReservationPlaces() {
        return managerReservationPlaces;
    }
    
    public void setManagerReservationPlaces(List<ReservationPlace> managerReservationPlaces) {
        this.managerReservationPlaces = managerReservationPlaces;
    }
    
    public List<Match> getMatchesAdminBy() {
        return matchesAdminBy;
    }
    
    public void setMatchesAdminBy(List<Match> matchesAdminBy) {
        this.matchesAdminBy = matchesAdminBy;
    }
}
