// Copyright (C) 2010 - DIGITEO - Michael Baudin
// Copyright (C) 1993 - 1995 - Anders Holtsberg
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function [p]=loddsinv(z,b)
p=[];
[nargout,nargin] = argn(0)
//LODDSINV Compute the inverse of log odds.
//
//	  p = loddsinv(z,b)
//
//	  The function is equal to 1/(1+exp(-u)) with 
//              u = z if only one input argument is given
//              u = z*b if size(z,2) = length(b)
//	       u = z*b(1:n-1) + b(n) if size(z,2) < length(b)

       


 
if nargin>1 then
  X = z;
  n = mtlb_length(b);
  if size(X,2)<n then
    // mtlb_e(b,1:n-1) may be replaced by b(1:n-1) if b is a vector.
    z = b(n)+X*mtlb_e(b,1:n-1);
  else
    z = X*b;
  end
end
p = 1 ./ (1+exp(-z));
endfunction
