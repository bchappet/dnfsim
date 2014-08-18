// Copyright (C) 2012 - 2014 - Michael Baudin
//
// This file must be used under the terms of the GNU Lesser General Public License license :
// http://www.gnu.org/copyleft/lesser.html

// A small test driver for cdflib.

#include <stdio.h>
#include <stdlib.h>
#include "cdflib.h"
#include "cdflib_private.h"
/*
*   PURPOSE
*      Generates random numbers uniform in (0,2147483647)
*      This is the URAND generator: 
*          s <- (a*s + c) mod m
*      with :
*             m = 2^{31} 
*             a = 843314861
*             c = 453816693
*      
*      s must be in [0,m-1] when user changes seed with unifrng_urand_set_state
*      period = m
*/
/* Reference
*   URAND, A UNIVERSAL RANDOM NUMBER GENERATOR 
*   BY, MICHAEL A. MALCOLM, CLEVE B. MOLER, 
*   STAN-CS-73-334, JANUARY 1973, 
*   COMPUTER SCIENCE  DEPARTMENT, 
*   School of Humanities and Sciences, STANFORD UNIVERSITY, 
*   ftp://reports.stanford.edu/pub/cstr/reports/cs/tr/73/334/CS-TR-73-334.pdf
*/
static unsigned int s = 0;

unsigned int myurand_raw()
{
	do
	{
		/* We get a result modulo 2^32 */
		s = 843314861ul * s + 453816693ul;  

		/* This is to get modulo 2^31 */
		if (s >= 2147483648ul) 
		{
			s -= 2147483648ul;
		}
	} while(s==0);

	return ( s );
}

// Returns a double randomly uniform in (0,1).
// This random number is generated according to the current RNG.
double myrand(void)
{
	double output;
	int R;
	// This is factor=1/2147483647
	double factor=4.6566128730773926e-10;
	R = myurand_raw();
	output = (double) R * factor;
	return output;
}

// Returns a double, with a random integer value.
// This integer is uniform in [1,2147483647]
// This is the direct output of the current RNG.
double mygenLargeInteger(void)
{
	double output;
	int R;
	R = myurand_raw();
	output = (double) R;
	return output;
}

/* Generates a double with an integer value uniform in the interval [a,b], 
*  where  (i)  a and b are integers (stored in double)
*         (ii) b-a+1 <= RngMaxInt[current_gen]
*  The associated checkings are assumed to be done prior to the call 
*  to unifrng_generateInteger. */
double mygenIntegerInRange(double a, double b)
{
	unsigned long k, d, qd;
	double output;
	int RngMaxInt=2147483647;

	d = (unsigned long)((b-a)+1);
	if ( d == 1)
		return a;

	qd = RngMaxInt - RngMaxInt % d;
	do
	{
		k = (unsigned long)mygenLargeInteger();
	}
	while ( k >= qd );

	output = a + (double)(k % d);
	return output;
}

void mymessagefun(char * message)
{
	printf("MyTest: %s\n",message);
	return;
}

void cdflibsetup()
{
	cdflib_startup();
	cdflib_messageSetFunction(mymessagefun);
	cdflib_randSetFunction(myrand);
	cdflib_randIntegerInRangeSetFunction(mygenIntegerInRange);
}
int main( int argc, const char* argv[] )
{
int status;
int n=10;
double x=0.;
double y;
double expected = 4.3178438821269056;
status = cdflib_kspdf(x, n, &y);

	return 0;
}
