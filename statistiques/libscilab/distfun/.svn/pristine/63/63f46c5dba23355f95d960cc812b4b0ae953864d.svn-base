// Copyright (C) 2012 - Michael Baudin
//
// This file must be used under the terms of the GNU Lesser General Public License license :
// http://www.gnu.org/copyleft/lesser.html

#include <math.h>

#include "cdflib.h"
#include "cdflib_private.h" 
#include "brent.h"

// T Distribution

/* Initalize Approximation to */
/* INVerse of the cumulative T distribution */
double cdflib_dt1(double p, double q, double df);


int cdflib_tCheckX(char * fname, double x)
{
	// Nothing to do.
	return CDFLIB_OK;
}
int cdflib_tCheckParams(char * fname, double v)
{
	int status;
	/*     V > 0 */
	status=cdflib_checkgreaterthan(fname, v, "v", 0.);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	return CDFLIB_OK;
}
int cdflib_tcdf(double x, double v, int lowertail, double *p)
{
	int status;
	double infinite = cdflib_infinite();
	double q;
	status=cdflib_tCheckX("cdflib_tcdf", x);
	if (status!=CDFLIB_OK)
	{
		return status;
	}

	// Check V
	status=cdflib_tCheckParams("cdflib_tcdf", v);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	if (cdflib_isnan(x) || cdflib_isnan(v))
	{
		*p = x+v;
		status = CDFLIB_OK;
		return status;
	}
	if (v==infinite)
	{
		cdflib_cumnor(x, p, &q, &status);
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
	cdflib_cumt(x, v, p, &q, &status);
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
int cdflib_tinv(double p, double v, int lowertail, double *x)
{
	int status;
	double q;
	static double fx;
	static double cum, ccum;
	double rtol = cdflib_doubleEps();
	double infinite = cdflib_infinite();
	double mu=0.;
	double sigma=1.;
	double huge=cdflib_doubleHuge();
	int iteration;
	double atol= cdflib_doubleTiny();
	double a, b;
	int changesign=1;
	int inversionlabel;

	// P in [0,1]
	status=cdflib_checkp("cdflib_tinv", p, "p");
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	// Check V
	status=cdflib_tCheckParams("cdflib_tinv", v);
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
	if ( p == 0. ) 
	{
		*x = -infinite;
		status = CDFLIB_OK;
		return status;
	}
	if ( q == 0. ) 
	{
		*x = infinite;
		status = CDFLIB_OK;
		return status;
	}
	if (cdflib_isnan(p) || cdflib_isnan(v))
	{
		*x = p+v;
		status = CDFLIB_OK;
		return status;
	}
	if (v==infinite)
	{
		if (p<q)
		{
			status = cdflib_norminv(p, mu, sigma, CDFLIB_LOWERTAIL, x);
		}
		else
		{
			status = cdflib_norminv(q, mu, sigma, CDFLIB_UPPERTAIL, x);
		}
		return status;
	}
	//
	// Step A: Find a rough estimate of an interval.
	// If not, this would make a terrible a=-huge, b=huge.
	// This requires too many iterations (more than 500, for typical cases).
	// Compute a "small" b, so that f(b) changes sign.
	// b has the sequence -1.e308, -1.e298, ...,-0.0179,0.0179,1.79e8,1.79e18,etc...
	// We divide by 10 until we reach zero, then we multiply by 10.
	a=-cdflib_doubleHuge();
	b=a/1.e10;
	iteration=0;
	while (1)
	{
		cdflib_cumt(b, v, &cum, &ccum, &status);
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
		a=b;
		if (changesign==1 && fabs(b)<0.1)
		{
			b=-b;
			changesign=0;
		}
		else
		{
			if (b<0)
			{
				b=b/1.e10;
			}
			else
			{
				b=b*1.e10;
			}
		}
		iteration=iteration+1;
	}
	// Step B: Refine this estimate.
	/*     .. Get initial approximation for X */
	*x = cdflib_dt1(p, q, v);
	inversionlabel = 0;
	while (1)
	{
	    zero_rc ( a, b, atol, x, &inversionlabel, fx );
		if (inversionlabel<0) 
		{
			break;
		}
		cdflib_cumt(*x, v, &cum, &ccum, &status);
		if (status==CDFLIB_ERROR)
		{
			return status;
		}
		fx = cdflib_computefx(p, q, cum, ccum);
		if (inversionlabel==0) 
		{
			break;
		}
		iteration = iteration+1;
	}
	if (inversionlabel == 0) 
	{
		status = CDFLIB_OK;
	}
	else
	{
		cdflib_unabletoinvert("cdflib_tinv", *x, "x");
		status = CDFLIB_ERROR;
	} 
	cdflib_printiter("cdflib_tinv", iteration);
	return status;
}

// T PDF
int cdflib_tpdf(double x, double v, double *y)
{
	double lny;
	int status;
	double t;
	double half=0.5;
	double u;
	double halfv;
	double s;
	double inf=cdflib_infinite();
	//
	// Check arguments
	status=cdflib_tCheckX("cdflib_tpdf",x);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	status=cdflib_tCheckParams("cdflib_tpdf",v);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	//
	if (v==inf)
	{
		status=cdflib_normpdf(x, 0., 1., y);
		return status;
	}
	// Computes t = log(1+x^2/v)
	s = x*x/v;
	t = cdflib_log1p(s);
	// Computes u = log(B(v/2,1/2))
	halfv=v/2;
	u = cdflib_betaln(halfv, half);
	lny = - 0.5*(v+1)*t-0.5*log(v)-u;
	*y = exp ( lny );
	return ( CDFLIB_OK );
}

int cdflib_trnd(double v, double *x)
{
	double Z;
	double C;
	int status;
	status=cdflib_tCheckParams("cdflib_trnd",v);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	Z=cdflib_snorm();
	status=cdflib_chi2rnd(v,&C);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	*x=Z/sqrt(C/v);
	return CDFLIB_OK;
}

void cdflib_cumt(double x, double df, double *cum, double *ccum, int *status)
{
	double d1;
	double a, tt, xx, yy, oma, dfptt;
	tt = x * x;
	dfptt = df + tt;
	xx = df / dfptt;
	yy = tt / dfptt;
	d1 = df * .5;
	cdflib_cumbet(xx, yy, d1, .5, &a, &oma, status);
	if (*status==CDFLIB_ERROR)
	{
		return;
	}
	if (x <= 0.) {
		*cum = a * .5;
		*ccum = oma + *cum;
	}
	else
	{
		*ccum = a * .5;
		*cum = oma + *ccum;
	}
	*status=CDFLIB_OK;
	return;
}

double cdflib_dt1(double p, double q, double df)
{
	/* Initialized data */

	static double coef[20]	/* was [5][4] */ = { 1.,1.,0.,0.,0.,3.,16.,5.,
		0.,0.,-15.,17.,19.,3.,0.,-945.,-1920.,1482.,776.,79. };
	static int ideg[4] = { 2,3,4,5 };
	static double denom[4] = { 4.,96.,384.,92160. };

	/* System generated locals */
	double ret_val, d__1;

	/* Local variables */
	int i__;
	double x, xp, xx, sum, term;
	double denpow;

	/* ********************************************************************** */
	/*     DOUBLE PRECISION FUNCTION DT1(P,Q,DF) */
	/*     Double precision Initalize Approximation to */
	/*           INVerse of the cumulative T distribution */
	/*                              Function */
	/*     Returns  the  inverse   of  the T   distribution   function, i.e., */
	/*     the integral from 0 to INVT of the T density is P. This is an */
	/*     initial approximation */
	/*                              Arguments */
	/*     P --> The p-value whose inverse from the T distribution is */
	/*          desired. */
	/*     Q --> 1-P. */
	/*     DF --> Degrees of freedom of the T distribution. */
	/* ********************************************************************** */

	x = (d__1 = cdflib_dinvnr(p, q), fabs(d__1));
	xx = x * x;
	sum = x;
	denpow = 1.;
	for (i__ = 1; i__ <= 4; ++i__) {
		term = cdflib_devlpl(&coef[i__ * 5 - 5], ideg[i__ - 1], &xx) * x;
		denpow *= df;
		sum += term / (denpow * denom[i__ - 1]);
	}
	if (p >= .5) {
		xp = sum;
	}
	else
	{
		xp = -sum;
	}
	ret_val = xp;
	return ret_val;
}

