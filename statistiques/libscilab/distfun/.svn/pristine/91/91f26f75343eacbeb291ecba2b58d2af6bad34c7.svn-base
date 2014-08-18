// Copyright (C) 2012 - 2014 - Michael Baudin
//
// This file must be used under the terms of the GNU Lesser General Public License license :
// http://www.gnu.org/copyleft/lesser.html

#include "cdflib.h"
#include "cdflib_private.h" 
#include "brent.h"
#include <math.h>       /* pow */

// Noncentral Chi-Squared

int cdflib_chnCheckParams(char * fname, double df, double pnonc)
{
	int status;
	/*     DF > 0 */
	status = cdflib_checkgreaterthan(fname, df, "df", 0.);
	if (status!=CDFLIB_OK)
	{
		return status;
	}

	/*     PNONC >= 0 */
	status = cdflib_checkgreqthan(fname, pnonc, "pnonc", 0.);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	/*     PNONC <= 10000 */
	status = cdflib_checkloweqthan(fname, pnonc, "pnonc", 10000.);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	return CDFLIB_OK;
}

int cdflib_chnCheckX(char * fname, double x)
{
	/*     X >=0 */
	int status;
	status=cdflib_checkgreqthan(fname, x, "x", 0.);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	return CDFLIB_OK;
}
int cdflib_chncdf(double x, double df, double pnonc, int lowertail, double *p)
{
	int status;
	double q;
	double infinite = cdflib_infinite();
	// Check x
	status=cdflib_chnCheckX("cdflib_chncdf",x);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	// Check DF, PNONC
	status = cdflib_chnCheckParams("cdflib_chncdf",df, pnonc);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	/*     Calculating P and Q */
	if (cdflib_isnan(x) || cdflib_isnan(df) || cdflib_isnan(pnonc))
	{
		*p = x+df+pnonc;
		q = x+df+pnonc;
		status = CDFLIB_OK;
		return status;
	}
	if (x==infinite)
	{
		if (lowertail==CDFLIB_LOWERTAIL)
		{
			*p = 1;
		}
		else
		{
			*p = 0;
		}
		status = CDFLIB_OK;
		return status;
	}
	cdflib_cumchn(x, df, pnonc, p, &q, &status);
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
int cdflib_chninv(double p, double df, double pnonc, int lowertail, double *x)
{
	int status;
	double fx;
	double cum, ccum;
	double atol = cdflib_doubleTiny();
	double infinite = cdflib_infinite();
	double q;
	double b;
	int iteration;
	int inversionlabel;

	// P in [0,1]
	status=cdflib_checkp("cdflib_chninv", p, "p");
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	// Check DF, PNONC
	status = cdflib_chnCheckParams("cdflib_chninv",df, pnonc);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	status = CDFLIB_OK;
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
	if (cdflib_isnan(p) || cdflib_isnan(q) || cdflib_isnan(df) || cdflib_isnan(pnonc))
	{
		*x = p+q+df+pnonc;
		status = CDFLIB_OK;
		return status;
	}
	if (q==0)
	{
		*x=infinite;
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
		cdflib_cumchn(b, df, pnonc, &cum, &ccum, &status);
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
	*x = 0.;
	inversionlabel = 0;
	while (1)
	{
		zero_rc ( 0., b, atol, x, &inversionlabel, fx );
		if (inversionlabel<0) 
		{
			break;
		}
		cdflib_cumchn(*x, df, pnonc, &cum, &ccum, &status);
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
		cdflib_unabletoinvert("cdflib_chninv", *x, "x");
		status = CDFLIB_ERROR;
	}
	cdflib_printiter("cdflib_chninv", iteration);
	return status;
}
// Uses numerical derivatives
// References
// Numerical Derivatives in Scilab, Michael Baudin, May 2009
int cdflib_chnpdf(double x, double df, double pnonc, double *y)
{
	int status;
	double infinite = cdflib_infinite();
	double eps = cdflib_doubleEps();
	double fp, fm, fc; // CDF
	double cfp, cfm; // Complementary CDF
	double h;
	double ccum; 
	double inf = cdflib_infinite();
	// Check x
	status=cdflib_chnCheckX("cdflib_chnpdf",x);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	// Check DF, PNONC
	status = cdflib_chnCheckParams("cdflib_chnpdf",df, pnonc);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	/*     Calculating P and Q */
	if (cdflib_isnan(x) || cdflib_isnan(df) || cdflib_isnan(pnonc))
	{
		*y = x+df+pnonc;
		status = CDFLIB_OK;
		return status;
	}
	if (x==inf)
	{
		*y = 0;
		status = CDFLIB_OK;
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
		cdflib_cumchn(x+2*h, df, pnonc, &fp, &ccum, &status);
		if (status==CDFLIB_ERROR)
		{
			return status;
		}
		cdflib_cumchn(x+h, df, pnonc, &fc, &ccum, &status);
		if (status==CDFLIB_ERROR)
		{
			return status;
		}
		cdflib_cumchn(x, df, pnonc, &fm, &ccum, &status);
		if (status==CDFLIB_ERROR)
		{
			return status;
		}
		*y=(-fp+4*fc-3*fm)/(2*h);
	}
	else
	{
		// fp = (f(x+h) - f(x-h))/(2*h)
		cdflib_cumchn(x+h, df, pnonc, &fp, &cfp, &status);
		if (status==CDFLIB_ERROR)
		{
			return status;
		}
		cdflib_cumchn(x-h, df, pnonc, &fm, &cfm, &status);
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
	return status;
}

void cdflib_cumchn(double x, double df, double pnonc, double *cum, double *ccum, int * status)
{
	/* Initialized data */
	int ntired = 1000;
	double eps = cdflib_doubleEps();
	double d1;
	int i__;
	double wt, adj, sum, dfd2, term, chid2, lfact;
	int icent, iterb;
	double pcent;
	int iterf;
	double xnonc, pterm;
	double centaj;
	double lcntaj, sumadj, centwt, lcntwt;
    /*                              Variables */
    /*     EPS     --- Convergence criterion.  The sum stops when a */
    /*                 term is less than EPS*SUM. */
    /*     NTIRED  --- Maximum number of terms to be evaluated */
    /*                 in each sum. */
    /*     QCONV   --- .TRUE. if convergence achieved - */
    /*                 i.e., program did not stop on NTIRED criterion. */
    /*     CCUM <-- Compliment of Cumulative non-central */
    /*              chi-square distribution. */
	if (! (x <= 0.)) {
		goto L10;
	}
	*cum = 0.;
	*ccum = 1.;
	*status=CDFLIB_OK;
	return;
L10:
	if (! (pnonc <= 1e-10)) {
		goto L20;
	}
	/*     When non-centrality parameter is (essentially) zero, */
	/*     use cumulative chi-square distribution */
	cdflib_cumchi(x, df, cum, ccum, status);
	return;
L20:
	xnonc = pnonc / 2.;
	/* ********************************************************************** */
	/*     The following code calcualtes the weight, chi-square, and */
	/*     adjustment term for the central term in the infinite series. */
	/*     The central term is the one in which the poisson weight is */
	/*     greatest.  The adjustment term is the amount that must */
	/*     be subtracted from the chi-square to move up two degrees */
	/*     of freedom. */
	/* ********************************************************************** */
	icent = (int) xnonc;
	if (icent == 0) {
		icent = 1;
	}
	chid2 = x / 2.;
	/*     Calculate central weight term */
	d1 = (double) (icent + 1);
	lfact = incgam_loggam(d1);
	lcntwt = -xnonc + icent * log(xnonc) - lfact;
	centwt = exp(lcntwt);
	/*     Calculate central chi-square */
	d1 = df + 2. * (double) icent;
	cdflib_cumchi(x, d1, &pcent, ccum, status);
	if (*status==CDFLIB_ERROR)
	{
		return;
	}
	/*     Calculate central adjustment term */
	dfd2 = (df + 2. * (double) icent) / 2.;
	d1 = dfd2 + 1.;
	lfact = incgam_loggam(d1);
	lcntaj = dfd2 * log(chid2) - chid2 - lfact;
	centaj = exp(lcntaj);
	sum = centwt * pcent;
	/* ********************************************************************** */
	/*     Sum backwards from the central term towards zero. */
	/*     Quit whenever either */
	/*     (1) the zero term is reached, or */
	/*     (2) the term gets small relative to the sum, or */
	/*     (3) More than NTIRED terms are totaled. */
	/* ********************************************************************** */
	iterb = 0;
	sumadj = 0.;
	adj = centaj;
	wt = centwt;
	i__ = icent;
	goto L40;
L30:
	if (iterb > ntired || (sum < 1e-20 || term < eps * sum) || i__ == 0) {
		goto L50;
	}
L40:
	dfd2 = (df + 2. * (double) i__) / 2.;
	/*     Adjust chi-square for two fewer degrees of freedom. */
	/*     The adjusted value ends up in PTERM. */
	adj = adj * dfd2 / chid2;
	sumadj += adj;
	pterm = pcent + sumadj;
	/*     Adjust poisson weight for J decreased by one */
	wt *= i__ / xnonc;
	term = wt * pterm;
	sum += term;
	--i__;
	++iterb;
	goto L30;
L50:
	iterf = 0;
	/* ********************************************************************** */
	/*     Now sum forward from the central term towards infinity. */
	/*     Quit when either */
	/*     (1) the term gets small relative to the sum, or */
	/*     (2) More than NTIRED terms are totaled. */
	/* ********************************************************************** */
	sumadj = centaj;
	adj = centaj;
	wt = centwt;
	i__ = icent;
	goto L70;
L60:
	if (iterf > ntired || (sum < 1e-20 || term < eps * sum)) {
		goto L80;
	}
	/*     Update weights for next higher J */
L70:
	wt *= xnonc / (i__ + 1);
	/*     Calculate PTERM and add term to sum */
	pterm = pcent - sumadj;
	term = wt * pterm;
	sum += term;
	/*     Update adjustment term for DF for next iteration */
	++i__;
	dfd2 = (df + 2. * (double) i__) / 2.;
	adj = adj * chid2 / dfd2;
	sumadj += adj;
	++iterf;
	goto L60;
L80:
	*cum = sum;
	*ccum = .5 - *cum + .5;
	*status=CDFLIB_OK;
	return;
}

int cdflib_chnrnd(double df, double pnonc, double *x)
{
	double d__1, d__2;
	int status;

	// Check DF, PNONC
	status = cdflib_chnCheckParams("cdflib_chnrnd",df, pnonc);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	status=CDFLIB_OK;
	if (df >= 1.) {
		d__1 = (df - 1.) / 2.;
		/* Computing 2nd power */
		d__2 = cdflib_snorm() + sqrt(pnonc);
		*x = cdflib_sgamma(d__1) * 2. + d__2 * d__2;
	}
	else
	{
		/* Computing 2nd power */
		d__1 = cdflib_snorm() + sqrt(pnonc);
		*x = d__1 * d__1;
	}
	return status;
}

