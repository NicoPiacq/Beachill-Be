package it.beachill.model.exceptions;



public class TeamCheckFailedException extends Exception{
    private String message;

    public TeamCheckFailedException(String m){
        this.message = m;
    }

    public String getMessage() {
        return this.message;
    }
}
