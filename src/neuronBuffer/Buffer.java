package neuronBuffer;

import utils.Cloneable;


public class Buffer implements Cloneable{
	
	
	/**Father neuron**/
	protected BufferedNeuronUM neuron;
	/**The charge charge**/
	protected int charge;
	/**1 if the output is high**/
	protected int output;
	/**For identification**/
	protected int id;
	
	
	
	
	public Buffer(int id,BufferedNeuronUM father)
	{
		charge = 0;
		output = 0;
		this.id = id;
		this.neuron = father;
	}
	
	@Override
	public Buffer clone(){
		Buffer clone = null;
		try{
			clone = (Buffer)super.clone();
		}catch (CloneNotSupportedException e) {
			System.err.println("errror clone " + e);
		}
		
		//copy attributes
		clone.charge = this.charge;
		clone.output = this.output;
		clone.id = this.id;
		//share?
		clone.neuron = this.neuron;
	//	System.err.println("cloning " + this.hashCode() + " to " + ((Object)clone.hashCode()) + " neuron was : "+clone.neuron.hashCode());
		
		return clone;
		
		
	}
	
	
	
	
	

	/**
	 * Compute the next step output
	 * @original connection : tab of input connections
	 */
	public void compute(double proba,int source,int... connection)
	{
			int sum = sum(connection) + source;
			int oldCharge  = getOldCharge();
			if(oldCharge == 0 && sum == 0 )
			{
				setOutput(0);
				setBuffer(0);
			}
			else
			{
				if(testProba(proba))
					setOutput(1);
				else
					setOutput(0);

				if(sum > 1)
				{
					setBuffer(oldCharge + sum - 1);
				}
				else
				{
					if(sum == 0)
					{
						setBuffer(oldCharge - 1);
					}

				}
			}
			//System.out.println("i:"+i + " charge:"+charge);
			//System.out.println("out = " + output);
		
	}
	
	/**
	 * Return the charge of the previous state
	 * @return
	 */
	protected int getOldCharge() {
	//	System.err.println("@ : " + ((Object)hashCode()));
		return ((BufferedNeuronUM) neuron.getUnit().getUnitModel()).getBuffer(id).charge;
	}

	protected boolean testProba(double proba) {
		return Math.random() <= proba ;
	}
	
	
	
	protected void setOutput(int val)
	{
			output = val;
	}




	public String printBuff()
	{
		return ""+charge;
	}
	
	
	public void setBuffer(int buffer) {
		this.charge = buffer;
	}

	
	/**
	 * Return the sum of high connections
	 * @original connection
	 * @return
	 */
	private int sum(int[] connection) {
		int ret = 0;
		for(int b : connection )
		{
			//System.out.print(l[i] );
			ret += b;
		}
		//System.out.println("ret : " + ret);
		return ret;
	}


	public int getCharge() {
		return charge;
	}


	public int getOutput() {
		return output;
	}





	public void reset() {
//		if(charge > 0)
//			System.out.println("Buffer charge : " + this.charge);
		charge = 0;
		output = 0;
		
	}
	
	public String toString()
	{
		return "{"+charge+","+output+"}";
//		return ""+output;
	}

	public void setNeuron(BufferedNeuronUM bufferedNeuronUM) {
		this.neuron = bufferedNeuronUM;
		
	}

}
