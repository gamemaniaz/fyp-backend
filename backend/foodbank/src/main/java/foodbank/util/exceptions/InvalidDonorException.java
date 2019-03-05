package foodbank.util.exceptions;

public class InvalidDonorException extends RuntimeException {

	private static final long serialVersionUID = -4291224438823963994L;

	public InvalidDonorException(final String message) {
		super(message);
	}
	
	public InvalidDonorException(final String message, final Throwable throwable) {
		super(message, throwable);
	}
	
}
