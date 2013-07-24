package precision;


import maps.Parameter;
import maps.Var;

/**
 * 
 * @author bchappet
 *
 */
public class PrecisionVar extends Var {

	
	public static int count = 0;

	protected int val;
	protected Parameter precision;
	protected int nb;

	
	public PrecisionVar(String name,int val,Parameter precision) {
		super(name,val);
		this.val = val;
		this.precision = precision;
		assertPrecision();
		nb = count;
		count ++;
		//System.out.println("Constructiong precision var " + Arrays.toString(Thread.currentThread().getStackTrace()));
	}
	
	public PrecisionVar(int val,Parameter precision) {
		this(null,val,precision);
	}


	private void assertPrecision() {
		int valPos = this.positive();
		int mask = (int) (Math.pow(2,precision.get()+1)-1);
		if(valPos != (valPos & mask)){
			//bit overflow
			//System.out.println("Reduction : " + valPos + " => " + mask);
			valPos = (valPos & mask);
			//valPos = mask;
		}

		if(this.val < 0)
			this.val = - valPos;
		else
			this.val = valPos;

	}


	public double get() {
		return val;
	}


	public void set(int val) {
		this.val = val;
		assertPrecision();
		if(this.name != null){
			signalParents();
		}

	}

	@Override
	public void set(double val) {
		this.val = (int)val;
		assertPrecision();
		if(this.name != null){
			signalParents();
		}

	}


	public PrecisionVar add(PrecisionVar b){
		int res = val + b.val;
		return new PrecisionVar(res,precision);

	}

	public PrecisionVar addThis(PrecisionVar b){
		this.val = val + b.val;
		assertPrecision();
		return this;
	}

	public PrecisionVar sub(PrecisionVar b){
		return new PrecisionVar((val - b.val),precision);
	}

	public PrecisionVar mult(PrecisionVar b){
		PrecisionVar res = new PrecisionVar(0,precision);
		int bPos = b.positive();
		for(int i = 0 ; i < bPos ; i++){
			res.addThis(this);
		}
		if(!b.isPositive())
			res.val = -res.val;
		return res;
	}

	@Deprecated
	public PrecisionVar div(PrecisionVar b){
		//TODO wrong
		PrecisionVar res = new PrecisionVar(this.val / b.val,precision);
		return res;
	}

	public boolean isPositive() {
		return val >= 0;
	}


	private int positive() {
		if(val <  0)
			return -val;
		else
			return val;
	}


	public PrecisionVar shiftLeftThis(int nb){
		val = ((val << nb));
		assertPrecision();
		return this;
	}

	public PrecisionVar shiftRightThis(int nb){
		val = val >> nb;
		assertPrecision();
		return this;
	}
	
	public PrecisionVar shiftRight(int nb) {
		int val2 = val;
		val2 = val2 >> nb;
		return new PrecisionVar(val2,precision);
	}

	public String toString(){

		String bin = Integer.toBinaryString(val);
		if(val < 0){
			String precisionPart = bin.substring(bin.length()-(int)precision.get()+1, bin.length());
			return val + " : " + "1" + precisionPart;
		}else{
			String filling = "";
			for(int i = 0 ; i < precision.get() - 1 - bin.length() ; i++){
				filling += "0";
			}
			return val + " : " +"0" + filling + bin;
		}




	}

	public PrecisionVar clone(){
		PrecisionVar clone = (PrecisionVar)super.clone();
		clone.val = this.val;
		clone.precision = this.precision;
		clone.nb = count;
		count++;


		return clone;
	}

	public static void main(String[] args){

		Var precision = new Var(10);
		PrecisionVar a = new PrecisionVar(2,precision);
		PrecisionVar b = new PrecisionVar(-256,precision);

		PrecisionVar c = new PrecisionVar(-9,precision);

		System.out.println(c);
		System.out.println(c.shiftRightThis(3));

		a.shiftLeftThis(2);
		PrecisionVar d = a.mult(a);
		System.out.println(d);

		PrecisionVar aa = new PrecisionVar(3,precision);
		PrecisionVar bb = new PrecisionVar(-342,precision);
		PrecisionVar cc = aa.mult(bb);
		System.out.println(cc);

		int zz = -9 >> 3;
			System.out.println(zz);
			PrecisionVar ex = new PrecisionVar(4096,precision);
			System.out.println(ex);
	}


	public int getPrecision() {
		return (int) precision.get();
	}
	
	public Parameter getPrecisionVar() {
		return precision;
	}



	/**
	 * Copie value of pv in this {@link PrecisionVar} if the precision is compatible
	 * ie if this.precision >= pv.precision;
	 * @param nbSpikes
	 */
	public void set(PrecisionVar pv) {
		if(this.precision.get() >= pv.precision.get()){
			this.val = pv.val;
		}else{
			//TODO
		}

	}


	/**
	 * 
	 * @return true if this var is not 0
	 */
	public boolean notNull() {
		return this.val != 0;
	}

	/**
	 * decrement this
	 */
	public void decr() {
		this.val --; 
		assertPrecision();

	}


	/**
	 * this Greater or equals pv
	 * @param pv
	 * @return
	 */
	public boolean ge(PrecisionVar pv) {
		if(this.precision.get() >= pv.precision.get()){
			return this.val >= pv.val;
		}
		else{
			//TODO
			//System.err.println("bad precision" + Arrays.toString(Thread.currentThread().getStackTrace()));
			return false;
		}
	}


	public static PrecisionVar getRandomPrecisionVar(Parameter precision2) {
		return new PrecisionVar((int)(Math.random()*Math.pow(2,precision2.get())),precision2);
	}


	/**
	 * 
	 * @param b
	 * @return a new {@link PrecisionVar} with this * b
	 */
	public PrecisionVar mult(int b) {
		PrecisionVar res = new PrecisionVar(0,precision);
		int bPos = Math.abs(b);
		for(int i = 0 ; i < bPos ; i++){
			res.addThis(this);
		}
		if(b < 0)
			res.val = -res.val;
		return res;
	}

	public int hashCode(){
		return nb;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		PrecisionVar other = (PrecisionVar) obj;
		if (precision == null) {
			if (other.precision != null)
				return false;
		} else if (!precision.equals(other.precision))
			return false;
		if (val != other.val)
			return false;
		return true;
	}

	public int getNb() {
		return nb;
	}
	
	





}
