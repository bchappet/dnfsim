package main.java.model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import main.java.maps.Map;
import main.java.maps.MatrixCSVFileReader;
import main.java.maps.Parameter;
import main.java.maps.Var;
import main.java.space.Space;

public class BooleanMatrixCSVFileReader  extends Map<Boolean,Integer> {
	
	private Boolean[] values;
	
	/**Parameters**/
	public final static int FILE_NAME = 0; //String
	public final static int SEP = 1;
	
	protected int iteration = 0;

	public BooleanMatrixCSVFileReader(String name, Var<BigDecimal> dt,
			Space<Integer> space, Parameter... params) {
		super(name, dt, space, params);
		this.values = new Boolean[space.getVolume()];
		for(int i=0 ; i < this.values.length ; i++){
			this.values[i] = false;
		}
	}
	
	@Override
	public void compute()  {
		try {
			String fileName = ((Var<String>) getParam(FILE_NAME)).get();
			String sep = ((Var<String>) getParam(SEP)).get();
			
			FileReader fr = new FileReader(fileName+"_"+iteration +".csv");
			BufferedReader br = new BufferedReader(fr);
			
			
			int i = 0;
			try{
				String line;
				while((line = br.readLine()) != null){
					String[] values = line.split(sep);
					for(String val : values){
						try{
							Double num = Double.parseDouble(val);
							boolean bool;
							if(num == 0.){
								bool = false;
							}else{
								bool = true;
							}
							this.setIndex(i,bool); 
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
	public Boolean getIndex(int index) {
		return this.values[index];
	}
	
	public void setIndex(int i, boolean val) {
		this.values[i] = val;
		
	}

	@Override
	public List<Boolean> getValues() {
		return new ArrayList<Boolean>(Arrays.asList(this.values));
	}

}
