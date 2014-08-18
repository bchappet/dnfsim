// Copyright (C) 2012 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

// Test the F law in the R language
options(digits=20)

# [x mu sigma PDF-P CDF-P CDF-Q]
# Inverse CDF to obtain x values
p <- c(1,0.999999999999999999,0.99999999999999,0.9999999999,0.9999999,0.99999,0.9999,0.999,0.99,0.9,0.8,0.6,0.5,0.2,0.1,1.e-2,1.e-5,1.e-10,1.e-20,1.e-50,1.e-100,1.e-200,1.e-300,0);
# Reverse p to obtain q values
np <- length(p)
q <- p;
for (i in 1:np) {
    q[i] = p[np-i+1]
}
v=5;
ncp=0;
row <- c(0,0,0,0,0)
for (i in 1:length(p)) {
  x = qt(p[i], v,ncp)
  row[1] = x
  row[2] = v
  row[3] = dt ( x, v ,ncp)
  row[4] = pt ( x, v ,ncp)
  row[5] = pt ( x, v ,ncp, lower.tail = FALSE )
  print(row,digits=17)
  x = qt(q[i], v,ncp)
  row[1] = x
  row[2] = v
  row[3] = dt ( x, v ,ncp)
  row[4] = pt ( x, v ,ncp)
  row[5] = pt ( x, v ,ncp, lower.tail = FALSE )
  print(row,digits=17)
}
