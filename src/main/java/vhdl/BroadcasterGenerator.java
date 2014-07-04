package main.java.vhdl;

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

import main.resources.utils.BitOverflowException;
import main.resources.utils.Hardware;

public class BroadcasterGenerator extends VHDLGenerator2 {
	
	

	
	
	protected int width_id;

	public BroadcasterGenerator(int res){
		super(res);
		this.width_id = Hardware.necessaryNbBit(res*res);
		System.out.println("WIDTH_ID := " + width_id + ";");
	}
	
	public static void main(String[] args) throws IOException, SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, BitOverflowException{
		BroadcasterGenerator gen = new BroadcasterGenerator(3);
		gen.generateFile("vhdlSource", "Broadcaster");
		
		//System.out.println(Hardware.necessaryNbBit(255));
		
		

	}


	public String createRomTableAER(){
		String ret = indent +"(" + "\n";
		
		for(int i = 1 ; i <= res*res ; i++){
			ret += indent + "" + i + " => " + Hardware.nThBit(i,res*res) + "," + "\n";
		}
		
		ret += indent +"others => (others => \'0\'))";
		
		
		return ret;
	}
	
	protected String recursiveGenerateDichotomicSearch(int start,int end,String indent2) throws BitOverflowException{
		String ret = "";
		if(end == start){
			ret += indent2+"id_active_neuron <= " + Hardware.toVectorString(end+1,width_id) + ";\n";
			
		}else {

			int middle = (start + end)/2;
			ret += indent2 + "if(merged("+middle+" downto "+start+") /= "+ Hardware.toVectorString(0,middle-start+1) +") then\n";
			ret += recursiveGenerateDichotomicSearch(start, middle,indent2+"\t");
			ret += indent2 + "else \n";
			ret += recursiveGenerateDichotomicSearch(middle+1, end,indent2+"\t");
			ret += indent2 + "end if; \n";
			//System.out.println(ret);
			
		}
		
		return ret;
		
	}
	
	public String generateDichotomicSearch() throws BitOverflowException{
		
		String ret = indent +  "if(merged(RES*RES-1 downto 0) = "+ Hardware.toVectorString(0,res*res)+") then\n";
				ret += indent +"	id_active_neuron <= (others => '0');\n";
				ret += indent +"else\n";
				ret +=recursiveGenerateDichotomicSearch(0,res*res-1,indent+"\t");
				ret += indent +"end if;\n";
		
		return ret;
	}
	
	


	

	


	
	
	







}
