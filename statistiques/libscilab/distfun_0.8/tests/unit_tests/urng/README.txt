Copyright (C) 2012 - Michael Baudin
Copyright (C) 2010-2011 - DIGITEO - Michael Baudin

Rationale for the unit tests of random number generators.

There are several ways to check the output of random number generators.

1. Uniform random number generators are based on deterministic integer sequences.
These integers are then scaled into the [0,1[ interval.
When we set the seed to a constant (e.g. seed = 0, or seed=123456),
the doubles which are produced are always the same.
This does not check the quality of the generator.
But it does check that the generator under evaluation is, indeed, the 
expected generator.

2. Non-uniform random numbers have a given mean and a variance which 
can be predicted by there associated distribution function.
The problem is there to set the relative tolerance. 
The convergence of the sample mean to the expectation is driven by the
law of large numbers. 
The standard deviation of the sample mean is sigma/sqrt(n), 
meaning that to reduce the shift by a factor 2, we have to use 4x more points. 

3. The quality of a random number generator can be assessed by 
statistical tests. 
The Kolmogorov-Smirnov test is based on the comparison between the 
empirical CDF and the theoretical CDF. The difference between the two is 
a random variable, which must evolve according to the Normal distribution function. 
Then, the sum of the squares of the differences should evolve according to the 
Chi-square distribution function. By inverting this last distribution, we can 
compute the probability that the empirical distribution matches the theoretical distribution.

4. The empirical histogram must match the theoretical PDF. 
The number of classes is NC.
The classes XC are computed from NC and the actual output ; X has NC+1 entries.
The empirical histogram is the probability that X is the intervals defined by X.
The theoretical histogram is computed by differences 
of the cumulated probability distribution function.
The histograms can be graphically compared with the statements:
plot(X(1:$-1),"Empirical PDF","bo-"); Empirical Histogram
plot(X(1:$-1),"Theoretical PDF","rox-"); Theoretical Histogram
The limitation of this test is that it depends on the parameter NC: 
if NC changes, the test changes.

5. The empirical CDF must match the theoretical CDF. 
We can either 
 * make a P-P plot and compare a straight line with the probability 
 of the samples (test 5.1),
 * compare the empirical CDF with the theoretical CDF (test 5.2).
The test 5.1 (P-P plot) is used for continuous random variables.
The test 5.2 (empirical histogram) is used for integer random variables.
The P-P plot is computed by sorting the samples into increasing order, 
and computing the cumulated probability P(X<=x) of each sample x.
The test #5 does not depend on any parameter: this makes it different 
from the test #4.

In these scripts, we check the non-uniform random numbers.
We use the strategies #2 and #4 and #5.
The strategy #3 should be used in a future version of the tests (it requires to 
develop the kstest function).

