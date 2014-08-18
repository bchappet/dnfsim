// Copyright (C) 2012 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function y = distfun_genericpdf(varargin)
    // Compute the PDF from the CDF.
    //
    // Calling Sequence
    //    distfun_genericpdf(x,cdffun)
    //    distfun_genericpdf(x,cdffun,iscontinuous)
    //
    // Parameters
    // x : a matrix of doubles, the points where to evaluate the PDF.
    // cdffun : a list, the CDF function and its arguments
    // iscontinuous : a 1-by-1 matrix of booleans, %t for continuous variables, %f for integer variables (default iscontinuous=%t)
    // y : a matrix of doubles, the density
    //
    // Description
    // The distfun_genericpdf function computes the PDF 
    // from a given CDF, by derivation. 
    //
    // The CDF function should have header :
    //
	// <programlisting>
    // p=cdffun(x,a)
    // p=cdffun(x,a,b)
    // p=cdffun(x,a,b,c)
	// </programlisting>
    //
    // The cdffun function must be a list(f,a) or 
    // (f,a,b) or (f,a,b,c) where 
    // f is the PDF function and a, b, or c scalars which are automatically 
    // added at the end of the calling sequence.
    //
    // The performance of this function may be poor.
    //
    // Examples
    // x=3;
    // v1=5;
    // v2=6;
    // distfun_genericpdf(x,list(distfun_fcdf,v1,v2))
    // // Check with the exact PDF
    // distfun_fpdf(x,v1,v2)
    //
    // // Integer distribution
    // x=3;
    // pr = 0.3;
    // computed = distfun_genericpdf(x,list(distfun_geocdf,pr),%f)
    // expected = distfun_geopdf(x,pr)
    //
    // Authors
    //   Copyright (C) 2012 - Michael Baudin
    [lhs,rhs]=argn()
    apifun_checkrhs ( "distfun_genericpdf" , rhs , 2:3 )
    apifun_checklhs ( "distfun_genericpdf" , lhs , 0:1 )
    //
    x=varargin(1)
    __cdffun__=varargin(2)
    iscontinuous=apifun_argindefault ( varargin,3,%t )
    //
    // Check type
    apifun_checktype ( "distfun_genericpdf" , x , "x" , 1 , "constant" )
    apifun_checktype ( "distfun_genericpdf" , __cdffun__ , "cdffun" , 2 , "list" )
    apifun_checktype ( "distfun_genericpdf" , iscontinuous , "iscontinuous" , 3 , "boolean" )
    //
    // Check size
    apifun_checkscalar( "distfun_genericpdf" , iscontinuous , "iscontinuous" , 3 )
    //
    // Analyse the list
    __cdffun__f = __cdffun__(1)
    apifun_checktype ( "distfun_genericpdf" , __cdffun__(1) , "cdffun(1)" , 2 , "function" )
    for i=2:length(__cdffun__)
        varname="cdffun("+string(i)+")"
        apifun_checktype ( "distfun_genericpdf" , __cdffun__(i) , varname , 2 , "constant" )
        apifun_checkscalar ( "distfun_genericpdf" , __cdffun__(i) , varname , 3 )
    end
    //
    nrows = size(x,"r")
    ncols = size(x,"c")
    //
    for i=1:nrows*ncols
        if (iscontinuous) then
            y(i) = derivative(list(__cdffun__f,__cdffun__(2:$)),x(i))
        else
            y1=__cdffun__f(x(i),__cdffun__(2:$))
            y2=__cdffun__f(x(i)-1,__cdffun__(2:$))
            y(i)=y1-y2
        end
    end
    y=matrix(y,nrows,ncols)
endfunction
