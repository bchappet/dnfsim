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

package main.java.maps;

import main.java.neigborhood.WrappedSpace;
import main.java.space.Space;
import main.resources.utils.Matrix;

import java.math.BigDecimal;

import static java.lang.Math.max;
import static java.lang.Math.min;

/***
 *  the matrix has to be squared TODO fix
 */
public class Convolution2Matrix2D extends MatrixDouble2D {



	/**Parameters for convolution : MatrixDouble2D with OpenCV**/
	public final static int KERNEL = 0;
	public final static int INPUT = 1;


	public Convolution2Matrix2D(String name, Var<BigDecimal> dt, Space space, Parameter<Double>... params){
		super(name,dt,space,params);
	}

	/**
	 * @Precond : the kernel and the main.java.input are MatrixDouble2D with OpenCV
	 */
	@Override
	public void compute()
	{

        boolean wrap = this.getSpace() instanceof WrappedSpace;
        Matrix input =  ((MatrixDouble2D)getParam(INPUT)).getMat();
        Matrix kernel =  ((MatrixDouble2D)getParam(KERNEL)).getMat();

        this.setMat(input.convolve(kernel, wrap));


	}


}
