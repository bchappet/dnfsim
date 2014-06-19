package coordinates;

import maps.Var;

public class DefaultRoundedSpace extends RoundedSpace {

	public DefaultRoundedSpace(Var resolution,int dim,boolean wrap) {
		super(getUniformDouble(dim, -0.5),getUniformDouble(dim, 1d),resolution,wrap);
	}
	
	public DefaultRoundedSpace clone() 
	{
		return (DefaultRoundedSpace) super.clone();
	}
	
	

}
