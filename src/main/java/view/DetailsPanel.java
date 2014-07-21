package main.java.view;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

import main.java.controler.ParameterControler;
import main.java.controler.VarControler;

public class DetailsPanel extends ViewPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2038731760531048035L;

	/**Displayed name of the panel**/
	private String title;
	private ParameterControler lastDisplayed;

	public DetailsPanel(String name,ViewFactory vf)
	{
		super(name,vf);
		setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
		title = new String("Please select a component in the tree");
		setBorder(BorderFactory.createTitledBorder(title));
		this.lastDisplayed = null;
	}


	

	public void setDisplayedParam(String title,ParameterControler disp) {
		
		if(this.lastDisplayed != null){
			this.lastDisplayed.removeView();
		}
		
		((TitledBorder) this.getBorder()).setTitle(title);
		
		JScrollPane headerScroll = new JScrollPane(new ParamHeaderPanel(disp));
		JScrollPane paramScroll = new JScrollPane(this.getParamPanel(disp));
		
		ParameterView pv1 = disp.getParamView();
		ParameterView pv; 
		if(pv1 == null){
			pv = getViewFactory().constructView(disp);
		}else{
			pv = pv1.clone();
			disp.addParamView(pv);
		}
		
		JScrollPane quickScroll = new JScrollPane((Component) pv);
		
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
		
		this.lastDisplayed = disp;
		
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




	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
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
