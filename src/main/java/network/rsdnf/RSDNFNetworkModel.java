package main.java.network.rsdnf;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;

import main.java.console.CommandLine;
import main.java.console.CommandLineFormatException;
import main.java.coordinates.NullCoordinateException;
import main.java.maps.MatrixCSVFileReader;
import main.java.maps.Parameter;
import main.java.maps.UnitMap;
import main.java.maps.Var;
import main.java.network.generic.NetworkCommandLine;
import main.java.network.generic.NetworkModel;
import main.java.network.generic.SpreadingGraph;
import main.java.network.generic.TypeGraph;
import main.java.network.generic.Utils;
import main.java.network.generic.packet.Spike;
import main.java.space.Space2D;
import main.java.unitModel.UnitModel;

/**
 *
 * @author CARRARA Nicolas
 */
public class RSDNFNetworkModel extends NetworkModel<RSDNFTransmitter,Spike,SpikeEdge> {

	private static final String NEURAL_MAP = "NeuralMap";

	public  RSDNFNetworkModel(String name) throws CommandLineFormatException {
		super(name);
		
	}
	
	@Override
	protected void initializeParameters() throws CommandLineFormatException, NullCoordinateException{
		writeRSDNFNetworkFile();
		super.initializeParameters();
		
		Var<BigDecimal> dt = (Var<BigDecimal>)((NetworkCommandLine)command).get(NetworkCommandLine.NETWORK_DT);
		Var<Integer> size = (Var<Integer>)((NetworkCommandLine)command).get(RSDNFCommandLine.WIDTH_NEURON_MAP); 
		
		System.out.println("Size : " + size.get());
		Space2D space = new Space2D(size, size);

		//Compute the nb of received spike per neuron
		UnitMap<Double, Integer> neuralMap = new UnitMap<>(this.NEURAL_MAP, dt, space, new UnitModel<Double>(0.) {
			@Override
			protected Double compute(BigDecimal time, int index,
					List<Parameter> params) {
				//we have to agragate every 4 transmitter nb
				SpreadingGraph graph = (SpreadingGraph) params.get(0);
//				System.out.println(graph);
				int nbTrans = 4;
				double sum = 0;
				for (int i = 0; i < nbTrans; i++) {
//					System.out.println("index acces " + (index * nbTrans + i));
					sum += graph.getIndex(index * nbTrans + i).getTotalPacketReceived();
				}
				return sum;
			}
		}, this.getSpreadingGraph());
		this.addParameters(neuralMap);
	}

	/**
	 * creer un fichier reprensentant le reseau RSDNF
	 *
	 * @return Un fichier representant le reseau RSDNF
	 * @throws main.java.console.CommandLineFormatException
	 */
	public File writeRSDNFNetworkFile() throws CommandLineFormatException {
		int width_neuron_map = ((Var<Integer>)command.get(RSDNFCommandLine.WIDTH_NEURON_MAP)).get();
		width_neuron_map = width_neuron_map * width_neuron_map;
		System.out.println("widht neuron map = " + width_neuron_map);
		int transmitter_by_neuron = ((Var<Integer>)command.get(RSDNFCommandLine.TRANSMITTER_BY_NEURON)).get();
		System.out.println("transmitter_by_neuron = " + transmitter_by_neuron);
		double[][] matrice = matToFlat(generateAdjMatrix(width_neuron_map, transmitter_by_neuron), width_neuron_map, transmitter_by_neuron);
		System.out.println(command.get(RSDNFCommandLine.TRANSITION_MATRIX_FILE).get());
		File f = new File(((Var<String>) command.get(RSDNFCommandLine.TRANSITION_MATRIX_FILE)).get());
		Utils.writeCSVFile(f, matrice);
		return f;
	}

	//    def generateRandomAdjMatrix(n,t):
	public static double[][][][] generateAdjMatrix(int n, int t) {
		int N = 0;
		int S = 1;
		int E = 2;
		int W = 3;
		//	adj = np.zeros((n,n,t,t),dtype=np.float) #adjacent matric of the graph
		double[][][][] adj = new double[n][n][t][t];
		//	sqrt = int( math.sqrt(n))
		int sqrt = (int) Math.sqrt(n);
		//	for i in range(n):
		for (int i = 0; i < n; i++) {
			//          for j in range(n):
			for (int j = 0; j < n; j++) {
				//		if j == i-sqrt:#N
				if (j == (i - sqrt)) {
					//                  rand = getRandom(i,j);
					//                  adj[i,j,N,N] =rand;
					adj[i][j][N][N] = 1;
					//                  adj[i,j,N,E] =rand;
					adj[i][j][N][E] = 1;
					//                  adj[i,j,N,W] =rand;
					adj[i][j][N][W] = 1;
					//		elif j == i+sqrt:#S
				} else if (j == i + sqrt) {
					//                  rand = getRandom(i,j);
					//                  adj[i,j,S,S] =rand;
					adj[i][j][S][S] = 1;
					//                  adj[i,j,S,E] =rand;
					adj[i][j][S][E] = 1;
					//                  adj[i,j,S,W] =rand;
					adj[i][j][S][W] = 1;
					//		elif j == (i+1) and (i+1) % sqrt != 0:#E
				} else if (j == (i + 1) && (i + 1) % sqrt != 0) {
					//                  adj[i,j,E,E] =getRandom(i,j);
					adj[i][j][E][E] = 1;
					//		elif j == (i-1) and i %sqrt != 0:#W
				} else if (j == (i - 1) && i % sqrt != 0) {
					//                  adj[i,j,W,W] =getRandom(i,j);
					adj[i][j][W][W] = 1;
					//              else:
				} else {
					//                  adj[i,j] = 0
				}
			}
		}
		//      return adj
		return adj;
	}

	public static double[][] matToFlat(double[][][][] m, int n, int t) {
		//flatMat = np.zeros((n*t,n*t),dtype=np.float) #adjacent matric of the graph
		System.out.println(" flat mat dim  = " + n * t);
		double[][] flatMat = new double[n * t][n * t];
		//for i in range(m.shape[0]):
		for (int i = 0; i < m.length; i++) {
			//for j in range(m.shape[1]):
			for (int j = 0; j < m[0].length; j++) {
				//for t1 in range(t):
				for (int t1 = 0; t1 < t; t1++) {
					//for t2 in range(t):
					for (int t2 = 0; t2 < t; t2++) {
						//flatMat[j*t+t1,i*t+t2] = m[i,j,t2,t1]
						flatMat[j * t + t1][i * t + t2] = m[i][j][t2][t1];
					}
				}
			}
		}
		//return flatMat
		return flatMat;
	}

	@Override
	protected void constructGraph(File matrixTransitionFile) {
		try {
			setSpreadingGraph(getSpreadingGraphFactory().constructGraph(matrixTransitionFile, TypeGraph.RSDNF, command));
//			matrixTransitionFile.delete();
		} catch (CommandLineFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public CommandLine constructCommandLine() {
		return new RSDNFCommandLine();
	}

}
