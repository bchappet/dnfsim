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

import main.resources.utils.ArrayUtils;
import main.resources.utils.JamaMatrix;
import main.resources.utils.OpenCVMatrix;
import main.resources.utils.Matrix;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class OpenCVMatrixTestSpeed {

    private Matrix a;
    private Matrix b;
    private int size = 1000;

    private  long start,end;
    private static double delta = 1e-7;

    @Before
    public void setUp() throws Exception {
        double[][] tabA = ArrayUtils.randomMatrix(size/2, size );
        double[][] tabB = ArrayUtils.randomMatrix(size,size/2);


       a = new OpenCVMatrix(tabA);
        //System.out.println(a.toString());
        a.timesEquals(20);
        b= new OpenCVMatrix(tabB);
    }








    @Test
    public void testCloneSpeed() throws Exception {
        start = System.currentTimeMillis();
        Matrix clone = a.clone();
        end = System.currentTimeMillis();
        System.out.println("Clone : " + (end-start));

    }




    @Test
    public void testGetSpeed() throws Exception {
        start = System.currentTimeMillis();
        double get = a.get(this.size/4,this.size/2);
        end = System.currentTimeMillis();
        System.out.println("Get : " + (end-start));

    }

    @Test
    public void testGetArraySpeed() throws Exception {
        start = System.currentTimeMillis();
        double[][] array = a.getArray();
        end = System.currentTimeMillis();
        System.out.println("GetArray : " + (end-start));

    }



    @Test
    public void testSetSpeed() throws Exception {
        start = System.currentTimeMillis();
        a.set(this.size/4,this.size/2,208d);
        end = System.currentTimeMillis();
        System.out.println("Set : " + (end-start));

    }

    @Test
    public void testGetRowPackedCopySpeed() throws Exception {
        start = System.currentTimeMillis();
        double[] row = a.getRowPackedCopy();
        end = System.currentTimeMillis();
        System.out.println("RowPackedCopy : " + (end-start));

    }




    @Test
    public void testTransposeSpeed() throws Exception {
        start = System.currentTimeMillis();
        Matrix m = a.transpose();
        end = System.currentTimeMillis();
        System.out.println("Transpose : " + (end-start));

    }



    @Test
    public void testTimesSpeed() throws Exception {
        start = System.currentTimeMillis();
        Matrix res = a.times(b);
        end = System.currentTimeMillis();
        System.out.println("Times : " + (end-start));

    }



    @Test
    public void testArrayTimesSpeed() throws Exception {
        start = System.currentTimeMillis();
        Matrix res = a.arrayTimes(a);
        end = System.currentTimeMillis();
        System.out.println("ArrayTimes : " + (end-start));

    }


    @Test
    public void testInverseSpeed() throws Exception {
        start = System.currentTimeMillis();
        Matrix inv =a.inverse();
        end = System.currentTimeMillis();
        System.out.println("Pinv : " + (end-start));

    }



    @Test
    public void testPlusSpeed() throws Exception {
        start = System.currentTimeMillis();
        Matrix plus = a.plus(a);
        end = System.currentTimeMillis();
        System.out.println("Plus : " + (end-start));

    }


    @Test
    public void testTimes1Speed() throws Exception {
        start = System.currentTimeMillis();
        Matrix res = a.times(0.3);
        end = System.currentTimeMillis();
        System.out.println("Times double : " + (end-start));

    }


    @Test
    public void testTimesEqualsSpeed() throws Exception {
        start = System.currentTimeMillis();
        a.timesEquals(68.75);
        end = System.currentTimeMillis();
        System.out.println("Times Equals : " + (end-start));

    }

    @Test
    public void testHconcatSpeed() throws Exception {
        start = System.currentTimeMillis();
        a.hconcat(a);
        end = System.currentTimeMillis();
        System.out.println("Hconcat : " + (end-start));

    }

    @Test
    public void testGetValuesSpeed(){
        start = System.currentTimeMillis();
        List<Double> vals = a.getValues();
        end = System.currentTimeMillis();
        System.out.println("GetValues : " + (end-start));
    }

    @Test
    public void testConvolutionWrapSpeed(){
        double[][] tab = ArrayUtils.randomMatrix(size/4+1,size/4+1);
        Matrix test = new OpenCVMatrix(tab);
        start = System.currentTimeMillis();
        Matrix res = test.convolve(test,true);
        end = System.currentTimeMillis();
        System.out.println("ConvolutionWrap : " + (end-start));
    }

    @Test
    public void testConvolutionNoWrapSpeed(){
        double[][] tab = ArrayUtils.randomMatrix(size/4+1,size/4+1);
        Matrix test = new OpenCVMatrix(tab);
        start = System.currentTimeMillis();
        Matrix res = test.convolve(test,false);
        end = System.currentTimeMillis();
        System.out.println("ConvolutionNoWrap : " + (end-start));
    }
}