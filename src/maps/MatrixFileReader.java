package maps;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import coordinates.Space;

/**
 * File format is fileName_$iteration$.??
 * @author bchappet
 *
 */
public class MatrixFileReader extends Matrix {
	
	protected String fileName;
	protected int iteration = 0;

	public MatrixFileReader(String name, Var dt, Space space,String fileName,
			Parameter... params) {
		super(name, dt, space, params);
		this.fileName = fileName;
	}
	
	@Override
	public void compute()  {
		try {
//			System.out.println("iteration : " + iteration);
			FileReader fr = new FileReader(fileName+"_"+iteration +".fig");
			BufferedReader br = new BufferedReader(fr);
			
			
			int i = 0;
			try{
				String line;
				line = br.readLine();
				line = br.readLine();
				line = br.readLine();
				while((line = br.readLine()) != null){
					String[] values = line.split(" ");
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
	
	@Override
	public void reset(){
		super.reset();
		iteration = 0;
	}
	
	

	

}
