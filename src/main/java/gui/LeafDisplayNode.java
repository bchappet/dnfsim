package main.java.gui;

import main.java.coordinates.NullCoordinateException;
import main.java.maps.AbstractMap;
import main.java.maps.Leaf;
import main.java.maps.Parameter;

public class LeafDisplayNode extends DisplayNode {

	public LeafDisplayNode(DisplayNode parent, Leaf linked, RunnerGUI gui) {
		super(parent, linked, gui);
	}
	
	public void valueChanged(ParameterView parameterView) throws NullCoordinateException {
		parameterView.setDisplayedParam("Leaf selected" ,
		new MapHeaderPanel(gui, (Parameter) linked),
		super.getParamPanel(),
		this.getQuickViewPanel());
	}
	
	public QuickViewPanel getQuickViewPanel() throws NullCoordinateException
	{
		if(((Parameter)linked).getSpace().getDim() == 2)
		{
			((Parameter)linked).constructMemory();
			return new DisplayMap(gui,  (Parameter) linked);
		}
		else
		{
			return null;
		}
	}

}
