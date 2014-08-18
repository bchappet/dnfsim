Distribution Functions toolbox
Gateway for non-uniform random number generators

Purpose
-------

This gateway provides basic features for the Distribution Functions 
toolbox for Scilab. 
This is a set of two different collection of sources:
 * sources to produce uniform random numbers, 
 * sources to produce non-uniform random numbers.

These gateways exist but are not documented: they are "private" not designed 
to be directly used by the end user. 
Essentially, this is an intermediate state: when the toolbox is "finished", 
all functions should be provided directly with gateways.

Non uniform random numbers
--------------------------

Here are the list of files related to the generation of non-uniform 
random numbers :

    "sci_distfun_grandbet.c"
    "sci_distfun_grandf.c"
    "sci_distfun_grandmul.c"
    "sci_distfun_grandgam.c"
    "sci_distfun_grandnor.c"
    "sci_distfun_grandunf.c"
    "sci_distfun_granduin.c"
    "sci_distfun_grandnbn.c"
    "sci_distfun_grandbin.c"
    "sci_distfun_grandmn.c"
    "sci_distfun_grandmarkov.c"
    "sci_distfun_grandnch.c"
    "sci_distfun_grandnf.c"
    "sci_distfun_grandchi.c"


Calling sequences
-----------------

Y=distfun_grandbet(m,n,A,B)
Y=distfun_grandbin(m,n,N,p)
Y=distfun_grandnbn(m,n,N,p)
Y=distfun_grandchi(m,n,Df)
Y=distfun_grandnch(m,n,Df,Xnon)
Y=distfun_grandexp(m,n,Av)
Y=distfun_grandf(m,n,Dfn,Dfd)
Y=distfun_grandnf(m,n,Dfn,Dfd,Xnon)
Y=distfun_grandgam(m,n,,shape,rate)
Y=distfun_grandnor(m,n,Av,Sd)
Y=distfun_grandgeom(m,n, p)
Y=distfun_grandpoi(m,n,mu)
Y=distfun_grandunf(m,n,Low,High)
Y=distfun_granduin(m,n,Low,High)
Y=distfun_grandmn(n,Mean,Cov)
Y=distfun_grandmarkov(n,P,x0)
Y=distfun_grandmul(n,nb,P)
Y=distfun_grandprm(n,vect)

See the source codes and the help of the "grand function in Scilab 5 for more details.

Arguments
---------

m, n
integers, size of the wanted matrix Y

Y
a m-by-n matrix of doubles, with random entries

Description
-----------

This function generates random numbers from various distributions.
The calling sequences:

Y=distfun_grandbet(m,n,A,B)
Y=distfun_grandbin(m,n,N,p)
Y=distfun_grandnbn(m,n,N,p)
Y=distfun_grandchi(m,n,Df)
Y=distfun_grandnch(m,n,Df,Xnon)
Y=distfun_grandexp(m,n,Av)
Y=distfun_grandf(m,n,Dfn,Dfd)
Y=distfun_grandnf(m,n,Dfn,Dfd,Xnon)
Y=distfun_grandgam(m,n,,shape,rate)
Y=distfun_grandnor(m,n,Av,Sd)
Y=distfun_grandgeom(m,n, p)
Y=distfun_grandpoi(m,n,mu)
Y=distfun_grandunf(m,n,Low,High)
Y=distfun_granduin(m,n,Low,High)
    
produce a m-by-n matrix with random entries.

The calling sequences:

Y=distfun_grandmn(n,Mean,Cov)
Y=distfun_grandmarkov(n,P,x0)
Y=distfun_grandmul(n,nb,P)
Y=distfun_grandprm(n,vect)
    
produce a m-by-n matrix with random entries, where m is the size of the 
argument Mean, Cov, P or vect depending on the case (see below for details).

Random numbers from a given distribution
----------------------------------------

beta
Y=distfun_grandbet(m,n,A,B) generates random variates from the beta distribution with parameters A and B. The density of the beta distribution is (0 < x < 1) :
 
A and B must be reals >10^(-37). Related function(s) : cdfbet.

binomial
Y=distfun_grandbin(m,n,N,p) generates random variates from the binomial distribution with parameters N (positive integer) and p (real in [0,1]) : number of successes in N independent Bernouilli trials with probability p of success. Related function(s) : binomial, cdfbin.

negative binomial
Y=distfun_grandnbn(m,n,N,p) generates random variates from the negative binomial distribution with parameters N (positive integer) and p (real in (0,1)) : number of failures occurring before N successes in independent Bernouilli trials with probability p of success. Related function(s) : cdfnbn.

chisquare
Y=distfun_grandchi(m,n, Df) generates random variates from the chisquare distribution with Df (real > 0.0) degrees of freedom. Related function(s) : cdfchi.

non central chisquare
Y=distfun_grandnch(m,n,Df,Xnonc) generates random variates from the non central chisquare distribution with Df degrees of freedom (real >= 1.0) and noncentrality parameter Xnonc (real >= 0.0). Related function(s) : cdfchn.

exponential
Y=distfun_grandexp(m,n,Av) generates random variates from the exponential distribution with mean Av (real >= 0.0).

F variance ratio
Y=distfun_grandf(m,n,Dfn,Dfd) generates random variates from the F (variance ratio) distribution with Dfn (real > 0.0) degrees of freedom in the numerator and Dfd (real > 0.0) degrees of freedom in the denominator. Related function(s) : cdff.

non central F variance ratio
Y=distfun_grandnf(m,n,Dfn,Dfd,Xnonc) generates random variates from the noncentral F (variance ratio) distribution with Dfn (real >= 1) degrees of freedom in the numerator, and Dfd (real > 0) degrees of freedom in the denominator, and noncentrality parameter Xnonc (real >= 0). Related function(s) : cdffnc.

gamma
Y=distfun_grandgam(m,n,shape,rate) generates random variates from the gamma distribution with parameters shape (real > 0) and rate (real > 0). The density of the gamma distribution is :
Related function(s) : gamma, cdfgam.

Gauss Laplace (normal)
Y=distfun_grandnor(m,n,Av,Sd) generates random variates from the normal distribution with mean Av (real) and standard deviation Sd (real >= 0). Related function(s) : cdfnor.

multivariate gaussian (multivariate normal)
Y=distfun_grandmn(n,Mean,Cov) generates n multivariate normal random variates ; Mean must be a m x 1 matrix and Cov a m x m symmetric positive definite matrix (Y is then a m x n matrix).

geometric
Y=distfun_grandgeom(m,n, p) generates random variates from the geometric distribution with parameter p : number of Bernouilli trials (with probability succes of p) until a succes is met. p must be in [pmin,1] (with pmin = 1.3 10^(-307)).
Y contains positive real numbers with integer values, with are the "number of trials to get a success".

markov
Y=distfun_grandmarkov(n,P,x0) generate n successive states of a Markov chain described by the transition matrix P. Initial state is given by x0. If x0 is a matrix of size m=size(x0,"*") then Y is a matrix of size m x n. Y(i,:) is the sample path obtained from initial state x0(i).

multinomial
Y=distfun_grandmul(n,nb,P) generates n observations from the Multinomial distribution : class nb events in m categories (put nb "balls" in m "boxes"). P(i) is the probability that an event will be classified into category i. P the vector of probabilities is of size m-1 (the probability of category m being 1-sum(P)). Y is of size m x n, each column Y(:,j) being an observation from multinomial distribution and Y(i,j) the number of events falling in category i (for the j th observation) (sum(Y(:,j)) = nb).

Poisson
Y=distfun_grandpoi(m,n,mu) generates random variates from the Poisson distribution 
with mean mu (real >= 0.0). Related function(s) : cdfpoi.

random permutations
Y=distfun_grandprm(n,vect) generate n random permutations of the column vector (m x 1) vect.

uniform
Y=distfun_grandunf(m,n,Low,High) generates random reals uniformly distributed in [Low, High).

uniform
Y=distfun_granduin(m,n,Low,High) generates random integers uniformly distributed 
between Low and High (included). High and Low must be integers such that (High-Low+1) < 2,147,483,561.


History
-------

The Scilab packaging for Scilab v4 is by 
Jean-Philippe Chancelier and Bruno Pincon.
This collection is essentially from the "Randlib" module, designed 
by Jean-Philippe Chancelier and Bruno Pincon for Scilab v4. 
This gateway is a refactoring of the "grand" function of Scilab 5, where 
all functions were available in one single function.

The codes to generate sequences following other distributions than 
def, unf, lgi, uin and geom are from "Library of Fortran Routines for Random 
Number Generation", by Barry W. Brown and James Lovato, Department of 
Biomathematics, The University of Texas, Houston. 
The source code is available at : 

http://www.netlib.org/random/ranlib.f.tar.gz

Authors
-------

 * Copyright (C) 2012 - Michael Baudin
 * Copyright (C) 2011 - DIGITEO - Michael Baudin
 * Copyright (C) 2008 - 2011 - INRIA - Michael Baudin
 * Copyright (C) 2010 - DIGITEO - Michael Baudin
 * Copyright (C) 2005 - Bruno Pincon
 * Copyright (C) 2004 - Bruno Pincon
 * Copyright (C) 2002 - Bruno Pincon
 * Copyright (C) - Jean-Philippe Chancelier
 * Copyright (C) 1994 - Barry W. Brown, James Lovato (ranlib)
 * Probably many others ...

Licence
------

This toolbox is released under the CeCILL_V2 licence :

http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

