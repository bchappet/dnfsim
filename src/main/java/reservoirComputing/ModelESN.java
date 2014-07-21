package main.java.reservoirComputing;

import java.math.BigDecimal;
import java.util.List;

import main.java.console.CNFTCommandLine;
import main.java.console.CommandLine;
import main.java.console.CommandLineFormatException;
import main.java.coordinates.NullCoordinateException;
import main.java.maps.InfiniteDt;
import main.java.maps.Map;
import main.java.maps.MatrixCSVFileReader;
import main.java.maps.MatrixDouble2DWrapper;
import main.java.maps.MultiplicationMatrix;
import main.java.maps.Parameter;
import main.java.maps.Trajectory;
import main.java.maps.UnitMap;
import main.java.maps.Var;
import main.java.model.Model;
import main.java.plot.Trace;
import main.java.space.NoDimSpace;
import main.java.space.Space;
import main.java.space.Space1D;
import main.java.space.Space2D;
import main.java.statistics.Statistics;
import main.java.unitModel.ComputeUM;
import main.java.unitModel.RandTrajUnitModel;
import main.java.unitModel.UniformRandomUM;
import main.java.unitModel.UnitModel;

/**
 * Basic ESN main.java.model 
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

	public ModelESN(String name) {
		super(name);
	}


	/**
	 * Separate the main.java.input generation for a better modularity
	 * @return
	 * @throws CommandLineFormatException
	 */
	protected Parameter getInput() throws CommandLineFormatException{
		Var<BigDecimal> dt_input = command.get(ESNCommandLine.DT_INPUT);
		Var<Integer> lenght_input = command.get(ESNCommandLine.LENGHT_INPUT);
		Space<Integer> space_input = new Space1D(lenght_input);
//		space_input.setSimulationSpace(new  DiscreteSquareSpace(new Var(Math.sqrt(lenght_input.val)), 2,false ));
//		space_input.setDimension(new int[]{0,0});
//		Space space_input = new NoDimSpace();

		Map inputRes = new UnitMap(INPUT,dt_input,space_input,new UniformRandomUM(0d),new Var(-1),new Var(1));
		
		return inputRes;
	}
	
	
	@Override
	public CommandLine constructCommandLine() {
		return new ESNCommandLine();
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
		Var<Integer> polynomial_degree = command.get(ESNCommandLine.OUTPUT_POLYNOMIAL_DEGREE);
//		System.out.println("polynomila degre " + polynomial_degree.get());
		Trajectory<Integer> degre_plus1 = 
				new Trajectory<Integer>(ESNCommandLine.OUTPUT_POLYNOMIAL_DEGREE + "+1",
						new InfiniteDt(), new ComputeUM(polynomial_degree.get()+1), new Var<String>("$1+1"),polynomial_degree);

		Var<BigDecimal> dtOutput = command.get(ESNCommandLine.DT_OUTPUT);
		Var<Integer> length_output = command.get(ESNCommandLine.LENGHT_OUTPUT);
//		Space spaceOutput = new DiscreteSquareSpace(length_output, 1, false);
		
		//The main.java.space for coefficient depends on the polynom degree
		double p = polynomial_degree.get();
		Space spaceCoefficient = new Space2D(degre_plus1,polynomial_degree);
		//Rand unif generate the random coefficients of the polynom, they are fixed (static)
		Parameter randUnif = new UnitMap("targeted_output_coeficients",dtOutput,spaceCoefficient,new UniformRandomUM(0d),new Var(-1),new Var(1));
		//System.out.println(((AbstractMap) randUnif).display2D());
		((UnitMap) input).addMemories((Integer) output_memory.getIndex(0),null);
		Parameter  outputRes = new UnitMap(TARGET_OUTPUT,dtOutput,spaceOutput,new PolynomialUM(0d),input,randUnif,output_memory,polynomial_degree);
		return outputRes;

	}


	@Override
	protected void initializeParameters() throws CommandLineFormatException,
	NullCoordinateException {
		Var learningRate = command.get(ESNCommandLine.LEARNING_RATE);
		//boolean wrap_reservoir = initScript.getBool(ESNCommandLine.WRAP_RESERVOIR);

		Var<Integer> lenght_reservoir = command.get(ESNCommandLine.LENGTH_RESERVOIR);
		Var length_output = command.get(ESNCommandLine.LENGHT_OUTPUT);

		Var<BigDecimal> dtReservoir = command.get(ESNCommandLine.DT_RESERVOIR);
		Var<BigDecimal>  dtLearning = command.get(ESNCommandLine.DT_LEARNING);
		Var<BigDecimal>  dtOutput = command.get(ESNCommandLine.DT_OUTPUT);

		Space2D spaceReservoir = new Space2D(new Var<Integer>(1),lenght_reservoir); 
		spaceOutput = new Space1D(length_output);

		Parameter weightsRRFileName = command.get(ESNCommandLine.WRR_FILE);
		Parameter weightsROFileName = command.get(ESNCommandLine.WRO_FILE);
		Parameter currentSep = command.get(ESNCommandLine.SEP);

		Space spaceWeightsReservoir = new Space2D(lenght_reservoir,lenght_reservoir);


		weightsIR = new UnitMap(WEIGHTS_IR,new InfiniteDt(), spaceReservoir,new RandTrajUnitModel(0d), new Var<Double>(0d),new Var<Double>(1d));
		weightsRR = new MatrixCSVFileReader(WEIGHTS_RR, new InfiniteDt(), spaceWeightsReservoir, weightsRRFileName,currentSep);
		weightsRO = new  MatrixCSVFileReader(WEIGHTS_RO, new InfiniteDt(), new Space2D(lenght_reservoir,new Var<Integer>(1)),weightsROFileName,currentSep);
		input = getInput();
		Map conv_WRR_R = new MultiplicationMatrix("conv_WRR_R", dtReservoir, spaceReservoir); 
		//Parameter conv_WIR_I = new FFTConvolutionMatrix2D("conv_WIR_I", dtReservoir, spaceReservoir, weightsIR,main.java.input);
		Map conv_WIR_I = new MultiplicationMatrix("conv_WIR_I",dtReservoir,spaceReservoir,new MatrixDouble2DWrapper( weightsIR),new MatrixDouble2DWrapper((Map) input));
		reservoir = new UnitMap(RESERVOIR,dtReservoir,spaceReservoir,new TanHReservoirNeuronUM(0d),conv_WRR_R,conv_WIR_I);
		conv_WRR_R.addParameters(new MatrixDouble2DWrapper(weightsRR),new MatrixDouble2DWrapper((Map) reservoir));
		targetOutput = getTargetOutput();
		weightsRO.addParameters(reservoir,targetOutput);

		Map output_tmp = new MultiplicationMatrix(OUTPUT+"_tmp",dtOutput,spaceReservoir,new MatrixDouble2DWrapper(weightsRO),new MatrixDouble2DWrapper((Map) (reservoir)));
		//The output is a linear identity activation function
		output = new Trajectory(OUTPUT,dtOutput,new UnitModel<Double>(0d) {
			@Override
			public Double compute(BigDecimal time,int index, List<Parameter> params) {
				List<Double> values  = params.get(0).getValues();
				double sum = 0;
				for(int i = 0 ; i < values.size() ; i++){
					sum += values.get(i);
				}
				return sum;
			}
		},output_tmp);
		
		this.root = output;
		addParameters(targetOutput);



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
		Space noDimSpace = new NoDimSpace();
		Trajectory nrmse = new Trajectory(StatisticsESN.ERROR_DIST,new InfiniteDt(),new UnitModel<Double>(0d) {
			@Override
			public Double compute(BigDecimal time,int index, List<Parameter> params) {
				Statistics stats = (Statistics) params.get(0);
				Trace error = stats.getTrace(StatisticsESN.ERROR_DIST);
				Trace targetedOutput = stats.getTrace(StatisticsESN.TARGET_OUTPUT);
				
				double meanError = error.getMean();
				double varTargetedOutput = targetedOutput.getVar();

				return Math.sqrt(meanError)/Math.pow(varTargetedOutput, 2);
			}
		},stats);
		
		charac = new CharacteristicsESN(nrmse);
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
