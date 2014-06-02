package test.java.maps;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Arrays;

import main.java.maps.ConvolutionMatrix2D;
import main.java.maps.MatrixDouble2D;
import main.java.maps.Var;
import main.java.space.Space2D;
import main.resources.utils.ArrayUtils;

import org.junit.Before;
import org.junit.Test;

public class ConvolutionMatrix2DTest {
	
	private Space2D space;


	@Before
	public void setUp() throws Exception {
	}
	
	@Test
	public void testWrap() {
		double[][] values = 
			{
				 {1,2,3},
				 {4,5,6},
				 {7,8,9}
			};
		
		double[][] kernel= 
			{
				 {7,6,5},
				 {3,2,1},
				 {9,4,6},
			};
		
		Double[][] test =  
			{
				{254.0,261.0,247.0},
				{212.0,219.0,205.0},
				{179.0,186.0,172.0}
			};
		
		space= new Space2DWrap(3,3);
		Var<BigDecimal> dt = new Var<BigDecimal>("dt",new BigDecimal("0.1"));
		
		MatrixDouble2D val = new MatrixDouble2D("val", dt, values);
		
		MatrixDouble2D ker = new MatrixDouble2D("kernel", dt,  kernel);
		
		MatrixDouble2D conv = new ConvolutionMatrix2D("conv",dt,space,ker,val);
		conv.compute();
		
		Double[][] res =conv.get2DArray();

		System.out.println("Wrapped");
		System.out.println(conv.toString());
		assertTrue(ArrayUtils.equals2D(test,res));
	}
	
	
	@Test
	public void testNoWrap() {
		double[][] values = 
			{
				 {1,2,3},
				 {4,5,6},
				 {7,8,9}
			};
		
		double[][] kernel= 
			{
				 {7,6,5},
				 {3,2,1},
				 {9,4,6},
			};
		
		Double[][] test =  //TODO
			{
				{254.0,261.0,247.0},
				{212.0,219.0,205.0},
				{179.0,186.0,172.0}
			};
		
		space= new Space2D(3,3);
		Var<BigDecimal> dt = new Var<BigDecimal>("dt",new BigDecimal("0.1"));
		
		MatrixDouble2D val = new MatrixDouble2D("val", dt, values);
		
		MatrixDouble2D ker = new MatrixDouble2D("kernel", dt,  kernel);
		
		MatrixDouble2D conv = new ConvolutionMatrix2D("conv",dt,space,ker,val);
		conv.compute();
		
		Double[][] res =conv.get2DArray();
		System.out.println("NoWrapped");
		System.out.println(conv.toString());
		assertTrue(ArrayUtils.equals2D(test,res));//TODO
	}

}
