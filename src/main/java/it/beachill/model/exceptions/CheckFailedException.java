package it.beachill.model.exceptions;

public class CheckFailedException extends Exception{
	private String message;
	
	public CheckFailedException(String m) {
		this.message = m;
	}
	
	public String getMessage() {
		return this.message;
	}
}
