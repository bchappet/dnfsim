package main.java.console;

public class InvalidKeyException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -495928617187104555L;

	public InvalidKeyException() {
	}

	public InvalidKeyException(String message) {
		super(message);
	}

	public InvalidKeyException(Throwable cause) {
		super(cause);
	}

	public InvalidKeyException(String message, Throwable cause) {
		super(message, cause);
	}

}
