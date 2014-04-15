package maps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import unitModel.UnitModel;
import coordinates.NullCoordinateException;
import coordinates.Space;

/**
 * Aggregate of several parameter
 * @author bchappet
 *
 */
public class SampleMap extends VectorMap  {
	protected List<double[]> memories;

	public SampleMap(String name, Parameter dt, Space space, Parameter... maps) {
		super(name, dt, space, maps);
	}
	
	
	@Override
	public void compute() throws NullCoordinateException {
		//System.out.println("Compute");
		vect = new double[params.size()];
		for(int i = 0 ; i < vect.length ; i++){
			//System.out.println("Param i : " + params.get(i).getName());
			vect[i] = params.get(i).get();
		}
		
		if(memories != null){
			memories.add(0, vect.clone());
			memories.remove(memories.size()-1);
			
		}


	}


	@Override
	public double[] getVector(int delay) {
		return memories.get(delay);
	}


	@Override
	public void addMemories(int nb, UnitModel... historic)
			throws NullCoordinateException {
		
		memories = new ArrayList<double[]>(nb+1);
		double[] vectHist0 = new double[]{historic[0].get(),historic[0].get()};
		if(vect == null){
			memories.add(vectHist0);
		}else{
			memories.add(vect);
		}
		memories.add(vect);
		for(int i = 0 ; i < nb ; i++ ){
			double[] vectHistI = new double[]{historic[i].get(),historic[i].get()};
			memories.add(vectHistI);
		}
		
		
	}


}