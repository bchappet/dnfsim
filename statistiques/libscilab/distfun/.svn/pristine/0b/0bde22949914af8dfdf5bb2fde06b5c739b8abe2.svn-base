/* devlpl.f -- translated by f2c (version 20100827).
with manual tuning by Michael Baudin (2012)
*/

#include "cdflib.h"
#include "cdflib_private.h" 

double cdflib_devlpl(double *a, int n, double *x)
{
	/* System generated locals */
	double ret_val;

	/* Local variables */
	static int i;
	static double term;

	/* ********************************************************************** */

	/*     DOUBLE PRECISION FUNCTION DEVLPL(A,N,X) */
	/*              Double precision EVALuate a PoLynomial at X */

	/*                              Function */

	/*     returns */
	/*          A(1) + A(2)*X + ... + A(N)*X**(N-1) */

	/*                              Arguments */

	/*     A --> Array of coefficients of the polynomial. */
	/*                                        A is DOUBLE PRECISION(N) */

	/*     N --> Length of A, also degree of polynomial - 1. */

	/*     X --> Point at which the polynomial is to be evaluated. */

	/* ********************************************************************** */

	/* Parameter adjustments */
	--a;

	/* Function Body */
	term = a[n];
	for (i = n - 1; i >= 1; --i) {
		term = a[i] + term * *x;
	}
	ret_val = term;
	return ret_val;
}

