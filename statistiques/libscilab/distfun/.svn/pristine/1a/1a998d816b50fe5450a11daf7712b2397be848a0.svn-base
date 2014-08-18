/* From f2c
with manual tuning by Michael Baudin (2012)
*/

#include "unifrng.h"
#include "unifrng_private.h"

int i_indx(char *a, char *b, long  la, long  lb)
{
	long i, n;
	char *s, *t, *bend;

	n = la - lb + 1;
	bend = b + lb;

	for(i = 0 ; i < n ; ++i)
	{
		s = a + i;
		t = b;
		while(t < bend)
			if(*s++ != *t++)
			{
				goto no;
			}
		return(i+1);
no: ;
	}
	return(0);
}