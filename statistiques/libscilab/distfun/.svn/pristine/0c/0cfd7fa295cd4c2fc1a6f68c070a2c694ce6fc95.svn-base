// Copyright (C) 2012 - Prateek Papriwal
// Copyright (C) 2012 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt
//
function distfun_hygetable(x,M,k,N)
    //  Print a contingency table.
    //
    // Calling Sequence
    //   distfun_hygetable(x,M,k,N)
    //
    // Parameters
    //   x : a 1x1 matrix of doubles, the number of successful draws in the experiment. x belongs to the set [0,min(k,N)]
    //   M : a 1x1 matrix of doubles, the total size of the population. M belongs to the set {0,1,2,3........}
    //   k : a 1x1 matrix of doubles, the number of success states in the population. k belongs to the set {0,1,2,3,.......M-1,M}
    //   N : a 1x1 matrix of doubles, the total number of draws in the experiment. N belongs to the set {0,1,2,3.......M-1,M}
    //
    // Description
    //   Prints a contingency table of the inputs, for the 
	//   corresponding parameters.
	//
    // <screen>
    // _________   Drawn    Not Drawn    Total
    // Successes   x        k-x          k 
    // Failures    N-x      M+x-N-k      M-k
    // Total       N        M-N          M
    // </screen>
    //
	// If the number of successes x in the sample is too small, 
	// then a warning is printed. 
	// In this case, the probability associated with x is zero. 
	//
    // Examples
    // distfun_hygetable(3,9,5,6)
	//
	// // A warning is printed:
    // // distfun_hygetable(0,9,5,6)
    //
    // Bibliography
    // http://en.wikipedia.org/wiki/Hypergeometric_distribution
    //
    // Authors
    // Copyright (C) 2012 - Michael Baudin

    [lhs,rhs] = argn()
    apifun_checkrhs("distfun_hygetable",rhs,4)
    apifun_checklhs("distfun_hygetable",lhs,0:1)
    //
    // Check type
    apifun_checktype("distfun_hygetable",x,"x",1,"constant")
    apifun_checktype("distfun_hygetable",M,"M",2,"constant")
    apifun_checktype("distfun_hygetable",k,"k",3,"constant")
    apifun_checktype("distfun_hygetable",N,"N",4,"constant")
    //
    // Check size
    apifun_checkscalar("distfun_hygetable",x,"x",1)
    apifun_checkscalar("distfun_hygetable",M,"M",2)
    apifun_checkscalar("distfun_hygetable",k,"k",3)
    apifun_checkscalar("distfun_hygetable",N,"N",4)
    //
    // Check content
    apifun_checkgreq("distfun_hygetable",x,"x",1,0)
    apifun_checkgreq("distfun_hygetable",M,"M",2,0)
    apifun_checkgreq("distfun_hygetable",k,"k",3,0)
    apifun_checkgreq("distfun_hygetable",N,"N",4,0)
    //
    myloweq("distfun_hygetable",x,"x",1,N) // x<=N
    myloweq("distfun_hygetable",x,"x",1,k) // x<=k
    myloweq("distfun_hygetable",k,"k",2,M) // k<=M
    myloweq("distfun_hygetable",N,"N",4,M) // N<=M
    //
	// Compute the format which is necessary to print the table.
	maxnumberwidth = max(..
	length(string(x)),..
	length(string(M)),..
	length(string(k)),..
	length(string(N)),..
	length(string(M+x-N-k)),..
	length(string(k-x)),..
	length(string(M-k)),..
	length(string(N-x)),..
	length(string(M-N))..
	)
	maxlabelwidth = max(length(["Drawn","Not Drawn","Total","Success","Failure"]))
	maxcolumnwidth = 0+max(maxnumberwidth,maxlabelwidth)
	// Example:
	// maxcolumnwidth: 15
	// onecolumn: "%-15s"
	onecolumn = msprintf("%%-%ds",maxcolumnwidth)
	fmt = msprintf("%s %s %s %s\n",onecolumn,onecolumn,onecolumn,onecolumn)
	//
	mprintf(fmt+"\n",..
		"","Drawn","Not Drawn","Total")
	mprintf(fmt+"\n",..
		"Success",string(x),string(k-x),string(k))
	mprintf(fmt+"\n",..
		"Failure",string(N-x),string(M+x-N-k),string(M-k))
	mprintf(fmt+"\n",..
		"Total",string(N),string(M-N),string(M))
    if (M+x-N-k<0) then
	    warning(msprintf(gettext("%s: The number of successes x=%s in the sample is too low.\n"),..
		"distfun_hygetable",string(x)))
	end
endfunction
function myloweq( funname , var , varname , ivar , thr )
    // Workaround for bug http://forge.scilab.org/index.php/p/apifun/issues/867/
    // Caution:
    // This function assumes that var and thr are matrices with
    // same size.
    // Expand the arguments before calling it.
    if ( or ( var > thr ) ) then
        k = find ( var > thr ,1)
        errmsg = msprintf(gettext("%s: Wrong input argument %s at input #%d. Entry %s(%d) is equal to %s but should be lower than %s."),funname,varname,ivar,varname,k,string(var(k)),string(thr(k)));
        error(errmsg);
    end
endfunction
