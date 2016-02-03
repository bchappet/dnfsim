package optimisation;

import java.util.concurrent.BlockingQueue;

import statistics.Characteristics;
import statistics.CharacteristicsCNFT;

import model.Model;

public class CMSVAIndivEvaluator extends CNFTIndivEvaluator {

	public CMSVAIndivEvaluator(BlockingQueue<Model> modelPool,
			CNFTGALauncher gaLauncher, String[] scenarios,
			String[] parameterNames, int[] nbIteration) {
		super(modelPool, gaLauncher, scenarios, parameterNames, nbIteration);
	}
	
	protected  double  getScenarioFitness(int numInd,int numIt,int scenarioId, Characteristics charac){
		charac.compute();
		double conv =  charac.getParam(CharacteristicsCNFT.CONVERGENCE).get();
		double noFocus = charac.getParam(CharacteristicsCNFT.NO_FOCUS).get();
		double obstinacy = charac.getParam(CharacteristicsCNFT.OBSTINACY).get();
		double meanError  = charac.getParam(CharacteristicsCNFT.MEAN_ERROR).get();
		double accError = charac.getParam(CharacteristicsCNFT.ACC_ERROR).get();
		double fitness = 0;
		if(scenarioId != SWITCHING){

			double[] val = {conv,noFocus,obstinacy,meanError,accError};
			double[] normVal = new double[val.length];
			double sum = 0;

			for(int i  = 0 ; i < 5 ; i++){
				normVal[i] = normalize(val[i], i);
				sum += normVal[i];
			}
			fitness = sum;
			GACNFTPrinter gaStats = (GACNFTPrinter) gaLauncher.getGao().getGaStats();
			gaStats.addScenario(numInd,scenarioId,val,normVal,fitness);
		}else{
			if(obstinacy == 1)//We are expecting a switch of target
				fitness = 0;
			else
				fitness = MAX_FITNESS;
		}

		return fitness;


	}

}
