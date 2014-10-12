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


import main.java.maps.Map;
import main.java.neigborhood.WrappedSpace;
import main.java.space.ISpace2D;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.max;
import static java.lang.Math.min;

/**
 * Created by bchappet on 26/09/14.
 */
public class JamaMatrix implements main.resources.utils.Matrix{
    
    private Jama.Matrix mat;


    public Jama.Matrix getMat() {
        return mat;
    }

    @Override
    public Matrix hconcat(Matrix b) {
        double[][] at = this.getArray();
		double[][] bt = b.getArray();

//		System.out.println("a " + ((Map) getParam(A)).getSpace());
//		System.out.println(ArrayUtils.toString(a));
//		System.out.println("b " + ((Map) getParam(B)).getSpace());
//		System.out.println(ArrayUtils.toString(b));


		double[][] res = ArrayUtils.horizontalConcatenation(at, bt);

        return  new JamaMatrix(res);
    }

    @Override
    public Matrix convolve(Matrix kernelM, boolean wrap) {

        /**Kernel size**/

        int kx = kernelM.getNbColumns();
        int ky = kernelM.getNbRows();
        /**Input size**/

        int vx = this.getNbColumns();
        int vy = this.getNbRows();
        /**Result size**/
        int width = kx;

        ////////////////////////////////////////////////////////////////////

        List<Double> input = this.getValues();
        List<Double> kernel = kernelM.getValues();

        //		try{
        //		System.out.println(((Matrix)params.get(INPUT)).display2D());
        //		System.out.println(((Matrix)params.get(KERNEL)).display2D());
        //		}catch (Exception e) {
        //			e.printStackTrace();
        //		}
        int cxs = kx;
        int cys = ky;
        int xs = vx;
        int ys = vy;


        double[][] result = new double[xs][ys]; //result of convolution



            for (int x = 0; x < xs; x++) {
                for (int y = 0; y < ys; y++) {
                    double res = 0;
                    if (wrap) {
                        for (int cx = 0; cx < cxs; cx++) {
                            int inx = (x + (cxs - 1) / 2 - cx + xs) % xs;
                            for (int cy = 0; cy < cys; cy++) {
                                int iny = (y + (cys - 1) / 2 - cy + ys) % ys;
                                res += input.get(inx + iny * ys) * kernel.get(cx + cy * cys);
                            }
                        }
                    } else {
                        final int cxmin = max(0, (cxs - 1) / 2 - x);
                        final int cxmax = min((cxs - 1) / 2 + xs - x, cxs);
                        for (int cx = cxmin; cx < cxmax; cx++) {
                            int inx = x - (cxs - 1) / 2 + cx;
                            final int cymin = max(0, (cys - 1) / 2 - y);
                            final int cymax = min((cys - 1) / 2 + ys - y, cys);
                            for (int cy = cymin; cy < cymax; cy++) {
                                int iny = y - (cys - 1) / 2 + cy;
                                res += input.get(inx + iny * ys) * kernel.get(cx + cy * cys);
                            }
                        }
                    }
                    result[y][x] = res;//to be compatible with jamat: transpose
                }
            }


        return new JamaMatrix(result);


    }

    /**
     * Construct an m-by-n matrix of zeros.
     * @param m
     * @param n
     */
    public JamaMatrix(int m, int n) {
        mat = new Jama.Matrix(m, n);
    }

    /**
     *  Construct an m-by-n constant matrix.
     * @param m
     * @param n
     * @param s
     */
    public JamaMatrix(int m, int n, double s) {
        mat = new Jama.Matrix(m, n, s);
    }

    private JamaMatrix(Jama.Matrix mat){
        this.mat = mat;
    }

    /**
     *   Construct a matrix from a 2-D array.
     * @param doubles
     */
    public JamaMatrix(double[][] doubles) {
        mat = new Jama.Matrix(doubles);
    }

    /**
     * Construct a matrix quickly without checking arguments.
     * @param doubles
     * @param m
     * @param n
     */
    public JamaMatrix(double[][] doubles, int m, int n) {
        mat = new Jama.Matrix(doubles, m, n);
    }

    /**
     * Construct a matrix from a one-dimensional packed array
     * @param vals
     * @param m
     */
    public JamaMatrix(double[] vals, int m) {
        mat = new Jama.Matrix(vals, m);
    }

    @Override
    public int getNbColumns() {
        return mat.getColumnDimension();
    }

    @Override
    public int getNbRows() {
        return mat.getRowDimension();
    }

    public JamaMatrix clone(){
        return new JamaMatrix(mat.copy());
    }

    @Override
    public double get(int x, int y) {
        return mat.get(y,x);
    }

    @Override
    public double[][] getArray() {
       return mat.getArray();
    }

    @Override
    public void set(int x, int y, double val) {
        mat.set(y,x,val);
    }

    @Override
    public double[] getRowPackedCopy() {
        return mat.getRowPackedCopy();
    }

    @Override
    public List<Double> getValues() {
        int size = getNbColumns()*getNbRows();
        ArrayList<Double> list  = new ArrayList<>(size);
        for (int i = 0; i <getNbRows(); i++) {
            for (int j = 0; j < getNbColumns(); j++) {
                   list.add(mat.get(i,j));
            }
        }
        return list;
    }

    public JamaMatrix transpose(){
        return new JamaMatrix(mat.transpose());
    }



    @Override
    public main.resources.utils.Matrix times(main.resources.utils.Matrix b){
        if(b instanceof  JamaMatrix) {
            return new JamaMatrix(mat.times((Jama.Matrix)((JamaMatrix) b).getMat()));
        }else{
            throw  new IllegalArgumentException("Multiplication impossible with different type");
        }
    }

    @Override
    public Matrix arrayTimes(Matrix b) {
        if(b instanceof  JamaMatrix) {
            return new JamaMatrix(this.mat.arrayTimes((Jama.Matrix)((JamaMatrix) b).getMat()));
        } else{
            throw  new IllegalArgumentException("Multiplication impossible with different type");
        }
    }

    @Override
    public Matrix inverse() {
            return new JamaMatrix(this.mat.inverse());
    }

    @Override
    public Matrix plus(Matrix b) {
        if(b instanceof  JamaMatrix) {
            return new JamaMatrix(this.mat.plus((Jama.Matrix) ((JamaMatrix) b).getMat()));
        } else{
            throw  new IllegalArgumentException("Addition impossible with different type");
        }
    }

    @Override
    public Matrix times(double val) {
        return new JamaMatrix(this.mat.times(val));
    }

    @Override
    public void timesEquals(double val) {
        this.mat.timesEquals(val);
    }


    public static Matrix identity(int n, int m) {
        return new JamaMatrix(Jama.Matrix.identity(n,m));
    }

    public String toString(){
        return ArrayUtils.toString(this.mat.getArray());
    }

    @Override
    public int[] shape() {
        return new int[]{getNbRows(),getNbColumns()};
    }

    @Override
    public void setArray(double[][] doubles) {
        this.mat = new Jama.Matrix(doubles);
    }
}
