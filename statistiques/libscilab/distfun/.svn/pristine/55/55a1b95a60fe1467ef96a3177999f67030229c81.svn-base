/* phrtsd.f -- translated by f2c (version 20100827).
with manual tuning by Michael Baudin (2012)
*/

#include "unifrng.h"
#include "unifrng_private.h"

/* Subroutine */ void unifrng_phraseToSeed(char *phrase, int *phraseLength, int *seed1, 
	int *seed2)
{
	/* Initialized data */

	static int shift[5] = { 1,64,4096,262144,16777216 };

	/* System generated locals */
	int i__1;

	/* Local variables */
	static int i__, j, ichr, lphr, values[5];

	/* ********************************************************************** */

	/*     SUBROUTINE PHRTSD( PHRASE, SEED1, SEED2 ) */
	/*               PHRase To SeeDs */


	/*                              Function */


	/*     Uses a phrase (character string) to generate two seeds for the RGN */
	/*     random number generator. */


	/*                              Arguments */


	/*     PHRASE --> Phrase to be used for random number generation */
	/*                         CHARACTER*(*) PHRASE */

	/*     SEED1 <-- First seed for RGN generator */
	/*                         int SEED1 */

	/*     SEED2 <-- Second seed for RGN generator */
	/*                         int SEED2 */


	/*                              Note */


	/*     Trailing blanks are eliminated before the seeds are generated. */

	/*     Generated seed values will fall in the range 1..2^30 */
	/*     (1..1,073,741,824) */

	/* ********************************************************************** */

	*seed1 = 1234567890;
	*seed2 = 123456789;
	lphr = *phraseLength;
	if (lphr < 1) {
		return;
	}
	i__1 = lphr;
	for (i__ = 1; i__ <= i__1; ++i__) {
		ichr = i_indx("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0"
			"123456789!@#$%^&*()_+[];:'\"<>?,./", phrase + (i__ - 1), (
			long int)86, (long int)1) % 64;
		if (ichr == 0) {
			ichr = 63;
		}
		for (j = 1; j <= 5; ++j) {
			values[j - 1] = ichr - j;
			if (values[j - 1] < 1) {
				values[j - 1] += 63;
			}
		}
		for (j = 1; j <= 5; ++j) {
			*seed1 = (*seed1 + shift[j - 1] * values[j - 1]) % 1073741824;
			*seed2 = (*seed2 + shift[j - 1] * values[6 - j - 1]) % 1073741824;
		}
	}
	return;
} /* phrtsd_ */

