package main.java.console;

import javax.swing.JTextField;
import javax.swing.text.DefaultCaret;



public class TextFieldIn extends JTextField
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected TextIn in;
	
	public TextFieldIn()
	{
		super();
		in = new TextIn(this);
		this.addActionListener(in);
		this.setCaret(new MyCaret());
	}

	public TextFieldIn(String str) {
		super(str);
		in = new TextIn(this);
		this.addActionListener(in);
		
	}

	public TextIn getIn() {
		return in;
	}
	
	protected class MyCaret extends DefaultCaret
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void moveDot(int dot)
		{
//			System.out.println("here..1");
			if(dot > 0)
				super.moveDot(dot);
			
		}
		
		public void setDot(int dot)
		{
//			System.out.println("here..2");
			if(dot > 0)
				super.setDot(dot);
			
		}
	}
}