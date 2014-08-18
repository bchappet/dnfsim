
/*
* Copyright (C) 2012 - Michael Baudin
* Copyright (C) ENPC
*
* This file must be used under the terms of the CeCILL.
* This source file is licensed as described in the file COPYING, which
* you should have received as part of this distribution.  The terms
* are also available at
* http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt
*
*/

#include <string.h>
#include <math.h>
#include <stdio.h>

#include "unifrng.h"
#include "unifrng_private.h"

/* the current generator : */
int current_gen = MT;

// Maximum number of generators : 
// MT=0, KISS=1, CLCG4=2, CLCG2=3, URAND=4, FSULTRA=5, CRAND=6
#define unifrng_NbGen 7
/*  names */
char *names_gen[unifrng_NbGen] = { "mt",  "kiss","clcg4", "clcg2", "urand", "fsultra", "crand" };

// The dimension of the internal state of each generator
int dim_state_mt=625;
int dim_state_fsultra = 40;
int dim_state_clcg4=4;
int dim_state_clcg2=2;
int dim_state_urand=1;
int dim_state_kiss=4;
int dim_state_crand=1;


// Get the current generator
void unifrng_getcurrentgen(int * cgen)
{
	*cgen = current_gen;
}

// Set the current generator
int unifrng_setcurrentgen(int newgen)
{
	if (newgen==MT 
		|| newgen==KISS 
		|| newgen==CLCG4 
		|| newgen==CLCG2 
		|| newgen==URAND 
		|| newgen==FSULTRA 
		|| newgen==CRAND)
	{
		current_gen = newgen;
		return UNIFRNG_OK;
	}
	else
	{
		// Error !
		return UNIFRNG_ERROR;
	}
}

// Set the current generator
int unifrng_setcurrentgenname(char * newgen)
{
	if (strcmp("mt",newgen)==0)
	{
		current_gen = MT;
		return UNIFRNG_OK;
	}
	else if (strcmp("kiss",newgen)==0)
	{
		current_gen = KISS;
		return UNIFRNG_OK;
	}
	else if (strcmp("clcg4",newgen)==0)
	{
		current_gen = CLCG4;
		return UNIFRNG_OK;
	}
	else if (strcmp("clcg2",newgen)==0)
	{
		current_gen = CLCG2;
		return UNIFRNG_OK;
	}
	else if (strcmp("urand",newgen)==0)
	{
		current_gen = URAND;
		return UNIFRNG_OK;
	}
	else if (strcmp("fsultra",newgen)==0)
	{
		current_gen = FSULTRA;
		return UNIFRNG_OK;
	}
	else if (strcmp("crand",newgen)==0)
	{
		current_gen = CRAND;
		return UNIFRNG_OK;
	}
	else
	{
		// Error !
		return UNIFRNG_ERROR;
	}
}

// Get the current generator
void unifrng_getcurrentgenname(char ** gen)
{
	unifrng_getcurrentgen(&current_gen);
	*gen = names_gen[current_gen];
}

// Get the dimension of internal state of the current generator
int unifrng_getcurrentgenstatedim()
{
	int dim;

	switch(current_gen)
	{
	case(MT) :
		dim = dim_state_mt;
		break;
	case(KISS) :
		dim = dim_state_kiss;
		break;
	case(CLCG4) :
		dim = dim_state_clcg4;
		break;
	case(CLCG2) :
		dim = dim_state_clcg2;
		break;
	case(URAND) :
		dim = dim_state_urand;
		break;
	case(FSULTRA) :
		dim = dim_state_fsultra;
		break;
	case(CRAND) :
		dim = dim_state_crand;
		break;
	};
	return dim;
}
// Get the internal state of the current generator
void unifrng_getcurrentgenstate(double * state)
{
	switch(current_gen)
	{
	case(MT) :
		unifrng_mt_get_state(state);
		break;
	case(KISS) :
		unifrng_kiss_get_state(state);
		break;
	case(CLCG4) :
		unifrng_clcg4_get_state_current(state);
		break;
	case(CLCG2) :
		unifrng_clcg2_get_state(state);
		break;
	case(URAND) :
		unifrng_urand_get_state(state);
		break;
	case(FSULTRA) :
		unifrng_fsultra_get_state(state);
		break;
	case(CRAND) :
		unifrng_crand_get_state(state);
		break;
	};
}



/*  pointers onto the generators func */
unsigned int (*gen[unifrng_NbGen])() = { 
	unifrng_mt_rand, 
	unifrng_kiss_rand,  
	unifrng_clcg4_with_gen, 
	unifrng_clcg2_rand, 
	unifrng_urand_rand, 
	unifrng_fsultra_rand, 
	unifrng_crand_rand
};

/* all the generators provide integers in [1, RngMaxInt] :        */
static unsigned long RngMaxInt[unifrng_NbGen] = {
	4294967295ul,  /* mt    */
	4294967295ul,  /* kiss  */
	2147483646ul,  /* clcg4 */
	2147483561ul,  /* clcg2 */
	2147483647ul,  /* urand */
	4294967295ul,  /* fsultra */
	32767          /* crand */
}; 
/* the factors (1/(RngMaxInt+1)) to get reals in (0,1) :           */
static double factor[unifrng_NbGen] = {
	2.3283064365386963e-10,  /* mt    */
	2.3283064365386963e-10,  /* kiss  */
	4.6566128752457969e-10,  /* clcg4 */
	4.6566130595601735e-10,  /* clcg2 */
	4.6566128730773926e-10,  /* urand */
	2.3283064365386963e-10,  /* fsultra*/
	3.0517578125e-5          /* crand */
}; 

// Returns a double randomly uniform in (0,1).
// This random number is generated according to the current RNG.
double unifrng_rand(void)
{
	double output;
	output = (double) gen[current_gen]() * factor[current_gen];
	return output;
}

// Returns a double, with a random integer value.
// This integer is uniform in [1,RngMaxInt]
// This is the direct output of the current RNG.
double unifrng_generateLargeInteger(void)
{
	double output;
	output = (double) gen[current_gen]();
	return output;
}

/* Generates a double with an integer value uniform in the interval [a,b], 
*  where  (i)  a and b are integers (stored in double)
*         (ii) b-a+1 <= RngMaxInt[current_gen]
*  The associated checkings are assumed to be done prior to the call 
*  to unifrng_generateInteger. */
double unifrng_generateIntegerInRange(double a, double b)
{
	/*  
	*  We use the classic method with a minor difference : to choose
	*  uniformly an int in [a,b] (ie d=b-a+1 numbers) with a generator
	*  which provides uniformly integers in [0,RngMaxInt] (ie m=RngMaxInt+1
	*  numbers) we do the Euclidian division :
	*    
	*    m = q d + r,   r in [0,d-1]
	*
	*  and accept only numbers l in [0, qd-1], then the output is k = a + (l mod d)
	*  (ie numbers falling in [qd , RngMaxInt] are rejected).
	*  The problem is that RngMaxInt is 2^32-1 for mt and unifrng_kiss_rand so that RngMaxInt+1 = 0
	*  with the 32 bits unsigned int arithmetic. So in place of rejected r
	*  numbers we reject r+1 by using RngMaxInt in place of m. The constraint is
	*  then that (b-a+1) <= RngMaxInt and if we doesn't want to deal we each generator
	*  we take (b-a+1) <= Min RngMaxInt =  2147483561 (clcg2)
	*/
	unsigned long k, d, qd;
	double output;

	d = (unsigned long)((b-a)+1);
	if ( d == 1)
		return a;

	qd = RngMaxInt[current_gen] - RngMaxInt[current_gen] % d;
	do
	{
		k = (unsigned long)unifrng_generateLargeInteger();
	}
	while ( k >= qd );

	output = a + (double)(k % d);
	return output;
}

// Initialize the message function to NULL.
void (* unigrng_messagefunction)(char * message) = NULL;

// Prints a message.
void unigrng_messageprint ( char * message ) 
{
	if (unigrng_messagefunction==NULL) {
		printf("%s: %s\n","unifrng",message);
	} 
	else 
	{
		unigrng_messagefunction(message);
	}
}

// Configures the message function
void unifrng_messagesetfunction ( void (* f)(char * message) ) 
{
	unigrng_messagefunction = f;
}
