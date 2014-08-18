// Copyright (C) 2013 - Michael Baudin
// Copyright (C) 2010 - DIGITEO - Michael Baudin
// Copyright (C) 1993 - 1995 - Anders Holtsberg
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function [p,S] = polyfit(x,y,n)
    // Polynomial curve fitting
    //
    // Calling Sequence
    //   p = polyfit(x,y,n)
    //   [p,S] = polyfit(x,y,n)
    //
    // Parameters
    //   x : a nx-by-mx matrix of doubles
    //   y : a nx-by-mx matrix of doubles
    //   n : a 1-by-1 matrix of doubles, integer value, n>=0
    //   p : a 1-by-(n+1) matrix of doubles, the coefficients of the polynomial
    //   S : a structure to estimate the errors of evaluations
    //
    // Description
    // <literal>polyfit(x,y,n)</literal> finds the coefficients of a polynomial p(x) of
    // degree n that fits the data, p(x(i)) to y(i), in a least-squares sense.
    //
    // These coefficients are ordered with powers in decreasing order:
    //
    // <latex>
    // p(x) = p_1 x^n + p_2 x^{n-1} + ... + p_n x + p_{n+1}.
    // </latex>
    //
    // <literal>[p,s]=polyfit(x,y,n)</literal> returns the polynomial coefficients p and a
    // data structure S for use with <literal>polyval</literal> to produce error 
    // estimates on predictions.
    //
    // Examples
    // x = linspace(0,%pi,10)';
    // y = sin(x)
    // p = polyfit(x,y,3)
    // // Evaluate :
    // f = polyval(p,x)
    // // Compare with a table
    // disp([x y f abs(f-y)])
    // // Compare with a polynomial
    // ff = p(1)*x.^3+p(2)*x.^2+p(3)*x+p(4)
    // // Compare with a graphics
    // // Notice that the fit is good up to x=%pi, 
    // // then the fit is not good anymore.
    // x = linspace(0,4,100)';
    // y = sin(x);
    // f = polyval(p,x);
    // scf();
    // plot(x,y,"r-")
    // plot(x,f,"b-")
    // plot([%pi %pi],[-1 1],"g-")
    // legend(["Sin","Polynomial","Limit"],"in_lower_left");
    // xtitle("Degree 3 least squares polynomial","X","F(x)")
    //
    // // Source [2]
    // // Caution : the urban definition changes in 1950
    // cdate=(1790:10:1990)';
    // pop=[
    //     3.929214   
    //     5.308483   
    //    7.239881   
    //    9.638453   
    //    12.860702  
    //    17.063353  
    //    23.191876  
    //    31.443321  
    //    38.558371  
    //    50.189209  
    //    62.979766  
    //    76.212168  
    //    92.228496  
    //    106.02154  
    //    123.20262  
    //    132.16457  
    //    151.3258   
    //    179.32317  
    //    203.30203  
    //    226.5422   
    //    248.70987  
    // ];
    // scf();
    // // Plot the data
    // plot(cdate,pop,"+")
    // // Calculate fit parameters
    // [p,S] = polyfit(cdate,pop,2);
    // // Evaluate the fit
    // pop_fit = polyval(p,cdate,S);
    // // Plot the fit
    // plot(cdate,pop_fit,"g-")
    // // Annotate the plot
    // legend("Polynomial Model","Data","Location","in_upper_left");
    // xtitle("","Census Year","Population (millions)");
    // 
    // Authors
    // Copyright (C) 2013 - Michael Baudin
    // Copyright (C) 2010 - DIGITEO - Michael Baudin
    // Copyright (C) 1993 - 1995 - Anders Holtsberg
    //
    // Bibliography
    // [1] http://en.wikipedia.org/wiki/Polynomial_interpolation
    // [2] USA population : Population: 1790 to 1990, http://www.census.gov/population/censusdata/table-4.pdf
    // [3] http://www.mathworks.fr/fr/help/matlab/data_analysis/programmatic-fitting.html
    // [4] Numerical Computing with MATLAB, Cleve Moler, 2004
    // 

    [lhs,rhs]=argn()
    apifun_checkrhs ( "polyfit" , rhs , 3:3 )
    apifun_checklhs ( "polyfit" , lhs , 1:2 )
    //
    // Check Type
    apifun_checktype ( "polyfit" , x , "x" , 1 , "constant" )
    apifun_checktype ( "polyfit" , y , "y" , 2 , "constant" )
    apifun_checktype ( "polyfit" , n , "n" , 3 , "constant" )
    //
    // Check Size
    nx=size(x,"*")
    apifun_checkvector ( "polyfit" , x , "x" , 1 , nx )
    apifun_checkvector ( "polyfit" , y , "y" , 2 , nx )
    apifun_checkscalar ( "polyfit" , n , "n" , 3 )
    //
    // Check content
    apifun_checkgreq ( "polyfit" , n , "n" , 3 , 0 )
    apifun_checkflint ( "polyfit" , n , "n" , 3 )
    //
    x=x(:)
    y=y(:)
    //
    vander=makematrix_vandermonde(x,n+1)
    // Reverse the columns
    vander=vander(:,$:-1:1)
    [Q,R] = qr(vander,"e");
    Q = Q(:,1:size(R,2))
    R = R(1:size(R,2),:)
    // 
    // Workaround for bug #9196
    // http://bugzilla.scilab.org/show_bug.cgi?id=9196
    //p = R\(Q'*y); 
    p = linalg_dgesv(R,Q'*y)
    r = y - vander*p; //residuals
    p=p';
    freed = nx - (n+1); //degree of freedom

    //on return : choleski factor and the norm of residuals
    S.R=R
    S.df=freed
    S.normr=norm(r)
endfunction
