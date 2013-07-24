package hardSimulator;

import java.util.Arrays;

import maps.Parameter;
import maps.Var;
import unitModel.UnitModel;
import coordinates.NullCoordinateException;
import coordinates.Space;

public class TransmitterHUM extends UnitModel {

	//parameters
	protected static final int PRECISION = 0;

	//in
	protected int[] input;
	protected int proba;

	//out
	protected int spike;

	//var
	protected Var buffer;


	public TransmitterHUM(Parameter dt, Space space, Parameter... parameters) {
		super(dt,space,parameters);
		buffer = new Var(0);
		
	}
	
	public TransmitterHUM clone(){
		TransmitterHUM clone = (TransmitterHUM) super.clone();
		clone.buffer = this.buffer.clone();
		return clone;
	}
	
	public TransmitterHUM clone2(){
		TransmitterHUM clone = (TransmitterHUM) super.clone2();
		clone.buffer = this.buffer;
		return clone;
	}


	@Override
	public double compute() throws NullCoordinateException {
		int nbActiveInput = nbActiveInput(input);
//		System.out.println("transmitter : nbActiv: " + nbActiveInput + " input : " + Arrays.toString(input));

		buffer.addThis(nbActiveInput);

		if(buffer.get() != 0d){
			if(proba == 1){//we transmit the spike if proba ==1
				spike = 1;
			}else{
				spike = 0;
			}
			buffer.set(buffer.get()-1);
		}else{
			spike= 0;
		}


		return spike;
	}


	public static int nbActiveInput(int[] input2) {
		int sum = 0;
		for(int elem : input2){
			sum += elem;
		}
		return sum;
	}


	public void setInput(int[] input) {
		this.input = input;

	}
	
	


	public int getSpike() {
		return spike;
	}

	public void setProba(int proba) {
		this.proba = proba;
	}


	

}
