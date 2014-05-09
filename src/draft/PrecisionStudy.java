package draft;
import java.util.Arrays;


public class PrecisionStudy {

	public static class Precision{
		double precisionLoss;
		int fractional;
		int decimal;

		public Precision(double precisionLoss,int fractional,int decimal){
			this.precisionLoss = precisionLoss;
			this.fractional = fractional;
			this.decimal = decimal;
		}

		@Override
		public String toString() {
			return "Precision [precisionLoss=" + precisionLoss
					+ ",  decimal=" + decimal + ", fractional=" + fractional
					+ "]";
		}

	}

	public static class Constrains{
		boolean min;
		double minDiffPrecision;
		int maxSize;

		public Constrains(boolean min,int maxSize,double minDiffPrecision){
			this.min = min;
			this.maxSize = maxSize;
			this.minDiffPrecision = minDiffPrecision;
		}

		@Override
		public String toString() {
			return "Constrains [min=" + min + ", maxSize=" + maxSize + "]";
		}


	}

	public PrecisionStudy() {
		// TODO Auto-generated constructor stub
	}


	public static Precision precisionLoss(double min,double max,Constrains cons ){
		if(cons.min){
			int p = 0;
			double precisionLoss = Double.MAX_VALUE;
			int fractional = 0;
			int decimal = 0;
			while(precisionLoss > cons.minDiffPrecision ){
				int fact = (int) Math.pow(2, p);
				double multMin = min * fact;
				double multMax = max * fact;

				int intMin = round(multMin);
				int intMax = round(multMax);

				//precisionLoss = ((multMax - ((max/ min) * intMin))/fact)/max;
				precisionLoss = 1-intMin/multMin;
				fractional = p;
				decimal = Math.max(0, nbBitFor(intMax)-p);

				p++;
			}
			//precisionLoss <= multMax/4
			return new Precision(precisionLoss, fractional, decimal);
		}
		else{
			return null;
		}

	}
	public static int round(double multMin) {
		return (int) multMin;
	}

	public static double getIntensity(double i,double res,int n,double alpha,double tau,double fact){
		return i*40*40*fact/(res*res*n*alpha*tau);
	}
	
	public static double getIntensityInv(double i,double res,int n,double alpha,double tau,double fact){
		return i*res*res*n*alpha*tau/(40*40*fact);
	}
	
	public static double getThreshold(double th,double fact){
		return th * fact;
	}


	/**
	 * Return the number of bit necessary to encode the given unsigned integer
	 * @param val
	 * @return
	 */
	private static int nbBitFor(int val) {

		int i = 1;
		while(val >= Math.pow(2, i))
		{
			i++;
		}
		//val < 2^i
		return i-1;
	}
	
	private static int findGoodMultOrDiv(double dt,double tau){
		double[] res = new double[4];
		res[0]= 1 - (dt/tau);
		res[1] = dt/tau;
		res[2] = 1/res[0];
		res[3] = 1/res[1];
		
		System.out.println(Arrays.toString(res));
		double[] tab = new double[4];
		for(int i = 0 ; i < 4; i ++){
			tab[i] = findClosestBit(res[i],i,dt,tau);
		}
		System.out.println(Arrays.toString(tab));
		return 0;
	}
	
	/**
	 * Mode = 0 is 1-dt/tau
	 * @param val
	 * @param mode
	 * @return
	 */
	protected static int findClosestBit(double val,int mode,double dt,double tau){
		double diffMin = Double.MAX_VALUE;
		
		int p = 0;
		if(val >=1){
		while(diffMin > Math.abs(val- Math.pow(2, p))){
			diffMin = Math.abs(val- Math.pow(2, p));
			p++;
		}
		
		}else{
			while(diffMin > Math.abs(val- Math.pow(2, -p))){
				diffMin = Math.abs(val- Math.pow(2, -p));
				p++;
			}
		}
		p--;
//		res[0]= 1 - (dt/tau);
//		res[1] = dt/tau;
//		res[2] = 1/res[0];
//		res[3] = 1/res[1];
		if(val >=1){
			p = p;
		}else{
			p = -p;
		}
		double dt_tau_fp;
		if(mode == 0){
			dt_tau_fp = 1 - Math.pow(2, p);
		}else if(mode == 1){
			dt_tau_fp =  Math.pow(2, p);
		}else if(mode == 2){
			dt_tau_fp = 1 - 1/Math.pow(2,p);
		}else if(mode == 3){
			dt_tau_fp = 1/Math.pow(2, p);
		}
		else{
			dt_tau_fp = 0;
		}
		
	
		System.out.println(val+ "," + Math.pow(2, p)+"," + dt_tau_fp + "," + dt/tau + ","+dt/dt_tau_fp);
		return p;
			
	}
	
	public static double getClosestFPValue(int fractional,double val)
	{
		double f = Math.pow(2, fractional);
		return round(val * f)/f; 
	}
	
	protected static double normDiff(double ori,double other){
		return Math.abs(ori - other)/ori;
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		double ia = 2.33;
		double ib = 1.59;
		double res = 49;
		double alpha = 10;
		int n = 20;
		double tau = 0.64;
		double th = 0.75;
		double dt = 0.1;
		int compTime = 460;


		double precisionTarget = 0.0001;
		Constrains cons = new Constrains(true, 100,precisionTarget);
		double meanPrecisionLoss = Double.MAX_VALUE; 


		double fact = 1;
		System.out.println("Fact : " + fact);

		double IA =  getIntensity(ia,res,n,alpha,tau,fact);
		double IB =  getIntensity(ib,res,n,alpha,tau,fact);


		Precision pIA = precisionLoss(IA, IA*compTime, cons);
		Precision pIB = precisionLoss(IB, IB*compTime, cons);

		System.out.println("ia : " + IA*compTime + "," + IA + " : " + pIA);
		System.out.println("ib : " + IB*compTime + "," + IB + " : " + pIB);

		double TH = th*fact;
		Precision pPot = precisionLoss(IB, TH, cons);
		System.out.println("pot : "+th + "," + IB + " : " + pPot);

		meanPrecisionLoss = (pIA.precisionLoss + pIB.precisionLoss + pPot.precisionLoss)/3d;
		System.out.println(meanPrecisionLoss);
		
		findGoodMultOrDiv(dt, tau);
		
		double iafp = getIntensityInv(getClosestFPValue( pIA.fractional,IA), res, n, alpha, tau, fact);
		double ibfp = getIntensityInv(getClosestFPValue( pIB.fractional,IB), res, n, alpha, tau, fact);
		double thfp = getClosestFPValue( pPot.fractional, TH)/fact;
		
		System.out.println("IA FPVal : " + iafp);
		System.out.println("IB FPVal : " + ibfp);
		System.out.println("Th FPVal : " + thfp);
		System.out.println("IA FPVal : " + normDiff(ia,iafp));
		System.out.println("IB FPVal : " + normDiff(ib,ibfp));
		System.out.println("Th FPVal : " + normDiff(th,thfp ));


	}
	
	




}
