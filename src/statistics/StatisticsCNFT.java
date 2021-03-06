package statistics;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import maps.AbstractMap;
import maps.Parameter;
import maps.Var;
import model.Model;
import plot.Trace;
import plot.WTrace;
import coordinates.NullCoordinateException;
import coordinates.Space;


/**
 * 
 * @author bchappet
 *
 */
public class StatisticsCNFT extends Statistics{

	/** Name of statistic fields**/
	public static final String ACT_SUM = "ActivitySum";
	public static final String CENTER_X = "CenterX";
	public static final String CENTER_Y = "CenterY";
	public static final String CLOSEST_TRACK = "ClosestTrack";
	public static final String TRACK_Y = "TrackY";
	public static final String TRACK_X = "TrackX";
	public static final String ERROR_DIST = "ErrorDist";
	

	public static final String WIDTH = "Bubble Width";
	public static final String HEIGHT = "Bubble Height";
	public static final String MAX_WEIGHT = "Weight max";
	public static final String CONVERGENCE = "Convergence";
	public static final String ACC_ERROR = "Acceptable Error";
	public static final String FOCUS = "Focus";
	public static final String TRUE_ERROR ="True Error";
	public static final String MAX ="Max";
	public static final String TEST_CONV = "TestConv";
	public static final String LYAPUNOV = "Lyapunov";
	public static final String MSE_SOM = "MeanSquareError SOM";




	public StatisticsCNFT(String name, Var dt, Space space,StatMap... trajectoryUnitMap) {
		super(name,dt,space,trajectoryUnitMap);
	}


}
