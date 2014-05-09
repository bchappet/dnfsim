package main.java.unitModel;

import main.java.coordinates.NullCoordinateException;
import main.java.maps.Unit;
import main.java.neuronBuffer.BufferedNeuronUM;

public class PotentialNeuronUM extends NeighborhoodUnitModel {

	
	//Parameters:
	public static final int THRESHOLD = 0; //when we are above this parameter
	public static final int NB_SPIKE = 1; //the exc and inh will emit NB_SPike spikes
	public static final int FOCUS = 2;
	
	public static final int WA = 3;
	public static final int WB = 4;
	//Neighboorhood
	public static final int EXC = 0; //BufferedNeuron map 
	public static final int INH = 1; //BufferedNeuron map
	
	
	@Override
	public double compute() throws NullCoordinateException {
		
		
		Unit exc =  neighborhoods.get(EXC)[0];
		Unit inh =  neighborhoods.get(INH)[0];
		
		BufferedNeuronUM excUM = (BufferedNeuronUM) exc.getUnitModel();
		BufferedNeuronUM inhUM = (BufferedNeuronUM) inh.getUnitModel();
		//System.out.println("compute");
		
		//System.out.println("focus : " + params.get(FOCUS).get(coord));
		
		if(params.getIndex(FOCUS).getIndex(coord) > params.getIndex(THRESHOLD).getIndex(coord)){
		
			int n = (int) params.getIndex(NB_SPIKE).getIndex(coord);
			//We reset neuron activity and buffers
			exc.reset();
			inh.reset();
			//We excite the neuron
			excUM.setExited(n);
			inhUM.setExited(n);
			
			return 0;
			
		}else{
		
			double sum = excUM.get()*params.getIndex(WA).getIndex(coord) + inhUM.get()*params.getIndex(WB).getIndex(coord);
			//We reset neuron activity when we get it
			excUM.resetActivity();
			inhUM.resetActivity();
			//Or we also reset the buffer
//			exc.reset();
//			inh.reset();
			
			return sum;
			

		}
		
		
		
		
	}

}
