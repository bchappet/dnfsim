package model;

import maps.Leaf;
import maps.Map;
import maps.MatrixFileReader;
import maps.Parameter;
import maps.Var;
import statistics.Charac;
import statistics.CharacConvergence2;
import statistics.CharacMaxMax;
import statistics.CharacMaxSum;
import statistics.CharacMeanCompTime;
import statistics.CharacNoFocus;
import statistics.CharacTestConvergence;
import statistics.Characteristics;
import statistics.Stat;
import statistics.StatMap;
import statistics.Statistics;
import unitModel.RandTrajUnitModel;
import unitModel.Sum;
import unitModel.UnitModel;
import console.CNFTCommandLine;
import console.CommandLineFormatException;
import coordinates.NullCoordinateException;

public class ModelESpikeFileReader extends ModelESpike {

	public ModelESpikeFileReader(String name) {
		super(name);
	}
	
	protected void initDefaultInput() throws CommandLineFormatException, NullCoordinateException
	{
		Parameter file = new MatrixFileReader(INPUT,command.get(CNFTCommandLine.FILE_DT),extendedFramedSpace,"src/tests/files/input");
		UnitModel noise = new RandTrajUnitModel(command.get(CNFTCommandLine.NOISE_DT),extendedFramedSpace,
				new Var(0),command.get(CNFTCommandLine.NOISE_AMP));
		Map mNoise = new Map("Noise",noise);
		mNoise.constructMemory();
		
		UnitModel sum = new Sum(command.get(CNFTCommandLine.INPUT_DT),extendedFramedSpace, file,mNoise);
		this.input = new Map(INPUT,sum);
	}
	
	public void modifyModel() throws CommandLineFormatException, NullCoordinateException {
		//nothing
	}
	
	protected void initializeStatistics() throws CommandLineFormatException {
		Stat stat = new Stat(command.get(CNFTCommandLine.STAT_DT),noDimSpace,this);
		StatMap wsum = stat.getWsum(new Leaf(potential));
		StatMap sizeBubbleH = stat.getSizeBubbleHeight(new Leaf(potential),wsum,command.get(CNFTCommandLine.ACT_THRESHOLD));
		StatMap sizeBubbleW = stat.getSizeBubbleWidth(new Leaf(potential),wsum,command.get(CNFTCommandLine.ACT_THRESHOLD));
		stats = new Statistics("Stats",command.get(CNFTCommandLine.STAT_DT),noDimSpace,
				wsum,
				stat.getTestConvergence(new Leaf(potential)),
				stat.getMax(new Leaf(potential)),
				stat.getLyapunov(new Leaf(potential), new Leaf(cnft), new Leaf(input)),
				sizeBubbleH,sizeBubbleW
				);
	}
	
	protected  void initializeCharacteristics() throws CommandLineFormatException
	{
		Charac conv = new CharacConvergence2(Characteristics.CONVERGENCE,stats, noDimSpace, this);
		Charac noFocus = new CharacNoFocus(Characteristics.NO_FOCUS, stats, noDimSpace, this, conv);
		Charac maxSum = new CharacMaxSum(Characteristics.MAX_SUM, stats, noDimSpace, this);
		Charac meanCompTime = new CharacMeanCompTime(Characteristics.MEAN_COMP_TIME, stats, noDimSpace, this, conv);
		Charac maxMax = new CharacMaxMax(Characteristics.MAX_MAX,stats,noDimSpace,this);
		Charac testConv = new CharacTestConvergence(Characteristics.TEST_CONV, stats, noDimSpace, this,
				command.get(CNFTCommandLine.WA),command.get(CNFTCommandLine.SHAPE_FACTOR),command.get(CNFTCommandLine.STAB_TIME));

		charac = new Characteristics(noDimSpace, stats, conv,noFocus,maxSum,meanCompTime,maxMax,testConv);

	}

}
