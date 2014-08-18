// Copyright (C) 2012-2013 - Michael Baudin
// Copyright (C) 2012 - Maria Christopoulou 
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

// <-- JVM NOT MANDATORY -->


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

Bexpected = [
-3482258.63459582
15.0618722713733
-0.358191792925910E-01
-2.02022980381683
-1.03322686717359
-0.511041056535807E-01
1829.15146461355
];
X = [ones(size(X,"r"),1) X];
[B,bint,r,rint,stats,fullstats] = regres(Y,X);

assert_checkalmostequal(B,Bexpected,[],[],"element");
assert_checkalmostequal(fullstats.ResidualSS,836424.055505915,1.e-12);
assert_checkalmostequal(fullstats.ResidualMean,92936.0061673238,1.e-12);
assert_checkalmostequal(fullstats.R2,0.995479004577296,1.e-12);
assert_checkalmostequal(fullstats.F,330.285339234588,1.e-11);

Bstddev = [
 890420.383607373
 84.9149257747669
  0.334910077722432E-01
 0.488399681651699
 0.214274163161675
  0.226073200069370
 455.478499142212
];
assert_checkalmostequal(fullstats.Bstddev,Bstddev,1.e-11,[],"element");
// From Octave
bintExpected = [
  -5.49652952410773e+006  -1.46798774508794e+006
  -1.77029035472034e+002  2.07152780015145e+002
  -1.11581103448723e-001  3.99427448634332e-002
  -3.12506665777324e+000  -9.15392949861547e-001
  -1.51794870314301e+000  -5.48505031204308e-001
  -5.62517216321774e-001  4.60309005014385e-001
  7.98787494414885e+002  2.85951543481429e+003
];
assert_checkalmostequal(bint,bintExpected,1.e-7,[],"element");
// From Octave
r = [
   267.3400297742810
   -94.0139424102454
    46.2871677852954
  -410.1146218869235
   309.7145908192254
  -249.3112153014389
  -164.0489563733436
   -13.1803568473570
    14.3047726139057
   455.3940946034481
   -17.2689271119352
   -39.0550424934590
  -155.5499736160615
   -85.6713080029167
   341.9315139497885
  -206.7578251754712
];
assert_checkalmostequal(bint,bintExpected,1.e-7,[],"element");
// From Octave
pval = 4.98403096571565e-010;
assert_checkalmostequal(fullstats.pval,pval,1.e-7);
// From Octave:
rintExpected = [
  -244.6904259157840   779.3704854643460
  -570.5619235811749   382.5340387606842
  -536.7580890136342   629.3324245842250
  -887.9131822669657    67.6839384931187
   -70.2273505111387   689.6565321495893
  -794.7841638236241   296.1617332207463
  -668.8593450087005   340.7614322620133
  -527.8793936365845   501.5186799418706
  -524.5183995528589   553.1279447806703
   -19.4607653506776   930.2489545575738
  -602.3286761383787   567.7908219145082
  -564.0035361036099   485.8934511166918
  -720.6070519869496   409.5071047548266
  -724.5371104370828   553.1944944312494
  -168.7032503675583   852.5662782671352
  -579.9292931086839   166.4136427577415
];
assert_checkalmostequal(rint,rintExpected,1.e-7,[],"element");
//
stats_expected = [
0.995479004577296   
330.285339234588   
4.98403096571565e-010   
92936.0061673238
];
assert_checkalmostequal(stats,stats_expected,1.e-7,[],"element");

// "Introduction to probability and statistics for engineers and scientists.", 
// Third Edition, Sheldon Ross, Elsevier Academic Press, 2004
// Example 9.10a
// X1 : Population in Thousands
// X2 : Divorce Rate per 100,000
// X : [X1 X2]
// Y : Suicide Rate per 100,000
X = [
679     30.4 
1420   34.1 
1349   17.2 
296     26.8 
6975   29.1 
323     18.7 
4200   32.6 
633     32.5 
];

Y = [
11.6
16.1
9.3
9.1
8.4
7.7
11.3
8.4
];

B_expected = [
  3.50735335946465
  -2.47709904245734e-4
  2.60946557606973e-1
];

bint_expected = [
  -7.69538991001717  1.47100966289465e1
  -1.35033441754429e-3  8.54914609052820e-4
  -1.47391682627427e-1  6.69284797841373e-1
];

r_expected = [
   0.328066314266224
   4.046117090166510
   1.638526510522911
  -1.327398971674792
  -0.973121603713572
  -0.607043687643677
   0.326170460380111
  -3.431316112303725
];

rint_expected = [
  -6.303957321158832   6.960089949691280
   0.560936547000659   7.531297633332360
  -3.190186334174643   6.467239355220465
  -7.834598404586989   5.179800461237406
  -4.368329563530528   2.422086356103384
  -6.265283800667740   5.051196425380386
  -5.979919938074893   6.632260858835114
  -7.944536914582465   1.081904689975015
];

X = [ones(size(X,"r"),1) X];
[B_computed,bint_computed,r_computed,rint_computed,stats,fullstats] = regres(Y,X);

rtol = 1.e-13;
assert_checkalmostequal(B_expected,B_computed,rtol,[],"element");
assert_checkalmostequal(bint_expected,bint_computed,rtol,[],"element");
assert_checkalmostequal(r_expected,r_computed,rtol,[],"element");
assert_checkalmostequal(rint_expected,rint_computed,rtol,[],"element");
assert_checkalmostequal(fullstats.R2,0.352768544480185,rtol);
assert_checkalmostequal(fullstats.pval,0.337014851500115,rtol);
assert_checkalmostequal(fullstats.F,1.362605840119684,rtol);
assert_checkalmostequal(fullstats.ResidualMean,6.824246659137049,rtol);
// Example 9.10b
assert_checkalmostequal(fullstats.ResidualSS,34.12,1.e-4);
stats_expected = [
0.352768544480185   
1.362605840119684   
0.337014851500115   
6.824246659137049
];
assert_checkalmostequal(stats,stats_expected,rtol,[],"element");
//
// Source :
// http://www.itl.nist.gov/div898/strd/lls/data/LINKS/DATA/Norris.dat
// Norris.dat contains 1 response variable y, 1 predictor variable  and 36 observations.
// The model used is Y = B(1) + B(2)*X.
X = [
    0.2    
    337.4  
    118.2  
    884.6  
    10.1   
    226.5  
    666.3  
    996.3  
    448.6  
    777.   
    558.2  
    0.4    
    0.6    
    775.5  
    666.9  
    338.   
    447.5  
    11.6   
    556.   
    228.1  
    995.8  
    887.6  
    120.2  
    0.3    
    0.3    
    556.8  
    339.1  
    887.2  
    999.   
    779.   
    11.1   
    118.3  
    229.2  
    669.1  
    448.9  
    0.5    
];

Y = [
    0.1    
    338.8  
    118.1  
    888.   
    9.2    
    228.1  
    668.5  
    998.5  
    449.1  
    778.9  
    559.2  
    0.3    
    0.1    
    778.1  
    668.8  
    339.3  
    448.9  
    10.8   
    557.7  
    228.3  
    998.   
    888.8  
    119.6  
    0.3    
    0.6    
    557.6  
    339.3  
    888.   
    998.5  
    778.9  
    10.2   
    117.6  
    228.9  
    668.4  
    449.2  
    0.2    
];
expected = [
-0.262323073774029
1.00211681802045
];
B = regres(Y,[ones(X),X]);
assert_checkalmostequal(B,expected,[],[],"element");
// Check more data
[B,bint,r,rint,stats,fullstats] = regres(Y,[ones(X),X]);
Bintexpected = [
  -0.7354667    0.2108205  
   1.0012434    1.0029903  
];
assert_checkalmostequal(bint,Bintexpected,1.e-6,[],"element");
Rexpected = [
   0.1618997  
   0.9481087  
  -0.0878848  
   1.7897859  
  -0.6590568  
   1.3828638  
   1.0518872  
   0.3533373  
  -0.1872815  
   0.5175555  
   0.0807153  
   0.1614763  
  -0.2389470  
   1.2207307  
   0.7506171  
   0.8468386  
   0.7150470  
  -0.5622320  
   0.7853723  
  -0.0205231  
   0.3543957  
  -0.4165646  
  -0.5921185  
   0.2616880  
   0.5616880  
  -0.1163212  
  -0.2554899  
  -0.8157179  
  -2.3523781  
  -1.4866782  
  -0.6611736  
  -0.6880965  
  -0.5228516  
  -1.8540399  
  -0.3879165  
  -0.0387353  
];
assert_checkalmostequal(r,Rexpected,1.e-6,[],"element");
assert_checkalmostequal(fullstats.R2,0.999993745883712,1.e-15);
assert_checkalmostequal(fullstats.ResidualSS,26.6173985294224,1.e-13);
assert_checkalmostequal(fullstats.ResidualMean,0.782864662630069,1.e-13);

//
// "Introduction to probability and statistics for engineers 
// and scientists.", 
// Third Edition, Sheldon Ross, Elsevier Academic Press, 2004
// Example 9.4a
// X : Speed
// Y : Miles per Gallon

X = [
45
50
55
60
65
70
75
];
Y = [
24.2
25.0
23.0
22.0
21.5
20.6
19.8
];
[B,bint] = regres(Y,[ones(X),X]);
Bintexpected = [
   29.094968   35.647889  
  -0.2217218  -0.1139925  
];
Bexpected = [
   32.371429  
  -0.1678571  
];
assert_checkalmostequal(B,Bexpected,1.e-6,[],"element");
assert_checkalmostequal(bint,Bintexpected,1.e-6,[],"element");
if (%f) then
scf();
plot(X,Y,"bo")
xtitle("","Speed","Miles per Gallon")
end

// http://en.wikipedia.org/wiki/Simple_linear_regression
// "Okun's law in macroeconomics is an example of the simple 
// linear regression. Here the dependent variable (GDP growth) 
// is presumed to be in a linear relationship with the changes 
// in the unemployment rate."
gdpch = [
   1.5671  
   0.4097  
   0.1913  
  -1.4     
  -0.2775  
   1.1326  
  -1.0303  
   4.1384  
   2.9991  
   3.8703  
   1.7934  
   1.1175  
   1.7028  
   2.0014  
   0.1902  
   1.161   
   0.0804  
   0.6323  
   3.312   
   1.8452  
   0.8198  
  -0.6375  
  -1.6014  
  -0.498   
   0.1496  
   1.0819  
   1.9827  
   2.8587  
   1.5986  
   1.3343  
   0.5286  
  -0.4272  
   0.7874  
  -0.1123  
   1.6438  
   0.5667  
  -0.2291  
   0.9735  
  -1.0414  
  -2.693   
   0.5809  
   2.2444  
   2.2503  
   2.0885  
   2.6133  
  -0.0429  
   0.3303  
   2.2188  
  -0.4935  
   0.1849  
  -1.288   
   0.578   
   1.872   
   1.6426  
   2.0405  
   1.7437  
   1.081   
   0.9839  
   0.2503  
   1.1831  
   1.3022  
   1.9188  
   0.728   
   2.2304  
   1.1891  
   1.348   
   0.2611  
   2.4518  
   1.3491  
   2.0335  
   2.3896  
   2.4811  
   0.378   
   0.6349  
   0.8721  
   0.9197  
  -0.0638  
   0.7235  
   0.7485  
   2.034   
   1.7204  
   0.7042  
   0.427   
   1.5468  
   0.2614  
   0.5802  
  -0.471   
  -0.1372  
   0.2075  
   0.8758  
  -1.0734  
   2.7927  
   0.5483  
   0.7677  
   0.2504  
   1.963   
   2.2887  
   0.9913  
   1.7388  
   2.5511  
   1.0019  
  -0.3944  
   0.8283  
  -0.7661  
   0.2695  
  -1.1113  
  -0.5509  
  -1.2802  
   0.8778  
   1.7354  
   1.2587  
   2.3733  
   0.8298  
   0.4743  
   0.8214  
   1.2186  
   1.798   
   1.7996  
   0.1203  
   0.2797  
   3.8528  
   0.9652  
   1.3363  
   0.2523  
   0.0634  
   0.7115  
   0.3329  
   0.3298  
  -2.0388  
  -0.1544  
   1.7793  
   1.9426  
  -0.6995  
   1.1906  
  -1.1806  
  -1.657   
   0.4314  
  -0.4741  
   0.0713  
   1.1555  
   2.361   
   1.78    
   2.0539  
   2.1847  
   1.6937  
   0.8646  
   0.7543  
   0.8347  
   0.7833  
   1.4946  
   0.8231  
   0.9111  
   0.4165  
   0.9281  
   0.5322  
   0.7337  
   1.0626  
   0.8376  
   1.7346  
   0.6657  
   1.1775  
   0.5118  
   1.2989  
   1.2157  
   0.544   
   0.4742  
   0.3464  
   1.2482  
   0.2293  
  -0.1827  
  -0.8215  
  -0.4922  
   0.5595  
   0.2459  
   0.5385  
   0.9284  
   0.9361  
   0.7726  
   1.3174  
  -0.0272  
   0.6081  
   0.438   
   1.5109  
   0.8467  
   1.3943  
   0.5457  
   1.2333  
   0.3699  
   0.195   
   0.7743  
   0.8001  
   0.715   
   1.6479  
   0.4998  
   1.14    
   1.073   
   1.4408  
   1.0416  
   0.6852  
   1.4916  
   0.555   
   1.0139  
   1.6345  
   0.7534  
   0.4844  
   1.2694  
   1.7239  
   0.6338  
   1.1905  
   0.1412  
   0.2723  
  -0.1504  
  -0.3987  
  -0.0729  
   0.6793  
   1.2369  
   0.3119  
   0.9923  
   0.3437  
];
unempch = [
  -0.0666667  
   0.1        
   0.0666667  
   0.8333333  
   1.2        
   0.8333333  
   0.2666667  
  -0.5666667  
  -0.8333333  
  -0.9333333  
  -0.4        
  -0.7333333  
  -0.4        
   0.0666667  
   0.2        
  -0.3        
  -0.1        
   0.2666667  
  -0.4        
  -0.1333333  
  -0.1333333  
   0.1666667  
   0.9666667  
   1.5666667  
   0.5333333  
   0.1666667  
  -0.6333333  
  -0.6        
  -0.3333333  
  -0.3        
   0.1333333  
  -0.2        
   0.1666667  
  -0.0666667  
   0.         
  -0.2        
   0.1666667  
   0.1333333  
   0.7        
   1.3666667  
   1.0666667  
  -0.0333333  
  -0.9666667  
  -0.5333333  
  -0.7333333  
   0.1666667  
   0.3333333  
  -0.4666667  
   0.1        
   0.3        
   0.7333333  
   0.5333333  
   0.2        
  -0.2333333  
  -0.5666667  
  -0.5666667  
  -0.1        
   0.0333333  
  -0.0333333  
   0.2333333  
  -0.0333333  
  -0.2333333  
   0.0666667  
  -0.1        
  -0.2666667  
  -0.2        
  -0.0333333  
  -0.0666667  
  -0.2333333  
  -0.3        
  -0.2666667  
  -0.2333333  
  -0.0333333  
  -0.0666667  
  -0.0666667  
   0.1333333  
   0.         
  -0.0333333  
   0.1        
  -0.1666667  
  -0.1666667  
  -0.0333333  
  -0.1333333  
   0.         
   0.0333333  
   0.1333333  
   0.         
   0.6        
   0.6        
   0.4        
   0.6666667  
   0.1        
  -0.0333333  
   0.1333333  
  -0.1        
  -0.1666667  
  -0.0666667  
  -0.1333333  
  -0.2        
  -0.4333333  
   0.         
  -0.1333333  
  -0.0333333  
   0.3666667  
   0.0666667  
   0.4333333  
   0.9666667  
   1.6666667  
   0.6        
  -0.4        
  -0.1666667  
  -0.5666667  
  -0.1666667  
   0.1666667  
   0.0333333  
  -0.2666667  
  -0.3666667  
  -0.2333333  
  -0.2333333  
  -0.3333333  
  -0.3333333  
   0.0333333  
  -0.1333333  
  -0.0333333  
  -0.1666667  
   0.1666667  
   0.1        
   0.3333333  
   1.0333333  
   0.3333333  
  -0.2666667  
   0.0333333  
  -0.0333333  
   0.         
   0.8333333  
   0.6        
   0.6        
   0.4666667  
   0.7666667  
  -0.3        
  -0.2333333  
  -0.7666667  
  -0.8333333  
  -0.6666667  
  -0.4333333  
   0.         
  -0.1333333  
  -0.0666667  
   0.0666667  
  -0.1        
  -0.1666667  
   0.         
   0.1333333  
  -0.2        
  -0.1333333  
  -0.2333333  
  -0.3333333  
  -0.2666667  
  -0.1666667  
  -0.1333333  
  -0.2333333  
   0.         
  -0.1333333  
  -0.1333333  
   0.0333333  
   0.         
   0.1333333  
  -0.0666667  
   0.0333333  
   0.3666667  
   0.4333333  
   0.4666667  
   0.2333333  
   0.0333333  
   0.2333333  
   0.2666667  
   0.2333333  
   0.0333333  
  -0.2666667  
  -0.2333333  
  -0.0666667  
  -0.2666667  
  -0.1666667  
  -0.0666667  
  -0.3666667  
  -0.2        
  -0.3666667  
  -0.1666667  
   0.2        
   0.         
  -0.1        
  -0.0333333  
  -0.0333333  
  -0.2333333  
   0.0666667  
  -0.1        
  -0.2333333  
  -0.1333333  
  -0.2        
  -0.0333333  
  -0.2333333  
   0.1333333  
  -0.1        
  -0.1333333  
  -0.0333333  
  -0.0333333  
  -0.1666667  
  -0.0333333  
  -0.0666667  
   0.1        
  -0.1333333  
   0.2333333  
   0.3        
   0.3666667  
   0.7666667  
   0.0333333  
   0.2        
  -0.0666667  
   0.1333333
	];
if (%f) then
scf();
plot(unempch,gdpch,"bo")
xtitle("","Quaterly change in the unemployment rate (D%)",..
"Quaterly change in GDP (D%)")
end
[B,bint] = regres(gdpch,[ones(unempch),unempch]);
Bexpected = [
0.859
-1.817
];
Bintexpected = [
0.76 0.96
-2.06 -1.58
];
assert_checkalmostequal(B,Bexpected,[],1.e-3,"element");
assert_checkalmostequal(bint,Bintexpected,[],1.e-2,"element");
//
// http://en.wikipedia.org/wiki/Simple_linear_regression
Height = [1.47	1.50	1.52	1.55	1.57	1.60	1.63	1.65	1.68	1.70	1.73	1.75	1.78	1.80	1.83]';
Mass =[52.21	53.12	54.48	55.84	57.20	58.57	59.93	61.29	63.11	64.47	66.28	68.10	69.92	72.19	74.46]';
[B,bint] = regres(Mass,[ones(Height),Height]);
Bexpected = [
-39.062
61.272
];
Bintexpected = [
-45.4 -32.7
57.4 65.1
];
assert_checkalmostequal(B,Bexpected,[],1.e-2,"element");
assert_checkalmostequal(bint,Bintexpected,[],1.e-1,"element");
//
//
// "Introduction to probability and statistics for engineers and scientists.", 
// Third Edition, Sheldon Ross, Elsevier Academic Press, 2004
// Example 9.2a
// X : Relative Humidity
// Y : Moisture Content
X = [46 53 29 61 36 39 47 49 52 38 55 32 57 54 44]';
Y = [12 15 7 17 10 11 11 12 14 9 16 8 18 14 12]';
[B,bint] = regres(Y,[ones(X),X]);
Bexpected = [
-2.51
-0.32
];
Bintexpected = [
-6.240 1.238
0.263 0.383
];
[flag,errormsg]=assert_checkalmostequal(B,Bexpected,1.e-2,[],"element");
[flag,errormsg]=assert_checkalmostequal(bint,Bintexpected,1.e-2,[],"element");
