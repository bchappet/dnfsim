// Copyright (C) 2009 - DIGITEO - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

// Test the Beta law in the R language
options(digits=20)
//
// Test the PDF
//
dbeta ( -1.0 , 1.0 , 2.0 );
dbeta ( 0.0 , 1.0 , 2.0 );
dbeta ( 0.1 , 1.0 , 2.0 );
dbeta ( 0.2 , 1.0 , 2.0 );
dbeta ( 0.4 , 1.0 , 2.0 );
dbeta ( 0.6 , 1.0 , 2.0 );
dbeta ( 0.8 , 1.0 , 2.0 );
dbeta ( 0.9 , 1.0 , 2.0 );
dbeta ( 1.0 , 1.0 , 2.0 );
dbeta ( 2.0 , 1.0 , 2.0 );
//
// Test the CDF
//
pbeta(0.0, 1, 2)
pbeta(0.1, 1, 2)
pbeta(0.2, 1, 2)
pbeta(0.4, 1, 2)
pbeta(0.6, 1, 2)
pbeta(0.8, 1, 2)
pbeta(0.9, 1, 2)
pbeta(1.0, 1, 2)
pbeta(2.0, 1, 2)
//
// Test the Inverse CDF
//
qbeta(0.5, 1, 2)
qbeta(0., 1, 2)
qbeta(1., 1, 2)
qbeta(0.6, 1, 2)
qbeta(0.8, 1, 2)
qbeta(0.9, 1, 2)
qbeta(0.2, 1, 2)
qbeta(0.1, 1, 2)
qbeta(1.e-2, 1, 2)
qbeta(1.e-5, 1, 2)
qbeta(1.e-10, 1, 2)
qbeta(1.e-20, 1, 2)
qbeta(1.e-50, 1, 2)
qbeta(1.e-100, 1, 2)
qbeta(1.e-200, 1, 2)

options(digits=20)

# [x a a PDF-P CDF-P CDF-Q]
# Inverse CDF to obtain x values
p <- c(1,0.999999999999999999,0.99999999999999,0.9999999999,0.9999999,0.99999,0.9999,0.999,0.99,0.9,0.8,0.6,0.5,0.2,0.1,1.e-2,1.e-5,1.e-10,1.e-20,1.e-50,1.e-100,1.e-200,0);
a=1;
b=2;
row <- c(0,0,0,0,0)
for (i in 1:length(p)) {
  x = qbeta(p[i], a, b)
  row[1] = x
  row[2] = a
  row[3] = b
  row[4] = dbeta ( x, a, b )
  row[5] = pbeta ( x, a, b )
  row[6] = pbeta ( x, a, b , lower.tail = FALSE )
  print(row,digits=17)
  x = qbeta(p[i], a, b, lower.tail = FALSE)
  row[1] = x
  row[2] = a
  row[3] = b
  row[4] = dbeta ( x, a, b )
  row[5] = pbeta ( x, a, b )
  row[6] = pbeta ( x, a, b , lower.tail = FALSE )
  print(row,digits=17)
}
