// Copyright (C) 2013 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

// <-- JVM NOT MANDATORY -->

path = distfun_getpath (  );
exec(fullfile(path,"tests","unit_tests","testingutilities.sce"));

// Check expansion of arguments
a = matrix(1:6,2,3);
b = a+10;
checkRNG ( 2 , 3 , list(distfun_logurnd ,a,b));

// Check mean and variance
distfun_seedset(1.);
N = 100000;
for a = [1 3 6]
    b=a+1;
    computed = distfun_logurnd(a,b,[1 N]);
    [M,V] = distfun_logustat ( a , b );
    [MLog,VLog] = distfun_unifstat ( a , b );
    // Check mean
    c = mean(computed);
    assert_checkalmostequal(c,M,1.e-1);
    // Check mean(log(X))
    c = mean(log(computed));
    assert_checkalmostequal(c,MLog,1.e-1);
    // Check variance
    c = variance(computed);
    assert_checkalmostequal(c,V,1.e-1);
    // Check variance
    c = variance(log(computed));
    assert_checkalmostequal(c,VLog,1.e-1);
end
// The number of classes in the histogram
// NC must be even.
NC = 2*12;
N=10000;
rtol = 1.e-2;
atol = 1.e-2;
for a = [1 3 6]
    b=a+1;
    R = distfun_logurnd(a,b,1,N);
    checkRNGLaw ( R , list(distfun_logucdf,a,b) , N , NC , rtol , atol );
end