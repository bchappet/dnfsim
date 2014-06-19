package vhdl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import maps.Parameter;
import coordinates.Space;

public class VHDLNeuronGeneratorCNFT  {


	protected int res ;
	protected int decimal = 3;
	protected int fractional = 7;
	protected double dt_tau = 0.156;
	protected double alpha=10;
	protected int x,y;
	protected Parameter wExc,wInh;


	public VHDLNeuronGeneratorCNFT(int x,int y,int res,Parameter wExc,Parameter wInh) {
		this.res = res;
		this.x= x;
		this.y = y;
		this.wExc = wExc;
		this.wInh = wInh;
	}

	public void generateNeuron(String folder,String name) throws IOException{
		Reader r = new FileReader(folder+"/"+name+".vhdl");
		BufferedReader br = new BufferedReader(r);
		Writer w = new FileWriter(folder+"/"+name+x+"_"+y+".vhdl");
		BufferedWriter bw = new BufferedWriter(w);

		try{
			String line;
			while((line = br.readLine()) != null){
				String addition = "";
				if(line.contains("@name")){
					line= line.replace("@name", ""+name+x+"_"+y);
				}else{
					String indent = null;
					Pattern pattern = Pattern.compile("(\\s*)@(\\d+)");
					Matcher matcher  = pattern.matcher(line);
					if(matcher.find()){
						indent = matcher.group(1);
						int nb = Integer.parseInt(matcher.group(2));
						switch(nb){
						case 0 : addition = "";break;
						case 1 : addition = generatePotentialPorts(res);break;
						case 2 : addition = generateWeights(x, y, wExc, "exc_weights") +
								generateWeights(x, y, wInh,"inh_weights");break;
						case 3 : addition = generateConnectPotentialMap();break;
						default : System.err.println("Warning bad number on line " + line);
						}

					}
				}
				if(!addition.isEmpty())
					bw.write( addition);
				else 
					bw.write(line);

				bw.newLine();
			}
		}finally{
			br.close();
			bw.close();
		}

	}

//	public String generateGenerics(){
//		int decimal = 3;
//		int fractional = 7;
//		double dt_tau = 0.156;
//		double alpha=10;
//		return ""
//		+"RES : in natural := "+res+"; --resolution of the map\n"
//		+"INT : in natural := "+decimal+"; --Precision of computation\n"
//		+"FRAC : in natural := "+fractional+"; --fractional part of fixed point atrithmetic\n"
//		+"DT_TAU : in real := "+dt_tau+"; --dt/tau \n"
//		+"ALPHA : in real := "+alpha+"";
//	}

	public static String generatePotentialPorts(int res){
		String ret = "";

		for(int i = 0 ; i < res ; i++){
			for(int j = 0 ; j  < res ; j++){
				ret += "potential"+i+"_"+j+" : in std_logic_vector(INT+FRAC-1 downto 0);\n";
			}
		}
		return ret;
	}

	public String generateConnectPotentialMap(){
		String ret = "";
		for(int i = 0 ; i < res ; i++){
			for(int j = 0 ; j  < res ; j++){
				ret += "potential_map("+i+","+j+")"+" <= " + "to_ufixed(potential"+i+"_"+j+",INT-1,-FRAC);\n";
			}
		}
		return ret;
	}

	public String generateWeights(int x,int y,Parameter weights,String varName){

		StringBuilder ret = new StringBuilder();
		ret.append("--Generated " + weights.getName() + " weights memory");
		ret.append(System.getProperty("line.separator"));
		ret.append("constant " + varName + " : cnft_map := ( ");
		ret.append(System.getProperty("line.separator"));

		for(int i = 0 ; i < res ;i ++){
			ret.append(i + " => (");
			for(int j = 0 ; j < res ; j++){
				ret.append("to_ufixed("+weight(x,y,j,i,weights)+",INT-1,-FRAC)," );
			}
			ret.deleteCharAt(ret.length()-1);
			ret.append("),");
			ret.append(System.getProperty("line.separator"));
		}
		ret.deleteCharAt(ret.length()-2);
		ret.append(");");

		return ret.toString();

	}

	/**
	 * 
	 * Return the connection weight between the neuron ni,nj and i,j  (torique)
	 * 0,0 = bottom left
	 * @param ni
	 * @param nj
	 * @param i
	 * @param j
	 * @param weights 
	 * @return
	 */
	public double weight(int ni, int nj, int  i , int j,Parameter weights){

		int cx =  res/2;
		int cy = cx;

		int xdiff = i - ni;
		int ydiff = j - nj;

		int centeredXDiff = cx + xdiff;
		int centeredYDiff = cy + ydiff;

		int torX = centeredXDiff % res;
		int torY = centeredYDiff % res;
		if(torX < 0) torX += res;
		if(torY < 0) torY += res;

		return weights.getFast(torX,torY);
	}

	



}
