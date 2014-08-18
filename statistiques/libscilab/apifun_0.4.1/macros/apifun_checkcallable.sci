// Copyright (C) 2010 - 2011 - DIGITEO - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function errmsg=apifun_checkcallable(funname,var,varname,ivar )
    // Generates an error if the variable is not a callable function.
    //
    // Calling Sequence
    //   errmsg=apifun_checkcallable(funname,var,varname,ivar)
    //
    // Parameters
    //   funname : a 1-by-1 matrix of strings, the name of the calling function.
    //   var : a matrix of valid Scilab data type, the variable
    //   varname : a 1-by-1 matrix of string, the name of the variable
    //   ivar : a 1-by-1 matrix of floating point integers, the index of the input argument in the calling sequence
    //   errmsg : a 1-by-1 matrix of strings, the error message. If there was no error, the error message is the empty matrix.
    //
    // Description
    // Checks that the variable var is a function (type 11 or 13), 
    // a compiled function (type 130) or a list.
    // If the variable var is a list, then the first element of the list 
    // must be a function.
    //
    // This function is designed to be used in algorithms which require a callback 
    // (i.e. a callable function) as input argument.
    // This situation typically happens in optimization or integration algorithms, 
    // where the algorithm must call back the function in order to evaluate 
    // at some point x.
    // In this case, it often happens that the objective function requires 
    // extra-arguments. 
    // This is why a "callable" can also be a list, where the 
    // first element in the list is the function and the remaining elements 
    // are extra-arguments, which are appended at the end of the calling sequence.
    //
    // Examples
    // function y=myf(varargin)
    //   // y=myf(x)
    //   // y=myf(x,a)
    //   [lhsnb,rhsnb]=argn()
    //   x = varargin(1)
    //   if ( rhsnb==1) then
    //     a = 2
    //   else
    //     a = varargin(2)
    //   end
    //   y = a*x
    // endfunction
    // 
    // function y=myalgorithm(f,x)
    //   apifun_checkcallable ( "myalgorithm",f,"f",1 )
    //   if ( typeof(f)=="function" ) then
    //     __f__ = f
    //     __args__ = list()
    //   else
    //     __f__ = f(1)
    //     __args__ = f(2:$)
    //   end
    //   y = __f__(x,__args__(:))
    // endfunction
    // // Calling sequences which work
    // y = myalgorithm ( myf,2 );
    // y = myalgorithm ( list(myf,3),2 );
    // // Calling sequences which generate an error
    // y = myalgorithm ( 1,2 )
    // y = myalgorithm ( list(3,4),2 )
    //
    // Authors
    //   Michael Baudin - 2010 - 2011 - DIGITEO
    //

    [lhsnb,rhsnb]=argn()
    if ( rhsnb <> 4 ) then
        msgfmt = "%s: Wrong number of input argument: %d expected.\n"
        errmsg = msprintf(gettext(msgfmt), "apifun_checkcallable", rhsnb,4)
        error(errmsg)
    end
    //
    // Checking type of input arguments
    if ( typeof(funname) <> "string" ) then
        errmsg = msprintf(gettext("%s: Expected a string for input argument %s at input #%d, but got %s instead."), "apifun_checkcallable", "funname",1,typeof(funname) );
        error(errmsg)
    end
    if ( typeof(varname) <> "string" ) then
        errmsg = msprintf(gettext("%s: Expected a constant for input argument %s at input #%d, but got %s instead."), "apifun_checkcallable", "varname",3,typeof(varname) );
        error(errmsg)
    end
    if ( typeof(ivar) <> "constant" ) then
        errmsg = msprintf(gettext("%s: Expected a constant for input argument %s at input #%d, but got %s instead."), "apifun_checkcallable", "ivar",4,typeof(ivar) );
        error(errmsg)
    end
    //
    // Checking size of input arguments
    if ( or(size(funname) <> [1 1]) ) then
        strcomp = strcat(string(size(funname))," ")
        errmsg = msprintf(gettext("%s: Expected a scalar for input argument %s at input #%d, but got [%s] instead."), "apifun_checkcallable", "funname",1,strcomp );
        error(errmsg)
    end
    if ( or(size(varname) <> [1 1]) ) then
        strcomp = strcat(string(size(varname))," ")
        errmsg = msprintf(gettext("%s: Expected a scalar for input argument %s at input #%d, but got [%s] instead."), "apifun_checkcallable", "varname",3,varname );
        error(errmsg)
    end
    if ( or(size(ivar) <> [1 1]) ) then
        strcomp = strcat(string(size(ivar))," ")
        errmsg = msprintf(gettext("%s: Expected a scalar for input argument %s at input #%d, but got [%s] instead."), "apifun_checkcallable", "ivar",4,strcomp );
        error(errmsg)
    end
    //
    errmsg = []
    // Check that var is a function or a list
    if ( and ( type ( var ) <> [11 13 15 130] ) ) then
        lclmsg = "%s: Expected function or list for variable %s at input #%d, but got %s instead."
        errmsg = msprintf(gettext(lclmsg),"assert_typecallable", varname,ivar,typeof(var) );
        error(errmsg);
    end
    if ( type ( var ) == 15 ) then
        // Check that var(1) is a function
        if ( and ( type ( var(1) ) <> [11 13] ) ) then
            lclmsg = "%s: Expected function for variable %s(1) at input #%d, but got %s instead."
            errmsg = msprintf(gettext(lclmsg),"assert_typecallable", varname,ivar,typeof(var) );
            error(errmsg);
        end
    end
endfunction




