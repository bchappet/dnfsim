package main.java.network.probalisticFlooding;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import main.java.console.CommandLine;
import main.java.console.CommandLineFormatException;
import main.java.maps.Var;
import main.resources.utils.ArrayUtils;

public class PFUtils {
	public static double[][] generateAdjacentMatrix(int size) throws CommandLineFormatException{
		

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
		//System.out.println(Arrays.toString(adj));
		return adj;
	}

	public static void writePFAdjacentMatrix(String path,int size) throws IOException, CommandLineFormatException {
		
		
		//		System.out.println(path.get());
		//		System.out.println("path pfmodel : " + path.get());
		//System.out.println("salut salut :"+path.get());
		FileWriter fw = new FileWriter(path,false);
		PrintWriter pw = new PrintWriter(fw);

		double[][] adj  = generateAdjacentMatrix(size);
		String str = ArrayUtils.toString(adj);
		pw.print(str);
		//System.out.println(str);
		pw.close();

	}
}
