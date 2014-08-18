/*  
* 
* Copyright (C) 2012 - Michael Baudin
* Copyright (C) 2010 - DIGITEO - Michael Baudin
* Copyright (C) 2004 - Bruno Pincon
* Copyright (C) Luc Devroye
* Copyright (C) Pierre Lecuyer
* 
*  PURPOSE
*     uniform random number generator developed by Pierre 
*     Lecuyer based on a clever and tested combination of 
*     two linear congruential sequences
*
*        s1 <- a1*s1 mod m1 ,  a1 = 40014, m1 = 2147483563
*        s2 <- a2*s2 mod m2 ,  a2 = 40692, m2 = 2147483399
*
*        output <-  s1-s2 mod (m1 - 1)  
*
*        so output is in [0, 2147483561], period about 2.3 10^18
*
*        The state is given by (s1, s2). In case of a user
*        modification of the state we must have :
* 
*              s1 in [1, m1-1]
*              s2 in [1, m2-1]
*
*  ORIGIN
*     The basic code is provided at the Luc Devroye 's home page.
*     Modifications by Bruno Pincon (in particular added routines 
*     to set and get the state, and modify the generator to get 
*     exactly  s1-s2 mod (m1 - 1) for "coherence" with the others 
*     generators : provides numbers in [0, MaxRngInt(generator)] 
*     (see NOTE some lines after)
*
******************************************************************
*     MB, 20/09/2012
*     Modified so that 0 can never be produced.
*     This explains the do-while block.
*     http://forge.scilab.org/index.php/p/distfun/issues/913/
*
******************************************************************
*/

#include <math.h>
#include <stdio.h>
#include "unifrng.h"
#include "unifrng_private.h"

/* initial default state (seeds) : */
static int s1 = 1234567890 ;
static int s2 = 123456789  ;

unsigned int unifrng_clcg2_rand()
{
	register int k,z;

	do
	{
		/*  s1 = a1*s1 mod m1  (Schrage 's method)  */
		k= s1 /53668;
		s1 =40014*(s1%53668)-k*12211;
		if (s1 < 0) 
		{
			s1 += 2147483563;
		}

		/*  s2 = a2*s2 mod m2  (Schrage 's method)  */
		k=s2/52774;
		s2=40692*(s2%52774)-k*3791;
		if (s2 < 0) 
		{
			s2 += 2147483399;
		}

		/* final step : z = s1-s2 mod m1-1  */
		z = s1 - s2;  /* here z is in [2-m2,m1-2] */
		if (z < 0) 
		{
			z += 2147483562;
		}
	} while(z==0);

	/* NOTE : in the  original implementation the final test is :
	*     if (z < 1) z += 2147483562;
	* 
	*   which is not exactly  z = s1-s2 mod (m1 - 1)
	*
	*   This is also why it is different from the version used by
	*   randlib.
	*/

	return( (unsigned int) z );
}

int unifrng_clcg2_set_state(double g1, double g2)
{
	char buffer [1024];
	if ( g1 == floor(g1) && g2 == floor(g2)  && 
		1 <= g1 && g1 <= 2147483562    && 
		1 <= g2 && g2 <= 2147483398 )
	{
		s1 = (int) g1;
		s2 = (int) g2;
		return UNIFRNG_OK;
	}
	else
	{
		sprintf(buffer,"%s: Bad seeds (%.17e,%.17e) for clcg2, must be integers with  s1 in [1, 2147483562] and s2 in [1, 2147483398]\n","unifrng_clcg2_set_state",g1,g2);
		unigrng_messageprint(buffer);
		return UNIFRNG_ERROR;
	}
}

void unifrng_clcg2_get_state(double g[])
{
	g[0] = (double) s1;
	g[1] = (double) s2;
}

