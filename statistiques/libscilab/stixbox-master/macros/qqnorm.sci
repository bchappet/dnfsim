// Copyright (C) 2010 - DIGITEO - Michael Baudin
// Copyright (C) 1993 - 1995 - Anders Holtsberg
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function []=qqnorm(x,ps)
[nargout,nargin] = argn(0)
//QQNORM   Normal probability paper
//
//         qqnorm(x)
//         qqnorm(x, symbol)
//
//         Data on x-axis and  qnorm((i-1/2)/n)  on y-axis.
//	  The second optional argument is the plot symbol, e g '+'.
//
//         See also QQPLOT
 
if nargin<2 then
  ps = '*';
end
%v = x
if min(size(%v))==1 then %v=gsort(%v),else %v=gsort(%v,'r'),end
x = %v($:-1:1,:);
n = max(size(x));
X = ((1:n)-1/2)/n;
Y = sqrt(2)*erfinv(2*X-1);
mtlb_plot(x,Y,ps);
xtitle(' ','Data',' ');
xtitle(' ',' ','Quantile');
endfunction
