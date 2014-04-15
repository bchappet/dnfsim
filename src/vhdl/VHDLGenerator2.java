package vhdl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VHDLGenerator2 {
	
	
	boolean write = true; //true if we replace code from @ to @END
	protected String indent = ""; //current indentation of text
	protected int res;
	
	public VHDLGenerator2(int res2) {
		res = res2;
	}
	
	public String END(){
		write = true;
		return "";
	}

	public void generateFile(String folder,String name) throws IOException, SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		Reader r = new FileReader(folder+"/"+name+".vhd");
		BufferedReader br = new BufferedReader(r);
		Writer w = new FileWriter(folder+"/"+name+"_"+res+".vhd");
		BufferedWriter bw = new BufferedWriter(w);


		try{

			String line;
			
			while((line = br.readLine()) != null){
				String addition = "";
				
				Pattern pattern = Pattern.compile("(\\s*)--@(\\w+);");
				Matcher matcher  = pattern.matcher(line);
				if(matcher.find()){
					write = false;
					addition = line + "\n";
					
					indent = matcher.group(1);
					String method = matcher.group(2);
					//call the method from this class

					Method toCall = this.getClass().getMethod(method);
					addition += (String) toCall.invoke(this);
					


				}
				
				if(!addition.isEmpty()){
					bw.write( addition);
					bw.newLine();
				}
				else {
					if(write){
						bw.write(line);
						bw.newLine();
					}
					
				}

				
			}


		}finally{
			System.out.println("Closing " + folder+"/"+name+"_"+res+".vhd");
			bw.close();
			br.close();
		}

	}

}
