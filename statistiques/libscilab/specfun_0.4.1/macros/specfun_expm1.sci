// Copyright (C) 2010 - DIGITEO - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function y = specfun_expm1 ( x )
  // Compute exp(x)-1 accurately for small values of x.
  //
  // Calling Sequence
  //   y = specfun_expm1 ( x )
  //
  // Parameters
  //   x : a matrix of doubles
  //   y : a matrix of doubles, exp(x)-1
  //
  // Description
  // Returns exp(x)-1 accurately for small values of x.
  //
  // The method suggested by Kahan is used to improve accuracy.
  //
  // Examples
  // specfun_expm1(2)
  //
  // // Plot this function for positive inputs.
  // scf();
  // plot(linspace(-0.5,2,1000),specfun_expm1)
  //
  // // Compare the precision of expm1 and exp
  // // for small x: expm1 gives the exact result
  // // while exp(x)-1 is not as accurate.
  // x = 0.000001;
  // e = 1.000000500000166666e-6 // From Wolfram Alpha
  // y1 = specfun_expm1(x); abs(y1-e)/abs(e)
  // y2 = exp(x)-1;  abs(y2-e)/abs(e)
  //
  // Authors
  // Michael Baudin, DIGITEO, 2010
  //
  // Bibliography
  //   Don Hatch, http://www.plunk.org/~hatch/rightway.php
  //   http://www.opengroup.org/onlinepubs/000095399/functions/expm1.html

  [lhs, rhs] = argn()
  apifun_checkrhs ( "specfun_expm1" , rhs , 1 )
  apifun_checklhs ( "specfun_expm1" , lhs , 1 )
  //
  apifun_checktype ( "specfun_expm1" , x , "x" , 1 , "constant" )
  //
  y = x
  //
  u = exp(x)
  m = find(u-1 <> -1 & u <> 1)
  y(m) = (u(m)-1).*x(m)./log(u(m))
  //
  if ( u <> [] ) then
    y(find(u-1==-1)) = -1
  end
  y(find(x==-%inf)) = -1
  y(find(x==%inf)) = %inf
endfunction



