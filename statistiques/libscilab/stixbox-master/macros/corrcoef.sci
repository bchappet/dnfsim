// Copyright (C) 2013 - Michael Baudin
// Copyright (C) 2009 - CEA - Jean-Marc Martinez
// Copyright (C) 2009-2010 - DIGITEO - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function R=corrcoef(varargin)
    // Correlation coefficient
    //
    // Calling Sequence
    //   R=corrcoef(X)
    //   R=corrcoef(x,y)
    //
    // Parameters
    // X : a n-by-nbvar matrix of doubles, where nbvar is the number of variables
    // x: a n-by-1 or 1-by-n matrix of doubles
    // y: a n-by-1 or 1-by-n matrix of doubles
    // R: a 1-by-1 or nbvar-by-nbvar matrix of doubles, the linear correlation coefficient.
    //
    // Description
    //   <literal>corrcoef(x,y)</literal> returns the linear correlation 
    //   coefficient of x and y.
    //   This is sometimes called Pearson's product-moment coefficient.
    //
    //   The correlation coefficient between x and y is defined by 
    //
    // <latex>
    // \begin{eqnarray}
    // R = \frac{\sum_{i=1}^n (x_i-\bar{x})(y_i-\bar{y})}
    //     {\sqrt{\sum_{i=1}^n (x_i-\bar{x})^2 \sum_{i=1}^n (y_i-\bar{y})^2}}
    // \end{eqnarray}
    // </latex>
    //
    //   <literal>corrcoef(X)</literal> returns the linear correlation 
    //   coefficient between coumns of X.
    //   In this case, the correlation coefficient R and the 
    //   covariance matrix C are related by
    //
    // <latex>
    // \begin{eqnarray}
    // R(i,j) = \frac{C(i,j)}{\sqrt{C(i,i)C(j,j)}}
    // \end{eqnarray}
    // </latex>
    //
    // for i,j=1,2,...,nbvar.
    //
    // The corrcoef function is compatible with Matlab.
    //
    // Examples
    // // Source : [1], Example 2.6a
    // x = [24.2;22.7;30.5;28.6;25.5;32;28.6;26.5;25.3;26;24.4;24.8;20.6;..
    // 25.1;21.4;23.7;23.9;25.2;27.4;28.3;28.8;26.6];
    // y = [25;31;36;33;19;24;27;25;16;14;22;23;20;25;25;23;27;30;33;32;35;24];
    // expected = 0.4189
    // R = corrcoef ( x , y )
    // // Draw the scatter plot
    // scf();
    // plot(x,y,"bo")
    //
    // // Source : [1], Example 2.6b
    // x = [12 16 13 18 19 12 18 19 12 14];
    // y = [73 67 74 63 73 84 60 62 76 71];
    // R = corrcoef ( x , y )
    // expected = -0.7638
    //
    // // For properly chosen data, the linear correlation 
    // // coefficient can be close to zero.
    // n = 1000;
    // x = linspace(-%pi/2,3*%pi/2,n);
    // y = sin(x)+distfun_normrnd(0,0.5,1,n);
    // R = corrcoef ( x , y )
    // // This does not imply that there is no dependency
    // // between the variables
    // scf();
    // plot(x,y,"bo")
    //
    // // See with one argument
    // [X,txt] = getdata(1);
    // R=corrcoef(X)
    // expected=[
    //     1.           0.4615668    0.6934031  
    //     0.4615668    1.           0.3544662  
    //     0.6934031    0.3544662    1.         
    // ];
    //
    // // Compute a correlation matrix
    // // X is uncorrelated
    // X = distfun_normrnd(0,1,30,4);
    // // Put some correlation
    // X(:,4) = sum(X,"c");
    // R=corrcoef(X)
    // // See that X4 is correlated with other Xi, 
    // // since the 4th column and 4th row have higher 
    // // values.
    //
    // Authors
    // Copyright (C) 2013 - Michael Baudin
    // Copyright (C) 2009 - CEA - Jean-Marc Martinez
    // Copyright (C) 2009-2010 - DIGITEO - Michael Baudin
    //
    // Bibliography
    // [1] "Introduction to probability and statistics for engineers and scientists.", Chapter 2 Descriptive statistics, Sheldon Ross
    // [2] http://en.wikipedia.org/wiki/Correlation_and_dependence
    // [3] Pearson product-moment correlation coefficient, http://en.wikipedia.org/wiki/Pearson_product-moment_correlation_coefficient

    [lhs, rhs] = argn();
    apifun_checkrhs ( "corrcoef" , rhs , 1 : 2 )
    apifun_checklhs ( "corrcoef" , lhs , 0:1 )
    //
    x=varargin(1)
    y=apifun_argindefault(varargin,2,[])
    //
    // Check input arguments
    //
    // Check type
    apifun_checktype ( "corrcoef" , x ,   "x" ,  1 , "constant" )
    if (y<>[]) then
        apifun_checktype ( "corrcoef" , y ,   "y" ,  2 , "constant" )
    end
    //
    // Check size
    if (y<>[]) then
        apifun_checkvector ( "corrcoef" , x , "x" , 1 , size(x,"*") )
        apifun_checkdims ( "corrcoef" , y ,    "y" ,    2 , size(x) )
        apifun_checkvector ( "corrcoef" , y , "y" , 1 , size(y,"*") )
    end
    //
    // Check content
    // Nothing to check
    //
    if (rhs==1) then
        C=cov(x);
        D=sqrt(diag(C)*diag(C)');
        R=C./D
    else
        x = x(:)
        y = y(:)
        x = x - mean(x)
        y = y - mean(y)
        sx = sqrt(sum(x.^2))
        sy = sqrt(sum(y.^2))
        R = x'*y / sx / sy
    end
endfunction

