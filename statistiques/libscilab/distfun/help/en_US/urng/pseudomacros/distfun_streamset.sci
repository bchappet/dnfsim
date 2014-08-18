// Copyright (C) 2012 - Michael Baudin
// Copyright (C) Bruno Pincon
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt


function distfun_streamset ( g )
    // Set the current stream
    //
    // Calling Sequence
    //   distfun_streamset ( g )
    //
    // Parameters
    // g: a 1-by-1 matrix of doubles, integer value, the current stream
    //
    // Description
    //   Sets the current stream g. 
    //   The input argument g must be in the set 0,1,2,...,100.
    //
    // Examples
	// distfun_genset ( "clcg4" );
    // g = distfun_streamget ( )
    // distfun_streamset ( 1 )
    // g = distfun_streamget ( )
	// // Return to the default generator
    // distfun_genset ( "mt" );
    //
    // Authors
    // Copyright (C) 2012 - Michael Baudin
    // Copyright (C) Bruno Pincon

endfunction

