package main.java.maps;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;

import main.java.space.Space;

/**
 * Read a signal file (csv) considering that every line is a computational state.
 * Consequently only SingleValueParameters or one dimensional vector can be stored with signal file
 * The file cannot be changed during computation for now... (the stream is open on construction)
 * @author bchappet
 *
 */
public class VectorFileReaderMap extends MatrixCSVFileReader {

	protected String sep;
	protected BufferedReader br;

	public VectorFileReaderMap(String name, Var<BigDecimal> dt,
			Space<Integer> space, Parameter... params) throws FileNotFoundException {
		super(name, dt, space, params);

		String fileName = ((Var<String>) getParam(FILE_NAME)).get();
		sep = ((Var<String>) getParam(SEP)).get();

		FileReader fr = new FileReader(fileName);
		br = new BufferedReader(fr);
	}

	@Override
	public void compute()  {
		String line;
		try {
			line = br.readLine();
			if(line == null){
				throw new NoMoreDataException("The file " + getParam(FILE_NAME).getIndex(0) + " does not have any more data to read.");
			}
			String[] valuesS = line.split(sep);
			for(int i = 0 ; i < valuesS.length ; i++){
				String val = valuesS[i];
				super.setIndex(i,Double.parseDouble(val)); 
			}
		} catch (IOException | NoMoreDataException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}






}
