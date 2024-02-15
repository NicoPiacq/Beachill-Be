package it.beachill.dtos;

import it.beachill.model.entities.reservation.ReservationPlace;
import it.beachill.model.entities.reservation.Sport;
import it.beachill.model.entities.user.User;

public class ReservationPlaceDto {
    private Long id;
    private String name;
    private String address;
    private String city;
    private String province;
    private String region;
    private Long managerId;

    public ReservationPlaceDto() {
    }

    public ReservationPlaceDto(ReservationPlace reservationPlace) {
        this.id = reservationPlace.getId();
        this.name = reservationPlace.getName();
        this.address = reservationPlace.getAddress();
        this.managerId = reservationPlace.getManager().getId();
        this.city = reservationPlace.getCity();
        this.province = reservationPlace.getProvince();
        this.region = reservationPlace.getRegion();
    }

    public ReservationPlace fromDto(){
        ReservationPlace reservationPlace = new ReservationPlace();
        if(this.id != null) {
            reservationPlace.setId(this.id);
        }
        reservationPlace.setName(this.name);
        reservationPlace.setAddress(this.address);
        reservationPlace.setManager(new User(this.managerId));
        reservationPlace.setCity(this.city);
        reservationPlace.setRegion(this.region);
        reservationPlace.setProvince(this.province);
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}
