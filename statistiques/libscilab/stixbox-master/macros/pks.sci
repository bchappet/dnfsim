// Copyright (C) 2010 - DIGITEO - Michael Baudin
// Copyright (C) 1993 - 1995 - Anders Holtsberg
// Copyright (C) 2001-2002 - ENPC - Jean-Philippe Chancelier
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function [p]=pks(x)
p=[];
//PROBKS        Kolmogorov Smirnov distribution function
// 
//          p = pks(x)
//       Input     x  vector or matrix
//       Output    p  Prob( KS < x )
//   Adapted from Press, Teukolsky, Vetterling and Flannery,
//   Numerical Recipes in C p620.
//   From ? Version 1.0 RHS 8/11/93
//   
//   modifie le 26 sept 2001 (jpc) 
//   
//   
x2 = -2*x.^2;
factor = -2;
// the computation is performed just for x > 0.14 
// else value is assumed to be 0 
tag = x > 0.14;
p = bool2s(tag) 
absterm = 0;
n = 0;
 
while  or(tag) & ( n<100 ) then
  n = n+1;
  term = factor .* exp(x2 .* (n^2)) .* bool2s(tag);
  p = p + term;
  absterm1 = abs(term);
  tag = bool2s(tag) .* (bool2s(absterm1>0.001 .* absterm)) .* (bool2s(absterm1>0.00000001 .* abs(p)));
  factor = -factor;
  absterm = absterm1;
end
 
p = p .* (1-tag);
// si non convergence
endfunction
