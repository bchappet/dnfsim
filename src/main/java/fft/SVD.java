package main.java.fft;
/**
 * Singular Value Decomposition (SVD) of a matrix
 *
 * @author Jean-Charles Quinton
 * @version 11/02/10
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

import static java.lang.Math.abs;
import static java.lang.Math.copySign;
import static java.lang.Math.hypot;
import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.lang.Math.sqrt;
import main.java.maps.Matrix;
import main.java.maps.Matrix2D;
import main.java.maps.Parameter;

public class SVD {
	
	public static final int X = 0;
	public static final int Y = 1;
	
    /** Reference matrix */
	protected  Matrix2D map;
    /** Level of comparison to check the matrix correspondance */
    int comparison_level = FOUR;
    /** Defined levels of comparison */
    public static final int FULL = -1; // test all points on the map
    public static final int NONE = 0; // identical matrices if same size
    public static final int FOUR = 2; // four points (should be {0,xs/2}x{0,ys/2}
    
    /** SVD decomposition of this matrix */
    public double[] u;
    public double[][] v;
    double[] w;
    
    
    /** Constructor */
    public SVD(Parameter map) {
        this.map = new Matrix2D(map);
        svd();
        svdReorder();
    }
    
    /** Test if the source matrix of this decomposition
     *  is identical to the given matrix */
    public boolean isSVDof(Parameter map) {
        return this.map.equalsRough(map,comparison_level);
    }
    
    /** Get the rank of the SVD */
    public int rank() {
        int s=0;
        while (s<w.length && w[s]>1e-10) s++;
        return s;
    }
    
    /** Get the nth singular value */
    public double getSingular(int s) {
        return w[s];
    }
    
    /** Compute the SVD decomposition of this matrix
     *  COPY of the algorithm in NUMERICAL RECIPES
     *  Webnote No. 2, Rev. 1
     *  
     *  Stoer, J., and Bulirsch, R. 2002, Introduction to Numerical Analysis, 3rd ed. (New York: Springer),?6.7.
     *  Golub, G.H., and Van Loan, C.F. 1996, Matrix Computations, 3rd ed. (Baltimore: Johns Hopkins University Press), Chapter 12 (SVD).
     *  
     *  We here list the implementation that constructs the singular value decomposition
     *  of any matrix. See ?11.3??11.4, and also [1,2], for discussion relating to the
     *  underlying method. Note that all the hard work is done by decompose; reorder
     *  simply orders the columns into canonical order (decreasing wj 's, and with sign ips
     *  to get the maximum number of positive elements. The function pythag does just
     *  what you might guess from its name, coded so as avoid overflow or underflow.
     *  
     *  Given the matrix A stored in u[0..m-1][0..n-1], this routine computes its singular value
     *  decomposition, A = U.W.Vt and stores the results in the matrices u and v, and the vector
     *  w. */
    public boolean svd() {
        // Parameters and outputs
        int m = map.getSpace().getDiscreteSize()[X];
        int n = map.getSpace().getDiscreteSize()[Y];
        double eps = 0.00001; // for convergence
        // Copy the original matrix
        // (we want a dense copy to access all the values)
        Matrix copy = map.clone();
        u = copy.getValues(); // size m*n
        v = new double[n][n];
        w = new double[n];
        // Copy of original code
        // (except for some methods already existing in the Math class => copySign...)
        boolean flag;
        int i,its,j,jj,k,l=0,nm=0;
        double anorm,c,f,g,h,s,scale,x,y,z;
        double[] rv1 = new double[n];
        g = scale = anorm = 0.0; // Householder reduction to bidiagonal form.
        for (i=0;i<n;i++) {
            l=i+2;
            rv1[i]=scale*g;
            g=s=scale=0.0;
            if (i < m) {
                for (k=i;k<m;k++) scale += abs(u[k + i * m]);
                if (scale != 0.0) {
                    for (k=i;k<m;k++) {
                        u[k  + i * m] /= scale;
                        s += u[k + i * m]*u[k + i * m];
                    }
                    f=u[i + i  * m];
                    g = -copySign(sqrt(s),f);
                    h=f*g-s;
                    u[i + i * m]=f-g;
                    for (j=l-1;j<n;j++) {
                        for (s=0.0,k=i;k<m;k++) s += u[k + i * m]*u[k + j * m];
                        f=s/h;
                        for (k=i;k<m;k++) u[k + j  * m ] += f*u[k + i * m];
                    }
                    for (k=i;k<m;k++) u[k + i * m] *= scale;
                }
            }
            w[i]=scale *g;
            g=s=scale=0.0;
            if (i+1 <= m && i+1 != n) {
                for (k=l-1;k<n;k++) scale += abs(u[i + k * m]);
                if (scale != 0.0) {
                    for (k=l-1;k<n;k++) {
                        u[i + k * m] /= scale;
                        s += u[i + k * m]*u[i + k * m];
                    }
                    f=u[i + (l-1)*m];
                    g = -copySign(sqrt(s),f);
                    h=f*g-s;
                    u[i + (l-1)*m]=f-g;
                    for (k=l-1;k<n;k++) rv1[k]=u[i + k * m]/h;
                    for (j=l-1;j<m;j++) {
                        for (s=0.0,k=l-1;k<n;k++) s += u[j + k * m]*u[i + k * m];
                        for (k=l-1;k<n;k++) u[j + k * m] += s*rv1[k];
                    }
                    for (k=l-1;k<n;k++) u[i + k * m] *= scale;
                }
            }
            anorm=max(anorm,(abs(w[i])+abs(rv1[i])));
        }
        for (i=n-1;i>=0;i--) { // Accumulation of right-hand transformations.
            if (i < n-1) {
                if (g != 0.0) {
                    for (j=l;j<n;j++) // Double division to avoid possible underflow.
                    v[j][i]=(u[i + j * m]/u[i + l * m])/g;
                    for (j=l;j<n;j++) {
                        for (s=0.0,k=l;k<n;k++) s += u[i + k * m]*v[k][j];
                        for (k=l;k<n;k++) v[k][j] += s*v[k][i];
                    }
                }
                for (j=l;j<n;j++) v[i][j]=v[j][i]=0.0;
            }
            v[i][i]=1.0;
            g=rv1[i];
            l=i;
        }
        for (i=min(m,n)-1;i>=0;i--) { // Accumulation of left-hand transformations.
            l=i+1;
            g=w[i];
            for (j=l;j<n;j++) u[i + j * m]=0.0;
            if (g != 0.0) {
                g=1.0/g;
                for (j=l;j<n;j++) {
                    for (s=0.0,k=l;k<m;k++) s += u[k + i * m]*u[k +j*m];
                    f=(s/u[i + i * m])*g;
                    for (k=i;k<m;k++) u[k + j * m] += f*u[k + i* m];
                }
                for (j=i;j<m;j++) u[j + i * m] *= g;
            } else for (j=i;j<m;j++) u[j + i * m]=0.0;
            ++u[i + i * m];
        }
        for (k=n-1;k>=0;k--) { // Diagonalization of the bidiagonal form: Loop over
            for (its=0;its<30;its++) { // singular values, and over allowed iterations.
                flag=true;
                for (l=k;l>=0;l--) { // Test for splitting.
                    nm=l-1;
                    if (l == 0 || abs(rv1[l]) <= eps*anorm) {
                        flag=false;
                        break;
                    }
                    if (abs(w[nm]) <= eps*anorm) break;
                }
                if (flag) {
                    c=0.0; // Cancellation of rv1[l], if l > 0.
                    s=1.0;
                    for (i=l;i<k+1;i++) {
                        f=s*rv1[i];
                        rv1[i]=c*rv1[i];
                        if (abs(f) <= eps*anorm) break;
                        g=w[i];
                        h=hypot(f,g);
                        w[i]=h;
                        h=1.0/h;
                        c=g*h;
                        s = -f*h;
                        for (j=0;j<m;j++) {
                            y=u[j + nm * m];
                            z=u[j + i * m];
                            u[j + nm * m]=y*c+z*s;
                            u[j + i * m]=z*c-y*s;
                        }
                    }
                }
                z=w[k];
                if (l == k) { // Convergence.
                    if (z < 0.0) { // Singular value is made nonnegative.
                        w[k] = -z;
                        for (j=0;j<n;j++) v[j][k] = -v[j][k];
                    }
                    break;
                }
                if (its == 29) {
                    System.err.println("No SVD convergence in 30 svdcmp iterations");
                    u = null;
                    v = null;
                    w = null;
                    return false;
                }
                x=w[l]; // Shift from bottom 2-by-2 minor.
                nm=k-1;
                y=w[nm];
                g=rv1[nm];
                h=rv1[k];
                f=((y-z)*(y+z)+(g-h)*(g+h))/(2.0*h*y);
                g=hypot(f,1.0);
                f=((x-z)*(x+z)+h*((y/(f+copySign(g,f)))-h))/x;
                c=s=1.0; // Next QR transformation:
                for (j=l;j<=nm;j++) {
                    i=j+1;
                    g=rv1[i];
                    y=w[i];
                    h=s*g;
                    g=c*g;
                    z=hypot(f,h);
                    rv1[j]=z;
                    c=f/z;
                    s=h/z;
                    f=x*c+g*s;
                    g=g*c-x*s;
                    h=y*s;
                    y *= c;
                    for (jj=0;jj<n;jj++) {
                        x=v[jj][j];
                        z=v[jj][i];
                        v[jj][j]=x*c+z*s;
                        v[jj][i]=z*c-x*s;
                    }
                    z=hypot(f,h);
                    w[j]=z; // Rotation can be arbitrary if z D 0.
                    if (z!=0) {
                        z=1.0/z;
                        c=f*z;
                        s=h*z;
                    }
                    f=c*g+s*y;
                    x=c*y-s*g;
                    for (jj=0;jj<m;jj++) {
                        y=u[jj + j * m];
                        z=u[jj + i * m];
                        u[jj + j * m]=y*c+z*s;
                        u[jj + i * m]=z*c-y*s;
                    }
                }
                rv1[l]=0.0;
                rv1[k]=f;
                w[k]=x;
            }
        }
        return true;
    }

    /** Given the output of decompose, this routine sorts the singular values, and corresponding columns
     *  of u and v, by decreasing magnitude. Also, signs of corresponding columns are flipped so as to
     *  maximize the number of positive elements. */
    private void svdReorder() {
        // Parameters and outputs
        int m = map.getSpace().getDiscreteSize()[X];
        int n = v.length;
        // Copy of original code
        int i,j,k,s,inc=1;
        double sw;
        double[] su = new double[m];
        double[] sv = new double[n];
        do { inc *= 3; inc++; } while (inc <= n); // Sort. The method is Shell's sort.
        do {
            inc /= 3;
            for (i=inc;i<n;i++) {
                sw = w[i];
                for (k=0;k<m;k++) su[k] = u[k + i * m];
                for (k=0;k<n;k++) sv[k] = v[k][i];
                j = i;
                while (w[j-inc] < sw) {
                    w[j] = w[j-inc];
                    for (k=0;k<m;k++) u[k + j * m] = u[k + (j-inc)*m];
                    for (k=0;k<n;k++) v[k][j] = v[k][j-inc];
                    j -= inc;
                    if (j < inc) break;
                }
                w[j] = sw;
                for (k=0;k<m;k++) u[k + j * m] = su[k];
                for (k=0;k<n;k++) v[k][j] = sv[k];
            }
        } while (inc > 1);
        for (k=0;k<n;k++) { // Flip signs.
            s=0;
            for (i=0;i<m;i++) if (u[i + k * m] < 0.) s++;
            for (j=0;j<n;j++) if (v[j][k] < 0.) s++;
            if (s > (m+n)/2) {
                for (i=0;i<m;i++) u[i + k * m] = -u[i + k * m];
                for (j=0;j<n;j++) v[j][k] = -v[j][k];
            }
        }
    }
}