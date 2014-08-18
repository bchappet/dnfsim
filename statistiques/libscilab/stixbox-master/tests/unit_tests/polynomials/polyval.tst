// Copyright (C) 2013 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

p=[
    2.111148559743582870D-17  
  - 4.038880514217270679D-01  
    1.268851735219194143D+00  
  - 2.342223135071501081D-02  
]';
x = linspace(0,%pi,10)';
yexact = sin(x);
y = polyval(p,x);
assert_checkalmostequal(y,yexact,[],1.e-1);

// Evaluate at x matrix
p=[
    2.111148559743582870D-17  
  - 4.038880514217270679D-01  
    1.268851735219194143D+00  
  - 2.342223135071501081D-02  
]';
x = linspace(0,%pi,12);
x=matrix(x,3,4);
f=polyval(p,x);
y=sin(x);
assert_checkalmostequal(y,f,[],1.e-1);
// Evaluate at given integers
p=[3 2 1];
x=[5 7 9]
y=polyval(p,x);
expected=[86   162   262]
assert_checkequal(y,expected);

// Source: http://www.census.gov/population/censusdata/table-4.pdf
// USA population : Population: 1790 to 1990
// Caution : the urban definition changes in 1950
cdate=(1790:10:1990)';
pop=[
    3.929214   
    5.308483   
    7.239881   
    9.638453   
    12.860702  
    17.063353  
    23.191876  
    31.443321  
    38.558371  
    50.189209  
    62.979766  
    76.212168  
    92.228496  
    106.02154  
    123.20262  
    132.16457  
    151.3258   
    179.32317  
    203.30203  
    226.5422   
    248.70987  
 ];
// Calculate fit parameters
[p,S] = polyfit(cdate,pop,2);
// Evaluate the fit and the prediction error estimate (delta)
[pop_fit,delta] = polyval(p,cdate,S);
delta_expected=[
    3.2220547  
    3.0813545  
    2.9849684  
    2.9253889  
    2.8942908  
    2.8833769  
    2.8850804  
    2.8930172  
    2.9022024  
    2.9091074  
    2.9116398  
    2.9091074  
    2.9022024  
    2.8930172  
    2.8850804  
    2.8833769  
    2.8942908  
    2.9253889  
    2.9849684  
    3.0813545  
    3.2220547  
];
assert_checkalmostequal(delta,delta_expected,1.e-5);
pop_fitexpected=[
    5.4486838  
    5.2407157  
    6.3340153  
    8.7285827  
    12.424418  
    17.42152   
    23.719891  
    31.319529  
    40.220435  
    50.422608  
    61.926049  
    74.730758  
    88.836735  
    104.24398  
    120.95249  
    138.96227  
    158.27332  
    178.88563  
    200.79922  
    224.01407  
    248.53018  
];
assert_checkalmostequal(pop_fit,pop_fitexpected,1.e-5);
