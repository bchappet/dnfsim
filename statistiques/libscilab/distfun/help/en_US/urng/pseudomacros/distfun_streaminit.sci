// Copyright (C) 2012 - Michael Baudin
// Copyright (C) Bruno Pincon
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt


function distfun_streaminit ( I )
    // Initializes the current substream
    //
    // Calling Sequence
    //   distfun_streaminit ( I )
    //
    // Parameters
    // I: a 1-by-1 matrix of doubles, integer value, the type of action on the current stream. Available values are I=-1,0 or 1.
    //
    // Description
	//   This function let us move forward or backward within the stream g.
	//
	//   <itemizedlist>
	//   <listitem>
	//   <para>
    //   distfun_streaminit(-1) : 
	//   </para>
	//   <para>
    //     sets the state of the current stream to its initial seed, 
	//     i.e. reset it to the start of the substream.
	//   </para>
	//   </listitem>
	//   <listitem>
	//   <para>
    //   distfun_streaminit(0) : 
	//   </para>
	//   <para>
    //     sets the state of the current stream 
    //     to its previous seed, i.e. set it to the beginning of the current substream.
	//   </para>
	//   </listitem>
	//   <listitem>
	//   <para>
    //   distfun_streaminit(1) : 
	//   </para>
	//   <para>
    //     sets the state of the current stream to the beginning 
	//     of the next substream and resets the current substream parameters.
	//   </para>
	//   </listitem>
	//   </itemizedlist>
    //
    // Examples
	// distfun_genset ( "clcg4" );
    // distfun_streamset ( 0 ) // this is the default
	// // Set the virt. gen. g=0 to the begining of the stream
	// distfun_streaminit(-1)
	// // Go on to the next subtream ...
	// distfun_streaminit(1)
	// // ... go on to the next subtream ...
	// distfun_streaminit(1)
	// // Return to the default generator
	// distfun_genset ( "mt" );
	//
	// // Example with moves forward and backward in the stream
	// distfun_genset ( "clcg4" );
    // distfun_streamset ( 0 ) // this is the default
	// // Set the virt. gen. g=0 to the begining of the stream
	// distfun_streaminit(-1)
	// // Go on to the next subtream ...
	// distfun_streaminit(1)
	// // ... go back to the previous subtream ...
	// distfun_streaminit(0)
	// // Return to the default generator
	// distfun_genset ( "mt" );
    //
    // Authors
    // Copyright (C) 2012 - Michael Baudin
    // Copyright (C) Bruno Pincon

endfunction

