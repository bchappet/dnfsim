package main.java.network.probalisticFlooding;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;

import main.java.console.CommandLine;
import main.java.console.CommandLineFormatException;
import main.java.coordinates.NullCoordinateException;
import main.java.maps.InfiniteDt;
import main.java.maps.Map;
import main.java.maps.MatrixDouble2D;
import main.java.maps.Var;
import main.java.network.generic.AccumulationUnitMap;
import main.java.network.generic.DirectedEdge;
import main.java.network.generic.NetworkCommandLine;
import main.java.network.generic.NetworkException;
import main.java.network.generic.NetworkModel;
import main.java.network.generic.SpreadingGraphFactory;
import main.java.network.generic.TypeGraph;
import main.java.network.generic.packet.Packet;
import main.java.pfspike.PFSCommandLine;
import main.java.pfspike.PFSSpreadingGraph;
import main.java.space.Space2D;

public class PFSModelTest1 extends NetworkModel<PFNode<Packet>,Packet,DirectedEdge<Packet,PFNode<Packet>>>{

	
	public PFSModelTest1(String name) throws IOException, CommandLineFormatException {
		super(name);

	}

	@Override
	protected void initializeParameters() throws CommandLineFormatException, NullCoordinateException{
		Var<String> write = (Var<String>)((PFSCommandLine)command).get(PFCommandLine.WRITE_TRANSITION_MATRIX_FILE);
		if("true".equals(write.get())){
//			System.out.println("ecriture du fichier ..." +  command.get(PFCommandLine.TRANSITION_MATRIX_FILE).get()); // todo debug apparait deux fois
			try {
				int  size = (int) command.get(PFSCommandLine.SIZE).get();
				String path = (String) command.get(PFSCommandLine.TRANSITION_MATRIX_FILE).get();
				boolean isToric= (boolean) command.get(PFSCommandLine.TORIC).get();
				PFUtils.writePFAdjacentMatrix(path,size,isToric);
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(-1);
			}
		}
		super.initializeParameters();
		//System.out.println(command.getScript());
		Var<BigDecimal> dt = (Var<BigDecimal>)(command).get(PFSCommandLine.DT);
		Var<Integer> size = (Var<Integer>)(command).get(PFSCommandLine.SIZE);


		AccumulationUnitMap receivePacketUnitMap = new AccumulationUnitMap("ReceiveMap",getSpreadingGraph(), dt, size);


		//		UnitMap<Integer,Integer> concentrationMap = new UnitMap<>("concentrationMap", dt, new Space2D(size,size), null, spreadingGraph);
		addParameters(receivePacketUnitMap/*,concentrationMap*/);
		addParameters(command.get(PFSCommandLine.E_WEIGTH));
		addParameters(command.get(PFSCommandLine.NB_SPIKE));
		addParameters(command.get(PFSCommandLine.MODE));
		addParameters(command.get(PFSCommandLine.TORIC));
		
	}

	

	@Override
	public CommandLine constructCommandLine() {
		return new PFSCommandLine();
	}


	@Override
	protected void constructGraph(File matrixTransitionFile) throws NetworkException {
		try {
			
			Var<Integer> size = (Var<Integer>)(command).get(PFSCommandLine.SIZE);
			
			MatrixDouble2D focus = new MatrixDouble2D("focus", new InfiniteDt(),new Space2D(size, size)){
				@Override
				public void compute(){
					setIndex(0, 1.);
				}
			};
			
			
			PFSSpreadingGraph pfssge = (PFSSpreadingGraph) SpreadingGraphFactory.getInstance().constructGraph(
					matrixTransitionFile, 
					TypeGraph.EXCITATORY_GRAPH_PFSPIKE, 
					getCommandLine(),
					focus);
			
			setSpreadingGraph(pfssge);
		} catch (CommandLineFormatException e) {
			e.printStackTrace();
		}
	}

}
