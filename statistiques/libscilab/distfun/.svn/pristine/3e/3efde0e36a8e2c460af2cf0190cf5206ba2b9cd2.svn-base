/* 
*
* Copyright (C) 2013 - Michael Baudin
*
*   PURPOSE
*      Uses the pseudo-random number generator from the C language.
*      Uses a generator with an unsigned integer in the range 
*      [1,32767]
*
*/

#include <math.h>             
#include <stdio.h>
#include <stdlib.h>
#include "unifrng.h"
#include "unifrng_private.h"

static unsigned int s = 0;

unsigned int unifrng_crand_rand()
{
	do
	{
		s=rand();
	} while(s==0);
	return s;
}

int unifrng_crand_set_state(double g)
{
	char buffer [1024];
	if ( g == floor(g) &&  0 <= g && g <= 32767 )
	{
		s = (unsigned int) g;
		return UNIFRNG_OK;
	}
	else
	{
		sprintf (buffer, "%s: Bad seed %.17e for crand, must be an int in [0, 32767]\n","unifrng_crand_set_state",g);
		unigrng_messageprint(buffer);
		return UNIFRNG_ERROR;
	}
}

void unifrng_crand_get_state(double g[])
{
	g[0] = (double) s;
}
