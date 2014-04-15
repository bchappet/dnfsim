package reservoirComputing;

import java.util.Arrays;
import java.util.List;

import maps.AbstractMap;
import maps.Leaf;
import maps.Map;
import maps.MatrixCSVFileReader;
import maps.MultiplicationMatrix;
import maps.Parameter;
import maps.TrajectoryUnitMap;
import maps.Var;
import model.Model;
import plot.Trace;
import statistics.Charac;
import statistics.StatCNFT;
import statistics.StatMap;
import unitModel.RandTrajUnitModel;
import unitModel.UniformRandomUM;
import console.CNFTCommandLine;
import console.CommandLineFormatException;
import coordinates.DiscreteSpace;
import coordinates.DiscreteSquareSpace;
import coordinates.NoDimSpace;
import coordinates.NullCoordinateException;
import coordinates.Space;
import fft.FFTConvolutionMatrix2D;

/**
 * Basic ESN model 
 * with tanh activation function
 * pseudoInverse linear regression
 * 
 * @author bchappet
 *
 */
public class ModelESN extends Model {

	public static final String WEIGHTS_IR = "WeightsIR";
	public static final String WEIGHTS_RR = "WeightsRR";
	public static final String WEIGHTS_RO = "WeightsRO";

	public static final String INPUT = "input";
	public static final String RESERVOIR = "reservoir";
	public static final String OUTPUT = "output";
	public static final String TARGET_OUTPUT = "target_output";




	/**Reservoir of the ESN**/
	protected Parameter reservoir;
	/**Input : read from a file **/
	protected Parameter input;
	/**Output of the reservoir **/
	protected Parameter output;
	/**The targeted output for learning read from a file**/
	protected Parameter targetOutput;

	/**Weights for reservoir to reservoir neuron connection**/
	protected Parameter weightsRR;
	/**Weights for input to reservoir connections**/
	protected Parameter weightsIR;
	/**Weights for reservoir to output connections (they are learnt)**/
	protected Parameter weightsRO;
	
	private Space spaceOutput;

	public ModelESN(String name) {
		super(name);
	}


	/**
	 * Separate the input generation for a better modularity
	 * @return
	 * @throws CommandLineFormatException
	 */
	protected Parameter getInput() throws CommandLineFormatException{
		Var dt_input = command.get(ESNCommandLine.DT_INPUT);
		Var lenght_input = command.get(ESNCommandLine.LENGHT_INPUT);
		Space space_input = new DiscreteSquareSpace(lenght_input,1,false);
		space_input.setSimulationSpace(new  DiscreteSquareSpace(new Var(Math.sqrt(lenght_input.val)), 2,false ));
//		space_input.setDimension(new int[]{0,0});
//		Space space_input = new NoDimSpace();

		Parameter inputRes = new Map(INPUT,new UniformRandomUM(dt_input,space_input,new Var(-1),new Var(1)));
		return inputRes;
	}
	
	@Override
	protected void initializeCommandLine(String contextScript)
			throws CommandLineFormatException {
		command = new ESNCommandLine(contextScript);
		
	}


	/**
	 * Separate the output generation for a better modularity
	 * Here we use polynomial  output with non linearity and memory parameters
	 * CF paper Butcher et all 2013
	 * 
	 * @return
	 * @throws CommandLineFormatException 
	 */
	protected Parameter getTargetOutput() throws CommandLineFormatException{
		Parameter output_memory = command.get(ESNCommandLine.OUTPUT_MEMORY);
		Parameter polynomial_degree = command.get(ESNCommandLine.OUTPUT_POLYNOMIAL_DEGREE);


		Var dtOutput = command.get(ESNCommandLine.DT_OUTPUT);
		Var length_output = command.get(ESNCommandLine.LENGHT_OUTPUT);
//		Space spaceOutput = new DiscreteSquareSpace(length_output, 1, false);
		
		//The space for coefficient depends on the polynom degree
		double p = (int)polynomial_degree.get();
		Space spaceCoefficient = new DiscreteSpace(new Double[]{p+1,p}, false);
		//Rand unif generate the random coefficients of the polynom, they are fixed (static)
		Parameter randUnif = new Map("targeted_output_coeficients",new UniformRandomUM( dtOutput,spaceCoefficient,new Var(-1),new Var(1)));
		randUnif.constructMemory();
		//System.out.println(((AbstractMap) randUnif).display2D());

		Parameter  outputRes = new Map(TARGET_OUTPUT,new PolynomialUM(dtOutput,spaceOutput,input,randUnif,output_memory,polynomial_degree));
		return outputRes;

	}


	@Override
	protected void initializeParameters() throws CommandLineFormatException,
	NullCoordinateException {
		Var learningRate = command.get(ESNCommandLine.LEARNING_RATE);
		//boolean wrap_reservoir = command.getBool(ESNCommandLine.WRAP_RESERVOIR);

		Var lenght_reservoir = command.get(ESNCommandLine.LENGTH_RESERVOIR);
		Var length_output = command.get(ESNCommandLine.LENGHT_OUTPUT);

		Var dtReservoir = command.get(ESNCommandLine.DT_RESERVOIR);
		Var dtLearning = command.get(ESNCommandLine.DT_LEARNING);
		Var dtOutput = command.get(ESNCommandLine.DT_OUTPUT);

		Space spaceReservoir = new DiscreteSquareSpace(lenght_reservoir, 1,false ); //wrap or no is does not change anything here
		spaceReservoir.setSimulationSpace(new  DiscreteSquareSpace(new Var(Math.sqrt(lenght_reservoir.val)), 2,false ));
		//System.out.println("Space reservoir : " + spaceReservoir);
//		Space spaceOutput = new DiscreteSquareSpace(length_output, 1, false);
		spaceOutput = new DiscreteSquareSpace(length_output,1,false);
		spaceOutput.setSimulationSpace(new  DiscreteSquareSpace(new Var(Math.sqrt(length_output.val)), 2,false ));

		String weightsRRFileName = command.getString(ESNCommandLine.WRR_FILE);
		String weightsROFileName = command.getString(ESNCommandLine.WRO_FILE);

		Space spaceWeightsReservoir = new DiscreteSquareSpace(lenght_reservoir, 2, false);


		weightsIR = new Map(WEIGHTS_IR,new RandTrajUnitModel(dtReservoir, spaceReservoir, new Var(0),new Var(1)));
		weightsIR.toStatic();
		weightsRR = new MatrixCSVFileReader(WEIGHTS_RR, dtReservoir, spaceWeightsReservoir, weightsRRFileName,",");
		weightsRR.toStatic();
		
		weightsRO = new  MatrixCSVFileReader(WEIGHTS_RO, dtOutput, spaceReservoir,weightsROFileName,",");
		weightsRO.toStatic();
		input = getInput();
		Parameter conv_WRR_R = new MultiplicationMatrix("conv_WRR_R", dtReservoir, spaceReservoir); 
		//Parameter conv_WIR_I = new FFTConvolutionMatrix2D("conv_WIR_I", dtReservoir, spaceReservoir, weightsIR,input);
		Parameter conv_WIR_I = new MultiplicationMatrix("conv_WIR_I",dtReservoir,spaceReservoir,weightsIR,input);
		reservoir = new Map(RESERVOIR,new TanHReservoirNeuronUM(dtReservoir,spaceReservoir,conv_WRR_R,conv_WIR_I));
		conv_WRR_R.addParameters(weightsRR,new Leaf(reservoir));
		targetOutput = getTargetOutput();
		weightsRO.addParameters(new Leaf(reservoir),targetOutput);

		Parameter output_tmp = new MultiplicationMatrix(OUTPUT+"_tmp",dtOutput,spaceReservoir,weightsRO,reservoir);
		//The output is a linear identity activation function
		output = new TrajectoryUnitMap(OUTPUT,dtOutput,spaceOutput,output_tmp) {
			@Override
			public double computeTrajectory(double... param) {
				double values[] = getParam(0).getValues();
				double sum = 0;
				for(int i = 0 ; i < values.length ; i++){
					sum += values[i];
				}
				return sum;
			}
		};
		output.constructAllMemories();
		targetOutput.constructAllMemories();
		
		root = (AbstractMap) reservoir;
		addParameters(output);




	}

	@Override
	protected void initializeStatistics() throws CommandLineFormatException {
		// main statistic here is error
		Var stat_dt = command.get(CNFTCommandLine.STAT_DT);
		StatCNFT stat = new StatCNFT(stat_dt,this);
		Space noDimSpace = new NoDimSpace();
		//The error is (estimator - target)^2
		StatMap error = new StatMap(StatisticsESN.ERROR_DIST,stat_dt,noDimSpace,new Leaf(output),new Leaf(targetOutput)) {
			@Override
			public double computeStatistic() {
				Parameter output = getParam(0);
				Parameter targetOutput = getParam(1);
				return Math.pow(output.get() - targetOutput.get(),2);
			}
		};
		

		//The target output
		StatMap targetedOutput = new StatMap(StatisticsESN.TARGETED_OUTPUT,stat_dt,noDimSpace,new Leaf(targetOutput)) {
			@Override
			public double computeStatistic() {
				return getParam(0).get();
			}
		};
		//The output
		StatMap outputStat = new StatMap(StatisticsESN.OUTPUT,stat_dt,noDimSpace,new Leaf(output)) {
			@Override
			public double computeStatistic() {
				return getParam(0).get();
			}
		};

		stats = new StatisticsESN("Stats",stat_dt,noDimSpace,error,targetedOutput,outputStat);


	}

	@Override
	protected void initializeCharacteristics()
			throws CommandLineFormatException {
		//The main characteristic is NRMSE cf butcher et al 2013
		Space noDimSpace = new NoDimSpace();
		Charac nrmse = new Charac(CharacteristicsESN.NRMSE,stats,noDimSpace,this) {

			@Override
			public double computeTrajectory(double... param) {
				Trace error = stats.getTrace(StatisticsESN.ERROR_DIST);
				Trace targetedOutput = stats.getTrace(StatisticsESN.TARGETED_OUTPUT);
				
				double meanError = error.getMean();
				double varTargetedOutput = targetedOutput.getVar();

				return Math.sqrt(meanError)/Math.pow(varTargetedOutput, 2);
			}
		};
		charac = new CharacteristicsESN(noDimSpace, stats, nrmse);

	}



	@Override
	public List<Parameter> getDefaultDisplayedParameter() {
		Parameter[] ret = {input,reservoir,output,targetOutput,weightsIR,weightsRR,weightsRO};
		return Arrays.asList(ret);
	}

	@Override
	public void modifyModel() throws CommandLineFormatException,
	NullCoordinateException {
		//NOTHING

	}


	@Override
	public String getDefaultDisplayedStatistic() {
		return StatisticsESN.ERROR_DIST;
	}
	
	public String getText() {
		return "Basic ESN model with tanh activation function in reservoir neurons, one input and one output.";
	}


	

}
