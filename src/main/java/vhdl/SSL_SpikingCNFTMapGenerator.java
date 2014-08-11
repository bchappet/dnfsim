package main.java.vhdl;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import main.java.space.Coord2D;
import main.java.space.Space2D;
import main.resources.utils.BitOverflowException;
import main.resources.utils.Hardware;

public class SSL_SpikingCNFTMapGenerator extends VHDLGenerator2{
	
	//Direction definition for Spikes Vectors
		public final static int W = 0;
		public final static int E = 1;
		public final static int S = 2;
		public final static int N = 3;
	
	protected int INT = 3;
	protected int FRAC = 7;
	
	protected Space2D space;

	public SSL_SpikingCNFTMapGenerator(int res2) {
		super(res2);
		space = new Space2D(res, res);
	}
	
	public static void main(String[] args) throws SecurityException, IllegalArgumentException, IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException{
		
		
		int res = 31;
		SSL_SpikingCNFTMapGenerator gen1 = new SSL_SpikingCNFTMapGenerator(res);
		gen1.generateFile("vhdlSource", "SSLMap");
		
		TestSpikingCNFTMapGenerator gen2 = new TestSpikingCNFTMapGenerator(res);
		gen2.generateFile("vhdlSource", "test_SSLMap");
		
		CNFTConfigurationGenerator conf = new CNFTConfigurationGenerator(res);
		conf.generateFile("vhdlSource", "CNFTConfiguration");
		
		IOWrapperCNFTGenerator io = new IOWrapperCNFTGenerator(res);
		io.generateFile("vhdlSource", "IOWrapperCNFTSSL");
	}
	
	public String generatePorts(){
		String ret = "";
		for(int i = 0 ; i < res ; i ++){
			
			for(int j = 0 ; j < res ; j ++){
				ret += indent + "potential"+i+"_"+j+": out std_logic_vector(INT+FRAC-1 downto 0);\n";
				ret += indent + "input"+i+"_"+j+": in std_logic_vector(INT+FRAC downto 0);\n";
			}
		}
		return ret;
	}
	
	public String generateExcTable() throws BitOverflowException{
		double factor = 0.2;
		return getLateralWeightsTable(factor);
	}
	
	public String generateInhTable() throws BitOverflowException{
		double factor = 0.1;
		return getLateralWeightsTable(factor);
	}
	/**
	 * TODO real weight
	 * @return
	 * @throws BitOverflowException 
	 */
	public String getLateralWeightsTable(double factor) throws BitOverflowException{
		String ret = "";
		int distMax = (res-1) *2;
		for(int i = 0 ; i < distMax ; i ++){
			ret += indent + "" + i + " => " + Hardware.toFPVectorString((i+1)*factor,INT,FRAC,false) + "," + "\n";
		}
		return ret.substring(0, ret.length()-2) + "\n";
	}
	
	public String generateSignals(){
		String ret = "";
		for(int i = 0 ; i < res ; i ++){
			for(int j = 0 ; j < res ; j ++){
				ret += indent + "signal outSpikes"+i+"_"+j+" : std_logic_vector(TRANS_OUT_WIDTH*4-1 downto 0);\n";
			}
		}
		return ret;
	}
	
	public String connectSSLNeuron() throws BitOverflowException{
		String ret = "";
		
		for(int i = 0 ; i < res ; i ++){
			for(int j = 0 ; j < res ; j ++){
				
				ret += SSLCell(i,j);
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
				ret +=  SSLCell(i,j);
			}
		}
		
		return ret;
	}
	
	protected String  SSLCell(int i, int j){
	return	indent + "Neuron"+i+"_"+j+": SSLCell\n" +
			indent + "GENERIC MAP ( \n" +
			indent +"	EXC_TABLE => exc_table,\n" +
			indent +"	INH_TABLE => inh_table\n" +
			indent +")PORT MAP ( \n" +
			indent +"	CLK => CLK,\n"+
			indent +"	nextComp => nextComp,\n"+
			indent +"	propagate => propagate,\n"+
			indent +"	compute => compute,\n"+
			indent +"	Reset => Reset,\n"+
			indent + "	input => input"+i+"_"+j+", \n" +
			connectSpikesBus(i,j)+"\n"+
			indent + "	potential => potential"+i+"_"+j+", \n" +
			indent + "	outSpikes => outSpikes"+i+"_"+j+" \n" +

			indent + ");\n";
	}
	
	private String connectSpikesBus(int x, int y) {
		String ret = "" +
		
		
		 "inSpikes((N+1)*TRANS_OUT_WIDTH-1 downto N*TRANS_OUT_WIDTH) => outSpikes" +getNeighboor(x, y, N) +"((S+1)*TRANS_OUT_WIDTH-1 downto S*TRANS_OUT_WIDTH),\n" +   
		 "inSpikes((S+1)*TRANS_OUT_WIDTH-1 downto S*TRANS_OUT_WIDTH) => outSpikes" +getNeighboor(x, y, S) +"((N+1)*TRANS_OUT_WIDTH-1 downto N*TRANS_OUT_WIDTH),\n" + 
		 "inSpikes((E+1)*TRANS_OUT_WIDTH-1 downto E*TRANS_OUT_WIDTH) => outSpikes" +getNeighboor(x, y, E) +"((W+1)*TRANS_OUT_WIDTH-1 downto W*TRANS_OUT_WIDTH),\n" + 
		 "inSpikes((W+1)*TRANS_OUT_WIDTH-1 downto W*TRANS_OUT_WIDTH) => outSpikes" +getNeighboor(x, y, W) +"((E+1)*TRANS_OUT_WIDTH-1 downto E*TRANS_OUT_WIDTH),\n" ;

		
		return ret;
	}
	
	public String getNeighboor(int x,int y,int direction){
		
		boolean inside ;
		Coord2D<Integer> neigh = null;
		switch(direction){
			case N:neigh  = new Coord2D<Integer>( x,(y-1));break;	
			case S:neigh  = new Coord2D<Integer>(x, (y+1));break;
			case E:neigh  = new Coord2D<Integer>( (x+1), y);break;
			case W:neigh  = new Coord2D<Integer>( (x-1), y);break;
			default : //error;
		}
		
		inside = space.checkInside(neigh);
		if(!inside ){
			return "NULL";
		}
		else{
			
			return getNeuronId(neigh.x,neigh.y);
		}

	}

	
	
	public String getNeuronId(int x,int y){
		return ""+x+"_"+y;
	}

	
	

	

}
