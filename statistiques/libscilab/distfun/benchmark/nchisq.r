// Copyright (C) 2014 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

// Test the NonCentral Chi-Squared law in the R language
options(digits=20)
//
//

# Testing with R
# [Xn k ncp PDF-P CDF-P CDF-Q]

x  <- c(1,0.22,0.00001,10,1000,10 ^ -100,10 ^ 100,9,10,50)
k  <- c(1,5,1,5,10,1,1,10 ^ 5,10,5)

row <- c(0,0,0,0,0,0)
ncp=10.
for (i in 1:length(x)) {
	row[1] = x[i]
	row[2] = k[i]
	row[3] = ncp
	row[4] = dchisq(x[i],k[i],ncp)
	row[5] = pchisq(x[i],k[i],ncp)
	row[6] = pchisq(x[i],k[i],ncp,lower.tail = FALSE)
	print(row,digits=17)
}

# [x k ncp PDF-P CDF-P CDF-Q]
# Inverse CDF to obtain x values
p <- c(1,0.999999999999999999,0.99999999999999,0.9999999999,0.9999999,0.99999,0.9999,0.999,0.99,0.9,0.8,0.6,0.5,0.2,0.1,1.e-2,1.e-5,1.e-10,1.e-20,1.e-50,1.e-100,1.e-200,1.e-300,0);
# Reverse p to obtain q values
np <- length(p)
q <- p;
for (i in 1:np) {
    q[i] = p[np-i+1]
}
k=3;
ncp=10.
row <- c(0,0,0,0,0,0)
for (i in 1:length(p)) {
  x = qchisq(p[i], k,ncp )
  row[1] = x
  row[2] = k
  row[3] = ncp
  row[4] = dchisq ( x, k ,ncp )
  row[5] = pchisq ( x, k ,ncp )
  row[6] = pchisq ( x, k ,ncp , lower.tail = FALSE )
  print(row,digits=17)
  x = qchisq(q[i], k,ncp )
  row[1] = x
  row[2] = k
  row[3] = ncp
  row[4] = dchisq ( x, k ,ncp )
  row[5] = pchisq ( x, k ,ncp )
  row[6] = pchisq ( x, k ,ncp, lower.tail = FALSE )
  print(row,digits=17)
}
