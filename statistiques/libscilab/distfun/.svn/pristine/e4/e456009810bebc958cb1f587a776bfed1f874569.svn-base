// Copyright (C) 2012 - 2013 - Michael Baudin
// Copyright (C) 2011 - INRIA - Michael Baudin
// Copyright (C) 2011 - DIGITEO - Michael Baudin
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
mu = matrix(1:6,2,3);
sigma = matrix(2:7,2,3);
checkRNG ( 2 , 3 , list(distfun_lognrnd ,mu,sigma));

// Check mean and variance
distfun_seedset(1.);
N = 100000;
for mu = [1 3 6]
    for sigma = [1 0.1 0.01 0.001]
        computed = distfun_lognrnd(mu,sigma,[1 N]);
        [M,V] = distfun_lognstat ( mu , sigma );
        [MLog,VLog] = distfun_normstat ( mu , sigma );
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
end
// The number of classes in the histogram
// NC must be even.
NC = 2*12;
N=10000;
rtol = 1.e-2;
atol = 1.e-2;
for mu = [1 3 6]
    for sigma = [1 0.1 0.01 0.001]
    R = distfun_lognrnd(mu,sigma,1,N);
    checkRNGLaw ( R , list(distfun_logncdf,mu,sigma) , N , NC , rtol , atol );
    end
end
