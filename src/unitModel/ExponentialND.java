package unitModel;

import maps.Parameter;
import maps.Track;
import maps.Var;
import utils.ArrayUtils;
import coordinates.NullCoordinateException;
import coordinates.Space;

public class ExponentialND extends UnitModel implements Track  {
	
	//Parameters
	public static final int INTENSITY = 0;
	public static final int PROBA = 1;
	public static final int COORDS = 2;
	
	protected Double[] saveCenter;
	protected Double[] saveDim;


	public ExponentialND(Var dt, Space space,Parameter intensity,Parameter proba,
			Parameter ... center) {
		super(dt, space,ArrayUtils.concat(new Parameter[]{intensity,proba},center));
		saveCenter = new Double[space.getDim()];
		saveDim = new Double[space.getDim()];
	}

	@Override
	public double compute() throws NullCoordinateException {
		//Save center and dim
		for(int i = 0 ; i < saveCenter.length ; i++)
			saveCenter[i] = params.get(COORDS+i).get();
		
		double width = params.get(PROBA).get();
		for(int i = 0 ; i < saveDim.length ; i++)
			saveDim[i]	=  width;
		
		
		Double[] intCoor = space.discreteProj(coord); //Discrete value of this coordinates
		Double[] center = new Double[params.size()-COORDS];
		for(int i = 0 ; i < center.length; i++){
			center[i] = params.get(COORDS+i).get();
		}
		
		Double[] intCenter = space.discreteProj(center);//Discrete value of the center TODO no need to always compute it 
		
		double sumDist = 0;
		for(int i = 0 ; i < center.length; i++){
			sumDist += Math.abs(intCoor[i] - intCenter[i]);
		}
		
		
		
		
		return  params.get(INTENSITY).get(coord) * Math.pow(params.get(PROBA).get(coord), sumDist);
	}

	@Override
	public Double[] getCenter() throws NullCoordinateException {
		return saveCenter;
	}

	@Override
	public Double[] getDimension() throws NullCoordinateException {
		return saveDim;
	}
	

	

}
