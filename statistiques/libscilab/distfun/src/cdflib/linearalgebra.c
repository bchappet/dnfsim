// Copyright (C) 2012 - 2014 - Michael Baudin
// Copyright (C) 1978 - Jack Dongarra
// Copyright (C) CLEVE MOLER
//
// This file must be used under the terms of the GNU Lesser General Public License license :
// http://www.gnu.org/copyleft/lesser.html

#include <math.h>
#include <stdio.h>

#include "cdflib.h"
#include "cdflib_private.h"

void dscal_(int *n, double *da, double *dx, int *incx);
int lsame_(char *ca, char *cb);
void xerbla_(char *srname, int *info);
void dspr_(char *uplo, int *n, double *alpha, double *x, int *incx, double *ap);
double ddot_(int *, double *, int *, double *, int *);


/* Table of constant values */

static int c__1 = 1;
static double c_b20 = -1.;


void dpptrf_(char *uplo, int *n, double *ap, int *
	info, int uplo_len)
{
	/* System generated locals */
	int i__1, i__2;
	double d__1;

	/* Builtin functions */
	double sqrt(double);

	/* Local variables */
	static int j, jc, jj;
	static double ajj;
	static int upper;

	/*  -- LAPACK routine (version 3.3.1) -- */
	/*  -- LAPACK is a software package provided by Univ. of Tennessee,    -- */
	/*  -- Univ. of California Berkeley, Univ. of Colorado Denver and NAG Ltd..-- */
	/*  -- April 2011                                                      -- */

	/*  Purpose */
	/*  ======= */

	/*  DPPTRF computes the Cholesky factorization of a real symmetric */
	/*  positive definite matrix A stored in packed format. */

	/*  The factorization has the form */
	/*     A = U**T * U,  if UPLO = 'U', or */
	/*     A = L  * L**T,  if UPLO = 'L', */
	/*  where U is an upper triangular matrix and L is lower triangular. */

	/*  Arguments */
	/*  ========= */

	/*  UPLO    (input) CHARACTER*1 */
	/*          = 'U':  Upper triangle of A is stored; */
	/*          = 'L':  Lower triangle of A is stored. */

	/*  N       (input) INTEGER */
	/*          The order of the matrix A.  N >= 0. */

	/*  AP      (input/output) DOUBLE PRECISION array, dimension (N*(N+1)/2) */
	/*          On entry, the upper or lower triangle of the symmetric matrix */
	/*          A, packed columnwise in a linear array.  The j-th column of A */
	/*          is stored in the array AP as follows: */
	/*          if UPLO = 'U', AP(i + (j-1)*j/2) = A(i,j) for 1<=i<=j; */
	/*          if UPLO = 'L', AP(i + (j-1)*(2n-j)/2) = A(i,j) for j<=i<=n. */
	/*          See below for further details. */

	/*          On exit, if INFO = 0, the triangular factor U or L from the */
	/*          Cholesky factorization A = U**T*U or A = L*L**T, in the same */
	/*          storage format as A. */

	/*  INFO    (output) INTEGER */
	/*          = 0:  successful exit */
	/*          < 0:  if INFO = -i, the i-th argument had an illegal value */
	/*          > 0:  if INFO = i, the leading minor of order i is not */
	/*                positive definite, and the factorization could not be */
	/*                completed. */

	/*  Further Details */
	/*  ======= ======= */

	/*  The packed storage scheme is illustrated by the following example */
	/*  when N = 4, UPLO = 'U': */

	/*  Two-dimensional storage of the symmetric matrix A: */

	/*     a11 a12 a13 a14 */
	/*         a22 a23 a24 */
	/*             a33 a34     (aij = aji) */
	/*                 a44 */

	/*  Packed storage of the upper triangle of A: */

	/*  AP = [ a11, a12, a22, a13, a23, a33, a14, a24, a34, a44 ] */

	/*  ===================================================================== */

	/*     Test the input parameters. */

	/* Parameter adjustments */
	--ap;

	/* Function Body */
	*info = 0;
	upper = lsame_(uplo, "U");
	if (! upper && ! lsame_(uplo, "L")) {
		*info = -1;
	} else if (*n < 0) {
		*info = -2;
	}
	if (*info != 0) {
		i__1 = -(*info);
		xerbla_("DPPTRF", &i__1);
		return;
	}

	/*     Quick return if possible */

	if (*n == 0) {
		return;
	}

	if (upper) {

		/*        Compute the Cholesky factorization A = U**T*U. */

		jj = 0;
		i__1 = *n;
		for (j = 1; j <= i__1; ++j) {
			jc = jj + 1;
			jj += j;

			/*           Compute elements 1:J-1 of column J. */

			if (j > 1) {
				i__2 = j - 1;
				dtpsv_("Upper", "Transpose", "Non-unit", &i__2, &ap[1], &ap[jc], &c__1);
			}

			/*           Compute U(J,J) and test for non-positive-definiteness. */

			i__2 = j - 1;
			ajj = ap[jj] - ddot_(&i__2, &ap[jc], &c__1, &ap[jc], &c__1);
			if (ajj <= 0.) {
				ap[jj] = ajj;
				goto L30;
			}
			ap[jj] = sqrt(ajj);
			/* L10: */
		}
	} else {

		/*        Compute the Cholesky factorization A = L*L**T. */

		jj = 1;
		i__1 = *n;
		for (j = 1; j <= i__1; ++j) {

			/*           Compute L(J,J) and test for non-positive-definiteness. */

			ajj = ap[jj];
			if (ajj <= 0.) {
				ap[jj] = ajj;
				goto L30;
			}
			ajj = sqrt(ajj);
			ap[jj] = ajj;

			/*           Compute elements J+1:N of column J and update the trailing */
			/*           submatrix. */

			if (j < *n) {
				i__2 = *n - j;
				d__1 = 1. / ajj;
				dscal_(&i__2, &d__1, &ap[jj + 1], &c__1);
				i__2 = *n - j;
				dspr_("Lower", &i__2, &c_b20, &ap[jj + 1], &c__1, &ap[jj + *n- j + 1]);
				jj = jj + *n - j + 1;
			}
			/* L20: */
		}
	}
	goto L40;

L30:
	*info = j;

L40:
	return;
}


/* > \brief \b LSAME */

/*  =========== DOCUMENTATION =========== */

/* Online html documentation available at */
/*            http://www.netlib.org/lapack/explore-html/ */

/*  Definition: */
/*  =========== */

/*      LOGICAL FUNCTION LSAME( CA, CB ) */

/*     .. Scalar Arguments .. */
/*      CHARACTER          CA, CB */
/*     .. */


/* > \par Purpose: */
/*  ============= */
/* > */
/* > \verbatim */
/* > */
/* > LSAME returns .TRUE. if CA is the same letter as CB regardless of */
/* > case. */
/* > \endverbatim */

/*  Arguments: */
/*  ========== */

/* > \param[in] CA */
/* > \verbatim */
/* > \endverbatim */
/* > */
/* > \param[in] CB */
/* > \verbatim */
/* >          CA and CB specify the single characters to be compared. */
/* > \endverbatim */

/*  Authors: */
/*  ======== */

/* > \author Univ. of Tennessee */
/* > \author Univ. of California Berkeley */
/* > \author Univ. of Colorado Denver */
/* > \author NAG Ltd. */

/* > \date November 2011 */

/* > \ingroup auxOTHERauxiliary */

/*  ===================================================================== */
int lsame_(char *ca, char *cb)
{
	/* System generated locals */
	int ret_val;

	/* Local variables */
	static int inta, intb, zcode;


	/*  -- LAPACK auxiliary routine (version 3.4.0) -- */
	/*  -- LAPACK is a software package provided by Univ. of Tennessee,    -- */
	/*  -- Univ. of California Berkeley, Univ. of Colorado Denver and NAG Ltd..-- */
	/*     November 2011 */
	/* ===================================================================== */
	/*     Test if the characters are equal */

	ret_val = *(unsigned char *)ca == *(unsigned char *)cb;
	if (ret_val) {
		return ret_val;
	}

	/*     Now test for equivalence if both characters are alphabetic. */

	zcode = 'Z';

	/*     Use 'Z' rather than 'A' so that ASCII can be detected on Prime */
	/*     machines, on which ICHAR returns a value with bit 8 set. */
	/*     ICHAR('A') on Prime machines returns 193 which is the same as */
	/*     ICHAR('A') on an EBCDIC machine. */

	inta = *(unsigned char *)ca;
	intb = *(unsigned char *)cb;

	if (zcode == 90 || zcode == 122) {

		/*        ASCII is assumed - ZCODE is the ASCII code of either lower or */
		/*        upper case 'Z'. */

		if (inta >= 97 && inta <= 122) {
			inta += -32;
		}
		if (intb >= 97 && intb <= 122) {
			intb += -32;
		}

	} else if (zcode == 233 || zcode == 169) {

		/*        EBCDIC is assumed - ZCODE is the EBCDIC code of either lower or */
		/*        upper case 'Z'. */

		if (inta >= 129 && inta <= 137 || inta >= 145 && inta <= 153 || inta 
			>= 162 && inta <= 169) {
				inta += 64;
		}
		if (intb >= 129 && intb <= 137 || intb >= 145 && intb <= 153 || intb 
			>= 162 && intb <= 169) {
				intb += 64;
		}

	} else if (zcode == 218 || zcode == 250) {

		/*        ASCII is assumed, on Prime machines - ZCODE is the ASCII code */
		/*        plus 128 of either lower or upper case 'Z'. */

		if (inta >= 225 && inta <= 250) {
			inta += -32;
		}
		if (intb >= 225 && intb <= 250) {
			intb += -32;
		}
	}
	ret_val = inta == intb;

	return ret_val;
}

/* > \brief \b XERBLA */

/*  =========== DOCUMENTATION =========== */

/* Online html documentation available at */
/*            http://www.netlib.org/lapack/explore-html/ */

/* > \htmlonly */
/* > Download XERBLA + dependencies */
/* > <a href="http://www.netlib.org/cgi-bin/netlibfiles.tgz?format=tgz&filename=/lapack/lapack_routine/xerbla.
f"> */
/* > [TGZ]</a> */
/* > <a href="http://www.netlib.org/cgi-bin/netlibfiles.zip?format=zip&filename=/lapack/lapack_routine/xerbla.
f"> */
/* > [ZIP]</a> */
/* > <a href="http://www.netlib.org/cgi-bin/netlibfiles.txt?format=txt&filename=/lapack/lapack_routine/xerbla.
f"> */
/* > [TXT]</a> */
/* > \endhtmlonly */

/*  Definition: */
/*  =========== */

/*       SUBROUTINE XERBLA( SRNAME, INFO ) */

/*       .. Scalar Arguments .. */
/*       CHARACTER*(*)      SRNAME */
/*       INTEGER            INFO */
/*       .. */


/* > \par Purpose: */
/*  ============= */
/* > */
/* > \verbatim */
/* > */
/* > XERBLA  is an error handler for the LAPACK routines. */
/* > It is called by an LAPACK routine if an input parameter has an */
/* > invalid value.  A message is printed and execution stops. */
/* > */
/* > Installers may consider modifying the STOP statement in order to */
/* > call system-specific exception-handling facilities. */
/* > \endverbatim */

/*  Arguments: */
/*  ========== */

/* > \param[in] SRNAME */
/* > \verbatim */
/* >          SRNAME is CHARACTER*(*) */
/* >          The name of the routine which called XERBLA. */
/* > \endverbatim */
/* > */
/* > \param[in] INFO */
/* > \verbatim */
/* >          INFO is INTEGER */
/* >          The position of the invalid parameter in the parameter list */
/* >          of the calling routine. */
/* > \endverbatim */

/*  Authors: */
/*  ======== */

/* > \author Univ. of Tennessee */
/* > \author Univ. of California Berkeley */
/* > \author Univ. of Colorado Denver */
/* > \author NAG Ltd. */

/* > \date November 2011 */

/* > \ingroup auxOTHERauxiliary */

/*  ===================================================================== */
void xerbla_(char *srname, int *info)
{

	char buff[512];
	/*  -- LAPACK auxiliary routine (version 3.4.0) -- */
	/*  -- LAPACK is a software package provided by Univ. of Tennessee,    -- */
	/*  -- Univ. of California Berkeley, Univ. of Colorado Denver and NAG Ltd..-- */
	/*     November 2011 */

	/* ===================================================================== */

	sprintf(buff,"XERBLA : %s\n",srname);
	cdflib_messageprint(buff);
	sprintf(buff,"XERBLA : info=%d\n",info);
	cdflib_messageprint(buff);

	return;
}

void dscal_(int *n, double *da, double *dx, int *incx)
{
	/* System generated locals */
	int i__1, i__2;
	/* Local variables */
	static int i__, m, nincx, mp1;
	/*     scales a vector by a constant.   
	uses unrolled loops for increment equal to one.   
	jack dongarra, linpack, 3/11/78.   
	modified 3/93 to return if incx .le. 0.   
	modified 12/3/93, array(1) declarations changed to array(*)   
	Parameter adjustments */
	--dx;
	/* Function Body */
	if (*n <= 0 || *incx <= 0) {
		return;
	}
	if (*incx == 1) {
		goto L20;
	}
	/*        code for increment not equal to 1 */
	nincx = *n * *incx;
	i__1 = nincx;
	i__2 = *incx;
	for (i__ = 1; i__2 < 0 ? i__ >= i__1 : i__ <= i__1; i__ += i__2) {
		dx[i__] = *da * dx[i__];
		/* L10: */
	}
	return;
	/*        code for increment equal to 1   
	clean-up loop */
L20:
	m = *n % 5;
	if (m == 0) {
		goto L40;
	}
	i__2 = m;
	for (i__ = 1; i__ <= i__2; ++i__) {
		dx[i__] = *da * dx[i__];
		/* L30: */
	}
	if (*n < 5) {
		return;
	}
L40:
	mp1 = m + 1;
	i__2 = *n;
	for (i__ = mp1; i__ <= i__2; i__ += 5) {
		dx[i__] = *da * dx[i__];
		dx[i__ + 1] = *da * dx[i__ + 1];
		dx[i__ + 2] = *da * dx[i__ + 2];
		dx[i__ + 3] = *da * dx[i__ + 3];
		dx[i__ + 4] = *da * dx[i__ + 4];
		/* L50: */
	}
	return;
}

double ddot_(int *n, double *dx, int *incx, double *dy, 
	int *incy)
{


	/* System generated locals */
	int i__1;
	double ret_val;

	/* Local variables */
	static int i, m;
	static double dtemp;
	static int ix, iy, mp1;

	/*     forms the dot product of two vectors.   
	uses unrolled loops for increments equal to one.   
	jack dongarra, linpack, 3/11/78.   
	modified 12/3/93, array(1) declarations changed to array(*)   

	Parameter adjustments   
	Function Body */
#define DY(I) dy[(I)-1]
#define DX(I) dx[(I)-1]


	ret_val = 0.;
	dtemp = 0.;
	if (*n <= 0) {
		return ret_val;
	}
	if (*incx == 1 && *incy == 1) {
		goto L20;
	}

	/*        code for unequal increments or equal increments   
	not equal to 1 */

	ix = 1;
	iy = 1;
	if (*incx < 0) {
		ix = (-(*n) + 1) * *incx + 1;
	}
	if (*incy < 0) {
		iy = (-(*n) + 1) * *incy + 1;
	}
	i__1 = *n;
	for (i = 1; i <= *n; ++i) {
		dtemp += DX(ix) * DY(iy);
		ix += *incx;
		iy += *incy;
		/* L10: */
	}
	ret_val = dtemp;
	return ret_val;

	/*        code for both increments equal to 1   
	clean-up loop */

L20:
	m = *n % 5;
	if (m == 0) {
		goto L40;
	}
	i__1 = m;
	for (i = 1; i <= m; ++i) {
		dtemp += DX(i) * DY(i);
		/* L30: */
	}
	if (*n < 5) {
		goto L60;
	}
L40:
	mp1 = m + 1;
	i__1 = *n;
	for (i = mp1; i <= *n; i += 5) {
		dtemp = dtemp + DX(i) * DY(i) + DX(i + 1) * DY(i + 1) + DX(i + 2) * 
			DY(i + 2) + DX(i + 3) * DY(i + 3) + DX(i + 4) * DY(i + 4);
		/* L50: */
	}
L60:
	ret_val = dtemp;
	return ret_val;
}

void dspr_(char *uplo, int *n, double *alpha, double *x, int *incx, double *ap)
{
	/* System generated locals */
	int i__1, i__2;
	/* Local variables */
	static int info;
	static double temp;
	static int i__, j, k;
	static int kk, ix, jx, kx;
	//*  Purpose   
	//   =======   
	//   DSPR    performs the symmetric rank 1 operation   
	//      A := alpha*x*x' + A,   
	//   where alpha is a real scalar, x is an n element vector and A is an   
	//   n by n symmetric matrix, supplied in packed form.   
	//   Parameters   
	//   ==========   
	//   UPLO   - CHARACTER*1.   
	//            On entry, UPLO specifies whether the upper or lower   
	//            triangular part of the matrix A is supplied in the packed   
	//            array AP as follows:   
	//               UPLO = 'U' or 'u'   The upper triangular part of A is   
	//                                   supplied in AP.   
	//               UPLO = 'L' or 'l'   The lower triangular part of A is   
	//                                   supplied in AP.   
	//            Unchanged on exit.   
	//   N      - INTEGER.   
	//            On entry, N specifies the order of the matrix A.   
	//            N must be at least zero.   
	//            Unchanged on exit.   
	//   ALPHA  - DOUBLE PRECISION.   
	//            On entry, ALPHA specifies the scalar alpha.   
	//            Unchanged on exit.   
	//   X      - DOUBLE PRECISION array of dimension at least   
	//            ( 1 + ( n - 1 )*abs( INCX ) ).   
	//            Before entry, the incremented array X must contain the n   
	//            element vector x.   
	//            Unchanged on exit.   
	//   INCX   - INTEGER.   
	//            On entry, INCX specifies the increment for the elements of   
	//            X. INCX must not be zero.   
	//            Unchanged on exit.   
	//   AP     - DOUBLE PRECISION array of DIMENSION at least   
	//            ( ( n*( n + 1 ) )/2 ).   
	//            Before entry with  UPLO = 'U' or 'u', the array AP must   
	//            contain the upper triangular part of the symmetric matrix   
	//            packed sequentially, column by column, so that AP( 1 )   
	//            contains a( 1, 1 ), AP( 2 ) and AP( 3 ) contain a( 1, 2 )   
	//            and a( 2, 2 ) respectively, and so on. On exit, the array   
	//            AP is overwritten by the upper triangular part of the   
	//            updated matrix.   
	//            Before entry with UPLO = 'L' or 'l', the array AP must   
	//            contain the lower triangular part of the symmetric matrix   
	//            packed sequentially, column by column, so that AP( 1 )   
	//            contains a( 1, 1 ), AP( 2 ) and AP( 3 ) contain a( 2, 1 )   
	//            and a( 3, 1 ) respectively, and so on. On exit, the array   
	//            AP is overwritten by the lower triangular part of the   
	//            updated matrix.   
	//   Level 2 Blas routine.   
	//   -- Written on 22-October-1986.   
	//      Jack Dongarra, Argonne National Lab.   
	//      Jeremy Du Croz, Nag Central Office.   
	//      Sven Hammarling, Nag Central Office.   
	//      Richard Hanson, Sandia National Labs.   
	//      Test the input parameters.   
	//      Parameter adjustments */
	--ap;
	--x;
	/* Function Body */
	info = 0;
	if (! lsame_(uplo, "U") && ! lsame_(uplo, "L")) {
		info = 1;
	} else if (*n < 0) {
		info = 2;
	} else if (*incx == 0) {
		info = 5;
	}
	if (info != 0) {
		xerbla_("DSPR  ", &info);
		return;
	}
	/*     Quick return if possible. */
	if (*n == 0 || *alpha == 0.) {
		return;
	}
	/*     Set the start point in X if the increment is not unity. */
	if (*incx <= 0) {
		kx = 1 - (*n - 1) * *incx;
	} else if (*incx != 1) {
		kx = 1;
	}
	/*     Start the operations. In this version the elements of the array AP   
	are accessed sequentially with one pass through AP. */
	kk = 1;
	if (lsame_(uplo, "U")) {
		/*        Form  A  when upper triangle is stored in AP. */
		if (*incx == 1) {
			i__1 = *n;
			for (j = 1; j <= i__1; ++j) {
				if (x[j] != 0.) {
					temp = *alpha * x[j];
					k = kk;
					i__2 = j;
					for (i__ = 1; i__ <= i__2; ++i__) {
						ap[k] += x[i__] * temp;
						++k;
						/* L10: */
					}
				}
				kk += j;
				/* L20: */
			}
		} else {
			jx = kx;
			i__1 = *n;
			for (j = 1; j <= i__1; ++j) {
				if (x[jx] != 0.) {
					temp = *alpha * x[jx];
					ix = kx;
					i__2 = kk + j - 1;
					for (k = kk; k <= i__2; ++k) {
						ap[k] += x[ix] * temp;
						ix += *incx;
						/* L30: */
					}
				}
				jx += *incx;
				kk += j;
				/* L40: */
			}
		}
	} else {
		/*        Form  A  when lower triangle is stored in AP. */
		if (*incx == 1) {
			i__1 = *n;
			for (j = 1; j <= i__1; ++j) {
				if (x[j] != 0.) {
					temp = *alpha * x[j];
					k = kk;
					i__2 = *n;
					for (i__ = j; i__ <= i__2; ++i__) {
						ap[k] += x[i__] * temp;
						++k;
						/* L50: */
					}
				}
				kk = kk + *n - j + 1;
				/* L60: */
			}
		} else {
			jx = kx;
			i__1 = *n;
			for (j = 1; j <= i__1; ++j) {
				if (x[jx] != 0.) {
					temp = *alpha * x[jx];
					ix = jx;
					i__2 = kk + *n - j;
					for (k = kk; k <= i__2; ++k) {
						ap[k] += x[ix] * temp;
						ix += *incx;
						/* L70: */
					}
				}
				jx += *incx;
				kk = kk + *n - j + 1;
				/* L80: */
			}
		}
	}
	return;
}

void dtpsv_(char *uplo, char *trans, char *diag, int *n, 
	double *ap, double *x, int *incx)
{
	/* System generated locals */
	int i__1, i__2;
	/* Local variables */
	static int info;
	static double temp;
	static int i__, j, k;
	static int kk, ix, jx, kx;
	static int nounit;
	//  Purpose   
	//   =======   
	//   DTPSV  solves one of the systems of equations   
	//      A*x = b,   or   A'*x = b,   
	//   where b and x are n element vectors and A is an n by n unit, or   
	//   non-unit, upper or lower triangular matrix, supplied in packed form.   
	//   No test for singularity or near-singularity is included in this   
	//   routine. Such tests must be performed before calling this routine.   
	//   Parameters   
	//   ==========   
	//   UPLO   - CHARACTER*1.   
	//            On entry, UPLO specifies whether the matrix is an upper or   
	//            lower triangular matrix as follows:   
	//               UPLO = 'U' or 'u'   A is an upper triangular matrix.   
	//               UPLO = 'L' or 'l'   A is a lower triangular matrix.   
	//            Unchanged on exit.   
	//   TRANS  - CHARACTER*1.   
	//            On entry, TRANS specifies the equations to be solved as   
	//            follows:   
	//               TRANS = 'N' or 'n'   A*x = b.   
	//               TRANS = 'T' or 't'   A'*x = b.   
	//               TRANS = 'C' or 'c'   A'*x = b.   
	//            Unchanged on exit.   
	//   DIAG   - CHARACTER*1.   
	//            On entry, DIAG specifies whether or not A is unit   
	//            triangular as follows:   
	//               DIAG = 'U' or 'u'   A is assumed to be unit triangular.   
	//               DIAG = 'N' or 'n'   A is not assumed to be unit   
	//                                   triangular.   
	//            Unchanged on exit.   
	//   N      - INTEGER.   
	//            On entry, N specifies the order of the matrix A.   
	//            N must be at least zero.   
	//            Unchanged on exit.   
	//   AP     - DOUBLE PRECISION array of DIMENSION at least   
	//            ( ( n*( n + 1 ) )/2 ).   
	//            Before entry with  UPLO = 'U' or 'u', the array AP must   
	//            contain the upper triangular matrix packed sequentially,   
	//            column by column, so that AP( 1 ) contains a( 1, 1 ),   
	//            AP( 2 ) and AP( 3 ) contain a( 1, 2 ) and a( 2, 2 )   
	//            respectively, and so on.   
	//            Before entry with UPLO = 'L' or 'l', the array AP must   
	//            contain the lower triangular matrix packed sequentially,   
	//            column by column, so that AP( 1 ) contains a( 1, 1 ),   
	//            AP( 2 ) and AP( 3 ) contain a( 2, 1 ) and a( 3, 1 )   
	//            respectively, and so on.   
	//            Note that when  DIAG = 'U' or 'u', the diagonal elements of   
	//            A are not referenced, but are assumed to be unity.   
	//            Unchanged on exit.   
	//   X      - DOUBLE PRECISION array of dimension at least   
	//            ( 1 + ( n - 1 )*abs( INCX ) ).   
	//            Before entry, the incremented array X must contain the n   
	//            element right-hand side vector b. On exit, X is overwritten   
	//            with the solution vector x.   
	//   INCX   - INTEGER.   
	//            On entry, INCX specifies the increment for the elements of   
	//            X. INCX must not be zero.   
	//            Unchanged on exit.   
	//   Level 2 Blas routine.   
	//   -- Written on 22-October-1986.   
	//      Jack Dongarra, Argonne National Lab.   
	//      Jeremy Du Croz, Nag Central Office.   
	//      Sven Hammarling, Nag Central Office.   
	//      Richard Hanson, Sandia National Labs.   
	//      Test the input parameters.   
	--x;
	--ap;
	/* Function Body */
	info = 0;
	if (! lsame_(uplo, "U") && ! lsame_(uplo, "L")) {
		info = 1;
	} else if (! lsame_(trans, "N") && ! lsame_(trans, 
		"T") && ! lsame_(trans, "C")) {
			info = 2;
	} else if (! lsame_(diag, "U") && ! lsame_(diag, 
		"N")) {
			info = 3;
	} else if (*n < 0) {
		info = 4;
	} else if (*incx == 0) {
		info = 7;
	}
	if (info != 0) {
		xerbla_("DTPSV ", &info);
		return;
	}
	/*     Quick return if possible. */
	if (*n == 0) {
		return;
	}
	nounit = lsame_(diag, "N");
	/*     Set up the start point in X if the increment is not unity. This   
	will be  ( N - 1 )*INCX  too small for descending loops. */
	if (*incx <= 0) {
		kx = 1 - (*n - 1) * *incx;
	} else if (*incx != 1) {
		kx = 1;
	}
	/*     Start the operations. In this version the elements of AP are   
	accessed sequentially with one pass through AP. */
	if (lsame_(trans, "N")) {
		/*        Form  x := inv( A )*x. */
		if (lsame_(uplo, "U")) {
			kk = *n * (*n + 1) / 2;
			if (*incx == 1) {
				for (j = *n; j >= 1; --j) {
					if (x[j] != 0.) {
						if (nounit) {
							x[j] /= ap[kk];
						}
						temp = x[j];
						k = kk - 1;
						for (i__ = j - 1; i__ >= 1; --i__) {
							x[i__] -= temp * ap[k];
							--k;
							/* L10: */
						}
					}
					kk -= j;
					/* L20: */
				}
			} else {
				jx = kx + (*n - 1) * *incx;
				for (j = *n; j >= 1; --j) {
					if (x[jx] != 0.) {
						if (nounit) {
							x[jx] /= ap[kk];
						}
						temp = x[jx];
						ix = jx;
						i__1 = kk - j + 1;
						for (k = kk - 1; k >= i__1; --k) {
							ix -= *incx;
							x[ix] -= temp * ap[k];
							/* L30: */
						}
					}
					jx -= *incx;
					kk -= j;
					/* L40: */
				}
			}
		} else {
			kk = 1;
			if (*incx == 1) {
				i__1 = *n;
				for (j = 1; j <= i__1; ++j) {
					if (x[j] != 0.) {
						if (nounit) {
							x[j] /= ap[kk];
						}
						temp = x[j];
						k = kk + 1;
						i__2 = *n;
						for (i__ = j + 1; i__ <= i__2; ++i__) {
							x[i__] -= temp * ap[k];
							++k;
							/* L50: */
						}
					}
					kk += *n - j + 1;
					/* L60: */
				}
			} else {
				jx = kx;
				i__1 = *n;
				for (j = 1; j <= i__1; ++j) {
					if (x[jx] != 0.) {
						if (nounit) {
							x[jx] /= ap[kk];
						}
						temp = x[jx];
						ix = jx;
						i__2 = kk + *n - j;
						for (k = kk + 1; k <= i__2; ++k) {
							ix += *incx;
							x[ix] -= temp * ap[k];
							/* L70: */
						}
					}
					jx += *incx;
					kk += *n - j + 1;
					/* L80: */
				}
			}
		}
	} else {
		/*        Form  x := inv( A' )*x. */
		if (lsame_(uplo, "U")) {
			kk = 1;
			if (*incx == 1) {
				i__1 = *n;
				for (j = 1; j <= i__1; ++j) {
					temp = x[j];
					k = kk;
					i__2 = j - 1;
					for (i__ = 1; i__ <= i__2; ++i__) {
						temp -= ap[k] * x[i__];
						++k;
						/* L90: */
					}
					if (nounit) {
						temp /= ap[kk + j - 1];
					}
					x[j] = temp;
					kk += j;
					/* L100: */
				}
			} else {
				jx = kx;
				i__1 = *n;
				for (j = 1; j <= i__1; ++j) {
					temp = x[jx];
					ix = kx;
					i__2 = kk + j - 2;
					for (k = kk; k <= i__2; ++k) {
						temp -= ap[k] * x[ix];
						ix += *incx;
						/* L110: */
					}
					if (nounit) {
						temp /= ap[kk + j - 1];
					}
					x[jx] = temp;
					jx += *incx;
					kk += j;
					/* L120: */
				}
			}
		} else {
			kk = *n * (*n + 1) / 2;
			if (*incx == 1) {
				for (j = *n; j >= 1; --j) {
					temp = x[j];
					k = kk;
					i__1 = j + 1;
					for (i__ = *n; i__ >= i__1; --i__) {
						temp -= ap[k] * x[i__];
						--k;
						/* L130: */
					}
					if (nounit) {
						temp /= ap[kk - *n + j];
					}
					x[j] = temp;
					kk -= *n - j + 1;
					/* L140: */
				}
			} else {
				kx += (*n - 1) * *incx;
				jx = kx;
				for (j = *n; j >= 1; --j) {
					temp = x[jx];
					ix = kx;
					i__1 = kk - (*n - (j + 1));
					for (k = kk; k >= i__1; --k) {
						temp -= ap[k] * x[ix];
						ix -= *incx;
						/* L150: */
					}
					if (nounit) {
						temp /= ap[kk - *n + j];
					}
					x[jx] = temp;
					jx -= *incx;
					kk -= *n - j + 1;
					/* L160: */
				}
			}
		}
	}
	return;
}

