// Copyright (C) 2014 - Michael Baudin
//
// This file must be used under the terms of the GNU Lesser General Public License license :
// http://www.gnu.org/copyleft/lesser.html

#include <stdio.h>
#include <math.h>
#include <stdlib.h>

#include "cdflib.h"
#include "cdflib_private.h" 
#include "brent.h"

void mMultiply(double *A,double *B,double *C,int m);
void mPower(double *A,int eA,double *V,int *eV,int m,int n);
int cdflib_cumks(double x, int n, double *p, double *q);
int cdflib_kscomp (const void * elem1, const void * elem2) ;
int cdflib_ksrnd_raw(int n, double *x);

int cdflib_ksCheckParams(char * fname, int n)
{
	int status;
	/*     n >= 0 */
	status=cdflib_checkgreaterthan(fname, n, "n", 0.);
	if (status!=CDFLIB_OK)
	{
		return status;
	}

	return CDFLIB_OK;
}
int cdflib_ksCheckX(char * fname, double x)
{
	int status;
	/*     X in [0,1] */
	status=cdflib_checkgreqthan(fname, x, "x", 0.);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	status=cdflib_checkloweqthan(fname, x, "x", 1.);
	if (status!=CDFLIB_OK)
	{
		return status;
	}	
	return CDFLIB_OK;
}

int cdflib_kscdf(double x, int n, int lowertail, double *p)
{
	int status;
	double q;

	// Check X
	status=cdflib_ksCheckX("cdflib_kscdf",x);
	if (status!=CDFLIB_OK)
	{
		return status;
	}

	// Check n
	status=cdflib_ksCheckParams("cdflib_kscdf",n);
	if (status!=CDFLIB_OK)
	{
		return status;
	}

	if (cdflib_isnan(x) )
	{
		*p = x;
		status = CDFLIB_OK;
		return status;
	}
	status=cdflib_cumks(x, n, p, &q);
	if (status==CDFLIB_ERROR)
	{
		return status;
	}
	status = CDFLIB_OK;
	if (lowertail==CDFLIB_UPPERTAIL)
	{
		*p=q;
	}
	return status;
}

int cdflib_ksinv(double p, int n, int lowertail, double *x)
{
	int status;
	double q;

	double fx;
	double cum, ccum;

	double rtol = cdflib_doubleEps();
	int iteration;
	int inversionlabel;

	// P in [0,1]
	status=cdflib_checkp("cdflib_ksinv", p, "p");
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	// Check n
	status=cdflib_ksCheckParams("cdflib_ksinv",n);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	// Compute the couple (p,q) from (p,lowertail)
	if (lowertail==CDFLIB_LOWERTAIL)
	{
		q=1-p;
	}
	else
	{
		q=p;
		p=1-q;
	}
	/*     Calculating X */
	if (cdflib_isnan(p) || cdflib_isnan(q) || cdflib_isnan(n) )
	{
		*x = p+q;
		status = CDFLIB_OK;
		return status;
	}
	if (p==0)
	{
		*x = 0;
		status = CDFLIB_OK;
		return status;
	}
	//
	*x = 0.;
	fx=0.; // This prevents an unnecessary runtime warning.
	inversionlabel = 0;
	iteration=0;
	while (1)
	{
	    zero_rc ( 0., 1., rtol, x, &inversionlabel, fx );
		if (inversionlabel<0) 
		{
			break;
		}
		status=cdflib_cumks(*x, n, &cum, &ccum);
		if (status==CDFLIB_ERROR)
		{
			return status;
		}
		fx = cdflib_computefx(p, q, cum, ccum);
		if (inversionlabel==0) 
		{
			break;
		}
		iteration=iteration+1;
	}
	if (inversionlabel == 0) 
	{
		status = CDFLIB_OK;
	}
	else
	{
		cdflib_unabletoinvert("cdflib_ksinv", *x, "x");
		status = CDFLIB_ERROR;
	}
	cdflib_printiter("cdflib_ksinv", iteration);
	return status;
}

// Reference
// Evaluating Kolmogorov’s Distribution
// George Marsaglia, The Florida State University
// Wai Wan Tsang, Jingbo Wang, The University of Hong Kong
// Journal of Statistical Software
// Vol. 8, Issue 18, Nov 2003
// Submitted 2003-11-05, Accepted 2003-11-10

void mMultiply(double *A,double *B,double *C,int m)
{ 
	int i,j,k; double s;
	for(i=0;i<m;i++) {
		for(j=0; j<m; j++)
		{
			s=0.; 
			for(k=0;k<m;k++) 
			{
				s+=A[i*m+k]*B[k*m+j]; 
			}
			C[i*m+j]=s;
		}
	}
}
void mPower(double *A,int eA,double *V,int *eV,int m,int n)
{ 
	double *B;int eB,i;
	if(n==1) 
	{
		for(i=0;i<m*m;i++) 
		{
			V[i]=A[i];
		}
		*eV=eA; 
		return;
	}
	mPower(A,eA,V,eV,m,n/2);
	B=(double*)malloc((m*m)*sizeof(double));
	mMultiply(V,V,B,m); eB=2*(*eV);
	if(n%2==0)
	{
		for(i=0;i<m*m;i++) 
		{
			V[i]=B[i]; 
		}
		*eV=eB;
	}
	else 
	{
		mMultiply(A,B,V,m); 
		*eV=eA+eB;
	}
	if(V[(m/2)*m+(m/2)]>1e140) 
	{
		for(i=0;i<m*m;i++) 
			{
				V[i]=V[i]*1e-140;
		}
		*eV+=140;
	}
	free(B);
}
// Computes K(n, d) = Pr(Dn < d)
int cdflib_cumks(double d, int n, double *p, double *q)
{ 
	int k,m,i,j,g,eH,eQ;
	double h,s,*H,*Q;
	int status;
	if (d==0.)
	{
		*p=0.;
		*q=1.;
		status = CDFLIB_OK;
		return status;
	}
	s=d*d*n; 
	if(s>7.24||(s>3.76 && n>99)) {
		*p=1-2*exp(-(2.000071+.331/sqrt((double) n)+1.409/n)*s);
		*q=1-*p;
		status = CDFLIB_OK;
		return status;
	}
	k=(int)(n*d)+1; 
	m=2*k-1; 
	h=k-n*d;
	H=(double*)malloc((m*m)*sizeof(double));
	if (H==NULL)
	{
		*p=cdflib_nan();
		*q=1-*p;
		status = CDFLIB_ERROR;
		return status;
	}
	Q=(double*)malloc((m*m)*sizeof(double));
	if (Q==NULL)
	{
		*p=cdflib_nan();
		*q=1-*p;
		status = CDFLIB_ERROR;
		return status;
	}
	for(i=0;i<m;i++) 
	{
		for(j=0;j<m;j++) 
		{
			if(i-j+1<0) 
			{
				H[i*m+j]=0; 
			}
			else
			{
				H[i*m+j]=1;
			}
		}
	}
	for(i=0;i<m;i++) 
	{
		H[i*m]-=pow(h,i+1); 
		H[(m-1)*m+i]-=pow(h,(m-i));
	}
	H[(m-1)*m]+=(2*h-1>0?pow(2*h-1,m):0);
	for(i=0;i<m;i++) 
	{
		for(j=0;j<m;j++) 
		{
			if(i-j+1>0) 
			{
				for(g=1;g<=i-j+1;g++) 
				{
					H[i*m+j]/=g;
				}
			}
		}
	}
	eH=0; 
	mPower(H,eH,Q,&eQ,m,n);
	s=Q[(k-1)*m+k-1];
	for(i=1;i<=n;i++) 
	{
		s=s*i/n; 
		if(s<1e-140) 
		{
			s*=1e140;
			eQ-=140;
		}
	}
	s*=pow(10.,eQ); 
	free(H); 
	free(Q); 
	//
	*p=s;
	*q=1-*p;  // TODO : is there better ?
	status = CDFLIB_OK;
	return status;
}

int cdflib_ksrnd(int n, double *x)
{
	int status;
	status = cdflib_ksCheckParams("cdflib_ksrnd", n);
	if (status!=CDFLIB_OK)
	{
		return ( status );
	}
	status=cdflib_ksrnd_raw(n, x);
	// Is inversion faster for large n ? No.
	// // Generate KS random numbers by inversion.
	// u=cdflib_randgenerate();
	// status=cdflib_ksinv(u, n, CDFLIB_LOWERTAIL, x);
	return status;
}

// Compares two doubles (for qsort)
int cdflib_kscomp (const void * elem1, const void * elem2) 
{
    double f = *((double*)elem1);
    double s = *((double*)elem2);
    if (f > s) return  1;
    if (f < s) return -1;
    return 0;
}

// Generate KS random numbers using the statistics.
int cdflib_ksrnd_raw(int n, double *x)
{
	double * u = NULL;
	double dnminus;
	double dnplus;
	double xn;
	int i;
	int status;
	// Fill an array of uniform random numbers
	u=(double *) malloc(n * sizeof(double));
	if (u==NULL)
	{
		*x=cdflib_nan();
		status = CDFLIB_ERROR;
		return status;
	}
	for ( i=0 ; i < n; i++)
	{
		u[i] = cdflib_randgenerate();
	}
	// Sort them
	qsort (u, n, sizeof(double), cdflib_kscomp);
	// Compute D_n
	*x=0;
	xn = (double) n;
	for ( i=0 ; i < n; i++)
	{
		dnminus=u[i]-i/xn;
		dnplus=(i+1)/xn-u[i];
		if (*x<dnminus)
		{
			*x=dnminus;
		}
		if (*x<dnplus)
		{
			*x=dnplus;
		}
	}
	// Free Memory
	free(u);
	status = CDFLIB_OK;
	return status;
}

int cdflib_kspdf(double x, int n, double *y)
{
	int status;
	double eps = cdflib_doubleEps();
	double fp, fm; // CDF
	double cfp, cfm; // Complementary CDF
	double h;
	double ccum;
	double fc;
	// Check x
	status=cdflib_ksCheckX("cdflib_kspdf", x);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	// Check n
	status=cdflib_ksCheckParams("cdflib_kspdf", n);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	// Use finite differences
	// Use order 2 centered difference, if possible
	h = pow(eps,1./3.);
	if (x-h<=0)
	{
		// Can't use centered difference.
		// Use double forward 3 points formula
		// fp = (-f(x + 2h) + 4f(x + h) - 3f(x))/(2*h)
		status=cdflib_cumks(x+2*h, n, &fp, &ccum);
		if (status==CDFLIB_ERROR)
		{
			return status;
		}
		status=cdflib_cumks(x+h, n, &fc, &ccum);
		if (status==CDFLIB_ERROR)
		{
			return status;
		}
		status=cdflib_cumks(x, n, &fm, &ccum);
		if (status==CDFLIB_ERROR)
		{
			return status;
		}
		*y=(-fp+4*fc-3*fm)/(2*h);
	}
	else
	{
		// fp = (f(x+h) - f(x-h))/(2*h)
		status = cdflib_cumks(x+h, n, &fp, &cfp);
		if (status==CDFLIB_ERROR)
		{
			return status;
		}
		status=cdflib_cumks(x-h, n, &fm, &cfm);
		if (status==CDFLIB_ERROR)
		{
			return status;
		}
		if (fp>cfp)
		{
			// We are in the upper tail (X>Mode)
			*y=-(cfp-cfm)/(2*h);
		}
		else
		{
			// We are in the lower tail (X>Mode)
			*y=(fp-fm)/(2*h);
		}
	}
	if (*y<0.)
	{
		*y=0.;
	}
	status = CDFLIB_OK;
	return CDFLIB_OK;
}
