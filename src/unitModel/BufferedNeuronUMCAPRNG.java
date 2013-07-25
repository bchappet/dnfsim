package unitModel;


import java.util.Arrays;

import console.CNFTCommandLine;
import maps.AbstractUnitMap;
import maps.Parameter;
import maps.Var;
import neuronBuffer.BufferedNeuronUM;
import neuronBuffer.LeakyBuffer;
import routing.Routing;
import cellularAutomata.PRNGWrapperUM;
import coordinates.Space;

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
			capacity =  params.get(CAPACITY);
		}catch (IndexOutOfBoundsException e) {
			capacity = new Var(CNFTCommandLine.BUFF_CAP,Integer.MAX_VALUE);
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
		final AbstractUnitMap map = (AbstractUnitMap) params.get(PRNG_MAP);
		for(int i = 1 ; i < bufs.length ; i ++)
		{
			bufs[i] = new LeakyBuffer(i,this,capacity){
				@Override
				protected boolean testProba(double proba) {
					
					 PRNGWrapperUM um = (PRNGWrapperUM) map.getUnit(space.coordToIndex(neuron.coord)).getUnitModel();
					 double rand = um.getRandomNumber();
					 return rand <= proba;
				}
			};
		}
		
		
		
		
	}
	
	
	


}
