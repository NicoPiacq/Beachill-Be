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
    private ReservationPlace reservationPlace;
    @Column(name="start_time")
    private LocalTime startTime;
    @Column(name="end_time")
    private LocalTime endTime;
    private Long duration;
    @Column(name="day_number")
    private int dayNumber;

    public ScheduleProp() {
    }

    public ScheduleProp(Long id, ReservationPlace reservationPlace, LocalTime startTime, LocalTime endTime, Long duration, int dayNumber) {
        this.id = id;
        this.reservationPlace = reservationPlace;
        this.startTime = startTime;
        this.endTime = endTime;
        this.duration = duration;
        this.dayNumber = dayNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ReservationPlace getPlace() {
        return reservationPlace;
    }

    public void setPlace(ReservationPlace reservationPlace) {
        this.reservationPlace = reservationPlace;
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

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }
}
