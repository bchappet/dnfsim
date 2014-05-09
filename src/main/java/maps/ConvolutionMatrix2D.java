package main.java.maps;

import static java.lang.Math.max;
import static java.lang.Math.min;

import java.util.Arrays;

import main.java.coordinates.Space;

public class ConvolutionMatrix2D extends Matrix2D {

	/**The two dimension position**/
	public final static int X = 0;
	public final static int Y = 1;

	/**Parameters for convolution**/
	public final static int KERNEL = 0; 
	public final static int INPUT = 1;




	public ConvolutionMatrix2D(String name, Var dt, Space space,Parameter... params) {
		super(name, dt, space, params);
		if( space.getDim() != 2){
			System.err.println("Warning, bad dimension " + Arrays.toString(Thread.currentThread().getStackTrace()));
		}
		constructParametersMemory();
	}

	@Override
	public void addParameters(Parameter ... params)
	{
		super.addParameters(params);
		constructParametersMemory();
	}
	
	


	/**
	 * @Precond : the kernel and the main.java.input are matrices
	 */
	@Override
	public void compute()
	{
		/**Kernel size**/
		int kx = params.getIndex(KERNEL).getSpace().getDiscreteSize()[X];
		int ky = params.getIndex(KERNEL).getSpace().getDiscreteSize()[Y];
		/**Input size**/
		
		int vx = params.getIndex(INPUT).getSpace().getDiscreteSize()[X];
		int vy = params.getIndex(INPUT).getSpace().getDiscreteSize()[Y];
		/**This size**/
		int width = this.getSpace().getDiscreteSize()[X];

		////////////////////////////////////////////////////////////////////

		double[] input = params.getIndex(INPUT).getValues();
		double[] kernel = params.getIndex(KERNEL).getValues();

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
		boolean wrap = space.isWrap();

		for (int x=0; x<xs; x++) {
			for (int y=0; y<ys; y++) {
				double  res = 0;
				if (wrap) {
					for (int cx=0; cx<cxs; cx++) {
						int inx = (x+(cxs-1)/2-cx+xs)%xs;
						for (int cy=0; cy<cys; cy++) {
							int iny = (y+(cys-1)/2-cy+ys)%ys;
							res += input[inx + iny * ys]*kernel[cx + cy * cys];
						}
					}
				} else {
					final int cxmin = max(0,(cxs-1)/2-x);
					final int cxmax = min((cxs-1)/2+xs-x,cxs);
					for (int cx=cxmin; cx<cxmax; cx++) {
						int inx = x-(cxs-1)/2+cx;
						final int cymin = max(0,(cys-1)/2-y);
						final int cymax = min((cys-1)/2+ys-y,cys);
						for (int cy=cymin; cy<cymax; cy++) {
							int iny = y-(cys-1)/2+cy;
							res += input[inx + iny * ys]*kernel[cx + cy * cys];
						}
					}
				}
				values[x + y*width] = res;
			}
		}


		// find center position of kernel (half of kernel size)
//		int kCenterX = kx / 2 ;
//		int kCenterY = ky / 2 ;
//
//		for(int i=0; i < vy; i++)              // rows
//		{
//			for(int j=0; j < vx; j++)          // columns
//			{
//				//System.out.println("Coord : " + i+","+j);
//				double sum = 0;                     // init to 0 before sum
//				for(int m=0; m < ky; m++)     // kernel rows
//				{
//					int mm = ky - 1 - m;      // row index of flipped kernel
//					for(int n=0; n < kx; n++) // kernel columns
//					{
//						int nn = kx - 1 - n;  // column index of flipped kernel
//						// index of main.java.input signal, used for checking boundary
//						int ii = (i + (m - kCenterY));
//						int jj = (j + (n - kCenterX));
//
//						//Wrap coor if out of bounds
//						if( ii < 0 || ii >= vy   || jj < 0 || jj >= vx )
//						{
//							ii = (ii % vy);
//							jj =  (jj % vx);
//							if(ii < 0) ii += vy;
//							if(jj < 0) jj += vx;
//						}
//						try{
//						sum += main.java.input[ii + jj*vx] * kernel[mm +  nn*kx];
//						}catch (Exception e) {
//							//System.out.println("out of bounds");
//						}
//						//System.out.println(main.java.input[ii][jj]+ " * "+ kernel[mm][nn]);
//					}
//				}
//				values[i + j*width] = sum;
//				//System.out.println("R�sult : " + sum);
//			}
//		}

	}


}
