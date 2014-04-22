package initRecherche;

import maps.Parameter;
import maps.VectorMap;
import unitModel.UnitModel;
import coordinates.NullCoordinateException;
import coordinates.Space;

public class PonderationParameter extends VectorMap {
	
	/**The two dimension position**/
	/*public final static int X = 0;
	public final static int Y = 1;*/
	
	public PonderationParameter(String name, Parameter dt, Space space, Parameter... maps) {
		super(name, dt, space, maps);		
		vect = new double[ModelCNFTPredictive.NB_PREDICTIONS];
	}

	@Override
	public double[] getVector(int delay) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void compute() throws NullCoordinateException {
		// TODO Auto-generated method stub
	    /*System.out.println("+size+"+params.size());	
	    
	    vect = new double[params.size()];
	    double [] ei = new double[params.size()];
	    double max = 0;
	    
	    int sizeX = params.get(0).getSpace().getDiscreteSize()[X];
		int sizeY = params.get(0).getSpace().getDiscreteSize()[Y];
	    
	    for(int i=0; i<params.size(); i++) {
	    	//System.out.println("************"+i);
			for(int x=0; x<sizeX; x++) {
				for(int y=0; y<sizeY; y++) {
					System.out.println(x+"+++++"+y);
					ei[i] = ei[i] + params.get(i).get((double)x,(double)y);					
				}
			}
			
			if(max<ei[i])
				max = ei[i];
	    }
	    
	    for(int j=0; j<ei.length; j++) {
	    	vect[j] = max - ei[j];
	    }*/
	}

	@Override
	public void addMemories(int nb, UnitModel... historic)
			throws NullCoordinateException {
		// TODO Auto-generated method stub
		
	}

}
