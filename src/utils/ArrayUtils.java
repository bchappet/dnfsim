package utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import unitModel.UnitModel;

import maps.Parameter;
import neuronBuffer.Buffer;


public class ArrayUtils {
	
	/**
	 * Concatene the 2 array 
	 * @param a
	 * @param b
	 * @return
	 */
	public static <T> T[] concat(T[] a,T... b)
	{
		List<T> ret = new ArrayList<T>(a.length+b.length);
		ret.addAll(Arrays.asList(a));
		ret.addAll(Arrays.asList(b));
		return ret.toArray(a);
		
	}
	
	/**
	 * Deep copy each element of the source calling clone method, return an array list
	 * @param source
	 * @return an array list
	 * @throws CloneNotSupportedException 
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Cloneable> List<T> deepCopy(List<T> source) throws CloneNotSupportedException
	{
		List<T> ret = new ArrayList<T>();
		for(int i = 0 ; i < source.size() ; i++)
		{
			T p = source.get(i);
			ret.add(i, (T) p.clone());
		}
		return ret;
	}
	


	/**
	 * 
	 * @param source
	 * @return
	 */
	public static int[] deepCopy(int[] source) {
		int[] res = new int[source.length];
		for(int i = 0 ; i < source.length ; i++ ){
			res[i] = source[i];
		}
		return res;
	}

	/**
	 * Return the values of the parameter at specific coordinates
	 * If the coordinates are incompatible with the accessed map,
	 * its value will be null
	 * @param params
	 * @param coord
	 * @return
	 */
	public static double[] getValues(List<Parameter> params, Double[] coord) {
		double[] values = new double[params.size()];
		for(int i = 0 ; i < values.length ; i++){
//			System.out.println("Param : " + params.get(i));
			//try{
			values[i] = params.get(i).get(coord);
			//}catch (NullCoordinateException e) {
		//		values[i] = null;//the currently accessed parameter has different dimension than the accesing map
		//	}
		}
		return values;
	}

	/**
	 * Deep copy of charge
	 * @param bufs
	 * @return
	 */
	public static Buffer[] deepCopy(Buffer[] bufs) {
		Buffer[] ret = new Buffer[bufs.length];
		
		for(int i = 0 ; i < ret.length ; i++)
		{
			ret[i] = bufs[i].clone();
		}
		
		return ret;
	}

	public static Parameter[] asArray(List<Parameter> params) {
		Parameter[] array = new Parameter[params.size()];
		for(int i = 0 ; i < params.size() ; i++)
		{
			array[i] = params.get(i);
		}
		return array;
	}
	
	/**
	 * Reverse a vector (no construction)
	 * @param array
	 */
	public static int[] reverse(int[] array){
		int size = array.length;
		for(int i = 0 ; i < size/2 ; i++){
			int tmp = array[i];
			array[i] = array[size - 1 - i];
			array[size -1- i] = tmp;
		}
		return array;
	}

	public static double sum(List<Integer> numbers) {
		double res = 0;
		for(Integer n : numbers){
			res += n;
		}
		return res;
	}

	public static double avg(List<Integer> randomNumbers) {
		double sum = sum(randomNumbers);
		return sum / randomNumbers.size();
	}

	public static int sum(int[] results) {
		int sum = 0;
		for(int i = 0 ; i < results.length ; i++)
			sum += results[i];
		
		return sum;
	}

	public static double avg(double[] vect) {
		double sum = sum(vect);
		return sum/vect.length;
	}

	public static double sum(double[] vect) {
		double sum = 0;
		for(int i = 0 ; i < vect.length ; i++)
			sum +=vect[i];
		
		return sum;
	}

	

	

}
