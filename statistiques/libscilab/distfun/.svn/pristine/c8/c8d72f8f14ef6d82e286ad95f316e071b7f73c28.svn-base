// Copyright (C) 2012 - Michael Baudin
//
// This file must be used under the terms of the GNU Lesser General Public License license :
// http://www.gnu.org/copyleft/lesser.html

#include <math.h>

#include "cdflib.h"
#include "cdflib_private.h" 

// Normal Distribution

int cdflib_norCheckParams(char * fname, double mu, double sigma)
{
	int status;
	// Nothing to do for mu

	/*     SIGMA > 0 */
	status=cdflib_checkgreaterthan(fname, sigma, "sigma", 0.);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	return CDFLIB_OK;
}
int cdflib_norCheckX(char * fname, double x)
{
	// Nothing to do...
	return CDFLIB_OK;
}
int cdflib_normcdf(double x, double mu, double sigma, int lowertail, double *p)
{
	int status;
	static double a;
	double infinite = cdflib_infinite();
	double q;
	// Check X
	status=cdflib_norCheckX("cdflib_normcdf", x);
	if (status!=CDFLIB_OK)
	{
		return status;
	}

	// Check MU, SIGMA
	status=cdflib_norCheckParams("cdflib_normcdf", mu, sigma);
	if (status!=CDFLIB_OK)
	{
		return status;
	}

	/*     Computing P and Q */
	if (x==-infinite)
	{
		if (lowertail==CDFLIB_LOWERTAIL)
		{
			*p=0.;
		}
		else
		{
			*p=1.;
		}
		status = CDFLIB_OK;
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
	if (cdflib_isnan(x) || cdflib_isnan(mu) || cdflib_isnan(sigma))
	{
		*p = x+mu+sigma;
		status = CDFLIB_OK;
		return status;
	}
	a = (x - mu) / sigma;
	cdflib_cumnor(a, p, &q, &status);
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
int cdflib_norminv(double p, double mu, double sigma, int lowertail, double *x)
{
	int status;
	double infinite = cdflib_infinite();
	double q;
	double z;
	// P in [0,1]
	status=cdflib_checkp("cdflib_norminv", p, "p");
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	// Check MU, SIGMA
	status=cdflib_norCheckParams("cdflib_norminv", mu, sigma);
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
	if (q==0)
	{
		*x = infinite;
		status = CDFLIB_OK;
		return status;
	}
	if (p==0)
	{
		*x = -infinite;
		status = CDFLIB_OK;
		return status;
	}
	if (cdflib_isnan(p) || cdflib_isnan(mu) || cdflib_isnan(sigma))
	{
		*x = p+mu+sigma;
		status = CDFLIB_OK;
		return status;
	}
	z = cdflib_dinvnr(p, q);
	*x = sigma * z + mu;
	status = CDFLIB_OK;
	return status;
}

// Normal PDF
int cdflib_normpdf(double x, double mu, double sigma, double *y)
{
	double lny;
	int status;
	// sq2pi = sqrt(2.0*pi)
	double sq2pi = 2.50662827463100020;
	double z;
	//
	// Check arguments
	status=cdflib_norCheckX("cdflib_normpdf",x);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	status=cdflib_norCheckParams("cdflib_normpdf",mu, sigma);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	//
	z = ( x - mu ) / sigma;
	lny = - 0.5 * z * z - log(sigma * sq2pi);
	*y = exp ( lny );
	return ( CDFLIB_OK );
}

int cdflib_normrnd(double mu, double sigma, double *x)
{
	double Z;
	int status;
	status=cdflib_norCheckParams("cdflib_normrnd",mu, sigma);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	Z=cdflib_snorm();
	*x=sigma*Z + mu;
	return CDFLIB_OK;
}

/* ********************************************************************** */
/* Original Comments: */
/* ------------------------------------------------------------------ */
/* This function evaluates the normal distribution function: */
/*                              / x */
/*                     1       |       -t*t/2 */
/*          P(x) = ----------- |      e       dt */
/*                 sqrt(2 pi)  | */
/*                             /-oo */
/*   The main computation evaluates near-minimax approximations */
/*   derived from those in "Rational Chebyshev approximations for */
/*   the error function" by W. J. Cody, Math. Comp., 1969, 631-637. */
/*   This transportable program uses rational functions that */
/*   theoretically approximate the normal distribution function to */
/*   at least 18 significant decimal digits.  The accuracy achieved */
/*   depends on the arithmetic system, the compiler, the intrinsic */
/*   functions, and proper selection of the machine-dependent */
/*   constants. */
/* ******************************************************************* */
/* ******************************************************************* */
/* Explanation of machine-dependent constants. */
/*   MIN   = smallest machine representable number. */
/*   EPS   = argument below which anorm(x) may be represented by */
/*           0.5  and above which  x*x  will not underflow. */
/*           A conservative value is the largest machine number X */
/*           such that   1.0 + X = 1.0   to machine precision. */
/* ******************************************************************* */
/* ******************************************************************* */
/* Error returns */
/*  The program returns  ANORM = 0     for  ARG .LE. XLOW. */
/* Intrinsic functions required are: */
/*     ABS, AINT, EXP */
/*  Author: W. J. Cody */
/*          Mathematics and Computer Science Division */
/*          Argonne National Laboratory */
/*          Argonne, IL 60439 */
/*  Latest modification: March 15, 1992 */
/* ------------------------------------------------------------------ */
void cdflib_cumnor(double x, double *cum, double *ccum, int *status)
{
	/* Initialized data */
	static double one = 1.;
	/* ------------------------------------------------------------------ */
	/*  Coefficients for approximation in first interval */
	/* ------------------------------------------------------------------ */
	static double a[5] = { 2.2352520354606839287,161.02823106855587881,
		1067.6894854603709582,18154.981253343561249,
		.065682337918207449113 };
	static double b[4] = { 47.20258190468824187,976.09855173777669322,
		10260.932208618978205,45507.789335026729956 };
	/* ------------------------------------------------------------------ */
	/*  Coefficients for approximation in second interval */
	/* ------------------------------------------------------------------ */
	static double c__[9] = { .39894151208813466764,8.8831497943883759412,
		93.506656132177855979,597.27027639480026226,2494.5375852903726711,
		6848.1904505362823326,11602.651437647350124,9842.7148383839780218,
		1.0765576773720192317e-8 };
	static double d__[8] = { 22.266688044328115691,235.38790178262499861,
		1519.377599407554805,6485.558298266760755,18615.571640885098091,
		34900.952721145977266,38912.003286093271411,19685.429676859990727 
	};
	/* ------------------------------------------------------------------ */
	/*  Coefficients for approximation in third interval */
	/* ------------------------------------------------------------------ */
	static double p[6] = { .21589853405795699,.1274011611602473639,
		.022235277870649807,.001421619193227893466,2.9112874951168792e-5,
		.02307344176494017303 };
	static double q[5] = { 1.28426009614491121,.468238212480865118,
		.0659881378689285515,.00378239633202758244,7.29751555083966205e-5 
	};
	/* ------------------------------------------------------------------ */
	/*  Mathematical constants */
	/*  SQRPI = 1 / sqrt(2*pi), ROOT32 = sqrt(32), and */
	/*  THRSH is the argument for which anorm = 0.75. */
	/* ------------------------------------------------------------------ */
	static double half = .5;
	static double zero = 0.;
	static double sixten = 1.6;
	static double sqrpi = .39894228040143267794;
	static double thrsh = .66291;
	static double root32 = 5.656854248;
	/* System generated locals */
	double d__1;
	/* Local variables */
	static int i__;
	static double y, del, min__, eps, xsq, xden, temp, xnum;
	/* ------------------------------------------------------------------ */
	/*  Machine dependent constants */
	/* ------------------------------------------------------------------ */
	eps = cdflib_doubleEps() * .5;
	min__ = cdflib_doubleTiny();
	/* ------------------------------------------------------------------ */
	y = fabs(x);
	if (y <= thrsh) {
		/* ------------------------------------------------------------------ */
		/*  Evaluate  anorm  for  |X| <= 0.66291 */
		/* ------------------------------------------------------------------ */
		xsq = zero;
		if (y > eps) {
			xsq = x * x;
		}
		xnum = a[4] * xsq;
		xden = xsq;
		for (i__ = 1; i__ <= 3; ++i__) {
			xnum = (xnum + a[i__ - 1]) * xsq;
			xden = (xden + b[i__ - 1]) * xsq;
		}
		*cum = x * (xnum + a[3]) / (xden + b[3]);
		temp = *cum;
		*cum = half + temp;
		*ccum = half - temp;
		/* ------------------------------------------------------------------ */
		/*  Evaluate  anorm  for 0.66291 <= |X| <= sqrt(32) */
		/* ------------------------------------------------------------------ */
	} else if (y <= root32) {
		xnum = c__[8] * y;
		xden = y;
		for (i__ = 1; i__ <= 7; ++i__) {
			xnum = (xnum + c__[i__ - 1]) * y;
			xden = (xden + d__[i__ - 1]) * y;
		}
		*cum = (xnum + c__[7]) / (xden + d__[7]);
		d__1 = y * sixten;
		xsq = cdflib_dint(d__1) / sixten;
		del = (y - xsq) * (y + xsq);
		*cum = exp(-xsq * xsq * half) * exp(-del * half) * *cum;
		*ccum = one - *cum;
		if (x > zero) {
			temp = *cum;
			*cum = *ccum;
			*ccum = temp;
		}
		/* ------------------------------------------------------------------ */
		/*  Evaluate  anorm  for |X| > sqrt(32) */
		/* ------------------------------------------------------------------ */
	} else {
		*cum = zero;
		xsq = one / (x * x);
		xnum = p[5] * xsq;
		xden = xsq;
		for (i__ = 1; i__ <= 4; ++i__) {
			xnum = (xnum + p[i__ - 1]) * xsq;
			xden = (xden + q[i__ - 1]) * xsq;
		}
		*cum = xsq * (xnum + p[4]) / (xden + q[4]);
		*cum = (sqrpi - *cum) / y;
		d__1 = x * sixten;
		xsq = cdflib_dint(d__1) / sixten;
		del = (x - xsq) * (x + xsq);
		*cum = exp(-xsq * xsq * half) * exp(-del * half) * *cum;
		*ccum = one - *cum;
		if (x > zero) {
			temp = *cum;
			*cum = *ccum;
			*ccum = temp;
		}
	}
	/* ------------------------------------------------------------------ */
	/*  Fix up for negative argument, erf, etc. */
	/* ------------------------------------------------------------------ */
	if (*cum < min__) {
		*cum = 0.;
	}
	if (*ccum < min__) {
		*ccum = 0.;
	}
	*status=CDFLIB_OK;
	return;
}

