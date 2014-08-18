/* rexp.f -- translated by f2c (version 20100827).
with manual tuning by Michael Baudin (2012)
*/

#include <math.h>

#include "cdflib.h"
#include "cdflib_private.h" 

double cdflib_expm1(double x)
{
	/* Initialized data */

	static double p1 = 9.14041914819518e-10;
	static double p2 = .0238082361044469;
	static double q1 = -.499999999085958;
	static double q2 = .107141568980644;
	static double q3 = -.0119041179760821;
	static double q4 = 5.95130811860248e-4;

	/* System generated locals */
	double ret_val;

	/* Local variables */
	static double w;

	/* ----------------------------------------------------------------------- */
	/*            EVALUATION OF THE FUNCTION EXP(X) - 1 */
	/*                              Arguments */

	/*     X --> Argument at which exp(x)-1 desired */
	

	/*     DiDinato, A. R. and Morris,  A.   H.  Algorithm 708: Significant */
	/*     Digit Computation of the Incomplete  Beta  Function Ratios.  ACM */
	/*     Trans. Math.  Softw. 18 (1993), 360-373. */
	/* ----------------------------------------------------------------------- */

	if (fabs(x) > .15) {
		goto L10;
	}
	ret_val = x * (((p2 * x + p1) * x + 1.) / ((((q4 * x + q3) * x + q2) 
		* x + q1) * x + 1.));
	return ret_val;

L10:
	w = exp(x);
	if (x > 0.) {
		goto L20;
	}
	ret_val = w - .5 - .5;
	return ret_val;
L20:
	ret_val = w * (.5 - 1. / w + .5);
	return ret_val;
}

