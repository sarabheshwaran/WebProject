package uub.enums;

public enum Exceptions {

    INVALID_INPUT("Invalid input. Please provide valid data."),
    INVALID_AMNT("Invalid amount !"),
    INVALID_ID("Invalid ID !"),
    LOGIN_FAILED("Login failed"),
    ACCOUNT_NOT_IN_BRANCH("Account dont belong to this branch."),
    ACCOUNT_NOT_FOUND("Account not found."),
    USER_NOT_FOUND("User not found."),
    PASSWORD_WRONG("Wrong password !"),
    EMPLOYEE_NOT_FOUND("Employee not found !"),
    CUSTOMER_NOT_FOUND("Customer not found !"),
    BRANCH_NOT_FOUND("Branch not found"),
    SIGNUP_FAILED("Signup failed !"),
    UPDATE_FAILED("Update failed !"),

    
    DATABASE_CONNECTION_ERROR("Error connecting to the database."),

    TRANSACTION_ERROR("Transaction Failed"),

    NEGATIVE_BALANCE("Negative Balance!"),
    BALANCE_INSUFFICIENT("Insufficient balance !"),
    INVALID_RECEIVER("Invalid receiver account !"),
    UNKNOWN_ERROR("An unknown error occurred."),
    INVALID_AMOUNT("Invalid Amount !"),
	INVALID_ACCOUNT("Invalid Account"),

	EMAIL_INVALID("Invalid email !"),
	PHONE_INVALID("Invalid phone no. !"),
	PASSWORD_INVALID("Invalid password !"),
	PAN_INVALID("Invalid PAN no. !"),
	AADHAR_INVALID("Invalid aadhar no. !"),
	
	CONFIRM_PASSWORD("New password and repeat password doesnt match!");


    private final String errorMessage;

    Exceptions(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return errorMessage;
    }
}
