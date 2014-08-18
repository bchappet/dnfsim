/* translated by f2c (version 20100827).
with manual tuning by Michael Baudin (2012)
*/

#include <math.h>

#include "cdflib.h"
#include "cdflib_private.h" 

/* COMPUTATION OF LN(GAMMA(B)/GAMMA(A+B)) WHEN B >= 8 */
double cdflib_algdiv(double a, double b);

/* ASYMPTOTIC EXPANSION FOR IX(A,B) FOR LARGE A AND B. */
/* LAMBDA = (A + B)*Y - B  AND EPS IS THE TOLERANCE USED. */
/* IT IS ASSUMED THAT LAMBDA IS NONNEGATIVE AND THAT */
/* A AND B ARE GREATER THAN OR EQUAL TO 15. */
// Used in cdflib_bratio.
double cdflib_basym(double a, double b, double lambda, double eps);

/* CONTINUED FRACTION EXPANSION INCOMPLETE BETA FUNCTION FOR IX(A,B) WHEN A,B > 1. */
/* IT IS ASSUMED THAT  LAMBDA = (A + B)*Y - B. */
// Used in cdflib_bratio.
double cdflib_bfrac(double a, double b, double x, double y, 
	double lambda, double eps);


/* ASYMPTOTIC EXPANSION FOR INCOMPLETE BETA FUNCTION IX(A,B) WHEN A IS LARGER THAN B. */
/* THE RESULT OF THE EXPANSION IS ADDED TO W. IT IS ASSUMED */
/* THAT A >= 15 AND B <= 1.  EPS IS THE TOLERANCE USED. */
/* IERR IS A VARIABLE THAT REPORTS THE STATUS OF THE RESULTS. */
// ierr=0 : result is OK
// ierr=1 : THE EXPANSION CANNOT BE COMPUTED
// Used in cdflib_bratio.
void cdflib_bgrat(double a, double b, double x, 
	double y, double *w, double eps, int *ierr);

/* EVALUATION OF  EXP(MU) * (X**A*Y**B/BETA(A,B)) */
// Used in cdflib_bup.
double cdflib_brcmp1(int mu, double a, double b, double x, double y);

/* EVALUATION OF IX(A,B) - IX(A+N,B) WHERE N IS A POSITIVE INTEGER. */
/* EPS IS THE TOLERANCE USED. */
// Used in cdflib_bratio.
double cdflib_bup(double a, double b, double x, double y, 
	int n, double eps);

/* EVALUATION OF IX(A,B) */
/* FOR B < MIN(EPS,EPS*A) AND X <= 0.5. */
// Used in cdflib_bratio.
double cdflib_fpser(double a, double b, double x, double eps);

/* APSER YIELDS THE INCOMPLETE BETA RATIO I(SUB(1-X))(B,A) FOR */
/* A <= MIN(EPS,EPS*B), B*X <= 1, AND X <= 0.5. USED WHEN */
/* A IS VERY SMALL. USE ONLY IF ABOVE INEQUALITIES ARE SATISFIED. */
// Used in cdflib_bratio.
double cdflib_apser(double a, double b, double x, double eps);

/* POWER SERIES EXPANSION FOR EVALUATING 
INCOMPLETE BETA FUNCTION IX(A,B) WHEN B <= 1 */
/* OR B*X <= 0.7.  EPS IS THE TOLERANCE USED. */
// Used in cdflib_bratio.
double cdflib_bpser(double a, double b, double x, double eps);

double cdflib_betaln(double a0, double b0)
{
	/* Initialized data */

	static double e = .918938533204673;

	/* System generated locals */
	int i__1;
	double ret_val, d__1;

	/* Local variables */
	static double a, b, c__, h__;
	static int i__, n;
	static double u, v, w, z__;

	/* ----------------------------------------------------------------------- */
	/*     EVALUATION OF THE LOGARITHM OF THE BETA FUNCTION */
	/* ----------------------------------------------------------------------- */
	/*     E = 0.5*LN(2*PI) */
	/* -------------------------- */

	a = cdflib_min(a0,b0);
	b = cdflib_max(a0,b0);
	if (a >= 8.) {
		goto L100;
	}
	if (a >= 1.) {
		goto L20;
	}
	/* ----------------------------------------------------------------------- */
	/*                   PROCEDURE WHEN A < 1 */
	/* ----------------------------------------------------------------------- */
	if (b >= 8.) {
		goto L10;
	}
	d__1 = a + b;
	ret_val = incgam_loggam(a) + (incgam_loggam(b) - incgam_loggam(d__1));
	return ret_val;
L10:
	ret_val = incgam_loggam(a) + cdflib_algdiv(a, b);
	return ret_val;
	/* ----------------------------------------------------------------------- */
	/*                PROCEDURE WHEN 1 <= A < 8 */
	/* ----------------------------------------------------------------------- */
L20:
	if (a > 2.) {
		goto L40;
	}
	if (b > 2.) {
		goto L30;
	}
	ret_val = incgam_loggam(a) + incgam_loggam(b) - cdflib_gsumln(a, b);
	return ret_val;
L30:
	w = 0.;
	if (b < 8.) {
		goto L60;
	}
	ret_val = incgam_loggam(a) + cdflib_algdiv(a, b);
	return ret_val;

	/*                REDUCTION OF A WHEN B <= 1000 */

L40:
	if (b > 1e3) {
		goto L80;
	}
	n = (int) (a - 1.);
	w = 1.;
	i__1 = n;
	for (i__ = 1; i__ <= i__1; ++i__) {
		a += -1.;
		h__ = a / b;
		w *= h__ / (h__ + 1.);
		/* L50: */
	}
	w = log(w);
	if (b < 8.) {
		goto L60;
	}
	ret_val = w + incgam_loggam(a) + cdflib_algdiv(a, b);
	return ret_val;

	/*                 REDUCTION OF B WHEN B < 8 */

L60:
	n = (int) (b - 1.);
	z__ = 1.;
	i__1 = n;
	for (i__ = 1; i__ <= i__1; ++i__) {
		b += -1.;
		z__ *= b / (a + b);
		/* L70: */
	}
	ret_val = w + log(z__) + (incgam_loggam(a) + (incgam_loggam(b) - cdflib_gsumln(a, b)));
	return ret_val;

	/*                REDUCTION OF A WHEN B > 1000 */

L80:
	n = (int) (a - 1.);
	w = 1.;
	i__1 = n;
	for (i__ = 1; i__ <= i__1; ++i__) {
		a += -1.;
		w *= a / (a / b + 1.);
		/* L90: */
	}
	ret_val = log(w) - n * log(b) + (incgam_loggam(a) + cdflib_algdiv(a, b));
	return ret_val;
	/* ----------------------------------------------------------------------- */
	/*                   PROCEDURE WHEN A >= 8 */
	/* ----------------------------------------------------------------------- */
L100:
	w = cdflib_bcorr(a, b);
	h__ = a / b;
	c__ = h__ / (h__ + 1.);
	u = -(a - .5) * log(c__);
	v = b * cdflib_log1p(h__);
	if (u <= v) {
		goto L110;
	}
	ret_val = log(b) * -.5 + e + w - v - u;
	return ret_val;
L110:
	ret_val = log(b) * -.5 + e + w - u - v;
	return ret_val;
}

double cdflib_bup(double a, double b, double x, double y, 
	int n, double eps)
{
	/* System generated locals */
	int i__1;
	double ret_val, d__1;

	/* Local variables */
	static double d__;
	static int i__, k;
	static double l, r__, t, w;
	static int mu;
	static double ap1;
	static int kp1, nm1;
	static double apb;

	/* ----------------------------------------------------------------------- */
	/*     EVALUATION OF IX(A,B) - IX(A+N,B) WHERE N IS A POSITIVE INTEGER. */
	/*     EPS IS THE TOLERANCE USED. */
	/* ----------------------------------------------------------------------- */

	/*          OBTAIN THE SCALING FACTOR EXP(-MU) AND */
	/*             EXP(MU)*(X**A*Y**B/BETA(A,B))/A */

	apb = a + b;
	ap1 = a + 1.;
	mu = 0;
	d__ = 1.;
	if (n == 1 || a < 1.) {
		goto L10;
	}
	if (apb < ap1 * 1.1) {
		goto L10;
	}
	mu = (d__1 = cdflib_exparg(1), (int) fabs(d__1));
	k = (int) cdflib_exparg(0);
	if (k < mu) {
		mu = k;
	}
	t = (double) mu;
	d__ = exp(-t);

L10:
	ret_val = cdflib_brcmp1(mu, a, b, x, y) / a;
	if (n == 1 || ret_val == 0.) {
		return ret_val;
	}
	nm1 = n - 1;
	w = d__;

	/*          LET K BE THE INDEX OF THE MAXIMUM TERM */

	k = 0;
	if (b <= 1.) {
		goto L50;
	}
	if (y > 1e-4) {
		goto L20;
	}
	k = nm1;
	goto L30;
L20:
	r__ = (b - 1.) * x / y - a;
	if (r__ < 1.) {
		goto L50;
	}
	k = nm1;
	t = (double) nm1;
	if (r__ < t) {
		k = (int) r__;
	}

	/*          ADD THE INCREASING TERMS OF THE SERIES */

L30:
	i__1 = k;
	for (i__ = 1; i__ <= i__1; ++i__) {
		l = (double) (i__ - 1);
		d__ = (apb + l) / (ap1 + l) * x * d__;
		w += d__;
		/* L40: */
	}
	if (k == nm1) {
		goto L70;
	}

	/*          ADD THE REMAINING TERMS OF THE SERIES */

L50:
	kp1 = k + 1;
	i__1 = nm1;
	for (i__ = kp1; i__ <= i__1; ++i__) {
		l = (double) (i__ - 1);
		d__ = (apb + l) / (ap1 + l) * x * d__;
		w += d__;
		if (d__ <= eps * w) {
			goto L70;
		}
		/* L60: */
	}

	/*               TERMINATE THE PROCEDURE */

L70:
	ret_val *= w;
	return ret_val;
}

double cdflib_brcomp(double a, double b, double x, double y)
{
	/* Initialized data */

	static double const__ = .398942280401433;

	/* System generated locals */
	int i__1;
	double ret_val, d__1;

	/* Local variables */
	static double c__, e, h__;
	static int i__, n;
	static double t, u, v, z__, a0, b0, x0, y0, apb, lnx, lny;
	static double lambda;

	/* ----------------------------------------------------------------------- */
	/*               EVALUATION OF X**A*Y**B/BETA(A,B) */
	/* ----------------------------------------------------------------------- */

	/* ----------------- */
	/*     CONST = 1/SQRT(2*PI) */
	/* ----------------- */

	ret_val = 0.;
	if (x == 0. || y == 0.) {
		return ret_val;
	}
	a0 = cdflib_min(a,b);
	if (a0 >= 8.) {
		goto L130;
	}

	if (x > .375) {
		goto L10;
	}
	lnx = log(x);
	d__1 = -(x);
	lny = cdflib_log1p(d__1);
	goto L30;
L10:
	if (y > .375) {
		goto L20;
	}
	d__1 = -(y);
	lnx = cdflib_log1p(d__1);
	lny = log(y);
	goto L30;
L20:
	lnx = log(x);
	lny = log(y);

L30:
	z__ = a * lnx + b * lny;
	if (a0 < 1.) {
		goto L40;
	}
	z__ -= cdflib_betaln(a, b);
	ret_val = exp(z__);
	return ret_val;
	/* ----------------------------------------------------------------------- */
	/*              PROCEDURE FOR A < 1 OR B < 1 */
	/* ----------------------------------------------------------------------- */
L40:
	b0 = cdflib_max(a,b);
	if (b0 >= 8.) {
		goto L120;
	}
	if (b0 > 1.) {
		goto L70;
	}

	/*                   ALGORITHM FOR B0 <= 1 */

	ret_val = exp(z__);
	if (ret_val == 0.) {
		return ret_val;
	}

	apb = a + b;
	if (apb > 1.) {
		goto L50;
	}
	z__ = cdflib_gam1(apb) + 1.;
	goto L60;
L50:
	u = a + b - 1.;
	z__ = (cdflib_gam1(u) + 1.) / apb;

L60:
	c__ = (cdflib_gam1(a) + 1.) * (cdflib_gam1(b) + 1.) / z__;
	ret_val = ret_val * (a0 * c__) / (a0 / b0 + 1.);
	return ret_val;

	/*                ALGORITHM FOR 1 < B0 < 8 */

L70:
	u = cdflib_gamln1(a0);
	n = (int) (b0 - 1.);
	if (n < 1) {
		goto L90;
	}
	c__ = 1.;
	i__1 = n;
	for (i__ = 1; i__ <= i__1; ++i__) {
		b0 += -1.;
		c__ *= b0 / (a0 + b0);
		/* L80: */
	}
	u = log(c__) + u;

L90:
	z__ -= u;
	b0 += -1.;
	apb = a0 + b0;
	if (apb > 1.) {
		goto L100;
	}
	t = cdflib_gam1(apb) + 1.;
	goto L110;
L100:
	u = a0 + b0 - 1.;
	t = (cdflib_gam1(u) + 1.) / apb;
L110:
	ret_val = a0 * exp(z__) * (cdflib_gam1(b0) + 1.) / t;
	return ret_val;

	/*                   ALGORITHM FOR B0 >= 8 */

L120:
	u = cdflib_gamln1(a0) + cdflib_algdiv(a0, b0);
	ret_val = a0 * exp(z__ - u);
	return ret_val;
	/* ----------------------------------------------------------------------- */
	/*              PROCEDURE FOR A >= 8 AND B >= 8 */
	/* ----------------------------------------------------------------------- */
L130:
	if (a > b) {
		goto L140;
	}
	h__ = a / b;
	x0 = h__ / (h__ + 1.);
	y0 = 1. / (h__ + 1.);
	lambda = a - (a + b) * x;
	goto L150;
L140:
	h__ = b / a;
	x0 = 1. / (h__ + 1.);
	y0 = h__ / (h__ + 1.);
	lambda = (a + b) * y - b;

L150:
	e = -lambda / a;
	if (fabs(e) > .6) {
		goto L160;
	}
	u = cdflib_rlog1(e);
	goto L170;
L160:
	u = e - log(x / x0);

L170:
	e = lambda / b;
	if (fabs(e) > .6) {
		goto L180;
	}
	v = cdflib_rlog1(e);
	goto L190;
L180:
	v = e - log(y / y0);

L190:
	z__ = exp(-(a * u + b * v));
	ret_val = const__ * sqrt(b * x0) * z__ * exp(-cdflib_bcorr(a, b));
	return ret_val;
}

double cdflib_brcmp1(int mu, double a, double b, double x, double y)
{
	/* Initialized data */

	static double const__ = .398942280401433;

	/* System generated locals */
	int i__1;
	double ret_val, d__1;

	/* Local variables */
	static double c__, e, h__;
	static int i__, n;
	static double t, u, v, z__, a0, b0, x0, y0, apb, lnx, lny;
	static double lambda;

	/* ----------------------------------------------------------------------- */
	/*          EVALUATION OF  EXP(MU) * (X**A*Y**B/BETA(A,B)) */
	/* ----------------------------------------------------------------------- */

	/* ----------------- */
	/*     CONST = 1/SQRT(2*PI) */
	/* ----------------- */

	a0 = cdflib_min(a,b);
	if (a0 >= 8.) {
		goto L130;
	}

	if (x > .375) {
		goto L10;
	}
	lnx = log(x);
	d__1 = -(x);
	lny = cdflib_log1p(d__1);
	goto L30;
L10:
	if (y > .375) {
		goto L20;
	}
	d__1 = -(y);
	lnx = cdflib_log1p(d__1);
	lny = log(y);
	goto L30;
L20:
	lnx = log(x);
	lny = log(y);

L30:
	z__ = a * lnx + b * lny;
	if (a0 < 1.) {
		goto L40;
	}
	z__ -= cdflib_betaln(a, b);
	ret_val = cdflib_esum(mu, z__);
	return ret_val;
	/* ----------------------------------------------------------------------- */
	/*              PROCEDURE FOR A < 1 OR B < 1 */
	/* ----------------------------------------------------------------------- */
L40:
	b0 = cdflib_max(a,b);
	if (b0 >= 8.) {
		goto L120;
	}
	if (b0 > 1.) {
		goto L70;
	}

	/*                   ALGORITHM FOR B0 <= 1 */

	ret_val = cdflib_esum(mu, z__);
	if (ret_val == 0.) {
		return ret_val;
	}

	apb = a + b;
	if (apb > 1.) {
		goto L50;
	}
	z__ = cdflib_gam1(apb) + 1.;
	goto L60;
L50:
	u = a + b - 1.;
	z__ = (cdflib_gam1(u) + 1.) / apb;

L60:
	c__ = (cdflib_gam1(a) + 1.) * (cdflib_gam1(b) + 1.) / z__;
	ret_val = ret_val * (a0 * c__) / (a0 / b0 + 1.);
	return ret_val;

	/*                ALGORITHM FOR 1 < B0 < 8 */

L70:
	u = cdflib_gamln1(a0);
	n = (int) (b0 - 1.);
	if (n < 1) {
		goto L90;
	}
	c__ = 1.;
	i__1 = n;
	for (i__ = 1; i__ <= i__1; ++i__) {
		b0 += -1.;
		c__ *= b0 / (a0 + b0);
		/* L80: */
	}
	u = log(c__) + u;

L90:
	z__ -= u;
	b0 += -1.;
	apb = a0 + b0;
	if (apb > 1.) {
		goto L100;
	}
	t = cdflib_gam1(apb) + 1.;
	goto L110;
L100:
	u = a0 + b0 - 1.;
	t = (cdflib_gam1(u) + 1.) / apb;
L110:
	ret_val = a0 * cdflib_esum(mu, z__) * (cdflib_gam1(b0) + 1.) / t;
	return ret_val;

	/*                   ALGORITHM FOR B0 >= 8 */

L120:
	u = cdflib_gamln1(a0) + cdflib_algdiv(a0, b0);
	d__1 = z__ - u;
	ret_val = a0 * cdflib_esum(mu, d__1);
	return ret_val;
	/* ----------------------------------------------------------------------- */
	/*              PROCEDURE FOR A >= 8 AND B >= 8 */
	/* ----------------------------------------------------------------------- */
L130:
	if (a > b) {
		goto L140;
	}
	h__ = a / b;
	x0 = h__ / (h__ + 1.);
	y0 = 1. / (h__ + 1.);
	lambda = a - (a + b) * x;
	goto L150;
L140:
	h__ = b / a;
	x0 = 1. / (h__ + 1.);
	y0 = h__ / (h__ + 1.);
	lambda = (a + b) * y - b;

L150:
	e = -lambda / a;
	if (fabs(e) > .6) {
		goto L160;
	}
	u = cdflib_rlog1(e);
	goto L170;
L160:
	u = e - log(x / x0);

L170:
	e = lambda / b;
	if (fabs(e) > .6) {
		goto L180;
	}
	v = cdflib_rlog1(e);
	goto L190;
L180:
	v = e - log(y / y0);

L190:
	d__1 = -(a * u + b * v);
	z__ = cdflib_esum(mu, d__1);
	ret_val = const__ * sqrt(b * x0) * z__ * exp(-cdflib_bcorr(a, b));
	return ret_val;
}

double cdflib_bpser(double a, double b, double x, double eps)
{
	/* System generated locals */
	int i__1;
	double ret_val;

	/* Local variables */
	static double c__;
	static int i__, m;
	static double n, t, u, w, z__, a0, b0, apb, tol, sum;

	/* ----------------------------------------------------------------------- */
	/*     POWER SERIES EXPANSION FOR EVALUATING IX(A,B) WHEN B <= 1 */
	/*     OR B*X <= 0.7.  EPS IS THE TOLERANCE USED. */
	/* ----------------------------------------------------------------------- */

	ret_val = 0.;
	if (x == 0.) {
		return ret_val;
	}
	/* ----------------------------------------------------------------------- */
	/*            COMPUTE THE FACTOR X**A/(A*BETA(A,B)) */
	/* ----------------------------------------------------------------------- */
	a0 = cdflib_min(a,b);
	if (a0 < 1.) {
		goto L10;
	}
	z__ = a * log(x) - cdflib_betaln(a, b);
	ret_val = exp(z__) / a;
	goto L100;
L10:
	b0 = cdflib_max(a,b);
	if (b0 >= 8.) {
		goto L90;
	}
	if (b0 > 1.) {
		goto L40;
	}

	/*            PROCEDURE FOR A0 < 1 AND B0 <= 1 */

	ret_val = cdflib_powdd(x, a);
	if (ret_val == 0.) {
		return ret_val;
	}

	apb = a + b;
	if (apb > 1.) {
		goto L20;
	}
	z__ = cdflib_gam1(apb) + 1.;
	goto L30;
L20:
	u = a + b - 1.;
	z__ = (cdflib_gam1(u) + 1.) / apb;

L30:
	c__ = (cdflib_gam1(a) + 1.) * (cdflib_gam1(b) + 1.) / z__;
	ret_val = ret_val * c__ * (b / apb);
	goto L100;

	/*         PROCEDURE FOR A0 < 1 AND 1 < B0 < 8 */

L40:
	u = cdflib_gamln1(a0);
	m = (int) (b0 - 1.);
	if (m < 1) {
		goto L60;
	}
	c__ = 1.;
	i__1 = m;
	for (i__ = 1; i__ <= i__1; ++i__) {
		b0 += -1.;
		c__ *= b0 / (a0 + b0);
		/* L50: */
	}
	u = log(c__) + u;

L60:
	z__ = a * log(x) - u;
	b0 += -1.;
	apb = a0 + b0;
	if (apb > 1.) {
		goto L70;
	}
	t = cdflib_gam1(apb) + 1.;
	goto L80;
L70:
	u = a0 + b0 - 1.;
	t = (cdflib_gam1(u) + 1.) / apb;
L80:
	ret_val = exp(z__) * (a0 / a) * (cdflib_gam1(b0) + 1.) / t;
	goto L100;

	/*            PROCEDURE FOR A0 < 1 AND B0 >= 8 */

L90:
	u = cdflib_gamln1(a0) + cdflib_algdiv(a0, b0);
	z__ = a * log(x) - u;
	ret_val = a0 / a * exp(z__);
L100:
	if (ret_val == 0. || a <= eps * .1) {
		return ret_val;
	}
	/* ----------------------------------------------------------------------- */
	/*                     COMPUTE THE SERIES */
	/* ----------------------------------------------------------------------- */
	sum = 0.;
	n = 0.;
	c__ = 1.;
	tol = eps / a;
L110:
	n += 1.;
	c__ = c__ * (.5 - b / n + .5) * x;
	w = c__ / (a + n);
	sum += w;
	if (fabs(w) > tol) {
		goto L110;
	}
	ret_val *= a * sum + 1.;
	return ret_val;
}

void cdflib_bgrat(double a, double b, double x, 
	double y, double *w, double eps, int *ierr)
{
	/* System generated locals */
	int i__1;
	double d__1;

	double c__[30], d__[30];
	int i__;
	double j, l;
	int n;
	double p, q, r__, s, t, u, v, z__, n2, t2, dj, cn, nu, bm1;
	int nm1;
	double lnx, sum;
	double bp2n, coef;

	bm1 = b - .5 - .5;
	nu = a + bm1 * .5;
	if (y > .375) {
		goto L10;
	}
	d__1 = -(y);
	lnx = cdflib_log1p(d__1);
	goto L20;
L10:
	lnx = log(x);
L20:
	z__ = -nu * lnx;
	if (b * z__ == 0.) {
		goto L70;
	}

	/*                 COMPUTATION OF THE EXPANSION */
	/*                 SET R = EXP(-Z)*Z**B/GAMMA(B) */

	r__ = b * (cdflib_gam1(b) + 1.) * exp(b * log(z__));
	r__ = r__ * exp(a * lnx) * exp(bm1 * .5 * lnx);
	u = cdflib_algdiv(b, a) + b * log(nu);
	u = r__ * exp(-u);
	if (u == 0.) {
		goto L70;
	}
	incgam_incgam(b, z__, &p, &q, ierr);
	if (*ierr!=0)
	{
		*ierr = 1;
		return;
	}

	/* Computing 2nd power */
	d__1 = 1. / nu;
	v = d__1 * d__1 * .25;
	t2 = lnx * .25 * lnx;
	l = *w / u;
	j = q / r__;
	sum = j;
	t = 1.;
	cn = 1.;
	n2 = 0.;
	for (n = 1; n <= 30; ++n) {
		bp2n = b + n2;
		j = (bp2n * (bp2n + 1.) * j + (z__ + bp2n + 1.) * t) * v;
		n2 += 2.;
		t *= t2;
		cn /= n2 * (n2 + 1.);
		c__[n - 1] = cn;
		s = 0.;
		if (n == 1) {
			goto L40;
		}
		nm1 = n - 1;
		coef = b - n;
		i__1 = nm1;
		for (i__ = 1; i__ <= i__1; ++i__) {
			s += coef * c__[i__ - 1] * d__[n - i__ - 1];
			coef += b;
			/* L30: */
		}
L40:
		d__[n - 1] = bm1 * cn + s / n;
		dj = d__[n - 1] * j;
		sum += dj;
		if (sum <= 0.) {
			goto L70;
		}
		if (fabs(dj) <= eps * (sum + l)) {
			goto L60;
		}
	}

	/*                    ADD THE RESULTS TO W */
L60:
	*ierr = 0;
	*w += u * sum;
	return;

	/*               THE EXPANSION CANNOT BE COMPUTED */
L70:
	*ierr = 1;
	return;
}

double cdflib_bfrac(double a, double b, double x, double y, 
	double lambda, double eps)
{
	/* System generated locals */
	double ret_val, d__1;

	/* Local variables */
	static double c__, e, n, p, r__, s, t, w, c0, c1, r0, an, bn, yp1, 
		anp1, bnp1, beta, alpha;

	/* ----------------------------------------------------------------------- */
	/*     CONTINUED FRACTION EXPANSION FOR IX(A,B) WHEN A,B > 1. */
	/*     IT IS ASSUMED THAT  LAMBDA = (A + B)*Y - B. */
	/* ----------------------------------------------------------------------- */

	ret_val = cdflib_brcomp(a, b, x, y);
	if (ret_val == 0.) {
		return ret_val;
	}

	c__ = lambda + 1.;
	c0 = b / a;
	c1 = 1. / a + 1.;
	yp1 = y + 1.;

	n = 0.;
	p = 1.;
	s = a + 1.;
	an = 0.;
	bn = 1.;
	anp1 = 1.;
	bnp1 = c__ / c1;
	r__ = c1 / c__;

	/*        CONTINUED FRACTION CALCULATION */

L10:
	n += 1.;
	t = n / a;
	w = n * (b - n) * x;
	e = a / s;
	alpha = p * (p + c0) * e * e * (w * x);
	e = (t + 1.) / (c1 + t + t);
	beta = n + w / s + e * (c__ + n * yp1);
	p = t + 1.;
	s += 2.;

	/*        UPDATE AN, BN, ANP1, AND BNP1 */

	t = alpha * an + beta * anp1;
	an = anp1;
	anp1 = t;
	t = alpha * bn + beta * bnp1;
	bn = bnp1;
	bnp1 = t;

	r0 = r__;
	r__ = anp1 / bnp1;
	if ((d__1 = r__ - r0, fabs(d__1)) <= eps * r__) {
		goto L20;
	}

	/*        RESCALE AN, BN, ANP1, AND BNP1 */

	an /= bnp1;
	bn /= bnp1;
	anp1 = r__;
	bnp1 = 1.;
	goto L10;

	/*                 TERMINATION */

L20:
	ret_val *= r__;
	return ret_val;
}

double cdflib_apser(double a, double b, double x, double eps)
{
	/* Initialized data */

	static double g = .577215664901533;

	/* System generated locals */
	double ret_val;

	/* Local variables */
	static double c__, j, s, t, aj, bx, tol;

	/* ----------------------------------------------------------------------- */
	/*     APSER YIELDS THE INCOMPLETE BETA RATIO I(SUB(1-X))(B,A) FOR */
	/*     A <= MIN(EPS,EPS*B), B*X <= 1, AND X <= 0.5. USED WHEN */
	/*     A IS VERY SMALL. USE ONLY IF ABOVE INEQUALITIES ARE SATISFIED. */
	/* ----------------------------------------------------------------------- */

	bx = b * x;
	t = x - bx;
	if (b * eps > .02) {
		goto L10;
	}
	c__ = log(x) + cdflib_psi1(b) + g + t;
	goto L20;
L10:
	c__ = log(bx) + g + t;

L20:
	tol = eps * 5. * fabs(c__);
	j = 1.;
	s = 0.;
L30:
	j += 1.;
	t *= x - bx / j;
	aj = t / j;
	s += aj;
	if (fabs(aj) > tol) {
		goto L30;
	}

	ret_val = -(a) * (c__ + s);
	return ret_val;
} /* cdflib_apser */

double cdflib_basym(double a, double b, double lambda, double eps)
{
	/* Initialized data */

	static int num = 20;
	static double e0 = 1.12837916709551;
	static double e1 = .353553390593274;

	/* System generated locals */
	int i__1, i__2, i__3, i__4;
	double ret_val, d__1, d__2;

	/* Local variables */
	static double c__[21], d__[21], f, h__;
	static int i__, j, m, n;
	static double r__, s, t, u, w, z__, a0[21], b0[21], h2, j0, j1, r0, 
		r1, t0, t1, w0, z0, z2, hn, zn;
	static int im1, mm1, np1, imj, mmj;
	static double sum, znm1, bsum, dsum;

	/* ----------------------------------------------------------------------- */
	/*     ASYMPTOTIC EXPANSION FOR IX(A,B) FOR LARGE A AND B. */
	/*     LAMBDA = (A + B)*Y - B  AND EPS IS THE TOLERANCE USED. */
	/*     IT IS ASSUMED THAT LAMBDA IS NONNEGATIVE AND THAT */
	/*     A AND B ARE GREATER THAN OR EQUAL TO 15. */
	/* ----------------------------------------------------------------------- */

	/* ------------------------ */
	/*     ****** NUM IS THE MAXIMUM VALUE THAT N CAN TAKE IN THE DO LOOP */
	/*            ENDING AT STATEMENT 50. IT IS REQUIRED THAT NUM BE EVEN. */
	/*            THE ARRAYS A0, B0, C, D HAVE DIMENSION NUM + 1. */

	/* ------------------------ */
	/*     E0 = 2/SQRT(PI) */
	/*     E1 = 2**(-3/2) */
	/* ------------------------ */
	ret_val = 0.;
	if (a >= b) {
		goto L10;
	}
	h__ = a / b;
	r0 = 1. / (h__ + 1.);
	r1 = (b - a) / b;
	w0 = 1. / sqrt(a * (h__ + 1.));
	goto L20;
L10:
	h__ = b / a;
	r0 = 1. / (h__ + 1.);
	r1 = (b - a) / a;
	w0 = 1. / sqrt(b * (h__ + 1.));

L20:
	d__1 = -(lambda) / a;
	d__2 = lambda / b;
	f = a * cdflib_rlog1(d__1) + b * cdflib_rlog1(d__2);
	t = exp(-f);
	if (t == 0.) {
		return ret_val;
	}
	z0 = sqrt(f);
	z__ = z0 / e1 * .5;
	z2 = f + f;

	a0[0] = r1 * .66666666666666663;
	c__[0] = a0[0] * -.5;
	d__[0] = -c__[0];
	j0 = .5 / e0 * cdflib_erfcx(z0);
	j1 = e1;
	sum = j0 + d__[0] * w0 * j1;

	s = 1.;
	h2 = h__ * h__;
	hn = 1.;
	w = w0;
	znm1 = z__;
	zn = z2;
	i__1 = num;
	for (n = 2; n <= i__1; n += 2) {
		hn = h2 * hn;
		a0[n - 1] = r0 * 2. * (h__ * hn + 1.) / (n + 2.);
		np1 = n + 1;
		s += hn;
		a0[np1 - 1] = r1 * 2. * s / (n + 3.);

		i__2 = np1;
		for (i__ = n; i__ <= i__2; ++i__) {
			r__ = (i__ + 1.) * -.5;
			b0[0] = r__ * a0[0];
			i__3 = i__;
			for (m = 2; m <= i__3; ++m) {
				bsum = 0.;
				mm1 = m - 1;
				i__4 = mm1;
				for (j = 1; j <= i__4; ++j) {
					mmj = m - j;
					bsum += (j * r__ - mmj) * a0[j - 1] * b0[mmj - 1];
					/* L30: */
				}
				b0[m - 1] = r__ * a0[m - 1] + bsum / m;
				/* L40: */
			}
			c__[i__ - 1] = b0[i__ - 1] / (i__ + 1.);

			dsum = 0.;
			im1 = i__ - 1;
			i__3 = im1;
			for (j = 1; j <= i__3; ++j) {
				imj = i__ - j;
				dsum += d__[imj - 1] * c__[j - 1];
				/* L50: */
			}
			d__[i__ - 1] = -(dsum + c__[i__ - 1]);
			/* L60: */
		}

		j0 = e1 * znm1 + (n - 1.) * j0;
		j1 = e1 * zn + n * j1;
		znm1 = z2 * znm1;
		zn = z2 * zn;
		w = w0 * w;
		t0 = d__[n - 1] * w * j0;
		w = w0 * w;
		t1 = d__[np1 - 1] * w * j1;
		sum += t0 + t1;
		if (fabs(t0) + fabs(t1) <= eps * sum) {
			goto L80;
		}
		/* L70: */
	}

L80:
	u = exp(-cdflib_bcorr(a, b));
	ret_val = e0 * t * u * sum;
	return ret_val;
}

void cdflib_bratio(double a, double b, double x, 
	double y, double *w, double *w1, int *ierr)
{
	double d1, d2;
	int n;
	double t, z__, a0, b0, x0, y0;
	int ind;
	double eps;
	int ierr1;
	double lambda;


	/* -------------------- */
	/*     WRITTEN BY ALFRED H. MORRIS, JR. */
	/*        NAVAL SURFACE WARFARE CENTER */
	/*        DAHLGREN, VIRGINIA */
	/*     REVISED ... NOV 1991 */
	/* ----------------------------------------------------------------------- */

	/* ----------------------------------------------------------------------- */

	/*     ****** EPS IS A MACHINE DEPENDENT CONSTANT. EPS IS THE SMALLEST */
	/*            FLOATING POINT NUMBER FOR WHICH 1.0 + EPS > 1.0 */

	eps = cdflib_doubleEps();

	/* ----------------------------------------------------------------------- */
	*w = 0.;
	*w1 = 0.;
	if (a < 0. || b < 0.) {
		goto L270;
	}
	if (a == 0. && b == 0.) {
		goto L280;
	}
	if (x < 0. || x > 1.) {
		goto L290;
	}
	if (y < 0. || y > 1.) {
		goto L300;
	}
	z__ = x + y - .5 - .5;
	if (fabs(z__) > eps * 3.) {
		goto L310;
	}

	*ierr = 0;
	if (x == 0.) {
		goto L210;
	}
	if (y == 0.) {
		goto L230;
	}
	if (a == 0.) {
		goto L240;
	}
	if (b == 0.) {
		goto L220;
	}

	eps = cdflib_max(eps,1e-15);
	if (cdflib_max(a,b) < eps * .001) {
		goto L260;
	}

	ind = 0;
	a0 = a;
	b0 = b;
	x0 = x;
	y0 = y;
	if (cdflib_min(a0,b0) > 1.) {
		goto L40;
	}

	/*             PROCEDURE FOR A0 <= 1 OR B0 <= 1 */

	if (x <= .5) {
		goto L10;
	}
	ind = 1;
	a0 = b;
	b0 = a;
	x0 = y;
	y0 = x;

L10:
	/* Computing MIN */
	d1 = eps, d2 = eps * a0;
	if (b0 < cdflib_min(d1,d2)) {
		goto L90;
	}
	/* Computing MIN */
	d1 = eps, d2 = eps * b0;
	if (a0 < cdflib_min(d1,d2) && b0 * x0 <= 1.) {
		goto L100;
	}
	if (cdflib_max(a0,b0) > 1.) {
		goto L20;
	}
	if (a0 >= cdflib_min(.2,b0)) {
		goto L110;
	}
	if (cdflib_powdd(x0, a0) <= .9) {
		goto L110;
	}
	if (x0 >= .3) {
		goto L120;
	}
	n = 20;
	goto L140;

L20:
	if (b0 <= 1.) {
		goto L110;
	}
	if (x0 >= .3) {
		goto L120;
	}
	if (x0 >= .1) {
		goto L30;
	}
	d1 = x0 * b0;
	if (cdflib_powdd(d1, a0) <= .7) {
		goto L110;
	}
L30:
	if (b0 > 15.) {
		goto L150;
	}
	n = 20;
	goto L140;

	/*             PROCEDURE FOR A0 > 1 AND B0 > 1 */

L40:
	if (a > b) {
		goto L50;
	}
	lambda = a - (a + b) * x;
	goto L60;
L50:
	lambda = (a + b) * y - b;
L60:
	if (lambda >= 0.) {
		goto L70;
	}
	ind = 1;
	a0 = b;
	b0 = a;
	x0 = y;
	y0 = x;
	lambda = fabs(lambda);

L70:
	if (b0 < 40. && b0 * x0 <= .7) {
		goto L110;
	}
	if (b0 < 40.) {
		goto L160;
	}
	if (a0 > b0) {
		goto L80;
	}
	if (a0 <= 100.) {
		goto L130;
	}
	if (lambda > a0 * .03) {
		goto L130;
	}
	goto L200;
L80:
	if (b0 <= 100.) {
		goto L130;
	}
	if (lambda > b0 * .03) {
		goto L130;
	}
	goto L200;

	/*            EVALUATION OF THE APPROPRIATE ALGORITHM */

L90:
	*w = cdflib_fpser(a0, b0, x0, eps);
	*w1 = .5 - *w + .5;
	goto L250;

L100:
	*w1 = cdflib_apser(a0, b0, x0, eps);
	*w = .5 - *w1 + .5;
	goto L250;

L110:
	*w = cdflib_bpser(a0, b0, x0, eps);
	*w1 = .5 - *w + .5;
	goto L250;

L120:
	*w1 = cdflib_bpser(b0, a0, y0, eps);
	*w = .5 - *w1 + .5;
	goto L250;

L130:
	d1 = eps * 15.;
	*w = cdflib_bfrac(a0, b0, x0, y0, lambda, d1);
	*w1 = .5 - *w + .5;
	goto L250;

L140:
	*w1 = cdflib_bup(b0, a0, y0, x0, n, eps);
	b0 += n;
L150:
	d1 = eps * 15.;
	cdflib_bgrat(b0, a0, y0, x0, w1, d1, &ierr1);
	*w = .5 - *w1 + .5;
	goto L250;

L160:
	n = (int) b0;
	b0 -= n;
	if (b0 != 0.) {
		goto L170;
	}
	--n;
	b0 = 1.;
L170:
	*w = cdflib_bup(b0, a0, y0, x0, n, eps);
	if (x0 > .7) {
		goto L180;
	}
	*w += cdflib_bpser(a0, b0, x0, eps);
	*w1 = .5 - *w + .5;
	goto L250;

L180:
	if (a0 > 15.) {
		goto L190;
	}
	n = 20;
	*w += cdflib_bup(a0, b0, x0, y0, n, eps);
	a0 += n;
L190:
	d1 = eps * 15.;
	cdflib_bgrat(a0, b0, x0, y0, w, d1, &ierr1);
	*w1 = .5 - *w + .5;
	goto L250;

L200:
	d1 = eps * 100.;
	*w = cdflib_basym(a0, b0, lambda, d1);
	*w1 = .5 - *w + .5;
	goto L250;

	/*               TERMINATION OF THE PROCEDURE */

L210:
	if (a == 0.) {
		goto L320;
	}
L220:
	*w = 0.;
	*w1 = 1.;
	return;

L230:
	if (b == 0.) {
		goto L330;
	}
L240:
	*w = 1.;
	*w1 = 0.;
	return;

L250:
	if (ind == 0) {
		return;
	}
	t = *w;
	*w = *w1;
	*w1 = t;
	return;

	/*           PROCEDURE FOR A AND B < 1.E-3*EPS */

L260:
	*w = b / (a + b);
	*w1 = a / (a + b);
	return;

	/*                       ERROR RETURN */

L270:
	*ierr = 1;
	return;
L280:
	*ierr = 2;
	return;
L290:
	*ierr = 3;
	return;
L300:
	*ierr = 4;
	return;
L310:
	*ierr = 5;
	return;
L320:
	*ierr = 6;
	return;
L330:
	*ierr = 7;
	return;
}

double cdflib_algdiv(double a, double b)
{
	/* Initialized data */

	static double c0 = .0833333333333333;
	static double c1 = -.00277777777760991;
	static double c2 = 7.9365066682539e-4;
	static double c3 = -5.9520293135187e-4;
	static double c4 = 8.37308034031215e-4;
	static double c5 = -.00165322962780713;

	/* System generated locals */
	double ret_val, d__1;

	/* Local variables */
	static double c__, d__, h__, t, u, v, w, x, s3, s5, s7, x2, s9, s11;

	/* ----------------------------------------------------------------------- */

	/*     COMPUTATION OF LN(GAMMA(B)/GAMMA(A+B)) WHEN B >= 8 */

	/*                         -------- */

	/*     IN THIS ALGORITHM, DEL(X) IS THE FUNCTION DEFINED BY */
	/*     LN(GAMMA(X)) = (X - 0.5)*LN(X) - X + 0.5*LN(2*PI) + DEL(X). */

	/* ----------------------------------------------------------------------- */

	if (a <= b) {
		goto L10;
	}
	h__ = b / a;
	c__ = 1. / (h__ + 1.);
	x = h__ / (h__ + 1.);
	d__ = a + (b - .5);
	goto L20;
L10:
	h__ = a / b;
	c__ = h__ / (h__ + 1.);
	x = 1. / (h__ + 1.);
	d__ = b + (a - .5);

	/*                SET SN = (1 - X**N)/(1 - X) */

L20:
	x2 = x * x;
	s3 = x + x2 + 1.;
	s5 = x + x2 * s3 + 1.;
	s7 = x + x2 * s5 + 1.;
	s9 = x + x2 * s7 + 1.;
	s11 = x + x2 * s9 + 1.;

	/*                SET W = DEL(B) - DEL(A + B) */

	/* Computing 2nd power */
	d__1 = 1. / b;
	t = d__1 * d__1;
	w = ((((c5 * s11 * t + c4 * s9) * t + c3 * s7) * t + c2 * s5) * t + c1 * 
		s3) * t + c0;
	w *= c__ / b;

	/*                    COMBINE THE RESULTS */

	d__1 = a / b;
	u = d__ * cdflib_log1p(d__1);
	v = a * (log(b) - 1.);
	if (u <= v) {
		goto L30;
	}
	ret_val = w - v - u;
	return ret_val;
L30:
	ret_val = w - u - v;
	return ret_val;
}

double cdflib_fpser(double a, double b, double x, double eps)
{
	/* System generated locals */
	double ret_val;

	/* Local variables */
	static double c__, s, t, an, tol;

	/* ----------------------------------------------------------------------- */
	/*                 EVALUATION OF I (A,B) */
	/*                                X */
	/*          FOR B .LT. MIN(EPS,EPS*A) AND X .LE. 0.5. */
	/* ----------------------------------------------------------------------- */

	/*                  SET  FPSER = X**A */

	ret_val = 1.;
	if (a <= eps * .001) {
		goto L10;
	}
	ret_val = 0.;
	t = a * log(x);
	if (t < cdflib_exparg(1)) {
		return ret_val;
	}
	ret_val = exp(t);

	/*                NOTE THAT 1/B(A,B) = B */

L10:
	ret_val = b / a * ret_val;
	tol = eps / a;
	an = a + 1.;
	t = x;
	s = t / an;
L20:
	an += 1.;
	t = x * t;
	c__ = t / an;
	s += c__;
	if (fabs(c__) > tol) {
		goto L20;
	}

	ret_val *= a * s + 1.;
	return ret_val;
}

