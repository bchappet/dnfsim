package main.java.maps;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;

import main.java.space.Space;
import main.resources.utils.FluxUtils;

public class MatrixCSVFileReader extends MatrixFileReader  {
	

	

	public MatrixCSVFileReader(String name, Var<BigDecimal> dt, Space<Integer> space, Parameter... params) {
		super(name, dt, space, params);
	}
	
	@Override
	public void compute()  {
		try {
			String fileName = ((Var<String>) getParam(FILE_NAME)).get();

			
			FileReader fr = new FileReader(fileName+"_"+iteration +".csv");
			BufferedReader br = new BufferedReader(fr);
			
			
			int i = 0;
			try{
				String line;
				while((line = br.readLine()) != null){
					String[] values = line.split(FluxUtils.SEP);
					for(String val : values){
						try{
							super.setIndex(i,Double.parseDouble(val)); 
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
