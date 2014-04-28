package initRecherche;

import java.util.Iterator;

import maps.Map;
import maps.Parameter;
import maps.Precomputation;
import maps.Unit;
import maps.VectorMap;
import unitModel.UnitModel;
import coordinates.NullCoordinateException;
import coordinates.Space;

public class GlobalPredictionMap extends Map {
	
	public final static String GLOBAL_PONDERATION = "globalPonderation";	
	public final static int X = 0;
	public final static int Y = 1;
	
	protected VectorMap globalPonderation;

	
	public GlobalPredictionMap(String name, UnitModel unitModel) {
		super(name, unitModel);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * If we want to add the unit model later
	 * @param name
	 * @param um
	 * @param dt
	 * @param refSpace
	 * @param params
	 */
	public GlobalPredictionMap(String name,UnitModel um,Parameter dt, Space space, Parameter... maps) {
		super(name,um,dt,space,maps);
		globalPonderation = new PonderationParameter(GLOBAL_PONDERATION, dt, space);
		this.addParameters(globalPonderation);
	}
	
	/**
	 * One iteration of computation
	 * @throws NullCoordinateException 
	 */
	@Override
	public  void compute() throws NullCoordinateException
	{
		//System.out.println("global prediction map + " + this.getParams().size());
		computeGlobalPonderation();
		
		//System.out.println("Compute map :  " + this.name + " time " + this.time.val);
		if(isMemory)
		{

			Iterator<Unit> it = getComputationIterator();
			if(units.get(0).getUnitModel() instanceof Precomputation){
				while(it.hasNext()){
					Unit u = it.next();
					((Precomputation) u.getUnitModel()).precompute();
				}
			}
			//Compute every unit 
			Iterator<Unit> it2 = getComputationIterator();
			while(it2.hasNext())
			{
				Unit u = it2.next();
				u.compute();
			}
			if(units.get(0).isParallel())
			{
				//System.out.println("Swap : "   + this.name + " time " + this.time.val);
				//Swap memories for every unit
				for(Unit u : units)
				{
					u.swap();
				}
			}
		}
		else
		{
			//NoMemory => nothing to do
		}
	}
	
	
	private void computeGlobalPonderation() {
		
		double [] ei = new double[globalPonderation.getVector().length];
	
		
		double max = 0;
		
		for(int i=1; i<=ModelCNFTPredictive.NB_PREDICTIONS; i++) {
			
			int size = params.get(i).getValues().length;
			double[] values = params.get(i).getValues();
			
			//System.out.println("carte = "+params.get(i).getName());
			
			//sum over all the map
			for(int x=0; x<size; x++) {				
				ei[i-1] = ei[i-1] + values[x];	
				//System.out.println("***"+values[x]);
			}
			
			if(max<ei[i-1])
				max = ei[i-1];
			
//			System.out.println("$$ei = "+ei[i-1]);
		}
		

		//compute the wk for each map
		for(int j=0; j<ei.length; j++) {
	    	globalPonderation.getVector()[j] = max - ei[j];
	    }
	}


}
