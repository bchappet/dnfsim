// Copyright (C) 2012 - Michael Baudin
// Copyright (C) Jean-Philippe Chancelier and Bruno Pincon
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt


function gen = distfun_genget ( )
    // Get the current random number generator
    //
    // Calling Sequence
    //   gen = distfun_genget ( )
    //
    // Parameters
    // gen: a 1-by-1 matrix of strings, the current random number generator
    //
    // Description
    //   Gets the current random number generator. 
    //
    // Examples
	// // By default, "mt"
    // gen = distfun_genget ( ) 
	// // Set the CLCG2 generator
    // distfun_genset ( "clcg2" );
    // gen = distfun_genget ( )
	// // Return to the default generator
    // distfun_genset ( "mt" );
    //
    // Authors
    // Copyright (C) 2012 - Michael Baudin
    // Copyright (C) Jean-Philippe Chancelier and Bruno Pincon

endfunction

