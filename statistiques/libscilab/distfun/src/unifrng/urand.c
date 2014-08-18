/* 
*
* Copyright (C) 2012 - Michael Baudin
* Copyright (C) 2010 - DIGITEO - Michael Baudin
* Copyright (C) 2004 - Bruno Pincon
* Copyright (C) 1973 - CLEVE B. MOLER
* Copyright (C) 1973 - MICHAEL A. MALCOLM
*
*   PURPOSE
*      Generates random numbers uniform in (0,1)
*      This is the URAND generator: 
*          s <- (a*s + c) mod m
*      with :
*             m = 2^{31} 
*             a = 843314861
*             c = 453816693
*      
*      s must be in [0,m-1] when user changes seed with unifrng_urand_set_state
*      period = m
*
*   NOTES
*      a/ Rewritten (in C) so as to output integers like all the others 
*         generators (and also to have the same manner to set/get the state)
*      b/ Unsigned int arithmetic must be the classic 32 bits unsigned
*         arithmetic (ie also is exact modulo 2^32).
******************************************************************
*     MB, 20/09/2012
*      c/ Modified so that 0 can never be produced.
*         This explains the do-while block.
*         http://forge.scilab.org/index.php/p/distfun/issues/913/
******************************************************************
* 
*   URAND, A UNIVERSAL RANDOM NUMBER GENERATOR 
*   BY, MICHAEL A. MALCOLM, CLEVE B. MOLER, 
*   STAN-CS-73-334, JANUARY 1973, 
*   COMPUTER SCIENCE  DEPARTMENT, 
*   School of Humanities and Sciences, STANFORD UNIVERSITY, 
*   ftp://reports.stanford.edu/pub/cstr/reports/cs/tr/73/334/CS-TR-73-334.pdf
* 
* 
*/


/* to use floor    */
#include <math.h>             
#include <stdio.h>
#include "unifrng.h"
#include "unifrng_private.h"

static unsigned int s = 0;

unsigned int unifrng_urand_rand()
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

int unifrng_urand_set_state(double g)
{
	char buffer [1024];
	if ( g == floor(g) &&  0 <= g && g <= 2147483647 )
	{
		s = (unsigned int) g;
		return UNIFRNG_OK;
	}
	else
	{
		sprintf (buffer, "%s: Bad seed %.17e for urand, must be an int in [0, 2147483647]\n","unifrng_urand_set_state",g);
		unigrng_messageprint(buffer);
		return UNIFRNG_ERROR;
	}
}

void unifrng_urand_get_state(double g[])
{
	g[0] = (double) s;
}
