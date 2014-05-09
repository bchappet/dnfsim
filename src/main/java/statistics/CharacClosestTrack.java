package main.java.statistics;

import java.util.HashMap;
import java.util.Map;

import main.java.coordinates.Space;
import main.java.maps.Parameter;
import main.java.model.Model;
import main.java.plot.Trace;

/**
 * From convergence,
 * return the hash code of the track who was the closest during the majority of iteration
 * If the number of iteration is equal for two track, we take the first
 * @author bchappet
 *
 */
public class CharacClosestTrack extends Charac {

	public CharacClosestTrack(String name, Statistics stats, Space space,
			Model model, Parameter... params) {
		super(name, stats, space, model, params);
	}

	@Override
	public double computeTrajectory(double... param) {
		double ret = Statistics.ERROR;
		double convtime = get(CharacteristicsCNFT.CONVERGENCE);
		int convIt = (int) Math.round( (convtime/stats.dt.get()));
		if(convtime != StatisticsCNFT.ERROR)
		{
			Trace closestTrack = stats.getTrace(StatisticsCNFT.CLOSEST_TRACK);
			Map<Integer,Integer> map = new HashMap<Integer, Integer>();
			for(int i = convIt ; i <  closestTrack.size() ; i++){
				int hash = (int) closestTrack.get(i);
				
				if(hash != Statistics.ERROR){
					//System.out.println(i + " hash " + hash);
					if(map.containsKey(hash)){
						map.put(hash,map.get(hash)+1);
					}else{
						map.put(hash,1);
					}
				}
			}

			//find the max value
			Integer max = Integer.MIN_VALUE;
			Integer maxKey = Statistics.ERROR;
			for(Integer key : map.keySet()){
				Integer val = map.get(key);
				if(val.compareTo(max) > 0){
					max = val;
					maxKey = key;
				}
			}
			ret = maxKey.doubleValue();
		}
		return ret;
	}

}
