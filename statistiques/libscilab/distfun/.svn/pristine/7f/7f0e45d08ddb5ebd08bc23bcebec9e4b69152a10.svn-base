/*
* Copyright (C) 2012 - Michael Baudin
*
* This file must be used under the terms of the CeCILL.
* This source file is licensed as described in the file COPYING, which
* you should have received as part of this distribution.  The terms
* are also available at
* http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt
*
*/

// A small test driver for unifrng.

#include <stdio.h>
#include "unifrng.h"

void mymessagefun(char * message)
{
	printf("MyTest: %s\n",message);
	return;
}

int main( int argc, const char* argv[] )
{
	int i;
	double R;
	unifrng_messagesetfunction(mymessagefun);
	unifrng_setcurrentgenname("mt");
	for (i=0;i<10;i++)
	{
		R=unifrng_rand();
		printf("%f\n",R);
	}
	return 0;
}
