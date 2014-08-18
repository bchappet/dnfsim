// Copyright (C) 2008 - INRIA - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt


a = (1:5)';
s = [0 2 4 6 8 10 12 14 16 18 20];
tf = specfun_ismember(a, s);
expected = [%f %t %f %t %f]';
assert_checkequal ( tf , expected );
//
// A use-case with matrices
a = [
7 35 14 86 76   
15 51 24 96 49   
35 40 46 35 93   
85 34 74 82 22
];
s = [51 74 22 15 86];
tf = specfun_ismember(a, s);
expected = [
     %f     %f     %f     %t     %f
     %t     %t     %f     %f     %f
     %f     %f     %f     %f     %f
     %f     %f     %t     %f     %t
];
assert_checkequal ( tf , expected );
//
// A use-case with matrices
tf = specfun_ismember([], [51 74 22 15 86]);
expected = [];
assert_checkequal ( tf , expected );
//
// A use-case with matrices
tf = specfun_ismember([51 74 22 15 86],[]);
expected = [%f %f %f %f %f];
assert_checkequal ( tf , expected );
//
// With strings
a = ["1" "2" "3" "4" "5"]';
s = ["0" "2" "4" "6" "8" "10" "12" "14" "16" "18" "20"];
tf = specfun_ismember(a, s);
expected = [%f %t %f %t %f]';
assert_checkequal ( tf , expected );
//
// With strings
a = ["1" "2" "3" "4" "5"];
s = ["0" "2" "4" "6" "8" "10" "12" "14" "16" "18" "20"];
tf = specfun_ismember(a, s);
expected = [%f %t %f %t %f];
assert_checkequal ( tf , expected );

