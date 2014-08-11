package main.java.network.probalisticFlooding;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;

import main.java.console.CommandLine;
import main.java.console.CommandLineFormatException;
import main.java.coordinates.NullCoordinateException;
import main.java.maps.Var;
import main.java.network.generic.AccumulationUnitMap;
import main.java.network.generic.DirectedEdge;
import main.java.network.generic.NetworkCommandLine;
import main.java.network.generic.NetworkException;
import main.java.network.generic.NetworkModel;
import main.java.network.generic.TypeGraph;
import main.java.network.generic.packet.Packet;
import main.java.pfspike.PFSCommandLine;

public class PFModel extends NetworkModel<PFNode<Packet>,Packet,DirectedEdge<Packet,PFNode<Packet>>>{

	public PFModel(String name) throws IOException, CommandLineFormatException {
		super(name);

	}

	@Override
	protected void initializeParameters() throws CommandLineFormatException, NullCoordinateException{
		Var<String> write = (Var<String>)((PFCommandLine)command).get(PFSCommandLine.WRITE_TRANSITION_MATRIX_FILE);
		if("True".equals(write.get())){
			System.out.println("ecriture du fichier ..."); // todo debug apparait deux fois
			try {
				int  size = (int) command.get(PFCommandLine.SIZE).get();
				String path = (String) command.get(PFCommandLine.TRANSITION_MATRIX_FILE).get();
				PFUtils.writePFAdjacentMatrix(path,size);
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(-1);
			}
		}
		super.initializeParameters();

		Var<BigDecimal> dt = (Var<BigDecimal>)((NetworkCommandLine)command).get(PFSCommandLine.MAIN_DT);
		Var<Integer> size = (Var<Integer>)((NetworkCommandLine)command).get(PFSCommandLine.SIZE);


		AccumulationUnitMap receivePacketUnitMap = new AccumulationUnitMap("ReceiveMap",getSpreadingGraph(), dt, size);


		//		UnitMap<Integer,Integer> concentrationMap = new UnitMap<>("concentrationMap", dt, new Space2D(size,size), null, spreadingGraph);
		addParameters(receivePacketUnitMap/*,concentrationMap*/);
	}

	

	@Override
	public CommandLine constructCommandLine() {
		return new PFCommandLine();
	}


	@Override
	protected void constructGraph(File matrixTransitionFile) throws NetworkException {
		try {
			setSpreadingGraph(getSpreadingGraphFactory().constructGraph(matrixTransitionFile, TypeGraph.PROBABILISTIC_FLOODING, command));
		} catch (CommandLineFormatException e) {
			e.printStackTrace();
		}
	}

}
