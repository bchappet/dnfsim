// Copyright (C) 2010 - DIGITEO - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function y = specfun_gammainc ( varargin )
  // The incomplete gamma function.
  //
  // Calling Sequence
  //   y = specfun_gammainc ( x , a )
  //   y = specfun_gammainc ( x , a , tail )
  //
  // Parameters
  //   x : a matrix of doubles, the upper limit of integration of the gamma density. Must be real (not complex) and nonnegative.
  //   a : a matrix of doubles, the shape parameter of the gamma density. Must be real (not complex) and nonnegative.
  //   tail : a 1 x 1 matrix of strings, the tail of the CDF function, "upper" or "lower" (default tail="lower"). Set tail="lower" to get the probability from the CDF gamma distribution function, set tail="upper" to get the complementary probability, that is q = 1-p.
  //   y : a matrix of doubles, the incomplete gamma function.
  //
  // Description
  // Returns the incomplete gamma function.  If tail="lower", the function definition is:
  //
  // <latex>
  // \begin{eqnarray}
  // f(x,a) = \frac{1}{\Gamma(a)}\int_0^x e^{-t} t^{a-1} dt
  // \end{eqnarray}
  // </latex>
  //
  // The tail option may be used to overcome the limitation of floating point 
  // arithmetic. 
  // If tail="upper", the function definition is:
  //
  // <latex>
  // \begin{eqnarray}
  // f(x,a) = 1 - \frac{1}{\Gamma(a)}\int_0^x  e^{-t} t^{a-1} dt
  // \end{eqnarray}
  // </latex>
  //
  // We may use tail="upper" when the output of the gamminc 
  // function with tail="lower" is very close to 1. 
  //
  // If any of the input arguments a or x is a 1-by-1 matrix while the other one is a m-by-n matrix,
  // then the 1-by-1 matrix is expanded to m-by-n.
  // If the sizes of the two arguments do not match, we generate an error.
  //
  // If a=0 and tail=="lower", we return y=1, whatever the value of x.
  // If a=0 and tail=="upper", we return y=0, whatever the value of x.
  //
  // Examples
  // specfun_gammainc(1,2) // Expected : 0.264241117657115
  // specfun_gammainc(2,3) // Expected : 0.323323583816936
  // specfun_gammainc(2,3,"lower") // Expected : 0.323323583816936
  // // We have specfun_gammainc(x,a,"lower") == 1 - specfun_gammainc(x,a,"upper")
  // specfun_gammainc(2,3,"upper") // Expected : 0.676676416183064
  //
  // // The following example shows how to use the tail argument.
  // // For a=1 and x>40, the result is so close to 1 that the 
  // // result is represented by the floating point number y=1.
  // specfun_gammainc(40,1) // Expected : 1
  // // This is why we may compute the complementary probability with
  // // the tail option.
  // specfun_gammainc(40,1,"upper") // Expected : 4.248354255291594e-018
  //
  // // Show the expansion of a
  //  x = [1 2 3;4 5 6];
  //  a = 2;
  //  specfun_gammainc(x,a)
  //
  // Authors
  // Michael Baudin, DIGITEO, 2010
  //

  [lhs, rhs] = argn()
  apifun_checkrhs ( "specfun_gammainc" , rhs , 2:3 )
  apifun_checklhs ( "specfun_gammainc" , lhs , 1 )
  //
  x = varargin(1)
  a = varargin(2)
  if ( rhs < 3 ) then
    tail = "lower"
  else
    tail = varargin(3)
  end
  //
  apifun_checktype ( "specfun_gammainc" , x , "x" , 1 , "constant" )
  apifun_checktype ( "specfun_gammainc" , a , "a" , 2 , "constant" )
  apifun_checktype ( "specfun_gammainc" , tail , "tail" , 3 , "string" )
  //
  apifun_checkrange ( "specfun_gammainc" , x , "x" , 1 , 0 , %inf )
  apifun_checkrange ( "specfun_gammainc" , a , "a" , 2 , 0 , %inf )
  apifun_checkoption ( "specfun_gammainc" , tail , "tail" , 3 , ["lower" "upper"] )
  //
  // Expand arguments
  [ma,na] = size(a)
  [mx,nx] = size(x)
  if ( prod([mx,nx])==1 & prod([ma,na])<>1 ) then
    x=x*ones(a);
  elseif ( prod([mx,nx])<>1 & prod([ma,na])==1 ) then
    a=a*ones(x);
  else
    if ( [mx,nx] <> [ma,na] ) then
      localstr = gettext("%s: Arguments #%d and #%d do not match. Size of %s is [%d,%d] while size of %s is [%d,%d].")
      errmsg = msprintf(localstr, "specfun_gammainc", 1 , 2 , "x" , mx , nx , "a" , ma , na )
      error(errmsg)
    end
  end
  Rate = ones(a)
  //
  // Make a special case for entries with a=0
  kzeroa = find(a==0)
  a(kzeroa) = 1
  //
  [p,q] = cdfgam("PQ",x,a,Rate)
  if ( tail=="lower" ) then
    y = p
  else
    y = q
  end
  //
  // If a == 0, then y = 1 or 1 depending on tail
  if ( tail=="lower" ) then
    y(kzeroa)=1
  else
    y(kzeroa)=0
  end
endfunction




