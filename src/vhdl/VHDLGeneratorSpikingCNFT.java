package vhdl;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

import unitModel.GaussianND;
import unitModel.UnitModel;

import console.CNFTCommandLine;
import console.CommandLine;
import console.CommandLineFormatException;
import coordinates.DefaultRoundedSpace;
import coordinates.NullCoordinateException;
import coordinates.Space;

import maps.Map;
import maps.Parameter;
import maps.TrajectoryUnitMap;
import maps.Var;

public class VHDLGeneratorSpikingCNFT extends VHDLGeneratorCNFT {
	
	protected String name;

	public VHDLGeneratorSpikingCNFT(int res, Parameter wExc, Parameter wInh) {
		super(res, wExc, wInh);
	}
	
	public static void main(String[] args){
		
		
		int res = 19;
		CommandLine command = null;
		try {
			command = new CommandLine(
						"ia=1.25;"
						+"ib=-0.70;"
						+"wa=0.10;"
						+"wb=1.00;"
						+"tau=0.75;"
						+"alpha=10.0;"
						+"dt=0.1");
		} catch (CommandLineFormatException e1) {
			e1.printStackTrace();
		}
		
		Space space = new DefaultRoundedSpace(new Var(res), 2, true);
		Map[] cnft = null;
		try {
			cnft = initLateralWeightParams(command,space);
		} catch (CommandLineFormatException e) {
			e.printStackTrace();
		}
	
		
		VHDLGeneratorSpikingCNFT gen = new VHDLGeneratorSpikingCNFT(res,cnft[0],cnft[1]);
		
		
		try {
			gen.generateFile("vhdlSource","SpikingCNFTMap");
			gen.generateNeuronsFiles("vhdlSource","SpikingNeuronMixt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
	}
	
	/**
	 * Initialize somme Parameter used in lateral weights
	 * the objective here is to normalize some parameters
	 * Warning : use "this.space.getSimulationSpace().getResolution()" to acces the
	 * 	original resolution of the model and not the extended space resolution
	 * @throws CommandLineFormatException
	 * @throws CloneNotSupportedException
	 * @throws NullCoordinateException 
	 */
	protected static Map[] initLateralWeightParams(CommandLine command, final Space extendedSpace) 
			throws CommandLineFormatException
			{

		Parameter alphaP = command.get(CNFTCommandLine.ALPHA);

		Parameter pA = command.get(CNFTCommandLine.IA);
		Parameter hpA = new TrajectoryUnitMap("A_hidden",command.get(CNFTCommandLine.DT),
				extendedSpace,pA,alphaP) {

			@Override
			public double computeTrajectory(double... param) {
				return param[0] / 
						(extendedSpace.getSimulationSpace().getResolution()*
								extendedSpace.getSimulationSpace().getResolution()) *
								(40*40)/(param[1]);
			}
		}; 
		hpA.toStatic();


		Parameter pB = command.get(CNFTCommandLine.IB);
		Parameter hpB = new TrajectoryUnitMap("B_hidden",command.get(CNFTCommandLine.DT),
				extendedSpace,pB,alphaP) {

			@Override
			public double computeTrajectory(double... param)  {
				return param[0] / 
						(extendedSpace.getSimulationSpace().getResolution()*
								extendedSpace.getSimulationSpace().getResolution()) *
								(40*40)/param[1];
			}
		}; 
		hpB.toStatic();

		Parameter pa = command.get(CNFTCommandLine.WA);
		Parameter pb = command.get(CNFTCommandLine.WB);
		
		UnitModel a = new GaussianND(new Var(0.1), extendedSpace , pa, hpA, new Var(0),new Var(0));
		Map cnfta = new Map("cnftW" + "_A",a);
		cnfta.toStatic();
		UnitModel b = new GaussianND(new Var(0.1), extendedSpace, pb, hpB, new Var(0),new Var(0));
		Map cnftb = new Map("cnftW" + "_B",b);
		cnftb.toStatic();
		
		return new Map[]{cnfta,cnftb};

			}

	
	
	public void generateNeuronsFiles(String folder,String name) throws IOException{
		for(int i = 0 ; i < res ; i++){
			for(int j=0 ; j < res ; j++ ){
				VHDLNeuronGeneratorSpikingCNFT n = new VHDLNeuronGeneratorSpikingCNFT(i, j, res, wExc, wInh);
				n.generateNeuron(folder,name);
			}
		}
	}


	public void generateFile(String folder,String name) throws IOException{
		Writer w = new FileWriter(folder+"/"+name+".vhdl");
		this.name = name;
		PrintWriter bw = new PrintWriter(w);


		try{
			bw.println("library IEEE;");
			bw.println("use IEEE.STD_LOGIC_1164.ALL;");
			bw.println("use WORK.CNFTConfiguration.all;");
			bw.println("entity "+name+" is");
			
			bw.println("PORT(");

			bw.println(getMapPorts());
			
			bw.println("clk,compute_clk,reset : in std_logic");
			bw.println(");");
			bw.println("end "+name+";");
			bw.println("architecture Behavioral of "+name+" is");

			for(int i = 0 ; i < res ; i++){
				for(int j=0 ; j < res ; j++ ){
					bw.println(getNeuronComponent(i, j));
				}
			}

			
		

			bw.println( "type umemory_spike_array is array(0 to INT+FRAC-1) of std_logic;");
			bw.println(" type smemory_array is array(0 to INT+FRAC) of std_logic_vector(RES*RES-1 downto 0);");

			bw.println(	"signal spike_map : umemory_spike_array;");
			bw.println(	"signal input_map : smemory_array;");

			bw.println(getSignals());

			bw.println("begin");

			//bw.println(getOutputConnection());
			//bw.println("potential0_0 <= potentialS0_0;");

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


	public String getOutputConnection() {
		String ret = "";
		for(int i = 0 ; i < res ; i++){
			for(int j = 0 ; j  < res ; j++){
				ret += "spike"+i+"_"+j+" <= spikeS"+i+"_"+j+";\n";
			}
		}
		return ret;
	}


	public String getMapPorts(){
		String ret = "";

		for(int i = 0 ; i < res ; i++){
			for(int j = 0 ; j  < res ; j++){
				ret += "potential"+i+"_"+j+" : out std_logic_vector(INT+FRAC-1 downto 0);\n";
				ret += "input"+i+"_"+j+" : in std_logic_vector(INT+FRAC downto 0);\n";
			}
		}
		
//		ret +="input0_0 : in std_logic_vector(INT+FRAC downto 0);\n" +
//		"potential0_0 :out std_logic_vector(INT+FRAC-1 downto 0);";
		return ret;
	}

	public String getSignals(){
		String ret = "";
		for(int i = 0 ; i < res ; i++){
			for(int j = 0 ; j  < res ; j++){
				ret += "signal spikeS"+i+"_"+j+" : std_logic;\n";
			}
		}
		return ret;
	}

	public String getNeuronComponent(int i,int j){

		String ret = "";

		ret += "COMPONENT SpikingNeuronMixt"+i+"_"+j+"\n" +
				"PORT(\n" +
				VHDLNeuronGeneratorSpikingCNFT.generatePotentialPorts(res) +
				"     input : IN  std_logic_vector(INT+FRAC downto 0);\n" +
				"		spike : out std_logic; \n"+
				"     potentialOut : OUT  std_logic_vector(INT+FRAC-1 downto 0);\n" +
				"     clk : IN  std_logic;\n" +
				"	  compute_clk : in std_logic;\n" +
				"     reset : IN  std_logic\n" +
				"     );\n" +
				"END COMPONENT;";

		return ret;
	}

	public String getNeuronInstantation(int x,int y){
		String ret = "n"+x+"_"+y+":  SpikingNeuronMixt"+x+"_"+y+" PORT MAP (\n";

		for(int i = 0 ; i < res ; i++){
			for(int j  = 0 ; j< res ; j++){
				if(i == x && j == y)
					ret += "spike"+i+"_"+j+" => '0',\n";
				else
					ret += "spike"+i+"_"+j+" => " + "spikeS"+i+"_"+j+",\n";
			}
		}

		if(x==0 && y==0)
			ret += "input =>input0_0,\n";
		else
				//ret +=  "input =>(others => '0'),\n";  //input"+x+"_"+y+",\n";
			ret +=  "input =>input"+x+"_"+y+",\n";
		
			ret +=		"potentialOut =>  potential"+x+"_"+y+",\n"+
						"spike => spikeS"+x+"_"+y+",\n" +
						"clk => clk,\n"+
						"compute_clk => compute_clk,\n"+
						" reset => reset\n"+
						");";

		return ret;
	}
	
	

}
