// Copyright (C) 2012 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt


function distfun_checkvmn ( varargin )
  // Check that v, m and n are correct.
  //
  // Calling Sequence
  //   distfun_checkvmn ( funname , ivar )
  //   distfun_checkvmn ( funname , ivar , v )
  //   distfun_checkvmn ( funname , ivar , m , n )
  //
  // Parameters
  //   funname : a 1-by-1 matrix of strings, the function name
  //   ivar : a 1-by-1 matrix of flints, the index of the variable v on the calling sequence
  //   v : a 1x2 or 2x1 matrix of doubles, the size of R
  //   v(1) : the number of rows of R
  //   v(2) : the number of columns of R
  //
  // Description
  // This function is designed to be used in the distfun_*rnd functions.
  //
  // Functions with the calling sequences:
  //   R = distfun_XXXrnd ( a , v )
  //   R = distfun_XXXrnd ( a , m , n )
  // should call
  //   distfun_checkvmn ( "distfun_XXXrnd" , 2 , varargin(2:) )
  //
  // Functions with the calling sequences:
  //   R = distfun_XXXrnd ( a , b , v )
  //   R = distfun_XXXrnd ( a , b , m , n )
  // should call
  //   distfun_checkvmn ( "distfun_XXXrnd" , 3 , varargin(3:) )
  //
  
  [lhs,rhs]=argn()
  apifun_checkrhs ( "distfun_checkvmn" , rhs , 2:4 )
  apifun_checklhs ( "distfun_checkvmn" , lhs , 0:1 )
  //
  if ( rhs == 2 ) then
    // all is OK
	return
  end
  //
  funname = varargin(1)
  ivar = varargin(2)
  //
  // Check type
  if ( rhs == 3 ) then
    v = varargin(3)
    apifun_checktype ( funname , v , "v" , ivar , "constant" )  
  end
  if ( rhs == 4 ) then
    m = varargin(3)
    n = varargin(4)
    apifun_checktype ( funname , m , "m" , ivar , "constant" )  
    apifun_checktype ( funname , n , "n" , ivar+1 , "constant" )  
  end
    //
    // Check size
    if ( rhs == 3 ) then
        apifun_checkvector ( funname , v , "v" , ivar , 2 )
    end
    if ( rhs == 4 ) then
        apifun_checkscalar ( funname , m , "m" , ivar )
        apifun_checkscalar ( funname , n , "n" , ivar+1 )
    end
    //
    // Check content
    if ( rhs == 3 ) then
        apifun_checkflint ( funname , v , "v" , ivar )
        apifun_checkgreq ( funname , v , "v" , ivar , 0 )
    end
    if ( rhs == 4 ) then
        apifun_checkflint ( funname , m , "m" , ivar )
        apifun_checkgreq ( funname , m , "m" , ivar , 0 )
        apifun_checkflint ( funname , n , "n" , ivar+1 )
        apifun_checkgreq ( funname , n , "n" , ivar+1 , 0 )
    end
endfunction

