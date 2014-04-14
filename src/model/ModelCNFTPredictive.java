package model;

import java.util.Arrays;
import java.util.List;

import maps.ConvolutionMatrix2D;
import maps.Leaf;
import maps.Map;
import maps.Parameter;
import maps.Track;
import maps.TrajectoryUnitMap;
import maps.Var;
import statistics.StatisticsCNFT;
import unitModel.RateCodedUnitModel;
import unitModel.UnitModel;
import console.CNFTCommandLine;
import console.CommandLineFormatException;
import coordinates.NullCoordinateException;
import coordinates.Space;

public class ModelCNFTPredictive extends ModelCNFT {
	
	protected Parameter closestTrackX; //coordonéeX de la cible la plus proche
	protected Parameter closestTrackY; //coordonéeY de la cible la plus proche
	protected Map predictiveMap; //map prédictive
	protected Map speedMapX; //vitesse X
	protected Map speedMapY; //et vitesse Y

	public ModelCNFTPredictive(String name) {
		super(name);
	}
	
	
	/**
	 * Construct the model architecture
	 * @throws CommandLineFormatException if the given parameter was not initialized in
	 * the default script or model script
	 * @throws NullCoordinateException 
	 * @throws CloneNotSupportedException 
	 */
	protected void initModel() throws CommandLineFormatException, NullCoordinateException
	{
		Var vdt = command.get(CNFTCommandLine.DT); //default dt
		initLateralWeights();
		cnft = new ConvolutionMatrix2D(CNFT,vdt,extendedComputationSpace);
		potential = new Map(POTENTIAL,new RateCodedUnitModel(),vdt,extendedComputationSpace);

		potential.addParameters(new Leaf(potential),command.get(CNFTCommandLine.TAU),
				input,cnft,command.get(CNFTCommandLine.RESTING_POTENTIAL));
		cnft.addParameters(cnftW,new Leaf(potential));
		cnft.constructMemory();
		potential.constructMemory();
		
		speedMapX = new Map("speedMapY", new UnitModel() {
			@Override
			public double compute() throws NullCoordinateException {
				Parameter closestTrackX = getParam(0);
				double cx_1 = closestTrackX.getDelay(1);
				double cx = closestTrackX.get();
				return cx - cx_1;
			}
		}, vdt, noDimSpace);
		
		speedMapY = new Map("speedMapX", new UnitModel() {
			@Override
			public double compute() throws NullCoordinateException {
				Parameter closestTrackY = getParam(0);
				
				double cy_1 = closestTrackY.getDelay(1);
				double cy = closestTrackY.get();
				return cy - cy_1;
			}
		}, vdt, noDimSpace);
		
		predictiveMap = new Map("predictiveMap",new UnitModel() {
			@Override
			public double compute() throws NullCoordinateException {
				Parameter potential = getParam(0);
				double speedMapX = getParam(1).get();
				double speedMapY = getParam(2).get();
				double a = 100;
				
				//System.out.println("speedMapX : " +  speedMapX );
				double transX = this.coord[Space.X] - speedMapX*dt.get()*a;
				double transY = this.coord[Space.Y] - speedMapY*dt.get()*a;
				
				return potential.get(space.wrap(new Double[]{transX,transY}));
			}
		}, vdt,extendedComputationSpace,new Leaf(potential), speedMapX,speedMapY);
		this.addParameters(predictiveMap);
		this.root = potential;
	}
	
	@Override
	public List<Parameter> getDefaultDisplayedParameter() {
		Parameter[] ret = {input,cnftW,cnft,potential,predictiveMap};
		return Arrays.asList(ret);
	}
	
	protected void initializeStatistics() throws CommandLineFormatException {
		super.initializeStatistics();
		closestTrackX = this.stats.getParam(StatisticsCNFT.TRACK_X);
		closestTrackY = this.stats.getParam(StatisticsCNFT.TRACK_Y);
		closestTrackX.addMemories(1); // we need to access the previous position
		closestTrackY.addMemories(1);
		speedMapX.addParameters(closestTrackX);
		speedMapY.addParameters(closestTrackY);
	}

}
