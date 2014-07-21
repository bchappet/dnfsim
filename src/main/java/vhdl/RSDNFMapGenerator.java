package main.java.vhdl;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;

public class RSDNFMapGenerator extends VHDLGenerator2 {

public RSDNFMapGenerator(int res2) {
		super(res2);
	}

	//	public final static int[] LISTE_GEN = {3,5,7,9,11,19,31,35,49};
	public final static int[] LISTE_GEN = {49};

	//Direction definition for Spikes Vectors
	public final static int W = 0;
	public final static int E = 1;
	public final static int S = 2;
	public final static int N = 3;
	static final String modelName = "SpikingCNFTMap";


//	Space space;



	public static void main(String[] args) throws IOException, SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException{
		boolean wrap = false;

		for(int res : LISTE_GEN ){
//			Space space = new DefaultRoundedSpace(new Var(res), 2, wrap);
//			RSDNFMapGenerator gen = new RSDNFMapGenerator(space);
//			gen.generateFile("vhdlSource", modelName+"_"+res);
			
//			CNFTConfigurationGenerator gen2 = new CNFTConfigurationGenerator(res);
//			gen2.generateFile("vhdlSource", "CNFTConfiguration");
		}


	}


//	public RSDNFMapGenerator(Space space){
//		super((int)space.getResolution());
//		this.space = space;
//	}


	public void generateFile(String folder,String filename) throws IOException{
		Writer w = new FileWriter(folder+"/"+filename+".main.java.vhdl");
		PrintWriter bw = new PrintWriter(w);
//		int res = (int) space.getResolution();
		try{
			bw.println("library IEEE;");
			bw.println("use IEEE.STD_LOGIC_1164.ALL;");
			bw.println("use WORK.CNFTConfiguration.all;");
			bw.println("use WORK.CAPSRNGConfiguration.all;");
			bw.println("entity "+modelName+" is");

			bw.println("PORT(");

//			bw.println(getMapPorts());

			bw.println("compute,propagate,clk,reset,nextComp : in std_logic");
			bw.println(");");
			bw.println("end "+modelName+";");
			bw.println();

			bw.println("architecture Behavioral of "+modelName+" is");

			bw.println(generateNeuronComponent());


			bw.println("--Signal definition");
			bw.println("constant outSpikesExcNULL: STD_LOGIC_VECTOR(NEIGHBORHOOD-1 downto 0) := (others => '0');");
			bw.println("constant outSpikesInhNULL: STD_LOGIC_VECTOR(NEIGHBORHOOD-1 downto 0) := (others => '0');");

//			bw.println(getSignals());

			bw.println("begin");



			for(int i = 0 ; i < res ; i++){
				for(int j=0 ; j < res ; j++ ){
					bw.println(getNeuronInstantation(i, j));
				}
			}



			bw.println("END Behavioral;");


		}finally{
			bw.close();
			System.out.println("Closing " + folder+"/"+filename+".main.java.vhdl");
		}

	}

	public String getNeuronId(int x,int y){
		return ""+x+"_"+y;
	}

//	public String getNeighboor(int x,int y,int direction){
//		Double[] neigh = new Double[2];
//		boolean outOfBounds = false;
//		switch(direction){
//		case N:outOfBounds = space.wrapDiscrete(new Double[]{(double) x,(double) (y-1)},neigh);break;
//		case S:outOfBounds = space.wrapDiscrete(new Double[]{(double) x,(double) (y+1)},neigh);break;
//		case E:outOfBounds = space.wrapDiscrete(new Double[]{(double) (x+1),(double) y},neigh);break;
//		case W:outOfBounds = space.wrapDiscrete(new Double[]{(double) (x-1),(double) y},neigh);break;
//		default : //error;
//		}
//
//		if(outOfBounds && !space.isWrap()){
//			return "NULL";
//		}
//		else{
//			return getNeuronId(neigh[Space.X].intValue(),neigh[Space.Y].intValue());
//		}
//
//	}

	public String generateNeuronComponent(){
		return "component RSDNFCell is\n"+
				"port(\n"+
				"	 compute,propagate,clk,reset: in STD_LOGIC;\n"+
				"	 main.java.input: in std_logic_vector(INT+FRAC downto 0);\n"+
				"	 inSpikesExc: in STD_LOGIC_VECTOR(NEIGHBORHOOD-1 downto 0);--(N,S,E,W)\n"+
				"	 inSpikesInh: in STD_LOGIC_VECTOR(NEIGHBORHOOD-1 downto 0);\n"+
				"	 outSpikesExc: out STD_LOGIC_VECTOR(NEIGHBORHOOD-1 downto 0);\n"+
				"	 outSpikesInh: out STD_LOGIC_VECTOR(NEIGHBORHOOD-1 downto 0);\n"+
				"	 potential:  out std_logic_vector(INT+FRAC-1 downto 0)\n"+
				"   );\n"+
				"end component;\n";
	}

	public String getNeuronInstantation(int x,int y){

		String ret = "";

		ret  += "Neuron"+getNeuronId(x, y)+": RSDNFCell\n"+
				"port map(\n"+
				"CLK => CLK,\n"+
				"propagate => propagate,\n"+
				"compute => compute,\n"+
				"Reset => Reset,\n"+
				"main.java.input=> main.java.input"+getNeuronId(x, y)+",\n"+
//
//			 "inSpikesExc(N) => outSpikesExc" +getNeighboor(x, y, N) +"(S),\n" +   
//			 "inSpikesExc(S) => outSpikesExc" +getNeighboor(x, y, S) +"(N),\n" + 
//			 "inSpikesExc(E) => outSpikesExc" +getNeighboor(x, y, E) +"(W),\n" + 
//			 "inSpikesExc(W) => outSpikesExc" +getNeighboor(x, y, W) +"(E),\n" + 
//
//			 "inSpikesInh(N) => outSpikesInh" +getNeighboor(x, y, N) +"(S),\n" +   
//			 "inSpikesInh(S) => outSpikesInh" +getNeighboor(x, y, S) +"(N),\n" + 
//			 "inSpikesInh(E) => outSpikesInh" +getNeighboor(x, y, E) +"(W),\n" + 
//			 "inSpikesInh(W) => outSpikesInh" +getNeighboor(x, y, W) +"(E),\n" + 

			 "outSpikesExc => outSpikesExc"+getNeuronId(x, y)+",\n"+
			 "outSpikesInh => outSpikesInh"+getNeuronId(x, y)+",\n"+

			 "potential => potential"+getNeuronId(x, y)+"\n"+
			 ");";


		return ret;
	}






//
//	public String getOutputConnection() {
//		int res = (int) space.getResolution();
//		String ret = "";
//		for(int i = 0 ; i < res ; i++){
//			for(int j = 0 ; j  < res ; j++){
//				ret += "potential"+i+"_"+j+" <= potentialS"+i+"_"+j+";\n";
//			}
//		}
//		return ret;
//	}
//
//
//	public String getMapPorts(){
//		int res = (int) space.getResolution();
//		String ret = "";
//
//		for(int i = 0 ; i < res ; i++){
//			for(int j = 0 ; j  < res ; j++){
//				ret += "potential"+i+"_"+j+" : out std_logic_vector(INT+FRAC-1 downto 0);\n";
//				ret += "main.java.input"+i+"_"+j+" : in std_logic_vector(INT+FRAC downto 0);\n";
//			}
//		}
//
//		//		ret +="input0_0 : in std_logic_vector(DECIMAL+FRACTIONAL downto 0);\n" +
//		//		"potential0_0 :out std_logic_vector(DECIMAL+FRACTIONAL-1 downto 0);";
//		return ret;
//	}
//
//	public String getSignals(){
//		int res = (int) space.getResolution();
//		String ret = "";
//		for(int i = 0 ; i < res ; i++){
//			for(int j = 0 ; j  < res ; j++){
//				ret += "signal outSpikesExc"+getNeuronId(i, j)+" : std_logic_vector(NEIGHBORHOOD-1 downto 0);\n";
//				ret += "signal outSpikesInh"+getNeuronId(i, j)+" : std_logic_vector(NEIGHBORHOOD-1 downto 0);\n";
//			}
//		}
//		return ret;
//	}
//
//



}
