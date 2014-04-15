package gui;

import coordinates.NullCoordinateException;
import maps.AbstractMap;
import maps.Leaf;
import maps.Parameter;

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
