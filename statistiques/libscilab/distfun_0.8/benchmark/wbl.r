// Copyright (C) 2014 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

// Test the Weibull law in the R language
options(digits=20)

# [x scale scale PDF-P CDF-P CDF-Q]
# Inverse CDF to obtain x values
p <- c(1,0.999999999999999999,0.99999999999999,0.9999999999,0.9999999,0.99999,0.9999,0.999,0.99,0.9,0.8,0.6,0.5,0.2,0.1,1.e-2,1.e-5,1.e-10,1.e-20,1.e-50,1.e-100,1.e-200,0);
shape=1;
scale=2;
row <- c(0,0,0,0,0)
for (i in 1:length(p)) {
  x = qweibull(p[i], scale, shape)
  row[1] = x
  row[2] = scale
  row[3] = shape
  row[4] = dweibull ( x, shape,  scale)
  row[5] = pweibull ( x, shape,  scale)
  row[6] = pweibull ( x, shape,  scale, lower.tail = FALSE )
  print(row,digits=17)
  x = qbeta(p[i], scale, shape, lower.tail = FALSE)
  row[1] = x
  row[2] = scale
  row[3] = shape
  row[4] = dweibull ( x, shape,  scale)
  row[5] = pweibull ( x, shape,  scale)
  row[6] = pweibull ( x, shape,  scale, lower.tail = FALSE )
  print(row,digits=17)
}


# Inverse CDF to obtain x values
# shape = 1 is scale limit case
p <- c(1,0.999999999999999999,0.99999999999999,0.9999999999,0.9999999,0.99999,0.9999,0.999,0.99,0.9,0.8,0.6,0.5,0.2,0.1,1.e-2,1.e-5,1.e-10,1.e-20,1.e-50,1.e-100,1.e-200,0);
shape=0.5;
scale=2;
row <- c(0,0,0,0,0)
for (i in 1:length(p)) {
  x = qweibull(p[i], scale, shape)
  row[1] = x
  row[2] = scale
  row[3] = shape
  row[4] = dweibull ( x, shape,  scale)
  row[5] = pweibull ( x, shape,  scale)
  row[6] = pweibull ( x, shape,  scale, lower.tail = FALSE )
  print(row,digits=17)
  x = qbeta(p[i], scale, shape, lower.tail = FALSE)
  row[1] = x
  row[2] = scale
  row[3] = shape
  row[4] = dweibull ( x, shape,  scale)
  row[5] = pweibull ( x, shape,  scale)
  row[6] = pweibull ( x, shape,  scale, lower.tail = FALSE )
  print(row,digits=17)
}

# Inverse CDF to obtain x values
# shape = 1 is scale limit case
p <- c(1,0.999999999999999999,0.99999999999999,0.9999999999,0.9999999,0.99999,0.9999,0.999,0.99,0.9,0.8,0.6,0.5,0.2,0.1,1.e-2,1.e-5,1.e-10,1.e-20,1.e-50,1.e-100,1.e-200,0);
shape=2.0;
scale=2.;
row <- c(0,0,0,0,0)
for (i in 1:length(p)) {
  x = qweibull(p[i], scale, shape)
  row[1] = x
  row[2] = scale
  row[3] = shape
  row[4] = dweibull ( x, shape,  scale)
  row[5] = pweibull ( x, shape,  scale)
  row[6] = pweibull ( x, shape,  scale, lower.tail = FALSE )
  print(row,digits=17)
  x = qbeta(p[i], scale, shape, lower.tail = FALSE)
  row[1] = x
  row[2] = scale
  row[3] = shape
  row[4] = dweibull ( x, shape,  scale)
  row[5] = pweibull ( x, shape,  scale)
  row[6] = pweibull ( x, shape,  scale, lower.tail = FALSE )
  print(row,digits=17)
}
