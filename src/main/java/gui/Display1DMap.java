package main.java.gui;

import main.java.coordinates.NullCoordinateException;
import main.java.maps.Parameter;

/**
 * In this 1D version we stack the neurons
 * @author benoit
 *
 */
public class Display1DMap extends DisplayMap {

	public Display1DMap(RunnerGUI gui, Parameter linked) {
		super(gui, linked);
	}

	public Display1DMap(DisplayMap map) {
		super(map);
	}
	
	public void update() throws NullCoordinateException
	{
		resolution = displayed.getSpace().getSimulationSpace().getResolution();

		createBuffer();
		for(int i = 0 ; i < sx ; i++)
		{
			for(int j = 0 ; j < sy ; j++)
			{
				
				//Double[] coord = displayed.getSpace().continuousProj(new Double[]{(double)i,(double)j});
				
				this.buffer[i][j] = ((Parameter) displayed).getIndex(i + sx*j);
			}
		}


	}
	
	
	

}
