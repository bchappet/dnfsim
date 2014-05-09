package main.java.maps;

import main.java.neuronBuffer.BufferedNeuronUM;

public class NullBufferedNeuronUM extends BufferedNeuronUM {

	public NullBufferedNeuronUM() {
		super(null);
	}
	
	
	@Override
	public double compute(){
		return 0;
	}
	
	

}
