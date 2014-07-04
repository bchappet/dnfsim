package main.java.console;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;


/**
 * Singleton class designed to redirect System.out in a JTextArea
 * @author bchappet
 *
 */
public class TextAreaOutput extends OutputStream {
	
	protected  TextAreaInOut txt;
	protected PrintStream out;

	public TextAreaOutput(TextAreaInOut txt)
	{
		this.txt = txt;
		out = new PrintStream(this);
	}
	
	
	@Override
	public void write(int arg0) throws IOException {
		txt.append(""+(char) arg0);
		txt.setCaretPosition(txt.getDocument().getLength());
	}


	public PrintStream getOut() {
		return out;
	}
	
	

	

}
