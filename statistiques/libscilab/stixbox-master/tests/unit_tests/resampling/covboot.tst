// Copyright (C) 2013 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

// Case where the parameter to estimate is univariate:
// estimate a mean
x=[
    2.6393329  
    0.2261208  
    3.4090173  
    2.3128478  
    1.2304635  
    1.8089447  
    1.006141   
    1.2068124  
    2.5974333  
    8.182333   
    2.5544356  
    6.5075578  
    3.3702258  
    0.5537286  
    5.7919164  
    0.8986735  
    4.1781705  
    1.7169013  
    2.3771684  
    2.5330788  
];
c=covboot(x,mean);
c_expected=0.2466366;
assert_checkalmostequal(c,c_expected,[],0.1);
//
c=covboot(x,mean,500);
assert_checkalmostequal(c,c_expected,[],0.1);
//
[c,y]=covboot(x,mean);
assert_checkalmostequal(c,c_expected,[],0.1);
assert_checkequal(size(y),[1 200]);
// Test with a user-defined function.
function y=mymean(x)
    y=mean(x)
endfunction
c=covboot(x,mymean);
assert_checkalmostequal(c,c_expected,[],0.1);

// Case where the parameter to estimate is univariate:
// estimate a standard deviation
x=[
    2.6393329  
    0.2261208  
    3.4090173  
    2.3128478  
    1.2304635  
    1.8089447  
    1.006141   
    1.2068124  
    2.5974333  
    8.182333   
    2.5544356  
    6.5075578  
    3.3702258  
    0.5537286  
    5.7919164  
    0.8986735  
    4.1781705  
    1.7169013  
    2.3771684  
    2.5330788  
];
c=covboot(x,stdev);
c_expected=0.1554711;
assert_checkalmostequal(c,c_expected,[],5.e-2);
//
c=covboot(x,st_deviation);
assert_checkalmostequal(c,c_expected,[],5.e-2);

// Case where the parameter to estimate is multivariate:
// estimate a covariance
x = [
    3.1235574   1.3369432  
    0.7638813  -1.2221378  
    5.7365731  -0.9302936  
    0.5671322   0.8232697  
    9.1387573  -0.9258215  
    1.3842285   1.5298908  
    3.2133509  -1.0480822  
    2.1778416  -1.1749665  
    3.0751019  -0.6775047  
    3.3200015   0.5166559  
    3.6030218  -1.3445073  
    1.2318911   2.1079284  
    1.5779095   1.3972455  
    1.4751712  -0.3981207  
    1.6657888   1.0903741  
    9.006646    1.1983465  
    0.9351157   0.2731937  
    2.7389618   0.1574327  
    1.2000214   1.0212761  
    1.3329473   0.2564064  
];
c=covboot(x,cov);
c_expected=[
   6.5387931  -0.0160807  -0.0160807   0.0063107  
  -0.0160807   1.4127126   1.4127126  -0.4612902  
  -0.0160807   1.4127126   1.4127126  -0.4612902  
   0.0063107  -0.4612902  -0.4612902   7.8574787  
];
assert_checkalmostequal(c,c_expected,[],2.);
//
[c,y]=covboot(x,cov);
assert_checkalmostequal(c,c_expected,[],2.);
assert_checkequal(size(y),[4 200]);


// With extra-arguments for T.
x=[
    4.9952216    1.7230967    4.3599497    0.5664832    9.5779003  
    0.1172268    0.4631876    4.5983874    0.2744860    16.489273  
    2.5650301    5.9620251    0.6417484    1.8421542    0.5960831  
    4.2733101    1.5968826    7.9834905    0.5650942    0.9296631  
    0.9030809    6.0324748    6.6156088    0.7178707    4.7200918  
    4.5827578    1.1672855    5.8286847    2.4550336    3.9556549  
    0.8584001    0.9651479    5.5432723    1.3144472    4.5939304  
    4.4818554    7.1413554    2.0530236    1.2242408    9.1853402  
    4.0622808    0.9629044    0.9365593    3.6160683    5.1135541  
    1.4487633    4.0120296    9.8248865    5.4600839    2.3082734  
    6.1039767    4.4227663    1.0621471    2.2473891    7.4958383  
    0.1705638    1.8480077    0.1973479    0.9026908    0.7151402  
    0.7148210    2.7977726    6.8339945    1.630347     1.22196    
    0.7241723    2.3708646    1.9023413    2.1139935    0.3424373  
    7.6981779    5.6876688    7.0508992    0.4679860    3.8176482  
    1.9179274    6.830938     0.1318826    0.4950052    2.3847905  
    6.0137804    0.9105037    11.30042     3.1600421    6.2141152  
    5.5116843    0.2011673    4.3398571    0.9198861    8.7549043  
    4.1329368    2.4652051    0.4546495    2.6837028    3.0702837  
    4.2404543    7.3614213    2.8886355    4.3700878    0.7875088  
];
c=covboot(x,list(mean,"r"));
c_expected=[
   0.4956135   0.0175032  -0.0276027    0.0111202   0.0331390  
   0.0175032   0.4475945  -0.0250677    0.0344942   0.0031623  
  -0.0276027  -0.0250677   0.4552656    0.0405483  -0.0083690  
   0.0111202   0.0344942   0.0405483    0.4445590   0.0294593  
   0.0331390   0.0031623  -0.0083690    0.0294593   0.3455781  
];
assert_checkalmostequal(c,c_expected,[],0.2);
