package main.resources.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import main.java.maps.Parameter;
import main.java.neuronBuffer.Buffer;


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
	 * Return the values of the parameter at specific main.java.coordinates
	 * If the main.java.coordinates are incompatible with the accessed map,
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
			values[i] = params.get(i).getIndex(coord);
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

	/**
	 * Deep comparison of 2 2D arrays
	 * @param expected
	 * @param result
	 * @return
	 */
	public static <T> boolean equals2D(T[][] expected, T[][] result) {
		boolean ret = true;
		for(int i = 0 ; i < expected.length ; i++){
			for(int j = 0 ;j < expected[0].length ; j++){
				
				ret &= expected[i][j].equals(result[i][j]);
			}
		}
		return ret;
	}

	/**
	 * Transform a double array in a Double array
	 * @param tab
	 * @return
	 */
	public static Double[] toPrimitive(double[] tab) {
		Double[] ret = new Double[tab.length];
		for(int i = 0 ; i < tab.length ; i++){
			ret[i] = tab[i];
		}
		return ret;
	}
	
	/**
	 * Transform a Double 2D array in a double 2D array
	 * @param data
	 * @return
	 */
	public static double[][] toPrimitiveArray(Number[][] data){
		double[][] ret = new double[data.length][data[0].length];
		for(int i  = 0 ; i < data.length ; i++){
			for(int j = 0 ; j < data[0].length ; j++){
				ret[i][j] = data[i][j].doubleValue();
			}
		}
		return ret;
	}

	/**
	 * Transform a 2D array with one dimension of length 1 in a flat array of double
	 * @param data
	 * @return
	 */
	public static double[] toPrimitiveArray1D(Number[][] data) {
		int max = Math.max(data.length, data[0].length);
		double[] ret = new double[max];
		int k = 0;
		for(int i  = 0 ; i < data.length ; i++){
			for(int j = 0 ; j < data[0].length ; j++){
				ret[k] = data[i][j].doubleValue();
				k++;
			}
		}
		return ret;
	}

	/**
	 * Transform a list of Number in an 2D array of double with the specified dimension
	 * if values are missing they will be 0
	 * @param values
	 * @param width
	 * @param height
	 * @return
	 */
	public static double[][] toPrimitiveArray(List<Number> values, int width, int height) {
		
		double[][] ret = new double[height][width];
		
		for(int i = 0 ; i < values.size() ; i++){
			//System.out.println("i : "+ i);
			//System.out.println("y : " + i/height + " x : " + i %  height);
			ret[i/width][i%width] = values.get(i).doubleValue();
		}
		
		
		return ret;
	}

	/**
	 * Trandform a list of number into a 1D array of double
	 * @param values
	 * @return
	 */
	public static double[] toPrimitiveArray1D(List<Number> values) {
		double[] ret = new double[values.size()];
		for(int i = 0 ; i < ret.length ; i++){
			ret[i] = values.get(i).doubleValue();
		}
		return ret;
	}

	public static boolean equals2D(double[][] expected, double[][] result) {
		boolean ret = true;
		for(int i = 0 ; i < expected.length ; i++){
			for(int j = 0 ; j < expected[0].length ; j++){
				ret &= (expected[i][j] == result[i][j]);
			}
		}
		return ret;
	}

	public static String toString(double[][] val) {
		String ret = "";
		for(int i = 0 ; i < val.length-1 ; i++){ //colulmn Width Y
			for(int j = 0 ; j < val[0].length-1 ; j++){ //row width X
				ret += val[i][j] + ",";
			}
			ret += val[i][val[0].length-1];
			ret += "\n";
		}
		
		for(int j = 0 ; j < val[0].length-1 ; j++){
			ret += val[val.length-1][j] + ",";
		}
		ret += val[val.length-1][val[0].length-1];
		
		return ret;
		
	}

	public static String toString2D(List values,
			int width,
			int height) {
		String ret = "";
		for(int i = 0 ; i < height-1 ; i++){ //colulmn Width Y
			for(int j = 0 ; j < width-1 ; j++){ //row width X
				ret += values.get(i * width + j) + ",";
			}
			ret += values.get(i *width + width-1);
			ret += "\n";
		}
		
		for(int j = 0 ; j < width-1 ; j++){
			ret += values.get((height-1)*width) + ",";
		}
		ret += values.get((height-1)*width+ width -1);
		
		return ret;
	}

	

	

}
