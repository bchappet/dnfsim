// Copyright (C) 2012 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

//
// Continuous distribution
x=3;
v1=5;
v2=6;
computed = distfun_genericpdf(x,list(distfun_fcdf,v1,v2));
expected = distfun_fpdf(x,v1,v2);
assert_checkalmostequal(computed,expected,1.e-8);
//
// Integer distribution
x=3;
pr = 0.3;
computed = distfun_genericpdf(x,list(distfun_geocdf,pr),%f);
expected = distfun_geopdf(x,pr);
assert_checkalmostequal(computed,expected,1.e-14);
