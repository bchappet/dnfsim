// Copyright (C) 2010 - DIGITEO - Michael Baudin
// Copyright (C) 1993 - 1995 - Anders Holtsberg
// Copyright (C) 1998 - 2000 - Mathematique Universite de Paris-Sud - Jean Coursol
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function [x]=rexpweib(n,l,a)
x=[];
[nargout,nargin] = argn(0)
//REXP   Random numbers from the exponential or weibull distributions
// 
//         x=rexpweib(n,l)
//       Input    n  positive integer or vector [lig,col] of integers
//                l  positive real
//                a  Weibull parameter (default a=1: exponential dist.)
// 
//       Output   x  n-vector of random numbers
//                   chosen from a weibull distribution
//                            ( F(x) = 1-exp-[(l*x)^a]  )
// 
 

// 
if nargin<2|nargin>3 then
  error('Wrong number of input parameters');
  return
   
end
 
if nargin==2 then
  a = 1;
end
 
if mtlb_length(n)==1 then
  n = [n,1];
end
 
if mtlb_length(n)~=2|mtlb_length(l)>1|mtlb_length(a)>1 then
  error('Wrong input parameter type');
  return
   
end
 
if l<0|a<=0 then
  error('Wrong input parameter value');
  return
   
end
 
x = -log(grand(n(1),n(2),"def"))/l;
x = x.^(1/a);
endfunction
