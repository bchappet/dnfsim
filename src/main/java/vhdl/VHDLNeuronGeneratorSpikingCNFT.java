package main.java.vhdl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.java.maps.Parameter;

public class VHDLNeuronGeneratorSpikingCNFT extends VHDLNeuronGeneratorCNFT {

	public VHDLNeuronGeneratorSpikingCNFT(int x, int y, int res,
			Parameter wExc, Parameter wInh) {
		super(x, y, res, wExc, wInh);
	}
	
	public static String generatePotentialPorts(int res){
		String ret = "";

		for(int i = 0 ; i < res ; i++){
			for(int j = 0 ; j  < res ; j++){
				ret += "spike"+i+"_"+j+" : in std_logic;\n";
			}
		}
		return ret;
	}
	
	public String generateConnectPotentialMap(){
		String ret = "";
		for(int i = 0 ; i < res ; i++){
			for(int j = 0 ; j  < res ; j++){
				ret += "spike_map("+i+","+j+")"+" <= " + "spike"+i+"_"+j+";\n";
			}
		}
		return ret;
	}
	
	public void generateNeuron(String folder,String name) throws IOException{
		Reader r = new FileReader(folder+"/"+name+".main.java.vhdl");
		BufferedReader br = new BufferedReader(r);
		Writer w = new FileWriter(folder+"/"+name+x+"_"+y+".main.java.vhdl");
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

	
	

}
