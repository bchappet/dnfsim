/* dinvnr.f -- translated by f2c (version 20100827).
with manual tuning by Michael Baudin (2012)
*/

#include <math.h>

#include "cdflib.h"
#include "cdflib_private.h" 

/* STarting VALue for Neton-Raphon */
/* calculation of Normal distribution Inverse */
double cdflib_stvaln(double p);

double cdflib_dinvnr(double p, double q)
{
	/* System generated locals */
	double ret_val, d__1;

	/* Local variables */
	int i__;
	double dx, pp, cum, ccum, xcur;
	int qporq;
	double strtx;
	int status;

	/* ********************************************************************** */

	/*     DOUBLE PRECISION FUNCTION DINVNR(P,Q) */
	/*     Double precision NoRmal distribution INVerse */

	/*                              Function */

	/*     Returns X  such that CUMNOR(X)  =   P,  i.e., the  integral from - */
	/*     infinity to X of (1/SQRT(2*PI)) EXP(-U*U/2) dU is P */

	/*                              Arguments */

	/*     P --> The probability whose normal deviate is sought. */

	/*     Q --> 1-P */

	/*                              Method */

	/*     The  rational   function   on  page 95    of Kennedy  and  Gentle, */
	/*     Statistical Computing, Marcel Dekker, NY , 1980 is used as a start */
	/*     value for the Newton method of finding roots. */

	/*                              Note */

	/*     If P or Q .lt. machine EPS returns +/- DINVNR(EPS) */

	/* ********************************************************************** */

	/*     FIND MINIMUM OF P AND Q */

	qporq = p <= q;
	if (! qporq) {
		pp = q;
	} else {
		pp = p;
	}

	if (pp == .5) {
		ret_val = 0.;
		return ret_val;
	}

	/*     INITIALIZATION STEP */

	strtx = cdflib_stvaln(pp);
	xcur = strtx;

	/*     NEWTON INTERATIONS */

	for (i__ = 1; i__ <= 100; ++i__) {
		cdflib_cumnor(xcur, &cum, &ccum, &status);
		if (status==CDFLIB_ERROR)
		{
			return status;
		}
		dx = (cum - pp) / (.3989422804014326 * exp(-.5 * xcur * xcur));
		xcur -= dx;
		if ((d__1 = dx / xcur, fabs(d__1)) < 1e-13) {
			goto L40;
		}
	}
	ret_val = strtx;

	/*     IF WE GET HERE, NEWTON HAS FAILED */

	if (! qporq) {
		ret_val = -ret_val;
	}
	return ret_val;

	/*     IF WE GET HERE, NEWTON HAS SUCCEDED */

L40:
	ret_val = xcur;
	if (! qporq) {
		ret_val = -ret_val;
	}
	return ret_val;
}

double cdflib_stvaln(double p)
{
	/* Initialized data */
	static double xnum[5] = { -.322232431088,-1.,-.342242088547,
		-.0204231210245,-4.53642210148e-5 };
	static double xden[5] = { .099348462606,.588581570495,.531103462366,
		.10353775285,.0038560700634 };
	/* System generated locals */
	double ret_val;
	/* Local variables */
	static double y, z__, sign;

	/* ********************************************************************** */
	/*     DOUBLE PRECISION FUNCTION STVALN(P) */
	/*                    STarting VALue for Neton-Raphon */
	/*                calculation of Normal distribution Inverse */
	/*                              Function */
	/*     Returns X  such that CUMNOR(X)  =   P,  i.e., the  integral from - */
	/*     infinity to X of (1/SQRT(2*PI)) EXP(-U*U/2) dU is P */
	/*                              Arguments */
	/*     P --> The probability whose normal deviate is sought. */
	/*                              Method */
	/*     The  rational   function   on  page 95    of Kennedy  and  Gentle, */
	/*     Statistical Computing, Marcel Dekker, NY , 1980. */
	/* ********************************************************************** */

	if (! (p <= .5)) {
		goto L10;
	}
	sign = -1.;
	z__ = p;
	goto L20;
L10:
	sign = 1.;
	z__ = 1. - p;
L20:
	y = sqrt(log(z__) * -2.);
	ret_val = y + cdflib_devlpl(xnum, 5, &y) / cdflib_devlpl(xden, 5, &y);
	ret_val = sign * ret_val;
	return ret_val;
}

