// Copyright (C) 2012 - Michael Baudin
//
// This file must be used under the terms of the GNU Lesser General Public License license :
// http://www.gnu.org/copyleft/lesser.html

#include <math.h>
#include <stdio.h>

#include "cdflib.h" 
#include "cdflib_private.h" 

// Maximum value of x
static double CDFLIB_HYGEXMAX=9007199254740991;

// Computes log(binom{n}{k})
double cdflib_nchooseklog (double n, double k);

int cdflib_hygecdflowertail(double x, double M, double k, double N, double *p);

// Computes the hypergeometric PDF
int cdflib_hygepdf(double x, double M, double k, double N, double *p)
{
	double y1;
	double y2;
	double y3;
	double pr;
	double qr;
	int status;
	//
	// Check arguments
	//
	status=cdflib_hygeCheckX("cdflib_hygepdf",x, M, k, N);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	status=cdflib_hygeCheckParams("cdflib_hygepdf",M, k, N);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	//
	if (N-x<=M-k)
	{
		pr=N/M;
		qr=(M-N)/M;
		y1=cdflib_binopdfraw(x,k,pr,qr, CDFLIB_LOGNOT);
		y2=cdflib_binopdfraw(N-x,M-k,pr,qr, CDFLIB_LOGNOT);
		y3=cdflib_binopdfraw(N,M,pr,qr, CDFLIB_LOGNOT);
		*p=y1*y2/y3;
	}
	else
	{
		*p = 0.0;
	}
	return ( CDFLIB_OK );
}
int cdflib_hygeCheckX(char * fname, double x, double M, double k, double N)
{
	int status;
	char buffer [1024];
	// X has integer value
	status=cdflib_checkIntValue(fname, x, "x");
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	// Check that x is not too huge
	// We must have x-1<x
	if (x>CDFLIB_HYGEXMAX)
	{
		sprintf (buffer, "%s: Wrong value for input argument ""%s"": Must be lower than %e.\n",fname,"x",CDFLIB_HYGEXMAX);
		cdflib_messageprint(buffer);
		return ( CDFLIB_ERROR );
	}
	// x in {0,1,...,N}
	status=cdflib_checkrangedouble(fname, x, "x", 0, N);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	// x in {0,1,...,k}
	status=cdflib_checkrangedouble(fname, x, "x", 0, k);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	return ( CDFLIB_OK );
}

int cdflib_hygeCheckParams(char * fname, double M, double k, double N)
{
	int status;
	// M has integer value
	status=cdflib_checkIntValue(fname, M, "M");
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	// k has integer value
	status=cdflib_checkIntValue(fname, k, "k");
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	// N has integer value
	status=cdflib_checkIntValue(fname, N, "N");
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	// M>=0
	status=cdflib_checkgreqthan("cdflib_hygepdf", M, "M", 0);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	// N>=0
	status=cdflib_checkgreqthan("cdflib_hygepdf", N, "N", 0);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	// k<=M
	status=cdflib_checkloweqthan("cdflib_hygepdf", k, "k", M);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	// N<=M
	status=cdflib_checkloweqthan("cdflib_hygepdf", N, "N", M);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	return ( CDFLIB_OK );
}


// Computes the hypergeometric CDF
int cdflib_hygecdf(double x, double M, double k, double N, int lowertail, double *p)
{
	char buffer [1024];
	int flag;
	double xmode;
	int status;
	//
	// Check arguments
	//
	status=cdflib_hygeCheckX("cdflib_hygecdf",x, M, k, N);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	status=cdflib_hygeCheckParams("cdflib_hygecdf",M, k, N);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	status=cdflib_checklowertail("cdflib_hygecdf", lowertail);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	//
	// The mode of the Hypergeometric 
	// distribution: it has maximum probability.
	xmode = floor((N+1)*(k+1)/(M+2));
	// Arrange the integration to start 
	// with the largest probability, and to 
	// end with lowest probabilities. 
	if (x<xmode)
	{
		// Starting summing with x, decreasing x 
		// until the contributions are zero. 
		flag=cdflib_hygecdflowertail(x,M,k,N,p);
		if (flag==CDFLIB_ERROR)
		{
			sprintf (buffer, "%s: Cannot evaluate hypergeometric PDF.\n","cdflib_hygecdflowertail");
			cdflib_messageprint(buffer);
			return ( CDFLIB_ERROR );
		}
	}
	else
	{
		// Starting summing with x+1, increasing x 
		// until the contributions are zero. 
		// We compute the complementary probability: 
		// change lower_tail to manage this.
		if (lowertail==CDFLIB_LOWERTAIL)
		{
			lowertail=CDFLIB_UPPERTAIL;
		}
		else
		{
			lowertail=CDFLIB_LOWERTAIL;
		}
		if (x==N)
		{
			*p=0;
		}
		else
		{
			// Switch the tails to re-use the code
			flag=cdflib_hygecdflowertail(N-x-1,M,M-k,N,p);
			if (flag==CDFLIB_ERROR)
			{
				sprintf (buffer, "%s: Cannot evaluate hypergeometric PDF.\n","cdflib_hygecdflowertail");
				cdflib_messageprint(buffer);
				return ( CDFLIB_ERROR );
			}
		}
	}
	if (lowertail==CDFLIB_UPPERTAIL)
	{
		// Note: there is no loss of accuracy here:
		// the tail has been switched previously for this 
		// specific purpose.
		*p=1.-(*p);
	}
	return ( CDFLIB_OK );
}

// Computes the lower tail of the hypergeometric CDF.
int cdflib_hygecdflowertail(double x, double M, double k, double N, double *p)
{	
	// This function assume that x < xmode, where 
	// xmode=floor((N+1)*(k+1)/(M+2)) is the mode of the 
	// Hypergeometric distribution.
	// Remember that the mode is the x for which f(x) is 
	// maximum.
	//
	// The algorithm sums starting from x, decreasing x 
	// until the contributions are small compared to the 
	// sum. 
	//
	// This function manages two issues:
	// * accuracy: computing only the lower tail is accurate, 
	//   because the probabilities are computed in decreasing 
	//   order. Hence, the most significant part of the 
	//   result are computed first.
	// * performance: this minimizes the number of loops, 
	//   because only the minimum number of iterations is 
	//   performed. Moreover, only one single call to the PDF 
	//   is done: the other computations are performed with 
	//   a reccurence formula. 
	//
	// References
	// http://www.boost.org/doc/libs/1_48_0/boost/math/distributions/detail/hypergeometric_cdf.hpp
	// http://svn.r-project.org/R/trunk/src/nmath/phyper.c
	//

	char buffer [1024];
	double xmin;
	double px;
	double atol = 1.e-16;
	double i;
	int flag;

	if (N-M+k>0)
	{
		xmin = N-M+k;
	}
	else
	{
		xmin = 0;
	}
	flag=cdflib_hygepdf(x,M,k,N,&px);
	if (flag==CDFLIB_ERROR)
	{
		sprintf (buffer, "%s: Cannot evaluate hypergeometric PDF.\n","cdflib_hygecdflowertail");
		cdflib_messageprint(buffer);
		return ( CDFLIB_ERROR );
	}
	*p=0;
	if (px==0)
	{
		return ( CDFLIB_OK );
	}
	i=x;
	while (1)
	{
		*p=*p+px;
		if (i<=xmin)
		{
			break;
		}
		px=i*(M-N-k+i)*px/(N-i+1)/(k-i+1);
		if (px<atol*(*p))
		{
			break;
		}
		i=i-1;
	}
	return ( CDFLIB_OK );
}

// Computes the inverse hypergeometric CDF
int cdflib_hygeinv(double p, double M, double k, double N, int lowertail, double *x)
{
	char buffer [1024];
	double px;
	double xmax;
	double L, R;
	double xmin;
	int flag;
	int status;
	int iteration;
	//
	// Check arguments
	//
	status=cdflib_hygeCheckParams("cdflib_hygeinv", M, k, N);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	status=cdflib_checkrangedouble("cdflib_hygeinv", p, "p", 0., 1.);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	status=cdflib_checklowertail("cdflib_hygeinv", lowertail);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	//
	// Special cases
	//
	xmin=0;
	if (N>k)
	{
		xmax=k;
	}
	else
	{
		xmax=N;
	}
	if (p==0.)
	{
		if (lowertail==CDFLIB_LOWERTAIL)
		{
			*x=xmin;
		}
		else
		{
			*x=xmax;
		}
		return (CDFLIB_OK);
	}
	else if (p==1.)
	{
		if (lowertail==CDFLIB_LOWERTAIL)
		{
			*x=xmax;
		}
		else
		{
			*x=xmin;
		}
		return (CDFLIB_OK);
	}
	//
	// 1. x is minimum
	//
	*x=xmin;
	flag = cdflib_hygecdf(*x, M, k, N, lowertail, &px);
	if (flag==CDFLIB_ERROR)
	{
		sprintf (buffer, "%s: Cannot evaluate hypergeometric CDF.\n","cdflib_hygeinv");
		cdflib_messageprint(buffer);
		return ( CDFLIB_ERROR );
	}
	if (lowertail==CDFLIB_LOWERTAIL)
	{
		if (p<px)
		{
			return (CDFLIB_OK);
		}
	}
	else
	{
		if (p>px)
		{
			return (CDFLIB_OK);
		}
	}
	//
	// 2. x is maximum
	//
	*x=xmax;
	flag = cdflib_hygecdf(*x, M, k, N, lowertail, &px);
	if (flag==CDFLIB_ERROR)
	{
		sprintf (buffer, "%s: Cannot evaluate hypergeometric CDF.\n","cdflib_hygeinv");
		cdflib_messageprint(buffer);
		return ( CDFLIB_ERROR );
	}
	if (lowertail==CDFLIB_LOWERTAIL)
	{
		if (px<p)
		{
			return (CDFLIB_OK);
		}
	}
	else
	{
		if (px>p)
		{
			return (CDFLIB_OK);
		}
	}
	//
	// 3. x is intermediate
	//
	// A binary search.
	// At each step, we have:
	// If lowertail is true, cdf(L)<p<=cdf(R).
	// If lowertail is true, cdf(L)>p>=cdf(R).
	L=0;
	R=xmax;
	iteration=0;
	while (L<R-1)
	{
		*x=floor((L+R)/2);
		flag = cdflib_hygecdf(*x, M, k, N, lowertail, &px);
		if (flag==CDFLIB_ERROR)
		{
			sprintf (buffer, "%s: Cannot evaluate hypergeometric CDF.\n","cdflib_hygeinv");
			cdflib_messageprint(buffer);
			return ( CDFLIB_ERROR );
		}
		if (lowertail==CDFLIB_LOWERTAIL)
		{
			if (p<=px)
			{
				R=*x;
			}
			else
			{
				L=*x;
			}
		}
		else
		{
			if (p>=px)
			{
				R=*x;
			}
			else
			{
				L=*x;
			}
		}
		iteration=iteration+1;
	}
	cdflib_printiter("cdflib_hygeinv", iteration);
	*x=R;
	return ( CDFLIB_OK );
}


// Computes log(binom{n}{k})
double cdflib_nchooseklog_old (double n, double k )
{
	double b;
	b = incgam_loggam(n+1) - incgam_loggam(k+1) - incgam_loggam(n-k+1);
	return b;
}

// Uses the formula 
// nchoosek(n,k)=1/(n+1)/beta(k+1,n-k+1)
double cdflib_nchooseklog (double n, double k )
{
	double t;
	double r;
	t = cdflib_betaln(k+1, n-k+1);
	r = -log(n+1)-t;
	return r;
}

int cdflib_hygernd (double M, double k, double N, double *x)
{
	double U;
	int status;
	status=cdflib_hygeCheckParams("cdflib_hygernd",M, k, N);
	if (status!=CDFLIB_OK)
	{
		*x=cdflib_nan();
		return status;
	}
	U = cdflib_randgenerate();
	status=cdflib_hygeinv(U, M, k, N, CDFLIB_LOWERTAIL, x);
	return status;
}
