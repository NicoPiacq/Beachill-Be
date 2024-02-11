package it.beachill.dtos;

import it.beachill.model.entities.reservation.Field;
import it.beachill.model.entities.reservation.ReservationPlace;
import it.beachill.model.entities.reservation.Reservation;
import it.beachill.model.entities.user.User;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationDto {
    private Long id;
    private FieldDto fieldDto;
    private Long userId;
    private LocalDate date;
    private LocalTime start;
    private LocalTime end;

    public ReservationDto() {
    }

    public ReservationDto(Reservation reservation) {
        this.id = reservation.getId();
        this.fieldDto = new FieldDto(reservation.getField());
        this.userId = reservation.getUser().getId();
        this.date = reservation.getDate();
        this.start = reservation.getStart();
        this.end = reservation.getEnd();
    }

    public Reservation fromDto(){
        Reservation reservation = new Reservation();
        reservation.setId(this.id);
        reservation.setField(this.fieldDto.fromDto());
        reservation.setUser(new User(userId));
        reservation.setDate(this.date);
        reservation.setStart(this.start);
        reservation.setEnd(this.end);
        return  reservation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FieldDto getFieldDto() {
        return fieldDto;
    }

    public void setFieldDto(FieldDto fieldDto) {
        this.fieldDto = fieldDto;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStart() {
        return start;
    }

    public void setStart(LocalTime start) {
        this.start = start;
    }

    public LocalTime getEnd() {
        return end;
    }

    public void setEnd(LocalTime end) {
        this.end = end;
    }
}
