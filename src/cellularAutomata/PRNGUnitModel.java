package cellularAutomata;

import java.util.ArrayList;
import java.util.List;

import maps.AbstractUnitMap;
import maps.Map;
import maps.NeighborhoodMap;
import maps.Parameter;
import maps.UnitLeaf;
import maps.UnitParameter;
import unitModel.UnitModel;
import utils.ArrayUtils;
import utils.Hardware;
import utils.Precision;
import coordinates.NullCoordinateException;
import coordinates.Space;

public class PRNGUnitModel extends UnitModel {
	
	/**Parameters**/
	public static final int FRAC = 0;//Fractional fp size

	
	/**List of available random number**/
	protected List<Integer> randomNumbers;
	/**Index of next random number**/
	protected int index = 0;
	/**Map of {@link CACellUnitModel}**/
	protected Parameter map;


	public PRNGUnitModel(Parameter map) {
		randomNumbers = new ArrayList<Integer>();
		this.map =  map;
	}



	
	
	public PRNGUnitModel(Parameter map,Parameter dt, Space space, Parameter... parameters) {
		super(dt, space, parameters);
		randomNumbers = new ArrayList<Integer>();
		this.map =  map;
	}
	public void reset(){
		super.reset();
		index = 0;
		randomNumbers.clear();
		map.reset();
	}
	
	@Override
	public PRNGUnitModel clone(){
		PRNGUnitModel clone = (PRNGUnitModel) super.clone();
		clone.randomNumbers =  new ArrayList<Integer>();
		clone.index = 0;
		Parameter mapCloned = map.clone();
		clone.map = mapCloned;
		((NeighborhoodMap) mapCloned).getNeighborhoods().get(0).setMap(new UnitLeaf((UnitParameter) mapCloned));
		
		return clone;
	}
	
	@Override
	public PRNGUnitModel clone2(){
		PRNGUnitModel clone = (PRNGUnitModel) super.clone2();
		clone.randomNumbers =  new ArrayList<Integer>();
		clone.index = 0;
		Parameter mapCloned = map.clone();
		clone.map = mapCloned;
		return clone;
	}
	
	/**
	 * @return a new real random number. The number of available random number
	 *  and their precision depends on the size of the given CACellUnitModel.
	 *  Precision = width
	 *  Number available = height
	 * @warning If there is no more "new" random number a warning is displayed
	 */
	public double getRandomNumber(){
		
		if(index == randomNumbers.size()){
			System.err.println("Warning no more random number");
			index = 0;
		}
		int integer = randomNumbers.get(index);
		index ++;
		return Precision.to_real(integer,(int)params.get(FRAC).get());
		
	}
	
	/**
	 * Return the bit value at the specific index
	 * @return
	 */
	public int getIndex(int index){
		return (int) map.get(index);
	}
	
	public int getFast(int x,int y){
		return (int) map.getFast(x,y);
	}

	public Parameter getMap(){
		return map;
	}

	@Override
	public double compute() throws NullCoordinateException {
		index = 0;
		map.compute(); //as it is not shared whithin the units, we have to compute it here
		randomNumbers.clear();
		//Fill the list of random number with the map result
		int width = map.getSpace().getDiscreteSize()[Space.X];
		int height = map.getSpace().getDiscreteSize()[Space.Y];
		//System.out.println("width : " + width + " height : " + height);
		for(int i = 0 ; i < height ; i++){
			int[] logic_vector = new int[width];
			for(int j = 0 ; j < width ; j++){
				logic_vector[j] = (int) map.getFast(j,i);
			}
			//System.out.println("add random : " + Arrays.toString(logic_vector) + " == " + Hardware.toInteger(logic_vector));
			randomNumbers.add(Hardware.toInteger(ArrayUtils.reverse(logic_vector)));
		}
		return ArrayUtils.avg(randomNumbers);
	}
	
	

}
