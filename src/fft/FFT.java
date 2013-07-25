package fft;
/**
 * Utility class to perform a fast fourier transform without
 * allocating any extra memory (Cooley-Tukey in place).
 * 
 * derivated class from the MEAPsoft project (http://www.meapsoft.org/)
 * originally released under the  GNU General Public License
 * Copyright 2006-2007 Columbia University
 *
 * @author Mike Mandel (mim@ee.columbia.edu)
 * @version 02/01/07
 * 
 * ---------------------------------------------------------
 * 
 * Modification for comments and additional methods
 * (including inverse FFT and convolutions)
 * 
 * @author Jean-Charles Quinton
 * @version 02/11/10
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

import java.util.*;

public class FFT {
  // Size of the data and log_2 of this size
  int n, m;
  
  // Lookup tables. Only need to recompute when size of FFT changes.
  double[] cos;
  double[] sin;

  double[] window;
  
  public FFT(int n) {
    this.n = n;
    this.m = (int)(Math.log(n) / Math.log(2));

    // Make sure n is a power of 2
    if(n != (1<<m))
      throw new RuntimeException("FFT length must be power of 2");

    // precompute tables
    cos = new double[n/2];
    sin = new double[n/2];

//     for(int i=0; i<n/4; i++) {
//       cos[i] = Math.cos(-2*Math.PI*i/n);
//       sin[n/4-i] = cos[i];
//       cos[n/2-i] = -cos[i];
//       sin[n/4+i] = cos[i];
//       cos[n/2+i] = -cos[i];
//       sin[n*3/4-i] = -cos[i];
//       cos[n-i]   = cos[i];
//       sin[n*3/4+i] = -cos[i];    
//     }

    for(int i=0; i<n/2; i++) {
      cos[i] = Math.cos(-2*Math.PI*i/n);
      sin[i] = Math.sin(-2*Math.PI*i/n);
    }

    makeWindow();
  }

  protected void makeWindow() {
    // Make a blackman window:
    // w(n)=0.42-0.5cos{(2*PI*n)/(N-1)}+0.08cos{(4*PI*n)/(N-1)};
    window = new double[n];
    for(int i = 0; i < window.length; i++)
      window[i] = 0.42 - 0.5 * Math.cos(2*Math.PI*i/(n-1)) 
    + 0.08 * Math.cos(4*Math.PI*i/(n-1));
  }
  
  public double[] getWindow() {
    return window;
  }

  /***************************************************************
  * fft.c
  * Douglas L. Jones 
  * University of Illinois at Urbana-Champaign 
  * January 19, 1992 
  * http://cnx.rice.edu/content/m12016/latest/
  * 
  *   fft: in-place radix-2 DIT DFT of a complex input 
  * 
  *   input: 
  * n: length of FFT: must be a power of two 
  * m: n = 2**m 
  *   input/output 
  * x: double array of length n with real part of data 
  * y: double array of length n with imag part of data 
  * 
  *   Permission to copy and use this program is granted 
  *   as long as this header is included. 
  ****************************************************************/
  public void fft(double[] x, double[] y) {
      // Comments added by Jean-Charles Quinton
      int i,j,k,n1,n2,a;
      double c,s,e,t1,t2;
    
      // Bit-reverse
      j = 0;
      n2 = n/2;
      for (i=1; i < n - 1; i++) {
          n1 = n2;
          while ( j >= n1 ) {
              j = j - n1;
              n1 = n1/2;
          }
          j = j + n1;
          // Swap the elements
          if (i < j) {
              t1 = x[i]; // re(t)
              x[i] = x[j];
              x[j] = t1;
              t1 = y[i]; // im(t)
              y[i] = y[j];
              y[j] = t1;
          }
      }

      // FFT
      n1 = 0;
      n2 = 1;
      
      // Divide and conquer = Cooley-Tukey algorithm
      // (increasing the power of 2 used)
      for (i=0; i < m; i++) {
          // Decomposition of the size : n2=2*n1 (radix-2 approach)
          n1 = n2;
          n2 = n2 + n2;
          a = 0;
          
          // Complex convolution for the current recursive step
          for (j=0; j < n1; j++) {
              // Retrieve precomputed cos/sin values
              c = cos[a];
              s = sin[a];
              // Index computation for the lookup tables
              a +=  1 << (m-i-1);
              
              for (k=j; k < n; k=k+n2) {
                  // Complex product
                  t1 = c*x[k+n1] - s*y[k+n1];
                  t2 = s*x[k+n1] + c*y[k+n1];
                  // Update of the coefficients
                  x[k+n1] = x[k] - t1;
                  y[k+n1] = y[k] - t2;
                  x[k] = x[k] + t1;
                  y[k] = y[k] + t2;
              }
          }
      }
  }
    
//////// Modification start ////////

    /** Index for the real and imaginary parts */
    public static final int RE = 0;
    public static final int IM = 1;
    
    /** Size of the manipulated arrays */
    public int size() {
        return n;
    }

    /** FFT with no imaginary parameters */
    public double[][] fft(double[] x) {
        double[][] t = new double[2][];
        t[RE] = x; // real part
        t[IM] = new double[x.length]; // imaginary (initialized to 0)
        return fft(t);
    }
    
    /** FFT with a single parameter */
    public double[][] fft(double[][] t) {
        fft(t[RE],t[IM]);
        return t; // returned to simplify use and calls
    }

    // The following methods are inspired by the code of Robert Sedgewick and Kevin Wayne
    // in section 9.7 of their Introduction to Programming in Java, Princeton university.
    // (optimized for manipulating the arrays of the MEAPsoft implementation)

    /** Inverse FFT */
    public double[] ifft(double[][] t) {
        // Take the conjugate of the coefficients
        for (int i=0; i<n; i++) {
            //t[RE][i] is unchanged
            t[IM][i] = -t[IM][i];
        }
        // Compute the forward FFT
        fft(t);
        // Take the conjugate again
        for (int i=0; i<n; i++)
            //t[RE][i] is unchanged
            t[IM][i] = -t[IM][i];
        // Divide by n
        for (int i=0; i<n; i++) {
            t[RE][i] /= (double)n;
            t[IM][i] /= (double)n;
        }
        return t[RE];
    }

    /** Convolution in the frequency domain */
    public double[][] convolve(double[][] x, double[][] y) {
        // Check if the lengths correspond to the FFT object
        if (x[RE].length!=y[RE].length || x[RE].length!=n)
            throw new RuntimeException("Dimensions don't agree");
        // Result array
        double[][] p = new double[2][n];
        // Point-wise multiplication
        // (frequency equivalent to a convolution in the spatial domain)
        for (int i=0; i<n; i++) {
            // Complex product
            p[RE][i] = x[RE][i]*y[RE][i] - x[IM][i]*y[IM][i];
            p[IM][i] = x[RE][i]*y[IM][i] + x[IM][i]*y[RE][i];
        }
        return p;
    }

    /** Circular convolution in the spatial domain */
    public double[] convolve(double[] x, double[] y) {
        // FFT of the inputs
        double[][] xf = fft(x);
        double[][] yf = fft(y);
        // Concolution in the frequency domain
        double[][] pf = convolve(xf,yf);
        // iFFT to return to the spatial domain
        return ifft(pf);
    }

    /** Linear convolution in the spatial domain */
    public double[] convolveLinear(double[] x, double[] y) {
        // Pad with zeros an array of twice the original size
        // to remove the periodicity
        Arrays.copyOf(x,2*x.length);
        Arrays.copyOf(y,2*y.length);
        // Call the "circular" convolution
        return convolve(x,y);
    }

    /** Test the convolutions
     * (once it has been tested with the main for FFT and iFFT) */
    public static void testConvolve() {
        // Size of the arrays
        int n = 128;
        // Number of iterations for the tests
        double iter = 1000*128/n;
        
        // Data (impulse)
        double[] data = new double[n];
        data[n/2]=1;
        // Kernel (CNFT DoG)
        double[] kernel = new double[n];
        for(int i=0; i<n/2; i++) {
            double dx = i/(double)n;
            kernel[i] = 1.35*Math.exp(-(dx*dx)/(0.1*0.1))
                       -0.70*Math.exp(-(dx*dx)/(1.0*1.0));
            if (i>0) kernel[n-i] = kernel[i];
        }
        printArray("Data",data);
        printArray("Kernel",kernel);
        System.out.println();

        // Spatial convolution
        double[] res = null;
        long time = System.currentTimeMillis();
        for(int i=0; i<iter; i++) {
            res = new double[n];
            for (int x=0; x<n; x++) {
                for (int cx=0; cx<n; cx++) {
                    int inx = (x+cx+n)%n;
                    res[x] += data[inx]*kernel[cx];
                }
            }
        }
        time = System.currentTimeMillis() - time;
        printArray("Spatial convolution",res);
        System.out.println("Averaged " + (time/iter) + "ms per iteration\n");

        // FFT convolution
        FFT fft = new FFT(n);
        double[] ktemp;
        time = System.currentTimeMillis();
        for(int i=0; i<iter; i++) {
            ktemp = kernel.clone();
            res = data.clone();
            res = fft.convolve(res,ktemp);
        }
        time = System.currentTimeMillis() - time;
        printArray("FFT convolution",res);
        System.out.println("Averaged " + (time/iter) + "ms per iteration\n");
        
        // FFT convolution (factored kernel transformation)
        time = System.currentTimeMillis();
        double[][] kf = fft.fft(kernel);
        double[][] df = new double[2][];
        for(int i=0; i<iter; i++) {
            // FFT of the data
            df[0] = data.clone();
            df[1] = new double[n];
            fft.fft(df);
            // Concolution in the frequency domain
            double[][] pf = fft.convolve(df,kf);
            // iFFT to return to the spatial domain
            res = fft.ifft(pf);
        }
        time = System.currentTimeMillis() - time;
        printArray("FFT convolution",res);
        System.out.println("Averaged " + (time/iter) + "ms per iteration\n");
    }

//////// Modification end ////////
  
  // Test the FFT to make sure it's working
  public static void main(String[] args) {
    int N = 128;

    FFT fft = new FFT(N);

    double[] window = fft.getWindow();
    double[] re = new double[N];
    double[] im = new double[N];

    // Impulse
    re[0] = 1; im[0] = 0;
    for(int i=1; i<N; i++)
      re[i] = im[i] = 0;
    beforeAfter(fft, re, im);

    // Nyquist
    for(int i=0; i<N; i++) {
      re[i] = Math.pow(-1, i);
      im[i] = 0;
    }
    beforeAfter(fft, re, im);

    // Single sin
    for(int i=0; i<N; i++) {
      re[i] = Math.cos(2*Math.PI*i / N);
      im[i] = 0;
    }
    beforeAfter(fft, re, im);

    // Ramp
    for(int i=0; i<N; i++) {
      re[i] = i;
      im[i] = 0;
    }
    beforeAfter(fft, re, im);

    long time = System.currentTimeMillis();
    double iter = 30000*8/N;
    for(int i=0; i<iter; i++)
      fft.fft(re,im);
    time = System.currentTimeMillis() - time;
    System.out.println("Averaged " + (time/iter) + "ms per iteration");
  }

  protected static void beforeAfter(FFT fft, double[] re, double[] im) {
    System.out.println("Before: ");
    printReIm(re, im);
    fft.fft(re, im);
//    fft.ifft(new double[][]{re,im});
    System.out.println("After: ");
    printReIm(re, im);
  }

  protected static void printReIm(double[] re, double[] im) {
    printArray("Re",re);
    printArray("Im",im);
  }
  
  protected static void printArray(String name, double[] a) {
    System.out.print(name + ": [");
    for(int i=0; i<a.length; i++)
      System.out.print(((int)(a[i]*1000)/1000.0) + " ");
    System.out.println("]");
  }
}
