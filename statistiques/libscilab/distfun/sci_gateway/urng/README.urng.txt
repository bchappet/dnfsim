Distribution Functions toolbox
Gateways to uniform random number generators

Purpose
-------

This gateway provides basic features for the Distribution Functions 
toolbox for Scilab. 
This is a set sources to manage uniform random generators.

These gateways exist but are not documented: they are "private" not designed 
to be directly used by the end user. 
Essentially, this is an intermediate state: when the toolbox is "finished", 
all functions should be provided directly with gateways.

Calling sequences
-----------------

The following funcitons configure or quiery the state of the 
underlying uniform random number generators.

// For all generators :

gen = distfun_genget()
distfun_genset(gen)

s=distfun_seedget()
distfun_seedset(s)

// For clcg4 :

distfun_streamset(g)  
g=distfun_streamget()

distfun_streaminit(I)

distfun_streamsetall(s1,s2,s3,s4)

distfun_streamadvnst(K)

Actions common to all the generators
------------------------------------

gen=distfun_genget() 
returns the current base generator. 
In this case gen is a string among "mt", "kiss", "clcg2", "clcg4", "urand", "fsultra".

distfun_genset(gen) sets the current base generator to be gen, a string 
among "mt", "kiss", "clcg2", "clcg4", "urand", "fsultra". 

s=distfun_seedget() gets the current state (the current seed) 
of the current base generator ; 
s is given as a column vector (of integers) of 
 * dimension 625 for mt, 
 * dimension 4 for kiss, 
 * dimension 2 for clcg2, 
 * dimension 40 for fsultra, 
 * dimension 4 for clcg4,
 * dimension 1 for urand.
For clcg4, s is the current state of the current virtual generator.
For mt, the first entry, s(1), is an index in [1,624].

distfun_seedset(s) or distfun_seedset(s1[,s2,s3,s4]) 
sets the state of the current base generator (the new seeds).

distfun_seedset for mt
s is a vector of integers of dim 625 (the first component is an index and must be in [1,624], 
the 624 last ones must be in [0,2^32[) (but must not be all zeros). 
A simpler initialisation may be done with only one integer s1 (s1 must be in [0,2^32[).

distfun_seedset for kiss
4 integers s1,s2, s3,s4 in [0,2^32[ must be provided ;

distfun_seedset for clcg2
2 integers s1 in [1,2147483562] and s2 in [1,2147483398] must be given ;

distfun_seedset for clcg4
4 integers s1 in [1,2147483646], s2 in [1,2147483542], s3 in [1,2147483422], 
s4 in [1,2147483322] are required.
Sets the initial state of generator 0 to s1,s2,s3,s4. 
The initial seeds of the other generators are set accordingly to be synchronised. 

distfun_seedset for urand
1 integer s1 in [0,2^31[ must be given.

distfun_seedset for fsultra
s is a vector of integers of dim 40 (the first component is an index and 
must be in [0,37], the 2d component is a flag (0 or 1), the 3d an integer in [1,2^32[ 
and the 37 others integers in [0,2^32[). 
A simpler (and recommended) initialisation may be done with two integers s1 and s2 in [0,2^32[.


Functions for clcg4
-------------------

distfun_streamset
distfun_streamset(g) 
  sets the current virtual generator for clcg4 to g, where g is a double 
  with an integer value in the set 0,1,2,...,100.
  When clcg4 is set as the current uniform random number generator, 
  this is the virtual (clcg4) generator number g which is used.
  By default the current virtual generator is g=0.

distfun_streamget
g=distfun_streamget() returns the number of the current virtual clcg4 generator.

distfun_streaminit
distfun_streaminit(-1) : 
    sets the state of the current virtual generator to its initial seed
distfun_streaminit(0) : 
    sets the state of the current virtual generator 
    to its last (previous) seed (i.e. to the beginning of the current segment)
distfun_streaminit(1) : 
    sets the state of the current virtual generator to the beginning of the next segment 
    and resets the current segment parameters.

Notes on clcg4
--------------

The clcg4 generator may be used as the others generators, but 
additionnaly provides streams and substreams.
Indeed, it offers the advantage to be split in several (101) virtual 
generators with non over-lapping sequences. 
Indeed, when you use a classic generator you may change the initial 
state (seeds) in order to get 
another sequence but you there is no guarantee to get a completely different one. 
With clcg4, each virtual generator is associated with its own sequence, and 
the sequences are guaranteed to be different. 
In other words, this is as if there was 101 different random number generators, 
each one producing its own sequence of random numbers.

Each virtual generator corresponds to a sequence of 2^72 values which is further 
split into V=2^31 segments (or blocks) of length W=2^41. 
For a given virtual generator you have the possibility to return at the 
beginning of the sequence or at the beginning of the current segment or 
to go directly at the next segment. 
You may also change the initial state (seed) of the generator 0 with the 
"setall" option which then change also the initial state of the other 
virtual generators so as to get synchronisation. 
In other words, depending on the new initial state of virtual generator 0, 
the initial state of the virtual generator 1, 2, ..., 100 are recomputed so 
as to get 101 non over-lapping sequences.

Description
-----------

The user has the possibility to choose between different base 
generators (which give random integers following the "lgi" distribution, 
the others being gotten from it).

mt
the Mersenne-Twister of M. Matsumoto and T. Nishimura, period about 2^19937 (approximately 4.3x10^6001), 
state given by an array of 624 integers (plus an index onto this array). 
This is the default generator.

kiss
The "Keep It Simple Stupid" of G. Marsaglia, period about 2^123 (approximately 1.1x10^37), 
state given by 4 integers.

clcg2
a Combined 2 Linear Congruential Generator of P. L'Ecuyer, 
period about 2^61 (approximately 2.3x10^18), state given by 2 integers.

clcg4
a Combined 4 Linear Congruential Generator of P. L'Ecuyer, period about 2^121 (approximately 2.6x10^36), 
state given by 4 integers. 
This one is split in 101 different virtual (non over-lapping) generators which 
may be useful for different tasks (see "Actions specific to clcg4" and "Test example for clcg4").

fsultra
a Subtract-with-Borrow generator mixing with a congruential generator of 
Arif Zaman and George Marsaglia, period more than 10^356, state given by 
an array of 37 integers (plus an index onto this array, a flag (0 or 1) and another integer).

urand
state given by 1 integer, period of 2^31 (approximately 2.1x10^9). 
This generator is based on "Urand, A Universal Random Number Generator" By Michael A. Malcolm, 
Cleve B. Moler, Stan-Cs-73-334, January 1973, Computer Science Department, 
School Of Humanities And Sciences, Stanford University. 
This is the fastest of this toolbox but its statistical qualities 
are less satisfactory than the other generators. 
The "urand" generator corresponds to the generator used by the Scilab 
function rand.

Authors
-------

randlib
The codes to generate sequences following other distributions than 
def, unf, lgi, uin and geom are from "Library of Fortran Routines for Random 
Number Generation", by Barry W. Brown and James Lovato, Department of 
Biomathematics, The University of Texas, Houston. 
The source code is available at : http://www.netlib.org/random/ranlib.f.tar.gz

mt
The code is the mt19937int.c by M. Matsumoto and T. Nishimura, 
"Mersenne Twister: A 623-dimensionally equidistributed uniform pseudorandom 
number generator", ACM Trans. on Modeling and Computer Simulation Vol. 8, No. 1, January, pp.3-30 1998.

kiss
The code was given by G. Marsaglia at the end of a thread concerning RNG in C in 
several newsgroups (whom sci.math.num-analysis) "My offer of RNG's for C was an 
invitation to dance..." only kiss have been included in Scilab. 
Kiss is made of a combinaison of severals others which are not visible at the scilab level.

clcg2
The method is from P. L'Ecuyer but the C code is provided at Luc Devroye's home 
page (http://cg.scs.carleton.ca/~luc/rng.html See "lecuyer.c".). 
This generator is made of two linear congruential sequences 
 * s1 = a1*s1 mod m1, with a1 = 40014, m1 = 2147483563 and 
 * s2 = a2*s2 mod m2 , with a2 = 40692, m2 = 2147483399. 
The output is computed from output = s1-s2 mod (m1 - 1). 
Therefore, output is in [0, 2147483561]. The period is about 2.3 10^18. 
The state is given by (s1, s2). 
In case of a user modification of the state we must have : s1 in [1, m1-1] and s2 in [1, m2-1]. 
The default initial seeds are s1 = 1234567890 and s2 = 123456789.

clcg4
The code is from P. L'Ecuyer and Terry H.Andres and provided at the P. L'Ecuyer 
home page ( http://www.iro.umontreal.ca/~lecuyer/papers.html) 
Pierre L'Ecuyer and Terry H. Andres. 1997. 
A random number generator based on the combination of four LCGs. 
Math. Comput. Simul. 44, 1 (May 1997), 99-107.
A paper is also provided and this new package is the logical successor of an old 's 
one from : 
P. L'Ecuyer and S. Cote.
Implementing a Random Number Package with Splitting Facilities. 
ACM Transactions on Mathematical Software, 17:1,pp 98-111, 1991.

fsultra
code from Arif Zaman (arif@stat.fsu.edu) and George Marsaglia (geo@stat.fsu.edu)

Scilab packaging
 * By Jean-Philippe Chancelier and Bruno Pincon

This help page
 * By Jean-Philippe Chancelier and Bruno Pincon
 * Copyright (C) 2010 - DIGITEO - Michael Baudin

