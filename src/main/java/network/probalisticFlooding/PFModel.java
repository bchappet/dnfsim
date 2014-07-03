package main.java.network.probalisticFlooding;

import java.io.File;

import main.java.console.CommandLine;
import main.java.console.CommandLineFormatException;
import main.java.network.generic.DirectedEdge;
import main.java.network.generic.NetworkModel;
import main.java.network.generic.TypeGraph;
import main.java.network.generic.packet.Packet;

public class PFModel extends NetworkModel<PFNode,Packet,DirectedEdge<Packet,PFNode>>{

	public PFModel(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public CommandLine constructCommandLine() {
		return new PFCommandLine();
	}
	
	@Override
	protected void constructGraph(File matrixTransitionFile) {
		try {
			setSpreadingGraph(getSpreadingGraphFactory().constructGraph(matrixTransitionFile, TypeGraph.PROBABILISTIC_FLOODING, command));
		} catch (CommandLineFormatException e) {
			e.printStackTrace();
		}
	}

}
