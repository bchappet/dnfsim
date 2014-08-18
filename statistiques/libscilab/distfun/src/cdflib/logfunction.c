/* rlog.f -- translated by f2c (version 20100827).
with manual tuning by Michael Baudin (2012)
*/

#include <math.h>

#include "cdflib.h"
#include "cdflib_private.h" 

double cdflib_rlog(double x)
{
	/* Initialized data */

	static double a = .0566749439387324;
	static double b = .0456512608815524;
	static double p0 = .333333333333333;
	static double p1 = -.224696413112536;
	static double p2 = .00620886815375787;
	static double q1 = -1.27408923933623;
	static double q2 = .354508718369557;

	/* System generated locals */
	double ret_val;

	/* Local variables */
	static double r__, t, u, w, w1;

	/*     ------------------- */
	/*     COMPUTATION OF  X - 1 - LN(X) */
	/*     ------------------- */

	if (x < .61 || x > 1.57) {
		goto L40;
	}
	if (x < .82) {
		goto L10;
	}
	if (x > 1.18) {
		goto L20;
	}

	/*              ARGUMENT REDUCTION */

	u = x - .5 - .5;
	w1 = 0.;
	goto L30;

L10:
	u = x - .7;
	u /= .7;
	w1 = a - u * .3;
	goto L30;

L20:
	u = x * .75 - 1.;
	w1 = b + u / 3.;

	/*               SERIES EXPANSION */

L30:
	r__ = u / (u + 2.);
	t = r__ * r__;
	w = ((p2 * t + p1) * t + p0) / ((q2 * t + q1) * t + 1.);
	ret_val = t * 2. * (1. / (1. - r__) - r__ * w) + w1;
	return ret_val;


L40:
	r__ = x - .5 - .5;
	ret_val = r__ - log(x);
	return ret_val;
}

double cdflib_rlog1(double x)
{
	/* Initialized data */

	static double a = .0566749439387324;
	static double b = .0456512608815524;
	static double p0 = .333333333333333;
	static double p1 = -.224696413112536;
	static double p2 = .00620886815375787;
	static double q1 = -1.27408923933623;
	static double q2 = .354508718369557;

	/* System generated locals */
	double ret_val;

	/* Local variables */
	static double h__, r__, t, w, w1;

	/* ----------------------------------------------------------------------- */
	/*             EVALUATION OF THE FUNCTION X - LN(1 + X) */
	/* ----------------------------------------------------------------------- */

	if (x < -.39 || x > .57) {
		goto L40;
	}
	if (x < -.18) {
		goto L10;
	}
	if (x > .18) {
		goto L20;
	}

	/*              ARGUMENT REDUCTION */

	h__ = x;
	w1 = 0.;
	goto L30;

L10:
	h__ = x + .3;
	h__ /= .7;
	w1 = a - h__ * .3;
	goto L30;

L20:
	h__ = x * .75 - .25;
	w1 = b + h__ / 3.;

	/*               SERIES EXPANSION */

L30:
	r__ = h__ / (h__ + 2.);
	t = r__ * r__;
	w = ((p2 * t + p1) * t + p0) / ((q2 * t + q1) * t + 1.);
	ret_val = t * 2. * (1. / (1. - r__) - r__ * w) + w1;
	return ret_val;


L40:
	w = x + .5 + .5;
	ret_val = x - log(w);
	return ret_val;
}

