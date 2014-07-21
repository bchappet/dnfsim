package main.java.view;

import javax.swing.BorderFactory;
import javax.swing.JTextArea;

import main.java.controler.ParameterControler;



public class ParamHeaderPanel extends HeaderPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8554166763515477864L;

	protected ParameterControler param;

	public ParamHeaderPanel(ParameterControler param) {
		super();
		this.param = param;
		this.setBorder(BorderFactory.createTitledBorder("Param"));
		initGUI();

	}
	protected void initGUI()
	{
		String str = param.getName();
	
		if(!str.isEmpty())
		{
			JTextArea text = new JTextArea(str);
			text.setEditable(false);//TODO will be editable
			text.setLineWrap(true);
			//text.setFont(new Font("Serif", Font.BOLD, 16));
			text.setWrapStyleWord(true);

			this.add(text);
		}

	}

}
