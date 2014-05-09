package main.java.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import main.java.maps.Parameter;



public class MapQuickViewPanel extends QuickViewPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4174868071143851696L;
	protected DisplayMap swgMap;

	public MapQuickViewPanel(RunnerGUI gui,Parameter map) {
		super(gui,map);
		swgMap = new DisplayMap(gui,map);
		
		this.add(swgMap,BorderLayout.CENTER);
		swgMap.setPreferredSize(new Dimension(200, 200));
		//swgMap.initBuffers();
	}
	
	public void update()
	{
		//swgMap.update();
	}
	
	

}
