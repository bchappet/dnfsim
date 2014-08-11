package main.java.space;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class SpaceFactory {

	/**
	 * Construct 1D space from signal file
	 * @param file
	 * @param sep
	 * @return
	 * @throws IOException
	 */
	public static Space1D getSpace1D(String file,String sep) throws IOException{
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		
		String[] data = br.readLine().split(sep);
		int res = data.length;
		br.close();
		
		return new Space1D(res);
		
		
	}

}
