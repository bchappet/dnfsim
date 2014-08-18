// Copyright (C) 2010 - DIGITEO - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function errmsg=apifun_checktype(funname,var,varname,ivar,expectedtype )
  // Generates an error if the given variable is not of expected type.
  //
  // Calling Sequence
  //   errmsg=apifun_checktype(funname,var,varname,ivar,expectedtype)
  //
  // Parameters
  //   funname : a 1-by-1 matrix of strings, the name of the calling function.
  //   var : a 1-by-1 matrix of valid Scilab data type, the variable
  //   varname : a 1-by-1 matrix of string, the name of the variable
  //   ivar : a 1-by-1 matrix of floating point integers, the index of the input argument in the calling sequence
  //   expectedtype : a n x 1 or 1 x n matrix of strings, the available types for the variable #ivar
  //   errmsg : a 1-by-1 matrix of strings, the error message. If there was no error, the error message is the empty matrix.
  //
  // Description
  // This function is designed to be used to design functions with 
  // input arguments with variable type.
  // We use the typeof function to compute the type of the variable:
  // see help typeof to get the list of all available values for expectedtype.
  //
  // We recommend to check the type, and then the size (and not the other way).
  // Indeed, if the type of the argument is not good, the size function may
  // not be callable for the given argument.
  //
  //   Last update : 29/07/2010.
  //
  // Examples
  // // The function takes a string argument.
  // function myfunction ( x )
  //   apifun_checktype ( "myfunction",x,"x",1,"string" )
  //   disp("This is a string")
  // endfunction
  // // Calling sequences which work
  // myfunction ( "Scilab" )
  // // Calling sequences which generate an error
  // myfunction ( 123456 )
  //
  // // The function takes a string or a matrix of doubles argument.
  // function myfunction ( x )
  //   apifun_checktype ( "myfunction",x,"x",1,["string" "constant"] )
  //   if ( typeof(x) == "string" ) then
  //     disp("This is a matrix of strings")
  //   else
  //     disp("This is a matrix of doubles")
  //   end
  // endfunction
  // // Calling sequences which work
  // myfunction ( "Scilab" )
  // myfunction ( 123456 )
  // // Calling sequences which generate an error
  // myfunction ( uint8(2) )
  //
  // Authors
  // Copyright (C) 2010 - DIGITEO - Michael Baudin
  //

  [lhsnb,rhsnb]=argn()
  if ( rhsnb <> 5 ) then
    msgfmt = "%s: Unexpected number of input arguments : %d provided while %d are expected."
    errmsg = msprintf(gettext(msgfmt), "apifun_checktype", rhsnb,5)
    error(errmsg)
  end
  //
  // Checking type of input arguments
  if ( typeof(funname) <> "string" ) then
    errmsg = msprintf(gettext("%s: Expected a string for input argument %s at input #%d, but got %s instead."), "apifun_checktype", "funname",1,typeof(funname) );
    error(errmsg)
  end
  if ( typeof(varname) <> "string" ) then
    errmsg = msprintf(gettext("%s: Expected a string for input argument %s at input #%d, but got %s instead."), "apifun_checktype", "varname",3,typeof(varname) );
    error(errmsg)
  end
  if ( typeof(ivar) <> "constant" ) then
    errmsg = msprintf(gettext("%s: Expected a constant for input argument %s at input #%d, but got %s instead."), "apifun_checktype", "ivar",4,typeof(ivar) );
    error(errmsg)
  end
  if ( typeof(expectedtype) <> "string" ) then
    errmsg = msprintf(gettext("%s: Expected a string for input argument %s at input #%d, but got %s instead."), "apifun_checktype", "expectedtype",5,typeof(expectedtype) );
    error(errmsg)
  end
  //
  // Checking size of input arguments
  if ( or(size(funname) <> [1 1]) ) then
    strcomp = strcat(string(size(funname))," ")
    errmsg = msprintf(gettext("%s: Expected a scalar for input argument %s at input #%d, but got [%s] instead."), "apifun_checktype", "funname",1,strcomp );
    error(errmsg)
  end
  if ( or(size(varname) <> [1 1]) ) then
    strcomp = strcat(string(size(varname))," ")
    errmsg = msprintf(gettext("%s: Expected a scalar for input argument %s at input #%d, but got [%s] instead."), "apifun_checktype", "varname",3,varname );
    error(errmsg)
  end
  if ( or(size(ivar) <> [1 1]) ) then
    strcomp = strcat(string(size(ivar))," ")
    errmsg = msprintf(gettext("%s: Expected a scalar for input argument %s at input #%d, but got [%s] instead."), "apifun_checktype", "ivar",4,strcomp );
    error(errmsg)
  end
  if ( and(size(expectedtype) <> [1 1]) ) then
    strcomp = strcat(string(size(expectedtype))," ")
    errmsg = msprintf(gettext("%s: Expected a vector for input argument %s at input #%d, but got [%s] instead."), "apifun_checktype", "expectedtype",5,strcomp );
    error(errmsg)
  end
  //
  errmsg = []
  if ( and ( typeof ( var ) <> expectedtype ) ) then
    strexp = """" + strcat(expectedtype,""" or """) + """"
    errmsg = msprintf(gettext("%s: Expected type [%s] for input argument %s at input #%d, but got ""%s"" instead."),funname, strexp, varname,ivar,typeof(var) );
    error(errmsg);
  end
endfunction



