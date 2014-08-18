/* exparg.f -- translated by f2c (version 20100827).
with manual tuning by Michael Baudin (2012)
*/

#include <math.h>

#include "cdflib.h"
#include "cdflib_private.h" 

double cdflib_exparg(int l)
{
	/* System generated locals */
	double ret_val;

	/* Local variables */
	static int b, m;
	static double lnb;

	/* -------------------------------------------------------------------- */
	/*     IF L = 0 THEN  EXPARG(L) = THE LARGEST POSITIVE W FOR WHICH */
	/*     EXP(W) CAN BE COMPUTED. */

	/*     IF L IS NONZERO THEN  EXPARG(L) = THE LARGEST NEGATIVE W FOR */
	/*     WHICH THE COMPUTED VALUE OF EXP(W) IS NONZERO. */

	/*     NOTE... ONLY AN APPROXIMATE VALUE FOR EXPARG(L) IS NEEDED. */
	/* -------------------------------------------------------------------- */

	b = cdflib_radix();
	if (b != 2) {
		goto L10;
	}
	lnb = .69314718055995;
	goto L40;
L10:
	if (b != 8) {
		goto L20;
	}
	lnb = 2.0794415416798;
	goto L40;
L20:
	if (b != 16) {
		goto L30;
	}
	lnb = 2.7725887222398;
	goto L40;
L30:
	lnb = log((double) b);

L40:
	if (l == 0) {
		goto L50;
	}
	m = cdflib_emin() - 1;
	ret_val = m * lnb * .99999;
	return ret_val;
L50:
	m = cdflib_emax();
	ret_val = m * lnb * .99999;
	return ret_val;
}

