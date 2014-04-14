package vhdl;

public class IOWrapperCNFTGenerator extends SpikingCNFTMapGenerator {

	public IOWrapperCNFTGenerator(int res2) {
		super(res2);
	}
	
	public String connectPorts(){
		String ret = "";
		int nIn = INT + FRAC + 1;
		int nPot = INT + FRAC;
		int taps = res * res;
		int nb = 0;
		for(int i = 0 ; i < res ; i++){
			for(int j = 0 ; j < res ; j++){
				ret += indent + "potential" + i + "_" + j + " => ParallelPotentialCNFT (" + ((taps * nPot) - (nb*nPot) - 1) +" downto "+ ((taps * nPot) - (nb*nPot) - nPot) + "),\n";
				ret += indent + "input" + i + "_" + j + " => ParallelPixelToCNFT (" + ((taps * nIn) - (nb*nIn) - 1) +" downto "+ ((taps * nIn) - (nb*nIn) - nIn) + "),\n";
				nb ++;
			}
		}
		return ret;
	}

}
