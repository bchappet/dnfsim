// Copyright (C) 2010 - DIGITEO - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function errmsg=apifun_checkdims(funname,var,varname,ivar,matdims )
  // Generates an error if the variable has not the required size.
  //
  // Calling Sequence
  //   errmsg=apifun_checkdims(funname,var,varname,ivar,matdims)
  //
  // Parameters
  //   funname : a 1-by-1 matrix of strings, the name of the calling function.
  //   var : a 1-by-1 matrix of valid Scilab data type, the variable
  //   varname : a 1-by-1 matrix of string, the name of the variable
  //   ivar : a 1-by-1 matrix of floating point integers, the index of the input argument in the calling sequence
  //   matdims : 1 x 2 matrix of floating point integers, the number of rows, columns for the variable #ivar
  //   errmsg : a 1-by-1 matrix of strings, the error message. If there was no error, the error message is the empty matrix.
  //
  // Description
  // This function is designed to be used to design functions where 
  // the input argument has a known shape.
  // This function cannot be use when var is a function, or more
  // generally, for any input argument for which the size function
  // does not work.
  //
  // We recommend to check the type, and then the size (and not the other way).
  // Indeed, if the type of the argument is not good, the size function may
  // not be callable for the given argument.
  //
  //   Last update : 05/08/2010.
  //
  // Examples
  // // The function takes a 2 x 3 matrix of doubles.
  // function y = myfunction ( x )
  //   apifun_checkdims ( "myfunction",x,"x",1,[2 3] )
  //   y = x
  // endfunction
  // // Calling sequences which work
  // y = myfunction ( ones(2,3) )
  // y = myfunction ( zeros(2,3) )
  // // Calling sequences which generate an error
  // y = myfunction ( ones(1,3) )
  // y = myfunction ( zeros(2,4) )
  //
  // Authors
  // Copyright (C) 2010 - DIGITEO - Michael Baudin
  //

  [lhsnb,rhsnb]=argn()
  if ( rhsnb <> 5 ) then
    msgfmt = "%s: Unexpected number of input arguments : %d provided while %d are expected."
    errmsg = msprintf(gettext(msgfmt), "apifun_checkdims", rhsnb,5)
    error(errmsg)
  end
  //
  // Checking type of input arguments
  if ( typeof(funname) <> "string" ) then
    errmsg = msprintf(gettext("%s: Expected a string for input argument %s at input #%d, but got %s instead."), "apifun_checkdims", "funname",1,typeof(funname) );
    error(errmsg)
  end
  if ( typeof(varname) <> "string" ) then
    errmsg = msprintf(gettext("%s: Expected a constant for input argument %s at input #%d, but got %s instead."), "apifun_checkdims", "varname",3,typeof(varname) );
    error(errmsg)
  end
  if ( typeof(ivar) <> "constant" ) then
    errmsg = msprintf(gettext("%s: Expected a constant for input argument %s at input #%d, but got %s instead."), "apifun_checkdims", "ivar",4,typeof(ivar) );
    error(errmsg)
  end
  if ( typeof(matdims) <> "constant" ) then
    errmsg = msprintf(gettext("%s: Expected a constant for input argument %s at input #%d, but got %s instead."), "apifun_checkdims", "matdims",5,typeof(matdims) );
    error(errmsg)
  end
  //
  // Checking size of input arguments
  if ( or(size(funname) <> [1 1]) ) then
    strcomp = strcat(string(size(funname))," ")
    errmsg = msprintf(gettext("%s: Expected a scalar for input argument %s at input #%d, but got [%s] instead."), "apifun_checkdims", "funname",1,strcomp );
    error(errmsg)
  end
  if ( or(size(varname) <> [1 1]) ) then
    strcomp = strcat(string(size(varname))," ")
    errmsg = msprintf(gettext("%s: Expected a scalar for input argument %s at input #%d, but got [%s] instead."), "apifun_checkdims", "varname",3,varname );
    error(errmsg)
  end
  if ( or(size(ivar) <> [1 1]) ) then
    strcomp = strcat(string(size(ivar))," ")
    errmsg = msprintf(gettext("%s: Expected a scalar for input argument %s at input #%d, but got [%s] instead."), "apifun_checkdims", "ivar",4,strcomp );
    error(errmsg)
  end
  if ( size(matdims) <> [1 2] ) then
    strcomp = strcat(string(size(matdims))," ")
    errmsg = msprintf(gettext("%s: Expected size [%s] for input argument %s at input #%d, but got [%s] instead."), "apifun_checkdims", "1 2","matdims",5,strcomp );
    error(errmsg)
  end
  //
  errmsg = []
  if ( or ( size(var) <> matdims ) ) then
    strexp = strcat(string(matdims)," ")
    strcomp = strcat(string(size(var))," ")
    errmsg = msprintf(gettext("%s: Expected size [%s] for input argument %s at input #%d, but got [%s] instead."), funname, strexp, varname,ivar,strcomp );
    error(errmsg)
  end
endfunction



