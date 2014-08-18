// Copyright (C) 2012 - Michael Baudin
// Copyright (C) 2009 - 2011 - DIGITEO - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

// <-- JVM NOT MANDATORY -->

path = distfun_getpath (  );
exec(fullfile(path,"tests","unit_tests","testingutilities.sce"));

//
// Consistency Checks
//
mu=5;
sigma=6;
p=linspace(0.1,0.9,5);
rtol=1.e-12;
CheckInverseCDF("distfun_norminv",list(distfun_norminv,mu,sigma),p,rtol);

//
// Test accuracy
//
precision = 100*%eps;
path=distfun_getpath();
dataset = fullfile(path,"tests","unit_tests","norm","normal.R.dataset.csv");
table = readCsvDataset(dataset);
nt = size(table,"r");
for k = 1 : nt
    x = table(k,1);
    mu = table(k,2);
    sigma = table(k,3);
    p = table(k,5);
    q = table(k,6);
    if (p<q) then
        computed = distfun_norminv ( p , mu , sigma );
	else
        computed = distfun_norminv ( q , mu , sigma , %f );
	end
    assert_checkalmostequal ( computed , x , precision );
end

//
// Test accuracy of distfun_norminv
//
path=distfun_getpath();
dataset = fullfile(path,"tests","unit_tests","norm","normal.yalta.dataset.csv");
table = readCsvDataset(dataset);
mu = 0.;
sigma = 1.;
precision = 1.e-5;
nt = size(table,"r");
for k = 1 : nt
    p = table(k,1);
    expected = table(k,2);
    computed = distfun_norminv ( p , mu , sigma );
    assert_checkalmostequal ( computed , expected , precision );
end

//
// See upper tail: compare with R
x=distfun_norminv(0.0001,1.,2.);
assert_checkalmostequal ( x , -6.4380329709113599534 , 1.e-15 );
x=distfun_norminv(0.0001,1.,2.,%f);
assert_checkalmostequal ( x ,  8.4380329709113599534 , 1.e-6 );
x=distfun_norminv(1.e-10,1.,2.,%f);
assert_checkalmostequal ( x , 13.722681804808111394 , 1.e-15 );
