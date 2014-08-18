// Copyright (C) 2013 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt


x = linspace(0,%pi,10)';
y = sin(x);
p = polyfit(x,y,3);
expected=[
   2.111148559743582870D-17  
  -4.038880514217270679D-01  
   1.268851735219194143D+00  
  -2.342223135071501081D-02  
]';
assert_checkalmostequal(p,expected);
//
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
// Evaluate the fit
pop_fit = polyval(p,cdate,S);
assert_checkalmostequal(pop_fit,pop,[],10);
