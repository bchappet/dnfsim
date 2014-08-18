// Copyright (C) 2008 - 2010 - DIGITEO - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

// <-- JVM NOT MANDATORY -->

//
// assert_equal --
//   Returns 1 if the two real matrices computed and expected are equal.
// Arguments
//   computed, expected : the two matrices to compare
//   epsilon : a small number
//
function flag = assert_equal ( computed , expected )
  if ( and ( computed==expected ) ) then
    flag = 1;
  else
    flag = 0;
  end
  if flag <> 1 then pause,end
endfunction

// A sample function with many checkings.
//
// We make the checkings on the fly, that is, we 
// check the arguments as soon as they are read.
// Indeed, the value of n is read, checked, then used in the  
// checkings for remaining parameters.
// For example, the default value for the bounds variable 
// uses the n variable. In the case where n is not a floating 
// point integer (e.g. a boolean or a string), the type check 
// for n generates an error.
// Instead, if the type check for n is moved later in the 
// algorithm, then the statement "bounds = [zeros(n,1) ones(n,1)]"
// generates an error which is difficult to understand for the 
// user.


function [ integr , accur , varf ] = mycrudemc ( varargin )
  // Estimates a multidimensional integral using Monte Carlo.
  //
  // Calling Sequence
  //   integr = mycrudemc ( func , n )
  //   integr = mycrudemc ( func , n , callf )
  //   integr = mycrudemc ( func , n , callf , bounds )
  //   integr = mycrudemc ( func , n , callf , bounds , randgen )
  //   [ integr , accur , varf ] = mycrudemc ( ... )
  //
  // Parameters
  //   func : the function to be evaluated. If func is a list, the first element is expected to be a function and the remaining elements of the list are input arguments of the function, which are appended at the end.
  //   n: a 1 x 1 matrix of floating point integers, the spatial dimension.
  //   callf : a 1 x 1 matrix of floating point integers, the number of calls to the function (default = 1.e4)
  //   bounds : a n x 2 matrix of doubles, where lowb = bounds(:,1) = lower bound and uppb = bounds(:,2) = upper bound of integration (default = [zeros(n,1) ones(n,1)])
  //   randgen : a function, the random number generator. (default = grand)
  //   integr : a 1 x 1 matrix of doubles, the approximate value of the integral, the mean of the function.
  //   accur : a 1 x 1 matrix of doubles, the estimated error on the integral.
  //   varf : a 1 x 1 matrix of doubles, the approximate value of the variance of f.
  //
  // Description
  // The algorithm uses a crude Monte-Carlo method to estimate the integral.
  //
  // Authors
  //   Michael Baudin - 2010 - DIGITEO

  [lhs, rhs] = argn()
  apifun_checkrhs ( "mycrudemc" , rhs , 2 : 5 )
  apifun_checklhs ( "mycrudemc" , lhs , 0 : 3 )
  //
  func = varargin(1)
  apifun_checktype ( "mycrudemc" , func ,    "func" ,    1 , [ "function" "list" ] )
  n = varargin(2)
  apifun_checktype ( "mycrudemc" , n ,       "n" ,       2 , "constant" )
  apifun_checkscalar ( "mycrudemc" , n ,       "n" ,       2 )
  apifun_checkgreq ( "mycrudemc" , n ,     "n" ,     2 , 1 )
  callf = 1.e4
  if ( rhs >= 3 ) then
    if ( varargin(3) <> [] ) then
      callf = varargin(3)
      apifun_checktype ( "mycrudemc" , callf ,   "callf" ,   3 , "constant" )
      apifun_checkscalar ( "mycrudemc" , callf ,   "callf" ,   3 )
      apifun_checkgreq ( "mycrudemc" , callf , "callf" , 3 , 1 )
    end
  end
  bounds = [zeros(n,1) ones(n,1)]
  if ( rhs >= 4 ) then
    if ( varargin(4) <> [] ) then
      bounds = varargin(4)
      apifun_checktype ( "mycrudemc" , bounds ,  "bounds" ,  4 , "constant" )
      apifun_checkdims ( "mycrudemc" , bounds ,  "bounds" ,  4 , [n 2] )
    end
  end
  body = [
    "x=grand ( m , n , ""def"")"
  ];
  prot=funcprot();
  funcprot(0);
  deff("x=randgen(m,n)",body);
  funcprot(prot);
  if ( rhs >= 5 ) then
    if ( varargin(5) <> [] ) then
      prot=funcprot();
      funcprot(0);
      randgen = varargin(5)
      funcprot(prot);
      apifun_checktype ( "mycrudemc" , randgen , "randgen" , 5 , [ "function" "list" ] )
    end
  end
  //
  lowb = bounds (:,1)
  uppb = bounds (:,2)
  //
  if ( typeof(randgen) == "list" ) then  
    // List
    R = randgen(1); 
    u = R ( callf , n , randgen(2:$)); 
  else
    // Macro or compiled macro
    u = randgen ( callf , n );
  end
  if ( size(u) <> [callf n] ) then
    msg = msprintf(gettext("%s: Error: The expected shape of u from the random number generator is [%d,%d], while computed shape is [%d,%d].") , "mycrudemc",callf,n,size(u,"r"),size(u,"c"));
    error ( msg )
  end
  x = ones(callf,1) * lowb' + (ones(callf,1) * (uppb - lowb)') .*u;
  if ( typeof(func) == "list" ) then  
    // List or tlist
    R = func(1); 
    y = R ( callf , n , x , func(2:$)); 
  else
    // Macro or compiled macro
    y = func ( callf , n, x );
  end
  if ( size(y) <> [callf 1] ) then
    msg = msprintf(gettext("%s: Error: The expected shape of y = func(m,n,x) is [%d,%d], while computed shape is [%d,%d].") , "mycrudemc",m,1,size(y,"r"),size(y,"c"));
    error ( msg )
  end
  volume = prod ( uppb - lowb )
  mu = mean(y)
  integr = volume * mu
  // Do not use the variance function : saves the evaluation of mu
  varf = sum((y-mu).^2) / (callf - 1)
  accur = volume * sqrt(varf) / sqrt(callf - 1)
endfunction


function r = sumfunction (m,n,x)
    v = n/12
    e = n/2
    r = sqrt(1/v) * (sum(x,"c") - e)
endfunction

function [ value , seed ] = shragerandom ( seed )
  //  1979 - Linus Schrage
  //  2007 - John Burkardt (MATLAB version)
  //  2010 - Michael Baudin (Scilab version)
  //    "A More Portable Fortran Random Number Generator", Linus Schrage, ACM Transactions on Mathematical Software, Volume 5, Number 2, June 1979, pages 132-138.

  a = 16807
  b15 = 32768
  b16 = 65536
  p = 2147483647
  
  xhi = floor ( seed / b16 )
  xalo = ( seed - xhi * b16 ) * a
  leftlo = floor ( xalo / b16 )
  fhi = xhi * a + leftlo
  k = floor ( fhi / b15 )
  seed = ( ( ( xalo - leftlo * b16 ) - p  ) + ( fhi - k * b15 ) * b16 ) + k
  if ( seed < 0 )
    seed = seed + p
  end
  value = seed / p
endfunction

function u = shragerand ( m , n )
  global shrageseed
  u = zeros(m,n)
  for k = 1 : m
    for i = 1 : n
      [ value , shrageseed ] = shragerandom ( shrageseed );
      u(k,i) = value
    end
  end
endfunction

function r = sqrtfunction (m,n,x)
  r = sqrt(x)
endfunction

function u = mygen ( m , n , seed , gentype )
  grand ( "setgen" , gentype )
  grand ( "setsd" , seed )
  u = grand ( m , n , "def" )
endfunction

function r = sumfunction2 (m,n,x,e,v)
    r = sqrt(1/v) * (sum(x,"c") - e)
endfunction

//
stacksize("max")
//
//////////////////////////////////////////
// Calling sequences which work
//
// Test with default settings
dim_num = 10;
[ integr , accur , varf ] = mycrudemc ( sumfunction , dim_num );
//
// Customize callf
dim_num = 10;
callf = 1.e5;
[ integr , accur , varf ] = mycrudemc ( sumfunction , dim_num , callf );
//
dim_num = 1;
callf = [];
lowb = 10;
uppb = 30;
bounds = [lowb uppb];
[ integr , accur , varf ] = mycrudemc ( sqrtfunction , dim_num , callf , bounds );
//
// Customize randgen
global shrageseed;
shrageseed = 123456;
dim_num = 3;
callf = 1000;
bounds = [];
[ integr , accur , varf ] = mycrudemc ( sumfunction , dim_num , callf , bounds , shragerand );
//
// Customize randgen, which has additionnal parameters
dim_num = 3;
callf = 1000;
bounds = [];
randgen = list(mygen,123456,"urand");
[ integr , accur , varf ] = mycrudemc ( sumfunction , dim_num , callf , bounds , randgen );

//
// Customize the function :
// use a function which has additionnal parameters.
dim_num = 3;
func = list(sumfunction2,dim_num/2,dim_num/12);
[ integr , accur , varf ] = mycrudemc ( func , dim_num );

//////////////////////////////////////////
// Calling sequences which do not work
// Wrong number of arguments
dim_num = 3;
callf = 1000;
lowb = 10*ones(dim_num,1);
uppb = 30*ones(dim_num,1);
bounds = [lowb uppb];
ierr=execstr("mycrudemc ( sumfunction )","errcatch");
assert_equal ( ierr , 10000 );
// Wrong type for the argument #1
ierr=execstr("mycrudemc ( 1 , 1 )","errcatch");
assert_equal ( ierr , 10000 );
// Wrong type for the argument #2
ierr=execstr("mycrudemc ( sumfunction , %t )","errcatch");
assert_equal ( ierr , 10000 );
// Wrong type for the argument #3
ierr=execstr("mycrudemc ( sumfunction , dim_num, %t )","errcatch");
assert_equal ( ierr , 10000 );
// Wrong type for the argument #4
ierr=execstr("mycrudemc ( sumfunction , dim_num, callf , %t )","errcatch");
assert_equal ( ierr , 10000 );
// Wrong type for the argument #5
ierr=execstr("mycrudemc ( sumfunction , dim_num, callf , bounds , %t )","errcatch");
assert_equal ( ierr , 10000 );
// Wrong size for argument #2
ierr=execstr("mycrudemc ( sumfunction , [12 12] )","errcatch");
assert_equal ( ierr , 10000 );
// Wrong size for argument #3
ierr=execstr("mycrudemc ( sumfunction , dim_num , [12 12] )","errcatch");
assert_equal ( ierr , 10000 );
// Wrong value for argument #2
ierr=execstr("mycrudemc ( sumfunction , -1 )","errcatch");
assert_equal ( ierr , 10000 );
// Wrong value for argument #3
ierr=execstr("mycrudemc ( sumfunction , dim_num , -1 )","errcatch");
assert_equal ( ierr , 10000 );

