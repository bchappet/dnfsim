// Copyright (C) 2012-2013 - Michael Baudin
// Copyright (C) Jean-Philippe Chancelier and Bruno Pincon
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt


function F = distfun_genset ( gen )
    // Set the current random number generator
    //
    // Calling Sequence
    //   distfun_genset ( gen )
    //
    // Parameters
    // gen: a 1-by-1 matrix of strings, the current random number generator
    //
    // Description
    //   Sets the current random number generator to gen, a string in the set : 
    //   "mt", "kiss", "clcg2", "clcg4", "urand", "fsultra", "crand". 
    //
    // Examples
	// // Set the CLCG2 generator
    // distfun_genset ( "clcg2" );
	// // Now, generate normal random numbers from clcg2
	// // with mean mu=12 and sigma=7
	// distfun_normrnd(12,7,5,3)
	// // Set the KISS generator
    // distfun_genset ( "kiss" );
	// // Now, generate normal random numbers from kiss
	// // with mean mu=12 and sigma=7
	// distfun_normrnd(12,7,5,3)
	// // Return to the default generator
    // distfun_genset ( "mt" );
    //
    // Authors
    // Copyright (C) 2012-2013 - Michael Baudin
    // Copyright (C) Jean-Philippe Chancelier and Bruno Pincon

endfunction

