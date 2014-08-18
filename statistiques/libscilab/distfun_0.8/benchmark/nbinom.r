
// Copyright (C) 2014 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

// Test the Negative Binomial the R language


# Testing with R
# [x R P PDF-P CDF-P CDF-Q]

x=10
R=10
P=0.5
Q=0.5
row <- c(0,0,0,0,0,0)
for (i in 1:5) {
	row[1] = x
	row[2] = R
	row[3] = P
	row[4]= dnbinom(x,R,P)
	row[5]= pnbinom(x,R,P)
	row[6]= pnbinom(x,R,P,lower.tail = FALSE)
	print(row,digits=17)
	Q=Q/1.e10
	P=1-Q
}

# [x R P PDF-P CDF-P CDF-Q]
# Inverse CDF to obtain x values
p <- c(1,0.999999999999999999,0.99999999999999,0.9999999999,0.9999999,0.99999,0.9999,0.999,0.99,0.9,0.8,0.6,0.5,0.2,0.1,1.e-2,1.e-5,1.e-10,1.e-20,1.e-50,1.e-100,1.e-200,1.e-300,0);
# Reverse p to obtain q values
np <- length(p)
q <- p;
for (i in 1:np) {
    q[i] = p[np-i+1]
}
size=50;
prob=0.5;
row <- c(0,0,0,0,0)
for (i in 1:length(p)) {
  x = qbinom(p[i], size, prob)
  row[1] = x
  row[2] = size
  row[3] = prob
  row[4] = dnbinom ( x, size, prob)
  row[5] = pnbinom ( x, size, prob)
  row[6] = pnbinom ( x, size, prob, lower.tail = FALSE )
  print(row,digits=17)
  x = qbinom(q[i], size, prob)
  row[1] = x
  row[2] = size
  row[3] = prob
  row[4] = dnbinom ( x, size, prob)
  row[5] = pnbinom ( x, size, prob)
  row[6] = pnbinom ( x, size, prob, lower.tail = FALSE )
  print(row,digits=17)
}


p <- c(1,0.5,0);
size=10;
prob=0.;
row <- c(0,0,0,0,0)
for (i in 1:length(p)) {
  x = qbinom(p[i], size, prob)
  row[1] = x
  row[2] = size
  row[3] = prob
  row[4] = dnbinom ( x, size, prob)
  row[5] = pnbinom ( x, size, prob)
  row[6] = pnbinom ( x, size, prob, lower.tail = FALSE )
  print(row,digits=17)
  x = qbinom(q[i], size, prob)
  row[1] = x
  row[2] = size
  row[3] = prob
  row[4] = dnbinom ( x, size, prob)
  row[5] = pnbinom ( x, size, prob)
  row[6] = pnbinom ( x, size, prob, lower.tail = FALSE )
  print(row,digits=17)
}
