package it.beachill.dtos;

import it.beachill.model.entities.reservation.ReservationPlace;
import it.beachill.model.entities.reservation.ScheduleProp;

import java.time.LocalTime;

public class SchedulePropDto {
    private Long id;
    private Long placeId;
    private LocalTime startTime;
    private LocalTime endTime;
    private Long duration;
    private int dayNumber;

    public SchedulePropDto() {
    }

    public SchedulePropDto(ScheduleProp scheduleProp) {
        this.id = scheduleProp.getId();
        this.placeId = scheduleProp.getPlace().getId();
        this.startTime = scheduleProp.getStartTime();
        this.endTime = scheduleProp.getEndTime();
        this.duration = scheduleProp.getDuration();
        this.dayNumber = scheduleProp.getDayNumber();
    }

    public ScheduleProp fromDto(){
        ScheduleProp scheduleProp = new ScheduleProp();
        scheduleProp.setId(this.id);
        scheduleProp.setPlace(new ReservationPlace(this.placeId));
        scheduleProp.setStartTime(this.startTime);
        scheduleProp.setEndTime(this.endTime);
        scheduleProp.setDuration(this.duration);
        scheduleProp.setDayNumber(this.dayNumber);
        return scheduleProp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPlaceId() {
        return placeId;
    }

    public void setPlaceId(Long placeId) {
        this.placeId = placeId;
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

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public int getDayNumber() {
        return dayNumber;
    }

    public void setDayNumber(int dayNumber) {
        this.dayNumber = dayNumber;
    }
}
