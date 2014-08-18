# Copyright (C) 2010 - DIGITEO - Michael Baudin
#
# This file must be used under the terms of the CeCILL.
# This source file is licensed as described in the file COPYING, which
# you should have received as part of this distribution.  The terms
# are also available at
# http:#www.cecill.info/licences/Licence_CeCILL_V2-en.txt

# Test the Poisson law in the R language
options(digits=20)
#
# Test the PDF
#
dpois ( 1e+03 , 1e+03 )
dpois ( 1e+05 , 1e+05 )
dpois ( 1e+07 , 1e+07 )
dpois ( 1e+09 , 1e+09 )
dpois ( 0 , 200 )
dpois ( 103 , 200 )
dpois ( 104 , 200 )
dpois ( 133 , 200 )
dpois ( 134 , 200 )
dpois ( 200 , 200 )
dpois ( 314 , 200 )
dpois ( 315 , 200 )
dpois ( 400 , 200 )
dpois ( 900 , 200 )
#
# Test the CDF - P
#
ppois ( 1e+03 , 1e+03 )
ppois ( 1e+05 , 1e+05 )
ppois ( 1e+07 , 1e+07 )
ppois ( 1e+09 , 1e+09 )
ppois ( 0 , 200 )
ppois ( 103 , 200 )
ppois ( 104 , 200 )
ppois ( 133 , 200 )
ppois ( 134 , 200 )
ppois ( 200 , 200 )
ppois ( 314 , 200 )
ppois ( 315 , 200 )
ppois ( 400 , 200 )
ppois ( 900 , 200 )
#
# Test the CDF - Q
#
ppois ( 1e+03 , 1e+03 , lower.tail = FALSE )
ppois ( 1e+05 , 1e+05 , lower.tail = FALSE )
ppois ( 1e+07 , 1e+07 , lower.tail = FALSE )
ppois ( 1e+09 , 1e+09 , lower.tail = FALSE )
ppois ( 0 , 200 , lower.tail = FALSE )
ppois ( 103 , 200 , lower.tail = FALSE )
ppois ( 104 , 200 , lower.tail = FALSE )
ppois ( 133 , 200 , lower.tail = FALSE )
ppois ( 134 , 200 , lower.tail = FALSE )
ppois ( 200 , 200 , lower.tail = FALSE )
ppois ( 314 , 200 , lower.tail = FALSE )
ppois ( 315 , 200 , lower.tail = FALSE )
ppois ( 400 , 200 , lower.tail = FALSE )
ppois ( 900 , 200 , lower.tail = FALSE )

#
# Test the Inverse CDF - X
#
qpois ( 1e+03 , 1e+03 )
qpois ( 1e+05 , 1e+05 )
qpois ( 1e+07 , 1e+07 )
qpois ( 1e+09 , 1e+09 )
qpois ( 0 , 200 )
qpois ( 103 , 200 )
qpois ( 104 , 200 )
qpois ( 133 , 200 )
qpois ( 134 , 200 )
qpois ( 200 , 200 )
qpois ( 314 , 200 )
qpois ( 315 , 200 )
qpois ( 400 , 200 )
qpois ( 900 , 200 )

# Testing with R
# [x lambda PDF-P CDF-P CDF-Q]
x      <- c(1e+03,1e+05,1e+07,1e+09,0.0,50.,80.,103,104,133,134,200,250,280,314,315,400,600,900,1000)
lambda <- c(1e+03,1e+05,1e+07,1e+09,200,200,200,200,200,200,200,200,200,200,200,200,200,200,200,200.)
row <- c(0,0,0,0,0)
for (i in 1:length(x)) {
  row[1] = x[i]
  row[2] = lambda[i]
  row[3] = dpois ( x[i], lambda[i] )
  row[4] = ppois ( x[i], lambda[i] )
  row[5] = ppois ( x[i], lambda[i] , lower.tail = FALSE )
  print(row,digits=17)
}


# Inverse CDF
p <- c(1,0.999999999999999999,0.99999999999999,0.9999999999,0.9999999,0.99999,0.9999,0.999,0.99,0.9,0.8,0.6,0.5,0.2,0.1,1.e-2,1.e-5,1.e-10,1.e-20,1.e-50,1.e-100,1.e-200,1.e-300,0);
# Reverse p to obtain q values
np <- length(p)
q <- p;
for (i in 1:np) {
q[i] = p[np-i+1]
}
lambda = 200;
row <- c(0,0,0,0,0)
for (i in 1:length(x)) {
  row[1] = p[i]
  row[2] = q[i]
  row[3] = lambda
  row[4] = qpois ( p[i], lambda )
  row[5] = qpois ( q[i], lambda , lower.tail = FALSE )
  print(row,digits=17)
}

