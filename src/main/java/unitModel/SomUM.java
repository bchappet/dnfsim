package main.java.unitModel;

import java.util.Arrays;

import main.java.coordinates.NullCoordinateException;
import main.java.coordinates.Space;
import main.java.maps.Parameter;
import main.java.maps.SampleMap;

/**
 * Kohonen like neurons
 * @author bchappet
 *
 */
public class SomUM extends NeighborhoodUnitModel {



	//Parameters
	public static final int INPUT = 0;
	public static final int WTM = 1; //winner takes most map
	public static final int LEARNING_RATE = 2;
	//

	//Weight to describe the main.java.input
	protected double[] weights;

	//Input main.java.space
	protected Space inputSpace;



	public SomUM(Space inputSpace,Parameter dt, Space space, Parameter... parameters) {
		super(dt, space, parameters);
		this.inputSpace = inputSpace;
		this.weights = new double[]{0,0};
//		this.weights[Space.X] = inputSpace.getUniformSample(Space.X);
//		this.weights[Space.Y] = inputSpace.getUniformSample(Space.Y);
		//System.out.println("Original weights : " + Arrays.toString(weights));

	}





	@Override
	public double compute() throws NullCoordinateException {

		double[] inputsT1 = ((SampleMap) getParam(INPUT)).getVector(1);
		//System.out.println(Arrays.toString(inputsT1));
		//first we change the weights as we need t-1 potential from WTM map
		learnWeights(
				getParam(LEARNING_RATE).getIndex(coord),
				getParam(WTM).getIndex(coord),
				inputsT1);

		double[] inputs = ((SampleMap) getParam(INPUT)).getVector();

		//then we compute the new potential
		double pot =  computePotential(inputs);
		//We reset the cnft part

		//System.out.println("Reset");
		return pot;
	}

	/**
	 * 
	 * @param coord
	 */
	public void setCoord(Double... coord) {
		super.setCoord(coord);
		Double[] inputSpaceCoord = inputSpace.indexToCoordInt(space.getFramedSpace().coordIntToIndex(coord));
//		this.weights[Space.X] =  inputSpaceCoord[Space.X];
//		this.weights[Space.Y] = inputSpaceCoord[Space.Y];
		//System.out.println(main.java.space);
		//System.out.println(Arrays.toString(coord) + " weights : " + Arrays.toString(weights) );
	}


	public UnitModel setInitActivity() {
		UnitModel ret = super.setInitActivity();
		if(inputSpace != null){
			this.weights = new double[]{0,0};
			this.weights[Space.X] = inputSpace.getUniformSample(Space.X);
			this.weights[Space.Y] = inputSpace.getUniformSample(Space.Y);
			if(coord != null){

			}
			//System.out.println("Set activity weights : " + Arrays.toString(weights));
		}

		return ret;

	}

	/**
	 * Compute the new potential
	 */
	protected double computePotential(double[] inputs) {
		//here it is the mean of W-I

		//System.out.println(Arrays.toString(inputs));
		double prox =   proximity(inputs,weights);



		//System.out.println("Time : " + time.val + "@"+hashCode()+" ==> "
		//+ weights[0] +" - "+ getParam(INPUT).get(0) + " ==> " + sum/2d);

		return prox;



	}

	/**
	 * Learn weights according to the WTM map and the current main.java.input
	 */
	protected void learnWeights(double lr,double wtm,double[] input) {

		double[] dist = distAxisNonAbs(input,weights);
		Double[] newW = new Double[dist.length];

		for(int i =0 ; i < dist.length ; i++){

			double distance =  dist[i]/inputSpace.getSize()[i]; 
			if(wtm < 0){
//				lr = - lr/10;
//				if(distance < 0){
//					distance = - (1 - Math.abs(distance));
//				}else{
//					distance = 1- distance;
//				}
				wtm = 0;
			}
			double delta = lr * wtm * distance;
//			if(dist[i]/inputSpace.getSize()[i] > 1){
//
////				System.out.println("wtm : " + wtm + " distance : " + dist[i]/inputSpace.getSize()[i] );
////				System.out.println("ccords : " + Arrays.toString(main.java.space.discreteProj(coord)) + "  ==> " + delta);
//			}

			double wtmp = weights[i] + delta;
			
			

			newW[i] =  wtmp;
		}
		
		Double[] newW2 = inputSpace.wrap(newW);
		if(!Arrays.equals(newW, newW2)){
//			System.out.println("Wrap : " + Arrays.toString(newW) + " ==> " + Arrays.toString(newW2));
			for(int i = 0 ; i < newW2.length ; i++){
				if(newW2[i] == null){
					newW2[i] = newW[i];
				}
			}
		}
		for(int i = 0 ; i < newW2.length ; i++){
			weights[i] = newW2[i];
		}
		
		
	}

	private double[] distAxis(double[] input2, double[] weights2) {
		double[] distRough = distAxisAbs(input2, weights2);
		double[] dist = new double[distRough.length];
		for(int i = 0 ; i < distRough.length ; i++){
			dist[i] = distRough[i]/inputSpace.getSize()[i];
		}

		return dist;

	}





	/**
	 * Compute distance between the main.java.input and the weights at position i
	 * between 0 and 1
	 * @param input2
	 * @param weights2
	 * @return
	 */
	private double[] distAxisAbs(double[] inputs, double[] weights) {
		//		double in = inputs[i];
		//		double w = weights[i];
		//		//System.out.println("in : " + in +" w : " + w + " size : " + inputSpace.getSize()[i]);
		//		double res = (in - w)/inputSpace.getSize()[i];
		//		//System.out.println("===> " + res);
		//		return res;

		double ax = Math.abs(weights[Space.X]-inputs[Space.X]);
		double ay = Math.abs(weights[Space.Y]-inputs[Space.Y]);

		double dx;
		double dy;
		if(inputSpace.isWrap()){
			dx = Math.min(ax, inputSpace.getSize()[Space.X]-ax);
			dy = Math.min(ay, inputSpace.getSize()[Space.X]-ay);
		}else{
			dx = ax;
			dy = ay;
		}
		return new double[]{dx,dy};
	}
	
	/**
	 * Compute distance between the main.java.input and the weights at position i
	 * between 0 and 1
	 * @param input2
	 * @param weights2
	 * @return
	 */
	private double[] distAxisNonAbs(double[] inputs, double[] weights) {
		

		double ax = Math.abs(weights[Space.X]-inputs[Space.X]);
		double ay = Math.abs(weights[Space.Y]-inputs[Space.Y]);
		double nAbsX = inputs[Space.X] - weights[Space.X];
		double nAbsY = inputs[Space.Y] - weights[Space.Y];

		double dx;
		double dy;
		if(inputSpace.isWrap()){
			if(ax <= 1-ax){
				dx = nAbsX;
			}else{
				dx = 1 - nAbsX;
			}
			if(ay <= 1-ay){
				dy = nAbsY;
			}else{
				dy = 1 - nAbsY;
			}
		}else{
			dx = nAbsX;
			dy = nAbsY;
		}
		return new double[]{dx,dy};
	}

	/**
	 * Compute distance between the main.java.input and the weights at position i
	 * between 0 and 1
	 * @param input2
	 * @param weights2
	 * @return
	 */
	private double proximity(double[] inputs, double[] weights) {
		//		double in = inputs[i];
		//		double w = weights[i];
		//		//System.out.println("in : " + in +" w : " + w + " size : " + inputSpace.getSize()[i]);
		//		double sizeSpace = inputSpace.getSize()[i];
		//		double res = Math.pow(1-(Math.abs((in-w))/sizeSpace),2);
		//		//System.out.println("===> " + res);
		//		return res;

		double dist = dist(inputs,weights);

		return 1 -dist;
	}

	private double dist(double[] inputs, double[] weights){

		double[] dist = distAxisAbs(inputs, weights);
		double dx = dist[Space.X];
		double dy = dist[Space.Y];
		double distance = Math.sqrt(dx*dx+dy*dy)/inputSpace.getNorme();

		//System.out.println("Distance : " + distance);
		return distance;
	}

	public double[] getWeights(){
		return weights;
	}

	public Space getInputSpace() {
		return inputSpace;
	}

}
