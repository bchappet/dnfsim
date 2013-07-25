package model;

import java.util.Arrays;
import java.util.List;

import maps.AbstractMap;
import maps.Map;
import maps.NeighborhoodMap;
import maps.NullBufferedNeuronUM;
import maps.Parameter;
import maps.UnitLeaf;
import maps.Var;
import neigborhood.Neighborhood;
import neigborhood.OneToOneNeighborhood;
import neigborhood.V4Neighborhood2D;
import neuronBuffer.BufferedNeuronUM;
import routing.Assymetric2DHardRouting;
import unitModel.PotentialNeuronUM;
import console.CNFTCommandLine;
import console.CommandLineFormatException;
import coordinates.NullCoordinateException;
import coordinates.Space;


/**
 * Hardware simulation of rsdnf
 * @author bchappet
 *
 */
public class ModelRSDNF extends ModelNSpike {

	protected NeighborhoodMap exc;
	protected NeighborhoodMap inh;
	
	

	public ModelRSDNF(String name) {
		super(name);
		
	}
	
	protected void initializeParameters() throws CommandLineFormatException, NullCoordinateException {
		addParameters(command.get(CNFTCommandLine.HARD_DT));
		addParameters(command.get(CNFTCommandLine.BUFF_CAP));
		super.initializeParameters();
	}

	
	
	@Override
	protected void initLateralWeights() throws NullCoordinateException, CommandLineFormatException 
	{
		cnft = (AbstractMap) getLateralWeights(
				CNFT,command.get(CNFTCommandLine.DT),command.get(CNFTCommandLine.HARD_DT),space2d,
				pn,hpA,hppa,pn,hpB,hppb,focus,new Var("focusThreshold",0),command.get(CNFTCommandLine.BUFF_CAP));
	}
	
	protected  Parameter getLateralWeights(String name,Var dt,Var dtHard,Space space2D,
			Parameter na,Parameter ia,Parameter pa,Parameter nb,Parameter ib,Parameter pb,
			Parameter focus,Parameter threshold,Parameter capacity) throws CommandLineFormatException
	{
		exc = new NeighborhoodMap(CNFTW+"_+",
				new BufferedNeuronUM(new Assymetric2DHardRouting(),dtHard ,space2d,hppa,capacity)){
//			@Override
//			public void compute(){
//				super.compute();
//				System.out.println(this.display2D());
//			}
			
		};
		Neighborhood neigh = new V4Neighborhood2D(space2d, new UnitLeaf(exc));
		exc.addNeighboors(neigh);
		neigh.setNullUnit(new NullBufferedNeuronUM());
		exc.constructMemory();
		exc.toParallel();

		inh = new NeighborhoodMap(CNFTW+"_-",
				new BufferedNeuronUM(new Assymetric2DHardRouting(),dtHard ,space2d,hppb,capacity));
		Neighborhood neigh2 = new V4Neighborhood2D(space2d, new UnitLeaf(inh));
		inh.addNeighboors(neigh2);
		neigh2.setNullUnit(new NullBufferedNeuronUM());
		inh.constructMemory();
		inh.toParallel();
		
		NeighborhoodMap sum = new NeighborhoodMap(CNFT,new PotentialNeuronUM(),
				dt,space2d,threshold,pn,focus,hpA,hpB);
		
		sum.addNeighboors(new OneToOneNeighborhood(space2d, exc),
				new OneToOneNeighborhood(space2d, inh));
		sum.constructMemory();
		
		return sum;
		
	}

	@Override
	public List<Parameter> getDefaultDisplayedParameter() {
		Parameter[] ret ={input,exc,inh,cnft,potential,focus};
		return Arrays.asList(ret);
	}
	

	
	private void displayBuffers(Map map)
	{
		double res = map.getSpace().getResolution();
		int k = 0;
		for(int i = 0 ; i < (int) res ; i++){
			for(int j = 0 ; j < (int) res ; j++){
				System.out.print(((BufferedNeuronUM)map.getUnit(k).getUnitModel(0))+"::"
			+((BufferedNeuronUM)map.getUnit(k).getUnitModel(1) + "|---|"));
//				System.out.print(((BufferedNeuronUM)neuron.getUnit(k).getUnitModel())+";");
				k ++;
			}
			System.out.println();
			
		}
	}
	
	@Override
	public String getText() {
		return "Buffered RSDNF model. Much closer to hardware inpementation : the spikes are transmitted" +
				"from neurons to neurons and stored in buffers to ensure one transmission per step." +
				"The transmission updating time hard_dt is critical" ;
	}







}
