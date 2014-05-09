package main.java.hardSimulator;

import main.java.coordinates.NullCoordinateException;


public class NullSpikingNeuronHUM extends SpikingNeuronHUM {
	
	
	public void setInPortsExc(int direction,int val) {
		//nothing
	}

	public void setInPortsInh(int direction,int val) {
		//nothing
	}
	
	protected void onInitilization(){
		//nothing
	}
	
	public double compute() throws NullCoordinateException {
		return 0;
	}
	
	

}
