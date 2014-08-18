// Copyright (C) 2014 - Michael Baudin
//
// This file must be used under the terms of the GNU Lesser General Public License license :
// http://www.gnu.org/copyleft/lesser.html

#include <math.h>
#include <stdio.h>

#include "cdflib.h"
#include "cdflib_private.h" 
#include "brent.h"

// Noncentral T Distribution

int cdflib_nctCheckX(char * fname, double x)
{
	// Nothing to do.
	return CDFLIB_OK;
}
int cdflib_nctCheckParams(char * fname, double DF, double PNONC)
{
	int status;
	/*     DF > 0 */
	status=cdflib_checkgreaterthan(fname, DF, "DF", 0.);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	// PNONC : nothing to check
	return CDFLIB_OK;
}
int cdflib_nctcdf(double x, double DF, double PNONC, int lowertail, double *p)
{
	int status;
	double q;
	// Check x
	status=cdflib_nctCheckX("cdflib_nctcdf", x);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	// Check DF, PNONC
	status=cdflib_nctCheckParams("cdflib_nctcdf", DF, PNONC);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	if (cdflib_isnan(x) || cdflib_isnan(DF) || cdflib_isnan(PNONC))
	{
		*p = x+DF+PNONC;
		status = CDFLIB_OK;
		return status;
	}
	cdflib_cumnct(x, DF, PNONC, p, &q, &status);
	if (status==CDFLIB_ERROR)
	{
		return status;
	}
	if (lowertail==CDFLIB_UPPERTAIL)
	{
		*p=q;
	}
	return CDFLIB_OK;
}

int cdflib_nctinv(double p, double DF, double PNONC, int lowertail, double *x)
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
	status=cdflib_nctCheckParams("cdflib_nctcdf", DF, PNONC);
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
	if (cdflib_isnan(p) || cdflib_isnan(DF) || cdflib_isnan(PNONC))
	{
		*x = p+DF+PNONC;
		status = CDFLIB_OK;
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
		cdflib_cumnct(b, DF, PNONC, &cum, &ccum, &status);
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
	*x = 5.;
	inversionlabel = 0;
	while (1)
	{
	    zero_rc ( a, b, atol, x, &inversionlabel, fx );
		if (inversionlabel<0) 
		{
			break;
		}
		cdflib_cumnct(*x, DF, PNONC, &cum, &ccum, &status);
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
	return CDFLIB_OK;
}

int cdflib_nctpdf(double x, double DF, double PNONC, double *y)
{
	int status;
	double eps = cdflib_doubleEps();
	double fp, fm; // CDF
	double cfp, cfm; // Complementary CDF
	double h;
	// Check x
	status=cdflib_nctCheckX("cdflib_nctpdf", x);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	// Check DF, PNONC
	status=cdflib_nctCheckParams("cdflib_nctpdf", DF, PNONC);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	// Use finite differences
	// Use order 2 centered difference, if possible
	h = pow(eps,1./3.);
	// fp = (f(x+h) - f(x-h))/(2*h)
	cdflib_cumnct(x+h, DF, PNONC, &fp, &cfp, &status);
	if (status==CDFLIB_ERROR)
	{
		return status;
	}
	cdflib_cumnct(x-h, DF, PNONC, &fm, &cfm, &status);
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
	if (*y<0.)
	{
		*y=0.;
	}
	status = CDFLIB_OK;
	return CDFLIB_OK;
}

int cdflib_nctrnd(double DF, double PNONC, double *x)
{
	double z;
	double t;
	int status;
	// Check DF, PNONC
	status=cdflib_nctCheckParams("cdflib_nctpdf", DF, PNONC);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	// Generate by ratio of random variables 
	status=cdflib_normrnd(0, 1, &z);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	status = cdflib_chi2rnd(DF, &t);
	if (status!=CDFLIB_OK)
	{
		return status;
	}
	*x=(z+PNONC)/sqrt(t/DF);
	//
	status = CDFLIB_OK;
	return status;
}

void cdflib_cumnct(double t, double df, double pnonc,
	double *cum, double *ccum, int *status)
{
	double d1, d2;
	double b, d, e, s, x, t2, bb, xi, ss, tt, lnx, omx, dum1, 
		dum2, cent;
	int ierr;
	double xlnd, term, xlne;
	double twoi, bcent, dcent, ecent;
	double scent, lnomx;
	int qrevs;
	double pnonc2, lambda, halfdf, alghdf, bbcent;
	double dpnonc, sscent;
	char buffer [1024];
	/*     Case pnonc essentially zero */
	if (fabs(pnonc) <= 1e-10) {
		cdflib_cumt(t, df, cum, ccum, status);
		return;
	}
	qrevs = t < 0.;
	if (qrevs) {
		tt = -(t);
		dpnonc = -(pnonc);
	} else {
		tt = t;
		dpnonc = pnonc;
	}
	pnonc2 = dpnonc * dpnonc;
	t2 = tt * tt;
	if (fabs(tt) <= 1e-10) {
		d1 = -(pnonc);
		cdflib_cumnor(d1, cum, ccum, status);
		return;
	}
	lambda = pnonc2 * .5;
	x = df / (df + t2);
	omx = 1. - x;
	lnx = log(x);
	lnomx = log(omx);
	halfdf = df * .5;
	alghdf = incgam_loggam(halfdf);
	/*     ******************** Case i = lambda */
	cent = (double) ((int) lambda);
	if (cent < 1.) {
		cent = 1.;
	}
	/*     Compute d=T(2i) in log space and offset by exp(-lambda) */
	d1 = cent + 1.;
	xlnd = cent * log(lambda) - incgam_loggam(d1) - lambda;
	dcent = exp(xlnd);
	/*     Compute e=t(2i+1) in log space offset by exp(-lambda) */
	d1 = cent + 1.5;
	xlne = (cent + .5) * log(lambda) - incgam_loggam(d1) - lambda;
	ecent = exp(xlne);
	if (dpnonc < 0.) {
		ecent = -ecent;
	}
	/*     Compute bcent=B(2*cent) */
	d1 = cent + .5;
	cdflib_bratio(halfdf, d1, x, omx, &bcent, &dum1, &ierr);
	if (ierr!=0)
	{
		*status=CDFLIB_ERROR;
		sprintf (buffer, "%s: Unable to evaluate Incomplete Beta function at a=%e, b=%e, x=%e, y=%e","cdflib_cumnct",halfdf,d1,x,omx);
		cdflib_messageprint(buffer);
		return;
	}
	/*     compute bbcent=B(2*cent+1) */
	d1 = cent + 1.;
	cdflib_bratio(halfdf, d1, x, omx, &bbcent, &dum2, &ierr);
	if (ierr!=0)
	{
		*status=CDFLIB_ERROR;
		sprintf (buffer, "%s: Unable to evaluate Incomplete Beta function at a=%e, b=%e, x=%e, y=%e","cdflib_cumnct",halfdf,d1,x,omx);
		cdflib_messageprint(buffer);
		return;
	}
	/*     Case bcent and bbcent are essentially zero */
	/*     Thus t is effectively infinite */
	if (bcent + bbcent < 1e-10) {
		if (qrevs) {
			*cum = 0.;
			*ccum = 1.;
		} else {
			*cum = 1.;
			*ccum = 0.;
		}
		*status=CDFLIB_OK;
		return;
	}
	/*     Case bcent and bbcent are essentially one */
	/*     Thus t is effectively zero */
	if (dum1 + dum2 < 1e-100) {
		d1 = -(pnonc);
		cdflib_cumnor(d1, cum, ccum, status);
		return;
	}
	/*     First term in ccum is D*B + E*BB */
	*ccum = dcent * bcent + ecent * bbcent;
	/*     compute s(cent) = B(2*(cent+1)) - B(2*cent)) */
	d1 = halfdf + cent + .5;
	d2 = cent + 1.5;
	scent = incgam_loggam(d1) - incgam_loggam(d2) - alghdf + halfdf * lnx + (cent + 
		.5) * lnomx;
	scent = exp(scent);
	/*     compute ss(cent) = B(2*cent+3) - B(2*cent+1) */
	d1 = halfdf + cent + 1.;
	d2 = cent + 2.;
	sscent = incgam_loggam(d1) - incgam_loggam(d2) - alghdf + halfdf * lnx + (cent + 
		1.) * lnomx;
	sscent = exp(sscent);
	/*     ******************** Sum Forward */
	xi = cent + 1.;
	twoi = xi * 2.;
	d = dcent;
	e = ecent;
	b = bcent;
	bb = bbcent;
	s = scent;
	ss = sscent;
L10:
	b += s;
	bb += ss;
	d = lambda / xi * d;
	e = lambda / (xi + .5) * e;
	term = d * b + e * bb;
	*ccum += term;
	s = s * omx * (df + twoi - 1.) / (twoi + 1.);
	ss = ss * omx * (df + twoi) / (twoi + 2.);
	xi += 1.;
	twoi = xi * 2.;
	if (fabs(term) > *ccum * 1e-7) {
		goto L10;
	}
	/*     ******************** Sum Backward */
	xi = cent;
	twoi = xi * 2.;
	d = dcent;
	e = ecent;
	b = bcent;
	bb = bbcent;
	s = scent * (twoi + 1.) / ((df + twoi - 1.) * omx);
	ss = sscent * (twoi + 2.) / ((df + twoi) * omx);
L20:
	b -= s;
	bb -= ss;
	d *= xi / lambda;
	e *= (xi + .5) / lambda;
	term = d * b + e * bb;
	*ccum += term;
	xi += -1.;
	if (xi < .5) {
		goto L30;
	}
	twoi = xi * 2.;
	s = s * (twoi + 1.) / ((df + twoi - 1.) * omx);
	ss = ss * (twoi + 2.) / ((df + twoi) * omx);
	if (fabs(term) > *ccum * 1e-7) {
		goto L20;
	}
L30:
	if (qrevs) {
		*cum = *ccum * .5;
		*ccum = 1. - *cum;
	} else {
		*ccum *= .5;
		*cum = 1. - *ccum;
	}
	/*     Due to roundoff error the answer may not lie between zero and one */
	/*     Force it to do so */
	/* Computing MAX */
	d1 = cdflib_min(*cum,1.);
	*cum = cdflib_max(d1,0.);
	/* Computing MAX */
	d1 = cdflib_min(*ccum,1.);
	*ccum = cdflib_max(d1,0.);
	return;
}
