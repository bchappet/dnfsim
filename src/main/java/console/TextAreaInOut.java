package main.java.console;

import java.io.InputStream;
import java.io.PrintStream;

import javax.swing.JTextArea;

public class TextAreaInOut extends JTextArea {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4805615442410179324L;
	protected TextAreaOutput out;
	protected TextAreaInput in;
	
	public TextAreaInOut()
	{
		super();
		out = new TextAreaOutput(this);
		in = new TextAreaInput(this);
		this.getDocument().addDocumentListener(in);
	}

	public TextAreaInOut(String string) {
		super(string);
		out = new TextAreaOutput(this);
		in = new TextAreaInput(this);
	}

	public PrintStream getOut() {
		return out.getOut();
	}

	public InputStream getIn() {
		return in;
	}
	
	

}
