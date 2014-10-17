/*
 * This is a Dynamic Neural Field simulator which is extended to
 *     several other neural networks and extended to hardware simulation.
 *
 *     Copyright (C) 2014  Benoît Chappet de Vangel
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package test.java.maps;

import main.java.maps.Convolution2Matrix2D;
import main.java.maps.MatrixDouble2D;
import main.java.maps.Var;
import main.java.space.Space2D;
import main.resources.utils.ArrayUtils;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertTrue;

public class Convolution2Matrix2DTest {
	
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
		
		MatrixDouble2D conv = new Convolution2Matrix2D("conv",dt,space,ker,val);
        long start = System.currentTimeMillis();
        conv.compute();
        long end = System.currentTimeMillis();
		
		Double[][] res =conv.get2DArray();

		System.out.println("Wrapped");
		System.out.println(conv.toString());
        System.out.println("Time : " + (end-start) );//TODO seems slow...
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
		
		Double[][] test = 
			{
				{ 67., 106d,  69d},
				{143d, 219d, 135d},
				{ 99d, 148d,  80d}
			};
		
		space= new Space2D(3,3);
		Var<BigDecimal> dt = new Var<BigDecimal>("dt",new BigDecimal("0.1"));
		
		MatrixDouble2D val = new MatrixDouble2D("val", dt, values);
		
		MatrixDouble2D ker = new MatrixDouble2D("kernel", dt,  kernel);
		
		MatrixDouble2D conv = new Convolution2Matrix2D("conv",dt,space,ker,val);
        long start = System.currentTimeMillis();
		conv.compute();
        long end = System.currentTimeMillis();
		Double[][] res =conv.get2DArray();
		System.out.println("NoWrapped");
		System.out.println(conv.toString());
        System.out.println("Time : " + (end-start) );
        assertTrue(ArrayUtils.equals2D(test,res));//TODO
	}

}
