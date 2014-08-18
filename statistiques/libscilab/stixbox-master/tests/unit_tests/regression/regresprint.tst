// Copyright (C) 2012-2013 - Michael Baudin
// Copyright (C) 2012 - Maria Christopoulou 
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

// <-- JVM NOT MANDATORY -->
// <-- NO CHECK REF -->

// We use dataset provided by NIST in
// http://www.itl.nist.gov/div898/strd/lls/data/LINKS/DATA/Longley.dat
// Longley.dat contains 1 Response Variable y, 6 Predictor Variables x 
// and 16 Observations.
// The model used is Y = B0 + B1*x1 + B2*x2 +...Bn*xn

// Known Inputs and Outputs

X = [
83.0 234289 2356 1590 107608 1947
88.5 259426 2325 1456 108632 1948
88.2 258054 3682 1616 109773 1949
89.5 284599 3351 1650 110929 1950
96.2 328975 2099 3099 112075 1951
98.1 346999 1932 3594 113270 1952
99.0 365385 1870 3547 115094 1953
100.0 363112 3578 3350 116219 1954
101.2 397469 2904 3048 117388 1955
104.6 419180 2822 2857 118734 1956
108.4 442769 2936 2798 120445 1957
110.8 444546 4681 2637 121950 1958
112.6 482704 3813 2552 123366 1959
114.2 502601 3931 2514 125368 1960
115.7 518173 4806 2572 127852 1961
116.9 554894 4007 2827 130081 1962
];

Y = [
60323
61122
60171
61187
63221
63639
64989
63761
66019
67857
68169
66513
68655
69564
69331
70551
];

[B,bint,r,rint,stats,fullstats] = regres(Y,[ones(Y),X]);
regresprint(fullstats);
