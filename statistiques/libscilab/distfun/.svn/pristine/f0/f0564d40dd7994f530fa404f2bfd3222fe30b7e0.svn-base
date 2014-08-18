/* 
* 
* Copyright (C) 2012 - Michael Baudin
* Copyright (C) 2010 - DIGITEO - Michael Baudin
* Copyright (C) 2004 - Bruno Pincon
* Copyright (C) 1992 - Arif Zaman
* Copyright (C) 1992 - George Marsaglia
* 
FSU - ULTRA	The greatest random number generator that ever was
or ever will be.  Way beyond Super-Duper.
(Just kidding, but we think its a good one.)

Authors:	Arif Zaman (arif@stat.fsu.edu) and
George Marsaglia (geo@stat.fsu.edu).

Date:		27 May 1992

Version:	1.05

Copyright:	To obtain permission to incorporate this program into
any commercial product, please contact the authors at
the e-mail address given above or at

Department of Statistics and
Supercomputer Computations Research Institute
Florida State University
Tallahassee, FL 32306.

See Also:	README		for a brief description
ULTRA.DOC	for a detailed description

-----------------------------------------------------------------------
*/ 
/*
File: ULTRA.C

This is the ULTRA random number generator written entirely in C.

This may serve as a model for an assembler version of this routine.
The programmer should avoid simply duplicating and instead use the
usual assembler features to increase the speed of this routine.

Especially the subroutine SWB should be replaced by the one
machine instruction (usually called subtract-with-borrow) that
is available in almost every hardware.

For people not familiar with 8086 assembler, it may help to
consult this when reading the assembler code. This program should
be a dropin replacement for the assembler versions, but is about
half as fast.
*/

/* Slight modifications by Bruno Pincon (4 december 2004) for inclusion 
in scilab and nsp:

1/ in scilab we use only i32bit output ( renamed here fsultra )
and  I have deleted the others;

2/ only one array is now used (swbseed which is renamed
swb_state) and the xor with the linear congruential generator
is done only just before the output.

3/ add a var is_init (to say if the generator is initialised)

4/ add routine to set/get the state

*/
/*
**************************************************************
*
*     MB, 20/09/2012
*     Modified so that 0 can never be produced.
*     This explains the do-while block.
*     Also converted the macros into a proper C code.
*     http://forge.scilab.org/index.php/p/distfun/issues/913/
*
**************************************************************
*/
/* to use floor    */
#include <math.h>             
#include <stdio.h>
#include "unifrng.h"
#include "unifrng_private.h"

#define N  37           /* size of table        */
#define N2 24           /* The shorter lag      */

static int is_init=0;  
static int swb_state[N];          /* state of the swb generator */
static int swb_index=N;            /* an index on the swb state */
static int swb_flag;		   /* the carry flag for the SWB generator */
static unsigned int cong_state;   /* state of the congruential generator */

/* for this generator the state seems completly defined by:
swb_state, swb_index, swb_flag (which define the state of the swb generator)
cong_state (which defines the state of the congruential generator)
*/

/* those are the default for the simple initialisation routine */
static  double DEFAULT_SEED1= 1234567.0, DEFAULT_SEED2=7654321.0; 

/* SWB is the subtract-with-borrow operation which should be one line
in assembler code. This should be done by using the hardware s-w-b
operation in the SWBfill routine (renamed advance_state_swb here).

What has been done here is to look at the msb of x, y and z=x-y-c.
Using these three bits, one can determine if a borrow bit is needed
or not according to the following table:

msbz=0  msby=0  msby=1          msbz=1  msby=0  msby=1

msbx=0  0       1               msbx=0  1       1
msbx=1  0       0               msbx=1  0       1

PS: note that the definition is very carefully written because the
calls to SWB have y and z as the same memory location, so y must
be tested before z is assigned a value.
*/
#define SWB(c,x,y,z) c = (y<0) ? (((z=x-y-c) < 0) || (x>=0)) : (((z=x-y-c) < 0) && (x>=0));

static void advance_state_swb()
{ 
	int i;
	/*
	*  The following are the heart of the system and should be
	*  written is assembler to be as fast as possible. It may even make sense
	*  to unravel the loop and simply wirte 37 consecutive SWB operations!
	*/
	for (i=0;  i<N2; i++) 
	{
		SWB(swb_flag,swb_state[i+N-N2],swb_state[i],swb_state[i]);
	}
	for (i=N2; i<N;  i++) 
	{
		SWB(swb_flag,swb_state[i  -N2],swb_state[i],swb_state[i]);
	}
	swb_index = 0;
}

/* unifrng_fsultra_set_state_simple initializes the state from 2 integers  

it defines the constants and fills the swb_state array one bit at
a time by taking the leading bit of the xor of a shift register
and a congruential sequence. The same congruential generator continues
to be used as a mixing generator for the Subtract-with-borrow generator
to produce the `ultra' random numbers

Since this is called just once, speed doesn't matter much and it might
be fine to leave this subroutine coded just as it is.

PS:	there are quick and easy ways to fill this, but since random number
generators are really "randomness amplifiers", it is important to
start off on the right foot. This is why we take such care here.
*/

int unifrng_fsultra_set_state_simple(double s1, double s2)
{ 
	char buffer [1024];
	unsigned int shrgx, tidbits=0;
	int i, j;

	if (    (s1 == floor(s1) && 0.0 <= s1 && s1 <= 4294967295.0)
		&& (s2 == floor(s2) && 0.0 <= s2 && s2 <= 4294967295.0) )
	{
		cong_state = ((unsigned int) s1)*2 + 1;
		shrgx = (unsigned int) s2;
		for ( i=0 ; i<N ; i++)
		{
			for ( j=32 ; j>0 ; j--)
			{ 
				cong_state = cong_state * 69069;
				shrgx = shrgx ^ (shrgx >> 15);
				shrgx = shrgx ^ (shrgx << 17);
				tidbits = (tidbits>>1) | (0x80000000 & (cong_state^shrgx));
			}
			swb_state[i] = tidbits;
		}
		swb_index = 0;
		swb_flag = 0;
		advance_state_swb();  /* pour retrouver la même séquence que ds scilab V3.0 */
		is_init = 1;
		return UNIFRNG_OK;
	}
	else
	{
		sprintf (buffer, "%s: Bad seed (%.17e,%.17e) for fsultra, must be integers in [0, 2^32-1]\n","unifrng_fsultra_set_state_simple",s1,s2);
		unigrng_messageprint(buffer);
		return UNIFRNG_ERROR;
	}
}

int unifrng_fsultra_set_state(double *s)
{ 
	double t;
	int i;
	char buffer [1024];

	t = s[0];
	if ( floor(t) != t || t < 0.0  ||  t > (double) N)
	{
		sprintf(buffer,"%s: The first component of the fsultra state is equal to %.17e, must be an int in [0, %d]\n","unifrng_fsultra_set_state",t,N);
		unigrng_messageprint(buffer);
		return UNIFRNG_ERROR;
	}
	swb_index = (int) t;

	t = s[1];
	if ( t != 0.0  &&  t != 1.0)
	{
		sprintf(buffer,"%s: The second component of the fsultra state is equal to %.17e, but must be 0 or 1\n","unifrng_fsultra_set_state",t);
		unigrng_messageprint(buffer);
		return UNIFRNG_ERROR;
	}
	swb_flag = (int) t;

	t = s[2];
	if ( floor(t) != t  ||  t <= 0 ||  t > 4294967295.0 )
	{
		sprintf(buffer,"%s: The third component of the fsultra state is equal to %.17e, must be an int in [1, 2^32-1]\n","unifrng_fsultra_set_state",t);
		unigrng_messageprint(buffer);
		return UNIFRNG_ERROR;
	}
	cong_state = (unsigned int) t;

	for (i = 0 ; i < N ; i++)
	{
		t = s[i+3];
		if ( floor(t) != t  ||  t < 0 ||  t > 4294967295.0 )
		{
			sprintf(buffer,"%s: The %d-th component of the fsultra state is equal to %.17e, must be an int in [0, 2^32-1]\n","unifrng_fsultra_set_state",i+4,t);
			unigrng_messageprint(buffer);
			return UNIFRNG_ERROR;
		}
		swb_state[i] = (int) (((unsigned int) t) & 0xffffffff);
	}

	is_init = 1;
	return UNIFRNG_OK;
}


/*  to return the state at the scilab level  */
void unifrng_fsultra_get_state(double s[])
{
	int i;

	if ( ! is_init )
	{
		unifrng_fsultra_set_state_simple(DEFAULT_SEED1, DEFAULT_SEED2);
	}

	s[0] = (double)  swb_index;
	s[1] = (double)  swb_flag;
	s[2] = (double)  cong_state;
	for (i = 0 ; i < N ; i++) 
	{
		s[i+3] = (double) (unsigned int) swb_state[i];
	}
}

unsigned int unifrng_fsultra_rand()
{
	unsigned int s;

	do
	{
		if (swb_index >= N)  
		{ 
			/* generate N words at one time */
			if ( ! is_init )
			{
				unifrng_fsultra_set_state_simple(DEFAULT_SEED1, DEFAULT_SEED2);
			}
			else
			{
				advance_state_swb();
			}
		}
		cong_state = cong_state * 69069;
		s = swb_state[swb_index++] ^ cong_state;
	}
	while (s==0);

	return (s);
}
