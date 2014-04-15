package gui;

import javax.swing.BorderFactory;
import javax.swing.JTextArea;

import maps.Parameter;



public class ParamHeaderPanel extends HeaderPanel {



	/**
	 * 
	 */
	private static final long serialVersionUID = 8554166763515477864L;

	protected Parameter param;

	public ParamHeaderPanel(RunnerGUI gui,Parameter param) {
		super(gui);
		this.param = param;
		this.setBorder(BorderFactory.createTitledBorder("Param"));
		initGUI();

	}
	protected void initGUI()
	{
		String str = param.toString();
	
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
