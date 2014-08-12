package main.java.reservoirComputing;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import main.java.console.CNFTCommandLine;
import main.java.console.CommandLine;
import main.java.console.CommandLineFormatException;
import main.java.coordinates.NullCoordinateException;
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
import main.java.unitModel.RandTrajUnitModel;
import main.java.unitModel.UnitModel;
import Jama.Matrix;

/**
 * Basic ESN main.java.model 
 * with tanh activation function
 * pseudoInverse linear regression
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
	
/**
	 * Separate the input generation for a better modularity
	 * @return
	 * @throws CommandLineFormatException
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	protected Parameter getInput() throws CommandLineFormatException, FileNotFoundException, IOException{
		Var<BigDecimal> dt_input = command.get(ESNCommandLine.DT);
		Var<String> input_file = command.get(ESNCommandLine.INPUT_FILE);
		Var<String> currentSep = command.get(ESNCommandLine.SEP);
		MatrixDouble2D inputRes  = new VectorFileReaderMap(INPUT,dt_input,SpaceFactory.getSpace1D(input_file.get(),currentSep.get()),input_file,currentSep);
		MatrixDouble2D normalize = new NormalisationMatrix(inputRes,command.get(ESNCommandLine.INPUT_SCALE));
		return normalize;
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
		Var<BigDecimal> dt_input = command.get(ESNCommandLine.DT);
		Var<String> input_file = command.get(ESNCommandLine.TGT_OUTPUT_FILE);
		Var<String> currentSep = command.get(ESNCommandLine.SEP);
		Parameter ret  = new VectorFileReaderMap(TARGET_OUTPUT,dt_input,SpaceFactory.getSpace1D(input_file.get(),currentSep.get()),input_file,currentSep);
		return ret;

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

		weightsIR = new TransposedMatrix(new  MatrixDouble2DWrapper(new UnitMap(WEIGHTS_IR,new InfiniteDt(), spaceReservoir,new RandTrajUnitModel(0d), new Var<Double>(0d),new Var<Double>(0.5d))));
		weightsRR = new MatrixCSVFileReader(WEIGHTS_RR, new InfiniteDt(), spaceWeightsReservoir, weightsRRFileName,currentSep);
		weightsRO = new  LearningWeightMatrix(WEIGHTS_RO, dt, new Space2D(lenght_reservoir,new Var<Integer>(1)));
		input = getInput();
		
		
		
		
		
		Map dot_WRR_R = new MultiplicationMatrix("dot_WRR_R", dt, spaceReservoir.transpose()); 
		Map dot_WIR_I = new MultiplicationMatrix("dot_WIR_I",dt,spaceReservoir.transpose(), weightsIR, input);
		reservoir = new UnitMap(RESERVOIR,dt,spaceReservoir,new TanHReservoirNeuronUM(0d));
		reservoir.addParameters(command.get(ESNCommandLine.LEAK),reservoir,dot_WRR_R,dot_WIR_I);
		
		MatrixDouble2D matReservoir = new MatrixDouble2DWrapper((Map) reservoir);
		MatrixDouble2D columnVectorMatReservoir = new TransposedMatrix(matReservoir);
		dot_WRR_R.addParameters(weightsRR,columnVectorMatReservoir);
		
		targetOutput = getTargetOutput();
		weightsRO.addParameters(matReservoir,targetOutput);

		output = new MultiplicationMatrix(OUTPUT,dt,new NoDimSpace(),weightsRO,columnVectorMatReservoir);
		//The output is a linear identity activation function
		
		
		
				
				
		
		this.root = output;
		addParameters(command.get(ESNCommandLine.LEAK),command.get(ESNCommandLine.INPUT_SCALE));
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