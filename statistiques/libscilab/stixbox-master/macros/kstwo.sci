// Copyright (C) 2010 - DIGITEO - Michael Baudin
// Copyright (C) 1993 - 1995 - Anders Holtsberg
// Copyright (C) 1998 - 2000 - Mathematique Universite de Paris-Sud - Jean Coursol
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function [d,p]=kstwo(x,y)
d=[];p=[];
//KSTWO            Kolmogorov-Smirnov statistic from two samples
// 
//            [d,p] = KSTWO(x,y)
//          Input   x,y samples (column vectors)
// 
//          Output  d   Kolmogorov-Smirnov statistic d
//                  p   significance level p for the null hypothesis
//                      that the data sets x and y are drawn from the
//                      same distribution.
 
// Adapted from Press, Teukolsky, Vetterling
// and Flannery, Numerical Recipes in Fortran p619.
// Uses PROBKS
 


 
%v = x
if min(size(%v))==1 then %v=gsort(%v),else %v=gsort(%v,'r'),end
x = %v($:-1:1,:);
%v = y
if min(size(%v))==1 then %v=gsort(%v),else %v=gsort(%v,'r'),end
y = %v($:-1:1,:);
// gsort in ascending order
 
dx = max(size(x));
dy = max(size(y));
// samples lengths
 
kx = 1;
ky = 1;
fnx = 0;
fny = 0;
count = 1;
// initialise variables
while (kx<=dx)&(ky<=dy) then
  // generate cumulative distribution
  ddx = x(kx);
  ddy = y(ky);
  // functions
  if ddx<=ddy then
    fnx = kx/dx;
    kx = kx+1;
  end
  if ddy<=ddx then
    fny = ky/dy;
    ky = ky+1;
  end
  dt(1,count) = abs(fnx-fny);
  // difference between functions
  count = count+1;
end
 
d = mtlb_max(dt);
// maximum of difference
N = sqrt(dx*dy/(dx+dy));
//p=probks((N+0.12+0.11/N)*d);		% calculate probability
p = 1-pks((N+0.12+0.11/N)*d);
// calculate probability
endfunction
