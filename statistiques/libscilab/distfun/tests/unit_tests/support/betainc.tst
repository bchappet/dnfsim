// Copyright (C) 2014 - Michael Baudin
//
// This file must be used under the terms of the GNU Lesser General Public License license :
// http://www.gnu.org/copyleft/lesser.html


// <-- JVM NOT MANDATORY -->
// <-- ENGLISH IMPOSED -->

computed=distfun_betainc(.5,(0:10)',3);
expected=[
   1.00000000000000
   0.87500000000000
   0.68750000000000
   0.50000000000000
   0.34375000000000
   0.22656250000000
   0.14453125000000
   0.08984375000000
   0.05468750000000
   0.03271484375000
   0.01928710937500
];
assert_checkalmostequal ( computed , expected );