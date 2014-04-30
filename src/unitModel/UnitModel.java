package unitModel;

import java.util.List;

import maps.Parameter;
import maps.Unit;
import maps.UnitMap;
import coordinates.NullCoordinateException;

import utils.Cloneable;

/**
 * The instances of this class stores the states of the smallest unit of the model with the activity (Var).
 * The extending class must implements the compute() (double) method which gives the
 * next state of the unit.
 * 
 * As a {@link ParameterUser}, a UnitModel has acces to 
 * <li> a list of parameters : params (List<Parameter>)</li>
 * <li> the time of simulation (seconds) time (Var)</li>
 * <li> the computation frequency (seconds) dt (Var)</li>
 * <li> the refSpace of computation : refSpace (Space)</li> 
 * 
 * In addition, the {@link UnitModel} should knows its coordinates (continuous refSpace) if it is necessary.
 * 
 * With the defaults attributes a {@link UnitModel} only compute its activity using the computation implementation.
 * 
 * There is a possibility to construct {@link UnitModel} as a composition of several {@link UnitModel} stocked
 * in the  subUnits (List<UnitModel>) list.
 * 
 * <p>Basically there are two mode of computation (they are handled by the {@link UnitMap} and the {@link Unit} :
 * No memory mode : the map "artificially" set the coordinates and compute the result of compute() method which is directly returned :
 * nothing is stored here, the activity attribute is useless.</p>
 * <p>Memory mode : there is a {@link UnitModel} for each discrete positions available in the refSpace, each {@link UnitModel} knows its 
 * corresponding continuous coordinates, and each {@link UnitModel} stores its state in the activity attributes</p>
 * 
 * 
 * 
 * 
 * @author bchappet
 *
 */
public abstract class UnitModel<T>  implements Cloneable  {



	/**Activity of the unit**/
	private T activity;


	public UnitModel(T initActivity) {
		this.activity = initActivity;
	}

	/**
	 * the activity is cloned 
	 * the parameter list is shared
	 */
	@Override
	public UnitModel<T> clone()
	{
		UnitModel<T> o = null;
		try {
			o = (UnitModel<T>) super.clone();
			if(this.activity instanceof Cloneable){
				o.activity = (T) ((Cloneable)this.activity).clone();
			}else{
				//o.activity = (T) this.activity.getClass().newInstance();
			}
		} catch (CloneNotSupportedException e){
			//Clone is supported
			e.printStackTrace();
		} 
		return o;

	}


	
	/**
	 * Update the activity using compute method
	 * @return
	 * @throws NullCoordinateException
	 */
	public T computeActivity(List<Parameter<T>> params){
		T ret = compute(params);
		this.set(ret);
		return ret;
	}


	/**
	 * Return the activity resulting of a computation with the differents parameters
	 * @param params parameter for the computation
	 * @return
	 * @throws NullCoordinateException 
	 */
	protected abstract  T compute(List<Parameter<T>> params);

	/**
	 * Get current activity
	 * @return
	 */
	public T get() {
		return activity;
	}

	/**
	 * Change the activity reference by the provided one
	 * WARNING, no clone inside
	 * @param act
	 * @throws NullCoordinateException 
	 */
	public void set(T act)
	{
		this.activity = act;
	}

	


















}
