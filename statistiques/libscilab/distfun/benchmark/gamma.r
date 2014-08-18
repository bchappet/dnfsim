# Copyright (C) 2012 - Michael Baudin
# Copyright (C) 2010 - DIGITEO - Michael Baudin
#
# This file must be used under the terms of the CeCILL.
# This source file is licensed as described in the file COPYING, which
# you should have received as part of this distribution.  The terms
# are also available at
# http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt
#
# Test the Gamma law in the R language
# Default is the rate, this is why we specify the scale.
options(digits=20)
#
# Test the PDF
#
dgamma ( 0.1 , 0.1 , scale=1 )
dgamma ( 0.2 , 0.1 , scale=1 )
dgamma ( 0.2 , 0.2 , scale=1 )
dgamma ( 0.3 , 0.2 , scale=1 )
dgamma ( 0.3 , 0.3 , scale=1 )
dgamma ( 0.4 , 0.3 , scale=1 )
dgamma ( 0.4 , 0.4 , scale=1 )
dgamma ( 0.5 , 0.4 , scale=1 )
dgamma ( 0.5 , 0.5 , scale=1 )
dgamma ( 0.6 , 0.5 , scale=1 )
#
# Test the CDF
#
pgamma ( 0.1 , 0.1 , scale=1 )
pgamma ( 0.2 , 0.1 , scale=1 )
pgamma ( 0.2 , 0.2 , scale=1 )
pgamma ( 0.3 , 0.2 , scale=1 )
pgamma ( 0.3 , 0.3 , scale=1 )
pgamma ( 0.4 , 0.3 , scale=1 )
pgamma ( 0.4 , 0.4 , scale=1 )
pgamma ( 0.5 , 0.4 , scale=1 )
pgamma ( 0.5 , 0.5 , scale=1 )
pgamma ( 0.6 , 0.5 , scale=1 )
# 
#  Test the Inverse CDF
#  This is completely wrong : should set p values instead!!!
#
qgamma ( 0.1 , 0.1 , scale=1 )
qgamma ( 0.2 , 0.1 , scale=1 )
qgamma ( 0.2 , 0.2 , scale=1 )
qgamma ( 0.3 , 0.2 , scale=1 )
qgamma ( 0.3 , 0.3 , scale=1 )
qgamma ( 0.4 , 0.3 , scale=1 )
qgamma ( 0.4 , 0.4 , scale=1 )
qgamma ( 0.5 , 0.4 , scale=1 )
qgamma ( 0.5 , 0.5 , scale=1 )
qgamma ( 0.6 , 0.5 , scale=1 )


# Testing with R
# [x shape scale PDF-P CDF-P CDF-Q]
x     <- c(0.1,0.2,0.2,0.3,0.3,0.4,0.4,0.5,0.5,0.6,0.5,0.5,0.5, 1.0,2.0,4.0,10.0,20.0,40.0,1.e2,3.e2,5.e2,1.e3, 1.e-2,1.e-4,1.e-8,1.e-20,1.e-40,1.e-100,1.e-200,1.e-300,0.0)
shape <- c(0.1,0.1,0.2,0.2,0.3,0.3,0.4,0.4,0.5,0.5,0.5,0.5,0.5, 0.5,0.5,0.5,0.50,0.50,0.50,0.50,0.50,0.50,0.50, 0.500,0.500,0.500,0.5000,0.5000,0.50000,0.50000,0.50000,0.5)
scale <- c(1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,2.0,3.0,4.0, 1.0,1.0,1.0,1.00,1.00,1.00,1.00,1.00,1.00,1.00, 1.000,1.000,1.000,1.0000,1.0000,1.00000,1.00000,1.00000,1.0)
row <- c(0,0,0,0,0,0)
for (i in 1:length(x)) {
  row[1] = x[i]
  row[2] = shape[i]
  row[3] = scale[i]
  row[4] = dgamma ( x[i], shape[i] , scale=scale[i] )
  row[5] = pgamma ( x[i], shape[i] , scale=scale[i] )
  row[6] = pgamma ( x[i], shape[i] , scale=scale[i] , lower.tail = FALSE )
  print(row,digits=17)
}

# [x a b PDF-P CDF-P CDF-Q]
# Inverse CDF to obtain x values
p <- c(1,0.999999999999999999,0.99999999999999,0.9999999999,0.9999999,0.99999,0.9999,0.999,0.99,0.9,0.8,0.6,0.5,0.2,0.1,1.e-2,1.e-5,1.e-10,1.e-20,1.e-50,1.e-100,1.e-200,1.e-300,0);
# Reverse p to obtain q values
np <- length(p)
q <- p;
for (i in 1:np) {
    q[i] = p[np-i+1]
}
a=3;
b=5.;
row <- c(0,0,0,0,0)
for (i in 1:length(p)) {
  x = qgamma(p[i], a, scale=b )
  row[1] = x
  row[2] = a
  row[3] = b
  row[4] = dgamma ( x, a , scale=b )
  row[5] = pgamma ( x, a , scale=b )
  row[6] = pgamma ( x, a , scale=b , lower.tail = FALSE )
  print(row,digits=17)
  x = qchisq(q[i], k, 0 )
  row[1] = x
  row[2] = a
  row[3] = b
  row[4] = dgamma ( x, a , scale=b )
  row[5] = pgamma ( x, a , scale=b )
  row[6] = pgamma ( x, a , scale=b , lower.tail = FALSE )
  print(row,digits=17)
}
