package it.beachill.dtos;

import it.beachill.model.entities.reservation.ReservationPlace;
import it.beachill.model.entities.reservation.Sport;
import it.beachill.model.entities.user.User;

public class ReservationPlaceDto {
    private Long id;
    private String name;
    private String address;
    private Long managerId;
    private String sportName;
    private int fieldNumber;

    public ReservationPlaceDto() {
    }

    public ReservationPlaceDto(ReservationPlace reservationPlace) {
        this.id = reservationPlace.getId();
        this.name = reservationPlace.getName();
        this.address = reservationPlace.getAddress();
        this.managerId = reservationPlace.getManager().getId();
        this.sportName = reservationPlace.getSport().getSportName();
        this.fieldNumber = reservationPlace.getFieldNumber();
    }

    public ReservationPlace fromDto(){
        ReservationPlace reservationPlace = new ReservationPlace();
        reservationPlace.setId(this.id);
        reservationPlace.setName(this.name);
        reservationPlace.setAddress(this.address);
        reservationPlace.setManager(new User(this.managerId));
        reservationPlace.setSport(new Sport(this.sportName));
        reservationPlace.setFieldNumber(this.fieldNumber);
        return reservationPlace;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getManagerId() {
        return managerId;
    }

    public void setManagerId(Long managerId) {
        this.managerId = managerId;
    }

    public String getSportName() {
        return sportName;
    }

    public void setSportName(String sportName) {
        this.sportName = sportName;
    }

    public int getFieldNumber() {
        return fieldNumber;
    }

    public void setFieldNumber(int fieldNumber) {
        this.fieldNumber = fieldNumber;
    }
}
