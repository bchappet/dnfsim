// Copyright (C) 2014 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

# Test the Normal law in the R language
options(digits=20)
mu=c(12,-31);
sigma=matrix(c(3.0,0.5,0.5,1.0), ncol=2)
#
# mu=[12,-31]
# sigma=[3.0,0.5;0.5,1.0]
# [x,PDF]
rowi <- c(0,0,0)
for (i in 1:7) {
  x=rmvnorm(1,mu,sigma)
  y=dmvnorm(x,mu, sigma)
  rowi[1]=x[1]
  rowi[2]=x[2]
  rowi[3]=y
  print(rowi,digits=17)
}
# See with mu+i*sigma, i=1,2,...,7
for (i in 1:7) {
  x[1]=mu[1]+i*sqrt(sigma[1,1])
  x[2]=mu[2]+i*sqrt(sigma[2,2])
  y=dmvnorm(x,mu, sigma)
  rowi[1]=x[1]
  rowi[2]=x[2]
  rowi[3]=y
  print(rowi,digits=17)
}
# See with mu-i*sigma, i=1,2,...,7
for (i in 1:7) {
  x[1]=mu[1]-i*sqrt(sigma[1,1])
  x[2]=mu[2]-i*sqrt(sigma[2,2])
  y=dmvnorm(x,mu, sigma)
  rowi[1]=x[1]
  rowi[2]=x[2]
  rowi[3]=y
  print(rowi,digits=17)
}
# See with mu+/-i*sigma, i=1,2,...,7
for (i in 1:7) {
  x[1]=mu[1]+i*sqrt(sigma[1,1])
  x[2]=mu[2]-i*sqrt(sigma[2,2])
  y=dmvnorm(x,mu, sigma)
  rowi[1]=x[1]
  rowi[2]=x[2]
  rowi[3]=y
  print(rowi,digits=17)
}
# See with mu+/-i*sigma, i=1,2,...,7
for (i in 1:7) {
  x[1]=mu[1]-i*sqrt(sigma[1,1])
  x[2]=mu[2]+i*sqrt(sigma[2,2])
  y=dmvnorm(x,mu, sigma)
  rowi[1]=x[1]
  rowi[2]=x[2]
  rowi[3]=y
  print(rowi,digits=17)
}
