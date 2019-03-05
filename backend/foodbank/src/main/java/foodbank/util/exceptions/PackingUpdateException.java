package foodbank.util.exceptions;

public class PackingUpdateException extends RuntimeException {

	private static final long serialVersionUID = -6535127984577384652L;
	
	public PackingUpdateException(final String message) {
		super(message);
	}
	
	public PackingUpdateException(final String message, final Throwable throwable) {
		super(message, throwable);
	}

}
