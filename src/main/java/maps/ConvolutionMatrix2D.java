package main.java.maps;

import static java.lang.Math.max;
import static java.lang.Math.min;

import java.math.BigDecimal;
import java.util.List;

import main.java.neigborhood.WrappedSpace;
import main.java.space.ISpace2D;
import main.java.space.Space;
import main.java.space.Space2D;
import Jama.Matrix;

public class ConvolutionMatrix2D extends MatrixDouble2D {

	/**The two dimension position**/
	public final static int X = 0;
	public final static int Y = 1;

	/**Parameters for convolution**/
	public final static int KERNEL = 0; 
	public final static int INPUT = 1;


	public ConvolutionMatrix2D(String name,Var<BigDecimal> dt,Space space,Parameter<Double>... params){
		super(name,dt,space,params);
	}

	/**
	 * @Precond : the kernel and the main.java.input are matrices
	 */
	@Override
	public void compute()
	{
		/**Kernel size**/
		
		int kx = ((ISpace2D) ((Map) getParam(KERNEL)).getSpace()).getDimX();
		int ky = ((ISpace2D) ((Map) getParam(KERNEL)).getSpace()).getDimY();
		/**Input size**/
		
		int vx = ((ISpace2D) ((Map) getParam(INPUT)).getSpace()).getDimX();
		int vy = ((ISpace2D) ((Map) getParam(INPUT)).getSpace()).getDimY();
		/**This size**/
		int width = ((ISpace2D)(this.getSpace())).getDimX();

		////////////////////////////////////////////////////////////////////

		List<Double> input = getParam(INPUT).getValues();
		List<Double> kernel = getParam(KERNEL).getValues();

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
		boolean wrap = this.getSpace() instanceof WrappedSpace;
		
		double[][] result = new double[xs][ys]; //result of convolution

		for (int x=0; x<xs; x++) {
			for (int y=0; y<ys; y++) {
				double  res = 0;
				if (wrap) {
					for (int cx=0; cx<cxs; cx++) {
						int inx = (x+(cxs-1)/2-cx+xs)%xs;
						for (int cy=0; cy<cys; cy++) {
							int iny = (y+(cys-1)/2-cy+ys)%ys;
							res += input.get(inx + iny * ys)*kernel.get(cx + cy * cys);
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
							res += input.get(inx + iny * ys)*kernel.get(cx + cy * cys);
						}
					}
				}
				result[y][x] = res;//to be compatible with jamat: transpose
			}
		}
		
		this.setJamat(new Matrix(result));


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
