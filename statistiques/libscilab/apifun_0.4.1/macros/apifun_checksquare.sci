// Copyright (C) 2010 - DIGITEO - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function errmsg=apifun_checksquare(funname,var,varname,ivar )
  // Generates an error if the variable is not a square matrix.
  //
  // Calling Sequence
  //   errmsg=apifun_checksquare(funname,var,varname,ivar)
  //
  // Parameters
  //   funname : a 1-by-1 matrix of strings, the name of the calling function.
  //   var : a 1-by-1 matrix of valid Scilab data type, the variable
  //   varname : a 1-by-1 matrix of string, the name of the variable
  //   ivar : a 1-by-1 matrix of floating point integers, the index of the input argument in the calling sequence
  //   errmsg : a 1-by-1 matrix of strings, the error message. If there was no error, the error message is the empty matrix.
  //
  // Description
  // This function is designed to be used to design functions where 
  // the input argument is a square matrix.
  // This function cannot be use when var is a function, or more
  // generally, for any input argument for which the size function
  // does not work.
  //
  // Examples
  // // The function takes a 2 x 3 matrix of doubles.
  // function y = myfunction ( x )
  //   apifun_checksquare ( "myfunction",x,"x",1 )
  //   y = x
  // endfunction
  // // Calling sequences which work
  // y = myfunction ( ones(2,2) )
  // y = myfunction ( zeros(3,3) )
  // // Calling sequences which generate an error
  // y = myfunction ( ones(1,3) )
  // y = myfunction ( zeros(2,4) )
  //
  // Authors
  // Copyright (C) 2010 - DIGITEO - Michael Baudin
  //

  [lhsnb,rhsnb]=argn()
  if ( rhsnb <> 4 ) then
    msgfmt = "%s: Unexpected number of input arguments : %d provided while %d are expected."
    errmsg = msprintf(gettext(msgfmt), "apifun_checksquare", rhsnb,4)
    error(errmsg)
  end
  //
  // Checking type of input arguments
  if ( typeof(funname) <> "string" ) then
    errmsg = msprintf(gettext("%s: Expected a string for input argument %s at input #%d, but got %s instead."), "apifun_checksquare", "funname",1,typeof(funname) );
    error(errmsg)
  end
  if ( typeof(varname) <> "string" ) then
    errmsg = msprintf(gettext("%s: Expected a string for input argument %s at input #%d, but got %s instead."), "apifun_checksquare", "varname",3,typeof(varname) );
    error(errmsg)
  end
  if ( typeof(ivar) <> "constant" ) then
    errmsg = msprintf(gettext("%s: Expected a constant for input argument %s at input #%d, but got %s instead."), "apifun_checksquare", "ivar",4,typeof(ivar) );
    error(errmsg)
  end
  //
  // Checking size of input arguments
  if ( or(size(funname) <> [1 1]) ) then
    strcomp = strcat(string(size(funname))," ")
    errmsg = msprintf(gettext("%s: Expected a scalar for input argument %s at input #%d, but got [%s] instead."), "apifun_checksquare", "funname",1,strcomp );
    error(errmsg)
  end
  if ( or(size(varname) <> [1 1]) ) then
    strcomp = strcat(string(size(varname))," ")
    errmsg = msprintf(gettext("%s: Expected a scalar for input argument %s at input #%d, but got [%s] instead."), "apifun_checksquare", "varname",3,varname );
    error(errmsg)
  end
  if ( or(size(ivar) <> [1 1]) ) then
    strcomp = strcat(string(size(ivar))," ")
    errmsg = msprintf(gettext("%s: Expected a scalar for input argument %s at input #%d, but got [%s] instead."), "apifun_checksquare", "ivar",4,strcomp );
    error(errmsg)
  end
  //
  errmsg = []
  if ( size(var,"r") <> size(var,"c") ) then
    strcomp = strcat(string(size(var))," ")
    errmsg = msprintf(gettext("%s: Expected a square matrix for input argument %s at input #%d, but got [%s] instead."), funname, varname,ivar,strcomp );
    error(errmsg)
  end
endfunction



