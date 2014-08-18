x=distfun_nbinrnd(3,0.5,1000000,1);
distfun_inthisto(x);


// http://en.wikipedia.org/wiki/Negative_binomial_distribution
R=5;
P=R/(10 + R);
scf();
x=distfun_nbinrnd(R,P,10000,1);
distfun_inthisto(x);
x=0:30;
y = distfun_nbinpdf(x,R,P);
plot(x,y,"ro");
g=gca();
g.children(2).children.background=-2;
g.data_bounds(:,1)=[0;30];
g.auto_ticks=["on","on","on"];
legend(["Data","PDF"]);
title("Negative Binomial PDF");
xlabel("x")
ylabel("P(X=x)")

// CDF
R=5;
P=R/(10 + R);
x=0:30;
y = distfun_nbincdf(x,R,P);
scf();
plot(x,y,"ro")
N=10000;
x=distfun_nbinrnd(R,P,N,1);
x=gsort(x,"g","i");
y=(1:N)/(N+1);
plot(x,y,"b-")
legend(["Data","PDF"]);
title("Negative Binomial CDF");
xlabel("x")
ylabel("P(X<=x)")

// Complementary CDF
R=5;
P=R/(10 + R);
x=0:30;
y = distfun_nbincdf(x,R,P,%f);
scf();
plot(x,y,"ro")
N=10000;
x=distfun_nbinrnd(R,P,N,1);
x=gsort(x,"g","d");
y=(1:N)/(N+1);
plot(x,y,"b-")
legend(["Data","PDF"]);
title("Negative Binomial CDF");
xlabel("x")
ylabel("P(X>x)")

function [M,V] = distfun_nbinstat(R,P)
    [lhs,rhs] = argn()
    apifun_checkrhs("distfun_nbinstat",rhs,2)
    apifun_checklhs("distfun_nbinstat",lhs,1:2)
    Q=1-P
    M=R.*Q./P
    V=R.*Q./P.^2
endfunction

R=5;
P=1/3;
[M,V] = distfun_nbinstat(R,P)
N=100000;
x=distfun_nbinrnd(R,P,N,1);
mprintf("Mean(x)=%f (exact=%f)\n",mean(x),M)
mprintf("Mean(x)=%f (exact=%f)\n",variance(x),V)


R=5;
P=1/3;
p=distfun_nbincdf(10,R,P)
x = distfun_nbininv(p,R,P,%t)
disp(x)
