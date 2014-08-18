// Copyright (C) 2014 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

// Samples from Binomial distribution with 
// N=12 and pr=0.42
data = [7 3 7 7 3 8 7 4 6 4];
parmhat=distfun_binofitmm(data);
exact=[13.    0.4214286];
assert_checkalmostequal(parmhat,exact,1.e-7);
// The parameters are not equal to the 
// moments : we cannot check for equality.
