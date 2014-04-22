package maps;

public class VarString extends Var {
	
	protected String initStr;
	protected String str;

	

	public VarString(String name, String str) {
		super(name, str.hashCode());
		initStr = str;
		this.str = str;
	}

	
	
	
	
	public String getString()
	{
		return str;
	}
	
//	public void reset()
//	{
//		super.reset();
//		this.str = initStr;
//	}
	
	public void setString(String newStr)
	{
		this.str = newStr;
		if(this.name != null){
			signalParents();
		}
	}

}
