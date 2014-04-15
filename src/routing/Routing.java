package routing;

/**
 * A {@link Routing} class specify a routing politic for spike transmission
 * within a map.
 * The implementation is usualy specific of a type of space.
 * 
 * only one method must be overriden : getTargets
 * 
 * @author bchappet
 *
 */
public abstract class Routing {
	
	/**Index of the couple of result in the resulting array**/
	/**Neighbor targeted*/
	public static final int TARGET = 0; //Neighboor targeted
	/**Direction of outcoming spike**/
	public static final int DIRECTION = 1; //Direction of outcoming spike

	/**
	 * Knowing the direction from which we have receive, we return
	 * a list of neighboor index to send to and the associated direction of the
	 * new transmission. 
	 * The resulting array contains a couple neighboor,direction for every target 
	 * 
	 * @param from : direction of incoming signal
	 * @return
	 */
	public abstract int[][] getTargets (int from);
	
	/**
	 * return an array with the targets (and associated direction)
	 * for the first emmission.
	 * @return
	 */
	public abstract int[][] getFirstTargets();

}
