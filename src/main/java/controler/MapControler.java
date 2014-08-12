package main.java.controler;

import java.util.List;

import main.java.maps.Array2D;
import main.java.maps.Array2DDouble;
import main.java.maps.Map;
import main.java.maps.MatrixDouble2D;
import main.java.maps.Parameter;
import main.java.maps.SingleValueParam;
import main.java.space.Coord;
import main.java.space.Space;
import main.resources.utils.ArrayUtils;

public class MapControler extends ComputableControler {

	public MapControler(Map param) {
		super(param);
	}
	
	public String displayText() {
		if(getParam() instanceof MatrixDouble2D){
			return getParam().toString();
		}else{
			return getParam().toString();
		}
	}
	/**
	 * Return the values of the map in double[][] format
	 * @return
	 */
	public double[][] getArray2D(){
		Parameter param = this.getParam();
		//System.out.println("Map : " + this.getName());
		double[][] ret;
		if(param instanceof Array2DDouble){
			ret = ((Array2DDouble)param).get2DArrayDouble();
		}
		else if(param instanceof Array2D){
			ret =  ArrayUtils.toPrimitiveArray(((Array2D<? extends Number>)param).get2DArray());
		}else{
			List<? extends Number> list = param.getValues(); 
			Coord<SingleValueParam<Integer>> dim =  ((Map)param).getSpace().getDimensions();
			ret = ArrayUtils.toPrimitiveDoubleArray2D(list, dim.getIndex(0).get(), dim.getIndex(1).get());
		}
		return ret;
	}
	
	public double[] getArray1D(){
		Parameter param = this.getParam();
		List<? extends Number> list = param.getValues(); 
		return ArrayUtils.listToPrimitiveArray1D(list);
	
		
	}
	

	public Space getSpace() {
		return ((Map)getParam()).getSpace();
	}

	public List getValues() {
		return getParam().getValues();
	}

	public Number get(int i) {
		return (Number) getParam().getIndex(i);
	}

}
