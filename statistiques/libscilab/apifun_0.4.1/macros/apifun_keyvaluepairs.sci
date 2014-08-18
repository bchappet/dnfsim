// Copyright (C) 2012 - Michael Baudin
// Copyright (C) 2011 - DIGITEO - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt



function options=apifun_keyvaluepairs (default,varargin)
    // Returns options from key-value pairs.
    //
    // Calling Sequence
    // options=apifun_keyvaluepairs(default)
    // options=apifun_keyvaluepairs(default,key1,value1)
    // options=apifun_keyvaluepairs(default,key1,value1,key2,value2,...)
    //
    // Parameters
    //   default : a struct, the default values of the options
    //   key1 : a 1-by-1 matrix of strings, the name of the first option
    //   value1 : the value of the first option
    //   options : a struct, the actual values of the options
    //
    // Description
    // The function manages a set of options identified by 
    // their keys. 
    // The key-value pairs are processed by replacing any actual 
    // option by its actual value. 
    // This function is designed to be used within functions 
    // having optional arguments. 
    //
    // The <literal>default</literal> input argument must contain the default values 
    // of the arguments. 
    // Its fields define the set of valid options. 
    // 
    // The calling sequence <literal>options=apifun_keyvaluepairs (default)</literal>
    // returns <literal>options</literal> as a copy of <literal>default</literal>.
    //
    // The calling sequence <literal>options=apifun_keyvaluepairs (default,key1,value1)</literal>
    // first checks that <literal>key1</literal> is a valid key, 
    // by comparing it to the list of fields of the <literal>default</literal> data structure. 
    // If the field is unknown, an error is generated.
    // If the field is valid, the associated field of <literal>options</literal> 
    // is set to <literal>value1</literal>.
    //
    // The <literal>value1</literal> variable can have any valid data type. 
    // There is no check that <literal>value1</literal> has the same type 
    // as the associated field in <literal>default</literal>: 
    // the check of the type is left to the user of <literal>apifun_keyvaluepairs</literal>.
    //
    // The other key-value pairs are treated in the same way.
    //
    // Examples
    // // A theoretical use-case
    // //
    // // Set the defaults
    // default.a = 1;
    // default.b = 1;
    // default.c = 1;
    // options=apifun_keyvaluepairs (default)
    // options=apifun_keyvaluepairs (default,"a",2)
    // options=apifun_keyvaluepairs (default,"b",12)
    // options=apifun_keyvaluepairs (default,"b",12,"a",999)
    // options=apifun_keyvaluepairs (default,"c",-1,"b",12,"a",999)
    // // Error cases:
    // options=apifun_keyvaluepairs (default,"c")
    // options=apifun_keyvaluepairs (default,"d",2)
    //
    // // A practical use-case: a function with 3 optional arguments.
    // // y = myfunction(x)
    // // y = myfunction(x,key1,value1,...)
    // // Available keys: 
    // // "a" (default a=1)
    // // "b" (default b=1)
    // // "c" (default c=1)
    // function y = myfunction(x,varargin)
    //     //
    //     // 1. Set the defaults
    //     default.a = 1
    //     default.b = 1
    //     default.c = 1
    //     //
    //     // 2. Manage (key,value) pairs
    //     options=apifun_keyvaluepairs (default,varargin(1:$))
    //     //
    //     // 3. Get parameters
    //     a = options.a
    //     b = options.b
    //     c = options.c
    //     // TODO : check the types, size and content of a, b, c
    //     y = a*x^2+b*x+c
    // endfunction
    // //
    // y = myfunction(1)
    // expected = 1*1^2+1*1+1
    // //
    // y = myfunction(1,"a",2)
    // expected = 2*1^2+1*1+1
    // //
    // y = myfunction(1,"b",3)
    // expected = 1*1^2+3*1+1
    // //
    // y = myfunction(1,"c",4,"b",3)
    // expected = 1*1^2+3*1+4
    // //
    // // Error cases:
    // y = myfunction(1,"d")
    // y = myfunction(1,"d",4)
    //
    // Authors
    //   Michael Baudin - 2011 - DIGITEO
    //   Michael Baudin - 2012
    //
    // Bibliography
    // "Why using key=value syntax to manage input arguments is not a good idea", Michael Baudin, 2011, http://wiki.scilab.org/

    [lhs,rhs]=argn();
    if rhs<1 then
        error(msprintf(gettext("%s: Wrong number of input arguments: At least %d expected.\n"),"apifun_keyvaluepairs",1));
    end
    //
    if ( typeof(default) <> "st" ) then
        errmsg = msprintf(gettext("%s: Wrong type for argument %d: Struct expected.\n"), "apifun_keyvaluepairs",1);
        error(errmsg)
    end
    if modulo(rhs,2)<>1 then
        errmsg = msprintf(gettext("%s: Even number of arguments."), "apifun_keyvaluepairs");
        error(errmsg)
    end
    //
    nbkeys = length(varargin)/2;
    ivar = 0;
    options = default
    defaultnames = fieldnames(default)
    for i=1:nbkeys
        //
        // 1. Get the (key,value) pair
        ivar = ivar + 1;
        key = varargin(ivar);
        ivar = ivar + 1;
        // Use funcprot to enable the set of a function into the variable "value".
        // If not, a warning message is triggered, when a double value 
        // is stored into "value" after a function has already been 
        // stored in it.
        if ( i>1 & typeof(value)=="function") then
            prot = funcprot();
            funcprot(0);
            funcmode = %t
        else
            funcmode = %f
        end
        value = varargin(ivar);
        if ( funcmode ) then
            funcprot(prot);
        end
        //
        // 2. Check that the key exists
        i = find(key==defaultnames)
        if ( i == [] ) then
            lclmsg = "%s: Unknown key: %s."
            errmsg = msprintf(gettext(lclmsg), "apifun_keyvaluepairs",key);
            error(errmsg)
        end
        //
        // 3. Process the key
        instr = "options."+key+"=value"
        ierr = execstr(instr,"errcatch")
        if (ierr<>0) then
            lamsg = lasterror()
            lclmsg = "%s: Error while setting the field %s: %s."
            errmsg = msprintf(gettext(lclmsg), "apifun_keyvaluepairs",key,lamsg);
            error(errmsg)
        end
    end
endfunction
