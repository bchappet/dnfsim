// Copyright (C) 2013 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

// Check that z is a resample of x
function checkResample(x,z)
    x=x(:)'
    z=z(:)'
    for i=z
        j=find(i==x);
        assert_checktrue(j<>[])
    end
endfunction

// With x column vector
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
];
z=rboot(x);
assert_checkequal(size(z),[9 1]);
checkResample(x,z);
// Check that z is a resample of x
// Get 3 resamples
z=rboot(x,3);
assert_checkequal(size(z),[9 3]);
checkResample(x,z(:,1));
checkResample(x,z(:,2));
checkResample(x,z(:,3));

// With a x matrix
x=[
    4.9952216    1.7230967    4.3599497
    0.1172268    0.4631876    4.5983874
    2.5650301    5.9620251    0.6417484
    4.2733101    1.5968826    7.9834905
    0.9030809    6.0324748    6.6156088
    4.5827578    1.1672855    5.8286847
    0.8584001    0.9651479    5.5432723
    4.4818554    7.1413554    2.0530236
    4.0622808    0.9629044    0.9365593
    1.4487633    4.0120296    9.8248865
    6.1039767    4.4227663    1.0621471
    0.1705638    1.8480077    0.1973479
];
z=rboot(x);
assert_checkequal(size(z),[12 3]);
checkResample(x,z);
// Get 3 resamples
z=rboot(x,3);
assert_checkequal(size(z),[12 9]);
checkResample(x,z);