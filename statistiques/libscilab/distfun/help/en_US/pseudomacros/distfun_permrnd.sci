// Copyright (C) 2014 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function x = distfun_permrnd ( varargin )
    // Random permutation
    //
    // Calling Sequence
    //   x = distfun_permrnd ( v )
    //   x = distfun_permrnd ( v , n )
    //
    // Parameters
    //   v : a m-by-1 matrix of doubles, the vector to permute
    //   n : a 1-by-1 matrix of floating point integers, the number of permutations (default n=1)
    //   x: a m-by-n matrix of doubles, the random permutations of v
    //
    // Description
    //   Generates random permutations.
    //
    // Examples
    // // Use x = distfun_permrnd ( v )
    // x=distfun_permrnd((1:6)')
    // x=distfun_permrnd((1:6)')
    //
    // // Generate 4 permutations
    // x=distfun_permrnd((1:6)',4)
    //
    // Authors
    //   Copyright (C) 2014 - Michael Baudin

endfunction
