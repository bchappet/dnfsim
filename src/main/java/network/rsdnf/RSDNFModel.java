package main.java.network.rsdnf;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.java.console.CommandLine;
import main.java.console.CommandLineFormatException;
import main.java.coordinates.NullCoordinateException;
import main.java.maps.Var;
import main.java.network.generic.NetworkModel;
import static main.java.network.generic.SpreadingGraphFactory.TypeGraph.RSDNF;
import main.java.network.generic.Utils;

/**
 *
 * @author CARRARA Nicolas
 */
public class RSDNFModel extends NetworkModel {

    public RSDNFModel(String name) {
        super(name);
    }

    public static void main(String[] args) {
//        RSDNFModel r = new RSDNFModel(("coucou"));
        try {
            //        new ModelESN("ok");
            RSDNFModel r = new RSDNFModel(("coucou"));
            CommandLine cl = new RSDNFCommandLine() {
                @Override
                public String defaultScript() {
                    return super.defaultScript() + WEIGTH + "=0.0;";
                }
            };//rsdnf.constructCommandLine();
            cl.setContext("");
            r.initialize(cl);
        } catch (CommandLineFormatException ex) {
            Logger.getLogger(RSDNFModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(RSDNFModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(RSDNFModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullCoordinateException ex) {
            Logger.getLogger(RSDNFModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * creer un fichier reprensentant le reseau RSDNF
     *
     * @return Un fichier representant le reseau RSDNF
     * @throws main.java.console.CommandLineFormatException
     */
    public File writeRSDNFNetworkFile() throws CommandLineFormatException {
        double[][] matrice = matToFlat(generateAdjMatrix(10, 4), 10, 4);
        File f = new File(((Var<String>) command.get(RSDNFCommandLine.PATH_RSDNF_FILE)).get());
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
    protected void constructGraph() {
        try {
            File networkFile = writeRSDNFNetworkFile();
            setSpreadingGraph(getSpreadingGraphFactory().constructGraph(networkFile, RSDNF, command));
        } catch (CommandLineFormatException ex) {
            Logger.getLogger(RSDNFModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected boolean computeStopCondition() {
        return false;
    }

    @Override
    public CommandLine constructCommandLine() {
        return new RSDNFCommandLine();
    }

}
