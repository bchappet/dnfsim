package main.java.gui;

import main.java.coordinates.NullCoordinateException;
import main.java.maps.AbstractMap;
import main.java.maps.NeighborhoodMap;
import main.java.maps.Parameter;
import main.java.maps.SampleMap;
import main.java.maps.UnitMap;
import main.java.unitModel.SomUM;

public class MapDisplayNode extends DisplayNode {


	public MapDisplayNode(DisplayNode parent, AbstractMap linked, RunnerGUI gui) {
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

			if(linked instanceof UnitMap &&
					(((UnitMap) linked).getUnitModel()) instanceof SomUM){
				return  new DisplaySOMMap(gui, (NeighborhoodMap)linked, ((SomUM)((UnitMap)linked).getUnitModel()).getInputSpace());
			}
			else if(linked instanceof SampleMap){
				return  new DisplaySampleMap(gui, (Parameter)linked);

			}else{

				return new DisplayMap(gui,  (AbstractMap) linked);
			}
		}
		else if(((AbstractMap)linked).getSpace().getDim() == 1)
		{
			((AbstractMap)linked).constructMemory();
			return new Display1DMap(gui,  (AbstractMap) linked);
		}
		else{
			throw new Error("Not implemented");
		}
	}



}
