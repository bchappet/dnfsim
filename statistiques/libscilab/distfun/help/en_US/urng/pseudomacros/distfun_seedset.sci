// Copyright (C) 2012 - Michael Baudin
// Copyright (C) Jean-Philippe Chancelier and Bruno Pincon
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt


function distfun_seedset(s)
    // Set the current state of the current random number generator
    //
    // Calling Sequence
    //   distfun_seedset(s)
    //   distfun_seedset(s1)
    //   distfun_seedset(s1,s2)
    //   distfun_seedset(s1,s2,s3,s4)
    //
    // Parameters
    // s: a m-by-1 matrix of doubles, integer value, the state of the current random number generator
    // s1,s2,s3,s4: a 1-by-1 matrix of doubles, integer value, the state 1, 2, 3 or 4 of the current random number generator
    //
    // Description
	//   Depending on the current random number generator, the distfun_seedset
	//   function may accept different calling sequences.
	//
	//   <itemizedlist>
	//   <listitem>
	//       <para>"mt"</para>
	//       <para>
    //       A simple calling sequence is :
	//       <screen>
	//       distfun_seedset(s1) 
	//       </screen>
	//       where s1 must be in [0,2^32[.
	//       </para>
	//       <para>
    //       Another calling sequence is :
	//       <screen>
	//       distfun_seedset(s) 
	//       </screen>
    //       where s is a 625-by-1 matrix of doubles, integer values. 
	//       The entry s(1) is an index and must be in [1,624], 
    //       the 624 last ones must be in [0,2^32[ (but must not be all zeros). 
	//       </para>
	//   </listitem>
	//   <listitem>
	//       <para>
    //       "kiss" :
	//       </para>
	//       <para>
    //       The calling sequence is :
	//       <screen>
	//       distfun_seedset(s1,s2,s3,s4) 
	//       </screen>
    //       where s1,s2,s3,s4 are four integers in [0,2^32[, or 
	//       <screen>
	//       distfun_seedset(s) 
	//       </screen>
    //       where s is a 4-by-1 matrix of doubles, integer value in [0,2^32[.
	//       </para>
	//   </listitem>
	//   <listitem>
	//       <para>
    //       "clcg2"
	//       </para>
	//       <para>
    //       The calling sequence is :
	//       <screen>
	//       distfun_seedset(s1,s2) 
	//       </screen>
    //       where s1 and s2 are two integers, s1 in [1,2147483562] and s2 in [1,2147483398], or 
	//       <screen>
	//       distfun_seedset(s) 
	//       </screen>
    //       where s is a 2-by-1 matrix of doubles, integer value, 
	//       with s(1), s(2) as previously..
	//       </para>
	//   </listitem>
	//   <listitem>
	//       <para>"clcg4"</para>
	//       <para>
    //       The calling sequence is :
	//       <screen>
	//       distfun_seedset(s1,s2,s3,s4) 
	//       </screen>
    //       where s1, s2, s3 and s4 are four integers with s1 in [1,2147483646], 
	//       s2 in [1,2147483542], s3 in [1,2147483422], 
    //       s4 in [1,2147483322], or 
	//       <screen>
	//       distfun_seedset(s) 
	//       </screen>
    //       where s is a 4-by-1 matrix of doubles, integer value, 
	//       with s(1), s(2), s(3) and s(4) as previously.
    //       Sets the initial state of virtual generator with index g=0 to s1,s2,s3,s4. 
    //       The initial seeds of the other generators g=1,2,...,101 are automatically set 
	//       accordingly in order to be synchronised. 
	//       </para>
	//   </listitem>
	//   <listitem>
	//       <para>"urand"</para>
	//       <para>
    //       The calling sequence is :
	//       <screen>
	//       distfun_seedset(s1) 
	//       </screen>
    //       where s1 is one integer in [0,2^31[.
	//       </para>
	//   </listitem>
	//   <listitem>
	//       <para>"fsultra"</para>
	//       <para>
    //       A simple calling sequence is :
	//       <screen>
	//       distfun_seedset(s1,s2) 
	//       </screen>
    //       where s1 and s2 are two integers in [0,2^32[, or 
	//       <screen>
	//       distfun_seedset(s) 
	//       </screen>
    //       where s is a 2-by-1 matrix of doubles, integer value, 
	//       with s(1), s(2) as previously.
	//       </para>
	//       <para>
    //       The complete calling sequence is :
	//       <screen>
	//       distfun_seedset(s) 
	//       </screen>
    //       where s is a vector of integers of dimension 40. 
	// 	     The first component s(1) is an index and 
    //       must be in [0,37], the 2d component s(2) is a flag (0 or 1), 
	//       the 3d component s(3) an integer in [1,2^32[ 
    //       and the 37 others integers are in [0,2^32[. 
	//       </para>
	//   </listitem>
	//   <listitem>
	//       <para>"crand"</para>
	//       <para>
    //       The calling sequence is :
	//       <screen>
	//       distfun_seedset(s1) 
	//       </screen>
    //       where s1 is one integer in [0,2^15[.
	//       </para>
	//   </listitem>
	//   </itemizedlist>
	//
	//   If the arguments s, s1, s2, s3 or s4 have a fractional part (i.e. do not 
	//   have an integer value), or if there are not in the range of 
	//   possible values, then an error is generated.
	//
  	//   Setting the seed may be useful to get reproductible 
	//   or less reproductible random numbers.
	//
	// The pseudo random number generators are based on deterministic sequences. 
	// In order to get reproducible simulations, when we start Scilab, the initial seed of the 
	// generator is constant, such that the sequence will remain the same from a session 
	// to the other. 
	// Hence, by default, the first numbers produced by distfun are always the same.
	//
	// In some situations, we may want to initialize the seed of the generator in order 
	// to produce less predictible numbers. 
	// In this case, we may initialize the seed with the output of the getdate function:
	//
    // <screen>
	// n=getdate("s");
	// distfun_seedset(n);
    // </screen>
    //
    // Examples
	// distfun_seedset(1)
	// distfun_normrnd(12,7,5,3)
	// // Set the seed to the same value...
	// distfun_seedset(1)
	// // ... we get the same output :
	// distfun_normrnd(12,7,5,3)
    //
    // Authors
    // Copyright (C) 2012 - Michael Baudin
    // Copyright (C) Jean-Philippe Chancelier and Bruno Pincon

endfunction

