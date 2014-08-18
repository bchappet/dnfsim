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
// Example: The hypergeometric distribution.
//

function test_hypergeometric()

    mprintf("\nIt is non trivial to create an accurate hypergeometric distribution.\n");
    mprintf("\nLet''s define a mathematically correct implementation: hypergeopdf_wrong.\n");

    //
    // The hypergeometric probability function
    //
    //      p = hypergeopdf_wrong(k,n,K,N)
    //	Input	k,n,K,N nonnegative integers such that 
    //			n<= N, K<= N and n-k<= N-K	
    //			N is the total number of elements of the population
    //			n is the number of elements which are randomly
    //			  extracted
    //			K is the number of elements of the population which 
    //			  have the studied property
    //
    //	Output  p	probability Prob(X=k) where X is an hypergeometric
    //			random variable with parameters n,K,N
    //       Anders Holtsberg, 18-11-93
    //       Copyright (c) Anders Holtsberg
    //   last update: dec 2001 (jpc)
    //
    // Notes
    //   From Stixbox 1.2.5
    //   This implementation is NOT accurate.
    //
    // Reference 
    //   "An Accurate Computation of the Hypergeometric Distribution Function", 
    //   Trong Wu, ACM Transactions on Mathematical Software, Vol. 19, No. 1, March 1993, Pages 33-43 
    function p=hypergeopdf_wrong(k,n,K,N)

        if or(round(n)~=n | round(N)~=N |round(k)~=k |round(K)~=K)
            error('hypergeopdf_wrong: requires integer arguments');
        end

        if or(n>N | K>N | K<0) then
            error('Incompatible input arguments');
        end

        p = bincoef(k,K) .* bincoef(n-k,N-K) ./ bincoef(n,N);
    endfunction

    //  Binomial coefficients
    //         k = bincoef(n,N)
    //       Anders Holtsberg, 13-05-94
    //       Copyright (c) Anders Holtsberg
    // 	Input	n,N 	scalar or matrix with common size, such that 0<=n<=N
    //
    //	Output	k	matrix of binomial coefficients
    //			Gamma(N+1)/(Gamma(n+1)Gamma(N-n+1))
    // last update: dec 2001 (jpc)
    function k=bincoef(n,N)
        k = exp(gammaln(N+1)-gammaln(n+1)-gammaln(N-n+1));
        if and(round(n)==n) & and(round(N)==N) then
            k = round(k)
        end
    endfunction


    mprintf("Let''s define a mathematically AND numerically correct implementation : hypergeopdf.\n");

    //
    // hypergeopdf evaluates the Hypergeometric PDF.
    //
    //  Formula:
    //    PDF(X)(N,M,L) = C(M,X) * C(L-M,N-X) / C(L,N).
    //
    //  Definition:
    //    PDF(X)(N,M,L) is the probability of drawing X white balls in a
    //    single random sample of size N from a population containing
    //    M white balls and a total of L balls.
    //
    //  Modified:
    //    12 September 2004
    //
    //  Author:
    //    John Burkardt
    //    Scilab version : Michael Baudin
    //
    //  Parameters:
    //    Input, integer X, the desired number of white balls.
    //    0 <= X <= N, usually, although any value of X can be given.
    //
    //    Input, integer N, the number of balls selected.
    //    0 <= N <= L.
    //
    //    Input, integer M, the number of white balls in the population.
    //    0 <= M <= L.
    //
    //    Input, integer L, the number of balls to select from.
    //    0 <= L.
    //
    //    Output, real PDF, the probability of exactly K white balls.
    //
    function pdf = hypergeopdf ( x, n, m, l )
        //
        //  Special cases.
        //
        if ( x < 0 )
            pdf = 1.0;
        elseif ( n < x )
            pdf = 0.0;
        elseif ( m < x )
            pdf = 0.0;
        elseif ( l < x )
            pdf = 0.0;
        elseif ( n == 0 )
            if ( x == 0 )
                pdf = 1.0;
            else
                pdf = 0.0;
            end
        else
            c1 = binomial_coef_log (   m,   x );
            c2 = binomial_coef_log ( l-m, n-x );
            c3 = binomial_coef_log ( l,   n   );
            pdf_log = c1 + c2 - c3;
            pdf = exp ( pdf_log );
        end
    endfunction


    //
    // BINOMIAL_COEF_LOG computes the logarithm of the Binomial coefficient.
    //
    //  Formula:
    //    CNK_LOG = LOG ( C(N,K) ) = LOG ( N! / ( K! * (N-K)! ) ).
    //
    //  Modified:
    //    03 September 2004
    //
    //  Author:
    //    John Burkardt
    //    Scilab version : Michael Baudin
    //
    //  Parameters:
    //    Input, integer N, K, are the values of N and K.
    //    Output, real CNK_LOG, the logarithm of C(N,K).
    //
    function cnk_log = binomial_coef_log ( n, k )
        cnk_log = factorial_log ( n ) - factorial_log ( k ) - factorial_log ( n - k );
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

    // Example : 
    //   p = hypergeopdf_wrong ( 200 , 515 , 500 , 1030 )
    // Result :
    //  p = 0.0  
    //  Expected result = 1.65570E-10

    // Table 3
    // Hypergeometric distribution with parameters (k, N = 1030, M = 515, n = 500)
    function checkHypergeometric ( k , N , M , n , expected , precision ) 
        computed = hypergeopdf ( k , M , n , N )
        digits = assert_computedigits ( computed , expected );
        mprintf("hypergeopdf, k=%d, N=%d, M=%d, n=%d, digits=%.1f\n",k,N,M,n,digits)
    endfunction

    function checkHypergeometricWrong ( k , N , M , n , expected , precision ) 
        computed = hypergeopdf_wrong ( k , M , n , N )
        digits = assert_computedigits ( computed , expected );
        mprintf("hypergeopdf_wrong, k=%d, N=%d, M=%d, n=%d, digits=%.1f\n",k,N,M,n,digits)
    endfunction

    //
    mprintf("\nAt first, the results seems to be correct...\n\n");
    N = 103;
    M = 51;
    n = 50;
    checkHypergeometricWrong ( 10, N , M , n , 3.46223D-09 );
    checkHypergeometric ( 10, N , M , n , 3.46223D-09 );

    //
    mprintf("\n... but other parameters make the accuracy issue appear !\n\n");

    N = 1030;
    M = 515;
    n = 500;

    checkHypergeometricWrong ( 0   , N , M , n , 1.60137e-280 );
    checkHypergeometricWrong ( 100 , N , M , n , 7.46483e-83  );
    checkHypergeometricWrong ( 187 , N , M , n , 1.53541e-15  );
    checkHypergeometricWrong ( 188 , N , M , n , 4.13038e-15  );
    checkHypergeometricWrong ( 200 , N , M , n , 1.65570e-10  );
    checkHypergeometricWrong ( 300 , N , M , n , 1.65570e-10  );
    checkHypergeometricWrong ( 312 , N , M , n , 4.13038e-15  );
    checkHypergeometricWrong ( 313 , N , M , n , 1.53541e-15  );
    checkHypergeometricWrong ( 400 , N , M , n , 7.46483e-83  );
    checkHypergeometricWrong ( 500 , N , M , n , 1.60137e-280 );

    checkHypergeometric ( 0   , N , M , n , 1.60137e-280 );
    checkHypergeometric ( 100 , N , M , n , 7.46483e-83  );
    checkHypergeometric ( 187 , N , M , n , 1.53541e-15  );
    checkHypergeometric ( 188 , N , M , n , 4.13038e-15  );
    checkHypergeometric ( 200 , N , M , n , 1.65570e-10  );
    checkHypergeometric ( 300 , N , M , n , 1.65570e-10  );
    checkHypergeometric ( 312 , N , M , n , 4.13038e-15  );
    checkHypergeometric ( 313 , N , M , n , 1.53541e-15  );
    checkHypergeometric ( 400 , N , M , n , 7.46483e-83  );
    checkHypergeometric ( 500 , N , M , n , 1.60137e-280 );

endfunction test_hypergeometric
test_hypergeometric();
clear test_hypergeometric

//
// Load this script into the editor
//
filename = "accuracy_hypergeometric.sce";
dname = get_absolute_file_path(filename);
editor ( fullfile(dname,filename) );
