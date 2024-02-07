package it.beachill.dtos;

import it.beachill.model.entities.tournament.TournamentPlace;

public class TournamentPlaceDto {
    private String place;
    private String address;
    private int fieldNumber;

    public TournamentPlaceDto() {}

    public TournamentPlaceDto(String place, String address, int fieldNumber) {
        this.place = place;
        this.address = address;
        this.fieldNumber = fieldNumber;
    }
    public TournamentPlaceDto(TournamentPlace place) {
        this.place = place.getPlace();
        this.address = place.getAddress();
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getFieldNumber() {
        return fieldNumber;
    }

    public void setFieldNumber(int fieldNumber) {
        this.fieldNumber = fieldNumber;
    }
}
