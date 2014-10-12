/*
 * This is a Dynamic Neural Field simulator which is extended to
 *     several other neural networks and extended to hardware simulation.
 *
 *     Copyright (C) 2014  Benoît Chappet de Vangel
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package main.java.reservoirComputing;

import main.java.console.CNFTCommandLine;
import main.java.console.CommandLine;
import main.java.console.CommandLineFormatException;
import main.java.coordinates.NullCoordinateException;
import main.java.maps.*;
import main.java.model.Model;
import main.java.plot.Trace;
import main.java.space.NoDimSpace;
import main.java.space.Space;
import main.java.space.Space1D;
import main.java.space.Space2D;
import main.java.statistics.Statistics;
import main.java.unitModel.NARMAnthOrderUM;
import main.java.unitModel.RandTrajUnitModel;
import main.java.unitModel.TanhUM;
import main.java.unitModel.UnitModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

/**
 * Basic ESN main.java.model 
 * with tanh activation function
 * pseudoInverse linear regression
 * See Jaeger's Adaptative nonlinear system identification with ESN
 * 
 * @author bchappet
 *
 */
public class ModelESN3 extends Model {






	/**Reservoir of the ESN**/
	protected Map reservoir;
	/**Input : read from a file **/
	protected Map input;
	/**Output of the reservoir **/
	protected Map output;
	/**The targeted output for learning read from a file**/
	protected Map targetOutput;

	/**Weights for reservoir to reservoir neuron connection**/
	protected Map weightsRR;
	/**Weights for main.java.input to reservoir connections**/
	protected Map weightsIR;
	/**Weights for reservoir to output connections (they are learnt)**/
	protected Map weightsRO;

	private Space spaceOutput;

	StateSaver<Double> stateRes;
	StateSaver<Double> statesTargetOutput;


	public ModelESN3(String name) {
		super(name);
	}


	@Override
	public CommandLine constructCommandLine() {
		return new ESNCommandLine();
	}





	@Override
	protected void initializeParameters() throws CommandLineFormatException,
	NullCoordinateException, FileNotFoundException, IOException {
		//boolean wrap_reservoir = initScript.getBool(ESNCommandLine.WRAP_RESERVOIR);

		Var<Integer> lenght_reservoir = command.get(ESNCommandLine.LENGTH_RESERVOIR);
        int lengthReservoir = lenght_reservoir.get();
		Var<BigDecimal> dt = command.get(ESNCommandLine.DT);
        boolean withInput = (boolean) command.get(ESNCommandLine.WITH_INPUT).get();
        boolean squared = (boolean) command.get(ESNCommandLine.SQUARED).get();
        ESNUtils utils = new ESNUtils(lengthReservoir,dt,withInput,squared);
		Space1D spaceReservoir = new Space1D(lenght_reservoir);

        double spectralRadius = (double) command.get(ESNCommandLine.SPECTRAL_RADIUS).get();
        boolean sparseWeights = (boolean) command.get(ESNCommandLine.SPARSE_WEIGHTS).get();

        boolean tanh =true;

		weightsIR = utils.getWeightsInput();
        String fileWRR;
        if(sparseWeights) {
            double connectivity = (double) command.get(ESNCommandLine.CONNECTIVITY).get();
            fileWRR = utils.generateSparseRandomWeights(spectralRadius, connectivity, 1.);
        }else{
            fileWRR  = utils.generateRandomWeights(spectralRadius,1.);
        }
        weightsRR = utils.readWeightFromFile(fileWRR);
		weightsRO = utils.generatePseudoInverseLearningMatrix();
        int narmaOrder = (int) command.get(ESNCommandLine.ORDER_OUTPUT).get();
        Map[] IO =  utils.generateNARAMAIO(narmaOrder);
        input = IO[0];
        targetOutput = IO[1];

		
		Map dot_WRR_R = new MultiplicationMatrix("dot_WRR_R", dt, spaceReservoir.transpose()); 
		Map dot_WIR_I = new MultiplicationMatrix("dot_WIR_I",dt,spaceReservoir.transpose(), weightsIR, input);

        double leak = (double) command.get(ESNCommandLine.LEAK).get();
        double reservoirNoise = (double) command.get(ESNCommandLine.NOISE_AMP).get();

        reservoir = utils.getTanhReservoir(leak,reservoirNoise);
        MatrixDouble2D matReservoir = new MatrixDouble2DWrapper((Map) reservoir);
        MatrixDouble2D columnVectorMatReservoir = new TransposedMatrix(matReservoir);
        dot_WRR_R.addParameters(weightsRR,columnVectorMatReservoir);

		reservoir.addParameters(reservoir,dot_WRR_R,dot_WIR_I);
		
        Map outputPreSignal = utils.getOutputPresignal(input,matReservoir,weightsRO);
		output = utils.getOutput(outputPreSignal,tanh);
		

		Map targetOutputPresignal = utils.getTargetOutputPresignal(targetOutput,tanh);
        weightsRO.addParameters(matReservoir,targetOutputPresignal,input);

		this.root = output;
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
