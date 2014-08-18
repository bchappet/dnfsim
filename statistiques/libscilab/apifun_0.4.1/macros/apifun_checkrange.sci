// Copyright (C) 2012 - Michael Baudin
// Copyright (C) 2010 - DIGITEO - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function errmsg=apifun_checkrange(funname,var,varname,ivar,vmin,vmax )
    // Check that the value is in a given range.
    //
    // Calling Sequence
    //   errmsg=apifun_checkrange(funname,var,varname,ivar,vmin,vmax)
    //
    // Parameters
    //   funname : a 1-by-1 matrix of strings, the name of the calling function.
    //   var : a matrix of values, the variable
    //   varname : a 1-by-1 matrix of string, the name of the variable
    //   ivar : a 1-by-1 matrix of floating point integers, the index of the input argument in the calling sequence
    //   vmin : a 1-by-1 matrix of values, the minimum value for the variable #ivar
    //   vmax : a 1-by-1 matrix of values, the maximum value for the variable #ivar
    //   errmsg : a 1-by-1 matrix of strings, the error message. If there was no error, the error message is the empty matrix.
    //
    // Description
    // This function is designed to be used to design functions where an 
    // input argument is expected to be greater or equal to a threshold.
    // The error is generated if the condition and ( vmin <= var & var <= vmax ) is false.
    // This function can be used for whatever variable type for which 
    // the comparison "<=" can be evaluated.
    //
    // The variable var can be of type matrix of doubles ("constant"), 
    // integer (uint8, uint16, uint32, int8, int16, int32) or any other datatype
    // for which the "<=" operator has been defined, including user-defined 
    // datatypes (for example, with tlists).
    //
    // Caution : do not use apifun_checkrange in place of apifun_checkoption.
    // The apifun_checkrange should be used to check that var is in a mathematical 
    // interval, which contains an infinite number of values.
    // The apifun_checkoption should be used to check that var is in a mathematical 
    // set, which contains an finite number of values.
    //
    // Examples
    // // The function takes an argument x such that 0<= x <=1.
    // function y = myfunction ( x )
    //   apifun_checkrange ( "myfunction",x,"x",1,0,1 )
    //   y = sqrt(1-x)
    // endfunction
    // // Calling sequences which work
    // myfunction ( [0.1 0.2 0.8] )
    // // Calling sequences which generate an error
    // // myfunction ( [-0.1 0.2 0.8] )
    // // myfunction ( [0.1 0.2 1.8] )
    //
    // Authors
    // Copyright (C) 2012 - Michael Baudin
    // Copyright (C) 2010 - DIGITEO - Michael Baudin
    //

    [lhsnb,rhsnb]=argn()
    if ( rhsnb <> 6 ) then
        msgfmt = "%s: Unexpected number of input arguments : %d provided while %d are expected."
        errmsg = msprintf(gettext(msgfmt), "apifun_checkrange", rhsnb,6)
        error(errmsg)
    end
    //
    // Checking type of input arguments
    if ( typeof(funname) <> "string" ) then
        errmsg = msprintf(gettext("%s: Expected a string for input argument %s at input #%d, but got %s instead."), "apifun_checkrange", "funname",1,typeof(funname) );
        error(errmsg)
    end
    if ( typeof(varname) <> "string" ) then
        errmsg = msprintf(gettext("%s: Expected a string for input argument %s at input #%d, but got %s instead."), "apifun_checkrange", "varname",3,typeof(varname) );
        error(errmsg)
    end
    if ( typeof(ivar) <> "constant" ) then
        errmsg = msprintf(gettext("%s: Expected a constant for input argument %s at input #%d, but got %s instead."), "apifun_checkrange", "ivar",4,typeof(ivar) );
        error(errmsg)
    end
    //
    // Checking size of input arguments
    if ( or(size(funname) <> [1 1]) ) then
        strcomp = strcat(string(size(funname))," ")
        errmsg = msprintf(gettext("%s: Expected a scalar for input argument %s at input #%d, but got [%s] instead."), "apifun_checkrange", "funname",1,strcomp );
        error(errmsg)
    end
    if ( or(size(varname) <> [1 1]) ) then
        strcomp = strcat(string(size(varname))," ")
        errmsg = msprintf(gettext("%s: Expected a scalar for input argument %s at input #%d, but got [%s] instead."), "apifun_checkrange", "varname",3,varname );
        error(errmsg)
    end
    if ( or(size(ivar) <> [1 1]) ) then
        strcomp = strcat(string(size(ivar))," ")
        errmsg = msprintf(gettext("%s: Expected a scalar for input argument %s at input #%d, but got [%s] instead."), "apifun_checkrange", "ivar",4,strcomp );
        error(errmsg)
    end
    if ( or(size(vmin) <> [1 1]) ) then
        strcomp = strcat(string(size(vmin))," ")
        errmsg = msprintf(gettext("%s: Expected a scalar for input argument %s at input #%d, but got [%s] instead."), "apifun_checkrange", "vmin",5,strcomp );
        error(errmsg)
    end
    if ( or(size(vmax) <> [1 1]) ) then
        strcomp = strcat(string(size(vmax))," ")
        errmsg = msprintf(gettext("%s: Expected a scalar for input argument %s at input #%d, but got [%s] instead."), "apifun_checkrange", "vmax",6,strcomp );
        error(errmsg)
    end
    //
    errmsg = []
    if ( ~and ( vmin <= var & var <= vmax ) ) then
        k = find ( vmin > var | var > vmax | isnan(var) )
        k = k(1)
        errmsg = msprintf(gettext("%s: Expected that all entries of input argument %s at input #%d are in the range [%s,%s], but entry #%d is equal to %s."),funname,varname,ivar,string(vmin),string(vmax),k,string(var(k)));
        error(errmsg);
    end
endfunction

