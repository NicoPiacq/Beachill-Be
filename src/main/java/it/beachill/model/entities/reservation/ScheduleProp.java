package it.beachill.model.entities.reservation;
import jakarta.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name = "schedule_prop", schema = "reservation")
public class ScheduleProp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="place_id")
    private Place place;
    @Column(name="start_time")
    private LocalTime startTime;
    @Column(name="end_time")
    private LocalTime endTime;
    @Column(name="day_number")
    private int dayNumber;

    public ScheduleProp() {
    }

    public ScheduleProp(Long id, Place place, LocalTime startTime, LocalTime endTime, int dayNumber) {
        this.id = id;
        this.place = place;
        this.startTime = startTime;
        this.endTime = endTime;
        this.dayNumber = dayNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
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

    public int getDayNumber() {
        return dayNumber;
    }

    public void setDayNumber(int dayNumber) {
        this.dayNumber = dayNumber;
    }
}
