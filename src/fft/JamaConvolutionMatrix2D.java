package fft;
/**
 * Activity field using a dense matrix model
 *
 * @author Jean-Charles Quinton
 * @version 01/12/10
 * creation 22/01/10
 */

/*
    Copyright 2010 Jean-Charles Quinton

    This file is part of DNF.

    DNF is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    DNF is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with DNF.  If not, see <http://www.gnu.org/licenses/>.
 */

import static java.lang.Math.max;
import static java.lang.Math.min;

import java.awt.geom.Rectangle2D;
import java.util.Arrays;

import utils.ArrayUtils;

import maps.Matrix;
import maps.Matrix;
import maps.Matrix2D;
import maps.Parameter;
import maps.Var;
import coordinates.NullCoordinateException;
import coordinates.Space;

public class JamaConvolutionMatrix2D extends Matrix2D {

	public static final int KERNEL = 0;
	public static final int INPUT = 1;
	
	private double[] kernel_values;
	private double[] input_values;
	private double[] out_values;




	public JamaConvolutionMatrix2D(String name, Parameter dt, Space space, Parameter... params) {
		super(name,dt,space,params);
	}

	public JamaConvolutionMatrix2D(String name, Parameter dt, Space space, double[] values,
			Parameter... params) {
		super(name,dt,space,values,params);
	}

	@Override
	public void compute(){
		Parameter kernel = params.get(KERNEL);
		Parameter input = params.get(INPUT);
		
		kernel.constructMemory();
		input.constructMemory();
		
		kernel_values = kernel.getValues();
		input_values = input.getValues();
		out_values = this.values;
		
		

		convolve(kernel,this,input,space.isWrap());
	}






	/** Fill the matrix with the given map content */
	public void copy(JamaConvolutionMatrix2D map) {
		// Identity function for all cells of the matrix
		apply(new Function() {
			public double apply(double... params) {
				return params[0]; // identity
			}
		}, map);
	}










	//	public double aggregate(double init, Aggregation aggreg) {
	//		double res = init;
	//		for (int ux=0; ux<xs; ux++) {
	//			for (int uy=0; uy<ys; uy++) {
	//				res = aggreg.apply(res,ux,uy,values[ux][uy]);
	//			}
	//		}
	//		return res;
	//	}
	//
	//	public void scale(double scale) {
	//		for (int ux=0; ux<xs; ux++) {
	//			for (int uy=0; uy<ys; uy++) {
	//				values[ux][uy] *= scale;
	//			}
	//		}
	//	}

	public  boolean convolve(Parameter kernel2, JamaConvolutionMatrix2D out,Parameter input2,boolean wrap) {
		// Use SVD by default
		// (it is only useless if the matrix/kernel are too small,
		//  and this should be evaluated in this function => TODO)
		return convolve(kernel2,out,input2,true,wrap);
	}

	/** Convolution function selecting the apropriate implementation */
	public  boolean convolve(Parameter kernel2, JamaConvolutionMatrix2D out,Parameter input2, boolean svd,boolean wrap) {
		// If all parameters are dense, call the optimized version of the method
		// directly using the "values2D" variable

		if (svd) {
			// Try with the FFT 1D-convolutions (will return false if it did not apply)
			return /*((FFTConvolutionMatrix2D)kernel).convolveDenseSVDFFT(kernel,in,(FFTConvolutionMatrix2D)out,wrap) ? true :*/
					convolveDenseSVDMat(kernel2,input2,(JamaConvolutionMatrix2D)out,wrap);
		} else
			return convolveDense((JamaConvolutionMatrix2D)kernel2,(JamaConvolutionMatrix2D)out,(JamaConvolutionMatrix2D)input2,wrap);

	}

	/** Convolution of dense matrices
	 *  (the size of the matrices is not constrained here,
	 *   any m1*n1 convolution can be applied to an m2*n2 matrix)
	 *  @param kernel   convolution kernel
	 *  @param out      output matrix */
	protected  boolean convolveDense(Matrix2D kernel, Matrix2D out,Matrix2D in,boolean wrap) {
		return convolveDense(kernel_values,
				kernel.getSpace().getDiscreteSize()[X],
				kernel.getSpace().getDiscreteSize()[Y],
				out_values,
				out.getSpace().getDiscreteSize()[X],
				out.getSpace().getDiscreteSize()[Y],
				input_values,
				in.getSpace().getDiscreteSize()[X],
				in.getSpace().getDiscreteSize()[Y],wrap);
	}

	/** Convolution of dense matrices
	 *  (the size of the matrices is not constrained here,
	 *   any m1*n1 convolution can be applied to an m2*n2 matrix)
	 *  @param kernel   convolution kernel
	 *  @param out      output matrix */
	protected static boolean convolveDense(
			double[] kernel,int kx, int ky,
			double[] out,int ox,int oy,
			double[] in,int ix,int iy,
			boolean wrap) {
		
		// Check if the output has the right size
		if (out==null || ox!=ix || oy!=iy)
			return false;
		// Size of the convolution
		int cxs = kx;
		int cys = ky;
		// Apply the convolution

		for (int x=0; x<ix; x++) {
			for (int y=0; y<iy; y++) {
				double  res = 0;
				// Split the branches for more efficiency
				if (wrap) {
					for (int cx=0; cx<cxs; cx++) {
						int inx = (x+(cxs-1)/2-cx+ix)%ix;
						for (int cy=0; cy<cys; cy++) {
							int iny = (y+(cys-1)/2-cy+iy)%iy;
							res += in[inx + iny * ix]*kernel[cx + cy * kx];
						}
					}
				} else {
					final int cxmin = max(0,(cxs-1)/2-x);
					final int cxmax = min((cxs-1)/2+ix-x,cxs);
					for (int cx=cxmin; cx<cxmax; cx++) {
						int inx = x-(cxs-1)/2+cx;
						final int cymin = max(0,(cys-1)/2-y);
						final int cymax = min((cys-1)/2+iy-y,cys);
						for (int cy=cymin; cy<cymax; cy++) {
							int iny = y-(cys-1)/2+cy;
							res += in[inx + iny * ix]*kernel[cx + cy * kx];
						}
					}
				}
				out[x + y * ox]=res;
			}
		}
		return true;
	}

	/** Last used SVD decomposition
	 * (for exclusive use in the method convolve_svd) */
	private SVD svd = null;
	/** SVD decomposition rank */
	private int svd_rank;
	/** Stored "svd_rank" first rows of the transpose of V
	 * (that is already scaled by S for faster computations) */
	private double[][] svd_svt_rows;
	/** Stored "svd_rank" first columns of U */
	private double[][] svd_u_columns;

	/** Convolution using a SVD decomposition
	 *  (WARNING: parameters are swapped compared to the direct convolution method) */
	protected  boolean convolveDenseSVD(Parameter kernel,Parameter in, Matrix2D out,boolean wrap) {
		int kernel_xs = kernel.getSpace().getDiscreteSize()[X];
		int kernel_ys = kernel.getSpace().getDiscreteSize()[Y];

		int in_xs = in.getSpace().getDiscreteSize()[X];
		int in_ys = in.getSpace().getDiscreteSize()[Y];

		// Update the SVD (because the convolution matrix has changed) ?

		if (this.updateSVD(kernel) || svd_u_columns==null   ) {
			// Extract the rows of V' and scale them by S
			// as well as the columns of U
			svd_svt_rows = new double[svd_rank][1*kernel_ys];
			svd_u_columns = new double[svd_rank][kernel_xs*1];
			for (int k=0; k<svd_rank; k++) {
				double sk = svd.getSingular(k);
				for (int y=0; y<kernel_ys; y++)
					svd_svt_rows[k][0 + y * kernel_xs] = sk*svd.v[y][k];
				for (int x=0; x<kernel_xs; x++)
					svd_u_columns[k][x + 0 * kernel_xs] = svd.u[x + k * kernel_xs];
			}
		}
		// Initialize the ouput matrix
		out.apply(new Cst(0));
		// Intermediate matrices to store computations
		Parameter zt = Matrix2D.like(in);
		Parameter zt2 = Matrix2D.like(in);

		// For each singular value
		for (int k=0; k<svd_rank; k++) {
			// Compute the convolution (2-passes of 1D-convolutions)
			convolveDense(svd_u_columns[k],kernel_xs,1,
					zt2.getValues(),in_xs,in_ys,
					input_values,in_xs,in_ys,wrap); // columns of U
			convolveDense(svd_svt_rows[k],1,kernel_ys
					,zt.getValues(),in_xs,in_ys
					,zt2.getValues(),in_xs,in_ys,wrap); // rows of Vt
			// Update the output matrix
			out.apply(out.new Sum(),out,zt);
		}
		return true;
	}

	/** Update the SVD if needed or if it has not been initialized
	 * (this is a slow test but should be computed once and for all
	 * if the convolution kernel does not change = compare all the elements)
	 * @return      false if the SVD did not need to be evaluated/updated */
	
	public  boolean updateSVD(Parameter map) {
		if (svd==null || !svd.isSVDof(map)) { //TODO signal change
			// Generate a new SVD object
			// with the singular value decomposition of this matrix
			svd = new SVD(map);
			// Get the rank of the singular matrix
			// (number of non negligible singular values2D)
			svd_rank = svd.rank();
			return true;
		} else
			return false;
	}



	/** Stored matrix of shifted rows of SVt */
	private Matrix2D[] svd_svt_rs;
	/** Stored matrix of shifted columns of U */
	private Matrix2D[] svd_u_cs;

	/** Mat(hieu)ricial "Python efficient" convolution using a SVD decomposition
	 *  (WARNING: parameters are swapped compared to the direct convolution method) */
	protected  boolean convolveDenseSVDMat(Parameter kernel2,Parameter input2, JamaConvolutionMatrix2D out,boolean wrap) {
		// Update the SVD (because the convolution matrix has changed) ?
		
		if (updateSVD(kernel2) || svd_u_cs==null) {

			
			
			int kernel_xs = kernel2.getSpace().getDiscreteSize()[X];
			int kernel_ys = kernel2.getSpace().getDiscreteSize()[Y];
			// Produce matrices of shifted columns and rows
			// (to transform the convolution into a product,
			//  which might be optimized on some platforms)
			svd_svt_rs = new Matrix2D[svd_rank];
			svd_u_cs = new Matrix2D[svd_rank];
			// Extract the rows of V' and scale them by S
			// as well as the columns of U
			for (int k=0; k<svd_rank; k++) {
				svd_svt_rs[k] = new Matrix2D("svt_"+k,dt,kernel_ys,space);
				svd_u_cs[k] = new Matrix2D("u_"+k,dt,kernel_xs,space);
				double sk = svd.getSingular(k);
				for (int s=0; s<kernel_ys; s++)
					for (int y=0; y<kernel_ys; y++)
						svd_svt_rs[k].getValues()[s + y*kernel_ys] = sk*svd.v[(y+(kernel_ys-1)/2-s+kernel_ys)%kernel_ys][k];
				for (int s=0; s<kernel_xs; s++)
					for (int x=0; x<kernel_xs; x++)
						svd_u_cs[k].getValues()[x + s *kernel_xs] = svd.u[(x+(kernel_xs-1)/2-s+kernel_xs)%kernel_xs + k * kernel_xs];
			}
		}
		// Initialize the ouput matrix
		out.apply(out.new Cst(0));
		// Intermediate matrices to store computations
		Matrix2D zt = Matrix2D.like(input2);
		Matrix2D zt2 = Matrix2D.like(input2);
		
		int input_xs = input2.getSpace().getDiscreteSize()[X];
		int input_ys = input2.getSpace().getDiscreteSize()[Y];
		// For each singular value
		for (int k=0; k<svd_rank; k++) {
			// Compute the convolution (2-passes of 1D-convolutions)
			Matrix2D.multiply(svd_u_cs[k].getValues(),svd_u_cs[k].getXs(),svd_u_cs[k].getYs(),
					input_values,input_xs,input_ys
					,zt2.getValues(),zt2.getXs(),zt2.getYs()); // columns of U
			Matrix2D.multiply(zt2.getValues(),zt2.getXs(),zt2.getYs()
					,svd_svt_rs[k].getValues(),svd_svt_rs[k].getXs(),svd_svt_rs[k].getYs(),
					zt.getValues(),zt.getXs(),zt.getYs()); // rows of Vt
			// Update the output matrix
			out.apply(out.new Sum(),out,zt);
		}
		return true;
	}



	//	/** Last used FFT object */
	//	private FFT fft = null;
	//	/** Stored frequency rows of SVt */
	//	private double[][][] svd_svt_rf;
	//	/** Stored frequency columns of U */
	//	private double[][][] svd_u_cf;
	//
	//	/** Convolution using a SVD decomposition and 1D FFT convolutions
	//	 *  (WARNING: parameters are swapped compared to the direct convolution method) */
	//	protected  boolean convolveDenseSVDFFT(Matrix2D kernel,Matrix2D in, Matrix2D out,boolean wrap) {
	//		int kernel_xs = kernel.getSpace().getDiscreteSize()[X];
	//		int kernel_ys = kernel.getSpace().getDiscreteSize()[Y];
	//		// Check is the size is a power of 2
	//		if ((kernel_xs & (kernel_xs-1)) != 0) return false;
	//		// Check is the matrix is not rectangular (xs must be equal to ys for now)
	//		// (TODO: improve for the transpositions to remove the constraint)
	//		if (kernel_xs!=kernel_ys) return false;
	//		// Update the SVD and FFT (because the convolution matrix has changed) ?
	//		// (this is a slow test but should be computed once and for all
	//		// if the convolution kernel does not change = compare all the elements)
	//		if (updateSVD(kernel) || fft==null) {
	//			// Extract the rows of V' and scale them by S
	//			// as well as the columns of U
	//			// (matrices are manipulated to represent complex numbers)
	//			svd_svt_rf = new double[svd_rank][2][kernel_ys];
	//			svd_u_cf = new double[svd_rank][2][kernel_xs];
	//			for (int k=0; k<svd_rank; k++) {
	//				double sk = svd.getSingular(k);
	//				// Revert and shift the vectors by xs/2 or ys/2
	//				// (combination of the transform in the dense and SVD versions)
	//				for (int y=0; y<kernel_ys; y++)
	//					svd_svt_rf[k][FFT.RE][y] = sk*svd.v[(y+(kernel_ys-1)/2+kernel_ys)%kernel_ys][k];
	//				for (int x=0; x<kernel_xs; x++)
	//					svd_u_cf[k][FFT.RE][x] = svd.u[(x+(kernel_xs-1)/2+kernel_xs)%kernel_xs + k * kernel_xs];
	//			}
	//			// If the size of the matrix changed, generate a new FFT object
	//			if (fft==null || fft.size()!=kernel_xs || fft.size()!=kernel_ys)
	//				fft = new FFT(kernel_xs);
	//			// Perform the FFT on the decomposed kernel
	//			for (int k=0; k<svd_rank; k++) {
	//				fft.fft(svd_svt_rf[k]);
	//				fft.fft(svd_u_cf[k]);
	//			}
	//		}
	//		// Copy the input matrix (for the row access)
	//		Matrix[] inf = {
	//				(Matrix)in.clone(), // real part
	//				(Matrix)in.like() // imaginary part
	//		};
	//		// Compute the FFT of the input lines
	//		for (int i=0; i<kernel_ys; i++)
	//			fft.fft(inf[FFT.RE].getValues2D()[i],inf[FFT.IM].getValues2D()[i]);
	//		// Transpose to transform 1D-FFT columns into rows
	//		inf[FFT.RE] =  inf[FFT.RE].transpose(); // should not copy if xs=ys
	//		inf[FFT.IM] =  inf[FFT.IM].transpose();
	//		// Compute the FFT of the transposed 1D-FFT input
	//		for (int j=0; j<kernel_xs; j++)
	//			fft.fft(inf[FFT.RE].getValues2D()[j],inf[FFT.IM].getValues2D()[j]);
	//		// Transpose to transform 2D-FFT columns into rows (again)
	//		inf[FFT.RE] = (FFTConvolutionMatrix2D) inf[FFT.RE].transpose();
	//		inf[FFT.IM] = (FFTConvolutionMatrix2D) inf[FFT.IM].transpose();
	//		// We obtain a 2D-FTT of the input matrix
	//
	//		// Initialize the ouput matrix
	//		// (supposes xs=ys as we will also use it for the transposed output)
	//		out.apply(out.new Cst(0));
	//		// Output FFT coefficients
	//		Matrix[] outf = {
	//				out,(FFTConvolutionMatrix2D)out.like()
	//		};
	//		// Intermediate matrices to store computations
	//		Matrix[] zt2 = {
	//				in.like(),in.like()
	//		};
	//		Matrix[] zt = {
	//				in.like(),in.like()
	//		};
	//		// For each singular value, apply the circular 1D FFT convolutions
	//		for (int k=0; k<kernel.svd_rank; k++) {
	//			// 1D convolution with the FFT of the rows of Vt
	//			for (int i=0; i<kernel_ys; i++) {
	//				double[][] conv = kernel.fft.convolve(
	//						new double[][]{inf[FFT.RE].getValues2D()[i],inf[FFT.IM].getValues2D()[i]},
	//						kernel.svd_svt_rf[k]
	//						);
	//				zt2[FFT.RE].getValues2D()[i] = conv[FFT.RE];
	//				zt2[FFT.IM].getValues2D()[i] = conv[FFT.IM];
	//			}
	//			// Transpose to transform 1D-convoluted columns into rows
	//			zt2[FFT.RE] = (FFTConvolutionMatrix2D) zt2[FFT.RE].transpose();
	//			zt2[FFT.IM] = (FFTConvolutionMatrix2D) zt2[FFT.IM].transpose();
	//			// 1D convolution with the FFT of the columns of U
	//			for (int j=0; j<kernel_xs; j++) {
	//				double[][] conv = kernel.fft.convolve(
	//						new double[][]{zt2[FFT.RE].getValues2D()[j],zt2[FFT.IM].getValues2D()[j]},
	//						kernel.svd_u_cf[k]
	//						);
	//				zt[FFT.RE].getValues2D()[j] = conv[FFT.RE];
	//				zt[FFT.IM].getValues2D()[j] = conv[FFT.IM];
	//			}
	//			// Update the transposed output matrix
	//			// (as we did not retranspose the results)
	//			outf[FFT.RE].apply(out.new Sum(),outf[FFT.RE],zt[FFT.RE]);
	//			outf[FFT.IM].apply(out.new Sum(),outf[FFT.IM],zt[FFT.IM]);
	//		}
	//
	//		// Compute the inverse FFT to return to the spatial domain
	//		// on the columns
	//		for (int j=0; j<kernel_xs; j++)
	//			kernel.fft.ifft(new double[][]{outf[FFT.RE].getValues2D()[j],outf[FFT.IM].getValues2D()[j]});
	//		// Transpose to transform 1D-iFFT columns into rows
	//		outf[FFT.RE] = (FFTConvolutionMatrix2D) outf[FFT.RE].transpose();
	//		outf[FFT.IM] = (FFTConvolutionMatrix2D) outf[FFT.IM].transpose();
	//		// Compute the FFT of the transposed 1D-FFT input
	//		for (int i=0; i<kernel_ys; i++)
	//			kernel.fft.ifft(new double[][]{outf[FFT.RE].getValues2D()[i],outf[FFT.IM].getValues2D()[i]});
	//
	//		return true;
	//	}
	//
	//	










}