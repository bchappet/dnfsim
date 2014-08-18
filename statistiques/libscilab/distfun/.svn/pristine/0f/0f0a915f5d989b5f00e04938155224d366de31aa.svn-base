// Copyright (C) 2012 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

// Prints the XML code for the docbook page :
// 1distfun_tutorial.xml, 
// in the section "Chi-square distribution".

alpha = [
0.995
0.99
0.975
0.95
0.05 
0.025 
0.01 
0.005
];
format("v",8)
mprintf("<tr>\n")
mprintf("<td>k</td>\n")
for j=1:8
    mprintf("<td>alpha=%s</td>\n",string(alpha(j)))
end
mprintf("</tr>\n")
for k=1:30
    mprintf("<tr>\n")
    mprintf("<td>k=%s</td>\n",string(k))
	for j=1:8
        X=distfun_chi2inv(alpha(j),k,%f);
        mprintf("<td>%s</td>\n",string(X))
    end
    mprintf("</tr>\n")	
end
// Put the default format back
format("v",10)
