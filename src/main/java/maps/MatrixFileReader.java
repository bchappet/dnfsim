package main.java.maps;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import main.java.space.Space;

/**
 * File format is fileName_$iteration$.??
 * @author bchappet
 *
 */
public class MatrixFileReader extends Map<Double,Integer> {
	
	private Double[] values;
	
	/**Parameters**/
	public final static int FILE_NAME = 0; //String
	
	protected int iteration = 0;

	public MatrixFileReader(String name, Var<BigDecimal> dt, Space<Integer> space,
			Parameter... params) {
		super(name, dt, space, params);
		this.values = new Double[space.getVolume()];
		for(int i=0 ; i < this.values.length ; i++){
			this.values[i] = 0d;
		}
	}
	
	@Override
	public void compute()  {
		try {
//			System.out.println("iteration : " + iteration);
			String fileName = ((Var<String>) getParam(FILE_NAME)).get();
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
	public Double getIndex(int index) {
		return this.values[index];
	}

	@Override
	public List<Double> getValues() {
		return new ArrayList<Double>(Arrays.asList(this.values));
	}

	public void setIndex(int i, double val) {
		this.values[i] = val;
		
	}

	@Override
	public void reset() {
		this.iteration = 0;
		super.reset();
	}
	
	
	
	
	
	

	

}
