// Copyright (C) 2012 - Michael Baudin
// Copyright (C) 2009-2011 - DIGITEO - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function varargout=apifun_expandfromsize(varargin)
    // Expand variables from a size.
    //
    // Calling Sequence
    //   a=apifun_expandfromsize(1,a)
    //   a=apifun_expandfromsize(1,a,v)
    //   a=apifun_expandfromsize(1,a,m,n)
    //   [a,b]=apifun_expandfromsize(2,a,b)
    //   [a,b]=apifun_expandfromsize(2,a,b,v)
    //   [a,b]=apifun_expandfromsize(2,a,b,m,n)
    //   [a,b,c]=apifun_expandfromsize(3,a,b,c)
    //   [a,b,c]=apifun_expandfromsize(3,a,b,c,v)
    //   [a,b,c]=apifun_expandfromsize(3,a,b,c,m,n)
    //
    // Parameters
    //   a : a 1-by-1 or n-by-m matrix of doubles, the first parameter. 
    //   b : a 1-by-1 or n-by-m matrix of doubles, the second parameter. 
    //   c : a 1-by-1 or n-by-m matrix of doubles, the third parameter. 
    //   v : a 1-by-2 or 2-by-1 matrix of doubles, the required size of a, b, c.
    //   v(1) : the required number of rows
    //   v(2) : the required number of columns
    //   m : a 1-by-1 matrix of floating point integers, the required number of rows
    //   n : a 1-by-1 matrix of floating point integers, the required number of columns
    //
    // Description
    // Expand the input variables from a given size.
    //
    // This function may be used to increase the flexibility of 
    // a function, which has arguments of varying dimensions.
    //
    // The calling sequence 
    //
    // <screen>
    // a=apifun_expandfromsize(1,a)
    // </screen>
    //
    // returns in the output <literal>a</literal> the same as the 
    // input <literal>a</literal>.
    // This calling sequence is provided for consistency.
    //
    // The calling sequence 
    //
    // <screen>
    // a=apifun_expandfromsize(1,a,v)
    // </screen>
    //
    // expands the input <literal>a</literal> into the size 
    // required in <literal>v</literal>.
    // In other words, on output, <literal>a</literal> is a <literal>v(1)-by-v(2)</literal> 
    // matrix of doubles, where the entries are filled by copying from the input 
    // <literal>a</literal> argument.
    // 
    // The calling sequence 
    //
    // <screen>
    // a=apifun_expandfromsize(1,a,m,n)
    // </screen>
    //
    // expands the input <literal>a</literal> into the size 
    // required in <literal>m</literal> and <literal>n</literal>.
    // In other words, on output, <literal>a</literal> is a <literal>m-by-n</literal> 
    // matrix of doubles, where the entries are filled by copying from the input 
    // <literal>a</literal> argument.
    // 
    // The calling sequence 
    //
    // <screen>
    //   [a,b]=apifun_expandfromsize(2,a,b)
    // </screen>
    //
    // expands, if necessary, the input arguments 
    // <literal>a</literal> and <literal>b</literal>, 
    // so that, on output, the two matrices have the same size.
    // This common output size is the largest of the two matrices.
    //
    // The calling sequences 
    //
    // <screen>
    //   [a,b]=apifun_expandfromsize(2,a,b,v)
    //   [a,b]=apifun_expandfromsize(2,a,b,m,n)
    // </screen>
    //
    // expand both <literal>a</literal> and <literal>b</literal>  
    // to the size defined in <literal>v</literal> or <literal>m</literal> and 
    // <literal>n</literal>.
    // 
    // The calling sequence 
    //
    // <screen>
    //   [a,b,c]=apifun_expandfromsize(3,a,b,c,...)
    // </screen>
    //
    // expands, if necessary, the input arguments 
    // <literal>a</literal>, <literal>b</literal> and <literal>c</literal>, 
    // so that, on output, the three matrices have the same size.
    //
    // The input arguments are checked so that inconsistent calls 
    // produce errors.
    // For example, if <literal>a</literal> is not a 1-by-1 matrix 
    // of doubles, and if <literal>v</literal> is provided, 
    // then the size of <literal>a</literal> is compared to the 
    // values in <literal>v</literal> : they must match.
    // For example, if <literal>a</literal> is not a 1-by-1 matrix 
    // of doubles, and if <literal>m</literal> and <literal>n</literal> are provided, 
    // then the size of <literal>a</literal> is compared to the 
    // values in <literal>m</literal> and <literal>n</literal>: they must match.
    //
    // Examples
    // // Expand one variable
    // // Does nothing on a:
    // a=apifun_expandfromsize(1,1:6)
    // // Creates a 3-by-5 matrix of zeros :
    // a=apifun_expandfromsize(1,0,[3 5])
    // // Creates a 2-by-3 matrix (i.e. does nothing) :
    // a=apifun_expandfromsize(1,[1 2 3;4 5 6],2,3)
    // // Creates a 2-by-3 matrix of zeros:
    // a=apifun_expandfromsize(1,0,2,3)
    //
    // // Inconsistent call: this produces an error
    // a=apifun_expandfromsize(1,[1 2;4 5],2,3)
    //
    // // Expand two variables
    // // Does note change a or b :
    // [a,b]=apifun_expandfromsize(2,1:6,(1:6).^-1)
    // // Expands b to the size of a:
    // [a,b]=apifun_expandfromsize(2,1:6,2)
    // // Expand a to the size of b:
    // [a,b]=apifun_expandfromsize(2,1,1:6)
    // // Expand both a and b to make 3-by-5 matrices :
    // [a,b]=apifun_expandfromsize(2,0,1,[3 5])
    // // Expand a to the size of b :
    // [a,b]=apifun_expandfromsize(2,1,[1 2 3;4 5 6],2,3)
    // // Expand b to the size of a :
    // [a,b]=apifun_expandfromsize(2,[1 2 3;4 5 6],0.1,2,3)
    // // Expand both a and b to make two 2-by-3 matrices :
    // [a,b]=apifun_expandfromsize(2,0,1,2,3)
    // // Does nothing on a and b :
    // [a,b]=apifun_expandfromsize(2,[1 2 3;4 5 6],[1 2 3;4 5 6],2,3)
    //
    // // Expand two variables
    // // Expands b to the size of a and c:
    // [a,b,c]=apifun_expandfromsize(3,1:6,2,(1:6).^2)
    //
    // // Inconsistent call: this produces an error.
    // // [a,b]=apifun_expandfromsize(2,[1 2;4 5],[1 2 3;4 5 6])
    //
    // // A practical use-case.
    // // The function produces normal random numbers.
    // function X=myfunction(mu,sigma,m,n)
    //     [mu,sigma]=apifun_expandfromsize (2,mu,sigma,m,n)
    //     u=grand(m,n,"unf",0,1)
    //     X=cdfnor("X",mu,sigma,u,1-u)
    // endfunction
    // // Creates a 3-by-5 matrix: both mu and sigma 
    // // are expanded into two 3-by-5 matrices.
    // X=myfunction(1,2,3,5)
    // // Creates a 3-by-5 matrix.
    // // sigma is expanded to a 3-by-5 matrix.
    // X=myfunction(12*ones(3,5),2,3,5)
    //
    // Authors
    // Copyright (C) 2012 - Michael Baudin
    // Copyright (C) 2009-2011 - DIGITEO - Michael Baudin

    [lhs,rhs]=argn()
    apifun_checkrhs("apifun_expandfromsize",rhs,2:6)
    apifun_checklhs("apifun_expandfromsize",lhs,1:3)
    //
    nbpar = varargin(1)
    //
    // Check npar
    apifun_checktype("apifun_expandfromsize",nbpar,"nbpar",1,"constant")
    apifun_checkscalar("apifun_expandfromsize",nbpar,"nbpar",1)
    apifun_checkoption("apifun_expandfromsize",nbpar,"nbpar",1,[1 2 3])
    //
    // Check number of arguments depending on nbpar
    if(nbpar == 1) then
        apifun_checkrhs("apifun_expandfromsize",rhs,2 : 4)
        apifun_checklhs("apifun_expandfromsize",lhs,1)
        a = dfxpndf_1var(varargin(2:$))
        varargout(1) = a
    elseif(nbpar == 2) then
        apifun_checkrhs("apifun_expandfromsize",rhs,3 : 5)
        apifun_checklhs("apifun_expandfromsize",lhs,2)
        [a,b] = dfxpndf_2var(varargin(2:$))
        varargout(1) = a
        varargout(2) = b
    else
        apifun_checkrhs("apifun_expandfromsize",rhs,4 : 6)
        apifun_checklhs("apifun_expandfromsize",lhs,3)
        [a,b,c] = dfxpndf_3var(varargin(2:$))
        varargout(1) = a
        varargout(2) = b
        varargout(3) = c
    end
endfunction
function a = dfxpndf_1var(varargin)
    // Expand 1 variable from its size
    //   a = dfxpndf_1var(a)
    //   a = dfxpndf_1var(a,v)
    //   a = dfxpndf_1var(a,m,n)
    [lhs,rhs]=argn()
    select rhs
    case 1 then
        a = varargin(1)
        apifun_checktype("dfxpndf_1var",a,"a",1,"constant")
    case 2 then
        a = varargin(1)
        v = varargin(2)
        apifun_checktype("dfxpndf_1var",a,"a",1,"constant")
        apifun_checktype("dfxpndf_1var",v,"v",2,"constant")
        apifun_checkscalar("dfxpndf_1var",a,"a",1)
        m = v(1)
        n = v(2)
        a = a * ones(m,n)
    case 3 then
        a = varargin(1)
        m = varargin(2)
        n = varargin(3)
        apifun_checktype("dfxpndf_1var",a,"a",1,"constant")
        apifun_checktype("dfxpndf_1var",m,"m",2,"constant")
        apifun_checktype("dfxpndf_1var",n,"n",2,"constant")
        if(size(a,"*") == 1) then
            a = a * ones(m,n)
        else
            if(size(a,"*") <> 1) then
                apifun_checkdims("dfxpndf_1var",a,"a",1,[m n])
            end
            a=apifun_expandvar(a)
        end
    else
        errmsg = msprintf(gettext("%s: Internal error: unexpected rhs = %d."), "dfxpndf_1var",rhs)
        error(errmsg)
    end
endfunction
function [a,b] = dfxpndf_2var(varargin)
    // Expand 2 variables from their size
    //   [a,b] = dfxpndf_2var(a,b)
    //   [a,b] = dfxpndf_2var(a,b,v)
    //   [a,b] = dfxpndf_2var(a,b,m,n)
    [lhs,rhs]=argn()
    select rhs
    case 2 then
        a = varargin(1)
        b = varargin(2)
        apifun_checktype("dfxpndf_2var",a,"a",1,"constant")
        apifun_checktype("dfxpndf_2var",b,"b",2,"constant")
        [a,b]=apifun_expandvar(a,b)
    case 3 then
        a = varargin(1)
        b = varargin(2)
        v = varargin(3)
        apifun_checktype("dfxpndf_2var",a,"a",1,"constant")
        apifun_checktype("dfxpndf_2var",b,"b",2,"constant")
        apifun_checktype("dfxpndf_2var",v,"v",3,"constant")
        m = v(1)
        n = v(2)
        if(( size(a,"*") == 1) &(size(b,"*") == 1)) then
            a = a * ones(m,n)
            b = b * ones(m,n)
        else
            if(size(a,"*") <> 1) then
                apifun_checkdims("dfxpndf_2var",a,"a",1,[m n])
            end
            if(size(b,"*") <> 1) then
                apifun_checkdims("dfxpndf_2var",b,"b",2,[m n])
            end
            [a,b]=apifun_expandvar(a,b)
        end
    case 4 then
        a = varargin(1)
        b = varargin(2)
        m = varargin(3)
        n = varargin(4)
        apifun_checktype("dfxpndf_2var",a,"a",1,"constant")
        apifun_checktype("dfxpndf_2var",b,"b",2,"constant")
        apifun_checktype("dfxpndf_2var",m,"m",3,"constant")
        apifun_checktype("dfxpndf_2var",n,"n",4,"constant")
        if(( size(a,"*") == 1) &(size(b,"*") == 1)) then
            a = a * ones(m,n)
            b = b * ones(m,n)
        else
            if(size(a,"*") <> 1) then
                apifun_checkdims("dfxpndf_2var",a,"a",1,[m n])
            end
            if(size(b,"*") <> 1) then
                apifun_checkdims("dfxpndf_2var",b,"b",2,[m n])
            end
            [a,b]=apifun_expandvar(a,b)
        end
    else
        errmsg = msprintf(gettext("%s: Internal error: unexpected rhs = %d."), "dfxpndf_2var",rhs)
        error(errmsg)
    end
endfunction

function [a,b,c] = dfxpndf_3var(varargin)
    // Expand 3 variables from their size
    //   [a,b,c] = dfxpndf_3var(a,b,c)
    //   [a,b,c] = dfxpndf_3var(a,b,c,v)
    //   [a,b,c] = dfxpndf_3var(a,b,c,m,n)
    [lhs,rhs]=argn()
    select rhs
    case 3 then
        a = varargin(1)
        b = varargin(2)
        c = varargin(3)
        apifun_checktype("dfxpndf_3var",a,"a",1,"constant")
        apifun_checktype("dfxpndf_3var",b,"b",2,"constant")
        apifun_checktype("dfxpndf_3var",c,"c",3,"constant")
        [a,b,c]=apifun_expandvar(a,b,c)
    case 4 then
        a = varargin(1)
        b = varargin(2)
        c = varargin(3)
        v = varargin(4)
        apifun_checktype("dfxpndf_3var",a,"a",1,"constant")
        apifun_checktype("dfxpndf_3var",b,"b",2,"constant")
        apifun_checktype("dfxpndf_3var",c,"c",3,"constant")
        apifun_checktype("dfxpndf_3var",v,"v",4,"constant")
        m = v(1)
        n = v(2)
        if(( size(a,"*") == 1) &(size(b,"*") == 1) &(size(c,"*") == 1)) then
            a = a * ones(m,n)
            b = b * ones(m,n)
            c = c * ones(m,n)
        else
            if(size(a,"*") <> 1) then
                apifun_checkdims("dfxpndf_3var",a,"a",1,[m n])
            end
            if(size(b,"*") <> 1) then
                apifun_checkdims("dfxpndf_3var",b,"b",2,[m n])
            end
            if(size(c,"*") <> 1) then
                apifun_checkdims("dfxpndf_3var",c,"c",3,[m n])
            end
            [a,b,c]=apifun_expandvar(a,b,c)
        end
    case 5 then
        a = varargin(1)
        b = varargin(2)
        c = varargin(3)
        m = varargin(4)
        n = varargin(5)
        apifun_checktype("dfxpndf_3var",a,"a",1,"constant")
        apifun_checktype("dfxpndf_3var",b,"b",2,"constant")
        apifun_checktype("dfxpndf_3var",c,"c",3,"constant")
        apifun_checktype("dfxpndf_3var",m,"m",4,"constant")
        apifun_checktype("dfxpndf_3var",n,"n",5,"constant")
        if(( size(a,"*") == 1) &(size(b,"*") == 1) &(size(c,"*") == 1)) then
            a = a * ones(m,n)
            b = b * ones(m,n)
            c = c * ones(m,n)
        else
            if(size(a,"*") <> 1) then
                apifun_checkdims("dfxpndf_3var",a,"a",1,[m n])
            end
            if(size(b,"*") <> 1) then
                apifun_checkdims("dfxpndf_3var",b,"b",2,[m n])
            end
            if(size(c,"*") <> 1) then
                apifun_checkdims("dfxpndf_3var",c,"c",3,[m n])
            end
            [a,b,c]=apifun_expandvar(a,b,c)
        end
    else
        errmsg = msprintf(gettext("%s: Internal error: unexpected rhs = %d."), "dfxpndf_3var",rhs)
        error(errmsg)
    end
endfunction
