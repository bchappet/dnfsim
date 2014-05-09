package main.java.vhdl;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import main.resources.utils.BitOverflowException;
import main.resources.utils.Hardware;

public class SpikingCNFTMapDistanceGenerator extends SpikingCNFTMapGenerator {
	
	protected int coord_width; //size of the bit vector for each coord
//	public final static int[] LISTE_GEN = {3,5,7,9,11,19,31,35,49};
	public final static int[] LISTE_GEN = {49};
	public SpikingCNFTMapDistanceGenerator(int res2) {
		super(res2);
		coord_width = Hardware.necessaryNbBit(res);
	}
	
	public static void main(String[] args) throws SecurityException, IllegalArgumentException, IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException{
		for( int res : LISTE_GEN){
		SpikingCNFTMapDistanceGenerator gen1 = new SpikingCNFTMapDistanceGenerator(res);
		gen1.generateFile("vhdlSource", "SpikingCNFTMapDistance");
		
		CNFTConfigurationGenerator gen2 = new  CNFTConfigurationGenerator(res);
		gen2.generateFile("vhdlSource", "CNFTConfiguration");
		
		TestSpikingCNFTMapGenerator gen3 = new TestSpikingCNFTMapGenerator(res);
		gen3.generateFile("vhdlSource", "test_SpikingCNFTMap");
		
		IOWrapperCNFTGenerator gen4 = new IOWrapperCNFTGenerator(res);
		gen4.generateFile("vhdlSource", "IOWrapperCNFT");
		
		BroadcasterDistanceGenerator gen5 = new BroadcasterDistanceGenerator(res);
		gen5.generateFile("vhdlSource", "BroadcasterDistance");
		}
		
		
	}
	
	
	protected String lateralFeeding(String name,int i ,int j) throws BitOverflowException{
		String ret = "";
		double factor;
		if(name.equals("exc"))
			factor = 0.02;
		else
			factor = 0.01;
			
		ret += indent + name+"F"+i+"_"+j+" : SynapseDistance \n";
		ret += indent + "generic map(\n";
		ret += indent + "	rom_table => rom_table_"+ name +",\n";
		ret += indent + "COORD => " +  Hardware.toVectorString(i+1, coord_width) + "&" +  Hardware.toVectorString(j+1, coord_width) + "\n";
		ret += indent +")PORT MAP (\n";
		ret += indent +"	brodcastedNeuronId => brodcastedNeuronIdInt,\n";
		ret += indent +"	lateralFeedingOut => "+name+i+"_"+j+",\n";
		ret += indent +"	ovf => ovf"+name+i+"_"+j+",\n";
		ret += indent +"	enable => propagate,\n";
		ret += indent +"	clk => clk,\n";
		ret += indent +"	reset => nextComp\n";
		ret += indent +");\n";
		
		return ret;
	}
	
	

	public String generateExcTable() throws BitOverflowException{
		return getLateralWeightsTable(0.2);
	}
	
	public String generateInhTable() throws BitOverflowException{
		return getLateralWeightsTable(0.1);
	}
	
	/**
	 * TODO real weight
	 * @return
	 * @throws BitOverflowException 
	 */
	public String getLateralWeightsTable(double factor) throws BitOverflowException{
		String ret = indent +"(" + "\n";
		ret += indent + "0 => " + Hardware.toVectorString(0, INT+FRAC) + ",\n";
		for(int i = 1 ; i <= (res-1)*2 ; i++){
			ret += indent + "" + i + " => " + Hardware.toFPVectorString((((res-1)*2)-i+1)*factor,INT,FRAC,false) + "," + "\n";
		}
		ret = ret.substring(0,ret.length()-2) + "\n";
		ret += indent  + ")\n";
		
		
		
		
		return ret;
	}

}
