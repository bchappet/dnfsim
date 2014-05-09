package main.java.model;

import java.util.Arrays;
import java.util.List;

import main.java.console.CNFTCommandLine;
import main.java.console.CommandLineFormatException;
import main.java.coordinates.NullCoordinateException;
import main.java.coordinates.Space;
import main.java.maps.AbstractMap;
import main.java.maps.Map;
import main.java.maps.NeighborhoodMap;
import main.java.maps.NullBufferedNeuronUM;
import main.java.maps.Parameter;
import main.java.maps.UnitLeaf;
import main.java.maps.Var;
import main.java.neigborhood.Neighborhood;
import main.java.neigborhood.OneToOneNeighborhood;
import main.java.neigborhood.V4Neighborhood2D;
import main.java.neuronBuffer.BufferedNeuronUM;
import main.java.routing.Assymetric2DHardRouting;
import main.java.unitModel.PotentialNeuronUM;


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
		addParameters(command.get(CNFTCommandLine.BUFF_WIDTH));
		super.initializeParameters();
	}

	
	
	@Override
	protected void initLateralWeights() throws NullCoordinateException, CommandLineFormatException 
	{
		cnft = (AbstractMap) getLateralWeights(
				CNFT,command.get(CNFTCommandLine.DT),command.get(CNFTCommandLine.HARD_DT),space2d,
				pn,hpA,hppa,pn,hpB,hppb,focus,new Var("focusThreshold",0),command.get(CNFTCommandLine.BUFF_WIDTH));
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
		return "Buffered RSDNF main.java.model. Much closer to hardware inpementation : the spikes are transmitted" +
				"from neurons to neurons and stored in buffers to ensure one transmission per step." +
				"The transmission updating time hard_dt is critical" ;
	}







}
