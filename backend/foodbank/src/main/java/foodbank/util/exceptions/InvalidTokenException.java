package foodbank.util.exceptions;

public class InvalidTokenException extends RuntimeException {

	private static final long serialVersionUID = -6926579695082047877L;

	public InvalidTokenException(final String message) {
		super(message);
	}
	
	public InvalidTokenException(final String message, final Throwable throwable) {
		super(message, throwable);
	}
	
}
