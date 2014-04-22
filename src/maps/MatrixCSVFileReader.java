package maps;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

import coordinates.Space;

public class MatrixCSVFileReader extends MatrixFileReader implements Parameter {
	
	
	public final static int SEP = 1;
	

	public MatrixCSVFileReader(String name, Var dt, Space space, Parameter... params) {
		super(name, dt, space, params);
	}
	
	@Override
	public void compute()  {
		try {
			String fileName = ((VarString) getParam(FILE_NAME)).getString();
			String sep = ((VarString) getParam(SEP)).getString();
			
			FileReader fr = new FileReader(fileName+"_"+iteration +".csv");
			BufferedReader br = new BufferedReader(fr);
			
			
			int i = 0;
			try{
				String line;
				while((line = br.readLine()) != null){
					String[] values = line.split(sep);
					for(String val : values){
						try{
							this.values[i] = Double.parseDouble(val); 
						}catch (ArrayIndexOutOfBoundsException e) {
							break;
						}
							i++;
					}
				}
			}catch (Exception e) {
				e.printStackTrace();
			}finally{
				br.close();
			}
			
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
		iteration ++;
	}

}
