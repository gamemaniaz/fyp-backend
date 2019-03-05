package foodbank.util.exceptions;

public class InvalidBeneficiaryException extends RuntimeException {

	private static final long serialVersionUID = 5925637279554833331L;

	public InvalidBeneficiaryException(final String message) {
		super(message);
	}
	
	public InvalidBeneficiaryException(final String message, final Throwable throwable) {
		super(message, throwable);
	}
	
}
