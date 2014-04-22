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
	
	/**Parameters**/
	public final static int FILE_NAME = 0;
	protected int iteration = 0;

	public MatrixFileReader(String name, Var dt, Space space,
			Parameter... params) {
		super(name, dt, space, params);
	}
	
	@Override
	public void compute()  {
		try {
//			System.out.println("iteration : " + iteration);
			String fileName = ((VarString) getParam(FILE_NAME)).getString();
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
