package draft;

import static draft.PrecisionStudy.getClosestFPValue;
import static draft.PrecisionStudy.getIntensity;

import java.util.LinkedList;
import java.util.List;
public class PrecisionStudy2 {
	
	
	
	public static double getProba(double p, double res){
		return Math.pow(p,1/res);
	}
	
	public static double getProbaInv(double p, double res){
		return Math.pow(p,res);
	}
	
	
	
	public static void main(String[] args){
		double ia = 2.33;
		double ib = 1.59;
		
		double wa=0.0043;
		double wb=0.17;
		
		double res = 49;
		double alpha = 10;
		int n = 20;
		double tau = 0.64;
		double th = 0.75;
		double dt = 0.1;
		int compTime = 460;
		double fact = 1;
		
		
		List<Double> lia = new LinkedList<Double>();
		List<Double> lib = new LinkedList<Double>();
		
		List<Double> lth = new LinkedList<Double>();
		
		List<Double> lwa = new LinkedList<Double>();
		List<Double> lwb= new LinkedList<Double>();
		
		List<Double > ldt_tau = new LinkedList<Double>();
		
		
		int resMax = 20;
		
		double IA =  getIntensity(ia,res,n,alpha,tau,fact);
		double IB =  getIntensity(ib,res,n,alpha,tau,fact);
		double TH = PrecisionStudy.getThreshold(th, fact);
		double WA = getProba(wa, res);
		double WB = getProba(wb, res);
		double DT_TAU = tau/dt;
		
		ldt_tau.add(DT_TAU);
		for(int i = 1 ; i <= resMax ; i++ ){
			
			
			
			
			System.out.println(i + "&" + IA + "&"+IB+"&"+TH + "&"+WA+"&"+WB+"\\\\ \n \\hline");
			
			double _ia = getClosestFPValue(i,IA);
			double _ib = getClosestFPValue(i,IB);
			double _th = getClosestFPValue(i,TH);
			double _wa = getClosestFPValue(i, WA);
			double _wb = getClosestFPValue(i, WB);
			double _dt_tau = getClosestFPValue(i, DT_TAU);
			System.out.println(i + "&" + _ia + "&"+_ib+"&"+_th + "&"+_wa+"&"+_wb+"\\\\ \n \\hline");
			
			lia.add(_ia);
			lib.add(_ib);
			lth.add(_th);
			lwa.add(_wa);
			lwb.add(_wb);
			ldt_tau.add(_dt_tau);
		}
		
		System.out.println("my @ia = " + lia +";");
		System.out.println("my @ib = " + lib+";");
		System.out.println("my @th = " + lth+";");
		System.out.println("my @wa = " + lwa+";");
		System.out.println("my @wb = " + lwb+";");
		System.out.println("my @tau = " + ldt_tau+";");
	}

}
