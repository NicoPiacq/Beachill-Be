package it.beachill.dtos;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationSlotsDto {

    private Long fieldId;
    private LocalTime startTime;
    private LocalTime endTime;
    private boolean reserved;
    private LocalDate reservationDate;
    private String userName;
    private String userSurname;
    private Long userId;

    public ReservationSlotsDto() {
    }

    public ReservationSlotsDto(Long fieldId, LocalTime startTime, LocalTime endTime, boolean reserved, LocalDate reservationDate) {
        this.fieldId = fieldId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.reserved = reserved;
        this.reservationDate = reservationDate;
    }

    public ReservationSlotsDto(Long fieldId, LocalTime startTime, LocalTime endTime, boolean reserved, LocalDate reservationDate, String userName, String userSurname, Long userId) {
        this.fieldId = fieldId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.reserved = reserved;
        this.reservationDate = reservationDate;
        this.userName = userName;
        this.userSurname = userSurname;
        this.userId = userId;
    }

    public Long getFieldId() {
        return fieldId;
    }

    public void setFieldId(Long fieldId) {
        this.fieldId = fieldId;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public boolean isReserved() {
        return reserved;
    }

    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }

    public LocalDate getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(LocalDate reservationDate) {
        this.reservationDate = reservationDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserSurname() {
        return userSurname;
    }

    public void setUserSurname(String userSurname) {
        this.userSurname = userSurname;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
