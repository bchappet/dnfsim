package main.java.reservoirComputing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.List;

import main.java.console.CNFTCommandLine;
import main.java.console.CommandLine;
import main.java.console.CommandLineFormatException;
import main.java.coordinates.NullCoordinateException;
import main.java.maps.HorizontalConcatenationMatrix;
import main.java.maps.InfiniteDt;
import main.java.maps.Map;
import main.java.maps.MatrixCSVFileReader;
import main.java.maps.MatrixDouble2D;
import main.java.maps.MatrixDouble2DWrapper;
import main.java.maps.MultiplicationMatrix;
import main.java.maps.NormalisationMatrix;
import main.java.maps.Parameter;
import main.java.maps.Trajectory;
import main.java.maps.TransposedMatrix;
import main.java.maps.UnitMap;
import main.java.maps.Var;
import main.java.maps.VectorFileReaderMap;
import main.java.model.Model;
import main.java.plot.Trace;
import main.java.space.NoDimSpace;
import main.java.space.Space;
import main.java.space.Space1D;
import main.java.space.Space2D;
import main.java.space.SpaceFactory;
import main.java.statistics.Statistics;
import main.java.unitModel.NARMAnthOrderUM;
import main.java.unitModel.RandTrajUnitModel;
import main.java.unitModel.TanhUM;
import main.java.unitModel.UnitModel;

/**
 * Basic ESN main.java.model 
 * with tanh activation function
 * pseudoInverse linear regression
 * See Jaeger's Adaptative nonlinear system identification with ESN
 * 
 * @author bchappet
 *
 */
public class ModelESN2 extends Model {

	public static final String WEIGHTS_IR = "WeightsIR";
	public static final String WEIGHTS_RR = "WeightsRR";
	public static final String WEIGHTS_RO = "WeightsRO";

	public static final String INPUT = "input";
	public static final String RESERVOIR = "reservoir";
	public static final String OUTPUT = "output";
	public static final String TARGET_OUTPUT = "target_output";




	/**Reservoir of the ESN**/
	protected Map reservoir;
	/**Input : read from a file **/
	protected Parameter input;
	/**Output of the reservoir **/
	protected Map output;
	/**The targeted output for learning read from a file**/
	protected Parameter targetOutput;

	/**Weights for reservoir to reservoir neuron connection**/
	protected Map weightsRR;
	/**Weights for main.java.input to reservoir connections**/
	protected Map weightsIR;
	/**Weights for reservoir to output connections (they are learnt)**/
	protected Map weightsRO;
	
	private Space spaceOutput;
	
	StateSaver<Double> stateRes; 
	StateSaver<Double> statesTargetOutput; 
	

	public ModelESN2(String name) {
		super(name);
	}


	@Override
	public CommandLine constructCommandLine() {
		return new ESNCommandLine();
	}
	
	public static void generateRRWeightMatrix(int res, double rhowT,String outFile) throws IOException{
		 String s = null;
		 File workingDirectory = new File("src/main/java/reservoirComputing/scripts/rayonSpectral/");
		 Process p = Runtime.getRuntime().exec("./generateRandomScaledWeigth.sh " + res + " " + rhowT + " " + outFile,null,workingDirectory);
		 
	}
	
//	public static void main(String[] args) throws IOException{
//		generateRRWeightMatrix(100,0.8,"../../weights/weights100_0.8_0.csv");
//	}
	
/**
	 * Separate the input generation for a better modularity
	 * @return
	 * @throws CommandLineFormatException
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	protected Trajectory inputMem;
	protected Parameter getInput() throws CommandLineFormatException, FileNotFoundException, IOException{
		Var<BigDecimal> dt_input = command.get(ESNCommandLine.DT);

		
		
		
		Var<Integer> N = command.get(ESNCommandLine.ORDER_OUTPUT);
		inputMem =new Trajectory<Double>(INPUT, dt_input, 
				new RandTrajUnitModel(0d),new Var<>("center",0.25),new Var<>("radius",0.25) );
		Double[] mem = new Double[N.get()];
		for(int i = 0 ; i < N.get() ; i++){
			mem[i] = Math.random()*0.5;
		}
		inputMem.addMemories(N.get(),mem);
		
		MatrixDouble2D inputRes = new MatrixDouble2DWrapper(inputMem);
		return inputRes;
		
	}
	/**
	 * Separate the output generation for a better modularity
	 * 
	 * @return
	 * @throws CommandLineFormatException 
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	protected Parameter getTargetOutput() throws CommandLineFormatException, FileNotFoundException, IOException{
		Var<BigDecimal> dt= command.get(ESNCommandLine.DT);

		Var<Integer> N = command.get(ESNCommandLine.ORDER_OUTPUT);
		Trajectory target = new Trajectory<Double>(TARGET_OUTPUT+"_tmp", dt, new NARMAnthOrderUM(0d));
		target.addParameters(inputMem,target,N);
		target.addMemories(N.get());
		
		MatrixDouble2D targetRes = new MatrixDouble2DWrapper(TARGET_OUTPUT,target);
		return targetRes;

	}


	@Override
	protected void initializeParameters() throws CommandLineFormatException,
	NullCoordinateException, FileNotFoundException, IOException {
		//boolean wrap_reservoir = initScript.getBool(ESNCommandLine.WRAP_RESERVOIR);

		Var<Integer> lenght_reservoir = command.get(ESNCommandLine.LENGTH_RESERVOIR);
		Var<BigDecimal> dt = command.get(ESNCommandLine.DT);


		Space1D spaceReservoir = new Space1D(lenght_reservoir); 

		Parameter weightsRRFileName = command.get(ESNCommandLine.WRR_FILE);
		Parameter weightsROFileName = command.get(ESNCommandLine.WRO_FILE);
		Parameter currentSep = command.get(ESNCommandLine.SEP);

		Space spaceWeightsReservoir = new Space2D(lenght_reservoir,lenght_reservoir);

//		weightsIR = new TransposedMatrix(new  MatrixDouble2DWrapper(
//				new UnitMap(WEIGHTS_IR,new InfiniteDt(), spaceReservoir,
//						new RandomlyChoosenFromUniformUM(0d), new Var<Double>(-0.1d),new Var<Double>(0.1d))));
		weightsIR = new TransposedMatrix(new  MatrixDouble2DWrapper(new UnitMap(WEIGHTS_IR,new InfiniteDt(), spaceReservoir,new RandTrajUnitModel(0d), new Var<Double>(0d),new Var<Double>(0.1d))));
		weightsRR = new MatrixCSVFileReader(WEIGHTS_RR, new InfiniteDt(), spaceWeightsReservoir, weightsRRFileName,currentSep);
		weightsRO = new  LearningWeightMatrixWithInput(WEIGHTS_RO, dt, new Space2D(lenght_reservoir,new Var<Integer>(1)));
		input = getInput();
		
		
		//Construct noise map
		Map mNoise = new UnitMap("Noise",dt,spaceReservoir,new RandTrajUnitModel(0.),
				new Var(0),command.get(ESNCommandLine.NOISE_AMP));		

		
		Map dot_WRR_R = new MultiplicationMatrix("dot_WRR_R", dt, spaceReservoir.transpose()); 
		Map dot_WIR_I = new MultiplicationMatrix("dot_WIR_I",dt,spaceReservoir.transpose(), weightsIR, input);
		reservoir = new UnitMap(RESERVOIR,dt,spaceReservoir,new TanHReservoirNeuronUM(0d));
		reservoir.addParameters(dot_WRR_R,dot_WIR_I,mNoise);
		
		MatrixDouble2D matReservoir = new MatrixDouble2DWrapper((Map) reservoir);
		MatrixDouble2D columnVectorMatReservoir = new TransposedMatrix(matReservoir);
		dot_WRR_R.addParameters(weightsRR,columnVectorMatReservoir);
		
		targetOutput = getTargetOutput();
		weightsRO.addParameters(command.get(ESNCommandLine.REGULARIZATION_FACTOR),matReservoir,targetOutput,input);

		output = new UnitMap(OUTPUT,dt,new NoDimSpace(),new TanhUM(0d),
				new MultiplicationMatrix(OUTPUT+"_lin",dt,new NoDimSpace(),weightsRO,new HorizontalConcatenationMatrix(input,columnVectorMatReservoir)));
		//The output is a tanh activation function
		
		
		
				
				
		
		this.root = output;
		addParameters(command.get(ESNCommandLine.LEAK),command.get(ESNCommandLine.INPUT_SCALE),command.get(ESNCommandLine.ALPHA));
	}
	
	


	@Override
	protected void initializeStatistics() throws CommandLineFormatException {
		// main statistic here is error
		Var<BigDecimal> stat_dt = command.get(CNFTCommandLine.STAT_DT);
		
		
		
		
		
		//The error is (estimator - target)^2
		Trajectory error = new Trajectory(StatisticsESN.ERROR_DIST,stat_dt,new UnitModel<Double>(0d) {
			@Override
			public Double compute(BigDecimal time,int index, List<Parameter> params) {
				Parameter<Double> output =  params.get(0);
				Parameter<Double> targetOutput =  params.get(1);
				return Math.pow(output.getIndex(index) - targetOutput.getIndex(index),2);
			}
		},output,targetOutput);
		


		stats = new StatisticsESN(Statistics.NAME,stat_dt,error,targetOutput,output);


	}

	@Override
	protected void initializeCharacteristics()
			throws CommandLineFormatException {
		//The main characteristic is NRMSE cf butcher et al 2013
		
		Var<BigDecimal> dt = command.get(ESNCommandLine.DT);
	
		
		Trajectory<Double> mse = new Trajectory<Double>(CharacteristicsESN.MSE,new InfiniteDt(),new UnitModel<Double>(0d) {
			@Override
			public Double compute(BigDecimal time,int index, List<Parameter> params) {
				Statistics stats = (Statistics) params.get(0);
				double lengthErrorSecond = (double) params.get(1).getIndex(0);
				double dt =(( BigDecimal)(params.get(2).getIndex(0))).doubleValue();
				int iterationError = (int) (lengthErrorSecond/dt);
				
				Trace error = stats.getTrace(StatisticsESN.ERROR_DIST);
				Trace targetedOutput = stats.getTrace(StatisticsESN.TARGET_OUTPUT);
				
				double meanError = error.getMean(iterationError);

				return meanError;
			}
		},stats,command.get(ESNCommandLine.LENGTH_ERROR),dt);
		
		//Root mean squared error
		Trajectory<Double> rmse = new Trajectory<Double>(CharacteristicsESN.RMSE,new InfiniteDt(),new UnitModel<Double>(0d) {
			@Override
			public Double compute(BigDecimal time,int index, List<Parameter> params) {
				Trajectory<Double> mse =  (Trajectory<Double>) params.get(0);
				return Math.sqrt(mse.get());

			}
		},mse);
				
		//Normalize Root mean squared error (divised by the range of observed values)
		Trajectory<Double> nrmse = new Trajectory<Double>(CharacteristicsESN.NRMSE,new InfiniteDt(),new UnitModel<Double>(0d) {
			@Override
			public Double compute(BigDecimal time,int index, List<Parameter> params) {
				Statistics stats = (Statistics) params.get(0);
				double lengthErrorSecond = (double) params.get(1).getIndex(0);
				double dt =(( BigDecimal)(params.get(2).getIndex(0))).doubleValue();
				int iterationError = (int) (lengthErrorSecond/dt);
				Trace output = stats.getTrace(StatisticsESN.OUTPUT);
				double rangeObserved = output.getRange(iterationError);
				Trajectory<Double> rmse =  (Trajectory<Double>) params.get(3);
				return rmse.get()/rangeObserved;
			}
		},stats,command.get(ESNCommandLine.LENGTH_ERROR),dt,rmse);
		
		charac = new CharacteristicsESN(mse,rmse,nrmse);
	}



	

	@Override
	public void modifyModel() throws CommandLineFormatException,
	NullCoordinateException {
		//NOTHING

	}


	
	public String getText() {
		return "Basic ESN main.java.model with tanh activation function in reservoir neurons, one main.java.input and one output.";
	}


	


	

}
