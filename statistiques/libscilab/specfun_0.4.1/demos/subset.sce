// Copyright (C) 2009 - 2010 - DIGITEO - Michael Baudin
//
// This file must be used under the terms of the GNU LGPL license.

/////////////////////////////////////////////////////////////////////////////
// Slower implementations of specfun_subset

// Returns all nchoosek of k values from x as a row-by-row array
// with (n,k) rows where (n,k) is the binomial coefficient and n is the 
// number of values in x. 
// http://home.att.net/~srschmitt/script_nchoosek.html
// Kenneth H. Rosen, Discrete Mathematics and Its Applications, 2nd edition (NY: McGraw-Hill, 1991), pp. 284-286.
function cmap = specfun_subsetnaive ( x , k )
  n = size(x,"*")
  c = specfun_nchoosek ( n , k )
  cmap = zeros(k,c)
  a = 1:k
  cmap(:,1) = x(a)
  for m = 2 : c
    i = k
    while ( a(i) == n - k + i )
      i = i - 1
    end
    a(i) = a(i) + 1
    for j = i+1 : k
      a(j) = a(i) + j - i
    end
    cmap(:,m) = x(a)
  end
endfunction

function F = specfun_subsetRecCol ( E , k )
    // Compute all the subsets with  elements of the set E.
    //
    // Parameters
    // E : a n-by-1 matrix of doubles
    // k : a 1-by-1 matrix of floating point integers
    // F : a n-by-cnk matrix of doubles
    //
    // Description
    //   The returnd subsets are column-by-column.
    //
    // Authors : Chancelier, Pincon

  n = length(E)
  if ( k > n ) then
    F = []
  elseif ( k == n ) then
    F = E
  elseif ( k == 1 ) then
    F = E'
  else
    F = [];
    for i = 1:n-k+1
      EE = E(i+1:n)
      FF = specfun_subsetRecCol (EE,k-1)
      mm = size(FF,"c")
      F = [F , [ones(1,mm)*E(i);FF]]
    end
  end
endfunction

function F = specfun_subsetRecRow ( E , k )
    // Compute all the subsets with  elements of the set E.
    //
    // Parameters
    // E : a 1-by-n matrix of doubles
    // k : a 1-by-1 matrix of floating point integers
    // F : a cnk-by-n matrix of doubles
    //
    // Description
    //   The returnd subsets are row-by-row.
    //
    // Authors : Chancelier, Pincon

  n = length(E)
  if ( k > n ) then
    F = []
  elseif ( k == n ) then
    F = E
  elseif ( k == 1 ) then
    F = E'
  else
    F = [];
    for i = 1:n-k+1
      EE = E(i+1:n)
      FF = specfun_subsetRecRow(EE,k-1)
      mm = size(FF,1)
      F = [F ; E(i)*ones(mm,1),FF]
    end
  end
endfunction

function [t,msg] = benchfun ( name , __benchfun_fun__ , iargs , nlhs , kmax )
  // Benchmarks a function and measure its performance.
  //
  // Calling sequence
  //   [t,msg] = benchfun ( name , fun , iargs , nlhs , kmax )
  //
  // Parameters
  //   name : a 1 x 1 matrix of strings, the name of the function to be executed
  //   fun : a function, the function to be executed
  //   iargs : a list, the list of input arguments for the function
  //   nlhs : a 1 x 1 matrix of floating point integers, the number of output arguments of the function
  //   kmax : a 1 x 1 matrix of floating point integers, the number of executions
  //   t : a kmax x 1 matrix of doubles, the system times, in seconds, required to execute the function
  //   msg : a 1 x 1 matrix of strings, a message summarizing the benchmark
  //
  // Description
  //   This function is designed to be used when measuring the 
  //   performance of a function.
  //   It uses the tic/toc functions to measure the user time, i.e. the 
  //   wall clock time.
  //   The function is executed kmax times and the performance is 
  //   gathered into the matrix t.
  //   The message summarizes the test and contains the mean,
  //   min and max of the times.
  //
  // Examples
  // function c = pascalup_col (n)
  //   // Pascal up matrix.
  //   // Column by column version
  //   c = eye(n,n)
  //   c(1,:) = ones(1,n)
  //   for i = 2:(n-1)
  //     c(2:i,i+1) = c(1:(i-1),i)+c(2:i,i)
  //   end
  // endfunction
  // benchfun ( "pascalup_col" , pascalup_col , 100 , 10 );
  //
  // Authors
  //   2010 - DIGITEO - Michael Baudin
  
  [lhs,rhs] = argn()
  if ( rhs<>5 ) then
    noerrmsg = sprintf(gettext("%s: Unexpected number of arguments : %d provided while %d are expected."),..
      "benchfun",rhs,5);
    error(noerrmsg)
  end
  //
  // Check type
  if ( typeof(name) <> "string" ) then 
    error(sprintf(gettext("%s: Wrong type for input argument #%d: variable %s has type %s while %s is expected.\n"), ..
      "benchfun", 1, "name" , typeof(name) , "string"))
  end
  if ( and ( typeof(__benchfun_fun__) <> ["function" "list" "fptr"] ) ) then 
    error(sprintf(gettext("%s: Wrong type for input argument #%d: variable %s has type %s while %s is expected.\n"), ..
      "benchfun", 2, "fun" , typeof(__benchfun_fun__) , "function"))
  end
  if ( typeof(iargs) <> "list" ) then 
    error(sprintf(gettext("%s: Wrong type for input argument #%d: variable %s has type %s while %s is expected.\n"), ..
      "benchfun", 3, "nlhs" , typeof(iargs) , "list"))
  end
  if ( typeof(nlhs) <> "constant" ) then 
    error(sprintf(gettext("%s: Wrong type for input argument #%d: variable %s has type %s while %s is expected.\n"), ..
      "benchfun", 4, "nlhs" , typeof(nlhs) , "constant"))
  end
  if ( typeof(kmax) <> "constant" ) then 
    error(sprintf(gettext("%s: Wrong type for input argument #%d: variable %s has type %s while %s is expected.\n"), ..
      "benchfun", 5, "kmax" , typeof(kmaxs) , "constant"))
  end
  //
  // Check size
  if ( size(name,"*") <> 1 ) then 
    error(sprintf(gettext("%s: Wrong size for input argument #%d: variable %s has size %d while %d is expected.\n"), ..
      "benchfun", 1, "name" , size(name,"*") , 1))
  end
  if ( size(nlhs,"*") <> 1 ) then 
    error(sprintf(gettext("%s: Wrong size for input argument #%d: variable %s has size %d while %d is expected.\n"), ..
      "benchfun", 4, "nlhs" , size(nlhs,"*") , 1))
  end
  if ( size(kmax,"*") <> 1 ) then 
    error(sprintf(gettext("%s: Wrong size for input argument #%d: variable %s has size %d while %d is expected.\n"), ..
      "benchfun", 5, "kmax" , size(kmax,"*") , 1))
  end

  //
  // Compute the intstruction string to be launched
  ni = length ( iargs )
  instr = ""
  // Put the LHS arguments
  if ( nlhs > 0 ) then
    instr = instr + "["
  end
  for i = 1 : nlhs
    if ( i > 1 ) then
      instr = instr + ","
    end
    instr = instr + "x" + string(i)
  end
  if ( nlhs > 0 ) then
    instr = instr + "]"
  end
  if ( nlhs > 0 ) then
    instr = instr + "="
  end
  // Put the RHS arguments
  instr = instr + "__benchfun_fun__("
  for i = 1 : ni
    if ( i > 1 ) then
      instr = instr + ","
    end
    instr = instr + "iargs("+string(i)+")"
  end
  instr = instr + ")"
  //
  // Loop over the tests
  for k = 1 : kmax
    // Call the function
    tic()
    ierr = execstr ( instr , "errcatch" )
    t(k) = toc()
    if ( ierr <> 0 ) then
      errmsg = lasterror()
      error(sprintf(gettext("%s: Failed to run function: %s.\n"), ..
        "benchfun", errmsg))
    end
  end
  msg = msprintf("%s: %d iterations, mean=%f, min=%f, max=%f\n",name,kmax,mean(t),min(t),max(t))
  mprintf("%s\n",msg)
endfunction

// Compare the outputs
specfun_subset ( (1:5) , 3 )
specfun_subsetRecCol ( (1:5)' , 3 )'
specfun_subsetRecRow ( (1:5) , 3 )
specfun_subsetnaive ( (1:5)' , 3 )'

// Bench various algorithms
// k small, n large
// The recursive version make 2 recursive calls in this case.
n = 60
k = 3
x = (1:n);
specfun_nchoosek(n,k) // 34220
benchfun ( "subset(row)" , specfun_subset , list(x,k) , 1 , 10 );
benchfun ( "subset(column)" , specfun_subset , list(x',k,"c") , 1 , 10 );
benchfun ( "recursiveCol" , specfun_subsetRecCol , list(x',k) , 1 , 10 );
benchfun ( "recursiveRow" , specfun_subsetRecRow , list(x,k) , 1 , 10 );
benchfun ( "naive" , specfun_subsetnaive , list(x',k) , 1 , 10 );

// Another test
// k is half of n.
// The recursive version make 8 recursive calls in this case.
n = 18
k = 9
x = (1:n);
specfun_nchoosek(n,k) // 48620
benchfun ( "subset(row)" , specfun_subset , list(x,k) , 1 , 10 );
benchfun ( "subset(column)" , specfun_subset , list(x',k,"c") , 1 , 10 );
benchfun ( "recursiveCol" , specfun_subsetRecCol , list(x',k) , 1 , 10 );
benchfun ( "recursiveRow" , specfun_subsetRecRow , list(x,k) , 1 , 10 );
benchfun ( "naive" , specfun_subsetnaive , list(x',k) , 1 , 10 );

// Another test
// k is close to n.
// The recursive version make 56 recursive calls in this case and is much slower.
n = 60
k = 57
x = (1:n);
specfun_nchoosek(n,k) // 34220
benchfun ( "subset(row)" , specfun_subset , list(x,k) , 1 , 10 );
benchfun ( "subset(column)" , specfun_subset , list(x',k,"c") , 1 , 10 );
benchfun ( "recursiveCol" , specfun_subsetRecCol , list(x',k) , 1 , 1 );
benchfun ( "recursiveRow" , specfun_subsetRecRow , list(x,k) , 1 , 1 );
benchfun ( "naive" , specfun_subsetnaive , list(x',k) , 1 , 2 );

// Make all versions fail
stacksize("max");
n = 10000
k = n-1
specfun_nchoosek(n,k) // 9128, which is not large
specfun_subsetRecCol ((1:n)',k); // fails
specfun_subset (1:n,k); // Not enough memory



