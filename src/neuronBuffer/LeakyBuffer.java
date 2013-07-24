package neuronBuffer;

import maps.Parameter;

/**
 * The charge cannot handle more than its capacity
 * @author bchappet
 *
 */
public class LeakyBuffer extends Buffer {
	
	/**The maximal capacity of a charge**/
	protected Parameter capacity;
	
	
	public LeakyBuffer(int id,BufferedNeuronUM father,Parameter capacity)
	{
		super(id,father);
		this.capacity = capacity;
		
	}
	
	@Override
	public void setBuffer(int buffer) {
		this.charge = buffer;
		int cap = (int) capacity.get(neuron.getCoord());
		if(charge > cap)
			charge = cap;
	}
	
	@Override
	public LeakyBuffer clone(){
		LeakyBuffer clone = (LeakyBuffer) super.clone();
		clone.capacity = this.capacity;//shared
		
		return clone;
		
	}
	
	
	
	

}
