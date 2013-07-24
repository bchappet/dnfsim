package vhdl;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

import maps.Var;

import coordinates.DefaultRoundedSpace;
import coordinates.Space;

public class VHDLGeneratorRSDNF extends VHDLGenerator {


	//Direction definition for Spikes Vectors
	public final static int W = 0;
	public final static int E = 1;
	public final static int S = 2;
	public final static int N = 3;
	static final String modelName = "RSDNFNetworkVisualAttention";


	Space space;
	
	
	
	public static void main(String[] args){
		int res = 3;
		boolean wrap = false;
		Space space = new DefaultRoundedSpace(new Var(res), 2, wrap);
		VHDLGeneratorRSDNF gen = new VHDLGeneratorRSDNF(space);
		try {
			gen.generateFile("vhdlSource", modelName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}


	public VHDLGeneratorRSDNF(Space space){
		this.space = space;
	}

	
	public void generateFile(String folder,String filename) throws IOException{
		Writer w = new FileWriter(folder+"/"+filename+".vhdl");
		PrintWriter bw = new PrintWriter(w);
		int res = (int) space.getResolution();
		try{
			bw.println("library IEEE;");
			bw.println("use IEEE.STD_LOGIC_1164.ALL;");
			bw.println("use WORK.RSDNFConfiguration.all;");
			bw.println("entity "+modelName+" is");
			
			bw.println("PORT(");

			bw.println(getMapPorts());
			
			bw.println("clk,compute_clk,reset : in std_logic");
			bw.println(");");
			bw.println("end "+modelName+";");
			bw.println();
			
			bw.println("architecture Behavioral of "+modelName+" is");

			bw.println(generateNeuronComponent());
			

			bw.println("--Signal definition");
			bw.println("constant ExcitationSpikesToNeighborsNULL: STD_LOGIC_VECTOR(NEIGHBORHOOD-1 downto 0) := (others => '0');");
			bw.println("constant InhibitionSpikesToNeighborsNULL: STD_LOGIC_VECTOR(NEIGHBORHOOD-1 downto 0) := (others => '0');");

			bw.println(getSignals());

			bw.println("begin");

			
			
			for(int i = 0 ; i < res ; i++){
				for(int j=0 ; j < res ; j++ ){
					bw.println(getNeuronInstantation(i, j));
				}
			}
			
			
			
			bw.println("END Behavioral;");


		}finally{
			bw.close();
		}
		
	}

	public String getNeuronId(int x,int y){
		return ""+x+"_"+y;
	}

	public String getNeighboor(int x,int y,int direction){
		Double[] neigh = new Double[2];
		boolean outOfBounds = false;
		switch(direction){
		case N:outOfBounds = space.wrapDiscrete(new Double[]{(double) x,(double) (y-1)},neigh);break;
		case S:outOfBounds = space.wrapDiscrete(new Double[]{(double) x,(double) (y+1)},neigh);break;
		case E:outOfBounds = space.wrapDiscrete(new Double[]{(double) (x+1),(double) y},neigh);break;
		case W:outOfBounds = space.wrapDiscrete(new Double[]{(double) (x-1),(double) y},neigh);break;
		default : //error;
		}

		if(outOfBounds && !space.isWrap()){
			return "NULL";
		}
		else{
			return getNeuronId(neigh[Space.X].intValue(),neigh[Space.Y].intValue());
		}

	}
	
	public String generateNeuronComponent(){
		return "component RSDNFSpikingNeuron is\n"+
			"port(\n"+
			"	 CLK: in STD_LOGIC;\n"+
			"	 COMPUTE_CLK : in STD_LOGIC;\n"+
			"	 Reset: in STD_LOGIC;\n"+
			"	 FeedingPixel: in std_logic_vector(INT+FRAC downto 0);\n"+
			"	 ExcitationSpikesFromNeighbors: in STD_LOGIC_VECTOR(NEIGHBORHOOD-1 downto 0);--(N,S,E,W)\n"+
			"	 InhibitionSpikesFromNeighbors: in STD_LOGIC_VECTOR(NEIGHBORHOOD-1 downto 0);\n"+
			"	 ExcitationSpikesToNeighbors: out STD_LOGIC_VECTOR(NEIGHBORHOOD-1 downto 0);\n"+
			"	 InhibitionSpikesToNeighbors: out STD_LOGIC_VECTOR(NEIGHBORHOOD-1 downto 0);\n"+
			"	 Potential:  out std_logic_vector(INT+FRAC-1 downto 0)\n"+
			 "   );\n"+
			 "end component;\n";
	}

	public String getNeuronInstantation(int x,int y){

		String ret = "";

		ret  += "Neuron"+getNeuronId(x, y)+": RSDNFSpikingNeuron\n"+
				"port map(\n"+
				"CLK => CLK,\n"+
				"COMPUTE_CLK => compute_clk,\n"+
				"Reset => Reset,\n"+
				"FeedingPixel=> input"+getNeuronId(x, y)+",\n"+

			 "ExcitationSpikesFromNeighbors(N) => ExcitationSpikesToNeighbors" +getNeighboor(x, y, N) +"(S),\n" +   
			 "ExcitationSpikesFromNeighbors(S) => ExcitationSpikesToNeighbors" +getNeighboor(x, y, S) +"(N),\n" + 
			 "ExcitationSpikesFromNeighbors(E) => ExcitationSpikesToNeighbors" +getNeighboor(x, y, E) +"(W),\n" + 
			 "ExcitationSpikesFromNeighbors(W) => ExcitationSpikesToNeighbors" +getNeighboor(x, y, W) +"(E),\n" + 

			 "InhibitionSpikesFromNeighbors(N) => InhibitionSpikesToNeighbors" +getNeighboor(x, y, N) +"(S),\n" +   
			 "InhibitionSpikesFromNeighbors(S) => InhibitionSpikesToNeighbors" +getNeighboor(x, y, S) +"(N),\n" + 
			 "InhibitionSpikesFromNeighbors(E) => InhibitionSpikesToNeighbors" +getNeighboor(x, y, E) +"(W),\n" + 
			 "InhibitionSpikesFromNeighbors(W) => InhibitionSpikesToNeighbors" +getNeighboor(x, y, W) +"(E),\n" + 

			 "ExcitationSpikesToNeighbors => ExcitationSpikesToNeighbors"+getNeuronId(x, y)+",\n"+
			 "InhibitionSpikesToNeighbors => InhibitionSpikesToNeighbors"+getNeuronId(x, y)+",\n"+

			 "Potential => potential"+getNeuronId(x, y)+"\n"+
		");";


		 return ret;
	}




	


	public String getOutputConnection() {
		int res = (int) space.getResolution();
		String ret = "";
		for(int i = 0 ; i < res ; i++){
			for(int j = 0 ; j  < res ; j++){
				ret += "potential"+i+"_"+j+" <= potentialS"+i+"_"+j+";\n";
			}
		}
		return ret;
	}


	public String getMapPorts(){
		int res = (int) space.getResolution();
		String ret = "";

		for(int i = 0 ; i < res ; i++){
			for(int j = 0 ; j  < res ; j++){
				ret += "potential"+i+"_"+j+" : out std_logic_vector(INT+FRAC-1 downto 0);\n";
				ret += "input"+i+"_"+j+" : in std_logic_vector(INT+FRAC downto 0);\n";
			}
		}

		//		ret +="input0_0 : in std_logic_vector(DECIMAL+FRACTIONAL downto 0);\n" +
		//		"potential0_0 :out std_logic_vector(DECIMAL+FRACTIONAL-1 downto 0);";
		return ret;
	}

	public String getSignals(){
		int res = (int) space.getResolution();
		String ret = "";
		for(int i = 0 ; i < res ; i++){
			for(int j = 0 ; j  < res ; j++){
				ret += "signal ExcitationSpikesToNeighbors"+getNeuronId(i, j)+" : std_logic_vector(NEIGHBORHOOD-1 downto 0);\n";
				ret += "signal InhibitionSpikesToNeighbors"+getNeuronId(i, j)+" : std_logic_vector(NEIGHBORHOOD-1 downto 0);\n";
			}
		}
		return ret;
	}

	

	

}
