package main.java.vhdl;

public class TestSpikingCNFTMapGenerator extends VHDLGenerator2 {

	public TestSpikingCNFTMapGenerator(int res2) {
		super(res2);
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
				ret += indent + "signal potential"+i+"_"+j+" : std_logic_vector(INT+FRAC-1 downto 0):= (others => '0');\n";
				ret += indent + "signal main.java.input"+i+"_"+j+" : std_logic_vector(INT+FRAC downto 0):= (others => '0');\n";
			}
		}
		return ret;
	}

	public String connectSpikingCNFTMap(){
		String ret = "" + indent + " uut: SpikingCNFTMap PORT MAP (\n";
		for(int i = 0 ; i < res ; i ++){
			for(int j = 0 ; j < res ; j ++){
				ret += indent +"potential"+i+"_"+j+" => potential"+i+"_"+j+",\n";
				ret += indent +"main.java.input"+i+"_"+j+" => main.java.input"+i+"_"+j+",\n";
			}
		}
		ret += indent + "propagate => propagate,\n";
		ret += indent + "compute => compute,\n";
		ret += indent + "nextComp => nextComp,\n";
		ret += indent + "clk => clk,\n";
		ret += indent + "reset => reset\n";
		ret += indent + " );\n";

		return ret;
	}

}
