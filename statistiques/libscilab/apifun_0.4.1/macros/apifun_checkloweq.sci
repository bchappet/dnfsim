// Copyright (C) 2012 - Michael Baudin
// Copyright (C) 2010 - DIGITEO - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function errmsg=apifun_checkloweq(funname,var,varname,ivar,thr )
  // Checks that the value is lower or equal than a threshold.
  //
  // Calling Sequence
  //   errmsg=apifun_checkloweq(funname,var,varname,ivar,thr)
  //
  // Parameters
  //   funname : a 1-by-1 matrix of strings, the name of the calling function.
  //   var : a matrix of values, the variable
  //   varname : a 1-by-1 matrix of string, the name of the variable
  //   ivar : a 1-by-1 matrix of floating point integers, the index of the input argument in the calling sequence
  //   thr : a matrix of values, the maximum value for the variable #ivar
  //   errmsg : a 1-by-1 matrix of strings, the error message. If there was no error, the error message is the empty matrix.
  //
  // Description
  // This function is designed to be used to design functions where an 
  // input argument is expected to be greater or equal to a threshold.
  // The error is generated if the condition or ( var > thr ) is true.
  // This function can be used for whatever variable type for which 
  // the comparison ">" can be evaluated.
  //
  // The arguments var and thr can be either 1-by-1 matrices (i.e. scalar) or 
  // m-by-n matrices of doubles, but not all combinations are available. 
  // The available combinations are:
  //   <itemizedlist>
  //   <listitem>var scalar and thr scalar,</listitem>
  //   <listitem>var matrix and thr scalar,</listitem>
  //   <listitem>var matrix and thr matrix.</listitem>
  //   </itemizedlist>
  //
  // The case where var is a scalar and thr is a matrix 
  // is not available. 
  // In this situation, please use the <literal>min</literal> function 
  // and the calling sequence:
  // 
  // <screen>
  // errmsg=apifun_checkloweq(funname,var,varname,ivar,min(thr))
  // </screen>
  //
  // Examples
  // // The function takes an argument x such that x<=1.
  // function y = myfunction ( x )
  //   apifun_checkloweq ( "myfunction",x,"x",1,1 )
  //   y = sqrt(1-x)
  // endfunction
  // // Calling sequences which work
  // myfunction ( [-1.5,-2.5,-3.5] )
  // // Calling sequences which generate an error
  // // myfunction ( [1.5,1] )
  // // myfunction ( [1,-1,2.5,0] )
  //
  // Authors
  // Copyright (C) 2012 - Michael Baudin
  // Copyright (C) 2010 - DIGITEO - Michael Baudin
  //

  [lhsnb,rhsnb]=argn()
  if ( rhsnb <> 5 ) then
    msgfmt = "%s: Unexpected number of input arguments : %d provided while %d are expected."
    errmsg = msprintf(gettext(msgfmt), "apifun_checkloweq", rhsnb,5)
    error(errmsg)
  end
  //
  // Checking type of input arguments
  if ( typeof(funname) <> "string" ) then
    errmsg = msprintf(gettext("%s: Expected a string for input argument %s at input #%d, but got %s instead."), "apifun_checkloweq", "funname",1,typeof(funname) );
    error(errmsg)
  end
  if ( typeof(varname) <> "string" ) then
    errmsg = msprintf(gettext("%s: Expected a constant for input argument %s at input #%d, but got %s instead."), "apifun_checkloweq", "varname",3,typeof(varname) );
    error(errmsg)
  end
  if ( typeof(ivar) <> "constant" ) then
    errmsg = msprintf(gettext("%s: Expected a constant for input argument %s at input #%d, but got %s instead."), "apifun_checkloweq", "ivar",4,typeof(ivar) );
    error(errmsg)
  end
  //
  // Checking size of input arguments
  if ( or(size(funname) <> [1 1]) ) then
    strcomp = strcat(string(size(funname))," ")
    errmsg = msprintf(gettext("%s: Expected a scalar for input argument %s at input #%d, but got [%s] instead."), "apifun_checkloweq", "funname",1,strcomp );
    error(errmsg)
  end
  if ( or(size(varname) <> [1 1]) ) then
    strcomp = strcat(string(size(varname))," ")
    errmsg = msprintf(gettext("%s: Expected a scalar for input argument %s at input #%d, but got [%s] instead."), "apifun_checkloweq", "varname",3,varname );
    error(errmsg)
  end
  if ( or(size(ivar) <> [1 1]) ) then
    strcomp = strcat(string(size(ivar))," ")
    errmsg = msprintf(gettext("%s: Expected a scalar for input argument %s at input #%d, but got [%s] instead."), "apifun_checkloweq", "ivar",4,strcomp );
    error(errmsg)
  end
  if ( or(size(thr) <> 1) ) then
  if ( or(size(var) <> size(thr)) ) then
    errmsg = msprintf(gettext("%s: Incompatible input arguments #%d and #%d: Same sizes expected.\n"), "apifun_checkloweq", 3,5 );
    error(errmsg)
  end
  end
  //
  errmsg = []
  if ( isempty(var) & isempty(thr ) ) then
      return
  end
  if ( or ( var > thr ) ) then
    k = find ( var > thr )
    k = k(1)
    if ( size(thr,"*")==1) then
    errmsg = msprintf(gettext("%s: Expected that all entries of input argument %s at input #%d are lower or equal than %s, but entry #%d is equal to %s."),funname,varname,ivar,string(thr),k,string(var(k)));
    else
    errmsg = msprintf(gettext("%s: Expected that entry #%d of input argument %s at input #%d is lower or equal than %s, but entry #%d is equal to %s."),funname,k,varname,ivar,string(thr(k)),k,string(var(k)));
    end
    error(errmsg);
  end
endfunction

