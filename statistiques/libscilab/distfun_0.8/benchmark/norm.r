// Copyright (C) 2009 - DIGITEO - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

// Test the Normal law in the R language
options(digits=20)
//
// Test the Probability Density Function
//
dnorm ( 1.5 , 1.0 , 2.0 );
dnorm ( 5.0 , 1.0 , 2.0 );
dnorm ( 10.0 , 1.0 , 2.0 );
dnorm ( 15.0 , 1.0 , 2.0 );
dnorm ( 20. , 1.0 , 2.0 );
dnorm ( 30. , 1.0 , 2.0 );
dnorm ( 50. , 1.0 , 2.0 );
dnorm ( 76. , 1.0 , 2.0 );
//
// Test the Cumulative Probability Density Function
//
pnorm(1, 1, 2)
pnorm(2, 1, 2)
pnorm(3, 1, 2)
pnorm(4, 1, 2)
pnorm(5, 1, 2)
pnorm(-5, 1, 2)
pnorm(-6, 1, 2)
pnorm(-10, 1, 2)
pnorm(-15, 1, 2)
//
// Test the Inverse Cumulative Probability Density Function
//
qnorm(0.99, 1, 2)
qnorm(0.9, 1, 2)
qnorm(0.5, 1, 2)
qnorm(0., 1, 2)
qnorm(1., 1, 2)
qnorm(0.6, 1, 2)
qnorm(0.8, 1, 2)
qnorm(0.9, 1, 2)
qnorm(0.2, 1, 2)
qnorm(0.1, 1, 2)
qnorm(1.e-2, 1, 2)
qnorm(1.e-5, 1, 2)
qnorm(1.e-10, 1, 2)
qnorm(1.e-20, 1, 2)
qnorm(1.e-50, 1, 2)
qnorm(1.e-100, 1, 2)
qnorm(1.e-200, 1, 2)

# Inverse CDF to obtain x values
p <- c(1,0.999999999999999999,0.99999999999999,0.9999999999,0.9999999,0.99999,0.9999,0.999,0.99,0.9,0.8,0.6,0.5,0.2,0.1,1.e-2,1.e-5,1.e-10,1.e-20,1.e-50,1.e-100,1.e-200,1.e-300,0);
# Reverse p to obtain q values
np <- length(p)
q <- p;
for (i in 1:np) {
q[i] = p[np-i+1]
}
mu = 1;
sigma=2;
row <- c(0,0,0,0,0)
for (i in 1:length(x)) {
  x = qnorm(p[i], mu, sigma)
  print(x,digits=17)
}

# [x mu sigma PDF-P CDF-P CDF-Q]
x     <- c( Inf,16.30146181031128,13.72268177939484,11.39867516458132,9.52978158784768,8.438032970911417,7.180464612335625,5.652695748081682,3.5631031310892,2.683242467145829,1.5066942062716,1,-0.683242467145828,-1.563103131089201,-3.652695748081682,-7.52978158784565,-11.72268180480811,-17.5246801795968,-28.86667506957698,-Inf)
mu     = 1;
sigma  = 2;
row <- c(0,0,0,0,0,0)
for (i in 1:length(x)) {
  row[1] = x[i]
  row[2] = mu
  row[3] = sigma
  row[4] = dnorm ( x[i], mu , sigma )
  row[5] = pnorm ( x[i], mu , sigma )
  row[6] = pnorm ( x[i], mu , sigma , lower.tail = FALSE )
  print(row,digits=17)
}


# [x mu sigma PDF-P CDF-P CDF-Q]
# Inverse CDF to obtain x values
p <- c(1,0.999999999999999999,0.99999999999999,0.9999999999,0.9999999,0.99999,0.9999,0.999,0.99,0.9,0.8,0.6,0.5,0.2,0.1,1.e-2,1.e-5,1.e-10,1.e-20,1.e-50,1.e-100,1.e-200,1.e-300,0);
# Reverse p to obtain q values
np <- length(p)
q <- p;
for (i in 1:np) {
    q[i] = p[np-i+1]
}
mu=1.;
sigma=2.;
row <- c(0,0,0,0,0)
for (i in 1:length(p)) {
  x = qnorm(p[i], mu, sigma)
  row[1] = x
  row[2] = mu
  row[3] = sigma
  row[4] = dnorm ( x, mu, sigma )
  row[5] = pnorm ( x, mu, sigma )
  row[6] = pnorm ( x, mu, sigma , lower.tail = FALSE )
  print(row,digits=17)
  x = qnorm(q[i], mu, sigma)
  row[1] = x
  row[2] = mu
  row[3] = sigma
  row[4] = dnorm ( x, mu, sigma )
  row[5] = pnorm ( x, mu, sigma )
  row[6] = pnorm ( x, mu, sigma , lower.tail = FALSE )
  print(row,digits=17)
}
