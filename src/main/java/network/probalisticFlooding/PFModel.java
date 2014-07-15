package main.java.network.probalisticFlooding;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;

import main.java.console.CommandLine;
import main.java.console.CommandLineFormatException;
import main.java.coordinates.NullCoordinateException;
import main.java.maps.Var;
import main.java.network.generic.DirectedEdge;
import main.java.network.generic.NetworkCommandLine;
import main.java.network.generic.NetworkModel;
import main.java.network.generic.TotalPacketReceiveUnitMap;
import main.java.network.generic.TypeGraph;
import main.java.network.generic.packet.IPv4Datagramme;
import main.java.network.generic.packet.Packet;
import main.resources.utils.ArrayUtils;

public class PFModel extends NetworkModel<PFNode,Packet,DirectedEdge<Packet,PFNode>>{

	public PFModel(String name) throws IOException, CommandLineFormatException {
		super(name);

	}

	@Override
	protected void initializeParameters() throws CommandLineFormatException, NullCoordinateException{
		Var<String> write = (Var<String>)((PFCommandLine)command).get(PFCommandLine.WRITE_TRANSITION_MATRIX_FILE);
		if("True".equals(write.get())){
			System.out.println("ecriture du fichier ...");
			try {
				this.writePFAdjacentMatrix();
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(-1);
			}
		}
		super.initializeParameters();

		Var<BigDecimal> dt = (Var<BigDecimal>)((NetworkCommandLine)command).get(NetworkCommandLine.NETWORK_DT);
		Var<Integer> size = (Var<Integer>)((NetworkCommandLine)command).get(NetworkCommandLine.SIZE);


		TotalPacketReceiveUnitMap receivePacketUnitMap = new TotalPacketReceiveUnitMap(getSpreadingGraph(), dt, size);


		//		UnitMap<Integer,Integer> concentrationMap = new UnitMap<>("concentrationMap", dt, new Space2D(size,size), null, spreadingGraph);
		addParameters(receivePacketUnitMap/*,concentrationMap*/);
	}


	//	@Override
	//	protected void intializeGraph(){
	//		super.intializeGraph();
	//		for (int i = 0; i < 10; i++) {
	//			getSpreadingGraph().addToFIFO(0,new IPv4Datagramme("first"));
	//		}
	//	}

	private double[][] generateAdjacentMatrix() throws CommandLineFormatException{
		int  size = (int) command.get(PFCommandLine.SIZE).get();

		double[][] adj = new double[size*size][size*size];


		//create oriented adjacent matrix for a 4 neigh topology
		for (int i = 0; i < size*size; i++) {
			for (int j = 0; j < size*size; j++) {
				if( j == (i + 1) && (i + 1) % size != 0 ){ 
					// index = i + (j * size**2)
					adj[i][j] = 1;
				}
				else if( j == (i + size) ){
					adj[i][j] = 1;
				}
			}
		}

		//	no symetrise it up toward down make the graph unoriented
		for (int i = 0; i < size*size; i++) {
			for (int j = 0; j < i; j++) {
				//for j in range(i):
				adj[i][j] = adj[j][i];
			}
		}
		return adj;
	}

	private void writePFAdjacentMatrix() throws IOException, CommandLineFormatException {
		
		Var<String> path = command.get(PFCommandLine.TRANSITION_MATRIX_FILE);
		//		System.out.println(path.get());
		//		System.out.println("path pfmodel : " + path.get());
		//System.out.println("salut salut :"+path.get());
		FileWriter fw = new FileWriter(path.get(),false);
		PrintWriter pw = new PrintWriter(fw);

		double[][] adj  = generateAdjacentMatrix();
		String str = ArrayUtils.toString(adj);
		pw.print(str);
		//System.out.println(str);
		pw.close();

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
