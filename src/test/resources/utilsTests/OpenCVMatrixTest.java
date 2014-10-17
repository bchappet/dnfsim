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

package test.resources.utilsTests;

import main.resources.utils.*;
import org.junit.Before;
import org.junit.Test;
import org.opencv.core.Mat;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class OpenCVMatrixTest {

    private Matrix a,testA;
    private Matrix b,testB;
    private double[][] tA,tB;

    private  long start,end;
    private static double delta = 1e-6;

    @Before
    public void setUp() throws Exception {

         tA = new double[][]{{1,2,3},
                            {4,5,6}};

       testA = new OpenCVMatrix(tA);
    }

    @Test
    public void testConstruct(){
        Matrix m = new OpenCVMatrix(2,3);
        assertArrayEquals("The shape must be (2,3)",new int[]{2,3},m.shape());
        assertEquals("The nb of row must be 2 ", 2, testA.getNbRows());
        assertEquals("The nb of column must be 3 ", 3, testA.getNbColumns());
//        System.out.println(((Mat)testA.getMat()).dump());
//        System.out.println(testA);
        assertTrue("The arrays must be the same : ", ArrayUtils.equals2D(tA, testA.getArray(), delta));

        assertArrayEquals("The shape must be (2,3)",new int[]{2,3},testA.shape());
        assertArrayEquals("the row packed copy should be 1,2,3,4,5,6",
                new double[]{1, 2, 3, 4, 5, 6}, testA.getRowPackedCopy(), 1E-7);

    }

    @Test
    public void testClone(){
        Matrix clone = testA.clone();
        assertNotSame("The clone must have diff adress", testA, clone);
        assertNotSame("The clone must have a diff matrix",testA.getMat(),clone.getMat());
        assertTrue("The arrays must be the same : ", ArrayUtils.equals2D(tA, clone.getArray(), delta));
//        System.out.println(clone);

    }








    @Test
    public void testGet(){
        double get = testA.get(2, 0);
        assertEquals("The get should be 3",3.,get,delta);
        get = testA.get(2,1);
        assertEquals("The get should be 6",6.,get,delta);
        get = testA.get(0,1);
        assertEquals("The get should be 4",4.,get,delta);
    }









    @Test
    public void testSet(){
        double val = 0.1;
        testA.set(2, 0, val);
        double get = testA.get(2,0);
        assertEquals("The get should be " + val, val, get, delta);
    }








    @Test
    public void testTranspose(){
        Matrix t = testA.transpose();
        double[][] trans = new double[][]{{1,4},{2,5},{3,6}};
        assertTrue("The transposee should be  : " ,ArrayUtils.equals2D(trans, t.getArray(),delta));

    }




    @Test
    public void testTimes() throws BadMatrixDimensionException {
        Matrix b = testA.transpose();
//        System.out.println(Arrays.toString(testA.shape()));
//        System.out.println(Arrays.toString(b.shape()));
        Matrix res = testA.times(b);
        double[][] resT = new double[][]{{14.0,32.0},
                                        {32.0,77.0}};
//        System.out.println(res);
        assertTrue("The result should be good  for times" , ArrayUtils.equals2D(resT,res.getArray(),delta));
    }




    @Test
    public void testArrayTimes() throws Exception {
        Matrix res = testA.arrayTimes(testA);
        double[][] resT = new double[][]{{1,4,9},
                                         {16,25,36}};
        assertTrue("The result should be good for arrayTimes" , ArrayUtils.equals2D(resT,res.getArray(),delta));
    }




    @Test
    public void testInverse(){
        Matrix res = testA.transpose().inverse();
//        System.out.println(res);
        double[][] resT = new double[][]{{-0.94444444, -0.11111111,0.72222222},
                                        {0.44444444,  .11111111,-0.22222222}};
        assertTrue("The result should be good for inverse" , ArrayUtils.equals2D(resT,res.getArray(),delta));
    }



    @Test
    public void testPlus() throws Exception {
        Matrix res = testA.plus(testA);
        double[][] resT = new double[][]{{2,4,6},
                                         {8,10,12}};
        assertTrue("The result should be good for plus" , ArrayUtils.equals2D(resT,res.getArray(),delta));
    }




    @Test
    public void testTimes1() throws Exception {
        Matrix res = testA.times(2);
        double[][] resT = new double[][]{{2,4,6},
                                         {8,10,12}};
        assertTrue("The result should be good for time double" , ArrayUtils.equals2D(resT,res.getArray(),delta));
    }




    @Test
    public void testTimesEquals() throws Exception {
        testA.timesEquals(2);
        double[][] resT = new double[][]{{ 2,  4,6},
                {          8, 10,12}};
        assertTrue("The result should be good for time double" , ArrayUtils.equals2D(resT,testA.getArray(),delta));
    }

    @Test
    public void testHConcat(){
        Matrix res  = testA.hconcat(testA);

        double[][] resT = new double[][]{{1,2,3,1,2,3},
                                        {4,5,6,4,5,6}};
        assertTrue("The result should be good for time double" , ArrayUtils.equals2D(resT,res.getArray(),delta));
    }

    @Test
    public void testGetValues(){
        List<Double> res = testA.getValues();
        assertEquals("The lists should be equals",Arrays.asList(new Double[]{1d,2d,3d,4d,5d,6d}),res);

    }

//    @Test
//    public void testConvolutionNoWrap(){
//
//        double[][] values =
//                {
//                        {1,2,3},
//                        {4,5,6},
//                        {7,8,9}
//                };
//        Matrix valM = new OpenCVMatrix(values);
//
//        double[][] kernel=
//                {
//                        {7,6,5},
//                        {3,2,1},
//                        {9,4,6},
//                };
//        Matrix kernelM = new OpenCVMatrix(kernel);
//
//        double[][] resT =
//                {
//                        { 67., 106d,  69d},
//                        {143d, 219d, 135d},
//                        { 99d, 148d,  80d}
//                };
//
//        Double[][] test =
//                {
//                        {254.0,261.0,247.0},
//                        {212.0,219.0,205.0},
//                        {179.0,186.0,172.0}
//                };
//        Matrix res = valM.convolve(kernelM,true);
//
//        System.out.println("res : " + res);
//        assertTrue("The result should be good for convolution" , ArrayUtils.equals2D(resT,res.getArray(),delta));
//
//
//    }

    @Test
    public void testConvolutionWrap(){

        double[][] values =
                {
                        {1,2,3},
                        {4,5,6},
                        {7,8,9}
                };
        Matrix valM = new OpenCVMatrix(values);

        double[][] kernel=
                {
                        {7,6,5},
                        {3,2,1},
                        {9,4,6},
                };
        Matrix kernelM = new OpenCVMatrix(kernel);



        double[][] resT =
                {
                        {254.0,261.0,247.0},
                        {212.0,219.0,205.0},
                        {179.0,186.0,172.0}
                };
        Matrix res = valM.convolve(kernelM,true);

        assertTrue("The result should be good for convolution" , ArrayUtils.equals2D(resT,res.getArray(),delta));


    }

    @Test
    public void testConvolutionNoWrap(){

        double[][] values =
                {
                        {1,2,3},
                        {4,5,6},
                        {7,8,9}
                };
        Matrix valM = new OpenCVMatrix(values);

        double[][] kernel=
                {
                        {7,6,5},
                        {3,2,1},
                        {9,4,6},
                };
        Matrix kernelM = new OpenCVMatrix(kernel);



        double[][] resT =
                {
                        { 67., 106d,  69d},
                        {143d, 219d, 135d},
                        { 99d, 148d,  80d}
                };
        Matrix res = valM.convolve(kernelM,false);

        assertTrue("The result should be good for convolution" , ArrayUtils.equals2D(resT,res.getArray(),delta));


    }





}
