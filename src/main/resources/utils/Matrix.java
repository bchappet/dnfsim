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

import java.lang.*;
import java.util.List;

/**
 * Created by bchappet on 26/09/14.
 */
public interface Matrix extends java.lang.Cloneable{


    /**
     * Return the number of columns (DimX)
     * @return
     */
    int getNbColumns();


    /**
     * Return the number of rows (DimY)
     * @return
     */
    int getNbRows();


    public Matrix clone();

    /**
     * Return the element at x,y
     * @param x
     * @param y
     * @return
     */
    public double get(int x, int y);

    /**
     * Return the array
     * @warning get(x,y) = array[y][x]
     * @return
     */
    public double[][] getArray();

    /**
     * Return the value at coord x,y
     * @param x
     * @param y
     * @param val
     */
    public void set(int x, int y, double val);

    /**
     *
     * @return the row packed copy: 1,2,3 -> 1,2,3,4,5,6
     *                              4,5,6
     */
    public double[] getRowPackedCopy();

    /**
     * Get list of row packed copy
     * @return
     */
    public List<Double> getValues();


    /**
     * Get the transposed matrix
     * @return
     */
    public Matrix transpose();

    /**
     * Matrix multiplication
     * @param b
     * @return
     */
    public Matrix times(Matrix b) throws BadMatrixDimensionException ;

    /**
     * Point to point multiplication this .* b
     * @param b
     * @return
     */
    public Matrix arrayTimes(Matrix b);

    public Matrix inverse();

    public Matrix plus(Matrix b);

    public Matrix times(double val);

    public void timesEquals(double reg);


    public String toString();

    /**
     * Return (m,n) : (y,x) (nbRow,nbCol)
     * @return
     */
    public int[] shape();


    /**
     * Set with array
     * @warning get(x,y) = array[y][x]
     */
    public void setArray(double[][] doubles);

    /**
     * Return the storing matrix
     * @return
     */
    public Object getMat();

    /**
     * Horizontal concatenation this . b
     * @param b
     * @return
     */
    public Matrix hconcat(Matrix b);

    /**
     * Perform a convolution
     * @param kernelM
     * @param wrap
     * @return
     */
    public Matrix convolve(Matrix kernelM, boolean wrap);
}
