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

package main.resources.utils;


import org.opencv.core.*;
import org.opencv.core.Point;
import org.opencv.imgproc.Imgproc;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.opencv.core.Core.copyMakeBorder;

/**
 * Created by bchappet on 26/09/14.
 */
public class OpenCVMatrix implements Matrix{

    private Mat mat;
    static{ System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }
    private static int type = CvType.CV_64FC1;




    public Mat getMat() {

        return mat;
    }



    /**
     * Construct an m-by-n matrix of zeros.
     * @param m nb of rows
     * @param n nb of cols
     */
    public OpenCVMatrix(int m, int n) {
        mat = Mat.zeros(m,n, type);
    }

    /**
     *  Construct an m-by-n constant matrix.
     * @param m
     * @param n
     * @param s
     */
    public OpenCVMatrix(int m, int n, double s) {
        mat = new Mat(m,n,type,new Scalar(s));
    }

    public OpenCVMatrix(Mat mat){
        this.mat = mat;
    }

    /**
     *   Construct a matrix from a 2-D array.
     * @param doubles
     */
    public OpenCVMatrix(double[][] doubles) {
        this(doubles.length,doubles[0].length);
        this.setArray(doubles);
    }







    @Override
    public int getNbColumns() {
        return mat.cols();
    }

    public void setArray(double[][] array){
        for(int i = 0 ; i < array.length ; i++){
            for(int j = 0 ; j < array[0].length ; j++){
                mat.put(i,j,array[i][j]);
            }
        }
    }

    @Override
    public int getNbRows() {
        return mat.rows();
    }

    public OpenCVMatrix clone(){
        return new OpenCVMatrix(mat.clone());
    }

    @Override
    public double get(int x, int y) {
     //  System.out.println(mat.toString());
        return mat.get(y,x)[0];
    }

    @Override
    public double[][] getArray() {

        double[][] array = new double[getNbRows()][getNbColumns()];
        for(int i = 0 ; i < array.length ; i++){
            for(int j = 0 ; j < array[0].length ; j++){
                array[i][j] = this.get(j,i);
            }
        }
        return array;
    }

    @Override
    public void set(int x, int y, double val) {
        mat.put(y, x, val);
    }

    @Override
    public double[] getRowPackedCopy() {
        double[] ret = new double[this.getNbRows() * this.getNbColumns()];
        int k = 0;

        for (int i = 0; i < this.getNbRows(); i++) {
             for (int j = 0; j < this.getNbColumns(); j++) {
                ret[k] = this.get(j,i);
                k++;
            }
        }
        return ret;
    }

    protected double[] toArray(){
        double[] a = new double[getNbRows()*getNbColumns()];
        mat.get(0, 0, a);
        return a;
    }

    @Override
    public List<Double> getValues() {
        double[] a = toArray();
        Double ab[] = new Double[a.length];
        for(int i=0; i<a.length; i++)
            ab[i] = a[i];
        return Arrays.asList(ab);
    }

    public OpenCVMatrix transpose(){
        return new OpenCVMatrix(mat.t());
    }



    @Override
    public Matrix times(Matrix b){
        if(b instanceof OpenCVMatrix) {

            Mat c = new Mat(this.getNbColumns(),b.getNbRows(),type);
          //  System.out.println("this " + mat);
          //  System.out.println("b " + ((OpenCVMatrix) b).getMat());
          //  System.out.println("c " + c);
            Core.gemm(this.mat,  (((OpenCVMatrix) b).getMat()),1,new Mat(),0,c);
            return new OpenCVMatrix(c);
        }else{
            throw  new IllegalArgumentException("Multiplication impossible with different type");
        }
    }

    @Override
    public Matrix arrayTimes(Matrix b) {
        if(b instanceof OpenCVMatrix) {
            return new OpenCVMatrix(this.mat.mul(((OpenCVMatrix) b).getMat()));
        } else{
            throw  new IllegalArgumentException("Multiplication impossible with different type");
        }
    }

    @Override
    public Matrix inverse() {
        return new OpenCVMatrix(this.mat.inv(Core.DECOMP_SVD));
    }

    @Override
    public Matrix plus(Matrix b) {
        if(b instanceof OpenCVMatrix) {
            Mat c = new Mat(this.getNbColumns(),this.getNbRows(),type);
            Core.add(this.mat, (((OpenCVMatrix) b).getMat()), c);
            return new OpenCVMatrix(c);
        } else{
            throw  new IllegalArgumentException("Addition impossible with different type");
        }
    }

    @Override
    public Matrix times(double val) {
        Mat c = new Mat(this.getNbRows(),this.getNbColumns(),type);
        Core.multiply(this.mat, new Scalar(val), c);
        return new OpenCVMatrix(c);
    }

    @Override
    public Matrix hconcat(Matrix b) {
        Mat c = new Mat(this.getNbRows(),this.getNbColumns()+b.getNbColumns(),type);
        List<Mat> list = new LinkedList<Mat>();
        list.add(this.mat);
        list.add(((OpenCVMatrix) b).getMat());
        Core.hconcat(list, c);
        return new OpenCVMatrix(c);
    }

    @Override
    public void timesEquals(double val) {
        Core.multiply(this.mat, new Scalar(val), this.mat);
    }


    public static Matrix identity(int n, int m) {
        return new OpenCVMatrix(Mat.eye(n,m,type));
    }

    public String toString(){
        return ArrayUtils.toString(this.getArray());
    }

    @Override
    public int[] shape() {
        return new int[]{getNbRows(),getNbColumns()};
    }

    @Override
    public Matrix convolve(Matrix kernelM,boolean wrap){
        int convType;//type of border for convolution
        if(wrap)
            convType = CONVOLUTION_WRAP;
        else
            convType = CONVOLUTION_SAME;

        Mat destMat = null;
        if( kernelM instanceof  OpenCVMatrix) {
            Mat input = (Mat) this.getMat();
            Mat kernel = (Mat) kernelM.getMat();


           destMat = conv2(input,kernel,convType);

        }else{
            throw  new IllegalArgumentException("Convolution impossible with different type");
        }

        return new OpenCVMatrix(destMat);
    }


    public static final int   CONVOLUTION_FULL = 0; /* Return the full convolution, including border */
    public static final int   CONVOLUTION_SAME = 1; /* Return only the part that corresponds to the original image */
    public static final int   CONVOLUTION_VALID = 2; /* Return only the submatrix containing elements that were not influenced by the border */
    public static final int   CONVOLUTION_WRAP = 3; /*as for 'same' except that instead of using
//                                                    zero-padding the input A is taken to wrap round as
//                                                        on a toroid. */



   public static Mat  conv2(Mat img, Mat kernel, int type ) {
        Mat source = img;
        Mat dest = new Mat();
       int borderMode = Core.BORDER_CONSTANT;


        if(type == CONVOLUTION_FULL || type == CONVOLUTION_WRAP) {
            if(type == CONVOLUTION_WRAP)
                borderMode = Core.BORDER_WRAP;
            source = new  Mat();
            int additionalRows = kernel.rows()-1, additionalCols = kernel.cols()-1;
            copyMakeBorder(img, source, (additionalRows + 1) / 2, additionalRows / 2, (additionalCols + 1) / 2,
                    additionalCols / 2, borderMode, new Scalar(0));
        }

        Point anchor = new Point(kernel.cols() - kernel.cols()/2 - 1, kernel.rows() - kernel.rows()/2 - 1);


        Mat kernel2 = kernel.clone();
        Core.flip(kernel,kernel2,-1);
        Imgproc.filter2D(source, dest, img.depth(),kernel2 , anchor, 0,Core.BORDER_CONSTANT);

        if( type == CONVOLUTION_VALID || type == CONVOLUTION_WRAP) {
            dest = dest.colRange((kernel.cols()-1)/2, dest.cols() - kernel.cols()/2)
                    .rowRange((kernel.rows()-1)/2, dest.rows() - kernel.rows()/2);
        }

       return dest;
    }







}
