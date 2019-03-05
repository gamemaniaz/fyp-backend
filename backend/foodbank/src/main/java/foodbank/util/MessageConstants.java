package foodbank.util;

public final class MessageConstants {

	public static final String USER_ADD_SUCCESS = "User successfully added.";
	public static final String USER_UPDATE_SUCCESS = "User successfully updated.";
	public static final String USER_DELETE_SUCCESS = "User successfully deleted.";
	public static final String LOGIN_SUCCESS = "User successfully authenticated.";
	public static final String ITEM_CREATION_SUCCESS = "Item successfully created.";
	public static final String ITEM_OVERWRITE_SUCCESS = "Item successfully updated.";
	public static final String ITEM_UPDATE_SUCCESS = "Item quantity successfully updated.";
	public static final String REQUEST_CREATE_SUCCESS = "Request successfully added.";
	public static final String REQUEST_UPDATE_SUCCESS = "Request successfully updated.";
	public static final String REQUEST_DELETE_SUCCESS = "Request successfully deleted.";
	public static final String BATCH_REQUEST_CREATE_SUCCESS = "Requests successfully added.";
	public static final String BENEFICIARY_RETRIEVE_SUCCESS = "Beneficiary details successfully found.";
	public static final String BENEFICIARY_UPDATE_SUCCESS = "Beneficiary successfully updated.";
	public static final String BENEFICIARY_ADD_SUCCESS = "Beneficiary successfully added.";
	public static final String WINDOW_OPEN_SUCCESS = "Window successfully opened";
	public static final String WINDOW_CLOSE_SUCCESS = "Window successfully closed";
	public static final String OPENING_DATE_UPDATE_SUCCESS = "Opening date successfully updated.";
	public static final String CLOSING_DATE_UPDATE_SUCCESS = "Closing date successfully updated.";
	public static final String DECAY_RATE_UPDATE_SUCCESS = "Decay rate successfully updated.";
	public static final String MULTIPLIER_RATE_UPDATE_SUCCESS = "Multiplier rate successfully updated.";
	public static final String ADMIN_BATCH_UPDATE_SUCCESS = "All admin settings successfully updated.";
	public static final String ALLOCATION_GENERATE_SUCCESS = "Allocations successfully generated.";
	public static final String ALLOCATION_CREATE_SUCCESS = "Allocation successfully created.";
	public static final String ALLOCATION_UPDATE_SUCCESS = "Allocation successfully updated.";
	public static final String ALLOCATION_APPROVE_SUCCESS = "Allocations successfully approved.";
	public static final String EMAIL_SEND_SUCCESS = "Email successfully sent.";
	public static final String PACKING_LIST_GENERATE_SUCCESS = "Packing lists successfully generated.";
	public static final String PACKING_LIST_UPDATE_SUCCESS = "Packing list successfully updated.";
	public static final String RESET_PASSWORD_SUCCESS = "The password has been successfully reset.";
	public static final String DONOR_ADD_SUCCESS = "Donor successfully added.";
	public static final String DONOR_UPDATE_SUCCESS = "Donor update successfully.";
	public static final String STOCKTAKE_ADD_DONOR_ITEM_SUCCESS = "Item and quantity by donor added successfully.";
	public static final String DONOR_DELETE_SUCCESS = "Donor successfully deleted.";
	public static final String ADMIN_GET_SUCCESS = "GET call for ADMIN is successful.";
	public static final String ALLOCATION_GET_SUCCESS = "GET call for ALLOCATION is successful.";
	public static final String BENEFICIARY_GET_SUCCESS = "GET call for BENEFICIARY is successful";
	public static final String DONOR_GET_SUCCESS = "GET call for DONOR is successful";
	public static final String REQUEST_GET_SUCCESS = "GET call for REQUEST is successful";
    public static final String REQUEST_HISTORY_RETRIEVE_SUCCESS = "Request history successfully retrieved.";
    public static final String USER_LIST_RETRIEVE_SUCCESS = "User list retrieved successfully.";
    public static final String USER_RETRIEVE_SUCCESS = "User retrieved successfully.";
    public static final String ITEM_RETRIEVE_SUCCESS = "Item successfully retrieved.";
    public static final String PACKING_LIST_RETRIEVE_SUCCESS = "Packing list successfully retrieved.";
	public static final String INVOICE_RETRIEVE_SUCCESS = "Invoices successfully retrieved.";
    public static final String INVOICE_DATA_RETRIEVE_SUCCESS = "Invoice data successfully retrieved.";
	public static final String INVOICE_GENERATE_SUCCESS = "Invoice successfully generated.";
    public static final String INVOICE_DOWNLOAD_LINK_GENERATE_SUCCESS = "Invoice URL successfully generated.";
	public static final String INVENTORY_RESET_SUCCESS = "Inventory successfully reset to 0.";
	public static final String ALL_PACKING_STATUS_RETRIEVE_SUCCESS = "All packing list statuses successfully retrieved.";
	public static final String PASSWORD_CHANGE_SUCCESS = "Password successfully changed.";
	public static final String DONOR_NP_RETRIEVE_SUCCESS = "Nonperishable Donations by Donor successfully retrieved.";
	public static final String FORGOT_PASSWORD_REQUEST = "The e-mail containing the link to reset your email has been sent to your e-mail address.";
	
	public static class ErrorMessages {
		
		public static final String USER_ALREADY_EXISTS = "This user already exists.";
		public static final String NO_SUCH_USER = "This user does not exist.";
		public static final String NO_SUCH_USER_EMAIL = "There is no registered user for this email address.";
		public static final String INVALID_CREDENTIALS = "Invalid login credentials.";
		public static final String DUPLICATE_ITEM = "This item already exists.";
		public static final String NO_SUCH_ITEM = "The specified food item cannot be found.";
		public static final String NO_SUCH_REQUEST = "The specified request cannot be found.";
		public static final String NO_SUCH_BENEFICIARY = "The requested beneficiary does not exist.";
		public static final String BENEFICIARY_ALREADY_EXISTS = "This beneficiary already exists.";
		public static final String DATE_PARSE_ERROR = "There was an error when parsing the date.";
		public static final String INACTIVE_WINDOW = "The window is currently inactive.";
		public static final String INVALID_ALLOCATION = "The specified allocation cannot be found.";
		public static final String EMAIL_AUTH_ERROR = "Sender email cannot be authenticated.";
		public static final String EMAIL_RECIPIENT_NOT_FOUND = "Email cannot be sent to desired recipient.";
		public static final String PACKING_UPDATE_ERROR = "This packing list does not exist.";
		public static final String DONOR_ALREADY_EXISTS = "This donor already exists.";
		public static final String DONOR_DOES_NOT_EXIST = "This donor does not exists.";
		public static final String ADMIN_GET_FAIL = "GET call for ADMIN failed.";
		public static final String ALLOCATION_GET_FAIL = "GET call for ALLOCATION failed";
		public static final String BENEFICIARY_GET_FAIL = "GET call for BENEFICIARY failed";
		public static final String DONOR_GET_FAIL = "GET call for DONOR failed";
		public static final String REQUEST_GET_FAIL = "GET call for REQUEST failed";
		public static final String PASSWORD_NOT_UPDATED = "Password was not changed.";
		public static final String INCORRECT_TOKEN = "Incorrect JWT token.";
		public static final String MISSING_TOKEN = "JWT token cannot be found.";
		public static final String INCORRECT_RESET_TOKEN = "Invalid reset password request token.";
		public static final String EXPIRED_TOKEN = "The token has expired.";
		
	}
	
	public static class EmailMessages {
		
		public static final String WINDOW_OPENING_SUBJECT = "[NOTICE] Food Bank Singapore - Window Opened";
		public static final String WINDOW_OPENING_MESSAGE = "This e-mail serves to notify you that the request window is now open. You may now feel free"
				+ " to make your requests for the items available this window.\n\n*** This is an automatically generated e-mail, please do not reply ***";
		public static final String RESET_PASSWORD_SUBJECT = "[NOTICE] Food Bank Singapore - Reset Password Request";
		public static final String RESET_PASSWORD_MESSAGE1 = "We have recently received a request to reset your password. Your new password is: ";
		public static final String RESET_PASSWORD_MESSAGE2 = "\n\nPlease log-in with the generated password and proceed to change your password for security purposes."
				+ "\n\n*** This is an automatically generated e-mail, please do not reply ***";
		public static final String FORGOT_PASSWORD_SUBJECT = "[PASSWORD RECOVERY] Food Bank Singapore - Password Recovery";
		public static final String FORGOT_PASSWORD_STARTER = "Dear ";
		public static final String FORGOT_PASSWORD_MESSAGE = " , \n\n\nWe have received a message to reset your Food Bank Account password. Simply click on the following "
				+ "URL to reset your password:\n\n";
		
	}
	
}
