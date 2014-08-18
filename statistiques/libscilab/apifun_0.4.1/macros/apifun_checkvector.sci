// Copyright (C) 2012 - Michael Baudin
// Copyright (C) 2010 - DIGITEO - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function errmsg=apifun_checkvector ( varargin )
  // Generates an error if the variable is not a vector.
  //
  // Calling Sequence
  //   errmsg=apifun_checkvector(funname,var,varname,ivar)
  //   errmsg=apifun_checkvector(funname,var,varname,ivar,nbval)
  //
  // Parameters
  //   funname : a 1-by-1 matrix of strings, the name of the calling function.
  //   var : a matrix of valid Scilab data type, the variable
  //   varname : a 1-by-1 matrix of string, the name of the variable
  //   ivar : a 1-by-1 matrix of floating point integers, the index of the input argument in the calling sequence
  //   nbval : a 1-by-1 matrix of floating point integers, the number of entries in the vector (default nbval=size(var,"*"), i.e. just check that the input is a vector).
  //   errmsg : a 1-by-1 matrix of strings, the error message. If there was no error, the error message is the empty matrix.
  //
  // Description
  // This function is designed to be used to design functions where 
  // the input argument is a vector, that is, a matrix for which 
  // nrows == 1 or ncols == 1.
  // This function cannot be use when var is a function, or more
  // generally, for any input argument for which the size function
  // does not work.
  //
  // Examples
  // // The function takes a vector of 3 doubles.
  // function y = myfunction ( x )
  //   apifun_checkvector ( "myfunction",x,"x",1,3 )
  //   y = x
  // endfunction
  // // Calling sequences which work
  // y = myfunction ( ones(1,3) )
  // y = myfunction ( zeros(3,1) )
  // // Calling sequences which generate an error
  // // The following are not vectors
  // y = myfunction ( ones(2,3) )
  // y = myfunction ( zeros(3,2) )
  // // The following have the wrong number of entries
  // y = myfunction ( ones(1,4) )
  //
  // Authors
  // Copyright (C) 2012 - Michael Baudin
  // Copyright (C) 2010 - DIGITEO - Michael Baudin
  //

  [lhsnb,rhsnb]=argn()
  if ( rhsnb <> 4 & rhsnb <> 5 ) then
    msgfmt = "%s: Wrong number of input arguments: %d or %d expected.\n"
    errmsg = msprintf(gettext(msgfmt), "apifun_checkvector",4,5)
    error(errmsg)
  end
  funname = varargin(1)
  var = varargin(2)
  varname = varargin(3)
  ivar = varargin(4)
  if ( rhsnb <= 4 ) then
      nbval = size(var,"*")
  else
      nbval = varargin(5)
  end
  //
  // Checking type of input arguments
  if ( typeof(funname) <> "string" ) then
    errmsg = msprintf(gettext("%s: Expected a string for input argument %s at input #%d, but got %s instead."), "apifun_checkvector", "funname",1,typeof(funname) );
    error(errmsg)
  end
  if ( typeof(varname) <> "string" ) then
    errmsg = msprintf(gettext("%s: Expected a string for input argument %s at input #%d, but got %s instead."), "apifun_checkvector", "varname",3,typeof(varname) );
    error(errmsg)
  end
  if ( typeof(ivar) <> "constant" ) then
    errmsg = msprintf(gettext("%s: Expected a constant for input argument %s at input #%d, but got %s instead."), "apifun_checkvector", "ivar",4,typeof(ivar) );
    error(errmsg)
  end
  if ( typeof(nbval) <> "constant" ) then
    errmsg = msprintf(gettext("%s: Expected a constant for input argument %s at input #%d, but got %s instead."), "apifun_checkvector", "nbval",5,typeof(nbval) );
    error(errmsg)
  end
  //
  // Checking size of input arguments
  if ( or(size(funname) <> [1 1]) ) then
    strcomp = strcat(string(size(funname))," ")
    errmsg = msprintf(gettext("%s: Expected a scalar for input argument %s at input #%d, but got [%s] instead."), "apifun_checkvector", "funname",1,strcomp );
    error(errmsg)
  end
  if ( or(size(varname) <> [1 1]) ) then
    strcomp = strcat(string(size(varname))," ")
    errmsg = msprintf(gettext("%s: Expected a scalar for input argument %s at input #%d, but got [%s] instead."), "apifun_checkvector", "varname",3,varname );
    error(errmsg)
  end
  if ( or(size(ivar) <> [1 1]) ) then
    strcomp = strcat(string(size(ivar))," ")
    errmsg = msprintf(gettext("%s: Expected a scalar for input argument %s at input #%d, but got [%s] instead."), "apifun_checkvector", "ivar",4,strcomp );
    error(errmsg)
  end
  if ( or(size(nbval) <> [1 1]) ) then
    strcomp = strcat(string(size(nbval))," ")
    errmsg = msprintf(gettext("%s: Expected a scalar for input argument %s at input #%d, but got [%s] instead."), "apifun_checkvector", "nbval",5,strcomp );
    error(errmsg)
  end
  //
  errmsg = []
  nrows = size(var,"r")
  ncols = size(var,"c")
  if ( nrows <> 1 & ncols <> 1 ) then
    strcomp = strcat(string(size(var))," ")
    errmsg = msprintf(gettext("%s: Expected a vector matrix for input argument %s at input #%d, but got [%s] instead."), funname, varname,ivar,strcomp );
    error(errmsg)
  end
  if ( ( nrows == 1 & ncols <> nbval ) | ( ncols == 1 & nrows <> nbval ) ) then
    strcomp = strcat(string(size(var))," ")
    errmsg = msprintf(gettext("%s: Expected %d entries for input argument %s at input #%d, but current dimensions are [%s] instead."), funname, nbval,varname,ivar,strcomp );
    error(errmsg)
  end
endfunction



