package hardSimulator;

import maps.Parameter;
import maps.Var;
import unitModel.UnitModel;
import coordinates.NullCoordinateException;
import coordinates.Space;

public class SourceHUM extends UnitModel {

	protected static final int NB_SPIKE = 0;
	protected static final int PRECISION = 1;


	//input
	protected int activate;

	//output
	protected int spike;

	//var
	protected Var buffer; 

	public SourceHUM(Parameter dt,Space space,Parameter... params){
		super(dt,space,params);
		
		buffer = new Var(0);
	}
	
	public SourceHUM clone(){
		SourceHUM clone = (SourceHUM) super.clone();
		clone.buffer = this.buffer.clone();
		return clone;
	}
	
	public SourceHUM clone2(){
		SourceHUM clone = (SourceHUM) super.clone2();
		clone.buffer = this.buffer;
		return clone;
	}




	@Override
	public double compute() throws NullCoordinateException {
		if(activate == 1){
			buffer.set(params.get(NB_SPIKE).get());
			//System.out.println("Activate source : " + params.get(NB_SPIKE).get());
		}
		//System.out.println("Source buffer : " + buffer + " @" + buffer.hashCode());
		if(buffer.get() != 0d){
			
			buffer.set(buffer.get()-1);
			spike = 1;
		}else{
			spike = 0;
		}

		return spike;

	}

	public void setActivate(int activate2) {
		this.activate = activate2;

	}

	public int getSpike() {
		return spike;
	}

}
