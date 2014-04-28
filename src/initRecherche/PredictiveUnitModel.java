package initRecherche;

import java.util.Arrays;

import maps.AbstractMap;
import maps.Leaf;
import maps.Map;
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
//		if (Double.isNaN(x)){ 
//			Leaf leaf = (Leaf) params.get(POTENTIAL);
//			AbstractMap map = (AbstractMap) leaf.getMap();
//			System.out.println("argl");
//			System.out.println("wrap : " + Arrays.toString(newCoordWrap));
//			System.out.println("potential " +map.display2D() );
//			int index =  map.getSpace().coordToIndex(newCoordWrap[0],newCoordWrap[1]);
//			System.out.println("index x " + index);
//			System.out.println("getIndex x " + map.get(index));
//			System.out.println("potential x " +((AbstractMap) ((Leaf) params.get(POTENTIAL)).getMap()).get(newCoordWrap[0],newCoordWrap[1]) );
			
//			}
//		else System.out.println("ok");
		return x;
	}

}
