package neuronBuffer;


public class NullBufferedNeuronUM extends BufferedNeuronUM {

	public NullBufferedNeuronUM() {
		super(null);
	}
	
	
	@Override
	public double compute(){
		return 0;
	}
	
	

}
