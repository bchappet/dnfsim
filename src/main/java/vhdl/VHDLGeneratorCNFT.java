package main.java.vhdl;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

import main.java.maps.Parameter;

public class VHDLGeneratorCNFT extends VHDLGenerator {

	int res;
	protected Parameter wExc,wInh;

	public VHDLGeneratorCNFT(int res,Parameter wExc,Parameter wInh){
		this.res = res;
		this.wExc = wExc;
		this.wInh = wInh;
	}
	
	public void generateNeuronsFiles(String folder,String name) throws IOException{
		for(int i = 0 ; i < res ; i++){
			for(int j=0 ; j < res ; j++ ){
				VHDLNeuronGeneratorCNFT n = new VHDLNeuronGeneratorCNFT(i, j, res, wExc, wInh);
				n.generateNeuron(folder,name);
			}
		}
	}


	public void generateFile(String folder,String filename) throws IOException{
		Writer w = new FileWriter(folder+"/"+filename+".main.java.vhdl");
		PrintWriter bw = new PrintWriter(w);


		try{
			bw.println("library IEEE;");
			bw.println("use IEEE.STD_LOGIC_1164.ALL;");
			bw.println("use WORK.CNFTConfiguration.all;");
			bw.println("entity cnft_map is");
			bw.println("PORT(");
			bw.println(getMapPorts());
			bw.println("clk,compute,nextComp,reset : in std_logic");
			bw.println(");");
			bw.println("end cnft_map;");
			bw.println("architecture Behavioral of cnft_map is");

			for(int i = 0 ; i < res ; i++){
				for(int j=0 ; j < res ; j++ ){
					bw.println(getNeuronComponent(i, j));
				}
			}

			bw.println("type umemory_map is array(0 to RES-1,0 to RES-1) of std_logic_vector(INT+FRAC-1 downto 0);");
			bw.println(" type smemory_map is array(0 to RES-1,0 to RES-1) of std_logic_vector(INT+FRAC downto 0);");

			bw.println( "type umemory_array is array(0 to INT+FRAC-1) of std_logic_vector(RES*RES-1 downto 0);");
			bw.println(" type smemory_array is array(0 to INT+FRAC) of std_logic_vector(RES*RES-1 downto 0);");

			bw.println(	"signal potential_map : umemory_array;");
			bw.println(	"signal input_map : smemory_array;");

			bw.println(getSignals());

			bw.println("begin");

			bw.println(getOutputConnection());
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
				ret += "potential"+i+"_"+j+" <= potentialS"+i+"_"+j+";\n";
			}
		}
		return ret;
	}


	public String getMapPorts(){
		String ret = "";

		for(int i = 0 ; i < res ; i++){
			for(int j = 0 ; j  < res ; j++){
				ret += "potential"+i+"_"+j+" : out std_logic_vector(INT+FRAC-1 downto 0);\n";
				ret += "main.java.input"+i+"_"+j+" : in std_logic_vector(INT+FRAC downto 0);\n";
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
				ret += "signal potentialS"+i+"_"+j+" : std_logic_vector(INT+FRAC-1 downto 0);\n";
			}
		}
		return ret;
	}

	public String getNeuronComponent(int i,int j){

		String ret = "";

		ret += "COMPONENT neuron_mixt"+i+"_"+j+"\n" +
				"PORT(\n" +
				VHDLNeuronGeneratorCNFT.generatePotentialPorts(res) +
				"main.java.input : IN  std_logic_vector(INT+FRAC downto 0);\n" +
				"potentialOut : OUT  std_logic_vector(INT+FRAC-1 downto 0);\n" +
				"clk : IN  std_logic;\n" +
				"compute : IN std_logic;\n" +
				"nextComp : IN std_logic;\n" +
				"reset : IN  std_logic\n" +
				");\n" +
				"END COMPONENT;";

		return ret;
	}

	public String getNeuronInstantation(int x,int y){
		String ret = "n"+x+"_"+y+": neuron_mixt"+x+"_"+y+" PORT MAP (\n";

		for(int i = 0 ; i < res ; i++){
			for(int j  = 0 ; j< res ; j++){
				if(i == x && j == y)
					ret += "potential"+i+"_"+j+" => (others => '0'),\n";
				else
					ret += "potential"+i+"_"+j+" => " + "potentialS"+i+"_"+j+",\n";
			}
		}

		if(x==0 && y==0)
			ret += "main.java.input =>input0_0,\n";
		else
				//ret +=  "main.java.input =>(others => '0'),\n";  //main.java.input"+x+"_"+y+",\n";
			ret +=  "main.java.input =>main.java.input"+x+"_"+y+",\n";
		
			ret +=		"potentialOut => potentialS"+x+"_"+y+",\n"+
						"clk => clk,\n"+
						"compute => compute,\n"+
						"nextComp => nextComp,\n"+
						" reset => reset\n"+
						");";

		return ret;
	}

}
