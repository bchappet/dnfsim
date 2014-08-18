// Copyright (C) 2012 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt
//

function distfun_verboseset()
    // Set verbose mode.
    //
    // Calling Sequence
    //   distfun_verboseset(verbosemode)
    //
    // Parameters
    //   verbosemode : a 1-by-1 matrix of booleans, the verbose mode.
    //
    // Description
    //   Set the verbose mode.
	//   This gives informations on the number of iterations in the 
	//   inverse CDF functions. 
	//   Default mode is quiet (verbosemode=%f).
	//
	// The list of functions which are sensitive to this option is the 
	// following :
	// <programlisting>
	//  cdflib_betainv
    //  cdflib_binoinv
    //  cdflib_chi2inv
    //  cdflib_cdfchn
    //  cdflib_finv
    //  cdflib_cdffnc
    //  cdflib_cdfnbn
    //  cdflib_cdfnbn
    //  cdflib_poissinv
    //  cdflib_tinv
    //  cdflib_hygeinvcdf
	// </programlisting>
    //
    // Examples
    // distfun_verboseset(%t)
    // distfun_chi2inv(0.1,50)
    // distfun_verboseset(%f)
    //
    // Authors
    // Copyright (C) 2012 - Michael Baudin

endfunction
