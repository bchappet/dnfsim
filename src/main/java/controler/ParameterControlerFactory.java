package main.java.controler;

import main.java.maps.Map;
import main.java.maps.Parameter;
import main.java.maps.Trajectory;
import main.java.maps.Var;
import main.java.network.generic.SpreadingGraph;
import main.java.network.view.GraphControler;
import main.java.space.Space;
import main.java.statistics.Characteristics;
import main.java.statistics.Statistics;
/**
 * Affect a ParameterControler to each parameter
 * @author benoit
 *
 */
public class ParameterControlerFactory {
	
	public static ParameterControler getControler(Parameter param){
		if(param instanceof Var){
			return new VarControler((Var) param);
		}else if(param instanceof Trajectory){
			return new TrajectoryControler((Trajectory)param);
		}else if(param instanceof Statistics) {
			return new StatisticsControler((Statistics)param);
		}else if(param instanceof Characteristics){
			return new CharacteristicsControler((Characteristics)param);
		}else if(param instanceof Space){
			return new SpaceControler((Space)param);
		}else if(param instanceof SpreadingGraph){
			return new GraphControler(param);
		}else{
			return new MapControler((Map) param);
		}
	}

}
