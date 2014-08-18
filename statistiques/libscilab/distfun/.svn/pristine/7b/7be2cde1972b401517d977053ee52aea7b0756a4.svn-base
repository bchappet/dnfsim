// Copyright (C) 2012 - Michael Baudin
// Copyright (C) Jean-Philippe Chancelier and Bruno Pincon
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt


function s = distfun_seedget ( )
    // Get the current state of the current random number generator
    //
    // Calling Sequence
    //   s = distfun_seedget ( )
    //
    // Parameters
    // s: a m-by-1 matrix of doubles, integer value, the current state of the current random number generator
    //
    // Description
    //   s is a column vector (of integers) of 
	//   <itemizedlist>
	//   <listitem>
	//   <para>
    //    dimension m=625 for mt, 
	//   </para>
	//   </listitem>
	//   <listitem>
	//   <para>
    //    dimension m=4 for kiss, 
	//   </para>
	//   </listitem>
	//   <listitem>
	//   <para>
    //    dimension m=2 for clcg2, 
	//   </para>
	//   </listitem>
	//   <listitem>
	//   <para>
    //    dimension m=40 for fsultra, 
	//   </para>
	//   </listitem>
	//   <listitem>
	//   <para>
    //    dimension m=4 for stream,
	//   </para>
	//   </listitem>
	//   <listitem>
	//   <para>
    //    dimension m=1 for urand.
	//   </para>
	//   </listitem>
	//   </itemizedlist>
	//
    //   For "clcg4", s is the current state of the current virtual generator.
	//
    //   For "mt", the first entry, s(1), is an index in [1,624].
    //
    // Examples
	// s = distfun_seedget ( );
	// size(s)
	//
	// // Set the CLCG2 generator
    // distfun_genset ( "clcg2" );
	// s = distfun_seedget ( )
	// // Return to the default generator
    // distfun_genset ( "mt" );
    //
    // Authors
    // Copyright (C) 2012 - Michael Baudin
    // Copyright (C) Jean-Philippe Chancelier and Bruno Pincon

endfunction

