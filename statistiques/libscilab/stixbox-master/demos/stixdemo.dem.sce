// Copyright (C) 2013 - Michael Baudin
// Copyright (C) 2010 - DIGITEO - Michael Baudin
// Copyright (C) 1993 - 1995 - Anders Holtsberg
// Copyright (C) 1998 - 2000 - Mathematique Universite de Paris-Sud - Jean Coursol
// Copyright (C) INRIA
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

//STIXDEMO Demonstrate various stixbox routines.
 
disp("When paused with -->halt() enter return key to continue")
disp("When halted without --> mouse click on the plot")

scf();
clf();

disp("Let us start with some data, for example a small correlated")
disp("two dimensional sample with some weird underlying distribution.")
 
n = 20;
Y=distfun_chi2rnd(3,n,1);
U=distfun_unifrnd(0,1,n,1);
X = Y+3*U;

halt()

disp("========================================================")
disp("      THE JACKKNIFE AND THE BOOTSTRAP ESTIMATE")
disp("                OF STANDARD DEVIATION")
disp("========================================================")
// 
disp("Let us compute the mean of X and its estimated standard")
disp("deviation.")

disp("mean(X)")
disp(mean(X))
disp("st_deviation(X)/sqrt(n)")
disp(st_deviation(X)/sqrt(n))
disp("stdjack(X,""mean"")")
disp(stdjack(X,"mean"))
disp("stdboot(X,""mean"")")
disp(stdboot(X,"mean"))
halt()
disp("The same thing for the median.")
 
disp("median(X)")
disp(median(X))
disp("stdjack(X,""median"")")
disp(stdjack(X,"median"))
disp("stdboot(X,""median"")")
disp(stdboot(X,"median"))
halt()
disp("A spectacular example is to give confidence intervals for the")
disp("quantile of a distribution. There are at least 3 ways to define")
disp("an empirical quantile estimator. Look at the plot to see them")
disp("applied to X.")
 
%v = X;
if min(size(%v))==1 then 
  %v=gsort(%v);
else  
 %v=gsort(%v,"r"); 
end;
XX = %v($:-1:1,:);
%v$2 = ((0:n)')/n;
%v$2 = [XX(1);XX];
%v$2 = %v$2';
%v$2 = %v$2';
xmin=floor(min(%v$2));
xmax=ceil(max(%v$2));
clf();
plot2d2('gnn',%v$2,%v$2/xmax,style=3,rect=[xmin,xmin,xmax,1]);
plot2d(XX,(((1:n)')-0.5)/n,style=9)
plot2d(XX,((1:n)')/(n+1),style=5)
ax=gca();ax.grid=[1 1];

halt()
disp("Let us use the method indicated by the green line and compute")
disp("some quantiles for X along with its standard deviation. The")
disp("bootstrap is used for computing a standard deviation estimate")
disp("which we use for plotting a 90 percent confidence interval through")
disp("a normal approximation.")

clf()
p = (0.1:0.1:0.9)';
qx = quantile(X,p,1);
sqx = stdboot(X,"quantile",200,p);
plot2d([p,p,p],[qx-1.6399999999999999*sqx,qx,qx+1.6399999999999999*sqx]);
ax=gca();ax.grid=[1 1];
halt()
disp("However, there are fancier methods than normal approximations ...")
// 
disp("Let us redo the quantile example with full fledged bootstrap")
disp("confidence intervals based on 1000 simulations.")

Imb = ciboot(X,"quantile",[],0.9,2000,p);
clf();
plot2d([p,p,p],Imb);
ax=gca();ax.grid=[1 1];

disp("Great fun, isn''t it?")

disp("A bootstrap confidence interval is based on the bootstrap")
disp("distribution for some quantity. One might have a look at the")
disp("distribution for it. The confidence interval for the standard")
disp("deviation of X will serve as an example.")
 
[Imb,T] = ciboot(X,"st_deviation",[],0.9,1000);
Imb = Imb

//
// Load this script into the editor
//
filename = "stixdemo.dem.sce";
dname = get_absolute_file_path(filename);
editor ( fullfile(dname,filename) );

