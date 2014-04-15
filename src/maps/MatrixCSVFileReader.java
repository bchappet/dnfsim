package maps;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import coordinates.Space;

public class MatrixCSVFileReader extends MatrixFileReader implements Parameter {
	
	protected String sep;

	public MatrixCSVFileReader(String name, Var dt, Space space,
			String fileName,String sep, Parameter... params) {
		super(name, dt, space, fileName, params);
		this.sep = sep;
	}
	
	@Override
	public void compute()  {
		try {
//			System.out.println("iteration : " + iteration);
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
