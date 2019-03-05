package foodbank.util.exceptions;

public class InvalidLoginException extends RuntimeException {

	private static final long serialVersionUID = 668949096563560617L;
	
	public InvalidLoginException(final String message) {
		super(message);
	}
	
	public InvalidLoginException(final String message, final Throwable throwable) {
		super(message, throwable);
	}
	
}
