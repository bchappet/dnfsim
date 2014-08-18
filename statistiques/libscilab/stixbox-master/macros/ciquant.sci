// Copyright (C) 2010 - DIGITEO - Michael Baudin
// Copyright (C) 1993 - 1995 - Anders Holtsberg
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function [ci]=ciquant(x,p,C)
// Nonparametric confidence interval for quantile
//	  ci = ciquant(x,p,C);
// 
//         Input C is confidence level for the interval (default 0.95),
//	        p is the probability that define quantile,
//               x is a sample; if x is a matrix, the procedure 
//               is colonwise.
//         The interval ci is of the form 
//                   [LeftLimit, PointEstimate, RightLimit]'. 
//         The interval is constructed conservatively in both ends, 
//         that is P(q<LeftLimit) <= C/2 and similarly for the upper limit.
// 
//	  See also QUANTILE.
[lhs,rhs]=argn(0); 
if rhs <= 2 then C = 0.95 ; end 
if size(x,1)==1 then x = x'; end
[n,m] = size(x);
if min(m,n)==1 then x= gsort(x,'g','i'),else x=gsort(x,'r','i'),end
x = [-%inf*ones(1,m);x;%inf*ones(1,m)];
pr = pbinom(0:n-1,n,p);
a = (1-C)/2;
J = find((pr>=a) & (pr<=(1-a)));
L = x(min(J),:);
H = x(max(J)+2,:);
ci = [L;quantile(x,p,2);H];

endfunction
