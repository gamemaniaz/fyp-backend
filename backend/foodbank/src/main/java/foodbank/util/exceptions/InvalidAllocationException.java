package foodbank.util.exceptions;

public class InvalidAllocationException extends RuntimeException {

	private static final long serialVersionUID = 6298278410995351125L;
	
	public InvalidAllocationException(final String message) {
		super(message);
	}
	
	public InvalidAllocationException(final String message, final Throwable throwable) {
		super(message, throwable);
	}

}
