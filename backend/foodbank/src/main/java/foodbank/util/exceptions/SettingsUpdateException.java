package foodbank.util.exceptions;

public class SettingsUpdateException extends RuntimeException {

	private static final long serialVersionUID = -4392626771229921441L;
	
	public SettingsUpdateException(final String message) {
		super(message);
	}
	
	public SettingsUpdateException(final String message, final Throwable throwable) {
		super(message, throwable);
	}

}
