// Copyright (C) 2010 - DIGITEO - Michael Baudin
// Copyright (C) 1993 - 1995 - Anders Holtsberg
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function [z]=lodds(p)
z=[];
//LODDS    Log odds function.
// 
//	  z = lodds(p)
// 
//	  The function is log(p/(1-p)).
 
       

 
if or(mtlb_any(abs(2*p-1)>=1)) then
  error('A probability input please');
end
z = log(p ./ (1-p));
endfunction
