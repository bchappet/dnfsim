package console;

import gui.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;

public class TextIn extends InputStream implements ActionListener{

	protected TextFieldIn txt;
	protected int carret;
	protected String currentString;
	protected boolean newInput;

	public TextIn(TextFieldIn txt)
	{
		this.newInput = true;
		this.currentString = "";
		this.carret = 0;
		this.txt = txt;
	}

	@Override
	public synchronized int read() throws IOException {

		int ret = 0;
		
		//If we reach the end of string return -1
		if(!newInput && carret == currentString.length())
		{
			ret = java.io.StreamTokenizer.TT_EOF;
			newInput = true;
		}
		else
		{
			while(carret == currentString.length())
			{
				try {
					this.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			ret = currentString.charAt(carret);
			carret ++;
			if(carret == currentString.length())
				newInput = false;
		}

		return ret;

	}

	@Override
	public synchronized void actionPerformed(ActionEvent e) {
		TextFieldIn ori = (TextFieldIn) e.getSource();
		currentString += ori.getText().replace(GUI.INVITE, "") + "\n" ;
		ori.setText(GUI.INVITE);
		//this.newInput = true;
		this.notifyAll();
	}

}
