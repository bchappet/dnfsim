// Copyright (C) 2010 - DIGITEO - Michael Baudin
// Copyright (C) 1993 - 1995 - Anders Holtsberg
// Copyright (C) 1998 - 2000 - Mathematique Universite de Paris-Sud - Jean Coursol
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function [b,Ib,e,s,Is]=lsfit(y,x,C)
b=[];Ib=[];e=[];s=[];Is=[];
[nargout,nargin] = argn(0)
//LSFIT	  Fit a multiple regression normal model.
//
//     	  [b, Ib, e, s, Is] = lsfit(y,x,C)
//
//         Input :  y dependant variate (column vector)
//                  x regressor variates
//
//                  C confidence level for the confidence intervals
//                    (default0.95).
//
// 	  Output : b  vector of point estimates, 
//                  Ib confidence intervals, 
//                  s  estimated standard deviation of error
//                     with confidence interval Is,
//                  e  residuals.
//
//         An intercept not included in x is automatically  
//         added as an extra column.
//
//	  See also LINREG and LSSELECT.

       



 
if nargin<3 then
  C = 0.95;
end

if C==[] then
  C = 0.95;
end  

if size(y,2)>1 then
  error('Input y must be column vector');
end
n = mtlb_length(y);
one = ones(n,1);
if or((one-x*(x\one))>100*%eps) then
  printf('   Intercept column added \n ');
  x = [x,ones(n,1)];
end
nb = size(x,2);
 
b = x\y;
if nargout<2 then
  return
end
yh = x*b;
e = y-yh;
d2 = mtlb_sum(e.^2)/(n-nb);
sb = sqrt(diag(inv(x'*x))*d2);
if (n-nb)<200 then
  t = qt(1-(1-C)/2,n-nb);
else
  t = qnorm(1-(1-C)/2);
end
Ib = [b-t*sb,b+t*sb];
 
if nargout<4 then
  return
end
s = sqrt(d2);
pupper = 1-(1-C)/2;
plower = (1-C)/2;
Is = sqrt((n-nb) ./ qchisq([pupper,plower],n-nb))*s;
endfunction
