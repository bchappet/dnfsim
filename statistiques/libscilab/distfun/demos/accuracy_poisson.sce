// Copyright (C) 2012 - Michael Baudin
// Copyright (C) 2009 - DIGITEO - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

// Assessing the quality of the Normal distribution function
// References
//   Yalta, A. T. 2008. The accuracy of statistical distributions in Microsoft® 
//   Excel 2007. Comput. Stat. Data Anal. 52, 10 (Jun. 2008), 4579-4586. 
//   DOI= http://dx.doi.org/10.1016/j.csda.2008.03.005 
//   Computation of Statistical Distributions (ELV), Leo Knüsel, 1989
//

//
// Creating functions to accurately evaluate distributions is nontrivial.
// Example: The Poisson distribution.
//

function test_poissonPDF()

    mprintf("\n");
    mprintf("This demo shows that it is not trivial\n");
    mprintf("to create an accurate Poisson distribution.\n");
    mprintf("\n");
    mprintf("Let''s define a mathematically correct implementation: poissonpdfWrong.\n");

    function p = poissonpdfWrong ( k , lambda )
        p = exp(-lambda)*lambda^k/factorial(k)
    endfunction

    mprintf("Let''s define a mathematically and numerically better implementation: poissonpdf.\n");

    // poissonpdf --
    //   Poisson distribution function
    //   Not a naive implementation :
    //   avoid overflows and vectorize.
    function p = poissonpdf ( k , lambda )
        p = exp ( - lambda )
        for i = 1 : k
            p = p * lambda / i
        end
    endfunction

    mprintf("Let''s define a mathematically and numerically better and fast implementation: poissonpdf.\n");

    function p = poissonpdfFast ( k , lambda )
        // Used vectorized statements.
        // Drawback : may consume a lot of memory.
        v = lambda./(1:k);
        p = exp(-lambda)*prod(v)
    endfunction


    //
    // FACTORIAL_LOG returns the logarithm of N!.
    //
    //  Definition:
    //    N! = Product ( 1 <= I <= N ) I
    //
    //  Method:
    //    N! = Gamma(N+1).
    //
    //  Modified:
    //    11 September 2004
    //
    //  Author:
    //    John Burkardt
    //    Scilab version : Michael Baudin
    //
    //  Parameters:
    //    Input, integer N, the argument of the function.
    //    0 <= N.
    //
    //    Output, real FACTORIAL_LOG, the logarithm of N!.
    //
    function value = factorial_log ( n )

        if ( n < 0 )
            fprintf ( 1, '\n' );
            fprintf ( 1, 'FACTORIAL_LOG - Fatal error!\n' );
            fprintf ( 1, '  N < 0.\n' );
            error ( 'FACTORIAL_LOG - Fatal error!' );
        end
        value = sum(log(2:n))
    endfunction

    mprintf("Let''s define a mathematically and numerically correct and fast implementation: poissonpdfBest.\n");

    function p = poissonpdfBest ( k , lambda )
        // Vectorized and consumes no memory
        logp  = -lambda + k*log(lambda) - factorial_log(k)
        p = exp(logp)
    endfunction

    //
    // Table 4
    // Check Poisson distribution with parameters (lambda, k)
    //
    function checkPoisson ( k, lambda, expected ) 
        computed = poissonpdf ( k , lambda )
        digits = assert_computedigits ( computed , expected );
        mprintf("poissonpdf: k=%d, lambda=%d, digits=%.1f\n",k,lambda,digits)
    endfunction

    function checkPoissonWrong ( k, lambda, expected ) 
        computed = poissonpdfWrong ( k , lambda )
        digits = assert_computedigits ( computed , expected );
        mprintf("poissonpdfWrong: k=%d, lambda=%d, digits=%.1f\n",k,lambda,digits)
    endfunction

    function checkPoissonFast ( k, lambda, expected ) 
        computed = poissonpdfFast ( k , lambda )
        digits = assert_computedigits ( computed , expected );
        mprintf("poissonpdfFast: k=%d, lambda=%d, digits=%.1f\n",k,lambda,digits)
    endfunction

    function checkPoissonBest ( k, lambda, expected ) 
        computed = poissonpdfBest ( k , lambda )
        digits = assert_computedigits ( computed , expected );
        mprintf("poissonpdfBest: k=%d, lambda=%d, digits=%.1f\n",k,lambda,digits)
    endfunction

    mprintf("\nSee the results of the wrong implementation...\n\n");

    checkPoissonWrong ( 0   ,   200   , 1.38390e-87 );
    checkPoissonWrong ( 103 ,   200   , 1.41720e-14 );
    checkPoissonWrong ( 104 ,   200   , 2.72538e-14 );
    checkPoissonWrong ( 133 ,   200   , 1.01322e-07 );
    checkPoissonWrong ( 134 ,   200   , 1.51227e-07 );
    checkPoissonWrong ( 200 ,   200   , 2.81977e-02 );
    checkPoissonWrong ( 314 ,   200   , 2.23568e-14 );
    checkPoissonWrong ( 315 ,   200   , 1.41948e-14 );
    checkPoissonWrong ( 400 ,   200   , 5.58069e-36 );
    checkPoissonWrong ( 900 ,   200   , 1.73230e-286);

    mprintf("\n... see the results of more accurate implementation...\n\n");

    checkPoisson ( 0   ,   200   , 1.38390e-87 );
    checkPoisson ( 103 ,   200   , 1.41720e-14 );
    checkPoisson ( 104 ,   200   , 2.72538e-14 );
    checkPoisson ( 133 ,   200   , 1.01322e-07 );
    checkPoisson ( 134 ,   200   , 1.51227e-07 );
    checkPoisson ( 200 ,   200   , 2.81977e-02 );
    checkPoisson ( 314 ,   200   , 2.23568e-14 );
    checkPoisson ( 315 ,   200   , 1.41948e-14 );
    checkPoisson ( 400 ,   200   , 5.58069e-36 );
    checkPoisson ( 900 ,   200   , 1.73230e-286);

    mprintf("\n... and see the results of good implementation !\n\n");

    checkPoissonWrong ( 800 ,  800   , 0.0141033);
    checkPoisson ( 800 ,  800   , 0.0141033);
    checkPoissonFast ( 800 ,  800   , 0.0141033);
    checkPoissonBest ( 800 ,  800   , 0.0141033);

    stacksize("max");
    tmax = 0.1;

    mprintf("\n");
    mprintf("See performances of various functions.\n");
    mprintf("A greater value of k is better.\n");
    mprintf("\n");
    mprintf("See performances of poissonpdf...\n");
    k = 1000;
	lambda = 100;
    while (%t)
        tic(); 
        p = poissonpdf ( k , lambda ); 
        t = toc();
        k = 1.2*k;
        if ( t > tmax ) then
            break
        end
    end
    mprintf("poissonpdf - Greatest performance : k = %d, lambda=%d, t= %.2f (s)",..
	k,lambda,t);

    mprintf("\nSee performances of poissonpdfFast...\n");
    k = 1;
	lambda = 100;
    while (%t)
        tic(); 
        p = poissonpdfFast ( k , lambda ); 
        t = toc();
        k = 1.2*k;
        if ( t > tmax ) then
            break
        end
    end
    mprintf("poissonpdfFast - Greatest performance : k = %d, lambda=%d, t= %.2f (s)",..
	k,lambda,t);

    mprintf("\nSee performances of poissonpdfBest...\n");
    k = 1;
	lambda = 100;
    while (%t)
        tic(); 
        p = poissonpdfBest ( k , 100 ); 
        t = toc();
        k = 1.2*k;
        if ( t > tmax ) then
            break
        end
    end
    mprintf("poissonpdfBest - Greatest performance : k = %d, lambda=%d, t= %.2f (s)",..
	k,lambda,t);


endfunction test_poissonPDF
test_poissonPDF();
clear test_poissonPDF

//
// Load this script into the editor
//
filename = "accuracy_poisson.sce";
dname = get_absolute_file_path(filename);
editor ( fullfile(dname,filename) );
