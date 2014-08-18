// Copyright (C) 201 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

// Test the Kolmogorov Smirnov law in the R language
//
// Default is the rate, this is why we specify the scale.
options(digits=20)
//
//
//

# Does x come from a shifted gamma distribution 
# with shape 3 and rate 2?
row <- c(0,0,0)
for (i in 1:10) {
	x <- rnorm(50)
	t<-ks.test(x+2, "pgamma", 3, 2)
	row[1] = t$statistic
	row[2] = 50
	row[3] = t$p.value
	print(row,digits=17)
}
row <- c(0,0,0)
for (i in 1:10) {
	x <- rnorm(50)
	t<-ks.test(x, "pnorm", 0, 1)
	row[1] = t$statistic
	row[2] = 50
	row[3] = t$p.value
	print(row,digits=17)
}
row <- c(0,0,0)
n<-5
for (i in 1:10) {
	x <- rnorm(n)
	t<-ks.test(x, "pnorm", 0, 1)
	row[1] = t$statistic
	row[2] = n
	row[3] = t$p.value
	print(row,digits=17)
	n<-2*n
}
