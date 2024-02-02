package it.beachill.model.exceptions;

public class LoginChecksFailedExceptions extends Exception {

    private String message;

    public LoginChecksFailedExceptions(String m) {
        this.message = m;
    }

    public String getMessage() {
        return this.message;
    }

}
