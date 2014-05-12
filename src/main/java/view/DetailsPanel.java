package main.java.view;


import java.awt.Component;
import java.awt.Dimension;

import main.java.controler.*;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;




public class DetailsPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2038731760531048035L;

	/**Displayed name of the panel**/
	protected String title;

	public DetailsPanel()
	{
		super();
		setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
		title = new String("Please select a component in the tree");
		setBorder(BorderFactory.createTitledBorder(title));
	}


	

	public void setDisplayedParam(String title,ParameterControler disp) {
		
		((TitledBorder) this.getBorder()).setTitle(title);
		
		JScrollPane headerScroll = new JScrollPane(new ParamHeaderPanel(disp));
		JScrollPane paramScroll = new JScrollPane(this.getParamPanel(disp));
		disp.initView(null);
		JScrollPane quickScroll = new JScrollPane((Component) disp.getParamView());
		
		Dimension dimThis = this.getSize();
		Dimension dim = new Dimension(((int)(dimThis.width/5d)),((int)(dimThis.height/5d)));
		Dimension dim2 = new Dimension(((int)(dim.width*2)),((int)(dim.height*2)));
		
		headerScroll.setPreferredSize(dim);
		paramScroll.setPreferredSize(dim2);
		quickScroll.setPreferredSize(dim2);
		
		this.removeAll();
		this.add(headerScroll);
		this.add(paramScroll);
		this.add(quickScroll);
		this.revalidate();
		this.repaint();
		
	}
	
	public JPanel getParamPanel(ParameterControler pc)
	{
		JPanel ret = new JPanel();
		ret.setBorder(BorderFactory.createTitledBorder("Parameters:"));
		ret.setLayout(new BoxLayout(ret,BoxLayout.PAGE_AXIS));
		for(int i = 0 ; i < pc.getChildCount() ; i++)
		{
			ParameterControler child = pc.getChild(i);
			if(child instanceof VarControler)
			{
				ParameterModifierPanel spm = new ParameterModifierPanel((VarControler)child);
				ret.add(spm);
			}
		}
		return ret;
	}

//	/**
//	 * Without quick main.java.view
//	 * @param title
//	 * @param headerPanel
//	 * @param paramPanel
//	 */
//	public void setDisplayedParam(String title,
//			JPanel headerPanel, JPanel paramPanel) {
//		
//		((TitledBorder) this.getBorder()).setTitle(title);
//		
//		JScrollPane headerScroll = new JScrollPane(headerPanel);
//		JScrollPane paramScroll = new JScrollPane(paramPanel);
//		
//		Dimension dimThis = this.getSize();
//		Dimension dim = new Dimension(((int)(dimThis.width/4d)),((int)(dimThis.height/4d)));
//		Dimension dim2 = new Dimension(((int)(dim.width*3)),((int)(dim.height*3)));
//		
//		headerScroll.setPreferredSize(dim);//1/4
//		paramScroll.setPreferredSize(dim2);//3/4
//		
//		this.removeAll();
//		this.add(headerScroll);
//		this.add(paramScroll);
//		this.revalidate();
//		this.repaint();
//		
//	}
	
	

	

}
