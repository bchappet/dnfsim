/* 
* 
* Copyright (C) 2012 - Michael Baudin
* Copyright (C) 2010 - DIGITEO - Michael Baudin
* Copyright (C) 2004 - Bruno Pincon
* Copyright (C) 1999 - G. Marsaglia
* 
*   PURPOSE
*      the kiss generator of G. Marsaglia
*      generate random integers (uint) in [0, 2^32 - 1]
*      the state is given by 4 integers (z, w, jsr, jcong)
*
*   NOTES
*      The code was given by G. Marsaglia at the end of  a
*      thread  concerning  RNG  in C in several newsgroups
*      (whom sci.math.num-analysis) "My offer of RNG's for
*      C  was an invitation to dance..."
*
*      Slight modifications by Bruno Pincon for inclusion in
*      Scilab (added set/get state routines)
*
*      kiss is made of combinaison of severals  others but  
*      they  are not interfaced at the scilab level.

   "The KISS generator, (Keep It Simple Stupid), is
   designed to combine the two multiply-with-carry
   generators in MWC with the 3-shift register SHR3 and
   the congruential generator CONG, using addition and
   exclusive-or. Period about 2^123.
   It is one of my favorite generators."

   "The  MWC generator concatenates two 16-bit multiply-
   with-carry generators, x(n)=36969x(n-1)+carry,
   y(n)=18000y(n-1)+carry  mod 2^16, has period about
   2^60 and seems to pass all tests of randomness. A
   favorite stand-alone generator---faster than KISS,
   which contains it."

   "SHR3 is a 3-shift-register generator with period
   2^32-1. It uses y(n)=y(n-1)(I+L^17)(I+R^13)(I+L^5),
   with the y's viewed as binary vectors, L the 32x32
   binary matrix that shifts a vector left 1, and R its
   transpose.  SHR3 seems to pass all except those
   related to the binary rank test, since 32 successive
   values, as binary vectors, must be linearly
   independent, while 32 successive truly random 32-bit
   integers, viewed as binary vectors, will be linearly
   independent only about 29% of the time."

   "CONG is a congruential generator with the widely used 69069
   multiplier: x(n)=69069x(n-1)+1234567.  It has period
   2^32. The leading half of its 32 bits seem to pass
   tests, but bits in the last half are too regular."
   
*
*      Need that it is assumed that the 
*         unsigned int arithmetic is the classic 32 bits 
*         unsigned arithmetic modulo 2^32 (ie all is exact
*         modulo 2^32) 
*
* 	License: This code is released under public domain.
* 	(Compatible with CeCILL)
*
**************************************************************
*
*     MB, 20/09/2012
*     Modified so that 0 can never be produced.
*     This explains the do-while block.
*     Also converted the macros into a proper C code.
*     http://forge.scilab.org/index.php/p/distfun/issues/913/
*
**************************************************************
*
*/

/* to use floor    */
#include <math.h>             
#include <stdio.h>
#include "unifrng.h"
#include "unifrng_private.h"


/*  the kiss 's state  (any int in [0,2^32-1] are OK ?) */
static unsigned int z=362436069, w=521288629, jsr=123456789, jcong=380116160;

unsigned int unifrng_kiss_rand()
{
	unsigned int zkiss, zmwc;
	do 
	{
		z=36969*(z&65535)+(z>>16);
		w=18000*(w&65535)+(w>>16);
		// MWC
		zmwc = (z<<16)+w;
		// CONG
		jcong=69069*jcong+1234567;
		// SHR3
		jsr^=(jsr<<17);
		jsr^=(jsr>>13);
		jsr^=(jsr<<5);
		// KISS
		zkiss = (zmwc^jcong)+jsr;
	}
	while (zkiss==0);

	return ( zkiss );
}

int unifrng_kiss_set_state(double g1, double g2, double g3, double g4)
{
	char buffer [1024];
	if (g1 == floor(g1) && g2 == floor(g2) && 
		g3 == floor(g3) && g4 == floor(g4) &&  
		0.0 <= g1 && g1 <= 4294967295.0 &&
		0.0 <= g2 && g2 <= 4294967295.0 &&
		0.0 <= g3 && g3 <= 4294967295.0 &&
		0.0 <= g4 && g4 <= 4294967295.0 )
	{
		z = (unsigned int) g1;
		w = (unsigned int) g2;
		jsr = (unsigned int) g3;
		jcong = (unsigned int) g4;
		return UNIFRNG_OK;
	}
	else
	{
		sprintf (buffer, "%s: Bad seeds (%.17e,%.17e,%.17e,%.17e) for kiss, must be integers in [0,2^32-1]\n","unifrng_kiss_set_state",g1,g2,g3,g4);
		unigrng_messageprint(buffer);
		return UNIFRNG_ERROR;
	}
}

void unifrng_kiss_get_state(double g[])
{
	g[0] = (double) z;
	g[1] = (double) w;
	g[2] = (double) jsr;
	g[3] = (double) jcong;
}

