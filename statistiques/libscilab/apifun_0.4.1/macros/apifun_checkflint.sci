// Copyright (C) 2012 - Michael Baudin
// Copyright (C) 2010 - 2011 - DIGITEO - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function errmsg=apifun_checkflint(funname,var,varname,ivar )
    // Generates an error if the variable is not a floating point integer.
    //
    // Calling Sequence
    //   errmsg=apifun_checkflint(funname,var,varname,ivar)
    //
    // Parameters
    //   funname : a 1-by-1 matrix of strings, the name of the calling function.
    //   var : a matrix of valid Scilab data type, the variable
    //   varname : a 1-by-1 matrix of string, the name of the variable
    //   ivar : a 1-by-1 matrix of floating point integers, the index of the input argument in the calling sequence
    //   errmsg : a 1-by-1 matrix of strings, the error message. If there was no error, the error message is the empty matrix.
    //
    // Description
    // Checks that the variable var is a "constant" and that its content is 
	// an integer (i.e. the value has no fractional part).
	// This is checked with the condition and(round(x)==x).
    //
    // Examples
    // // The function takes an argument x which is a double 
    // // with an integer value
    // function y = myfunction ( x )
    //   apifun_checkflint ( "myfunction",x,"x",1 )
    //   y = x.^2
    // endfunction
    // // Calling sequences which work
    // myfunction ( [-1 -2 12] )
    // // Calling sequences which generate an error
    // myfunction ( [1.5 1] )
    //
    // Authors
	// Copyright (C) 2012 - Michael Baudin
    // Michael Baudin - 2010 - 2011 - DIGITEO
    //

    [lhsnb,rhsnb]=argn()
    if ( rhsnb <> 4 ) then
        msgfmt = "%s: Wrong number of input argument: %d expected.\n"
        errmsg = msprintf(gettext(msgfmt), "apifun_checkflint",4)
        error(errmsg)
    end
    //
    // Checking type of input arguments
    if ( typeof(funname) <> "string" ) then
        errmsg = msprintf(gettext("%s: Expected a string for input argument %s at input #%d, but got %s instead."), "apifun_checkflint", "funname",1,typeof(funname) );
        error(errmsg)
    end
    if ( typeof(var) <> "constant" ) then
        errmsg = msprintf(gettext("%s: Expected a constant for input argument %s at input #%d, but got %s instead."), "apifun_checkflint", "var",2,typeof(var) );
        error(errmsg)
    end
    if ( typeof(varname) <> "string" ) then
        errmsg = msprintf(gettext("%s: Expected a constant for input argument %s at input #%d, but got %s instead."), "apifun_checkflint", "varname",3,typeof(varname) );
        error(errmsg)
    end
    if ( typeof(ivar) <> "constant" ) then
        errmsg = msprintf(gettext("%s: Expected a constant for input argument %s at input #%d, but got %s instead."), "apifun_checkflint", "ivar",4,typeof(ivar) );
        error(errmsg)
    end
    //
    // Checking size of input arguments
    if ( or(size(funname) <> [1 1]) ) then
        strcomp = strcat(string(size(funname))," ")
        errmsg = msprintf(gettext("%s: Expected a scalar for input argument %s at input #%d, but got [%s] instead."), "apifun_checkflint", "funname",1,strcomp );
        error(errmsg)
    end
    if ( or(size(varname) <> [1 1]) ) then
        strcomp = strcat(string(size(varname))," ")
        errmsg = msprintf(gettext("%s: Expected a scalar for input argument %s at input #%d, but got [%s] instead."), "apifun_checkflint", "varname",3,varname );
        error(errmsg)
    end
    if ( or(size(ivar) <> [1 1]) ) then
        strcomp = strcat(string(size(ivar))," ")
        errmsg = msprintf(gettext("%s: Expected a scalar for input argument %s at input #%d, but got [%s] instead."), "apifun_checkflint", "ivar",4,strcomp );
        error(errmsg)
    end
    //
    errmsg = []
    if ( or ( round(var)<>var ) ) then
    k = find ( round(var)<>var )
    k = k(1)
	lclmsg = "%s: Expected floating point integer for input argument %s at input #%d, but entry #%d is equal to %s."
    errmsg = msprintf(gettext(lclmsg),funname,varname,ivar,k,string(var(k)));
    error(errmsg);
  end
endfunction

