/*
 * Copyright (C) 2012 - Michael Baudin
 * Copyright (C) 2008 - John Burkardt
 * Copyright (C) 1973 - Richard Brent
 *
 * This code is distributed under the GNU LGPL license. 
 *
 */
 
#ifndef _CDFLIBBRENT_H_
#define _CDFLIBBRENT_H_

/*
  Purpose:

    GLOMIN seeks a global minimum of a function F(X) in an interval [A,B].

  Discussion:

    This function assumes that F(X) is twice continuously differentiable
    over [A,B] and that F''(X) <= M for all X in [A,B].

  Licensing:

    This code is distributed under the GNU LGPL license. 

  Modified:

    17 April 2008

  Author:

    Original FORTRAN77 version by Richard Brent.
    C version by John Burkardt.

  Reference:

    Richard Brent,
    Algorithms for Minimization Without Derivatives,
    Dover, 2002,
    ISBN: 0-486-41998-3,
    LC: QA402.5.B74.

  Parameters:

    Input, double A, B, the endpoints of the interval.
    It must be the case that A < B.

    Input, double C, an initial guess for the global
    minimizer.  If no good guess is known, C = A or B is acceptable.

    Input, double M, the bound on the second derivative.

    Input, double MACHEP, an estimate for the relative machine
    precision.

    Input, double E, a positive tolerance, a bound for the
    absolute error in the evaluation of F(X) for any X in [A,B].

    Input, double T, a positive error tolerance.

    Input, double F ( double x ), a user-supplied
    function whose global minimum is being sought.

    Output, double *X, the estimated value of the abscissa
    for which F attains its global minimum value in [A,B].

    Output, double GLOMIN, the value F(X).
*/
double glomin ( double a, double b, double c, double m, double machep, 
  double e, double t, double f ( double x ), double *x );

  /*
  Purpose:

    LOCAL_MIN seeks a local minimum of a function F(X) in an interval [A,B].

  Discussion:

    The method used is a combination of golden section search and
    successive parabolic interpolation.  Convergence is never much slower
    than that for a Fibonacci search.  If F has a continuous second
    derivative which is positive at the minimum (which is not at A or
    B), then convergence is superlinear, and usually of the order of
    about 1.324....

    The values EPS and T define a tolerance TOL = EPS * abs ( X ) + T.
    F is never evaluated at two points closer than TOL.  

    If F is a unimodal function and the computed values of F are always
    unimodal when separated by at least SQEPS * abs ( X ) + (T/3), then
    LOCAL_MIN approximates the abscissa of the global minimum of F on the 
    interval [A,B] with an error less than 3*SQEPS*abs(LOCAL_MIN)+T.  

    If F is not unimodal, then LOCAL_MIN may approximate a local, but 
    perhaps non-global, minimum to the same accuracy.

  Licensing:

    This code is distributed under the GNU LGPL license. 

  Modified:

    14 April 2008

  Author:

    Orignal FORTRAN77 version by Richard Brent.
    C version by John Burkardt.

  Reference:

    Richard Brent,
    Algorithms for Minimization Without Derivatives,
    Dover, 2002,
    ISBN: 0-486-41998-3,
    LC: QA402.5.B74.

  Parameters:

    Input, double A, B, the endpoints of the interval.

    Input, double EPS, a positive relative error tolerance.
    EPS should be no smaller than twice the relative machine precision,
    and preferably not much less than the square root of the relative
    machine precision.

    Input, double T, a positive absolute error tolerance.

    Input, double F ( double x ), a user-supplied
    function whose local minimum is being sought.

    Output, double *X, the estimated value of an abscissa
    for which F attains a local minimum value in [A,B].

    Output, double LOCAL_MIN, the value F(X).
*/
double local_min ( double a, double b, double eps, double t, 
  double f ( double x ), double *x );

/*
  Purpose:
 
    LOCAL_MIN_RC seeks a minimizer of a scalar function of a scalar variable.
 
  Discussion:
 
    This routine seeks an approximation to the point where a function
    F attains a minimum on the interval (A,B).
 
    The method used is a combination of golden section search and
    successive parabolic interpolation.  Convergence is never much
    slower than that for a Fibonacci search.  If F has a continuous
    second derivative which is positive at the minimum (which is not
    at A or B), then convergence is superlinear, and usually of the
    order of about 1.324...
 
    The routine is a revised version of the Brent local minimization 
    algorithm, using reverse communication.
 
    It is worth stating explicitly that this routine will NOT be
    able to detect a minimizer that occurs at either initial endpoint
    A or B.  If this is a concern to the user, then the user must
    either ensure that the initial interval is larger, or to check
    the function value at the returned minimizer against the values
    at either endpoint.
 
  Licensing:

    This code is distributed under the GNU LGPL license. 

  Modified:
 
    16 April 2008
 
  Author:
 
    John Burkardt
 
  Reference:
 
    Richard Brent,
    Algorithms for Minimization Without Derivatives,
    Dover, 2002,
    ISBN: 0-486-41998-3,
    LC: QA402.5.B74.
 
    David Kahaner, Cleve Moler, Steven Nash,
    Numerical Methods and Software,
    Prentice Hall, 1989,
    ISBN: 0-13-627258-4,
    LC: TA345.K34.
 
  Parameters
 
    Input/output, double *A, *B.  On input, the left and right
    endpoints of the initial interval.  On output, the lower and upper
    bounds for an interval containing the minimizer.  It is required
    that A < B.
 
    Input/output, int *STATUS, used to communicate between 
    the user and the routine.  The user only sets STATUS to zero on the first 
    call, to indicate that this is a startup call.  The routine returns STATUS
    positive to request that the function be evaluated at ARG, or returns
    STATUS as 0, to indicate that the iteration is complete and that
    ARG is the estimated minimizer.
 
    Input, double VALUE, the function value at ARG, as requested
    by the routine on the previous call.
 
    Output, double LOCAL_MIN_RC, the currently considered point.  
    On return with STATUS positive, the user is requested to evaluate the 
    function at this point, and return the value in VALUE.  On return with
    STATUS zero, this is the routine's estimate for the function minimizer.
 
  Local parameters:
 
    C is the squared inverse of the golden ratio.
 
    EPS is the square root of the relative machine precision.
*/
double local_min_rc ( double *a, double *b, int *status, double value );

/*
  Purpose:

    ZERO seeks the root of a function F(X) in an interval [A,B].

  Discussion:

    The interval [A,B] must be a change of sign interval for F.
    That is, F(A) and F(B) must be of opposite signs.  Then
    assuming that F is continuous implies the existence of at least
    one value C between A and B for which F(C) = 0.

    The location of the zero is determined to within an accuracy
    of 6 * MACHEPS * r8_abs ( C ) + 2 * T.

  Licensing:

    This code is distributed under the GNU LGPL license. 

  Modified:

    13 April 2008

  Author:

    Original FORTRAN77 version by Richard Brent.
    C version by John Burkardt.

  Reference:

    Richard Brent,
    Algorithms for Minimization Without Derivatives,
    Dover, 2002,
    ISBN: 0-486-41998-3,
    LC: QA402.5.B74.

  Parameters:

    Input, double A, B, the endpoints of the change of sign interval.

    Input, double MACHEP, an estimate for the relative machine
    precision.

    Input, double T, a positive error tolerance.

    Input, double F ( double x ), a user-supplied function whose zero 
    is being sought.

    Output, double ZERO, the estimated value of a zero of
    the function F.
*/
double zero ( double a, double b, double machep, double t, 
  double f ( double x ) );

/*
  Purpose:

    ZERO_RC seeks the root of a function F(X) using reverse communication.

  Discussion:

    The interval [A,B] must be a change of sign interval for F.
    That is, F(A) and F(B) must be of opposite signs.  Then
    assuming that F is continuous implies the existence of at least
    one value C between A and B for which F(C) = 0.

    The location of the zero is determined to within an accuracy
    of 6 * MACHEPS * r8_abs ( C ) + 2 * T.

    The routine is a revised version of the Brent zero finder 
    algorithm, using reverse communication.

  Licensing:

    This code is distributed under the GNU LGPL license. 

  Modified:

    14 October 2008

  Author:

    John Burkardt

  Reference:

    Richard Brent,
    Algorithms for Minimization Without Derivatives,
    Dover, 2002,
    ISBN: 0-486-41998-3,
    LC: QA402.5.B74.

  Parameters:

    Input, double A, B, the endpoints of the change of sign interval.

    Input, double T, a positive error tolerance.

    Output, double *ARG, the currently considered point.  The user
    does not need to initialize this value.  On return with STATUS positive,
    the user is requested to evaluate the function at ARG, and return
    the value in VALUE.  On return with STATUS zero, ARG is the routine's
    estimate for the function's zero.

    Input/output, int *STATUS, used to communicate between 
    the user and the routine.  The user only sets STATUS to zero on the first 
    call, to indicate that this is a startup call.  The routine returns STATUS
    positive to request that the function be evaluated at ARG, or returns
    STATUS as 0, to indicate that the iteration is complete and that
    ARG is the estimated zero

    Input, double VALUE, the function value at ARG, as requested
    by the routine on the previous call.
*/
void zero_rc ( double a, double b, double t, double *arg, int *status, 
  double value );
  
#endif /** _CDFLIBBRENT_H_   **/
