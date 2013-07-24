package console;

import java.io.IOException;
import java.io.InputStream;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;

/**
 * TODO presently, the input is handled with a JTextField
 * @author bchappet
 *
 */
public class TextAreaInput extends InputStream implements DocumentListener{

	protected  TextAreaInOut txt;
	protected int carret;
	protected Document doc;

	public TextAreaInput(TextAreaInOut txt)
	{
		this.txt = txt;
	}

	//  Reads the next byte of data from the input stream.
	//	The value byte is returned as an int in the range 0 to 255. 
	//	If no byte is available because the end of the stream has been reached,
	//	the value -1 is returned. This method blocks until input data is available, 
	//	the end of the stream is detected, or an exception is thrown. 
	@Override
	public int read() throws IOException {
		//return txt.re
		return 0;
	}

	@Override
	public void changedUpdate(DocumentEvent arg0) {
		this.doc = arg0.getDocument();
		
	}

	@Override
	public void insertUpdate(DocumentEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeUpdate(DocumentEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
