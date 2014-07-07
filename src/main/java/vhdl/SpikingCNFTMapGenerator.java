package main.java.vhdl;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import main.resources.utils.BitOverflowException;
import main.resources.utils.Hardware;

public class SpikingCNFTMapGenerator extends VHDLGenerator2 {
	
	protected int INT = 3;
	protected int FRAC = 7;

	public SpikingCNFTMapGenerator(int res2) {
		super(res2);
	}
	
	public static void main(String[] args) throws SecurityException, IllegalArgumentException, IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException{
		int res = 35;
		SpikingCNFTMapGenerator gen1 = new SpikingCNFTMapGenerator(res);
		gen1.generateFile("vhdlSource", "SpikingCNFTMap");
		
		TestSpikingCNFTMapGenerator gen2 = new TestSpikingCNFTMapGenerator(res);
		gen2.generateFile("vhdlSource", "test_SpikingCNFTMap");
		
		
	}
	
	public String generatePorts(){
		String ret = "";
		for(int i = 0 ; i < res ; i ++){
			
			for(int j = 0 ; j < res ; j ++){
				ret += indent + "potential"+i+"_"+j+": out std_logic_vector(INT+FRAC-1 downto 0);\n";
				ret += indent + "main.java.input"+i+"_"+j+": in std_logic_vector(INT+FRAC downto 0);\n";
			}
		}
		return ret;
	}
	
	public String generateSignals(){
		String ret = "";
		for(int i = 0 ; i < res ; i ++){
			
			for(int j = 0 ; j < res ; j ++){
				ret += indent + "signal potentialS"+i+"_"+j+" : std_logic_vector(INT+FRAC-1 downto 0);\n";
				ret += indent + "signal activate"+i+"_"+j+" : std_logic;\n";
				ret += indent + "signal exc"+i+"_"+j+" : std_logic_vector(INT+FRAC-1 downto 0);\n";
				ret += indent + "signal inh"+i+"_"+j+" : std_logic_vector(INT+FRAC-1 downto 0);\n";
				ret += indent + "signal ovf"+i+"_"+j+",ovfExc"+i+"_"+j+",ovfInh"+i+"_"+j+" : std_logic;\n";
			}
		}
		return ret;
	}
	
	public String connectPotential(){
		
		String ret = "";
		
		for(int i = 0 ; i < res ; i ++){
			for(int j = 0 ; j < res ; j ++){
				ret += indent + "potential"+i+"_"+j+" <= potentialS"+i+"_"+j+";\n";
			}
		}
		
		return ret;
	}
	
	public String connectActiveNeuron(){
		String ret = indent +"activeNeuronsInt <=";
		for(int i = res -1 ; i >= 0 ; i --){
			for(int j = res - 1 ; j >= 0 ; j --){
				ret += "activate"+i+"_"+j+" & ";
			}
		}
		ret = ret.substring(0, ret.length()-2) + ";\n";
		
		return ret;
	}
	
	public String connectSpikingNeuron() throws BitOverflowException{
		String ret = "";
		for(int i = 0 ; i < res ; i ++){
			for(int j = 0 ; j < res ; j ++){
				
				ret += spikingNeuron(i,j);
				ret += lateralFeeding("exc",i,j);
				ret += lateralFeeding("inh",i,j);
				
			}
		}
		
		return ret;
	}
	
	protected String spikingNeuron(int i, int j){
	return	indent + "n"+i+"_"+j+": SpikingNeuron PORT MAP ( \n" +
			indent + "	activate => activate"+i+"_"+j+", \n" +
			indent + "	main.java.input => main.java.input"+i+"_"+j+", \n" +
			indent + "	potential => potentialS"+i+"_"+j+", \n" +
			indent + "	exc => exc"+i+"_"+j+", \n" +
			indent + "	inh => inh"+i+"_"+j+", \n" +
			indent + "	ovf => ovf"+i+"_"+j+", \n" +
			indent + "	enable => compute, \n" +
			indent + "	clk => clk, \n" +
			indent + "	reset => reset \n" +
			indent + ");\n";
	}
	
	protected String lateralFeeding(String name,int i ,int j) throws BitOverflowException{
		String ret = "";
		double factor;
		if(name.equals("exc"))
			factor = 0.02;
		else
			factor = 0.01;
			
		ret += indent + name+"F"+i+"_"+j+" : Synapse \n";
		ret += indent + "generic map(\n";
		ret += indent + "	rom_table =>\n";
		ret += getLateralWeightsTable(factor);
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
	
	/**
	 * TODO real weight
	 * @return
	 * @throws BitOverflowException 
	 */
	public String getLateralWeightsTable(double factor) throws BitOverflowException{
		String ret = indent +"(" + "\n";
		
		for(int i = 1 ; i <= res*res ; i++){
			ret += indent + "" + i + " => " + Hardware.toFPVectorString(i*factor,INT,FRAC,false) + "," + "\n";
		}
		
		ret += indent +"others => (others => \'0\'))\n";
		
		
		return ret;
	}

	

}
