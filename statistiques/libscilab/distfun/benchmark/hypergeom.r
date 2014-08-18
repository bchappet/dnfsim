
// Copyright (C) 2012 - Prateek Papriwal
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

// Test the Binomial the R language
//
//
// Test the PDF
//
dhyper(0,515,515,500)
dhyper(100,515,515,500)
dhyper(187,515,515,500)
dhyper(188,515,515,500)
dhyper(200,515,515,500)
dhyper(300,515,515,500)
dhyper(312,515,515,500)
dhyper(313,515,515,500)
dhyper(400,515,515,500)
dhyper(500,515,515,500)
 
//
// Test the CDF
//
phyper(0,515,515,500)
phyper(100,515,515,500)
phyper(187,515,515,500)
phyper(188,515,515,500)
phyper(200,515,515,500)
phyper(300,515,515,500)
phyper(312,515,515,500)
phyper(313,515,515,500)
phyper(400,515,515,500)
phyper(500,515,515,500)
//
//

# Testing with R
# [x N pr PDF-P CDF-P CDF-Q]

x  <- c(0,100,187,188,200,300,312,313,400,500)
M  <- c(515,515,515,515,515,515,515,515,515,515)
k  <- c(515,515,515,515,515,515,515,515,515,515)
N  <- c(500,500,500,500,500,500,500,500,500,500)


row <- c(0,0,0,0,0,0,0)
for (i in 1:length(x)) {
	row[1] = x[i]
	row[2] = M[i]
	row[3] = k[i]
	row[4] = N[i]
	row[5]= dhyper(x[i],M[i],k[i],N[i])
	row[6]= phyper(x[i],M[i],k[i],N[i])
	row[7]= phyper(x[i],M[i],k[i],N[i],lower.tail = FALSE)
	print(row,digits=17)
}

# [x M k N PDF-P CDF-P CDF-Q]
# Inverse CDF to obtain x values
p <- c(1,0.999999999999999999,0.99999999999999,0.9999999999,0.9999999,0.99999,0.9999,0.999,0.99,0.9,0.8,0.6,0.5,0.2,0.1,1.e-2,1.e-5,1.e-10,1.e-20,1.e-50,1.e-100,1.e-200,1.e-300,0);
# Reverse p to obtain q values
np <- length(p)
q <- p;
for (i in 1:np) {
    q[i] = p[np-i+1]
}
M=80;
k=50;
N=30;
row <- c(0,0,0,0,0,0,0)
for (i in 1:length(p)) {
  x = qhyper(p[i], k,M-k,N)
  row[1] = x
  row[2] = M
  row[3] = k
  row[4] = N
  row[5] = dhyper ( x, k,M-k,N )
  row[6] = phyper ( x, k,M-k,N )
  row[7] = phyper ( x, k,M-k,N , lower.tail = FALSE )
  print(row,digits=17)
  x = qhyper(q[i], k,M-k,N)
  row[1] = x
  row[2] = M
  row[3] = k
  row[4] = N
  row[5] = dhyper ( x, k,M-k,N )
  row[6] = phyper ( x, k,M-k,N )
  row[7] = phyper ( x, k,M-k,N , lower.tail = FALSE )
  print(row,digits=17)
}
