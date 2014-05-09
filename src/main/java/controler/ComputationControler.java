package main.java.controler;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import main.java.maps.Map;
import main.java.maps.Parameter;
import main.java.view.ComputationView;

/**
 * The {@link ComputationControler} will control the computation of main.java.model and will warn the main.java.view for changes
 * respecting the MVC design
 * @author benoit
 *
 */
public class ComputationControler {
	
	private final transient Logger LOGGER = Logger.getLogger(ComputationControler.class.getName());

	
	/**
	 * Scale limit for dt (used in big decimal)
	 */
	public static final int SCALE_LIMIT = 10;
	public static final int ROUDING_MODE = BigDecimal.ROUND_HALF_EVEN;
	
	/**Current time of the simulation**/
	private BigDecimal time;
	
	/**OrderedMap for computation**/
	private List<ComputableControler> orderedMap;
	

	public ComputationControler(ParameterControlerTree tree) {
		this.time = new BigDecimal("0");
		this.orderedMap = new ArrayList<ComputableControler>();
		
		initOrderedMap( tree.getRoot());
		LOGGER.info("this.orderedMap initiated.");
		LOGGER.info("this.orderedMap="+this.orderedMap);
	}
	
	
	/**
	 * Fill the ordered map with the computated map in the good order
	 * @param tree //TODO register on tree change
	 */
	private void initOrderedMap(ParameterControler root) {
		for(int i = 0 ;i < root.getChildCount() ; i++){
			ParameterControler pc =  root.getChild(i);
			if(pc instanceof ComputableControler){
				initOrderedMap( pc);
				this.orderedMap.add( (ComputableControler) pc);
			}
		}
	}



	/**
	 * Compute the main.java.model main.java.maps until time == timeLimit
	 * @param timeLimit
	 */
	public void compute(BigDecimal timeLimit){
		LOGGER.info("Computation until: " + timeLimit );
		while(time.compareTo(timeLimit) < 0){
			//This is the time for this simulation
			time = getSmallestNextTime();
			LOGGER.fine("time = " + time);
			for(ComputableControler m : orderedMap){
				LOGGER.fine("compute : " + m);
				m.compute(this.time);
			}
			
		}
	}

	/**
	 * Return the smallest time for next comp 
	 * @param doubleValue
	 * @param orderedMap2
	 * @return
	 */
	private BigDecimal getSmallestNextTime(){
		BigDecimal smallestNextTime = new BigDecimal(Double.MAX_VALUE);
		for(ComputableControler m : this.orderedMap){
			BigDecimal nextTime = m.getNextTime();
			if(nextTime.compareTo(smallestNextTime) < 0){
				smallestNextTime = nextTime;
			}
			
		}
		return smallestNextTime;
	}

}
