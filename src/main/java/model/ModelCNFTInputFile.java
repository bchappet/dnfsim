package main.java.model;

import java.util.Arrays;
import java.util.List;

import main.java.console.CNFTCommandLine;
import main.java.console.CommandLineFormatException;
import main.java.coordinates.NullCoordinateException;
import main.java.maps.Leaf;
import main.java.maps.Map;
import main.java.maps.MatrixFileReader;
import main.java.maps.Parameter;
import main.java.maps.Var;
import main.java.statistics.Charac;
import main.java.statistics.CharacConvergence2;
import main.java.statistics.CharacMaxMax;
import main.java.statistics.CharacMaxSum;
import main.java.statistics.CharacMeanCompTime;
import main.java.statistics.CharacNoFocus;
import main.java.statistics.CharacTestConvergence;
import main.java.statistics.CharacteristicsCNFT;
import main.java.statistics.StatCNFT;
import main.java.statistics.StatMapCNFT;
import main.java.statistics.StatisticsCNFT;
import main.java.unitModel.RandTrajUnitModel;
import main.java.unitModel.Sum;
import main.java.unitModel.UnitModel;

public class ModelCNFTInputFile extends ModelCNFTFFT {
	
	Map mNoise;
	Parameter file;

	public ModelCNFTInputFile(String name) {
		super(name);
	}
	
	protected void initDefaultInput() throws CommandLineFormatException, NullCoordinateException
	{
		 file = new MatrixFileReader("file",command.get(CNFTCommandLine.FILE_DT),extendedFramedSpace,"src/tests/files/main.java.input");
		UnitModel noise = new RandTrajUnitModel(command.get(CNFTCommandLine.NOISE_DT),extendedFramedSpace,
				new Var(0),command.get(CNFTCommandLine.NOISE_AMP));
		 mNoise = new Map("Noise",noise);
		mNoise.constructMemory();
		
		UnitModel sum = new Sum(command.get(CNFTCommandLine.INPUT_DT),extendedFramedSpace, file,mNoise);
		this.input = new Map(INPUT,sum);
	}
	
	
	@Override
	public List<Parameter> getDefaultDisplayedParameter() {
		Parameter[] ret = {file,mNoise,input,cnftW,cnft,potential};
		return Arrays.asList(ret);
	}
	
	public void modifyModel() throws CommandLineFormatException, NullCoordinateException {
		//nothing
	}
	
	protected  void initializeCharacteristics() throws CommandLineFormatException
	{
		Charac conv = new CharacConvergence2(CharacteristicsCNFT.CONVERGENCE,stats, noDimSpace, this);
		Charac noFocus = new CharacNoFocus(CharacteristicsCNFT.NO_FOCUS, stats, noDimSpace, this, conv);
		Charac maxSum = new CharacMaxSum(CharacteristicsCNFT.MAX_SUM, stats, noDimSpace, this);
		Charac meanCompTime = new CharacMeanCompTime(CharacteristicsCNFT.MEAN_COMP_TIME, stats, noDimSpace, this, conv);
		Charac maxMax = new CharacMaxMax(CharacteristicsCNFT.MAX_MAX,stats,noDimSpace,this);
		Charac testConv = new CharacTestConvergence(CharacteristicsCNFT.TEST_CONV, stats, noDimSpace, this,
				command.get(CNFTCommandLine.WA),command.get(CNFTCommandLine.SHAPE_FACTOR),command.get(CNFTCommandLine.STAB_TIME));

		charac = new CharacteristicsCNFT(noDimSpace, stats, conv,noFocus,maxSum,meanCompTime,maxMax,testConv);

	}
	
	protected void initializeStatistics() throws CommandLineFormatException {
		StatCNFT stat = new StatCNFT(command.get(CNFTCommandLine.STAT_DT),noDimSpace,this);
		StatMapCNFT wsum = stat.getWsum(new Leaf(potential));
		StatMapCNFT sizeBubbleH = stat.getSizeBubbleHeight(new Leaf(potential),wsum,command.get(CNFTCommandLine.ACT_THRESHOLD));
		StatMapCNFT sizeBubbleW = stat.getSizeBubbleWidth(new Leaf(potential),wsum,command.get(CNFTCommandLine.ACT_THRESHOLD));
		stats = new StatisticsCNFT("Stats",command.get(CNFTCommandLine.STAT_DT),noDimSpace,
				wsum,
				stat.getTestConvergence(new Leaf(potential)),
				stat.getMax(new Leaf(potential)),
				stat.getLyapunov(new Leaf(potential), new Leaf(cnft), new Leaf(input)),
				sizeBubbleH,sizeBubbleW
				);
	}

}
