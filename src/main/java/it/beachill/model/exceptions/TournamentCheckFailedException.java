package it.beachill.model.exceptions;

public class TournamentCheckFailedException extends Throwable {
	private String message;
	
	public TournamentCheckFailedException(String m){
		this.message = m;
	}
	
	public String getMessage() {
		return this.message;
	}
}
