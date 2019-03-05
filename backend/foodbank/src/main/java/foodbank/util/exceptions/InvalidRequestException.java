package foodbank.util.exceptions;

public class InvalidRequestException extends RuntimeException {

	private static final long serialVersionUID = -1768025838218624212L;
	
	public InvalidRequestException(final String message) {
		super(message);
	}
	
	public InvalidRequestException(final String message, final Throwable throwable) {
		super(message, throwable);
	}

}
