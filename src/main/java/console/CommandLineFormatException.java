package main.java.console;

public class CommandLineFormatException extends Exception {

	

	public CommandLineFormatException(String message) {
		super(message);
	}

	public CommandLineFormatException(Throwable cause) {
		super(cause);
	}

	public CommandLineFormatException(String message, Throwable cause) {
		super(message, cause);
	}

}
