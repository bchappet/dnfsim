package main.java.unitModel;


import main.java.cellularAutomata.PRNGWrapperUM;
import main.java.console.CNFTCommandLine;
import main.java.coordinates.Space;
import main.java.maps.Parameter;
import main.java.maps.UnitMap;
import main.java.maps.Var;
import main.java.neuronBuffer.BufferedNeuronUM;
import main.java.neuronBuffer.LeakyBuffer;
import main.java.routing.Routing;

public class BufferedNeuronUMCAPRNG extends BufferedNeuronUM {
	
	public static final int PRNG_MAP = 2;//Map with the caprng

	public BufferedNeuronUMCAPRNG(Routing routing,Var dt, Space space, Parameter... parameters){
		super(routing,dt,space,parameters);
		
	}
	
	public BufferedNeuronUMCAPRNG(Routing routing)
	{
		super(routing);
	}
	
	public BufferedNeuronUMCAPRNG clone(){
		return (BufferedNeuronUMCAPRNG) super.clone();
	}
	public BufferedNeuronUMCAPRNG clone2(){
		return (BufferedNeuronUMCAPRNG) super.clone2();
	}
	
	protected void initBuffer()
	{
		Parameter capacity;
		try{
			capacity =  params.getIndex(CAPACITY);
		}catch (IndexOutOfBoundsException e) {
			capacity = new Var(CNFTCommandLine.BUFF_WIDTH,Integer.MAX_VALUE);
		}
		
		bufs = new LeakyBuffer[NB_BUFF];
		bufs[SP] = new LeakyBuffer(SP,this,capacity){
			@Override
			protected boolean testProba(double proba) {
				return true;
			}
		};
		
		/**
		 * TODO Validate order of probaility number extraction:
		 * 
		 */
		final UnitMap map = (UnitMap) params.getIndex(PRNG_MAP);
		for(int i = 1 ; i < bufs.length ; i ++)
		{
			bufs[i] = new LeakyBuffer(i,this,capacity){
				@Override
				protected boolean testProba(double proba) {
					
					 PRNGWrapperUM um = (PRNGWrapperUM) map.getUnit(space.coordIntToIndex(neuron.coord)).getUnitModel();
					 double rand = um.getRandomNumber();
					 return rand <= proba;
				}
			};
		}
		
		
		
		
	}
	
	
	


}
