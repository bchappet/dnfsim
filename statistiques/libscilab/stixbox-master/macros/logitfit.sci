// Copyright (C) 2010 - DIGITEO - Michael Baudin
// Copyright (C) 1993 - 1995 - Anders Holtsberg
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function [b,Ib,Vb]=logitfit(y,x,C)
b=[];Ib=[];Vb=[];
[nargout,nargin] = argn(0)
//LOGITFIT Fit a logistic regression model.
// 
//     	  [b, Ib, Vb] = logitfit(y,X,C)
// 
//	  Fit the model log(p/(1-p)) = X*b, where p is the probability
//	  that y is 1 and not 0. Output b is vector of point estimates,
//	  Ib is confidence intervals, and Vb is the estimated variance
//	  matrix of b. Input C is confidence level for the confidence
//	  intervals, default is 0.95.
// 
//         If an intercept is not included in X it is automatically added
//         as an extra column.
// 
//	  See also LODDS and LODDSINV.
 
       

 
if nargin<3 then
  C = 0.95;
end
if size(y,2)>1 then
  error('Input y must be column vector');
end
n = mtlb_length(y);
if mtlb_sum(bool2s(y==1|y==0))<n then
  error('Hey, only 0 or 1 as response varable y');
end
one = ones(n,1);
if or(abs(one-x*x\one)>0.0000000001) then
  fprintf('   Intercept column added \n ');
  x = [x,ones(n,1)];
end
nb = size(x,2);
 
b = x\(4*y-2);
 
for i = 1:50
  z = x*b;
  g1 = 1+exp(-z);
  g0 = 1+exp(z);
  df1 = -1 ./ g0;
  df0 = 1 ./ g1;
  degreef = mtlb_sum((y .* df1+(1-y) .* df0)*ones(1,nb) .* x)';
  ddf = 1 ./ (g0+g1)*ones(1,nb) .* x'*x;
  b = b-ddf\degreef;
  if and(abs(degreef)<0.0001) then
    break
     
  end
end
 
if i==50 then
  error('No convergence');
end
 
logL = y .* log(g1)+(1-y) .* log(g0);
Vb = inv(ddf);
lamda = qnorm(1-(1-C)/2);
Ib = lamda*sqrt(diag(Vb));
Ib = [b-Ib,b+Ib];
 
endfunction
