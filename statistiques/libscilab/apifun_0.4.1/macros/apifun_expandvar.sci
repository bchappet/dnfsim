// Copyright (C) 2010 - 2011 - DIGITEO - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function varargout=apifun_expandvar ( varargin )
  // Expand variables so that they all have the same shape.
  //
  // Calling Sequence
  //   ovar1=apifun_expandvar(ivar1)
  //   [ovar1,ovar2]=apifun_expandvar(ivar1,ivar2)
  //   [ovar1,ovar2,ovar3]=apifun_expandvar(ivar1,ivar2,ivar3)
  //   [ovar1,ovar2,ovar3,...]=apifun_expandvar(ivar1,ivar2,ivar3,...)
  //
  // Parameters
  //   ivar1 : a matrix of doubles, the input variable #1
  //   ovar1 : a matrix of doubles, the output variable #1
  //
  // Description
  //   This function expands input arguments of 
  //   computationnal functions. 
  //   This function provides flexiblity for functions 
  //   which have sets of arguments with the same goal.
  //
  // On input, the input arguments ivar1, ivar2, etc... may have  
  // different dimensions.
  // On output, the output arguments ovar1, ovar2, etc... all have the 
  // same dimensions.
  //
  // For example ivar1 may be a m-by-n matrix and ivar2 
  // may be a scalar (actually, a 1-by-1 matrix).
  // In this case, the output variable ovar1 is a copy of ivar1, but 
  // ovar2 is a matrix, with the same size as ivar1, and all entries set to 
  // ivar2.
  //   
  //   If two matrices or more are input arguments, these matrices 
  //   must have the same dimensions: if not, an error is generated.
  //
  // Examples
  //   // Expand ovar1 to [1 1 1]
  //   [ovar1,ovar2]=apifun_expandvar ( 1,[2 3 4] )
  //
  //   // Expand ovar2 to [4 4 4]
  //   [ovar1,ovar2]=apifun_expandvar ( [1 2 3],4 )
  //
  //   // Expand ovar2 to [4 4 4]'
  //   [ovar1,ovar2]=apifun_expandvar ( [1 2 3]',4 )
  //
  //  // Error case
  //  // a and b are matrices: they must have the same size.
  //  [a,b]=apifun_expandvar ( [1;2;3;4],[1;2] )
  //
  // // A practical use-case.
  // // The function f has three arguments: x, a, b
  // function y = myfunction(x,a,b)
  //     [a,b]=apifun_expandvar ( a,b )
  //     y = x*a+b
  // endfunction
  // // Regular use
  // x = 2;
  // y = myfunction(x,1,2)
  // // Extended use-cases
  // y = myfunction(x,[1;2;3;4],2)
  // y = myfunction(x,2,[1;2;3;4])
  // // Errors
  // y = myfunction(x,[1;2;3;4],[1;2])
  //
  // Authors
  //   Michael Baudin - 2009-2010 - DIGITEO

  [lhs,rhs]=argn()
  if ( rhs <> lhs ) then
    errmsg = sprintf ( gettext ( "%s: The number of output arguments %d do not match the number of input arguments %d."),"apifun_expandvar",lhs,rhs )
    error(errmsg)
  end

  //
  // Check if there is one argument which is a matrix.
  // imat is the index of the input argument which is a matrix.
  istherematrix = %f
  imat = 0
  for ivar = 1 : rhs
    if ( size ( varargin(ivar),"*" ) <> 1 ) then
      istherematrix = %t
      imat = ivar
      break
    end
  end
  // If there is no matrix, returns the output arguments as is.
  if ( ~istherematrix ) then
    for ovar = 1 : lhs
      varargout ( ovar ) = varargin ( ovar )
    end
    return
  end
  // If there is one matrix, get its size.
  nbrows = size ( varargin ( imat ),"r" )
  nbcols = size ( varargin ( imat ),"c" )
  // Check that all matrices have the same shape.
  for ivar = 1 : rhs
    nbi = size ( varargin ( ivar ),"*" )
    if ( nbi <> 1 ) then
      nbrowsi = size ( varargin ( ivar ),"r" )
      nbcolsi = size ( varargin ( ivar ),"c" )
      if ( nbrowsi <> nbrows ) then
        errmsg = msprintf(gettext("%s: Expected %d rows in input argument #%d, but found %d rows instead."), "apifun_expandvar",nbrows,ivar,nbrowsi );
        error(errmsg)
      end
      if ( nbcolsi <> nbcols ) then
        errmsg = msprintf(gettext("%s: Expected %d columns in input argument #%d, but found %d columns instead."), "apifun_expandvar",nbcols,ivar,nbcolsi );
        error(errmsg)
      end
    end
  end
  // Expand all input arguments which are scalar up to this shape.
  for ivar = 1 : rhs
    if ( size ( varargin(ivar),"*" ) == 1 ) then
      varargin ( ivar ) = varargin ( ivar ) * ones ( nbrows,nbcols )
    end
  end
  // Set all output variables
  for ovar = 1 : lhs
    varargout ( ovar ) = varargin ( ovar )
  end
endfunction

