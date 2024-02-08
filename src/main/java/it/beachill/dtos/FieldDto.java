package it.beachill.dtos;

import it.beachill.model.entities.reservation.Field;
import it.beachill.model.entities.reservation.ReservationPlace;
import it.beachill.model.entities.reservation.Sport;

public class FieldDto {

    private Long id;
    private String sportName;
    private ReservationPlaceDto reservationPlace;

    public FieldDto() {
    }

    public FieldDto(Field field) {
        this.id = field.getId();
        this.sportName = field.getSport().getSportName();
        this.reservationPlace = new ReservationPlaceDto(field.getReservationPlace());
    }

    public Field fromDto() {
        Field field = new Field();
        field.setId(this.id);
        field.setSport(new Sport(this.sportName));
        field.setReservationPlace(this.reservationPlace.fromDto());
        return field;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSportName() {
        return sportName;
    }

    public void setSportName(String sportName) {
        this.sportName = sportName;
    }

    public ReservationPlaceDto getReservationPlace() {
        return reservationPlace;
    }

    public void setReservationPlace(ReservationPlaceDto reservationPlace) {
        this.reservationPlace = reservationPlace;
    }
}
