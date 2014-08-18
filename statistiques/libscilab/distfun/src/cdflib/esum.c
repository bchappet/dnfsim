/* esum.f -- translated by f2c (version 20100827).
with manual tuning by Michael Baudin (2012)
*/

#include <math.h>

#include "cdflib.h"
#include "cdflib_private.h" 

double cdflib_esum(int mu, double x)
{
	/* System generated locals */
	double ret_val;

	/* Local variables */
	static double w;

	/* ----------------------------------------------------------------------- */
	/*                    EVALUATION OF EXP(MU + X) */
	/* ----------------------------------------------------------------------- */

	if (x > 0.) {
		goto L10;
	}

	if (mu < 0) {
		goto L20;
	}
	w = mu + x;
	if (w > 0.) {
		goto L20;
	}
	ret_val = exp(w);
	return ret_val;

L10:
	if (mu > 0) {
		goto L20;
	}
	w = mu + x;
	if (w < 0.) {
		goto L20;
	}
	ret_val = exp(w);
	return ret_val;

L20:
	w = (double) (mu);
	ret_val = exp(w) * exp(x);
	return ret_val;
}

