package main.java.view;

import main.java.maps.Var;

public class View1D extends View2D {
	
	/**How many line we save**/
	private static int memory = 100;
	/**Pointer to the present values**/
	private int current;
	

	public View1D(String name,double[] initialState,ColorMap colorMap,Var<Boolean> grid) {
		super(name,new double[memory][initialState.length],colorMap,grid);
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
