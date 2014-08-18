# Copyright (C) 2014 - Michael Baudin
#
# This file must be used under the terms of the CeCILL.
# This source file is licensed as described in the file COPYING, which
# you should have received as part of this distribution.  The terms
# are also available at
# http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt
#
# Test the pGamma law in the R language
# Default is the rate, this is why we specify the scale.
options(digits=20)

# [x a CDF-P CDF-Q]
# Inverse CDF to obtain x values
p <- c(1,0.999999999999999999,0.99999999999999,0.9999999999,0.9999999,0.99999,0.9999,0.999,0.99,0.9,0.8,0.6,0.5,0.2,0.1,1.e-2,1.e-5,1.e-10,1.e-20,1.e-50,1.e-100,1.e-200,1.e-300,0);
a=3;
row <- c(0,0,0,0)
for (i in 1:length(p)) {
  x = qgamma(p[i], a)
  row[1] = x
  row[2] = a
  row[3] = pgamma ( x, a )
  row[4] = pgamma ( x, a , lower.tail = FALSE )
  print(row,digits=17)
}
a=1.e-10;
row <- c(0,0,0,0)
for (i in 1:length(p)) {
  x = qgamma(p[i], a)
  row[1] = x
  row[2] = a
  row[3] = pgamma ( x, a )
  row[4] = pgamma ( x, a , lower.tail = FALSE )
  print(row,digits=17)
}
a=1.e10;
row <- c(0,0,0,0)
for (i in 1:length(p)) {
  x = qgamma(p[i], a)
  row[1] = x
  row[2] = a
  row[3] = pgamma ( x, a )
  row[4] = pgamma ( x, a , lower.tail = FALSE )
  print(row,digits=17)
}