// Copyright (C) 2012 - Michael Baudin
// Copyright (C) 2010 - DIGITEO - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

//
// <-- JVM NOT MANDATORY -->
// <-- ENGLISH IMPOSED -->

[x,txt] = getdata(10);
assert_checkequal ( size(x) , [38,8] );
assert_checkequal ( size(txt) , [48,1] );
assert_checkequal ( typeof(x) , "constant" );
assert_checkequal ( typeof(txt) , "string" );
//
for i = 1 : 23
  [x,txt] = getdata(i);
  assert_checkequal ( typeof(x) , "constant" );
  assert_checkequal ( typeof(txt) , "string" );
end
//
// A short abstract of all datasets
for i = 1 : 23
[x,txt] = getdata(i);
txt_title=txt(1);
mprintf("Dataset #%3d: %-30s (%3d-by-%-3d)\n",i,txt_title,size(x,"r"),size(x,"c"));
end

// A longer abstract of all datasets
for i = 1 : 23
[x,txt] = getdata(i);
txt_title=txt(1);
txt_source=txt(2);
txt_from=txt(3);
txt_dims=txt(4);
txt_descr=txt(5:$);
abstract = strcat(txt_descr(:)," ");
abstract = part(abstract,1:80)+"...";
mprintf("\nDataset #%3d: %-30s (%3d-by-%-3d)\n",i,txt_title,size(x,"r"),size(x,"c"));
mprintf("\t%s\n",txt_source);
mprintf("\t%s\n",txt_from);
mprintf("\t%s\n",txt_dims);
mprintf("\t%s\n",abstract);
end

