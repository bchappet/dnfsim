% Copyright (C) 2009 - 2010 - DIGITEO - Michael Baudin
%
% This file must be used under the terms of the CeCILL.
% This source file is licensed as described in the file COPYING, which
% you should have received as part of this distribution.  The terms
% are also available at
% http:%www.cecill.info/licences/Licence_CeCILL_V2-en.txt

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%/
% Accuracy test
%

format long

% Table of inputs
% [x shape scale]
table = [
 0.1 , 0.1 , 1 
 0.2 , 0.1 , 1 
 0.2 , 0.2 , 1 
 0.3 , 0.2 , 1 
 0.3 , 0.3 , 1 
 0.4 , 0.3 , 1 
 0.4 , 0.4 , 1 
 0.5 , 0.4 , 1 
 0.5 , 0.5 , 1 
 0.6 , 0.5 , 1 
];

ntests = size(table,1);
for i = 1 : ntests
  x = table(i,1);
  shape = table(i,2);
  scale = table(i,3);
  p = gampdf(x,shape,scale);
  fprintf('%.17e , %.17e , %.17e , %.17e\n',x,shape,scale,p);
end

