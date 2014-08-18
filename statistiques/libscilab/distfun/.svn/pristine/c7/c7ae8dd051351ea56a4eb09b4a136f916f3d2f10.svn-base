
/*
 * Copyright (C) 2012-2013 - Michael Baudin
 * Copyright (C) 2011 - DIGITEO - Michael Baudin
 * Copyright (C) 2008 - INRIA
 *
 * This file must be used under the terms of the CeCILL.
 * This source file is licensed as described in the file COPYING, which
 * you should have received as part of this distribution.  The terms
 * are also available at
 * http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt
 *
 */
 
#ifndef _UNIFRNG_H_
#define _UNIFRNG_H_

#ifdef _MSC_VER
	#if LIBUNIFRNG_EXPORTS 
		#define UNIFRNG_IMPORTEXPORT __declspec (dllexport)
	#else
		#define UNIFRNG_IMPORTEXPORT __declspec (dllimport)
	#endif
#else
	#define UNIFRNG_IMPORTEXPORT
#endif

#undef __BEGIN_DECLS
#undef __END_DECLS
#ifdef __cplusplus
# define __BEGIN_DECLS extern "C" {
# define __END_DECLS }
#else
# define __BEGIN_DECLS /* empty */
# define __END_DECLS /* empty */
#endif

__BEGIN_DECLS

// This is the status of a function which performs correctly.
static int UNIFRNG_OK = 0;

// The state of a function which generates an error.
static int UNIFRNG_ERROR = 1;

// Configure the function which prints messages.
// This function must be called before calling any 
// function.
void unifrng_messagesetfunction ( void (* f)(char * message) );

/* the available generators : */
enum {MT, KISS, CLCG4, CLCG2, URAND, FSULTRA, CRAND};
/* Number of Generators in Scilab */

// Get the current generator.
void unifrng_getcurrentgen(int * cgen);

// Set the current generator.
//   Returns UNIFRNG_ERROR if an error is detected.
//   Returns UNIFRNG_OK if OK.
int unifrng_setcurrentgen(int newgen);

// Get the current generator name.
void unifrng_getcurrentgenname(char ** gen);

// Set the current generator name.
//   Returns UNIFRNG_ERROR if an error is detected.
//   Returns UNIFRNG_OK if OK.
int unifrng_setcurrentgenname(char * newgen);

// Get the dimension of the internal state of the current generator
int unifrng_getcurrentgenstatedim();
// Get the internal state of the current generator
void unifrng_getcurrentgenstate(double * state);

// Returns a real uniform in (0,1)
double unifrng_rand(void);

// Returns a double containing an integer uniform in [a,b]
double unifrng_generateIntegerInRange(double a, double b);

// Returns the basic output of the current generator : 
// random integers following a uniform distribution over :
//   * [1, 2^32 - 1] for mt, kiss and fsultra
//   * [1, 2147483561] for clcg2
//   * [1, 2^31 - 2] for clcg4
//   * [1, 2^31 - 1] for urand.
//   * [1, 2^15 - 1] for urand.
double unifrng_generateLargeInteger(void);

/*     Uses a phrase (character string) to generate two seeds for the RGN */
/*     random number generator. */
void unifrng_phraseToSeed(char *phrase, int *phrasel, int *seed1, int *seed2);

/////////////////////////////////////////////////////////
//
// mt
//

/* header for the Mersenne Twister RNG */
unsigned int unifrng_mt_rand(void);

// Set the seed of the generator with one single double.
int unifrng_mt_set_state_simple(double s);

// Set the seed of the mt generator.
// Returns UNIFRNG_OK if the seed can be set.
// Returns UNIFRNG_ERROR in case of error.
int unifrng_mt_set_state(double seed_array[]);

// Returns the current seed of the random number generator.
void unifrng_mt_get_state(double state[]);

/////////////////////////////////////////////////////////
//
// kiss
//

/* header for kiss RNG */
unsigned int unifrng_kiss_rand(void);

// Set the seed of the kiss generator.
// Returns UNIFRNG_OK if the seed can be set.
// Returns UNIFRNG_ERROR in case of error.
int unifrng_kiss_set_state(double g1, double g2, double g3, double g4);

// Returns the current seed of the random number generator.
void unifrng_kiss_get_state(double g[]);

/////////////////////////////////////////////////////////
//
// clcg2
//

/* header for clcg2 RNG */

// Returns a uniform random number in [0,1)
unsigned int unifrng_clcg2_rand(void);

// Set the seed of the clcg2 generator.
// Returns UNIFRNG_OK if the seed can be set.
// Returns UNIFRNG_ERROR in case of error.
// TODO : turn g1 and g2 into integers.
int unifrng_clcg2_set_state(double g1, double g2);

// Get the state of the clcg2 generator.
void unifrng_clcg2_get_state(double g[]);

/////////////////////////////////////////////////////////
//
// urand
//

/* header for urand RNG */

// Returns a uniform random number in [0,1)
unsigned int unifrng_urand_rand(void);

// Set the seed of the urand generator.
// Returns UNIFRNG_OK if the seed can be set.
// Returns UNIFRNG_ERROR in case of error.
// TODO : turn g into an integer.
int unifrng_urand_set_state(double g);

// Get the current seed.
void unifrng_urand_get_state(double g[]);

/////////////////////////////////////////////////////////
//
// fsultra
//

/* header for fsultra RNG */
unsigned int unifrng_fsultra_rand(void);

// Set the seed of the fsultra generator.
// Returns UNIFRNG_OK if the seed can be set.
// Returns UNIFRNG_ERROR in case of error.
// TODO : turn g into an integer.
int unifrng_fsultra_set_state(double g[]);

// Set the seed of fsultra with 2 doubles.
// TODO : turn g1 and g2 into integers.
int unifrng_fsultra_set_state_simple(double g1,double g2);

// Returns the current seed of fsultra.
void unifrng_fsultra_get_state(double g[]);

/////////////////////////////////////////////////////////
//
// clcg4
//

/* header for clcg4 */
#define Maxgen_clcg4  100
unsigned int unifrng_clcg4_rand(int g);
unsigned int unifrng_clcg4_with_gen(void);

// Set the seed of the clcg4 generator.
// Returns UNIFRNG_OK if the seed can be set.
// Returns UNIFRNG_ERROR in case of error.
// TODO : turn s* into an integer.
int unifrng_clcg4_set_seed(int g, double s0, double s1, double s2, double s3);

// Returns the current seed of the virtual generator #g.
void unifrng_clcg4_get_state(int g, double s[4]);

// Returns the current seed of the current virtual generator.
void unifrng_clcg4_get_state_current(double s[4]);

typedef  enum {InitialSeed, LastSeed, NewSeed}  unifrng_clcg4_SeedType;
void unifrng_clcg4_init_generator(int g, unifrng_clcg4_SeedType Where);
int unifrng_clcg4_set_initial_seed(double s0, double s1, double s2, double s3);
void unifrng_clcg4_init_currentgenerator(unifrng_clcg4_SeedType Where);
void unifrng_clcg4_set_current_virtual(int newvirtualgen);
int unifrng_clcg4_get_current_virtual();
int unifrng_clcg4_set_current_seed(double s0, double s1, double s2, double s3);


/////////////////////////////////////////////////////////
//
// crand
//

/* header for crand RNG */
unsigned int unifrng_crand_rand(void);

// Set the seed of the crand generator.
// Returns UNIFRNG_OK if the seed can be set.
// Returns UNIFRNG_ERROR in case of error.
int unifrng_crand_set_state(double g);

// Returns the current seed of the random number generator.
void unifrng_crand_get_state(double g[]);

__END_DECLS

#endif /** _UNIFRNG_H_   **/




