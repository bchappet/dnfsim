// Copyright (C) 2012 - 2014 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function p = distfun_mnpdf(x,n,P)
    // Multinomial PDF
    //
    // Calling Sequence
    // p = distfun_mnpdf(x,n,P)
    //
    // Parameters
    // x : a m-by-k matrix of doubles, integer value, positive, the m outcomes
    // n : a 1-by-1 matrix of doubles, integer value, positive, the number of trials
    // P : a m-by-k matrix of doubles, positive, sum to 1, the probability of the k categories
    // p : a m-by-1 matrix of doubles, positive, the probability of each outcome
    //
    // Description
    // Computes the PDF from the multinomial distribution. 
    // On input, we assume that sum(P)==1 (otherwise an error is produced). 
    // In general, we should have sum(x,"c")==n.
    // Rows of x for sum(x,"c")>n are associated with p=0.
	//
	//   Each trial is classified in exactly one of k categories, 
	//   where each category has a different probability. 
	//   Perform n such trials. 
    //
    //   P is the vector of probabilities is of size k. 
    //   We expect that sum(P)=1 (otherwise, an error is generated). 
    //   P(i) is the probability that an event will be classified into category i. 
	//
    // The function definition is:
    //
    // <latex>
    // \begin{eqnarray}
    // f(x,n,P) = \frac{n!}{x_1!\ldots x_k!} p_1^{x_1} \cdots p_k^{x_k}
    // \end{eqnarray}
    // </latex>
    //
    //
    // Examples
    // // Compute the probability of the outcome x = [4 0 6]
    // n = 10
    // P = [0.3 0.4 0.3]
    // x = [4 0 6]
    // p = distfun_mnpdf(x,n,P)
    // p_expected = 0.001240029
    //
    // // Plot trinomial distribution
    // P = [1/2 1/3 1/6]; // Outcome probabilities
    // n = 6; // Sample size
    // x1 = 0:n;
    // x2 = 0:n;
    // [X1,X2] = meshgrid(x1,x2);
    // X3 = n-(X1+X2);
    // X3(X3<0)=0;
    // X=[X1(:),X2(:),X3(:)];
    // colors=["r","g","b","c","m","y","k"]
    // Y = distfun_mnpdf(X,n,repmat(P,(n+1)^2,1));
    // Y=matrix(Y,n+1,n+1)';
    // scf();
    // for i=1:n+1
    //     plot(x1,Y(i,:),colors(i)+"o-")
    // end
    // legend("x1="+string(0:n));
    // xlabel("X2")
    // ylabel("P")
    // title("Trinomial Distribution (X3=n-X1-X2)")
    //
    // Authors
    // Copyright (C) 2012 - 2014 - Michael Baudin
    //
    // Bibliography
    // Wikipedia, Multinomial distribution function, http://en.wikipedia.org/wiki/Multinomial_distribution

    [lhs,rhs] = argn()
    apifun_checkrhs("distfun_mnpdf",rhs,3)
    apifun_checklhs("distfun_mnpdf",lhs,0:1)
    //
    // Check type
    apifun_checktype("distfun_mnpdf",x,"x",1,"constant")
    apifun_checktype("distfun_mnpdf",n,"n",2,"constant")
    apifun_checktype("distfun_mnpdf",P,"P",3,"constant")
    // Check size
    apifun_checkscalar("distfun_mnpdf",n,"n",2)
    m=size(x,"r")
    k=size(x,"c")
    apifun_checkdims("distfun_mnpdf",P,"P",3,[m,k])
    // Check content
    apifun_checkflint("distfun_mnpdf",x,"x",1)
    apifun_checkrange("distfun_mnpdf",x,"x",1,0,n)
    apifun_checkflint("distfun_mnpdf",n,"n",2)
    apifun_checkgreq("distfun_mnpdf",n,"n",2,1)
    apifun_checkrange("distfun_mnpdf",P,"P",3,0,1)
    apifun_checkrange("distfun_mnpdf",sum(P,"c"),"sum(P,""c"")",3,1-10*%eps,1)
    //
    // Log(Multinomial Coefficient)
    logmncoef=specfun_factoriallog(n)- sum(specfun_factoriallog(x),"c")
    logp = logmncoef + sum(x.*log(P),"c")
    p = exp(logp)
    // For entries such that sum(x,"c")<>n, p=0
    p(sum(x,"c")<>n)=0
endfunction

