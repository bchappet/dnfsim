package initRecherche;

import unitModel.UnitModel;
import coordinates.NullCoordinateException;

public class PredictiveUnitModel extends UnitModel {
	
	public final static int POTENTIAL = 0;
	public final static int SPEED_X = 1;
	public final static int SPEED_Y = 2;
	
	public final static double GAMMA = 8.04;
	
	//private double speedX;
	//private double speedY;
	
	public PredictiveUnitModel() {
		super();
	}


	@Override
	public double compute() throws NullCoordinateException {

		double speedX = params.get(SPEED_X).get(coord);		
		double speedY = params.get(SPEED_Y).get(coord);
		
		//System.out.println("compute prediction : "+speedY);
		
		double locationX = coord[0];
		double locationY = coord[1];
		
		//compute the predictive location
		double newLocationX = locationX-(GAMMA*speedX*dt.get()); 
		double newLocationY = locationY-(GAMMA*speedY*dt.get());

		Double[] newCoord = {newLocationX,newLocationY};
		Double[] newCoordWrap = this.space.wrap(newCoord);

		double x= params.get(POTENTIAL).get(newCoordWrap[0],newCoordWrap[1]);
		if (Double.isNaN(x)) System.out.println("argl");
		else System.out.println("ok");
		return x;
	}

}
