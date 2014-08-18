/* gamma.f -- translated by f2c (version 20100827).
with manual tuning by Michael Baudin (2012)
*/

#include <math.h>

#include "cdflib.h"
#include "cdflib_private.h" 

double cdflib_gamln1(double a)
{
	/* Initialized data */

	static double p0 = .577215664901533;
	static double q3 = 1.56875193295039;
	static double q4 = .361951990101499;
	static double q5 = .0325038868253937;
	static double q6 = 6.67465618796164e-4;
	static double r0 = .422784335098467;
	static double r1 = .848044614534529;
	static double r2 = .565221050691933;
	static double r3 = .156513060486551;
	static double r4 = .017050248402265;
	static double r5 = 4.97958207639485e-4;
	static double p1 = .844203922187225;
	static double s1 = 1.24313399877507;
	static double s2 = .548042109832463;
	static double s3 = .10155218743983;
	static double s4 = .00713309612391;
	static double s5 = 1.16165475989616e-4;
	static double p2 = -.168860593646662;
	static double p3 = -.780427615533591;
	static double p4 = -.402055799310489;
	static double p5 = -.0673562214325671;
	static double p6 = -.00271935708322958;
	static double q1 = 2.88743195473681;
	static double q2 = 3.12755088914843;

	/* System generated locals */
	double ret_val;

	/* Local variables */
	static double w, x;

	/* ----------------------------------------------------------------------- */
	/*     EVALUATION OF LN(GAMMA(1 + A)) FOR -0.2 <= A <= 1.25 */
	/* ----------------------------------------------------------------------- */

	if (a >= .6) {
		goto L10;
	}
	w = ((((((p6 * a + p5) * a + p4) * a + p3) * a + p2) * a + p1) * a 
		+ p0) / ((((((q6 * a + q5) * a + q4) * a + q3) * a + q2) * a 
		+ q1) * a + 1.);
	ret_val = -(a) * w;
	return ret_val;

L10:
	x = a - .5 - .5;
	w = (((((r5 * x + r4) * x + r3) * x + r2) * x + r1) * x + r0) / (((((s5 * 
		x + s4) * x + s3) * x + s2) * x + s1) * x + 1.);
	ret_val = x * w;
	return ret_val;
}

double cdflib_gam1(double a)
{
	/* Initialized data */

	static double p[7] = { .577215664901533,-.409078193005776,
		-.230975380857675,.0597275330452234,.0076696818164949,
		-.00514889771323592,5.89597428611429e-4 };
	static double q[5] = { 1.,.427569613095214,.158451672430138,
		.0261132021441447,.00423244297896961 };
	static double r__[9] = { -.422784335098468,-.771330383816272,
		-.244757765222226,.118378989872749,9.30357293360349e-4,
		-.0118290993445146,.00223047661158249,2.66505979058923e-4,
		-1.32674909766242e-4 };
	static double s1 = .273076135303957;
	static double s2 = .0559398236957378;

	/* System generated locals */
	double ret_val;

	/* Local variables */
	static double d__, t, w, bot, top;

	/*     ------------------------------------------------------------------ */
	/*     COMPUTATION OF 1/GAMMA(A+1) - 1  FOR -0.5 <= A <= 1.5 */
	/*     ------------------------------------------------------------------ */

	t = a;
	d__ = a - .5;
	if (d__ > 0.) {
		t = d__ - .5;
	}
	if (t < 0.) {
		goto L40;
	} else if (t == 0.) {
		goto L10;
	} else {
		goto L20;
	}

L10:
	ret_val = 0.;
	return ret_val;

L20:
	top = (((((p[6] * t + p[5]) * t + p[4]) * t + p[3]) * t + p[2]) * t + p[1]
	) * t + p[0];
	bot = (((q[4] * t + q[3]) * t + q[2]) * t + q[1]) * t + 1.;
	w = top / bot;
	if (d__ > 0.) {
		goto L30;
	}
	ret_val = a * w;
	return ret_val;
L30:
	ret_val = t / a * (w - .5 - .5);
	return ret_val;

L40:
	top = (((((((r__[8] * t + r__[7]) * t + r__[6]) * t + r__[5]) * t + r__[4]
	) * t + r__[3]) * t + r__[2]) * t + r__[1]) * t + r__[0];
	bot = (s2 * t + s1) * t + 1.;
	w = top / bot;
	if (d__ > 0.) {
		goto L50;
	}
	ret_val = a * (w + .5 + .5);
	return ret_val;
L50:
	ret_val = t * w / a;
	return ret_val;
}

double cdflib_bcorr(double a0, double b0)
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
	static double a, b, c__, h__, t, w, x, s3, s5, s7, x2, s9, s11;

	/* ----------------------------------------------------------------------- */

	/*     EVALUATION OF  DEL(A0) + DEL(B0) - DEL(A0 + B0)  WHERE */
	/*     LN(GAMMA(A)) = (A - 0.5)*LN(A) - A + 0.5*LN(2*PI) + DEL(A). */
	/*     IT IS ASSUMED THAT A0 >= 8 AND B0 >= 8. */

	/* ----------------------------------------------------------------------- */

	a = cdflib_min(a0,b0);
	b = cdflib_max(a0,b0);

	h__ = a / b;
	c__ = h__ / (h__ + 1.);
	x = 1. / (h__ + 1.);
	x2 = x * x;

	/*                SET SN = (1 - X**N)/(1 - X) */

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

	/*                   COMPUTE  DEL(A) + W */

	/* Computing 2nd power */
	d__1 = 1. / a;
	t = d__1 * d__1;
	ret_val = (((((c5 * t + c4) * t + c3) * t + c2) * t + c1) * t + c0) / a + 
		w;
	return ret_val;
}

double cdflib_rcomp(double a, double x)
{
	/*     ------------------- */
	/*     EVALUATION OF EXP(-X)*X**A/GAMMA(A) */
	/*     ------------------- */

	/* Initialized data */

	/*     ------------------- */
	/*     RT2PIN = 1/SQRT(2*PI) */
	/*     ------------------- */
	static double rt2pin = .398942280401433;

	/* System generated locals */
	double ret_val, d__1;

	/* Local variables */
	static double t, u, t1;

	ret_val = 0.;
	if (a >= 20.) {
		goto L20;
	}
	t = a * log(x) - x;
	if (a >= 1.) {
		goto L10;
	}
	ret_val = a * exp(t) * (cdflib_gam1(a) + 1.);
	return ret_val;
L10:
	ret_val = exp(t) / incgam_gamma(a);
	return ret_val;

L20:
	u = x / a;
	if (u == 0.) {
		return ret_val;
	}
	/* Computing 2nd power */
	d__1 = 1. / a;
	t = d__1 * d__1;
	t1 = (((t * .75 - 1.) * t + 3.5) * t - 105.) / (a * 1260.);
	t1 -= a * cdflib_rlog(u);
	ret_val = rt2pin * sqrt(a) * exp(t1);
	return ret_val;
}

double cdflib_psi1(double xx)
{
	/* Initialized data */

	static double piov4 = .785398163397448;
	static double dx0 = 1.461632144968362341262659542325721325;
	static double p1[7] = { .0089538502298197,4.77762828042627,
		142.441585084029,1186.45200713425,3633.51846806499,
		4138.10161269013,1305.60269827897 };
	static double q1[6] = { 44.8452573429826,520.752771467162,
		2210.0079924783,3641.27349079381,1908.310765963,
		6.91091682714533e-6 };
	static double p2[4] = { -2.12940445131011,-7.01677227766759,
		-4.48616543918019,-.648157123766197 };
	static double q2[4] = { 32.2703493791143,89.2920700481861,
		54.6117738103215,7.77788548522962 };

	/* System generated locals */
	double ret_val, d__1, d__2;

	/* Local variables */
	static int i__, m, n;
	static double w, x, z__;
	static int nq;
	static double den, aug, sgn, xmx0, xmax1, upper;
	static double xsmall;

	/* --------------------------------------------------------------------- */

	/*                 EVALUATION OF THE DIGAMMA FUNCTION */

	/*                           ----------- */

	/*     PSI1(XX) IS ASSIGNED THE VALUE 0 WHEN THE DIGAMMA FUNCTION CANNOT */
	/*     BE COMPUTED. */

	/*     THE MAIN COMPUTATION INVOLVES EVALUATION OF RATIONAL CHEBYSHEV */
	/*     APPROXIMATIONS PUBLISHED IN MATH. COMP. 27, 123-127(1973) BY */
	/*     CODY, STRECOK AND THACHER. */

	/* --------------------------------------------------------------------- */
	/*     PSI1 WAS WRITTEN AT ARGONNE NATIONAL LABORATORY FOR THE FUNPACK */
	/*     PACKAGE OF SPECIAL FUNCTION SUBROUTINES. PSI1 WAS MODIFIED BY */
	/*     A.H. MORRIS (NSWC). */
	/* --------------------------------------------------------------------- */

	/*     PIOV4 = PI/4 */
	/*     DX0 = ZERO OF PSI1 TO EXTENDED PRECISION */

	/* --------------------------------------------------------------------- */
	/* --------------------------------------------------------------------- */

	/*     COEFFICIENTS FOR RATIONAL APPROXIMATION OF */
	/*     PSI1(X) / (X - X0),  0.5 <= X <= 3.0 */

	/* --------------------------------------------------------------------- */
	/* --------------------------------------------------------------------- */

	/*     COEFFICIENTS FOR RATIONAL APPROXIMATION OF */
	/*     PSI1(X) - LN(X) + 1 / (2*X),  X > 3.0 */

	/* --------------------------------------------------------------------- */

	/* --------------------------------------------------------------------- */

	/*     MACHINE DEPENDENT CONSTANTS ... */

	/*        XMAX1  = THE SMALLEST POSITIVE FLOATING POINT CONSTANT */
	/*                 WITH ENTIRELY INTEGER REPRESENTATION.  ALSO USED */
	/*                 AS NEGATIVE OF LOWER BOUND ON ACCEPTABLE NEGATIVE */
	/*                 ARGUMENTS AND AS THE POSITIVE ARGUMENT BEYOND WHICH */
	/*                 PSI1 MAY BE REPRESENTED AS ALOG(X). */

	/*        XSMALL = ABSOLUTE ARGUMENT BELOW WHICH PI*COTAN(PI*X) */
	/*                 MAY BE REPRESENTED BY 1/X. */

	/* --------------------------------------------------------------------- */
	xmax1 = (double) cdflib_largestint();
	/* Computing MIN */
	d__1 = xmax1, d__2 = 1. / cdflib_doubleEps();
	xmax1 = cdflib_min(d__1,d__2);
	xsmall = 1e-9;
	/* --------------------------------------------------------------------- */
	x = xx;
	aug = 0.;
	if (x >= .5) {
		goto L50;
	}
	/* --------------------------------------------------------------------- */
	/*     X < 0.5,  USE REFLECTION FORMULA */
	/*     PSI1(1-X) = PSI1(X) + PI * COTAN(PI*X) */
	/* --------------------------------------------------------------------- */
	if (fabs(x) > xsmall) {
		goto L10;
	}
	if (x == 0.) {
		goto L100;
	}
	/* --------------------------------------------------------------------- */
	/*     0 < ABS(X) <= XSMALL.  USE 1/X AS A SUBSTITUTE */
	/*     FOR  PI*COTAN(PI*X) */
	/* --------------------------------------------------------------------- */
	aug = -1. / x;
	goto L40;
	/* --------------------------------------------------------------------- */
	/*     REDUCTION OF ARGUMENT FOR COTAN */
	/* --------------------------------------------------------------------- */
L10:
	w = -x;
	sgn = piov4;
	if (w > 0.) {
		goto L20;
	}
	w = -w;
	sgn = -sgn;
	/* --------------------------------------------------------------------- */
	/*     MAKE AN ERROR EXIT IF X <= -XMAX1 */
	/* --------------------------------------------------------------------- */
L20:
	if (w >= xmax1) {
		goto L100;
	}
	nq = (int) w;
	w -= (double) nq;
	nq = (int) (w * 4.);
	w = (w - (double) nq * .25) * 4.;
	/* --------------------------------------------------------------------- */
	/*     W IS NOW RELATED TO THE FRACTIONAL PART OF  4.0 * X. */
	/*     ADJUST ARGUMENT TO CORRESPOND TO VALUES IN FIRST */
	/*     QUADRANT AND DETERMINE SIGN */
	/* --------------------------------------------------------------------- */
	n = nq / 2;
	if (n + n != nq) {
		w = 1. - w;
	}
	z__ = piov4 * w;
	m = n / 2;
	if (m + m != n) {
		sgn = -sgn;
	}
	/* --------------------------------------------------------------------- */
	/*     DETERMINE FINAL VALUE FOR  -PI*COTAN(PI*X) */
	/* --------------------------------------------------------------------- */
	n = (nq + 1) / 2;
	m = n / 2;
	m += m;
	if (m != n) {
		goto L30;
	}
	/* --------------------------------------------------------------------- */
	/*     CHECK FOR SINGULARITY */
	/* --------------------------------------------------------------------- */
	if (z__ == 0.) {
		goto L100;
	}
	/* --------------------------------------------------------------------- */
	/*     USE COS/SIN AS A SUBSTITUTE FOR COTAN, AND */
	/*     SIN/COS AS A SUBSTITUTE FOR TAN */
	/* --------------------------------------------------------------------- */
	aug = sgn * (cos(z__) / sin(z__) * 4.);
	goto L40;
L30:
	aug = sgn * (sin(z__) / cos(z__) * 4.);
L40:
	x = 1. - x;
L50:
	if (x > 3.) {
		goto L70;
	}
	/* --------------------------------------------------------------------- */
	/*     0.5 <= X <= 3.0 */
	/* --------------------------------------------------------------------- */
	den = x;
	upper = p1[0] * x;

	for (i__ = 1; i__ <= 5; ++i__) {
		den = (den + q1[i__ - 1]) * x;
		upper = (upper + p1[i__]) * x;
		/* L60: */
	}

	den = (upper + p1[6]) / (den + q1[5]);
	xmx0 = x - dx0;
	ret_val = den * xmx0 + aug;
	return ret_val;
	/* --------------------------------------------------------------------- */
	/*     IF X >= XMAX1, PSI1 = LN(X) */
	/* --------------------------------------------------------------------- */
L70:
	if (x >= xmax1) {
		goto L90;
	}
	/* --------------------------------------------------------------------- */
	/*     3.0 < X < XMAX1 */
	/* --------------------------------------------------------------------- */
	w = 1. / (x * x);
	den = w;
	upper = p2[0] * w;

	for (i__ = 1; i__ <= 3; ++i__) {
		den = (den + q2[i__ - 1]) * w;
		upper = (upper + p2[i__]) * w;
		/* L80: */
	}

	aug = upper / (den + q2[3]) - .5 / x + aug;
L90:
	ret_val = aug + log(x);
	return ret_val;
	/* --------------------------------------------------------------------- */
	/*     ERROR RETURN */
	/* --------------------------------------------------------------------- */
L100:
	ret_val = 0.;
	return ret_val;
}

double cdflib_gsumln(double a, double b)
{
	/* System generated locals */
	double ret_val, d__1;

	/* Local variables */
	static double x;

	/* ----------------------------------------------------------------------- */
	/*          EVALUATION OF THE FUNCTION LN(GAMMA(A + B)) */
	/*          FOR 1 <= A <= 2  AND  1 <= B <= 2 */
	/* ----------------------------------------------------------------------- */

	x = a + b - 2.;
	if (x > .25) {
		goto L10;
	}
	d__1 = x + 1.;
	ret_val = cdflib_gamln1(d__1);
	return ret_val;
L10:
	if (x > 1.25) {
		goto L20;
	}
	ret_val = cdflib_gamln1(x) + cdflib_log1p(x);
	return ret_val;
L20:
	d__1 = x - 1.;
	ret_val = cdflib_gamln1(d__1) + log(x * (x + 1.));
	return ret_val;
}

