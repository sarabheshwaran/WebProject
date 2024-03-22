package uub.staticlayer;

public class CustomBankException extends Exception{
	private static final long serialVersionUID = 1L;
	public CustomBankException(String message){
		super(message);
	}
	public CustomBankException(String message,Exception cause){
		super(message,cause);
	}

	public <T> CustomBankException(T message ,Exception cause){
		super(message.toString(),cause);
	}

	public <T> CustomBankException(T message){
		super(message.toString());
	}

	public CustomBankException() {
		super();
	}
	
	 public String getFullMessage() {
	        StringBuilder errorMessage = new StringBuilder(getMessage());
	        Throwable cause = getCause();
	        
	        while (cause != null) {
	            errorMessage.append(" : ").append(cause.getMessage());
	            cause = cause.getCause();
	        }
	        
	        return errorMessage.toString();
	    }
}
