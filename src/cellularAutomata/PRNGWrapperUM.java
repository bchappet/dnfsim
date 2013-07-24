package cellularAutomata;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import maps.Map;
import maps.Parameter;
import maps.Var;
import unitModel.UnitModel;
import utils.ArrayUtils;
import utils.Hardware;
import utils.Precision;
import coordinates.NullCoordinateException;
import coordinates.Space;

public class PRNGWrapperUM extends UnitModel {
	
	/**
	 * Parameters index
	 */
	public static final int MAP  = 0;//the linked PRNGUnitModel map
	public static final int FRAC = 1;//Precision of the wanted random umber (FRAC part)
	public static final int NB_VALUES = 2;//Number of desired random values
	
	/**
	 * Constant (for now TODO) size of the PRNG map Cells
	 */
	protected static final int PRNG_X = 8;
	protected static final int PRNG_Y = 8;
	
	
	/**List of available random number**/
	protected List<Integer> randomNumbers;
	protected int index = 0;
	
	protected boolean computed = false;
	

	public PRNGWrapperUM() {
		index = 0;
		randomNumbers = new ArrayList<Integer>();
	}

	

	public PRNGWrapperUM(Parameter dt, Space space, Parameter... parameters) {
		super(dt, space, parameters);
		index = 0;
		randomNumbers = new ArrayList<Integer>();
	}
	
	public void reset(){
		super.reset();
		index = 0;
		randomNumbers.clear();
	}
	
	@Override
	public PRNGWrapperUM clone(){
		PRNGWrapperUM clone = (PRNGWrapperUM) super.clone();
		clone.randomNumbers =  new ArrayList<Integer>();
		clone.index = 0;
		return clone;
	}
	
	@Override
	public PRNGWrapperUM clone2(){
		PRNGWrapperUM clone = (PRNGWrapperUM) super.clone2();
		clone.randomNumbers =  new ArrayList<Integer>();
		clone.index = 0;
		return clone;
	}

	
	
	/**
	 * @param index
	 * @return a new real random number. The number of available random number
	 *  and their precision depends on the size of the given CACellUnitModel.
	 *  Precision = width
	 *  Number available = height
	 */
	public double getRandomNumber(){
		//We compute only if we need to
		if(!computed)
			this.artificialComputation();
		
		int integer = randomNumbers.get(index);
		index ++;
//		System.out.println("get int : " + integer);
		return Precision.to_real(integer,(int)params.get(FRAC).get());
		
	}
	protected void artificialComputation(){
//		Double[] discrCoord = space.discreteProj(coord);
//		System.out.println("Discrete coord: " + Arrays.toString(discrCoord));
		index = 0;
		randomNumbers.clear();
		int width = (int) params.get(FRAC).get();//width of the logic_vector encoding a random number
		for(int i = 0 ; i  <  params.get(NB_VALUES).get() ; i++){
			int[] logic_vector = new int[width];
			for(int j = 0 ; j < width ; j++){
				logic_vector[j] = findBit(j,i);
			}
//			System.out.println("Vector : " + Arrays.toString(logic_vector));
			
			int p;
			randomNumbers.add(p = Hardware.toInteger(ArrayUtils.reverse(logic_vector)));
//			System.out.println("int : " + p);
			
		}
		computed = true;
	}

	@Override
	public double compute() throws NullCoordinateException {
		//System.out.println("Compute Wrapper " + time.get());
		computed = false;
		return ArrayUtils.avg(randomNumbers);
	}
	
	/**
	 * Find the bit i,j within the PRNG map
	 * @param i
	 * @param j
	 * @return
	 */
	protected int findBit(int i, int j) {
		Double[] discrCoord = space.discreteProj(coord);
//		System.out.println("=============================");
//		System.out.println("Find bit: " + i+","+j);
//		System.out.println("Discrete coord: " + Arrays.toString(discrCoord));
//		//add discrete coords to i,j
		int absi = (int) (i + discrCoord[Space.X] * params.get(FRAC).get());
		int absj = (int) (j + discrCoord[Space.Y] * params.get(NB_VALUES).get());
		
		//now find the right PRNG UM
		int umX = absi / PRNG_X;
		int umY = absj / PRNG_Y;
//		System.out.println("PRNG UM coord: " + umX + "," + umY);
		
		//get the coor on the um
		int coorX = absi % PRNG_X;
		int coorY = absj % PRNG_Y;
//		System.out.println("On the coord: " + coorX+","+coorY);
		
		//get the bit
		Map map = (Map) params.get(MAP);
		Space mapSpace = map.getSpace();
//		System.out.println("coord : " + Arrays.toString(new Double[]{(double)umX,(double)umY}));
//		System.out.println("discrcoord : " + Arrays.toString(mapSpace.continuousProj(new Double[]{(double)umX,(double)umY})));
//		System.out.println((mapSpace.coordToIndex(mapSpace.continuousProj(new Double[]{(double)umX,(double)umY}))));
//		
		int bit = ((PRNGUnitModel) map.getUnit(
				mapSpace.coordToIndex(mapSpace.continuousProj(new Double[]{(double)umX,(double)umY})))
				.getUnitModel()).getFast(coorX,coorY);
		
		return bit;
		
		
		
		
		
	}

}
