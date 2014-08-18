/*
 * Copyright (C) 2012 - Michael Baudin
 * Copyright (C) 2008 - John Burkardt
 * Copyright (C) 1973 - Richard Brent
 *
 * This code is distributed under the GNU LGPL license. 
 *
 */
# include <stdlib.h>
# include <stdio.h>
# include <math.h>
# include <time.h>

# include "brent.h"

// warning C4996: 'localtime': This function or variable may be unsafe. 
// Consider using localtime_s instead. 
// To disable deprecation, use _CRT_SECURE_NO_WARNINGS.
#ifdef _MSC_VER
#pragma warning( disable : 4996 )
#endif

double r8_abs ( double x );
double r8_epsilon ( void );
double r8_max ( double x, double y );
double r8_sign ( double x );
void timestamp ( void );

/******************************************************************************/

double glomin ( double a, double b, double c, double m, double machep, 
  double e, double t, double f ( double x ), double *x )

/******************************************************************************/
{
  double a0;
  double a2;
  double a3;
  double d0;
  double d1;
  double d2;
  double h;
  int k;
  double m2;
  double p;
  double q;
  double qs;
  double r;
  double s;
  double sc;
  double y;
  double y0;
  double y1;
  double y2;
  double y3;
  double yb;
  double z0;
  double z1;
  double z2;

  a0 = b;
  *x = a0;
  a2 = a;
  y0 = f ( b );
  yb = y0;
  y2 = f ( a );
  y = y2;

  if ( y0 < y )
  {
    y = y0;
  }
  else
  {
    *x = a;
  }

  if ( m <= 0.0 || b <= a )
  {
    return y;
  }

  m2 = 0.5 * ( 1.0 + 16.0 * machep ) * m;

  if ( c <= a || b <= c ) 
  {
    sc = 0.5 * ( a + b );
  }
  else
  {
    sc = c;
  }

  y1 = f ( sc );
  k = 3;
  d0 = a2 - sc;
  h = 9.0 / 11.0;

  if ( y1 < y )
  {
    *x = sc;
    y = y1;
  }

  for ( ; ; )
  {
    d1 = a2 - a0;
    d2 = sc - a0;
    z2 = b - a2;
    z0 = y2 - y1;
    z1 = y2 - y0;
    r = d1 * d1 * z0 - d0 * d0 * z1;
    p = r;
    qs = 2.0 * ( d0 * z1 - d1 * z0 );
    q = qs;

    if ( k < 1000000 || y2 <= y )
    {
      for ( ; ; )
      {
        if ( q * ( r * ( yb - y2 ) + z2 * q * ( ( y2 - y ) + t ) ) < 
          z2 * m2 * r * ( z2 * q - r ) )
        {
          a3 = a2 + r / q;
          y3 = f ( a3 );

          if ( y3 < y )
          {
            *x = a3;
            y = y3;
          }
        }
        k = ( ( 1611 * k ) % 1048576 );
        q = 1.0;
        r = ( b - a ) * 0.00001 * ( double ) ( k );

        if ( z2 <= r )
        {
          break;
        }
      }
    }
    else
    {
      k = ( ( 1611 * k ) % 1048576 );
      q = 1.0;
      r = ( b - a ) * 0.00001 * ( double ) ( k );

      while ( r < z2 )
      {
        if ( q * ( r * ( yb - y2 ) + z2 * q * ( ( y2 - y ) + t ) ) < 
          z2 * m2 * r * ( z2 * q - r ) )
        {
          a3 = a2 + r / q;
          y3 = f ( a3 );

          if ( y3 < y )
          {
            *x = a3;
            y = y3;
          }
        }
        k = ( ( 1611 * k ) % 1048576 );
        q = 1.0;
        r = ( b - a ) * 0.00001 * ( double ) ( k );
      }
    }

    r = m2 * d0 * d1 * d2;
    s = sqrt ( ( ( y2 - y ) + t ) / m2 );
    h = 0.5 * ( 1.0 + h );
    p = h * ( p + 2.0 * r * s );
    q = q + 0.5 * qs;
    r = - 0.5 * ( d0 + ( z0 + 2.01 * e ) / ( d0 * m2 ) );

    if ( r < s || d0 < 0.0 ) 
    {
      r = a2 + s;
    }
    else
    {
      r = a2 + r;
    }

    if ( 0.0 < p * q ) 
    {
      a3 = a2 + p / q;
    }
    else
    {
      a3 = r;
    }

    for ( ; ; )
    {
      a3 = r8_max ( a3, r );

      if ( b <= a3 ) 
      {
        a3 = b;
        y3 = yb;
      }
      else
      {
        y3 = f ( a3 );
      }

      if ( y3 < y ) 
      {
        *x = a3;
        y = y3;
      }

      d0 = a3 - a2;

      if ( a3 <= r ) 
      {
        break;
      }

      p = 2.0 * ( y2 - y3 ) / ( m * d0 );

      if ( ( 1.0 + 9.0 * machep ) * d0 <= r8_abs ( p ) )
      {
        break;
      }

      if ( 0.5 * m2 * ( d0 * d0 + p * p ) <= ( y2 - y ) + ( y3 - y ) + 2.0 * t )
      {
        break;
      }
      a3 = 0.5 * ( a2 + a3 );
      h = 0.9 * h;
    }

    if ( b <= a3 )
    {
      break;
    }

    a0 = sc;
    sc = a2;
    a2 = a3;
    y0 = y1;
    y1 = y2;
    y2 = y3;
  }

  return y;
}
/******************************************************************************/

double local_min ( double a, double b, double eps, double t, 
  double f ( double x ), double *x )

/******************************************************************************/
{
  double c;
  double d;
  double e;
  double fu;
  double fv;
  double fw;
  double fx;
  double m;
  double p;
  double q;
  double r;
  double sa;
  double sb;
  double t2;
  double tol;
  double u;
  double v;
  double w;
/*
  C is the square of the inverse of the golden ratio.
*/
  c = 0.5 * ( 3.0 - sqrt ( 5.0 ) );

  sa = a;
  sb = b;
  *x = sa + c * ( b - a );
  w = *x;
  v = w;
  e = 0.0;
  fx = f ( *x );
  fw = fx;
  fv = fw;

  for ( ; ; )
  { 
    m = 0.5 * ( sa + sb ) ;
    tol = eps * r8_abs ( *x ) + t;
    t2 = 2.0 * tol;
/*
  Check the stopping criterion.
*/
    if ( r8_abs ( *x - m ) <= t2 - 0.5 * ( sb - sa ) )
    {
      break;
    }
/*
  Fit a parabola.
*/
    r = 0.0;
    q = r;
    p = q;

    if ( tol < r8_abs ( e ) )
    {
      r = ( *x - w ) * ( fx - fv );
      q = ( *x - v ) * ( fx - fw );
      p = ( *x - v ) * q - ( *x - w ) * r;
      q = 2.0 * ( q - r );
      if ( 0.0 < q )
      {
        p = - p;
      }
      q = r8_abs ( q );
      r = e;
      e = d;
    }

    if ( r8_abs ( p ) < r8_abs ( 0.5 * q * r ) && 
         q * ( sa - *x ) < p && 
         p < q * ( sb - *x ) )
    {
/*
  Take the parabolic interpolation step.
*/
      d = p / q;
      u = *x + d;
/*
  F must not be evaluated too close to A or B.
*/
      if ( ( u - sa ) < t2 || ( sb - u ) < t2 )
      {
        if ( *x < m )
        {
          d = tol;
        }
        else
        {
          d = - tol;
        }
      }
    }
/*
  A golden-section step.
*/
    else
    {
      if ( *x < m )
      {
        e = sb - *x;
      }
      else
      {
        e = a - *x;
      }
      d = c * e;
    }
/*
  F must not be evaluated too close to X.
*/
    if ( tol <= r8_abs ( d ) )
    {
      u = *x + d;
    }
    else if ( 0.0 < d )
    {
      u = *x + tol;
    }
    else
    {
      u = *x - tol;
    }

    fu = f ( u );
/*
  Update A, B, V, W, and X.
*/
    if ( fu <= fx )
    {
      if ( u < *x )
      {
        sb = *x;
      }
      else
      {
        sa = *x;
      }
      v = w;
      fv = fw;
      w = *x;
      fw = fx;
      *x = u;
      fx = fu;
    }
    else
    {
      if ( u < *x )
      {
        sa = u;
      }
      else
      {
        sb = u;
      }

      if ( fu <= fw || w == *x )
      {
        v = w;
        fv = fw;
        w = u;
        fw = fu;
      }
      else if ( fu <= fv || v == *x || v== w )
      {
        v = u;
        fv = fu;
      }
    }
  }
  return fx;
}
/******************************************************************************/

double local_min_rc ( double *a, double *b, int *status, double value )

/******************************************************************************/
{
  static double arg;
  static double c;
  static double d;
  static double e;
  static double eps;
  static double fu;
  static double fv;
  static double fw;
  static double fx;
  static double midpoint;
  static double p;
  static double q;
  static double r;
  static double tol;
  static double tol1;
  static double tol2;
  static double u;
  static double v;
  static double w;
  static double x;
/*
   STATUS (INPUT) = 0, startup.
*/
  if ( *status == 0 )
  {
    if ( *b <= *a )
    {
      printf ( "\n" );
      printf ( "LOCAL_MIN_RC - Fatal error!\n" );
      printf ( "  A < B is required, but\n" );
      printf ( "  A = %f\n", *a );
      printf ( "  B = %f\n", *b );
      *status = -1;
      exit ( 1 );
    }
    c = 0.5 * ( 3.0 - sqrt ( 5.0 ) );

    eps = sqrt ( r8_epsilon ( ) );
    tol = r8_epsilon ( );

    v = *a + c * ( *b - *a );
    w = v;
    x = v;
    e = 0.0;

    *status = 1;
    arg = x;

    return arg;
  }
/*
   STATUS (INPUT) = 1, return with initial function value of FX.
*/
  else if ( *status == 1 )
  {
    fx = value;
    fv = fx;
    fw = fx;
  }
/*
   STATUS (INPUT) = 2 or more, update the data.
*/
  else if ( 2 <= *status )
  {
    fu = value;

    if ( fu <= fx )
    {
      if ( x <= u )
      {
        *a = x;
      }
      else
      {
        *b = x;
      }
      v = w;
      fv = fw;
      w = x;
      fw = fx;
      x = u;
      fx = fu;
    }
    else
    {
      if ( u < x )
      {
        *a = u;
      }
      else
      {
        *b = u;
      }

      if ( fu <= fw || w == x )
      {
        v = w;
        fv = fw;
        w = u;
        fw = fu;
      }
      else if ( fu <= fv || v == x || v == w )
      {
        v = u;
        fv = fu;
      }
    }
  }
/*
   Take the next step.
*/
  midpoint = 0.5 * ( *a + *b );
  tol1 = eps * r8_abs ( x ) + tol / 3.0;
  tol2 = 2.0 * tol1;
/*
   If the stopping criterion is satisfied, we can exit.
*/
  if ( r8_abs ( x - midpoint ) <= ( tol2 - 0.5 * ( *b - *a ) ) )
  {
    *status = 0;
    return arg;
  }
/*
   Is golden-section necessary?
*/
  if ( r8_abs ( e ) <= tol1 )
  {
    if ( midpoint <= x )
    {
      e = *a - x;
    }
    else
    {
      e = *b - x;
    }
    d = c * e;
  }
/*
   Consider fitting a parabola.
*/
  else
  {
    r = ( x - w ) * ( fx - fv );
    q = ( x - v ) * ( fx - fw );
    p = ( x - v ) * q - ( x - w ) * r;
    q = 2.0 * ( q - r );
    if ( 0.0 < q )
    {
      p = - p;
    }
    q = r8_abs ( q );
    r = e;
    e = d;
/*
   Choose a golden-section step if the parabola is not advised.
*/
    if ( 
      ( r8_abs ( 0.5 * q * r ) <= r8_abs ( p ) ) ||
      ( p <= q * ( *a - x ) ) ||
      ( q * ( *b - x ) <= p ) ) 
    {
      if ( midpoint <= x )
      {
        e = *a - x;
      }
      else
      {
        e = *b - x;
      }
      d = c * e;
    }
/*
   Choose a parabolic interpolation step.
*/
    else
    {
      d = p / q;
      u = x + d;

      if ( ( u - *a ) < tol2 )
      {
        d = tol1 * r8_sign ( midpoint - x );
      }

      if ( ( *b - u ) < tol2 )
      {
        d = tol1 * r8_sign ( midpoint - x );
      }
    }
  }
/*
   F must not be evaluated too close to X.
*/
  if ( tol1 <= r8_abs ( d ) ) 
  {
    u = x + d;
  }
  if ( r8_abs ( d ) < tol1 )
  {
    u = x + tol1 * r8_sign ( d );
  }
/*
   Request value of F(U).
*/
  arg = u;
  *status = *status + 1;

  return arg;
}
/******************************************************************************/

double r8_abs ( double x )

/******************************************************************************/
/*
  Purpose:

    R8_ABS returns the absolute value of an R8.

  Licensing:

    This code is distributed under the GNU LGPL license. 

  Modified:

    07 May 2006

  Author:

    John Burkardt

  Parameters:

    Input, double X, the quantity whose absolute value is desired.

    Output, double R8_ABS, the absolute value of X.
*/
{
  double value;

  if ( 0.0 <= x )
  {
    value = x;
  } 
  else
  {
    value = - x;
  }
  return value;
}
/******************************************************************************/

double r8_epsilon ( void )

/******************************************************************************/
/*
  Purpose:

    R8_EPSILON returns the R8 round off unit.

  Discussion:

    R8_EPSILON is a number R which is a power of 2 with the property that,
    to the precision of the computer's arithmetic,
      1 < 1 + R
    but 
      1 = ( 1 + R / 2 )

  Licensing:

    This code is distributed under the GNU LGPL license. 

  Modified:

    08 May 2006

  Author:

    John Burkardt

  Parameters:

    Output, double R8_EPSILON, the double precision round-off unit.
*/
{
  double r;

  r = 1.0;

  while ( 1.0 < ( double ) ( 1.0 + r )  )
  {
    r = r / 2.0;
  }

  return ( 2.0 * r );
}
/******************************************************************************/

double r8_max ( double x, double y )

/******************************************************************************/
/*
  Purpose:

    R8_MAX returns the maximum of two R8's.

  Licensing:

    This code is distributed under the GNU LGPL license. 

  Modified:

    18 August 2004

  Author:

    John Burkardt

  Parameters:

    Input, double X, Y, the quantities to compare.

    Output, double R8_MAX, the maximum of X and Y.
*/
{
  double value;

  if ( y < x )
  {
    value = x;
  } 
  else
  {
    value = y;
  }
  return value;
}
/******************************************************************************/

double r8_sign ( double x )

/******************************************************************************/
/*
  Purpose:

    R8_SIGN returns the sign of an R8.

  Licensing:

    This code is distributed under the GNU LGPL license. 

  Modified:

    18 October 2004

  Author:

    John Burkardt

  Parameters:

    Input, double X, the number whose sign is desired.

    Output, double R8_SIGN, the sign of X.
*/
{
  double value;

  if ( x < 0.0 )
  {
    value = -1.0;
  } 
  else
  {
    value = 1.0;
  }
  return value;
}
/******************************************************************************/

void timestamp ( void )

/******************************************************************************/
/*
  Purpose:

    TIMESTAMP prints the current YMDHMS date as a time stamp.

  Example:

    31 May 2001 09:45:54 AM

  Licensing:

    This code is distributed under the GNU LGPL license. 

  Modified:

    24 September 2003

  Author:

    John Burkardt

  Parameters:

    None
*/
{
# define TIME_SIZE 40

  static char time_buffer[TIME_SIZE];
  const struct tm *tm;
  size_t len;
  time_t now;

  now = time ( NULL );
  tm = localtime ( &now );

  len = strftime ( time_buffer, TIME_SIZE, "%d %B %Y %I:%M:%S %p", tm );

  printf ( "%s\n", time_buffer );

  return;
# undef TIME_SIZE
}
/******************************************************************************/

double zero ( double a, double b, double machep, double t, 
  double f ( double x ) )

/******************************************************************************/
{
  double c;
  double d;
  double e;
  double fa;
  double fb;
  double fc;
  double m;
  double p;
  double q;
  double r;
  double s;
  double sa;
  double sb;
  double tol;
/*
  Make local copies of A and B.
*/
  sa = a;
  sb = b;
  fa = f ( sa );
  fb = f ( sb );

  c = sa;
  fc = fa;
  e = sb - sa;
  d = e;

  for ( ; ; )
  {
    if ( r8_abs ( fc ) < r8_abs ( fb ) )
    {
      sa = sb;
      sb = c;
      c = sa;
      fa = fb;
      fb = fc;
      fc = fa;
    }

    tol = 2.0 * machep * r8_abs ( sb ) + t;
    m = 0.5 * ( c - sb );

    if ( r8_abs ( m ) <= tol || fb == 0.0 )
    {
      break;
    }

    if ( r8_abs ( e ) < tol || r8_abs ( fa ) <= r8_abs ( fb ) )
    {
      e = m;
      d = e;
    }
    else
    {
      s = fb / fa;

      if ( sa == c )
      {
        p = 2.0 * m * s;
        q = 1.0 - s;
      }
      else
      {
        q = fa / fc;
        r = fb / fc;
        p = s * ( 2.0 * m * a * ( q - r ) - ( sb - sa ) * ( r - 1.0 ) );
        q = ( q - 1.0 ) * ( r - 1.0 ) * ( s - 1.0 );
      }

      if ( 0.0 < p )
      {
        q = - q;
      }
      else
      {
        p = - p;
      }

      s = e;
      e = d;

      if ( 2.0 * p < 3.0 * m * q - r8_abs ( tol * q ) &&
        p < r8_abs ( 0.5 * s * q ) )
      {
        d = p / q;
      }
      else
      {
        e = m;
        d = e;
      }
    }
    sa = sb;
    fa = fb;

    if ( tol < r8_abs ( d ) )
    {
      sb = sb + d;
    }
    else if ( 0.0 < m )
    {
      sb = sb + tol;
    }
    else
    {
      sb = sb - tol;
    }

    fb = f ( sb );

    if ( ( 0.0 < fb && 0.0 < fc ) || ( fb <= 0.0 && fc <= 0.0 ) )
    {
      c = sa;
      fc = fa;
      e = sb - sa;
      d = e;
    }
  }
  return sb;
}
/******************************************************************************/

void zero_rc ( double a, double b, double t, double *arg, int *status, 
  double value )

/******************************************************************************/
{
  static double c;
  static double d;
  static double e;
  static double fa;
  static double fb;
  static double fc;
  double m;
  static double machep;
  double p;
  double q;
  double r;
  double s;
  static double sa;
  static double sb;
  double tol;
/*
  Input STATUS = 0.
  Initialize, request F(A).
*/
  if ( *status == 0 )
  {
    machep = r8_epsilon ( );

    sa = a;
    sb = b;
    e = sb - sa;
    d = e;

    *status = 1;
    *arg = a;
    return;
  }
/*
  Input STATUS = 1.
  Receive F(A), request F(B).
*/
  else if ( *status == 1 )
  {
    fa = value;
    *status = 2;
    *arg = sb;
    return;
  }
/*
  Input STATUS = 2
  Receive F(B).
*/
  else if ( *status == 2 )
  {
    fb = value;

    if ( 0.0 < fa * fb )
    {
      *status = -1;
      return;
    }
    c = sa;
    fc = fa;
  }
  else
  {
    fb = value;

    if ( ( 0.0 < fb && 0.0 < fc ) || ( fb <= 0.0 && fc <= 0.0 ) )
    {
      c = sa;
      fc = fa;
      e = sb - sa;
      d = e;
    }
  }
/*
  Compute the next point at which a function value is requested.
*/
  if ( r8_abs ( fc ) < r8_abs ( fb ) )
  {
    sa = sb;
    sb = c;
    c = sa;
    fa = fb;
    fb = fc;
    fc = fa;
  }

  tol = 2.0 * machep * r8_abs ( sb ) + t;
  m = 0.5 * ( c - sb );

  if ( r8_abs ( m ) <= tol || fb == 0.0 )
  {
    *status = 0;
    *arg = sb;
    return;
  }

  if ( r8_abs ( e ) < tol || r8_abs ( fa ) <= r8_abs ( fb ) )
  {
    e = m;
    d = e;
  }
  else
  {
    s = fb / fa;

    if ( sa == c )
    {
      p = 2.0 * m * s;
      q = 1.0 - s;
    }
    else
    {
      q = fa / fc;
      r = fb / fc;
      p = s * ( 2.0 * m * a * ( q - r ) - ( sb - sa ) * ( r - 1.0 ) );
      q = ( q - 1.0 ) * ( r - 1.0 ) * ( s - 1.0 );
    }

    if ( 0.0 < p )
    {
      q = - q;
    }
    else
    {
      p = - p;
    }
    s = e;
    e = d;

    if ( 2.0 * p < 3.0 * m * q - r8_abs ( tol * q ) && 
         p < r8_abs ( 0.5 * s * q ) )
    {
      d = p / q;
    }
    else
    {
      e = m;
      d = e;
    }
  }

  sa = sb;
  fa = fb;

  if ( tol < r8_abs ( d ) )
  {
    sb = sb + d;
  }
  else if ( 0.0 < m )
  {
    sb = sb + tol;
  }
  else
  {
    sb = sb - tol;
  }

  *arg = sb;
  *status = *status + 1;

  return;
}
