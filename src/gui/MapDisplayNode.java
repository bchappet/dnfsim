package gui;

import maps.AbstractMap;
import coordinates.NullCoordinateException;

public class MapDisplayNode extends DisplayNode {
	

	public MapDisplayNode(DisplayNode parent, AbstractMap linked, GUI gui) {
		super(parent, linked, gui);
	}
	
	public void valueChanged(ParameterView parameterView) throws NullCoordinateException {
		parameterView.setDisplayedParam("Map selected" ,
		new MapHeaderPanel(gui, (AbstractMap) linked),
		super.getParamPanel(),
		this.getQuickViewPanel());
	}
	
	public QuickViewPanel getQuickViewPanel() throws NullCoordinateException
	{
		if(((AbstractMap)linked).getSpace().getDim() == 2)
		{
			((AbstractMap)linked).constructMemory();
			return new DisplayMap(gui,  (AbstractMap) linked);
		}
		else
		{
			return null;
		}
	}

	

}
