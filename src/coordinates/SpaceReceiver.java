package coordinates;


/**
 * Every class who want to react at a refSpace changing should implement this class
 * @author bchappet
 *
 */
public interface SpaceReceiver {
	
	
	
	/**
	 * Method to react at a resolution changing
	 * @original oldSpace
	 */
	public void onResolutionChanged(double oldRes,double newRes);
	
	/**
	 * Method to react at a wrap changing
	 * @original oldSpace
	 */
	public void onWrapChanged(boolean newWrap);

	
	

}
