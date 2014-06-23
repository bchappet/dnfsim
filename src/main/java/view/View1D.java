package main.java.view;

import main.java.view.ColorMap;

public class View1D extends View2D {
	
	/**How many line we save**/
	private int memory = 10;
	/**Pointer to the present values**/
	private int current;
	

	public View1D(String name,double[] initialState,ColorMap colorMap) {
		super(name,new double[10][initialState.length],colorMap);
		this.current = 0;
		this.buffer[current] = initialState;
	}

	public void update(double[] values)
	{
		current = (current+1) % memory;
		this.buffer[current] = values;
	}
	
	@Override
	protected double getValue(int i, int j) {	
		return buffer[(j+1+current)%memory][i];
	}
}
