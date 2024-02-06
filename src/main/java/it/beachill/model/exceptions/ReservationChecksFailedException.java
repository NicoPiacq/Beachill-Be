package it.beachill.model.exceptions;

public class ReservationChecksFailedException extends Exception{
    private String message;

    public ReservationChecksFailedException(String m) {
        this.message = m;
    }

    public String getMessage() {
        return this.message;
    }

}
