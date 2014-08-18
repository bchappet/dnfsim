// Copyright (C) 2013 - Michael Baudin
// Copyright (C) 2010 - DIGITEO - Michael Baudin
// Copyright (C) 1993 - 1995 - Anders Holtsberg
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function z=rboot(x,b)
    // Simulate a bootstrap resample
    //  
    // Calling Sequence
    // z=rboot(x)
    // z=rboot(x,b)
    //
    // Parameters
    // x : a nrows-by-ncols matrix of doubles
    // b : a 1-by-1 matrix of doubles, the number of bootstrap resamples (default b=1)
    // z : a nrows-by-(ncols*b) matrix of doubles
    //
    // Description
    // <literal>rboot(x)</literal> gives a resample of x.
    //
    // <literal>rboot(x,b)</literal> gives b columns of resamples. This form works
    // only for x one-dimensional, ie x column vector.
    //
    // If x is a row vector, it is converted into 
    // a column vector.
    //
    // If x is a matrix, the resamples are stored in the columns of z:
    // <screen>
    // z=[z1,z2,...,zb]
    // </screen>
    // where zi is a nrows-by-ncols matrix of doubles, for i=1,2,...,b.
    //
    // Examples
    // // With x column vector
    // x=distfun_chi2rnd(3,10,1)
    // z=rboot(x)
    // // Get 3 resamples
    // z=rboot(x,3)
    // 
    // // With a x matrix
    // x=distfun_chi2rnd(3,10,3)
    // z=rboot(x)
    // // Get 3 resamples
    // z=rboot(x,3)
    //
    // Authors
    // Copyright (C) 2013 - Michael Baudin
    // Copyright (C) 2010 - DIGITEO - Michael Baudin
    // Copyright (C) 1993 - 1995 - Anders Holtsberg

    z=[];
    [nargout,nargin] = argn(0)

    if min(size(x))==1 then
        x = x(:);
    end

    if nargin<2 then
        b = 1;
    end
    nrows = size(x,"r")
    ncols = size(x,"c")
    // Workaround for bug ::
    // http://forge.scilab.org/index.php/p/distfun/issues/1085
    U=distfun_unifrnd(0,1,nrows,ncols*b)
    //U=distfun_rndunf(0,1,nrows,ncols*b)
    I = ceil(nrows*ncols*U)
    z = zeros(nrows,ncols*b)
    z(:) = x(I)
endfunction
