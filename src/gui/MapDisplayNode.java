package gui;

import unitModel.SomUM;
import maps.AbstractMap;
import maps.AbstractUnitMap;
import maps.NeighborhoodMap;
import maps.Parameter;
import maps.SampleMap;
import coordinates.NullCoordinateException;

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

			if(linked instanceof AbstractUnitMap &&
					(((AbstractUnitMap) linked).getUnitModel()) instanceof SomUM){
				return  new DisplaySOMMap(gui, (NeighborhoodMap)linked, ((SomUM)((AbstractUnitMap)linked).getUnitModel()).getInputSpace());
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
