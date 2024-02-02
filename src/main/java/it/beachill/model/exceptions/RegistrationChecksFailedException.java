package it.beachill.model.exceptions;

public class RegistrationChecksFailedException extends Exception {

    private String message;

    public RegistrationChecksFailedException(String m) {
        this.message = m;
    }

    public String getMessage() {
        return this.message;
    }

}
