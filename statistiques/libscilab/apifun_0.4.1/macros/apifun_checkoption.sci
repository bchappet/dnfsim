// Copyright (C) 2012 - Michael Baudin
// Copyright (C) 2010 - DIGITEO - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function errmsg=apifun_checkoption(funname,var,varname,ivar,expectedopt )
    // Generates an error if the value of an input argument is not expected.
    //
    // Calling Sequence
    //   errmsg=apifun_checkoption(funname,var,varname,ivar,expectedopt)
    //
    // Parameters
    //   funname : a 1-by-1 matrix of strings, the name of the calling function.
    //   var : a 1-by-1 matrix of valid Scilab data type, the variable
    //   varname : a 1-by-1 matrix of string, the name of the variable
    //   ivar : a 1-by-1 matrix of floating point integers, the index of the input argument in the calling sequence
    //   expectedopt : a n x 1 or 1 x n matrix of values, the available values for the variable #ivar
    //   errmsg : a 1-by-1 matrix of strings, the error message. If there was no error, the error message is the empty matrix.
    //
    // Description
    // This function is designed to be used to design functions where an 
    // input argument has a limited number of possible values.
    //
    // Caution : do not use apifun_checkrange in place of apifun_checkoption.
    // The apifun_checkrange should be used to check that var is in a mathematical 
    // interval, which contains an infinite number of values.
    // The apifun_checkoption should be used to check that var is in a mathematical 
    // set, which contains an finite number of values.
    //
    // Examples
    // // The function takes a string argument, either "r" or "c".
    // function myfunction ( x )
    //   apifun_checkoption ( "myfunction",x,"x",1,["r" "c"] )
    //   disp("This is the string:" + x)
    // endfunction
    // // Calling sequences which work
    // myfunction ( "r" )
    // myfunction ( "c" )
    // // Calling sequences which generate an error
    // myfunction ( "Scilab" )
    //
    // Authors
    // Copyright (C) 2012 - Michael Baudin
    // Copyright (C) 2010 - DIGITEO - Michael Baudin
    //

    [lhsnb,rhsnb]=argn()
    if ( rhsnb <> 5 ) then
        msgfmt = "%s: Unexpected number of input arguments : %d provided while %d are expected."
        errmsg = msprintf(gettext(msgfmt), "apifun_checkoption", rhsnb,5)
        error(errmsg)
    end
    //
    // Checking type of input arguments
    if ( typeof(funname) <> "string" ) then
        errmsg = msprintf(gettext("%s: Expected a string for input argument %s at input #%d, but got %s instead."), "apifun_checkoption", "funname",1,typeof(funname) );
        error(errmsg)
    end
    if ( typeof(varname) <> "string" ) then
        errmsg = msprintf(gettext("%s: Expected a constant for input argument %s at input #%d, but got %s instead."), "apifun_checkoption", "varname",3,typeof(varname) );
        error(errmsg)
    end
    if ( typeof(ivar) <> "constant" ) then
        errmsg = msprintf(gettext("%s: Expected a constant for input argument %s at input #%d, but got %s instead."), "apifun_checkoption", "ivar",4,typeof(ivar) );
        error(errmsg)
    end
    //
    // Checking size of input arguments
    if ( or(size(funname) <> [1 1]) ) then
        strcomp = strcat(string(size(funname))," ")
        errmsg = msprintf(gettext("%s: Expected a scalar for input argument %s at input #%d, but got [%s] instead."), "apifun_checkoption", "funname",1,strcomp );
        error(errmsg)
    end
    if ( or(size(var) <> [1 1]) ) then
        strcomp = strcat(string(size(var))," ")
        errmsg = msprintf(gettext("%s: Expected a scalar for input argument %s at input #%d, but got [%s] instead."), "apifun_checkoption", "var",2,strcomp );
        error(errmsg)
    end
    if ( or(size(varname) <> [1 1]) ) then
        strcomp = strcat(string(size(varname))," ")
        errmsg = msprintf(gettext("%s: Expected a scalar for input argument %s at input #%d, but got [%s] instead."), "apifun_checkoption", "varname",3,varname );
        error(errmsg)
    end
    if ( or(size(ivar) <> [1 1]) ) then
        strcomp = strcat(string(size(ivar))," ")
        errmsg = msprintf(gettext("%s: Expected a scalar for input argument %s at input #%d, but got [%s] instead."), "apifun_checkoption", "ivar",4,strcomp );
        error(errmsg)
    end
    //
    errmsg = []
    if ( and ( var <> expectedopt ) ) then
        stradd = """ or """
        strexp = """" + strcat(string(expectedopt),stradd) + """"
        errmsg = msprintf(gettext("%s: Expected value [%s] for input argument %s at input #%d, but got ""%s"" instead."),funname,strexp,varname,ivar,string(var));
        error(errmsg);
    end
endfunction

