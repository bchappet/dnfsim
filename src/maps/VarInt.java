package maps;


/**
 * A variable can only be modified with a set.
 * Update have no effect
 * Destined to be the leaf of the tree.
 * @author bchappet
 *
 */
public class VarInt extends  Var{
	

	public VarInt(String name,int value) {
		super(name,value);//We don't need refSpace
		this.val = value;
	}

	
	
	public double get() {
		return (int) val;
	}

	public void set(double value) {
		this.val = (int) value;
	}
	
	
	

	

	

	


	

}
