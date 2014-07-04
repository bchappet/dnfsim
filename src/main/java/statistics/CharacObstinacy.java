package main.java.statistics;

import main.java.coordinates.Space;
import main.java.model.Model;
import main.java.plot.Trace;

/**
 * Evaluate the obstinacy of focus : 
 * Number of time that focus is changing of target
 * good obstinacy == concentrate on one main.java.input during simulation
 * @author bchappet
 *
 */
public class CharacObstinacy extends Charac {

	public CharacObstinacy(String name, Statistics stats, Space space,
			Model model, Charac conv) {
		super(name, stats, space, model, conv);
	}

	@Override
	public double computeTrajectory(double... param) {

		double ret = Statistics.ERROR;
		double convtime =  get(CharacteristicsCNFT.CONVERGENCE);
		int convIt = (int) Math.round( (convtime/stats.dt.get()));
		
		
		if(convtime != Statistics.ERROR){
			Trace closestTrack = stats.getTrace(StatisticsCNFT.CLOSEST_TRACK);
		
			//the tracked stimulus is the tracked one at conv time
			int previousStimulusHash;
		
			//Since it can be Error, we have to find the next valid one
			do{
				previousStimulusHash = (int) closestTrack.get( convIt);
				convIt++;
			}while(previousStimulusHash == Statistics.ERROR);
			//System.out.println("init hash : " + previousStimulusHash);
			
			
			
			ret = 0;
			for(int i = convIt; i < closestTrack.size() ; i++)
			{
				int  currentStimulusHash = (int) closestTrack.get(i);
				if(currentStimulusHash != Statistics.ERROR )
				{
					//System.out.println("current hash : " + currentStimulusHash);
					if(currentStimulusHash != previousStimulusHash){
						//System.out.println(currentStimulusHash+ "!="+ previousStimulusHash);
						ret ++; //the closest stimulus is different from the previous one
						
						//update previous stimulus hash
						previousStimulusHash = currentStimulusHash;
					}
					
				}
				
			}
		}
		//System.out.println("Obstinacy : " + ret);
		return ret;
	}


	/*	@Override
	public double eval(int startingIteration) {
		Trace obs = statsControler.getCoord(StatisticsImproved.CLOSEST_INPUT);

		int nbChanging = 0; //the focus is change its target
		double prev = 0; //previous value

		for(int i = startingIteration; i < obs.size() ; i++)
		{
			double val = obs.get(i);
			if(val != StatisticsImproved.NOVALUE )
			{
				if(val != prev)
					nbChanging ++;

				prev = val;
			}
		}
		double ret = nbChanging;
		if(ret != 0)
		{
			ret = ret - 1;
		}
		return ret;
	}*/

}
