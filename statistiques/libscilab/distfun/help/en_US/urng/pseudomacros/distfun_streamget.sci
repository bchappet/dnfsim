// Copyright (C) 2012 - Michael Baudin
// Copyright (C) Bruno Pincon
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt


function g = distfun_streamget ( )
    // Get the current stream
    //
    // Calling Sequence
    //   g = distfun_streamget ( )
    //
    // Parameters
    // g: a 1-by-1 matrix of doubles, integer value, the current stream
    //
    // Description
    //   Gets the current stream g 
    //   in the set 0,1,2,...,100.
	//   The default stream is g=0.
    //
    // Examples
	// distfun_genset ( "clcg4" );
    // g = distfun_streamget ( )
	// // Return to the default generator
    // distfun_genset ( "mt" );
    //
    // Authors
    // Copyright (C) 2012 - Michael Baudin
    // Copyright (C) Bruno Pincon

endfunction

