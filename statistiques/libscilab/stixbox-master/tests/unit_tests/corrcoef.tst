// Copyright (C) 2013 - Michael Baudin
// Copyright (C) 2010 - INRIA - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt


//
// <-- JVM NOT MANDATORY -->
// <-- ENGLISH IMPOSED -->

// "Introduction to probability and statistics for
// engineers and scientists."
// Sheldon Ross
// Chapter 2 Descriptive statistics
// Example 2.6a
x = [24.2;22.7;30.5;28.6;25.5;32;28.6;26.5;25.3;26;24.4;24.8;20.6;..
  25.1;21.4;23.7;23.9;25.2;27.4;28.3;28.8;26.6];
y = [25;31;36;33;19;24;27;25;16;14;22;23;20;25;25;23;27;30;33;32;35;24];
computed = corrcoef ( x , y );
assert_checkalmostequal ( computed , 0.4189 , 1.e-3 );

// http://en.wikipedia.org/wiki/Correlation_and_dependence
x = [0 10 101 102];
y = [1 100 500 2000];
computed = corrcoef ( x , y );
assert_checkalmostequal ( computed , 0.7544 , 1.e-4 );
//
x = [12 16 13 18 19 12 18 19 12 14];
y = [73 67 74 63 73 84 60 62 76 71];
computed = corrcoef ( x , y );
assert_checkalmostequal ( computed , -0.7638 , 1.e-3 );
//
//
// Check with one argument
[X,txt] = getdata(1);
R=corrcoef(X);
expected=[
    1.           0.4615668    0.6934031  
    0.4615668    1.           0.3544662  
    0.6934031    0.3544662    1.         
];
assert_checkalmostequal ( R , expected , [],1.e-4 );
// Check consistency with corrcoef(x,y)
for i=1:3
    for j=1:3
        Rij=corrcoef(X(:,i),X(:,j));
        assert_checkalmostequal ( R(i,j) , Rij);
    end
end