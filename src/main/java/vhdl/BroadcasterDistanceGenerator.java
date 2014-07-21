package main.java.vhdl;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import main.resources.utils.BitOverflowException;
import main.resources.utils.Hardware;

public class BroadcasterDistanceGenerator extends VHDLGenerator2 {
	
	
	public final static int X = 0;
	public final static int Y = 1;
//	public final static int[] LISTE_GEN = {3,5,7,9,11,19,31,35};
	public final static int[] LISTE_GEN = {25,50,100,150,200};
	
	protected int width_id;

	public BroadcasterDistanceGenerator(int res){
		super(res);
		this.width_id = Hardware.necessaryNbBit(res)*2;
		System.out.println("WIDTH_ID := " + width_id + ";");
	}
	
	public static void main(String[] args) throws IOException, SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, BitOverflowException{
		
		for( int i : LISTE_GEN){
			BroadcasterDistanceGenerator gen = new BroadcasterDistanceGenerator(i);
			gen.generateFile("vhdlSource", "BroadcasterDistance");
		}
		
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
			int[] coord = indextoCoord(end,res);
			String x_bit = Hardware.toVectorString(coord[X] + 1,width_id/2);
			String y_bit = Hardware.toVectorString(coord[Y] + 1,width_id/2);
			ret += indent2 +"num_active_neuron <= " + Hardware.toVectorString(end+1,width_id) + ";\n";
			ret += indent2+"id_active_neuron <= " + x_bit +"&"+y_bit + ";\n";
			
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
	
	/**
	 * 
	 * @param index
	 * @param res
	 * @return [X,Y] (index % res , index / res)
	 */
	protected int[] indextoCoord(int index,int res){
		return new int[]{index % res , index / res};
	}
	
	


	

	


	
	
	







}
