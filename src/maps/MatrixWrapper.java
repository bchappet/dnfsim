package maps;

import coordinates.NullCoordinateException;

/**
 * Transform a parameter into a matrix.
 * For instance if we need to perform optimized computation (convolution) with a Map,
 *  we should wrap it into a Matrix using a MatrixWrapper to speed up the acces
 *  TODO : would easily be delay compatible
 *  @deprecated : useless as its as simple to use the double[] getValues() method of
 *  a {@link AbstractUnitMap} to get ist values like it was a {@link Matrix}
 * @author bchappet
 *
 */
public class MatrixWrapper extends Matrix {

	/**The linked param**/
	public static final int LINKED = 0;



	public MatrixWrapper(Parameter param) throws NullCoordinateException {
		super(param.getName()+"_MatrixWrapper", param.getDt(), param.getSpace(), param);
		//Construct the memory of the parameter
		this.constructParametersMemory();
		
		if(this.params.get(LINKED).isStatic())
			this.toStatic();
		
			
	}

	/**
	 *  As memory of the linked parameter is constructed
	 *  compute the linked parameter and copy its value in the matrix
	 * @throws NullCoordinateException
	 */
	@Override
	public void compute() throws NullCoordinateException {
		Parameter linked = this.params.get(LINKED);
			//Copy the linked values in the values array
		for(int i = 0 ; i < values.length ; i++)
			this.values[i] = linked.get(i);
	}
	
	/**
	 * The time is reseted to 0
	 * and the memory is blanked
	 * @throws NullCoordinateException 
	 */
	public void reset() {
		if(!isStatic){
			this.time.set(0);
			for(Parameter m : params){
				m.reset();
			}
		}

	}
	



//	@Override
//	public void addParameters(Parameter ...maps )
//	{
//		super.addParameters(maps);
//		try {
//			this.constructParametersMemory();
//			if(this.params.get(LINKED).isStatic());
//			this.toStatic();
//		} catch (NullCoordinateException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
	


}
