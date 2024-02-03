package it.beachill.model.exceptions;

public class PlayerChecksFailedException extends Throwable {
    private String message;

    public PlayerChecksFailedException(String message) {
        this.message = message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
