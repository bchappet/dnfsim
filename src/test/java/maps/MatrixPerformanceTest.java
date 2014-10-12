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


import main.java.maps.*;
import main.java.space.Space2D;
import main.java.unitModel.RandTrajUnitModel;
import main.resources.utils.ArrayUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertTrue;

public class MatrixPerformanceTest {
	
	private Space2D space;


    private Var<BigDecimal> dt;
    private int dimx;
    private int dimy ;
    private  long start,end;

    @Before
	public void setUp() throws Exception {

       dimx = 1000;
       dimy = 500;
        space = new Space2D(dimx,dimy);
        dt = new Var<BigDecimal>(new BigDecimal("1"));




	}

    @After
    public void teatDown(){


    }

    @Test
    public void testHorizontalConcatenationSpeed(){
        double[][] data = ArrayUtils.randomMatrix(dimy,dimx);
        MatrixDouble2D mat = new MatrixDouble2D("mat",dt,data);

        MatrixDouble2D uut = new HorizontalConcatenationMatrix(mat,mat);
        start = System.currentTimeMillis();
        uut.compute();
        end = System.currentTimeMillis();
        System.out.println("HorizontalConcatenation:" + (end-start));
    }

    @Test
    public void testToSquareSpeed(){
        double[][] data = ArrayUtils.randomMatrix(dimy,dimx);
        MatrixDouble2D mat = new MatrixDouble2D("mat",dt,data);
        MatrixDouble2D uut = new ToSquareMatrix((mat));
        start = System.currentTimeMillis();
        uut.compute();

        end = System.currentTimeMillis();
        System.out.println("ToSquare:" + (end-start));
    }

    @Test
    public void testMatrixWrapper(){
       UnitMap map = new UnitMap("map",new InfiniteDt(),space,new RandTrajUnitModel(0d),new Var<Double>(0d),new Var<Double>(1d));
        map.compute();
        MatrixDouble2D matrix = new MatrixDouble2DWrapper(map);
        start = System.currentTimeMillis();
        matrix.compute();
        end = System.currentTimeMillis();
        System.out.println("MatrixWrapper:" + (end-start));

    }

    @Test
    public void testMatrixProduct(){
        double[][] data = ArrayUtils.randomMatrix(dimy,dimx);
        double[][] data2 = ArrayUtils.randomMatrix(dimx,dimy);
        MatrixDouble2D matA = new MatrixDouble2D("mat",dt,data);
        MatrixDouble2D matB = new MatrixDouble2D("mat",dt,data2);
        MatrixDouble2D prod = new MultiplicationMatrix(matA,matB);
        start = System.currentTimeMillis();
        prod.compute();
        end = System.currentTimeMillis();
        System.out.println("MatrixProduct:" + (end-start));
    }

    @Test
    public void testTransposedmatrix(){
        double[][] data = ArrayUtils.randomMatrix(dimy,dimx);
        MatrixDouble2D mat = new MatrixDouble2D("mat",dt,data);
        MatrixDouble2D uut = new TransposedMatrix(mat);
        start = System.currentTimeMillis();
        uut.compute();
        end = System.currentTimeMillis();
        System.out.println("Transpose:" + (end-start));
    }

    @Test
    public void testPseudoInverse(){
        double[][] data = ArrayUtils.randomMatrix(dimy,dimx*2);
        MatrixDouble2D mat = new MatrixDouble2D("mat",dt,data);
        MatrixDouble2D mat2 = new InversionMatrix(mat);
        start = System.currentTimeMillis();
        mat2.compute();
        end = System.currentTimeMillis();
        System.out.println("PseudoInverse:" + (end-start));

    }

//    @Test
//    public void testConvolution(){
//        start = System.currentTimeMillis();
//        dimx = 201;
//        dimy = 201 ; //must be odd
//        Space2DWrap space = new Space2DWrap(dimx,dimy);
//        MatrixDouble2D matA = new MatrixDouble2D("matA",dt,ArrayUtils.randomMatrix(dimy,dimx));
//        MatrixDouble2D matB = new MatrixDouble2D("matB",dt,ArrayUtils.randomMatrix(dimy,dimx));
//
//        MatrixDouble2D conv = new ConvolutionMatrix2D("conv",dt,space,matA,matB);
//        conv.compute();
//        end = System.currentTimeMillis();
//        System.out.println("Convolution:" + (end-start));
//    }


	


}
