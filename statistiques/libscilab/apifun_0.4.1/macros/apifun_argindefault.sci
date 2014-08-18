// Copyright (C) 2010 - DIGITEO - Michael Baudin
//
// This file is released under the terms of the CeCILL_V2 license : http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function argin=apifun_argindefault ( vararglist,ivar,default )
    // Returns the value of an input argument.
    //
    // Calling Sequence
    //   argin=apifun_argindefault(vararglist,ivar,default)
    //
    // Parameters
    //   vararglist : a list, the arguments in the calling sequence
    //   ivar : a 1-by-1 matrix of floating point integers, the index of the input argument in the calling sequence
    //   default : the default value for the argument, if the argument is not provided or if the argument is the empty matrix
    //   argin : the actual value of the argument
    //
    // Description
    // Returns the value of the input argument #ivar, from the list of arguments in vararglist.
    // If this argument was not provided (i.e. if the length of vararglist is lower than ivar), 
    // or if the element at index #ivar in the list is equal to the 
    // empty matrix, returns the default value.
    //
    // This function is designed to be used in function which provide an 
    // optional number of input arguments.
    //
    // Examples
    // // Case where the argument is there
    // vararglist = list("a","b","c");
    // ivar = 2;
    // default = "e";
    // argin=apifun_argindefault ( vararglist,ivar,default ) // "b"
    //
    // // Case where argument is not there
    // vararglist = list("a","b","c");
    // ivar = 4;
    // default = "e";
    // argin=apifun_argindefault ( vararglist,ivar,default ) // "e"
    //
    // // Case where argument is there, but is empty matrix
    // vararglist = list([],"b","c");
    // ivar = 1;
    // default = "e";
    // argin=apifun_argindefault ( vararglist,ivar,default );
    // assert_equal ( argin,"e" );
    //
    // // A practical use-case: a function with 3 optional arguments.
    // function y = myfun ( varargin )
    //   //   y = myfun(x)
    //   //   y = myfun(x,a)
    //   //   y = myfun(x,a,b)
    //   //   y = myfun(x,a,b,c)
    //   //
    //   // Returns y = a*x^b+c
    //   // Defaults are a=5, b=6, c=7.
    //   // If any optional argument is [], we use the default value.
    //   [lhs, rhs] = argn();
    //   if ( rhs < 1 | rhs > 4 ) then
    //     errmsg = msprintf(gettext("%s: Wrong number of input arguments: %d to %d expected.\n"), "myfun", 1,4);
    //     error(errmsg)
    //   end
    //   //
    //   x = varargin(1)
    //   a=apifun_argindefault ( varargin,2,5 )
    //   b=apifun_argindefault ( varargin,3,6 )
    //   c=apifun_argindefault ( varargin,4,7 )
    //   y = a.*x.^b + c
    // endfunction
    // //
    // y = myfun(7) // 5*7^6+7
    // y = myfun(7,2) // 2*7^6+7
    // y = myfun(7,2,3) // 2*7^3+7
    // y = myfun(7,2,3,4) // 2*7^3+4
    // y = myfun(7,[],3,4) // 5*7^3+4
    // y = myfun(7,[],[],4) // 5*7^6+4
    // y = myfun(7,[],[],[]) // 5*7^6+7
    //
    // Authors
    //   Michael Baudin - 2010-2011 - DIGITEO
    //

    [lhsnb,rhsnb]=argn()
    if ( rhsnb <> 3 ) then
        msgfmt = "%s: Wrong number of input argument: %d expected.\n"
        errmsg = msprintf(gettext(msgfmt), "apifun_argindefault", 3)
        error(errmsg)
    end
    if ( lhsnb <> 1 ) then
        msgfmt = "%s: Wrong number of output  argument: %d expected.\n"
        errmsg = msprintf(gettext(msgfmt), "apifun_argindefault", 1)
        error(errmsg)
    end
    //
    // Checking type of input arguments
    if ( typeof(vararglist) <> "list" ) then
        errmsg = msprintf(gettext("%s: Wrong type for argument %d: List expected.\n"), "apifun_argindefault", 2 );
        error(errmsg)
    end
    if ( typeof(ivar) <> "constant" ) then
        errmsg = msprintf(gettext("%s: Wrong type for argument %d: Real matrix expected.\n"), "apifun_argindefault", "ivar",3 );
        error(errmsg)
    end
    //
    // Checking size of input arguments
    if ( or(size(ivar) <> [1 1]) ) then
        strcomp = strcat(string(size(ivar))," ")
        errmsg = msprintf(gettext("%s: Wrong size for input argument #%d: %d-by-%d matrix expected.\n"), "apifun_argindefault", 3, 1, 1 );
        error(errmsg)
    end
    //
    // Returns the value of the input argument #ivar.
    // If this argument was not provided, or was equal to the 
    // empty matrix, returns the default value.
    rhs = length(vararglist)
    if ( rhs < ivar ) then
        argin = default
    else
        if ( typeof(vararglist(ivar))== "constant" ) then
            if ( vararglist(ivar) <> [] ) then
                argin = vararglist(ivar)
            else
                argin = default
            end
        else
            argin = vararglist(ivar)
        end
    end
endfunction



