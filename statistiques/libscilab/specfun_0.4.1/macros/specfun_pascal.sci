// Copyright (C) 2009 - 2010 - DIGITEO - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function P = specfun_pascal ( varargin )
  //   Returns the Pascal matrix.
  //
  // Calling Sequence
  //   P = specfun_pascal ( n )
  //   P = specfun_pascal ( n , k )
  //
  // Parameters
  //   n : a matrix of floating point integers, must be positive
  //   k : a matrix of floating point integers, k=-1 returns the upper Pascal matrix, k=0 returns the symetric Pascal matrix, k=1 returns the lower triangular Pascal matrix, other values produce an error. Default k=0.
  //   P : a nxn matrix of floating point integers
  //
  // Description
  //   Returns the Pascal matrix.
  //
  //   The matrix U=specfun_pascal(n,1) is so that U'*U=S where 
  //   S=specfun_pascal(n,0).
  //
  // Any optional argument equal to the empty matrix [] is replaced by its default value.
  //
  //   The performances of this algorithm have been optimized.
  //
  // Examples
  // specfun_pascal(5) // symetric
  // specfun_pascal(5,-1) // upper
  // specfun_pascal(5,0) // symetric
  // specfun_pascal(5,1) // lower
  //
  // // Check a famous identity
  // n = 5;
  // U = specfun_pascal(n,-1)
  // S = specfun_pascal(n,0)
  // L = specfun_pascal(n,1)
  // L*U - S
  //
  // Authors
  //   Copyright (C) 2009 - 2010 - Michael Baudin
  //
  // Bibliography
  // http://bugzilla.scilab.org/show_bug.cgi?id=7670
  // http://en.wikipedia.org/wiki/Pascal_matrix
  
  function c = specfun_pascalupcol (n)
    // Pascal up matrix.
    // Column by column version
    c = eye(n,n)
    c(1,:) = ones(1,n)
    for i = 2:(n-1)
      c(2:i,i+1) = c(1:(i-1),i)+c(2:i,i)
    end
  endfunction
  
  function c = specfun_pascalsym (n)
    //   Returns Pascal's matrix of order n
    //   Less naive implementation : uses one loop.
    c = zeros(n,n)
    for i = 1:n
      c(i,1:n) = specfun_nchoosek (i+(1:n)-2,i-1)
    end
  endfunction
  
  [lhs, rhs] = argn()
  apifun_checkrhs ( "specfun_pascal" , rhs , 1:2 )
  apifun_checklhs ( "specfun_pascal" , lhs , 1 )
  //
  n = varargin(1)
  k = apifun_argindefault ( varargin , 2 , 0 )
  //
  apifun_checktype ( "specfun_pascal" , n , "n" , 1 , "constant" )
  apifun_checktype ( "specfun_pascal" , k , "k" , 2 , "constant" )
  //
  apifun_checkrange ( "specfun_pascal" , n , "n" , 1 , 0 , %inf )
  apifun_checkoption ( "specfun_pascal" , k , "k" , 2 , [-1 0 1] )
  //
  apifun_checkflint ( "specfun_pascal" , n , "n" , 1 )
  apifun_checkflint ( "specfun_pascal" , k , "k" , 2 )
  //
  if ( k == -1 ) then
    P = specfun_pascalupcol ( n )
  elseif ( k == 0 ) then
    P = specfun_pascalsym ( n )
  else
    P = specfun_pascalupcol (n)'
  end
endfunction
