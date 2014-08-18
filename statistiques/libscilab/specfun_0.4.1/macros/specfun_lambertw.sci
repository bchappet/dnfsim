// Copyright (C) 2010 - DIGITEO - Michael Baudin
// Copyright (C) 2009 - Pascal Getreuer
//
// Redistribution and use in source and binary forms, with or without 
// modification, are permitted provided that the following conditions are 
// met:
//
//     * Redistributions of source code must retain the above copyright 
//       notice, this list of conditions and the following disclaimer.
//     * Redistributions in binary form must reproduce the above copyright 
//       notice, this list of conditions and the following disclaimer in 
//       the documentation and/or other materials provided with the distribution
//       
// THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
// AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE 
// IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE 
// ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE 
// LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR 
// CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF 
// SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS 
// INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN 
// CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) 
// ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE 
// POSSIBILITY OF SUCH DAMAGE.

function w = specfun_lambertw ( varargin )
  // The Lambert W-Function.
  //
  // Calling Sequence
  //   w = specfun_lambertw ( z )
  //   w = specfun_lambertw ( z , b )
  //
  // Parameters
  //   z : a matrix of doubles
  //   b : a matrix of floating point integers, the index of the branch of the multi-valued function (default b = 0).
  //   w : a matrix of doubles
  //
  // Description
  //   If b=0, this function computes the principal value of the Lambert 
  //   W-Function, the solution of the equation
  //
  // <latex>
  // \begin{eqnarray}
  // z = w \exp(w)
  // \end{eqnarray}
  // </latex>
  //
  //   z may be a complex scalar or array.  
  //   For real z, the result is real on
  //   the principal branch for z >= -1/e.
  //
  //   The parameter b specifies which branch of the Lambert 
  //   W-Function to compute.  
  //   If z is an array, b may be either an
  //   integer array of the same size as z or an integer scalar.
  //   If z is a scalar, b may be an array of any size.
  //
  // Any optional argument equal to the empty matrix [] is replaced by its default value.
  //
  //   The algorithm uses series approximations as initializations
  //   and Halley's method as developed in Corless, Gonnet, Hare,
  //   Jeffrey, Knuth, "On the Lambert W Function", Advances in
  //   Computational Mathematics, volume 5, 1996, pp. 329-359.
  //
  //  The calling sequence specfun_lambertw ( z , b ) is different from Matlab/lambertw:
  //  the two arguments z and b are switched.
  //  The current choice is kept in Scilab, because this keeps the management of 
  //  the optionnal input argument b consistent.
  //
  // Examples
  // c = specfun_lambertw([0 -exp(-1); %pi 1])
  // e = [
  //     0                     -1.                   
  //     1.07365819479614921    0.56714329040978384  
  // ]
  //
  // // Compute Wk(1), for k=-4,-3,...,4
  // w = specfun_lambertw(1,(-4:4)')
  // e = [
  // -3.162952738804083896D+00-%i*2.342774750375521364D+01;
  // -2.853581755409037690D+00-%i*1.711353553941214756D+01;
  // -2.401585104868003029D+00-%i*1.077629951611507053D+01;
  // -1.533913319793574370D+00-%i*4.375185153061898369D+00;
  // 5.671432904097838401D-01;
  // -1.533913319793574370D+00+%i*4.375185153061898369D+00;
  // -2.401585104868003029D+00+%i*1.077629951611507053D+01;
  // -2.853581755409037690D+00+%i*1.711353553941214756D+01;
  // -3.162952738804083896D+00+%i*2.342774750375521364D+01
  // ];
  //
  // // Produce a plot of |W0(z)|.
  // x = linspace(-6,6,51);
  // y = linspace(-6,6,51);
  // [x,y] = meshgrid(x,y);
  // w = specfun_lambertw(x + %i*y);
  // surf(x,y,abs(w));
  // xlabel("Re z");
  // ylabel("Im z");
  // title("|W_0(z)|");
  //
  // // The following is an Inversion Error Test.
  // // Ideally, lambertw(z,b)*exp(lambertw(z,b)) = z for any complex z
  // // and any integer branch index b, but this is limited by machine
  // // precision.  The inversion error |lambertw(z,b)*exp(lambertw(z,b)) - z|
  // // is small but worth minding.
  //
  // // Experimentation finds that the error is usually on the order of
  // // |z|*1e-16 on the principal branch.  This test computes the inversion
  // // error over the square [-10,10]x[-10,10] in the complex plane, large
  // // enough to characterize the error away from the branch points at
  // // z = 0 and -1/e.
  //
  // // Use NxN points to sample the complex plane
  // N = 81;
  // // Sample in the square [-R,R]x[-R,R]
  // R = 10;
  // x = linspace(-R,R,N);
  // y = linspace(-R,R,N);
  // [xx,yy] = meshgrid(x,y);
  // z = xx + %i*yy;
  // i = 1;
  // j = 1;
  // msubp = 3;
  // nsubp = 3;
  // for b = -4:4
  //   w = specfun_lambertw(z,b);
  //   InvError = abs(w.*exp(w) - z);
  //   mprintf("Largest error for b = %2d:  %.2e\n",b,max(InvError));
  //   p = (i-1)*nsubp + j;
  //   subplot(msubp,nsubp,p);
  //   h = gcf();
  //   cmap = graycolormap (10);
  //   h.color_map = cmap ;
  //   surf(x,y,InvError');
  //   xtitle("Inversion error for W_"+string(b)+"(z)" , "Re z" , "Im z" , "Error" );
  //   j = j + 1;
  //   if ( j > nsubp ) then
  //     j = 1;
  //     i = i + 1;
  //   end
  // end
  //
  // Authors
  // Pascal Getreuer, 2005-2006
  // Modified by Didier Clamond, 2005
  // Michael Baudin, DIGITEO, 2010 (Scilab port)
  //
  // Bibliography
  //   http://www.mathworks.com/matlabcentral/fileexchange/6909
  //   Lambert W Function, Pascal Getreuer, http://www.math.ucla.edu/~getreuer/lambertw.html
  //   Corless, Gonnet, Hare, Jeffrey, Knuth, "On the Lambert W Function", Advances in Computational Mathematics, volume 5, 1996, pp. 329-359.
  
  [lhs, rhs] = argn()
  apifun_checkrhs ( "specfun_lambertw" , rhs , 1:2 )
  apifun_checklhs ( "specfun_lambertw" , lhs , 1 )
  //
  z = varargin(1)
  b = apifun_argindefault ( varargin , 2 , 0 )
  //
  // Check type
  apifun_checktype ( "specfun_lambertw" , z , "z" , 1 , "constant" )
  apifun_checktype ( "specfun_lambertw" , b , "b" , 2 , "constant" )
  //
  // Check size
  // Nothing to do
  //
  // Check content
  apifun_checkflint ( "specfun_lambertw" , b , "b" , 2 )
  //
  if ( z == [] ) then
    w = []
    return
  end
  //
  // Expand z if necessary.
  if ( size(b,"*")<>1 & size(z,"*")==1 ) then
      z = ones(b)*z
  end
  //
  // Expand b if necessary.
  if ( size(z,"*")<>1 & size(b,"*")==1 ) then
      b = ones(z)*b
  end
  //
  // Now check the size
  if ( or(size(z)<>size(b)) ) then
    lclmsg = "%s: Incompatible input arguments #%d and #%d: Same sizes expected.\n"
    error(msprintf(gettext(lclmsg),"specfun_lambertw",1,2))
  end
  //
  // Use asymptotic expansion w = log(z) - log(log(z)) for most z
  tmp = log(z + (z == 0)) + %i*b*6.28318530717958648
  w = tmp - log(tmp + (tmp == 0))
  
  // For b = 0, use a series expansion when close to the branch point
  k = find(b == 0 & abs(z + 0.3678794411714423216) <= 1.5)
  tmp = sqrt(5.43656365691809047*z + 2) - 1 + %i*b*6.28318530717958648
  w(k) = tmp(k)
  
  for k = 1:36
    // Converge with Halley's iterations, about 5 iterations satisfies
    // the tolerance for most z
    c1 = exp(w)
    c2 = w.*c1 - z
    w1 = w + (w ~= -1)
    dw = c2./(c1.*w1 - ((w + 2).*c2./(2*w1)))
    w = w - dw
    
    if ( and(abs(dw) < 0.7e-16*(2+abs(w))) ) then
      break
    end
  end
  
  // Specially handle z = 0
  w(z == 0 & b == 0) = complex(0,0)
  w(z == 0 & b == -1) = complex(-%inf,0)
  w(z == 0 & b <> 0 & b <> -1) = complex(%inf,0)
  // Specially manage Nans
  w(isnan(z)) = complex(%nan,0)
  // Specially manage Inf
  w(z==%inf & b==0) = complex(%inf,0)
  w(z==%inf & b<>0) = complex(%nan,0)
  w(z==-%inf) = complex(%nan,0)
endfunction
