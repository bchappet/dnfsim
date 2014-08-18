// Copyright (C) 2012 - Michael Baudin
//
// This file must be used under the terms of the GNU Lesser General Public License license :
// http://www.gnu.org/copyleft/lesser.html

#include <math.h>
#include <stdio.h>

#include "cdflib.h"
#include "cdflib_private.h" 
#include "brent.h"

// Chi-Squared Distribution

int cdflib_chiCheckParams(char * fname, double k)
{
	int status;
	/*     K > 0 */
	// k can be non integer
	status = cdflib_checkgreaterthan(fname, k, "k", 0.);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	return CDFLIB_OK;
}
int cdflib_chiCheckX(char * fname, double x)
{
	int status;
	/*     X >=0 */
	status = cdflib_checkgreqthan(fname, x, "x", 0.);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	return CDFLIB_OK;
}
int cdflib_chi2cdf(double x, double k, int lowertail, double *p)
{
	int status;
	double q;
	double infinite = cdflib_infinite();
	status = cdflib_chiCheckX("cdflib_chi2cdf",x);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	// Check k
	status = cdflib_chiCheckParams("cdflib_chi2cdf",k);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	if (x==infinite)
	{
		if (lowertail==CDFLIB_LOWERTAIL)
		{
			*p=1.;
		}
		else
		{
			*p=0.;
		}
		status = CDFLIB_OK;
		return status;
	}
	if (cdflib_isnan(x) || cdflib_isnan(k))
	{
		*p = x+k;
		status = CDFLIB_OK;
		return status;
	}
	cdflib_cumchi(x, k, p, &q, &status);
	if (status==CDFLIB_ERROR) 
	{
		return status;
	}
	if (lowertail==CDFLIB_UPPERTAIL)
	{
		*p=q;
	}
	status = CDFLIB_OK;
	return status;
}
int cdflib_chi2inv(double p, double k, int lowertail, double *x)
{
	int status;
	double fx;
	double cum, ccum, porq;
	double atol = cdflib_doubleTiny();
	double infinite = cdflib_infinite();
	double q;
	double b;
	int iteration;
	int inversionlabel;

	// P in [0,1]
	status=cdflib_checkp("cdflib_chi2inv", p, "p");
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	// Check k
	status = cdflib_chiCheckParams("cdflib_chi2inv",k);
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

	if (p <= q) 
	{
		porq = p;
	}
	else
	{
		porq = q;
	}

	if ( p == 0. ) 
	{
		*x = 0.;
		status = CDFLIB_OK;
		return status;
	}
	if ( q == 0. ) 
	{
		*x = infinite;
		status = CDFLIB_OK;
		return status;
	}
	if (cdflib_isnan(p) || cdflib_isnan(k))
	{
		*x = p+k;
		status = CDFLIB_OK;
		return status;
	}
	//
	// Step A: Find a rough estimate of an interval.
	// Compute a "small" b, so that f(b) changes sign.
	b=cdflib_doubleTiny();
	iteration=0;
	while (1)
	{
		cdflib_cumchi(b, k, &cum, &ccum, &status);
		if (status==CDFLIB_ERROR)
		{
			return status;
		}
		fx = cdflib_computefx(p, q, cum, ccum);
		if (p <= q && fx>0)
		{
			break;
		}
		if (p > q && fx<0)
		{
			break;
		}
		b=b*1.e10;
		iteration=iteration+1;
	}
	// Step B: Refine this estimate.
	*x=0.;
	inversionlabel = 0;
	while (1)
	{
	    zero_rc ( 0., b, atol, x, &inversionlabel, fx );
		if (inversionlabel<0) 
		{
			break;
		}
		cdflib_cumchi(*x, k, &cum, &ccum, &status);
		if (status==CDFLIB_ERROR)
		{
			return status;
		}
		fx = cdflib_computefx(p, q, cum, ccum);
		if (fx + porq > 1.5) 
		{
			status = CDFLIB_ERROR;
			return status;
		}
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
		cdflib_unabletoinvert("cdflib_chi2inv", *x, "x");
		status = CDFLIB_ERROR;
	}
	cdflib_printiter("cdflib_chi2inv", iteration);
	return status;
}

// Computes the Chi-Square PDF
int cdflib_chi2pdf(double x, double k, double *y)
{
	int status;
	double a;
	a = k/2;
	//
	// Check arguments
	status = cdflib_chiCheckX("cdflib_chi2pdf",x);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	status = cdflib_chiCheckParams("cdflib_chi2pdf",k);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	status = cdflib_gammapdf(x,a,2.,y);
	return ( status );
}

int cdflib_chi2rnd(double k, double *x)
{
	double d__1;
	int status;
	status = cdflib_chiCheckParams("cdflib_chi2rnd",k);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	d__1 = k / 2.;
	*x = cdflib_sgamma(d__1) * 2.;
	return CDFLIB_OK;
}

void cdflib_cumchi(double x, double df, double *cum, double *ccum, int * status)
{
	double a, xx;
	a = df * .5;
	xx = x * .5;
	cdflib_cumgam(xx, a, cum, ccum, status);
	return;
}

