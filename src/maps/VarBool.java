package maps;


/**
 * 
 * 0 = false
 * others = 1 = true
 * @author bchappet
 *
 */
public class VarBool extends Var {

	

	public VarBool(String name, double value)  {
		super(name, formatValue(value));
	}

	
	
	/**
	 * Transform a value in 1 if it is different of 0
	 * @param str
	 * @return
	 */
	private static int formatValue(double value) {
		int ret;
		if(value == 0)
			ret = 0;
		else
			ret = 1;
		
		return ret;
	}
	
	public void set(double value)
	{
		this.val = formatValue(value);
	}
	
	

}
