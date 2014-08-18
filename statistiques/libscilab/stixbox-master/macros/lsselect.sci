// Copyright (C) 2010 - DIGITEO - Michael Baudin
// Copyright (C) 1993 - 1995 - Anders Holtsberg
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function [Q,I,B,BB]=lsselect(y,x,crit,how,pmax,level)
Q=[];I=[];B=[];BB=[];
[nargout,nargin] = argn(0)
//LSSELECT Select a predictor subset for regression
// 
//     	  [Q, I, B, BB] = lsselect(y,x,crit,how,pmax,level)
// 
//	  Selects a good subset of regressors in a multiple linear
//	  regression model. The criterion is one of the following
//	  ones, determined by the third argument, crit.
// 
//	    'HT'   Hypothesis Test (default level = 0.05)
//	    'AIC'  Akaike's Information Criterion
//	    'BIC'  Bayesian Information Criterion
//	    'CMV'  Cross Model Validation (inner criterion RSS)
// 
//	  The forth argument, how, choses between
// 
//	    'AS'   All Subsets
//	    'FI'   Forward Inclusion
//	    'BE'   Backward Elimination
// 
//	  The fifth argument, pmax, limits the number of included
//	  parameters. The returned Q is the criterion as a function of
//	  the number of parameters; it might be interpreted as an
//	  estimate of the prediction standard deviation. For the method
//	  'HT' the reported Q is instead the successive p-values for
//	  inclusion or elimination.
// 
//	  The last column of the prediction matrix x must be an intercept
//	  column, ie all elements are ones. This column is never excluded
//	  in the search for a good model. If it is not present it is added.
//	  The output I contains the index numbers of the included columns.
//	  For the method 'HT' the optional input argument """"level"""" is the
//	  p-value reference used for inclusion or deletion. Output B is
//	  the vector of coefficients, ie the suggested model is
//	  Y = X*B. Column p of BB is the best B of parameter size p.
// 
//	  This function is not highly optimized for speed but rather for
//	  flexibility. It would be faster if 'all subsets' were in a
//	  separate routine and 'forward' and 'backward' were in another
//	  routine, especially for CMV.
// 
//         See also LSFIT and LINREG.
 
       

 
n = mtlb_length(y);
nc = size(x,2);
 
if nargin<5 then
  pmax = nc;
elseif pmax==[] then
  pmax = nc;
end
if nargin<4 then
  how = 'FI';
  fprintf('   Default is forward selection\n');
end
if nargin<3 then
  crit = 'CMV';
  fprintf('   Default criterion is CMV\n');
end
if how=='BE' then
  pmax = nc;
end
if (nargin<6)&(crit=='HT') then
  level = 0.05;
  fprintf('   Default level is 0.05\n');
end
 
if or(x(:,nc)~=1) then
  fprintf('   An intercept column added');
  x = [x,ones(n,1)];
  nc = nc+1;
  pmax = pmax+1;
end
if nc<2 then
  disp('only one model');
  return
end
 
if ~(crit=='HT'|crit=='AIC'|crit=='BIC'|crit=='CMV') then
  error('Third argument error');
end
if ~(how=='BE'|how=='FI'|how=='AS') then
  error('Forth argument error');
end
 
Qsml = %nan*ones(pmax,1);
 
XX = x'*x;
XY = x'*y;
YY = y'*y;
 
// === If all subsets then set up an all-subsets-indicator-matrix ====
 
if how=='AS' then
  C = [];
  for i = 1:nc-1
    d = max(1,size(C,1));
    C = [zeros(d,1),C;ones(d,1),C];
    H = C*ones(size(C,2),1);
    J = find(H<pmax)';
    C = C(J,:);
  end
  H = H(J);
  [S,I] = gsort(H)
  S = S($:-1:1)
  I = I($:-1:1)
  C = C(I,:);
  C = [C,ones(size(C,1),1)];
  AllSubsets = C;
  AllSubsetsH = mtlb_sum(C')';
end
 
// === This is for CMV ===============================================
 
if crit=='CMV' then
  dataloopend = n+1;
  Qcmv = zeros(pmax,1);
  XXs = XX;
  XYs = XY;
  YYs = YY;
else
  dataloopend = 1;
end
 
for idata = 1:dataloopend
   
  if crit=='CMV' then
    fprintf('\n %3.0f:',idata*bool2s(idata~=dataloopend));
    if idata==dataloopend then
      XX = XXs;
      XY = XYs;
      YY = YYs;
    else
      xi = x(idata,:);
      yi = y(idata);
      XX = XXs-xi'*xi;
      XY = XYs-xi'*yi;
      YY = YYs-(yi^2);
    end
  end
   
  // === Now we begin to loop over model sizes =========================
   
  if how=='BE' then
    p = nc;
    loopto = 1;
    C = ones(1,nc);
  else
    p = 1;
    loopto = pmax;
    C = [zeros(1,nc-1),1];
  end
  BB = zeros(nc,pmax);
   
  fprintf('  ');
   
  while 1 then
     
    // === The whole loop over the models of size p  ======================
     
    fprintf('%2.0f ',p);
    qmin = 1.000000000000E+99;
    for k = 1:size(C,1)
      //  mtlb_find(C(k,:)) may be replaced by
      //  find(C(k,:))' if C(k,:) is not a row vector
      Jk = mtlb_find(C(k,:));
      // mtlb_e(XY,Jk) may be replaced by XY(Jk) if XY is a vector.
      // mtlb_e(XY,Jk) may be replaced by XY(Jk) if XY is a vector.
      q = YY-mtlb_e(XY,Jk)'*XX(Jk,Jk)\mtlb_e(XY,Jk);
      if q<qmin then
        qmin = q;
        Cbest = C(k,:);
      end
    end
    Qsml(p) = qmin;
    //  mtlb_find(Cbest) may be replaced by
    //  find(Cbest)' if Cbest is not a row vector
    I = mtlb_find(Cbest);
    // mtlb_e(XY,I) may be replaced by XY(I) if XY is a vector.
    BB(I,p) = XX(I,I)\mtlb_e(XY,I);
     
    // === And a piece for CMV only ======================================
     
    if (crit=='CMV')&(idata<(n+1)) then
      //  mtlb_find(Cbest) may be replaced by
      //  find(Cbest)' if Cbest is not a row vector
      Jk = mtlb_find(Cbest);
      // mtlb_e(xi,Jk) may be replaced by xi(Jk) if xi is a vector.
      // mtlb_e(XY,Jk) may be replaced by XY(Jk) if XY is a vector.
      q = yi-mtlb_e(xi,Jk)*XX(Jk,Jk)\mtlb_e(XY,Jk);
      Qcmv(p) = Qcmv(p)+q^2;
    end
     
    // === Next parameter size ===========================================
     
    if p==loopto then
      break
       
    end
     
    if how=='FI' then
      p = p+1;
      C = [];
      for i = 1:nc-1
        if Cbest(i)==0 then
          Cnew = Cbest;
          Cnew(i) = 1;
          C = [C;Cnew];
        end
      end
       
    elseif how=='BE' then
      p = p-1;
      C = [];
      for i = 1:nc-1
        if Cbest(i)==1 then
          Cnew = Cbest;
          Cnew(i) = 0;
          C = [C;Cnew];
        end
      end
       
    elseif how=='AS' then
      p = p+1;
      C = AllSubsets(find(AllSubsetsH==p)',:);
    end
     
  end
end
 
// === Finished at last ===============================================
 
if crit=='CMV' then
  Q = sqrt(Qcmv/n);
end
if crit=='AIC' then
  Q = sqrt(Qsml/n) .* exp(((1:pmax)')/n);
end
if crit=='BIC' then
  Q = sqrt(Qsml/n) .* exp(((1:pmax)')/n*log(n)/2);
end
 
if crit=='HT' then
  Qsml = sqrt(Qsml/n);
  Fvals = (Qsml(1:pmax-1).^2-Qsml(2:pmax).^2) ./ (Qsml(2:pmax).^2 ./ ((n-(2:pmax))'));
  Q = 1-pf(Fvals,1,(n-(2:pmax))');
  //  mtlb_find(Q>level) may be replaced by
  //  find(Q>level)' if Q>level is not a row vector
  i = mtlb_find(Q>level);
  if how=='BE' then
    i = [1;Q<level];
    i = find(i)';
    i = i(max(size(i)));
  else
    i = [Q>level;1];
    i = find(i)';
    i = i(1);
  end
else
  [S,i] = gsort(Q)
  S = S($:-1:1)
  i = i($:-1:1)
  i = i(1);
end
 
B = BB(:,%i);
//  mtlb_find(B) may be replaced by
//  find(B)' if B is not a row vector
I = mtlb_find(B);
 
fprintf('\n');
endfunction
