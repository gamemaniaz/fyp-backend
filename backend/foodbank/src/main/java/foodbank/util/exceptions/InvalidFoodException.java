package foodbank.util.exceptions;

public class InvalidFoodException extends RuntimeException {

	private static final long serialVersionUID = 1006041470024639157L;
	
	public InvalidFoodException(final String message) {
		super(message);
	}
	
	public InvalidFoodException(final String message, final Throwable throwable) {
		super(message, throwable);
	}

}
