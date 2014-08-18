// Copyright (C) 2012 - Prateek Papriwal
// Copyright (C) 2012 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

// Test the Chi-Square law in the R language
options(digits=20)
//
//
// Test the PDF
//
dchisq(1,1)
dchisq(0.22,5)
dchisq(0.00001,1)
dchisq(10,5)
dchisq(1000,10)
dchisq(10 ^ -100,1)
dchisq(10 ^ 100,1)
dchisq(9,10 ^ 5)
dchisq(10,10)
dchisq(50,5)
//
// Test the CDF
//
pchisq(1,1)
pchisq(0.22,5)
pchisq(0.00001,1)
pchisq(10,5)
pchisq(1000,10)
pchisq(10 ^ -100,1)
pchisq(10 ^ 100,1)
pchisq(9,10 ^ 5)
pchisq(10,10)
pchisq(50,5)
//
//

# Testing with R
# [Xn Pr PDF-P CDF-P CDF-Q]

x  <- c(1,0.22,0.00001,10,1000,10 ^ -100,10 ^ 100,9,10,50)
k  <- c(1,5,1,5,10,1,1,10 ^ 5,10,5)

row <- c(0,0,0,0,0)
for (i in 1:length(x)) {
	row[1] = x[i]
	row[2] = k[i]
	row[3] = dchisq(x[i],k[i])
	row[4] = pchisq(x[i],k[i])
	row[5] = pchisq(x[i],k[i],lower.tail = FALSE)
	print(row,digits=17)
}

# [x k PDF-P CDF-P CDF-Q]
# Inverse CDF to obtain x values
p <- c(1,0.999999999999999999,0.99999999999999,0.9999999999,0.9999999,0.99999,0.9999,0.999,0.99,0.9,0.8,0.6,0.5,0.2,0.1,1.e-2,1.e-5,1.e-10,1.e-20,1.e-50,1.e-100,1.e-200,1.e-300,0);
# Reverse p to obtain q values
np <- length(p)
q <- p;
for (i in 1:np) {
    q[i] = p[np-i+1]
}
k=3;
row <- c(0,0,0,0,0)
for (i in 1:length(p)) {
  x = qchisq(p[i], k, 0 )
  row[1] = x
  row[2] = k
  row[3] = dchisq ( x, k , 0 )
  row[4] = pchisq ( x, k , 0 )
  row[5] = pchisq ( x, k , 0 , lower.tail = FALSE )
  print(row,digits=17)
  x = qchisq(q[i], k, 0 )
  row[1] = x
  row[2] = k
  row[3] = dchisq ( x, k , 0 )
  row[4] = pchisq ( x, k , 0 )
  row[5] = pchisq ( x, k , 0 , lower.tail = FALSE )
  print(row,digits=17)
}
