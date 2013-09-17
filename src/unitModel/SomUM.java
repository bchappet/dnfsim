package unitModel;

import java.util.Arrays;

import maps.Parameter;
import coordinates.NullCoordinateException;
import coordinates.Space;

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

	//Weight to describe the input
	protected double[] weights;

	//Input space
	protected Space inputSpace;



	public SomUM(Space inputSpace,Parameter dt, Space space, Parameter... parameters) {
		super(dt, space, parameters);
		this.inputSpace = inputSpace;
		this.weights = new double[]{0,0};
		this.weights[Space.X] = inputSpace.getUniformSample(Space.X);
		this.weights[Space.Y] = inputSpace.getUniformSample(Space.Y);
		//System.out.println("Original weights : " + Arrays.toString(weights));
	}





	@Override
	public double compute() throws NullCoordinateException {
		//first we change the weights as we need t-1 potential from WTM map
		for(int i = 0 ; i < 2 ; i++){
			learnWeights(i,
					getParam(LEARNING_RATE).get(coord),
					getParam(WTM).get(coord),
					getParam(INPUT));
		}

		//then we compute the new potential
		return computePotential();
	}

	public UnitModel setInitActivity() {
		UnitModel ret = super.setInitActivity();
		if(inputSpace != null){
			this.weights = new double[]{0,0};
			this.weights[Space.X] = inputSpace.getUniformSample(Space.X);
			this.weights[Space.Y] = inputSpace.getUniformSample(Space.Y);
			//System.out.println("Set activity weights : " + Arrays.toString(weights));
		}

		return ret;

	}

	/**
	 * Compute the new potential
	 */
	protected double computePotential() {
		//here it is the mean of W-I
		double sum = 0;
		int n = 2;
		for(int i = 0 ; i < n ; i++){
			sum +=  weights[i] - getParam(INPUT).get(i);
		}
		return sum/(double)n;


	}

	/**
	 * Learn weights according to the WTM map and the current input
	 */
	protected void learnWeights(int i,double lr,double wtm,Parameter input) {


		if(wtm < 0)
			wtm = 0;
		double delta = lr * wtm * distance(i,input,weights);

		weights[i] += delta;


	}

	/**
	 * Compute distance between the input and the weights at position i
	 * @param input2
	 * @param weights2
	 * @return
	 */
	private double distance(int i,Parameter input, double[] weights) {
		double in = input.get(i);
		double w = weights[i];

		return in - w;
	}

	public double[] getWeights(){
		return weights;
	}

	public Space getInputSpace() {
		return inputSpace;
	}

}
