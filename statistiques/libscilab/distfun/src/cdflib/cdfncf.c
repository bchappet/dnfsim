// Copyright (C) 2012 - 2014 - Michael Baudin
//
// This file must be used under the terms of the GNU Lesser General Public License license :
// http://www.gnu.org/copyleft/lesser.html

#include "cdflib.h"
#include "cdflib_private.h" 
#include "brent.h"
#include <math.h>       /* pow */
#include <stdio.h>

// Noncentral F Distribution

int cdflib_ncfCheckX(char * fname, double x)
{
	int status;
	status=cdflib_checkgreqthan(fname, x, "x", 0.);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	return CDFLIB_OK;
}

int cdflib_ncfCheckParams(char * fname, double dfn, double dfd, double phonc)
{
	int status;
	/*     DFN > 0 */
	status=cdflib_checkgreaterthan(fname, dfn, "dfn", 0.);
	if (status!=CDFLIB_OK)
	{
		return status;
	}

	/*     DFD > 0 */
	status=cdflib_checkgreaterthan(fname, dfd, "dfd", 0.);
	if (status!=CDFLIB_OK)
	{
		return status;
	}

	/*     PHONC >= 0 */
	status=cdflib_checkgreqthan(fname, phonc, "phonc", 0.);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	return CDFLIB_OK;
}

int cdflib_ncfpdf(double x, double dfn, double dfd, double phonc, double *y)
{
	int status;
	double infinite = cdflib_infinite();
	double eps = cdflib_doubleEps();
	double fp, fm, fc; // CDF
	double cfp, cfm; // Complementary CDF
	double h;
	double ccum; 
	double inf = cdflib_infinite();

	status=cdflib_ncfCheckX("cdflib_ncfpdf", x);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	// Check DFN, DFD, PHONC
	status=cdflib_ncfCheckParams("cdflib_ncfpdf", dfn, dfd, phonc);
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
		cdflib_cumfnc(x+2*h, dfn, dfd, phonc, &fp, &ccum, &status);
		if (status==CDFLIB_ERROR)
		{
			return status;
		}
		cdflib_cumfnc(x+h, dfn, dfd, phonc, &fc, &ccum, &status);
		if (status==CDFLIB_ERROR)
		{
			return status;
		}
		cdflib_cumfnc(x, dfn, dfd, phonc, &fm, &ccum, &status);
		if (status==CDFLIB_ERROR)
		{
			return status;
		}
		*y=(-fp+4*fc-3*fm)/(2*h);
	}
	else
	{
		// fp = (f(x+h) - f(x-h))/(2*h)
		cdflib_cumfnc(x+h, dfn, dfd, phonc, &fp, &cfp, &status);
		if (status==CDFLIB_ERROR)
		{
			return status;
		}
		cdflib_cumfnc(x-h, dfn, dfd, phonc, &fm, &cfm, &status);
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


int cdflib_ncfcdf(double x, double dfn, double dfd, double phonc, int lowertail, double *p)
{
	double q;
	int status;

	status=cdflib_ncfCheckX("cdflib_ncfcdf", x);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	// Check DFN, DFD, PHONC
	status=cdflib_ncfCheckParams("cdflib_ncfcdf", dfn, dfd, phonc);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	/*     Calculating P and Q */
	if (cdflib_isnan(x) || cdflib_isnan(dfn) || cdflib_isnan(dfd) || cdflib_isnan(phonc))
	{
		*p = x+dfn+dfd+phonc;
		status = CDFLIB_OK;
		return status;
	}
	cdflib_cumfnc(x, dfn, dfd, phonc, p, &q, &status);
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


int cdflib_ncfinv(double p, double dfn, double dfd, double phonc, int lowertail, double *x)
{
	int status;

	static double fx;
	static double cum, ccum;

	double huge = cdflib_doubleHuge();
	double atol = cdflib_doubleTiny();
	double infinite = cdflib_infinite();
	int iteration;
	double b;
	double q;
	int inversionlabel;

	// P in [0,1]
	status=cdflib_checkp("cdflib_ncfinv", p, "p");
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	// Check DFN, DFD, PHONC
	status=cdflib_ncfCheckParams("cdflib_ncfinv", dfn, dfd, phonc);
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
	/*     Calculating F */
	if (cdflib_isnan(p) || cdflib_isnan(dfn) || cdflib_isnan(dfd) || cdflib_isnan(phonc))
	{
		*x = p+dfn+dfd+phonc;
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
		cdflib_cumfnc(b, dfn, dfd, phonc, &cum, &ccum, &status);
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
	inversionlabel = 0;
	while (1)
	{
		zero_rc ( 0., b, atol, x, &inversionlabel, fx );
		if (inversionlabel<0) 
		{
			break;
		}
		cdflib_cumfnc(*x, dfn, dfd, phonc, &cum, &ccum, &status);
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
		cdflib_unabletoinvert("cdflib_ncfinv", *x, "x");
		status = CDFLIB_ERROR;
	}
	cdflib_printiter("cdflib_ncfinv", iteration);
	return status;
}

void cdflib_cumfnc(double x, double dfn, double dfd, 
	double pnonc, double *cum, double *ccum, int *status)
{
	/* Initialized data */
	double eps = cdflib_doubleEps();
	double d1, d2;
	double b;
	int i__;
	double xx, yy, adn, aup, sum;
	int ierr;
	double prod, dsum, betdn;
	int icent;
	double betup, xnonc, dummy, xmult;
	double dnterm, centwt, upterm;
	char buffer [1024];
	/*                              Note */
	/*     THE SUM CONTINUES UNTIL A SUCCEEDING TERM IS LESS THAN EPS */
	/*     TIMES THE SUM (OR THE SUM IS LESS THAN 1.0E-20).  EPS IS */
	/*     SET TO 1.0E-4 IN A DATA STATEMENT WHICH CAN BE CHANGED. */
	if (! (x <= 0.)) {
		goto L10;
	}
	*cum = 0.;
	*ccum = 1.;
	*status=CDFLIB_OK;
	return;
L10:
	if (! (pnonc < 1e-10)) {
		goto L20;
	}
	/*     Handle case in which the non-centrality parameter is */
	/*     (essentially) zero. */
	cdflib_cumf(x, dfn, dfd, cum, ccum, status);
	return;
L20:
	xnonc = pnonc / 2.;
	/*     Calculate the central term of the poisson weighting factor. */
	icent = (int) xnonc;
	if (icent == 0) {
		icent = 1;
	}
	/*     Compute central weight term */
	d1 = (double) (icent + 1);
	centwt = exp(-xnonc + icent * log(xnonc) - incgam_loggam(d1));
	/*     Compute central incomplete beta term */
	/*     Assure that minimum of arg to beta and 1 - arg is computed */
	/*          accurately. */
	prod = dfn * x;
	dsum = dfd + prod;
	yy = dfd / dsum;
	if (yy > .5) {
		xx = prod / dsum;
		yy = 1. - xx;
	} else {
		xx = 1. - yy;
	}
	d1 = dfn * .5 + (double) icent;
	d2 = dfd * .5;
	cdflib_bratio(d1, d2, xx, yy, &betdn, &dummy, &ierr);
	if (ierr!=0)
	{
		*status=CDFLIB_ERROR;
		sprintf (buffer, "%s: Unable to evaluate Incomplete Beta function at a=%e, b=%e, x=%e, y=%e","cdflib_cumfnc",d1,d2,xx,yy);
		cdflib_messageprint(buffer);
		return;
	}
	adn = dfn / 2. + (double) icent;
	aup = adn;
	b = dfd / 2.;
	betup = betdn;
	sum = centwt * betdn;
	/*     Now sum terms backward from icent until convergence or all done */
	xmult = centwt;
	i__ = icent;
	d1 = adn + b;
	d2 = adn + 1.;
	dnterm = exp(incgam_loggam(d1) - incgam_loggam(d2) - incgam_loggam(b) + adn * log(xx)
		+ b * log(yy));
L30:
	d1 = xmult * betdn;
	if (sum < 1e-20 || d1 < eps * sum || i__ <= 0) {
		goto L40;
	}
	xmult *= i__ / xnonc;
	--i__;
	adn += -1;
	dnterm = (adn + 1) / ((adn + b) * xx) * dnterm;
	betdn += dnterm;
	sum += xmult * betdn;
	goto L30;
L40:
	i__ = icent + 1;
	/*     Now sum forwards until convergence */
	xmult = centwt;
	if (aup - 1 + b == 0.) {
		upterm = exp(-incgam_loggam(aup) - incgam_loggam(b) + (aup - 1) * log(xx) + b * 
			log(yy));
	} else {
		d1 = aup - 1 + b;
		upterm = exp(incgam_loggam(d1) - incgam_loggam(aup) - incgam_loggam(b) + (aup - 1) 
			* log(xx) + b * log(yy));
	}
	goto L60;
L50:
	d1 = xmult * betup;
	if (sum < 1e-20 || d1 < eps * sum) {
		goto L70;
	}
L60:
	xmult *= xnonc / i__;
	++i__;
	aup += 1;
	upterm = (aup + b - 2.) * xx / (aup - 1) * upterm;
	betup -= upterm;
	sum += xmult * betup;
	goto L50;
L70:
	*cum = sum;
	*ccum = .5 - *cum + .5;
	*status=CDFLIB_OK;
	return;
}

int cdflib_ncfrnd(double dfn, double dfd, double xnonc, double *x)
{
	double d__1, d__2;

	double xden, xnum;
	double tiny=cdflib_doubleTiny();
	int status;

	if (dfn > 1) 
	{
		d__1 = (dfn - 1.) / 2.;
		/* Computing 2nd power */
		d__2 = cdflib_snorm() + sqrt(xnonc);
		xnum = (cdflib_sgamma(d__1) * 2. + d__2 * d__2) / dfn;
	}
	else
	{
		// If dfn==1.
		/* Computing 2nd power */
		d__1 = cdflib_snorm() + sqrt(xnonc);
		xnum = d__1 * d__1;
	}
	d__1 = dfd / 2.;
	xden = cdflib_sgamma(d__1) * 2. / dfd;
	if (xden > xnum * tiny) {
		*x = xnum / xden;
		status=CDFLIB_OK;
	}
	else
	{
		cdflib_messageprint("Noncentral F Random Number: Overflow, returning Nan");
		*x = cdflib_nan();
		status=CDFLIB_ERROR;
	}
	return status;
}

