// Copyright (C) 2012 - Michael Baudin
// Copyright (C) 2008 - INRIA - Sabine Gaüzere
// Copyright (C) 2010-2011 - DIGITEO - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

//
// A set of utilities to test distribution functions.
//

function [x,y] = histcompute(n,data)
    // Computes the histogram of a data.
    //
    // Parameters
    // n : a 1-by-1 matrix of floating point integers, the number of classes.
    // data : a matrix of doubles, the data
    // x : 1-by-n+1 matrix of doubles, the classes of the histogram
    // y : 1-by-n+1 matrix of doubles, the empirical probability that one value in data which fall in each class
    //
    // Description
    // Computes the histogram of a data.
    //
    // The function histplot computes the data, but x and y are not 
    // output arguments of the function. 
    // This is why the histcompute function does it.
    // 
    mind = min(data);
    maxd = max(data);
    if (mind == maxd) then
        mind = mind - floor(n/2); 
        maxd = maxd + ceil(n/2);
    end
    x = linspace(mind, maxd, n+1);
    [ind , y] = dsearch(data, x)
    y = y./length(data)
endfunction

function checkRNGLaw ( varargin )
    //
    // Check the random number generator for a continuous distribution function.
    //
    // Calling Sequence
    //    checkRNGLaw(R,cdffun,N,NC,rtol,atol)
    //    checkRNGLaw(R,cdffun,N,NC,rtol,atol,iscontinuous)
    //    checkRNGLaw(R,cdffun,N,NC,rtol,atol,iscontinuous,pltgrph)
    //
    // Parameters
    // R: a N-by-1 matrix, the random numbers
    // cdffun : a list, the Cumulated Distribution Function
    // NC : a 1-by-1 matrix of floating point integers, the number of classes
    // N : a 1-by-1 matrix of floating point integers, the number of random values to test
    // rtol : a 1-by-1 matrix of doubles, the relative tolerance
    // atol : a 1-by-1 matrix of doubles, the absolute tolerance
    // iscontinuous : a 1-by-1 matrix of booleans, %t for continuous variables, %f for integer variables (default iscontinuous=%t)
    // pltgrph : a 1-by-1 matrix of booleans, %t to plot the graphics (default iscontinuous=%f)
    //
    // Description
    //  Compare the empirical histogram with the theoretical PDF.
    //  Compare the empirical CDF with the theoretical CDF.
    //  The cdffun function must have the header 
    //
    //  p = cdffun(x)
    //  p = cdffun(x,a)
    //  p = cdffun(x,a,b)
    //  p = cdffun(x,a,b,c)
    //
    // The cdffun function must be a list(f,a) or 
    // (f,a,b) or (f,a,b,c) where 
    // f is the CDF function and a, b, or c are automatically 
    // added at the end of the calling sequence.
    //

    [lhs,rhs]=argn()
    apifun_checkrhs ( "checkRNGLaw" , rhs , 6:8 )
    apifun_checklhs ( "checkRNGLaw" , lhs , 0:1 )
    //
    R = varargin(1)
    __cdffun__ = varargin(2)
    N = varargin(3)
    NC = varargin(4)
    rtol = varargin(5)
    atol  = varargin(6)
    iscontinuous=apifun_argindefault ( varargin,7,%t )
    pltgrph=apifun_argindefault ( varargin,8,%f )
    //
    // Check type
    apifun_checktype ( "checkRNGLaw" , R , "R" , 1 , "constant" )
    apifun_checktype ( "checkRNGLaw" , __cdffun__ , "cdffun" , 2 , "list" )
    apifun_checktype ( "checkRNGLaw" , N , "N" , 3 , "constant" )
    apifun_checktype ( "checkRNGLaw" , NC , "NC" , 4 , "constant" )
    apifun_checktype ( "checkRNGLaw" , rtol , "rtol" , 5 , "constant" )
    apifun_checktype ( "checkRNGLaw" , atol , "atol" , 6 , "constant" )
    apifun_checktype ( "checkRNGLaw" , iscontinuous , "iscontinuous" , 7 , "boolean" )
    apifun_checktype ( "checkRNGLaw" , pltgrph , "pltgrph" , 8 , "boolean" )
    //
    // Check size
    apifun_checkvector("checkRNGLaw",R,"R",1)
    apifun_checkscalar("checkRNGLaw",N , "N" , 3)
    apifun_checkscalar("checkRNGLaw",NC , "NC" , 4)
    apifun_checkscalar("checkRNGLaw",rtol , "rtol" , 5)
    apifun_checkscalar("checkRNGLaw",atol , "atol" , 6)
    apifun_checkscalar("checkRNGLaw",iscontinuous , "iscontinuous" , 7)
    apifun_checkscalar("checkRNGLaw",pltgrph , "pltgrph" , 8)
    //
    // Check content
    apifun_checkflint("checkRNGLaw",N , "N" , 3)
    apifun_checkflint("checkRNGLaw",NC , "NC" , 4)
    apifun_checkgreq("checkRNGLaw",N , "N" , 3, 1)
    apifun_checkgreq("checkRNGLaw",NC , "NC" , 4, 1)
    tiny = number_properties("tiny")
    apifun_checkgreq("checkRNGLaw",rtol , "rtol" , 5, tiny)
    apifun_checkgreq("checkRNGLaw",atol , "atol" , 6, tiny)
    
    // Manage the function
    mprintf("%s: Checking distribution of random numbers...\n","checkRNGLaw")
    mprintf("%s: Parameters:\n","checkRNGLaw")
    for i=2:length(__cdffun__)
        apifun_checkscalar("checkRNGLaw",__cdffun__(i) , "cdffun("+string(i)+")" , 8)
        mprintf("%s: A(%d)=%s\n","checkRNGLaw",i-1,string(__cdffun__(i)))
    end
    __cdffun__f = __cdffun__(1)
    //
    R = R(:);
    // Compare the empirical histogram with the PDF
    if (iscontinuous) then
        [X,EmpiricalPDF] = histcompute(NC,R);
        CDF = __cdffun__f(X,__cdffun__(2:$));
        TheoricPDF = diff(CDF);
        assert_checkalmostequal( EmpiricalPDF,TheoricPDF, rtol ,  atol , "element" );
    else
        X = unique(R);
        for k = 1 : size(X,"*")
            EmpiricalPDF(k) = length(find(R==X(k)));
        end
        EmpiricalPDF = EmpiricalPDF./N;
        CDF = __cdffun__f(X,__cdffun__(2:$));
        TheoricPDF=[CDF(1);diff(CDF)];
        assert_checkalmostequal( EmpiricalPDF , TheoricPDF ,  rtol ,  atol , "element" );
    end
    
    if ( pltgrph ) then
        scf();
        if (iscontinuous) then
            plot(X(1:$-1),EmpiricalPDF,"bo-"); // Empirical Histogram
            plot(X(1:$-1),TheoricPDF,"rox-"); // Theoretical Histogram
        else
            plot(X,EmpiricalPDF,"bo-"); // Empirical Histogram
            plot(X,TheoricPDF,"rox-"); // Theoretical Histogram
        end
        legend(["Empirical","Theory"]);
        xtitle("PDF","x","Density")
    end
    //
    // Compare the Empirical CDF and the Theoretical CDF
    if (iscontinuous) then
        RdevS=gsort(R,"g","i");
        PS=(1:N)'/N;
        P=__cdffun__f(RdevS,__cdffun__(2:$));
        assert_checkalmostequal( P, PS , rtol ,  atol , "element" );
    else    
        EmpiricalCDF = cumsum(EmpiricalPDF);
        P=__cdffun__f(X,__cdffun__(2:$));
        assert_checkalmostequal( P, EmpiricalCDF , rtol ,  atol , "element" );
    end
    if ( pltgrph ) then
        if (iscontinuous) then
            scf();
            plot(RdevS,PS,"b-"); // Empirical distribution
            plot(RdevS,P,"r-"); // Theoretical distribution
            legend(["Empirical CDF","Theoretical CDF"]);
            xtitle("CDF","x","P(X<=x)")
            // P-P Plot
            scf();
            plot(PS,PS,"b-"); // Straight Line
            plot(PS,P,"r-"); // Data
            legend(["Theory","Data"]);
            xtitle("P-P Plot","Theorical Cumulated Probabilities",..
            "Data Cumulated Probabilities")
        else
            scf();
            plot(X,EmpiricalCDF,"b-"); // Empirical distribution
            plot(X,P,"r-"); // Theoretical distribution
            legend(["Empirical CDF","Theoretical CDF"]);
            xtitle("CDF","x","P(X<=x)")
        end
    end
    mprintf("%s: ...OK\n","checkRNGLaw")
endfunction


function checkMeanVariance ( varargin )
    // Check the mean and variance of random numbers.
    //
    // Calling Sequence
    // checkMeanVariance ( m,n,R,mu,va,rtol )
    // checkMeanVariance ( m,n,R,mu,va,rtol,atol )
    //
    // Parameters
    // m : a 1-by-1 matrix of floating point integers, the number of rows
    // n : a 1-by-1 matrix of floating point integers, the number of columns
    // R: a m-by-n matrix, the random numbers
    // mu : a 1-by-1 matrix of doubles, the expected mean
    // va : a 1-by-1 matrix of doubles, the expected variance. If va==[], then the variance is not checked.
    // rtol : a 1-by-1 matrix of doubles, the relative tolerance
    // atol : a 1-by-1 matrix of doubles, the absolute tolerance

    [lhs,rhs]=argn()
    apifun_checkrhs ( "checkMeanVariance" , rhs , 6:7 )
    apifun_checklhs ( "checkMeanVariance" , lhs , 0:1 )
    //
    m=varargin(1)
    n=varargin(2)
    R=varargin(3)
    mu=varargin(4)
    va=varargin(5)
    rtol=varargin(6)
    atol = apifun_argindefault ( varargin , 7 , 0 )
    //
    assert_checkequal ( size(R) , [m,n] );
    assert_checkequal ( typeof(R) , "constant" );
    assert_checkalmostequal ( mean(R) , mu , rtol , atol );
    if ( va<>[] ) then
        assert_checkalmostequal ( variance(R) , va , rtol , atol );
    end
endfunction

function checkRNG2argsOld ( m , n , mygrandfun , a, b)
    // Check that (a,b) arguments of a distfun_*rng(m,n,a,b) function can be expanded.
    //
    // Parameters
    // m : a 1-by-1 matrix of floating point integers, the number of rows
    // n : a 1-by-1 matrix of floating point integers, the number of columns
    // mygrandfun : a function, the distfun_*rng function
    // a : a m-by-n matrix of floating point integers, the first argument
    // b : a m-by-n matrix of floating point integers, the second argument
    //
    // mygrandfun must have the header : R = mygrandfun(m,n,a,b)

    R=mygrandfun(a,b,m,n);
    assert_checkequal ( size(R) , [m,n] );
    //
    // Check expansion of a and b
    distfun_seedset(0);
    R1 = mygrandfun(a(1),b(1),m,n);
    assert_checkequal ( size(R1) , [m,n] );
    distfun_seedset(0);
    R2 = mygrandfun(a(1)*ones(m,n),b(1)*ones(m,n),m,n);
    assert_checkequal ( R1 , R2 );
endfunction

function checkRNG3argsOld ( m , n , mygrandfun , a, b, c)
    // Check that (a,b,c) arguments of a distfun_*rng(m,n,a,b,c) function can be expanded.
    //
    // Parameters
    // m : a 1-by-1 matrix of floating point integers, the number of rows
    // n : a 1-by-1 matrix of floating point integers, the number of columns
    // mygrandfun : a function, the distfun_*rng function
    // a : a m-by-n matrix of floating point integers, the first argument
    // b : a m-by-n matrix of floating point integers, the second argument
    // c : a m-by-n matrix of floating point integers, the third argument
    //
    // mygrandfun must have the header : R = mygrandfun(m,n,a,b,c)

    R=mygrandfun(a,b,c,m,n);
    assert_checkequal ( size(R) , [m,n] );
    //
    // Check expansion of a and b and c
    distfun_seedset(0);
    R1 = mygrandfun(a(1),b(1),c(1),m,n);
    assert_checkequal ( size(R1) , [m,n] );
    distfun_seedset(0);
    R2 = mygrandfun(a(1)*ones(m,n),b(1)*ones(m,n),c(1)*ones(m,n),m,n);
    assert_checkequal ( R1 , R2 );
endfunction

function checkUniformity(kgen,rtol,atol,N,NC,UinMax)
    // Check the uniformity of numbers computed from the RNG
    // Parameters
    // kgen : a 1-by-1 matrix of doubles, integer value, the index of the RNG. In the set 1, 2, ..., NGen
    // rtol : a 1-by-1 matrix of doubles, the relative tolerance
    // N  : a 1-by-1 matrix of doubles, integer value, the number of values to generate
    // UinMax : a 1-by-1 matrix of doubles, integer value, the maximum integer generable with uin option
    //
    // Description
    // Checks the output of the functions 
    // * distfun_rndunf
    // * distfun_rnduin 
    //

    //
    // Check distribution of uniform numbers in [0,1[
    R = distfun_unifrnd(0,1,N,1);
    checkMeanVariance (N , 1 , R , 1/2 , 1/12 , rtol );
    checkRNGLaw ( R , list(distfun_unifcdf,0,1) , N , NC , rtol , atol );
    //
    // Check distribution of uniform integers in [A,B]
    R = distfun_unidrnd(UinMax,N,1);
    checkRNGLaw ( R , list(distfun_unidcdf,UinMax) , N , NC , rtol , atol , %f);
endfunction

function data = readCsvDataset(dataset)
    data = csvRead(dataset," ",".",[],[],"/#(.*)/")
endfunction

function checkRNG ( m , n , __mygrandfun__ )
    // Check a random number generator.
    //
    // Calling Sequence
    //    checkRNG ( m , n , mygrandfun )
    //
    // Parameters
    // m : a 1-by-1 matrix of floating point integers, the number of rows
    // n : a 1-by-1 matrix of floating point integers, the number of columns
    // mygrandfun : a list representing the random number generator
    //
    // Description
    // The mygrandfun function must be a list(f,a) or 
    // (f,a,b) or (f,a,b,c) or (f,a,b,c,d) where 
    // f is the random generator function and a, b, or c or d are automatically 
    // added at the end of the calling sequence.
    //
    // f must have the header : 
    //
    // R = f(a,m,n)
    // R = f(a,b,m,n)
    // R = f(a,b,c,m,n)
    // R = f(a,b,c,d,m,n)

    [lhs,rhs]=argn()
    apifun_checkrhs ( "checkRNG" , rhs , 3 )
    apifun_checklhs ( "checkRNG" , lhs , 0:1 )
    //
    // Check type
    apifun_checktype ( "checkRNG" , m , "m" , 1 , "constant" )
    apifun_checktype ( "checkRNG" , n , "n" , 2 , "constant" )
    apifun_checktype ( "checkRNG" , __mygrandfun__ , "mygrandfun" , 3 , "list" )

    // Manage the list
    __mygrandfun__f = __mygrandfun__(1)
    apifun_checktype ( "checkRNG" , __mygrandfun__f , "mygrandfun(1)" , 3 , ["function" "fptr"] )
    nbargs = length(__mygrandfun__)-1
    //
    // Get a, b, c
    if (nbargs==1) then
        a=__mygrandfun__(2)
        apifun_checkdims("checkRNG",a,"mygrandfun(2)",3,[m n])
    elseif (nbargs==2) then
        a=__mygrandfun__(2)
        apifun_checkdims("checkRNG",a,"mygrandfun(2)",3,[m n])
        b=__mygrandfun__(3)
        apifun_checkdims("checkRNG",b,"mygrandfun(3)",3,[m n])
    elseif (nbargs==3) then
        a=__mygrandfun__(2)
        apifun_checkdims("checkRNG",a,"mygrandfun(2)",3,[m n])
        b=__mygrandfun__(3)
        apifun_checkdims("checkRNG",b,"mygrandfun(3)",3,[m n])
        c=__mygrandfun__(4)
        apifun_checkdims("checkRNG",c,"mygrandfun(4)",3,[m n])
    elseif (nbargs==4) then
        a=__mygrandfun__(2)
        apifun_checkdims("checkRNG",a,"mygrandfun(2)",3,[m n])
        b=__mygrandfun__(3)
        apifun_checkdims("checkRNG",b,"mygrandfun(3)",3,[m n])
        c=__mygrandfun__(4)
        apifun_checkdims("checkRNG",c,"mygrandfun(4)",3,[m n])
        d=__mygrandfun__(5)
        apifun_checkdims("checkRNG",d,"mygrandfun(5)",4,[m n])
    else
        error(msprintf("%s: Unable to manage %d arguments.\n","checkRNG",nbargs))
    end
    nrows=size(a,"r")
    ncols=size(a,"r")
    //
    // Check empty matrix
    mprintf("%s : Check empty matrix...\n","checkRNG")
    if (nbargs==1) then
        R=__mygrandfun__f([]);
    elseif (nbargs==2) then
        R=__mygrandfun__f([],[]);
    elseif (nbargs==3) then
        R=__mygrandfun__f([],[],[]);
    elseif (nbargs==4) then
        R=__mygrandfun__f([],[],[],[]);
    else
        error(msprintf("%s: Unable to manage %d arguments.\n","checkRNG",nbargs))
    end
    assert_checkequal ( R ,[]);
    //
    mprintf("%s : Check f(a,m,n)...\n","checkRNG")
    if (nbargs==1) then
        R=__mygrandfun__f(a,m,n);
    elseif (nbargs==2) then
        R=__mygrandfun__f(a,b,m,n);
    elseif (nbargs==3) then
        R=__mygrandfun__f(a,b,c,m,n);
    elseif (nbargs==4) then
        R=__mygrandfun__f(a,b,c,d,m,n);
    else
        error(msprintf("%s: Unable to manage %d arguments.\n","checkRNG",nbargs))
    end
    assert_checkequal ( size(R) , [m,n] );
    //
    // Check expansion of a and b
    mprintf("%s : Check expansion of a and b I...\n","checkRNG")
    distfun_seedset(0);
    if (nbargs==1) then
        R1 = __mygrandfun__f(a(1),m,n);
    elseif (nbargs==2) then
        R1 = __mygrandfun__f(a(1),b(1),m,n);
    elseif (nbargs==3) then
        R1 = __mygrandfun__f(a(1),b(1),c(1),m,n);
    elseif (nbargs==4) then
        R1 = __mygrandfun__f(a(1),b(1),c(1),d(1),m,n);
    else
        error(msprintf("%s: Unable to manage %d arguments.\n","checkRNG",nbargs))
    end
    assert_checkequal ( size(R1) , [m,n] );
    distfun_seedset(0);
    if (nbargs==1) then
        R2 = __mygrandfun__f(a(1)*ones(m,n),m,n);
    elseif (nbargs==2) then
        R2 = __mygrandfun__f(a(1)*ones(m,n),b(1)*ones(m,n),m,n);
    elseif (nbargs==3) then
        R2 = __mygrandfun__f(a(1)*ones(m,n),b(1)*ones(m,n),c(1)*ones(m,n),m,n);
    elseif (nbargs==4) then
        R2 = __mygrandfun__f(a(1)*ones(m,n),b(1)*ones(m,n),c(1)*ones(m,n),d(1)*ones(m,n),m,n);
    else
        error(msprintf("%s: Unable to manage %d arguments.\n","checkRNG",nbargs))
    end
    assert_checkequal ( R1 , R2 );
    //
    // Check expansion of a and b in R = distfun_frnd(a,b)
    mprintf("%s : Check expansion of a and b II...\n","checkRNG")
    //
    distfun_seedset(1);
    N = 2;
    computed1 = [];
    for i = 1:N
        if (nbargs==1) then
            R = __mygrandfun__f(a)
        elseif (nbargs==2) then
            R = __mygrandfun__f(a,b)
        elseif (nbargs==3) then
            R = __mygrandfun__f(a,b,c)
        elseif (nbargs==4) then
            R = __mygrandfun__f(a,b,c,d)
        else
            error(msprintf("%s: Unable to manage %d arguments.\n","checkRNG",nbargs))
        end
        computed1(i,:) = R(:)'
    end
    distfun_seedset(1);
    computed2  = [];
    for i = 1:N
        for k = 1 : m*n
            if (nbargs==1) then
                R = __mygrandfun__f(a(k));
            elseif (nbargs==2) then
                R = __mygrandfun__f(a(k),b(k));
            elseif (nbargs==3) then
                R = __mygrandfun__f(a(k),b(k),c(k));
            elseif (nbargs==4) then
                R = __mygrandfun__f(a(k),b(k),c(k),d(k));
            else
                error(msprintf("%s: Unable to manage %d arguments.\n","checkRNG",nbargs))
            end
            computed2(i,k)=R;
        end
    end
    assert_checkequal(computed1,computed2);
    //
    // Check expansion of a and b
    mprintf("%s : Check expansion of a and b III...\n","checkRNG")
    distfun_seedset(0);
    if (nbargs==1) then
        // Nothing to check
    elseif (nbargs==2) then
        R1 = __mygrandfun__f(a,b(1),m,n);
        assert_checkequal ( size(R1) , [m,n] );
        R1 = __mygrandfun__f(a(1),b,m,n);
        assert_checkequal ( size(R1) , [m,n] );
    elseif (nbargs==3) then
        R1 = __mygrandfun__f(a,b(1),c(1),m,n);
        assert_checkequal ( size(R1) , [m,n] );
        R1 = __mygrandfun__f(a(1),b,c(1),m,n);
        assert_checkequal ( size(R1) , [m,n] );
        R1 = __mygrandfun__f(a(1),b(1),c,m,n);
        assert_checkequal ( size(R1) , [m,n] );
    elseif (nbargs==4) then
        R1 = __mygrandfun__f(a,b(1),c(1),d(1),m,n);
        assert_checkequal ( size(R1) , [m,n] );
        R1 = __mygrandfun__f(a(1),b,c(1),d(1),m,n);
        assert_checkequal ( size(R1) , [m,n] );
        R1 = __mygrandfun__f(a(1),b(1),c,d(1),m,n);
        assert_checkequal ( size(R1) , [m,n] );
        R1 = __mygrandfun__f(a(1),b(1),c(1),d,m,n);
        assert_checkequal ( size(R1) , [m,n] );
    else
        error(msprintf("%s: Unable to manage %d arguments.\n","checkRNG",nbargs))
    end
    mprintf("%s : ...OK\n","checkRNG")
endfunction

function CheckInverseCDF(funname,__invfun__,p,rtol)
    // Check an inverse CDF function
    //
    // Calling Sequence
    //    CheckInverseCDF(funname,invfun,x,rtol)
    //
    // Parameters
    // funname : a 1-by-1 matrix of string, the function under test
    // __invfun__ : a list representing the function
    // p : a m-by-1 matrix of doubles, the required probabilities.
    // rtol : a 1-by-1 matrix of doubles, the relative tolerance
    //
    // Description
    // The CheckInverseCDF function checks an inverse CDF function with header :
    //
    // y=distfun_*inv(p,a,lowertail)
    // y=distfun_*inv(p,a,b,lowertail)
    // y=distfun_*inv(p,a,b,c,lowertail)
    // y=distfun_*inv(p,a,b,c,d,lowertail)
    //
    // The tests are designed to check the consistency of the 
    // function with respect to its API.
    //
    // The __invfun__ function must be a list(invfun,a) or 
    // (invfun,a,b) or (invfun,a,b,c) or (invfun,a,b,c,d) where 
    // invfun is the inverse CDF function and a, b, or c or d are automatically 
    // added at the end of the calling sequence.
    //
    // The checks avoid numerical difficulties, which must be tested 

    // with datasets.
    // The values of a, b and c should be easy, so that no numerical 
    // difficulty can occur.
    //
    // This function makes the following checks.
    //  * The empty matrix p=[] must return x=[].
    //  * The calling sequence is tested where a, b or c are scalars.
    //    This is computed with a loop on p(i). 
    //    This allows to check that the remaining vectorizes checks are 
    //    correct. 
    //  * The relative tolerance rtol is used to check the 
    //    calling sequence x=distfun_*inv(1-p,a,b,c,%f). 
    //  * The calling sequence is tested where all combinations of 
    //    scalar and vectors a, b and c are checked.
    //

    [lhs,rhs]=argn()
    apifun_checkrhs ( "CheckInverseCDF" , rhs , 4 )
    apifun_checklhs ( "CheckInverseCDF" , lhs , 0:1 )
    //
    // Check type
    apifun_checktype ( "CheckInverseCDF" , funname , "funname" , 1 , "string" )
    apifun_checktype ( "CheckInverseCDF" , __invfun__ , "invfun" , 2 , "list" )
    apifun_checktype ( "CheckInverseCDF" , p , "p" , 3 , "constant" )
    apifun_checktype ( "CheckInverseCDF" , rtol , "rtol" , 4 , "constant" )
    //
    // Check size
    apifun_checkscalar("CheckInverseCDF",funname,"funname",1)
    apifun_checkvector("CheckInverseCDF",p,"p",3)
    apifun_checkscalar("CheckInverseCDF",rtol,"rtol",4)
    //
    // Check content
    apifun_checkrange("CheckInverseCDF",p,"p",3,0,1)
    //
    __invfun__f = __invfun__(1)
    nbargs = length(__invfun__)-1
    __invfun__args = list()
    for i=1:nbargs
        __invfun__args(i)=__invfun__(i+1)
    end
    //
    // Check empty matrix
    mprintf("%s: Checking empty matrix...\n","CheckInverseCDF")
    if (nbargs==1) then
        mprintf("%s: x=%s([],[])\n","CheckInverseCDF",funname)
        x = __invfun__f ([],[])
    elseif (nbargs==2) then
        mprintf("%s: x=%s([],[],[])\n","CheckInverseCDF",funname)
        x = __invfun__f ([],[],[])
    elseif (nbargs==3) then
        mprintf("%s: x=%s([],[],[],[])\n","CheckInverseCDF",funname)
        x = __invfun__f ([],[],[],[])
    elseif (nbargs==4) then
        mprintf("%s: x=%s([],[],[],[])\n","CheckInverseCDF",funname)
        x = __invfun__f ([],[],[],[],[])
    else
        error(msprintf("%s: Unable to manage %d arguments.\n","CheckInverseCDF",nbargs))
    end
    assert_checkequal ( x ,[]);
    mprintf("%s: ... OK\n","CheckInverseCDF")
    //
    if (%f) then
    // Check NANs
    mprintf("%s: Checking NANs...\n","CheckInverseCDF")
    if (nbargs==1) then
        mprintf("%s: x=%s(%%nan,a(1))\n","CheckInverseCDF",funname)
        x = __invfun__f (%nan,a(1))
        assert_checktrue(isnan(x))
        mprintf("%s: x=%s(p(1),%%nan)\n","CheckInverseCDF",funname)
        x = __invfun__f (p(1),%nan)
        assert_checktrue(isnan(x))
    elseif (nbargs==2) then
        mprintf("%s: x=%s(%%nan,a(1),b(1))\n","CheckInverseCDF",funname)
        x = __invfun__f (%nan,a(1),b(1))
        assert_checktrue(isnan(x))
        mprintf("%s: x=%s(p(1),%%nan,b(1))\n","CheckInverseCDF",funname)
        x = __invfun__f (p(1),%nan,b(1))
        assert_checktrue(isnan(x))
        mprintf("%s: x=%s(p(1),a(1),%%nan)\n","CheckInverseCDF",funname)
        x = __invfun__f (p(1),a(1),%nan)
        assert_checktrue(isnan(x))
    elseif (nbargs==3) then
        mprintf("%s: x=%s(%%nan,a(1),b(1),c(1))\n","CheckInverseCDF",funname)
        x = __invfun__f (%nan,a(1),b(1),c(1))
        assert_checktrue(isnan(x))
        mprintf("%s: x=%s(p(1),%%nan,b(1),c(1))\n","CheckInverseCDF",funname)
        x = __invfun__f (p(1),%nan,b(1),c(1))
        assert_checktrue(isnan(x))
        mprintf("%s: x=%s(p(1),a(1),%%nan,c(1))\n","CheckInverseCDF",funname)
        x = __invfun__f (p(1),a(1),%nan,c(1))
        assert_checktrue(isnan(x))
        mprintf("%s: x=%s(p(1),a(1),b(1),%%nan)\n","CheckInverseCDF",funname)
        x = __invfun__f (p(1),a(1),b(1),%nan)
        assert_checktrue(isnan(x))
    elseif (nbargs==4) then
        mprintf("%s: x=%s(%%nan,a(1),b(1),c(1),d(1))\n","CheckInverseCDF",funname)
        x = __invfun__f (%nan,a(1),b(1),c(1),d(1))
        assert_checktrue(isnan(x))
        mprintf("%s: x=%s(p(1),%%nan,b(1),c(1),d(1))\n","CheckInverseCDF",funname)
        x = __invfun__f (p(1),%nan,b(1),c(1),d(1))
        assert_checktrue(isnan(x))
        mprintf("%s: x=%s(p(1),a(1),%%nan,c(1),d(1))\n","CheckInverseCDF",funname)
        x = __invfun__f (p(1),a(1),%nan,c(1),d(1))
        assert_checktrue(isnan(x))
        mprintf("%s: x=%s(p(1),a(1),b(1),%%nan,d(1))\n","CheckInverseCDF",funname)
        x = __invfun__f (p(1),a(1),b(1),%nan,d(1))
        assert_checktrue(isnan(x))
        mprintf("%s: x=%s(p(1),a(1),b(1),c(1),%%nan)\n","CheckInverseCDF",funname)
        x = __invfun__f (p(1),a(1),b(1),c(1),%nan)
        assert_checktrue(isnan(x))
    else
        error(msprintf("%s: Unable to manage %d arguments.\n","CheckInverseCDF",nbargs))
    end
    mprintf("%s: ... OK\n","CheckInverseCDF")
    end
    //
    nrows = size(p,"r")
    ncols = size(p,"c")
    //
    // Get a, b, c
    if (nbargs==1) then
        a=__invfun__args(1)
    elseif (nbargs==2) then
        a=__invfun__args(1)
        b=__invfun__args(2)
    elseif (nbargs==3) then
        a=__invfun__args(1)
        b=__invfun__args(2)
        c=__invfun__args(3)
    elseif (nbargs==4) then
        a=__invfun__args(1)
        b=__invfun__args(2)
        c=__invfun__args(3)
        d=__invfun__args(4)
    else
        error(msprintf("%s: Unable to manage %d arguments.\n","CheckInverseCDF",nbargs))
    end
    //
    // Get reference
    mprintf("%s: Get reference quantiles...\n","CheckInverseCDF")
    expected = []
    for i=1:nrows*ncols
        if (nbargs==1) then
            mprintf("%s: x(%d)=%s\n","CheckInverseCDF",i,formatCallingSequence(funname,list(p(i),a)))
            expected(i) = __invfun__f ( p(i) , a )
        elseif (nbargs==2) then
            mprintf("%s: x(%d)=%s\n","CheckInverseCDF",i,formatCallingSequence(funname,list(p(i),a,b)))
            expected(i) = __invfun__f ( p(i) , a , b )
        elseif (nbargs==3) then
            mprintf("%s: x(%d)=%s\n","CheckInverseCDF",i,formatCallingSequence(funname,list(p(i),a,b,c)))
            expected(i) = __invfun__f ( p(i) , a , b , c )
        elseif (nbargs==4) then
            mprintf("%s: x(%d)=%s\n","CheckInverseCDF",i,formatCallingSequence(funname,list(p(i),a,b,c,d)))
            expected(i) = __invfun__f ( p(i) , a , b , c,d )
        else
            error(msprintf("%s: Unable to manage %d arguments.\n","CheckInverseCDF",nbargs))
        end
    end
    expected=matrix(expected,nrows,ncols)
    mprintf("%s: ... OK\n","CheckInverseCDF")

    //
    // Compute complementary quantiles
    mprintf("%s: Compute complementary quantiles...\n","CheckInverseCDF")
    mprintf("%s: Check that invfun(p,%%t)==invfun(1-p,%%f)\n","CheckInverseCDF")
    if (nbargs==1) then
        mprintf("%s: x=%s\n","CheckInverseCDF",formatCallingSequence(funname,list(1-p,a,%f)))
        computed = __invfun__f ( 1-p , a , %f )
        assert_checkalmostequal ( computed , expected , rtol );
    elseif (nbargs==2) then
        mprintf("%s: x=%s\n","CheckInverseCDF",formatCallingSequence(funname,list(1-p,a,b,%f)))
        computed = __invfun__f ( 1-p , a , b , %f )
        assert_checkalmostequal ( computed , expected , rtol );
    elseif (nbargs==3) then
        mprintf("%s: x=%s\n","CheckInverseCDF",formatCallingSequence(funname,list(1-p,a,b,c,%f)))
        computed = __invfun__f ( 1-p , a , b , c , %f )
        assert_checkalmostequal ( computed , expected , rtol );
    elseif (nbargs==4) then
        mprintf("%s: x=%s\n","CheckInverseCDF",formatCallingSequence(funname,list(1-p,a,b,c,d,%f)))
        computed = __invfun__f ( 1-p , a , b , c ,d, %f )
        assert_checkalmostequal ( computed , expected , rtol );
    else
        error(msprintf("%s: Unable to manage %d arguments.\n","CheckInverseCDF",nbargs))
    end
    mprintf("%s: ... OK\n","CheckInverseCDF")
    //
    // Test with expanded arguments
    mprintf("%s: With arguments expanded...\n","CheckInverseCDF")
    v = ones(nrows,ncols)
    if (nbargs==1) then
        mprintf("%s: x=%s\n","CheckInverseCDF",formatCallingSequence(funname,list(p,a)))
        computed = __invfun__f ( p , a );
        assert_checkequal ( computed , expected );
        mprintf("%s: x=%s\n","CheckInverseCDF",formatCallingSequence(funname,list(p,a*v)))
        computed = __invfun__f ( p , a*v );
        assert_checkequal ( computed , expected );
    elseif (nbargs==2) then
        mprintf("%s: x=%s\n","CheckInverseCDF",formatCallingSequence(funname,list(p,a,b)))
        computed = __invfun__f ( p , a , b );
        assert_checkequal ( computed , expected );
        mprintf("%s: x=%s\n","CheckInverseCDF",formatCallingSequence(funname,list(p,a,b*v)))
        computed = __invfun__f ( p , a , b*v );
        assert_checkequal ( computed , expected );
        mprintf("%s: x=%s\n","CheckInverseCDF",formatCallingSequence(funname,list(p,a*v,b)))
        computed = __invfun__f ( p , a*v , b );
        assert_checkequal ( computed , expected );
        mprintf("%s: x=%s\n","CheckInverseCDF",formatCallingSequence(funname,list(p,a*v,b*v)))
        computed = __invfun__f ( p , a*v , b*v );
        assert_checkequal ( computed , expected );
    elseif (nbargs==3) then
        mprintf("%s: x=%s\n","CheckInverseCDF",formatCallingSequence(funname,list(p,a,b,c)))
        computed = __invfun__f ( p , a , b, c );
        assert_checkequal ( computed , expected );
        mprintf("%s: x=%s\n","CheckInverseCDF",formatCallingSequence(funname,list(p,a*v,b,c)))
        computed = __invfun__f ( p , a*v , b , c );
        assert_checkequal ( computed , expected );
        mprintf("%s: x=%s\n","CheckInverseCDF",formatCallingSequence(funname,list(p,a,b*v,c)))
        computed = __invfun__f ( p , a , b*v , c );
        assert_checkequal ( computed , expected );
        mprintf("%s: x=%s\n","CheckInverseCDF",formatCallingSequence(funname,list(p,a,b,c*v)))
        computed = __invfun__f ( p , a , b , c*v );
        assert_checkequal ( computed , expected );
        mprintf("%s: x=%s\n","CheckInverseCDF",formatCallingSequence(funname,list(p,a*v,b*v,c)))
        computed = __invfun__f ( p , a*v , b*v , c );
        assert_checkequal ( computed , expected );
        mprintf("%s: x=%s\n","CheckInverseCDF",formatCallingSequence(funname,list(p,a*v,b,c*v)))
        computed = __invfun__f ( p , a*v , b , c*v );
        assert_checkequal ( computed , expected );
        mprintf("%s: x=%s\n","CheckInverseCDF",formatCallingSequence(funname,list(p,a,b*v,c*v)))
        computed = __invfun__f ( p , a , b*v , c*v );
        assert_checkequal ( computed , expected );
        mprintf("%s: x=%s\n","CheckInverseCDF",formatCallingSequence(funname,list(p,a*v,b*v,c*v)))
        computed = __invfun__f ( p , a*v , b*v , c*v );
        assert_checkequal ( computed , expected );
    elseif (nbargs==4) then
        mprintf("%s: x=%s\n","CheckInverseCDF",formatCallingSequence(funname,list(p,a,b,c,d)))
        computed = __invfun__f ( p , a , b, c ,d);
        assert_checkequal ( computed , expected );
        mprintf("%s: x=%s\n","CheckInverseCDF",formatCallingSequence(funname,list(p,a*v,b,c,d)))
        computed = __invfun__f ( p , a*v , b , c ,d);
        assert_checkequal ( computed , expected );
        mprintf("%s: x=%s\n","CheckInverseCDF",formatCallingSequence(funname,list(p,a,b*v,c,d)))
        computed = __invfun__f ( p , a , b*v , c ,d);
        assert_checkequal ( computed , expected );
        mprintf("%s: x=%s\n","CheckInverseCDF",formatCallingSequence(funname,list(p,a,b,c*v,d)))
        computed = __invfun__f ( p , a , b , c*v ,d);
        assert_checkequal ( computed , expected );
        mprintf("%s: x=%s\n","CheckInverseCDF",formatCallingSequence(funname,list(p,a,b,c,d*v)))
        computed = __invfun__f ( p , a , b , c ,d*v);
        assert_checkequal ( computed , expected );
        mprintf("%s: x=%s\n","CheckInverseCDF",formatCallingSequence(funname,list(p,a*v,b*v,c,d)))
        computed = __invfun__f ( p , a*v , b*v , c ,d);
        assert_checkequal ( computed , expected );
        mprintf("%s: x=%s\n","CheckInverseCDF",formatCallingSequence(funname,list(p,a*v,b,c*v,d)))
        computed = __invfun__f ( p , a*v , b , c*v ,d);
        assert_checkequal ( computed , expected );
        mprintf("%s: x=%s\n","CheckInverseCDF",formatCallingSequence(funname,list(p,a,b*v,c*v,d)))
        computed = __invfun__f ( p , a , b*v , c*v ,d);
        assert_checkequal ( computed , expected );
        mprintf("%s: x=%s\n","CheckInverseCDF",formatCallingSequence(funname,list(p,a*v,b*v,c*v,d)))
        computed = __invfun__f ( p , a*v , b*v , c*v ,d);
        assert_checkequal ( computed , expected );
    else
        error(msprintf("%s: Unable to manage %d arguments.\n","CheckInverseCDF",nbargs))
    end
    mprintf("%s: ... OK\n","CheckInverseCDF")
endfunction

function CheckCDF(funname,__cdffun__,x,rtol)
    // Check a CDF function
    //
    // Calling Sequence
    //    CheckCDF(funname,cdffun,x,rtol)
    //
    // Parameters
    // funname : a 1-by-1 matrix of string, the function under test
    // cdffun : a list representing the function
    // x : a m-by-1 matrix of doubles, the points.
    // rtol : a 1-by-1 matrix of doubles, the relative tolerance
    //
    // Description
    // The CheckCDF function checks a CDF function with header :
    //
    // p=distfun_*cdf(x,a,lowertail)
    // p=distfun_*cdf(x,a,b,lowertail)
    // p=distfun_*cdf(x,a,b,c,lowertail
    //
    // The tests are designed to check the consistency of the 
    // function with respect to its API.
    //
    // The cdffun function must be a list(f,a) or 
    // (f,a,b) or (f,a,b,c) where 
    // f is the CDF function and a, b, or c are automatically 
    // added at the end of the calling sequence.
    //
    // The checks avoid numerical difficulties, which must be tested 
    // with datasets.
    // The values of a, b and c should be easy, so that no numerical 
    // difficulty can occur.
    //
    // This function makes the following checks.
    //  * The empty matrix x=[] must return p=[].
    //  * The calling sequence is tested where a, b or c are scalars.
    //    This is computed with a loop and on x(i). 
    //    This allows to check that the remaining vectorizes checks are 
    //    correct. 
    //  * The relative tolerance rtol is used to check the 
    //    calling sequence q=distfun_*cdf(x,a,b,c,%f). 
    //  * The calling sequence is tested where all combinations of 
    //    scalar and vectors a, b and c are checked.
    //
    [lhs,rhs]=argn()
    apifun_checkrhs ( "CheckCDF" , rhs , 4 )
    apifun_checklhs ( "CheckCDF" , lhs , 0:1 )
    //
    // Check type
    apifun_checktype ( "CheckCDF" , funname , "funname" , 1 , "string" )
    apifun_checktype ( "CheckCDF" , __cdffun__ , "cdffun" , 2 , "list" )
    apifun_checktype ( "CheckCDF" , x , "x" , 3 , "constant" )
    apifun_checktype ( "CheckCDF" , rtol , "rtol" , 4 , "constant" )
    //
    // Check size
    apifun_checkscalar("CheckCDF",funname,"funname",1)
    apifun_checkvector("CheckCDF",x,"x",3)
    apifun_checkscalar("CheckCDF",rtol,"rtol",4)
    //
    __cdffun__f = __cdffun__(1)
    nbargs = length(__cdffun__)-1
    __cdffun__args = list()
    for i=1:nbargs
        __cdffun__args(i)=__cdffun__(i+1)
    end
    //
    // Check empty matrix
    mprintf("%s: Checking empty matrix...\n","CheckCDF")
    if (nbargs==1) then
        mprintf("%s: p=%s([],[])\n","CheckCDF",funname)
        p = __cdffun__f ([],[])
    elseif (nbargs==2) then
        mprintf("%s: p=%s([],[],[])\n","CheckCDF",funname)
        p = __cdffun__f ([],[],[])
    elseif (nbargs==3) then
        mprintf("%s: p=%s([],[],[],[])\n","CheckCDF",funname)
        p = __cdffun__f ([],[],[],[])
    else
        error(msprintf("%s: Unable to manage %d arguments.\n","CheckCDF",nbargs))
    end
    assert_checkequal ( p ,[]);
    mprintf("%s: ... OK\n","CheckCDF")
    //
    // Get a, b, c
    if (nbargs==1) then
        a=__cdffun__args(1)
    elseif (nbargs==2) then
        a=__cdffun__args(1)
        b=__cdffun__args(2)
    elseif (nbargs==3) then
        a=__cdffun__args(1)
        b=__cdffun__args(2)
        c=__cdffun__args(3)
    else
        error(msprintf("%s: Unable to manage %d arguments.\n","CheckCDF",nbargs))
    end
    //
    if (%f) then
    // Check NANs
    mprintf("%s:Check NANs...\n","CheckCDF")
    if (nbargs==1) then
        mprintf("%s: p=%s(%%nan,a(1))\n","CheckCDF",funname)
        p = __cdffun__f ( %nan , a(1) )
        assert_checktrue(isnan(p))
        mprintf("%s: p=%s(x(1),%%nan)\n","CheckCDF",funname)
        p = __cdffun__f(x(1),%nan)
        assert_checktrue(isnan(p))
    elseif (nbargs==2) then
        mprintf("%s: p=%s(%%nan,a(1),b(1))\n","CheckCDF",funname)
        p = __cdffun__f(%nan,a(1),b(1))
        assert_checktrue(isnan(p))
        mprintf("%s: p=%s(x(1),%%nan,b(1))\n","CheckCDF",funname)
        p = __cdffun__f(x(1),%nan,b(1))
        assert_checktrue(isnan(p))
        mprintf("%s: p=%s(x(1),a(1),%%nan)\n","CheckCDF",funname)
        p = __cdffun__f(x(1),a(1),%nan)
        assert_checktrue(isnan(p))
    elseif (nbargs==3) then
        mprintf("%s: p=%s(%%nan,a(1),b(1),c(1))\n","CheckCDF",funname)
        p = __cdffun__f(%nan,a(1),b(1),c(1))
        assert_checktrue(isnan(p))
        mprintf("%s: p=%s(x(1),%%nan,b(1),c(1))\n","CheckCDF",funname)
        p = __cdffun__f(x(1),%nan,b(1),c(1))
        assert_checktrue(isnan(p))
        mprintf("%s: p=%s(x(1),a(1),%%nan,c(1))\n","CheckCDF",funname)
        p = __cdffun__f(x(1),a(1),%nan,c(1))
        assert_checktrue(isnan(p))
        mprintf("%s: p=%s(x(1),a(1),b(1),%%nan)\n","CheckCDF",funname)
        p = __cdffun__f(x(1),a(1),b(1),%nan)
        assert_checktrue(isnan(p))
    else
        error(msprintf("%s: Unable to manage %d arguments.\n","CheckCDF",nbargs))
    end
    mprintf("%s: ... OK\n","CheckCDF")
    end
    //
    nrows = size(x,"r")
    ncols = size(x,"c")
    //
    // Get reference
    mprintf("%s: Get reference quantiles...\n","CheckCDF")
    expected = []
    for i=1:nrows*ncols
        if (nbargs==1) then
            mprintf("%s: p(%d)=%s\n","CheckCDF",i,formatCallingSequence(funname,list(x(i),a)))
            expected(i) = __cdffun__f ( x(i) , a )
        elseif (nbargs==2) then
            mprintf("%s: p(%d)=%s\n","CheckCDF",i,formatCallingSequence(funname,list(x(i),a,b)))
            expected(i) = __cdffun__f ( x(i) , a , b )
        elseif (nbargs==3) then
            mprintf("%s: p(%d)=%s\n","CheckCDF",i,formatCallingSequence(funname,list(x(i),a,b,c)))
            expected(i) = __cdffun__f ( x(i) , a , b , c )
        else
            error(msprintf("%s: Unable to manage %d arguments.\n","CheckCDF",nbargs))
        end
    end
    expected=matrix(expected,nrows,ncols)
    mprintf("%s: ... OK\n","CheckCDF")
    //
    // Compute complementary probabilities
    mprintf("%s: Compute complementary probabilities...\n","CheckCDF")
    mprintf("%s: (check that p+q=1)\n","CheckCDF")
    if (nbargs==1) then
        mprintf("%s: q=%s\n","CheckCDF",formatCallingSequence(funname,list(x,a,%f)))
        computed = __cdffun__f ( x , a , %f )
        assert_checkalmostequal ( computed + expected , ones(nrows,ncols) , rtol );
    elseif (nbargs==2) then
        mprintf("%s: q=%s\n","CheckCDF",formatCallingSequence(funname,list(x,a,b,%f)))
        computed = __cdffun__f ( x , a , b , %f )
        assert_checkalmostequal ( computed + expected , ones(nrows,ncols) , rtol );
    elseif (nbargs==3) then
        mprintf("%s: q=%s\n","CheckCDF",formatCallingSequence(funname,list(x,a,b,c,%f)))
        computed = __cdffun__f ( x , a , b , c , %f )
        assert_checkalmostequal ( computed + expected , ones(nrows,ncols) , rtol );
    else
        error(msprintf("%s: Unable to manage %d arguments.\n","CheckCDF",nbargs))
    end
    mprintf("%s: ... OK\n","CheckCDF")
    //
    // Test with expanded arguments
    mprintf("%s: With arguments expanded...\n","CheckCDF")
    v = ones(nrows,ncols)
    if (nbargs==1) then
        mprintf("%s: p=%s\n","CheckCDF",formatCallingSequence(funname,list(x,a)))
        computed = __cdffun__f ( x , a );
        assert_checkequal ( computed , expected );
        mprintf("%s: p=%s\n","CheckCDF",formatCallingSequence(funname,list(x,a*v)))
        computed = __cdffun__f ( x , a*v );
        assert_checkequal ( computed , expected );
    elseif (nbargs==2) then
        mprintf("%s: p=%s\n","CheckCDF",formatCallingSequence(funname,list(x,a,b)))
        computed = __cdffun__f ( x , a , b );
        assert_checkequal ( computed , expected );
        mprintf("%s: p=%s\n","CheckCDF",formatCallingSequence(funname,list(x,a,b*v)))
        computed = __cdffun__f ( x , a , b*v );

        assert_checkequal ( computed , expected );
        mprintf("%s: p=%s\n","CheckCDF",formatCallingSequence(funname,list(x,a*v,b)))
        computed = __cdffun__f ( x , a*v , b );
        assert_checkequal ( computed , expected );
        mprintf("%s: p=%s\n","CheckCDF",formatCallingSequence(funname,list(x,a*v,b*v)))
        computed = __cdffun__f ( x , a*v , b*v );
        assert_checkequal ( computed , expected );
    elseif (nbargs==3) then
        mprintf("%s: p=%s\n","CheckCDF",formatCallingSequence(funname,list(x,a,b,c)))
        computed = __cdffun__f ( x , a , b, c );
        assert_checkequal ( computed , expected );
        mprintf("%s: p=%s\n","CheckCDF",formatCallingSequence(funname,list(x,a*v,b,c)))
        computed = __cdffun__f ( x , a*v , b , c );
        assert_checkequal ( computed , expected );
        mprintf("%s: p=%s\n","CheckCDF",formatCallingSequence(funname,list(x,a,b*v,c)))
        computed = __cdffun__f ( x , a , b*v , c );
        assert_checkequal ( computed , expected );
        mprintf("%s: p=%s\n","CheckCDF",formatCallingSequence(funname,list(x,a,b,c*v)))
        computed = __cdffun__f ( x , a , b , c*v );
        assert_checkequal ( computed , expected );
        mprintf("%s: p=%s\n","CheckCDF",formatCallingSequence(funname,list(x,a*v,b*v,c)))
        computed = __cdffun__f ( x , a*v , b*v , c );
        assert_checkequal ( computed , expected );
        mprintf("%s: p=%s\n","CheckCDF",formatCallingSequence(funname,list(x,a*v,b,c*v)))
        computed = __cdffun__f ( x , a*v , b , c*v );
        assert_checkequal ( computed , expected );
        mprintf("%s: p=%s\n","CheckCDF",formatCallingSequence(funname,list(x,a,b*v,c*v)))
        computed = __cdffun__f ( x , a , b*v , c*v );
        assert_checkequal ( computed , expected );
        mprintf("%s: p=%s\n","CheckCDF",formatCallingSequence(funname,list(x,a*v,b*v,c*v)))
        computed = __cdffun__f ( x , a*v , b*v , c*v );
        assert_checkequal ( computed , expected );
    else
        error(msprintf("%s: Unable to manage %d arguments.\n","CheckCDF",nbargs))
    end
    mprintf("%s: ... OK\n","CheckCDF")
endfunction

function str=cdflib_vec2string(x)
    // Convert a scalar or vector matrix of doubles into a string.
    //
    // Parameters
    // x : a scalar or vector matrix of doubles
    // str : a 1-by-1 matrix of strings
    //
    // Description
    // This function does not work for matrices.
    // To print more digits, we can use the "format" function.
    //
    // Examples
    // str=cdflib_vec2string(1.5)
    // expected="1.5"
    //
    // str=cdflib_vec2string([1 2 3]')
    // expected="[1;2;3]"
    //
    // str=cdflib_vec2string([1 2 3])
    // expected="[1,2,3]"
    //
    // // See more digits
    // format("e",25)
    // str=cdflib_vec2string(1.5)
    // expected="1.500000000000000000D+00"
    if (size(x,"*")==1) then
        if (typeof(x)=="boolean") then
            str="%"+string(x)
        else
            str=string(x)
        end
    elseif (size(x,"r")==1) then
        str="["+strcat(string(x),",")+"]"
    elseif (size(x,"c")==1) then
        str="["+strcat(string(x),";")+"]"
    else
        error(msprintf("%s: Cannot convert a matrix of doubles"))
    end
endfunction

function str=formatCallingSequence(funname,listofvec)
    // Creates a string containing a calling sequence.
    //
    // Description
    // This utility formats a list of actual arguments into 
    // a string, to be used to print the arguments used within a 
    // test.
    // This string can be copy/pasted into the console 
    // for a straightforward test.
    // Separates each item with a comma, to look as a 
    // regular function calling sequence.
    //
    // Examples
    // x=1
    // a=[1,2,3]
    // b=1
    // str=formatCallingSequence("myfunction",list(x,a,b))
    // expected="myfunction(1.5,[1,2,3],1)"
    str=funname+"("
    firstarg=%t
    for i=listofvec
        if (firstarg) then
            firstarg=%f
        else
            str=str+","
        end
        str=str+cdflib_vec2string(i)        
    end
    str=str+")"
endfunction


function CheckPDF(funname,__pdffun__,x)
    // Check a PDF function
    //
    // Calling Sequence
    //    CheckPDF(funname,pdffun,x)
    //
    // Parameters
    // funname : a 1-by-1 matrix of string, the function under test
    // pdffun : a list representing the function
    // x : a m-by-1 matrix of doubles, the points.
    //
    // Description
    // The CheckPDF function checks a PDF function with header :
    //
    // y=distfun_*pdf(x,a)
    // y=distfun_*pdf(x,a,b)
    // y=distfun_*pdf(x,a,b,c)
    // y=distfun_*pdf(x,a,b,c,d)
    //
    // The tests are designed to check the consistency of the 
    // function with respect to its API.
    //
    // The pdffun function must be a list(f,a) or 
    // (f,a,b) or (f,a,b,c) or (f,a,b,c,d) where 
    // f is the PDF function and a, b, or c or d are automatically 
    // added at the end of the calling sequence.
    //
    // The checks avoid numerical difficulties, which must be tested 
    // with datasets.
    // The values of a, b and c should be easy, so that no numerical 
    // difficulty can occur.
    //
    // This function makes the following checks.
    //  * The empty matrix x=[] must return y=[].
    //  * The calling sequence is tested where a, b or c are scalars.
    //    This is computed with a loop and on x(i). 
    //    This allows to check that the remaining vectorizes checks are 
    //    correct. 
    //  * The calling sequence is tested where all combinations of 
    //    scalar and vectors a, b and c are checked.
    //
    [lhs,rhs]=argn()
    apifun_checkrhs ( "CheckPDF" , rhs , 3 )
    apifun_checklhs ( "CheckPDF" , lhs , 0:1 )
    //
    // Check type
    apifun_checktype ( "CheckPDF" , funname , "funname" , 1 , "string" )
    apifun_checktype ( "CheckPDF" , __pdffun__ , "pdffun" , 2 , "list" )
    apifun_checktype ( "CheckPDF" , x , "x" , 3 , "constant" )
    //
    // Check size
    apifun_checkscalar("CheckPDF",funname,"funname",1)
    apifun_checkvector("CheckPDF",x,"x",3)
    //
    __pdffun__f = __pdffun__(1)
    nbargs = length(__pdffun__)-1
    __pdffun__args = list()
    for i=1:nbargs
        __pdffun__args(i)=__pdffun__(i+1)
    end
    //
    // Check empty matrix
    mprintf("%s: Checking empty matrix...\n","CheckPDF")
    if (nbargs==1) then
        mprintf("%s: y=%s([],[])\n","CheckPDF",funname)
        y = __pdffun__f ([],[])
    elseif (nbargs==2) then
        mprintf("%s: y=%s([],[],[])\n","CheckPDF",funname)
        y = __pdffun__f ([],[],[])
    elseif (nbargs==3) then
        mprintf("%s: y=%s([],[],[],[])\n","CheckPDF",funname)
        y = __pdffun__f ([],[],[],[])
    elseif (nbargs==4) then
        mprintf("%s: y=%s([],[],[],[],[])\n","CheckPDF",funname)
        y = __pdffun__f ([],[],[],[],[])
    else
        error(msprintf("%s: Unable to manage %d arguments.\n","CheckPDF",nbargs))
    end
    assert_checkequal ( y ,[]);
    mprintf("%s: ... OK\n","CheckPDF")
    //
    if (%f) then
        // Some functions do not support NANs, so 
        // disable this test.
    // Check NANs
    mprintf("%s: Checking NANs...\n","CheckPDF")
    if (nbargs==1) then
        mprintf("%s: y=%s(%%nan,a(1))\n","CheckPDF",funname)
        y = __pdffun__f(%nan,a(1))
        assert_checktrue(isnan(y))
        mprintf("%s: y=%s(x(1),%%nan)\n","CheckPDF",funname)
        y = __pdffun__f(x(1),%nan)
        assert_checktrue(isnan(y))
    elseif (nbargs==2) then
        mprintf("%s: y=%s(%%nan,a(1),b(1))\n","CheckPDF",funname)
        y = __pdffun__f(%nan,a(1),b(1))
        assert_checktrue(isnan(y))
        mprintf("%s: y=%s(x(1),%%nan,b(1))\n","CheckPDF",funname)
        y = __pdffun__f(x(1),%nan,b(1))
        assert_checktrue(isnan(y))
        mprintf("%s: y=%s(x(1),a(1),%%nan)\n","CheckPDF",funname)
        y = __pdffun__f(x(1),a(1),%nan)
        assert_checktrue(isnan(y))
    elseif (nbargs==3) then
        mprintf("%s: y=%s(%%nan,a(1),b(1),c(1))\n","CheckPDF",funname)
        y = __pdffun__f(%nan,a(1),b(1),c(1))
        assert_checktrue(isnan(y))
        mprintf("%s: y=%s(x(1),%%nan,b(1),c(1))\n","CheckPDF",funname)
        y = __pdffun__f(x(1),%nan,b(1),c(1))
        assert_checktrue(isnan(y))
        mprintf("%s: y=%s(x(1),a(1),%%nan,c(1))\n","CheckPDF",funname)
        y = __pdffun__f(x(1),a(1),%nan,c(1))
        assert_checktrue(isnan(y))
        mprintf("%s: y=%s(x(1),a(1),b(1),%%nan)\n","CheckPDF",funname)
        y = __pdffun__f(x(1),a(1),b(1),%nan)
        assert_checktrue(isnan(y))
    elseif (nbargs==4) then
        mprintf("%s: y=%s(%%nan,a(1),b(1),c(1),d(1))\n","CheckPDF",funname)
        y = __pdffun__f(%nan,a(1),b(1),c(1),d(1))
        assert_checktrue(isnan(y))
        mprintf("%s: y=%s(x(1),%%nan,b(1),c(1),d(1))\n","CheckPDF",funname)
        y = __pdffun__f(x(1),%nan,b(1),c(1),d(1))
        assert_checktrue(isnan(y))
        mprintf("%s: y=%s(x(1),a(1),%%nan,c(1),d(1))\n","CheckPDF",funname)
        y = __pdffun__f(x(1),a(1),%nan,c(1),d(1))
        assert_checktrue(isnan(y))
        mprintf("%s: y=%s(x(1),a(1),b(1),%%nan,d(1))\n","CheckPDF",funname)
        y = __pdffun__f(x(1),a(1),b(1),%nan,d(1))
        assert_checktrue(isnan(y))
        mprintf("%s: y=%s(x(1),a(1),b(1),c(1),%nan)\n","CheckPDF",funname)
        y = __pdffun__f(x(1),a(1),b(1),c(1),%nan)
        assert_checktrue(isnan(y))
    else
        error(msprintf("%s: Unable to manage %d arguments.\n","CheckPDF",nbargs))
    end
    mprintf("%s: ... OK\n","CheckPDF")
    end
    //
    nrows = size(x,"r")
    ncols = size(x,"c")
    //
    // Get a, b, c
    if (nbargs==1) then
        a=__pdffun__args(1)
    elseif (nbargs==2) then
        a=__pdffun__args(1)
        b=__pdffun__args(2)
    elseif (nbargs==3) then
        a=__pdffun__args(1)
        b=__pdffun__args(2)
        c=__pdffun__args(3)
    elseif (nbargs==4) then
        a=__pdffun__args(1)
        b=__pdffun__args(2)
        c=__pdffun__args(3)
        d=__pdffun__args(4)
    else
        error(msprintf("%s: Unable to manage %d arguments.\n","CheckPDF",nbargs))
    end
    //
    // Get reference
    mprintf("%s: Get reference quantiles...\n","CheckPDF")
    expected = []
    for i=1:nrows*ncols
        if (nbargs==1) then
            mprintf("%s: y(%d)=%s\n","CheckPDF",i,formatCallingSequence(funname,list(x(i),a)))
            expected(i) = __pdffun__f ( x(i) , a )
        elseif (nbargs==2) then
            mprintf("%s: y(%d)=%s\n","CheckPDF",i,formatCallingSequence(funname,list(x(i),a,b)))
            expected(i) = __pdffun__f ( x(i) , a , b )
        elseif (nbargs==3) then
            mprintf("%s: y(%d)=%s\n","CheckPDF",i,formatCallingSequence(funname,list(x(i),a,b,c)))
            expected(i) = __pdffun__f ( x(i) , a , b , c )
        elseif (nbargs==4) then
            mprintf("%s: y(%d)=%s\n","CheckPDF",i,formatCallingSequence(funname,list(x(i),a,b,c,d)))
            expected(i) = __pdffun__f ( x(i) , a , b , c,d )
        else
            error(msprintf("%s: Unable to manage %d arguments.\n","CheckPDF",nbargs))
        end
    end
    expected=matrix(expected,nrows,ncols)
    mprintf("%s: ... OK\n","CheckPDF")
    //
    // Test with expanded arguments
    mprintf("%s: With arguments expanded...\n","CheckPDF")
    v = ones(nrows,ncols)
    if (nbargs==1) then
        mprintf("%s: y=%s\n","CheckPDF",formatCallingSequence(funname,list(x,a)))
        computed = __pdffun__f ( x , a );
        assert_checkequal ( computed , expected );
        mprintf("%s: y=%s\n","CheckPDF",formatCallingSequence(funname,list(x,a*v)))
        computed = __pdffun__f ( x , a*v );
        assert_checkequal ( computed , expected );
    elseif (nbargs==2) then
        mprintf("%s: y=%s\n","CheckPDF",formatCallingSequence(funname,list(x,a,b)))
        computed = __pdffun__f ( x , a , b );
        assert_checkequal ( computed , expected );
        mprintf("%s: y=%s\n","CheckPDF",formatCallingSequence(funname,list(x,a,b*v)))
        computed = __pdffun__f ( x , a , b*v );
        assert_checkequal ( computed , expected );
        mprintf("%s: y=%s\n","CheckPDF",formatCallingSequence(funname,list(x,a*v,b)))
        computed = __pdffun__f ( x , a*v , b );
        assert_checkequal ( computed , expected );
        mprintf("%s: y=%s\n","CheckPDF",formatCallingSequence(funname,list(x,a*v,b*v)))
        computed = __pdffun__f ( x , a*v , b*v );
        assert_checkequal ( computed , expected );
    elseif (nbargs==3) then
        mprintf("%s: y=%s\n","CheckPDF",formatCallingSequence(funname,list(x,a,b,c)))
        computed = __pdffun__f ( x , a , b, c );
        assert_checkequal ( computed , expected );
        mprintf("%s: y=%s\n","CheckPDF",formatCallingSequence(funname,list(x,a*v,b,c)))
        computed = __pdffun__f ( x , a*v , b , c );
        assert_checkequal ( computed , expected );
        mprintf("%s: y=%s\n","CheckPDF",formatCallingSequence(funname,list(x,a,b*v,c)))
        computed = __pdffun__f ( x , a , b*v , c );
        assert_checkequal ( computed , expected );
        mprintf("%s: y=%s\n","CheckPDF",formatCallingSequence(funname,list(x,a,b,c*v)))
        computed = __pdffun__f ( x , a , b , c*v );
        assert_checkequal ( computed , expected );
        mprintf("%s: y=%s\n","CheckPDF",formatCallingSequence(funname,list(x,a*v,b*v,c)))
        computed = __pdffun__f ( x , a*v , b*v , c );
        assert_checkequal ( computed , expected );
        mprintf("%s: y=%s\n","CheckPDF",formatCallingSequence(funname,list(x,a*v,b,c*v)))
        computed = __pdffun__f ( x , a*v , b , c*v );
        assert_checkequal ( computed , expected );
        mprintf("%s: y=%s\n","CheckPDF",formatCallingSequence(funname,list(x,a,b*v,c*v)))
        computed = __pdffun__f ( x , a , b*v , c*v );
        assert_checkequal ( computed , expected );
        mprintf("%s: y=%s\n","CheckPDF",formatCallingSequence(funname,list(x,a*v,b*v,c*v)))
        computed = __pdffun__f ( x , a*v , b*v , c*v );
        assert_checkequal ( computed , expected );
    elseif (nbargs==4) then
        mprintf("%s: y=%s\n","CheckPDF",formatCallingSequence(funname,list(x,a,b,c,d)))
        computed = __pdffun__f ( x , a , b, c ,d);
        assert_checkequal ( computed , expected );
        mprintf("%s: y=%s\n","CheckPDF",formatCallingSequence(funname,list(x,a*v,b,c,d)))
        computed = __pdffun__f ( x , a*v , b , c ,d);
        assert_checkequal ( computed , expected );
        mprintf("%s: y=%s\n","CheckPDF",formatCallingSequence(funname,list(x,a,b*v,c,d)))
        computed = __pdffun__f ( x , a , b*v , c ,d);
        assert_checkequal ( computed , expected );
        mprintf("%s: y=%s\n","CheckPDF",formatCallingSequence(funname,list(x,a,b,c*v,d)))
        computed = __pdffun__f ( x , a , b , c*v ,d);
        assert_checkequal ( computed , expected );
        mprintf("%s: y=%s\n","CheckPDF",formatCallingSequence(funname,list(x,a,b,c,d*v)))
        computed = __pdffun__f ( x , a , b , c ,d*v);
        assert_checkequal ( computed , expected );
        mprintf("%s: y=%s\n","CheckPDF",formatCallingSequence(funname,list(x,a*v,b*v,c,d)))
        computed = __pdffun__f ( x , a*v , b*v , c ,d);
        assert_checkequal ( computed , expected );
        mprintf("%s: y=%s\n","CheckPDF",formatCallingSequence(funname,list(x,a*v,b,c*v,d)))
        computed = __pdffun__f ( x , a*v , b , c*v ,d);
        assert_checkequal ( computed , expected );
        mprintf("%s: y=%s\n","CheckPDF",formatCallingSequence(funname,list(x,a,b*v,c*v,d)))
        computed = __pdffun__f ( x , a , b*v , c*v ,d);
        assert_checkequal ( computed , expected );
        mprintf("%s: y=%s\n","CheckPDF",formatCallingSequence(funname,list(x,a*v,b*v,c*v,d)))
        computed = __pdffun__f ( x , a*v , b*v , c*v ,d);
        assert_checkequal ( computed , expected );
    else
        error(msprintf("%s: Unable to manage %d arguments.\n","CheckPDF",nbargs))
    end
    mprintf("%s: ... OK\n","CheckPDF")
endfunction

function CheckPDFvsCDF(varargin)
    // Check the consistency between the PDF and the CDF functions
    //
    // Calling Sequence
    //    CheckPDFvsCDF(pdffun,cdffun,x,rtol)
    //    CheckPDFvsCDF(pdffun,cdffun,x,rtol,iscontinuous)
    //
    // Parameters
    // pdffun : a list representing the function
    // cdffun : a list representing the function
    // x : a m-by-1 matrix of doubles, the points.
    // rtol : a 1-by-1 matrix of doubles, the relative tolerance
    // iscontinuous : a 1-by-1 matrix of booleans, %t for continuous variables, %f for integer variables (default iscontinuous=%t)
    //
    // Description
    // The CheckPDF function checks a PDF function with header :
    //
    // y=distfun_*pdf(x,a)
    // y=distfun_*pdf(x,a,b)
    // y=distfun_*pdf(x,a,b,c)
    //
    // and a CDF with header :
    //
    // y=distfun_*cdf(x,a)
    // y=distfun_*cdf(x,a,b)
    // y=distfun_*cdf(x,a,b,c)
    //
    // The pdffun function must be a list(f,a) or 
    // (f,a,b) or (f,a,b,c) where 
    // f is the PDF function and a, b, or c are automatically 
    // added at the end of the calling sequence.
    //
    // The cdffun function must be a list(f,a) or 
    // (f,a,b) or (f,a,b,c) where 
    // f is the CDF function and a, b, or c are automatically 
    // added at the end of the calling sequence.
    //
    // The test checks that the derivative of the CDF is the PDF. 
    //
    // We could as well integrate the PDF to get the CDF. 
    // Numerical derivation can be unstable, while 
    // numerical integration is more stable numerically. 
    // On the other hand, numerical integration requires 
    // a lower bound of integration: P(X<=x)=Int_xmin^x f(x)dx.
    // The value of xmin depends from distribution to distribution. 
    // Moreover, the mathematical value of xmin (e.g. -Inf for the 
    // Normal distribution) can be different from the numerical 
    // value (e.g. mu-10*sigma). 
    // If this difference is not taken into account, the 
    // integration solver may not find a point where the PDF function 
    // is nonzero.
    // This is why we restrict ourselves to the 
    // derivation check.
    //
    [lhs,rhs]=argn()
    apifun_checkrhs ( "CheckPDFvsCDF" , rhs , 4:5 )
    apifun_checklhs ( "CheckPDFvsCDF" , lhs , 0:1 )
    //
    __pdffun__ = varargin(1)
    __cdffun__ = varargin(2)
    x = varargin(3)
    rtol = varargin(4)
    iscontinuous =apifun_argindefault ( varargin,5,%t )
    //
    // Check type
    apifun_checktype ( "CheckPDFvsCDF" , __pdffun__ , "pdffun" , 1 , "list" )
    apifun_checktype ( "CheckPDFvsCDF" , __cdffun__ , "cdffun" , 2 , "list" )
    apifun_checktype ( "CheckPDFvsCDF" , x , "x" , 3 , "constant" )
    apifun_checktype ( "CheckPDFvsCDF" , rtol , "rtol" , 4 , "constant" )
    apifun_checktype ( "CheckPDFvsCDF" , iscontinuous , "iscontinuous" , 5 , "boolean" )
    //
    // Check size
    apifun_checkvector("CheckPDFvsCDF",x,"x",3)
    apifun_checkscalar("CheckPDFvsCDF",rtol , "rtol" , 4)
    apifun_checkscalar("CheckPDFvsCDF",iscontinuous , "iscontinuous" , 5)
    //
    // Manage the PDF, the CDF
    __pdffun__f = __pdffun__(1)
    __cdffun__f = __cdffun__(1)
    apifun_checktype("CheckPDFvsCDF",__pdffun__f,"pdffun(1)",1,"function")
    apifun_checktype("CheckPDFvsCDF",__cdffun__f,"pdffun(2)",2,"function")
    //
    // Compute the PDF
    mprintf("%s: Get reference PDF...\n","CheckPDFvsCDF")
    expected = __pdffun__f ( x , __pdffun__(2:$) )
    //
    // Derive the PDF from the CDF
    mprintf("%s: Derivate the CDF...\n","CheckPDFvsCDF")
    computed = distfun_genericpdf(x,__cdffun__,iscontinuous)
    //
    // Check
    mprintf("%s: Check...\n","CheckPDFvsCDF")
    digits=assert_computedigits(expected,computed)
    digits=floor(min(digits))
    [flag,errmsg] = assert_checkalmostequal ( expected , computed , rtol ,[], "element");
    if (~flag) then
        mprintf("%s: Number of common digits: %s\n","CheckPDFvsCDF",string(digits))
        mprintf("%s: Number of required digits: %s\n","CheckPDFvsCDF",string(-log10(rtol)))
        error(msprintf("%s: %s","CheckPDFvsCDF",errmsg))
    end
    mprintf("%s: ...OK\n","CheckPDFvsCDF")
endfunction


function C = test_cov ( varargin )
    // Returns the empirical covariance matrix.
    //
    // Calling Sequence
    //   This is a duplicate of the cov function 
    //   in Stixbox. 
    //   This is because we need cov to test 
    //   distfun, but Stixbox depends on distfun, 
    //   and not the contrary : the two modules 
    //   virtually depend to each other. 
    //   This is why we put this utility in the 
    //   tests, but not in the API of distfun, 
    //   to prevent a duplicate.


    [lhs,rhs]=argn()
    apifun_checkrhs ( "test_cov" , rhs , 1:3 )
    apifun_checklhs ( "test_cov" , lhs , 0:1 )
    //
    if (rhs==1) then
        x = varargin(1)
        //
        // Check type
        apifun_checktype ( "test_cov" , x , "x" , 1 , "constant" )
        nobs = size(x,"r")
        r = 1/(nobs-1)
        A = x
    elseif (rhs==2) then
        //
        x = varargin(1)
        y = varargin(2)
        //
        // Check type
        apifun_checktype ( "test_cov" , x , "x" , 1 , "constant" )
        apifun_checktype ( "test_cov" , y , "y" , 2 , "constant" )
        //
        // Check size
        nobs = size(x,"r")
        if (size(y,"*")==1) then
            apifun_checkoption ( "test_cov" , y , "y" , 2, [0 1])
            if (y==1) then
                r = 1/nobs
                A = x
            elseif (y==0) then
                r = 1/(nobs-1)
                A = x
            end
        else
            apifun_checkdims ( "test_cov" , x , "x" , 1, [nobs 1] )
            apifun_checkdims ( "test_cov" , y , "y" , 2, [nobs 1] )
            r = 1/(nobs-1)
            A = [x,y]
        end
    elseif (rhs==3) then
        //
        x = varargin(1)
        y = varargin(2)
        nrmlztn = varargin(3)
        //
        // Check type
        apifun_checktype ( "test_cov" , x , "x" , 1 , "constant" )
        apifun_checktype ( "test_cov" , y , "y" , 2 , "constant" )
        apifun_checktype ( "test_cov" , nrmlztn , "nrmlztn" , 3 , "constant" )
        //
        // Check size
        nobs = size(x,"r")
        apifun_checkdims ( "test_cov" , x , "x" , 1, [nobs 1] )
        apifun_checkdims ( "test_cov" , y , "y" , 2, [nobs 1] )
        apifun_checkscalar ( "test_cov" , nrmlztn , "nrmlztn" , 3)
        //
        // Check content
        apifun_checkoption ( "test_cov" , nrmlztn , "nrmlztn" , 3, [0 1])
        A = [x,y]
        if (nrmlztn==1) then
            r = 1/nobs
        else
            r = 1/(nobs-1)
        end
    end
    //
    // Compute with A in the general case
    nvar = size(A,"c")
    nobs = size(A,"r")
    for i = 1 : nvar
        A(:,i) = A(:,i) - mean(A(:,i))
    end
    C = zeros(nvar,nvar)
    for i = 1 : nvar
        C(i,i) = A(:,i)'*A(:,i)*r
        for j = i+1 : nvar
            C(i,j) = A(:,i)'*A(:,j)*r
            C(j,i) = C(i,j)
        end

    end
endfunction
