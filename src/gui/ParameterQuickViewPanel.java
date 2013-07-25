package gui;

import javax.swing.BoxLayout;

import maps.Parameter;



public class ParameterQuickViewPanel extends QuickViewPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8160890710007261989L;

	public ParameterQuickViewPanel(GUI gui,Parameter param) {
		super(gui,param);
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		
	}


}
