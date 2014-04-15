package maps;

import coordinates.Space;

public class MultiplicationMatrix extends Matrix implements Parameter {

	public MultiplicationMatrix(String name, Parameter dt, Space space,
			Parameter... params) {
		super(name, dt, space, params);
	}

	public MultiplicationMatrix(String name, Parameter dt, Space space,
			double[] values, Parameter... params) {
		super(name, dt, space, values, params);
	}

	@Override
	public void compute()
	{
		Jama.Matrix a = toJamaMatrix(getParam(0));
		Jama.Matrix b = toJamaMatrix(getParam(1));
//		a.print(0, 0);
//		b.print(0, 0);
		//System.out.println("a : " + getParam(0).getName() + " " + a.getColumnDimension() + "," +a.getRowDimension());
		//System.out.println("b : " + getParam(1).getName() + " " + b.getColumnDimension() + "," +b.getRowDimension());
		Jama.Matrix res = null;
		try{
			res = a.times(b);
		}catch(IllegalArgumentException e){
			res = a.times(b.transpose());
		}
//		res.print(0, 0);
		this.values = res.getColumnPackedCopy();

	}

	public static Jama.Matrix toJamaMatrix(Parameter m){
		Jama.Matrix ret = null;
		double[] val = m.getValues();
		int nbRow;
		if  (m.getSpace().getDim() == 1){
			nbRow = 1;
		}else{
			nbRow = m.getSpace().getDiscreteSize()[Space.Y];
		}
		ret = new Jama.Matrix(val,nbRow );
		ret = ret.transpose();
		return ret;
	}

}
