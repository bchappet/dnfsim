// Copyright (C) 2010 - DIGITEO - Michael Baudin
// Copyright (C) 1993 - 1995 - Anders Holtsberg
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function [x,txt]=getdata(varargin)
  // Returns a dataset.
  //
  // Calling Sequence
  //   x = getdata()
  //   [x,txt] = getdata()
  //   x = getdata(i)
  //   [x,txt] = getdata(i)
  //
  // Parameters
  //   i : a 1-by-1 matrix of floating point integer, the dataset index, in the range 1,2,...,23
  //   x : a m-by-n matrix of doubles, the data
  //   txt : a m-by-1 matrix of strings, the header of the dataset.
  //
  // Description
  // Returns famous datasets.
  // Each dataset is made of a header presenting the content of the dataset 
  // (to be stored into txt) and the data (to be stored into x).
  //
  // Without input argument, opens an interactive dialog asking the user
  // to choose one dataset.
  // If the user cancels, returns the empty matrix into x and and empty string into txt.
  //
  // With one input argument i, returns the dataset #i.
  //
  // With one output argument x, returns the data into x and opens an interactive 
  // dialog displaying the header.
  //
  // With two output arguments x and txt, returns the data into x and the header into txt.
  //
  // The txt header is structured as follows:
  // <itemizedlist>
  //   <listitem>
  //     txt(1) is the title,
  //   </listitem>
  //   <listitem>
  //     txt(2) is a reference using the dataset (a paper, a book, a technical report, ...),
  //   </listitem>
  //   <listitem>
  //     txt(3) is the original source of the dataset,
  //   </listitem>
  //   <listitem>
  //     txt(4) is the dimension of the dataset (number of observations and variables in the dataset),
  //   </listitem>
  //   <listitem>
  //     txt(5:$) is the description of the dataset.
  //   </listitem>
  // </itemizedlist>
  //
  // The following is the list of datasets available:
  //
  //  <programlisting>
  //  1  Phosphorus 
  //  2  Scottish Hill Race 
  //  3  Salary Survey 
  //  4  Health Club 
  //  5  Brain and Body Weight 
  //  6  Cement 
  //  7  Colon Cancer 
  //  8  Growth 
  //  9  Consumption Function
  //  10 Cost-of-Living 
  //  11 Demographic 
  //  12 Cable 
  //  13 Service call
  //  14 Phone call
  //  15 Turnover 
  //  16 Unemployment 
  //  17 Quality Control
  //  18 Graphics cards 
  //  19 Data Processing System development
  //  20 Paper 
  //  21 Bulb 
  //  22 Memory Chip 
  //  23 French firm 
  //  </programlisting>
  //
  // Examples
  // // A regular call
  // [x,txt] = getdata(10)
  // // Displays only the header
  // [x,txt] = getdata(10); txt
  // // Select the dataset interactively.
  // [x,txt] = getdata()
  // // Display the header interactively.
  // x = getdata(10)
  // // Select the dataset interactively and 
  // // display the header interactively.
  // x = getdata()
  //
  // // A short abstract of all datasets
  // for i = 1 : 23
  //   [x,txt] = getdata(i);
  //   txt_title=txt(1);
  //   mprintf("Dataset #%3d: %-30s (%3d-by-%-3d)\n",i,txt_title,size(x,"r"),size(x,"c"));
  // end
  //
  // // A longer abstract of all datasets
  // for i = 1 : 23
  //   [x,txt] = getdata(i);
  //   txt_title=txt(1);
  //   txt_source=txt(2);
  //   txt_from=txt(3);
  //   txt_dims=txt(4);
  //   txt_descr=txt(5:$);
  //   abstract = strcat(txt_descr(:)," ");
  //   abstract = part(abstract,1:80)+"...";
  //   mprintf("\nDataset #%3d: %-30s (%3d-by-%-3d)\n",i,txt_title,size(x,"r"),size(x,"c"));
  //   mprintf("\t%s\n",txt_source);
  //   mprintf("\t%s\n",txt_from);
  //   mprintf("\t%s\n",txt_dims);
  //   mprintf("\t%s\n",abstract);
  // end
  // 
  // Authors
  // Copyright (C) 2010 - DIGITEO - Michael Baudin
  // Copyright (C) 1993 - 1995 - Anders Holtsberg
  //


  [lhs, rhs] = argn()
  apifun_checkrhs ( "getdata" , rhs , 0:1 )
  apifun_checklhs ( "getdata" , lhs , 1:2 )
  //
  if ( rhs==0 ) then
    dd=[
        "1  Phosphorus Data";
	"2  Scottish Hill Race Data"; 
	"3  Salary Survey Data"; 
	"4  Health Club Data"; 
	"5  Brain and Body Weight Data"; 
	"6  Cement Data"; 
	"7  Colon Cancer Data"; 
	"8  Growth Data"; 
	"9  Consumption Function"; 
	"10 Cost-of-Living Data"; 
	"11 Demographic Data";
	"12 Cable Data";
	"13 Service call";
	"14 Phone call";
	"15 Turnover Data";
	"16 Unemployment Data";
	"17 Quality Control";
	"18 Graphics cards ";
	"19 Data Processing System development";
	"20 Paper Data";
	"21 Bulb Data";
	"22 Memory Chip Data";
	"23 French firm Data"
        ];
    i=x_choose(dd,"DATASETS Famous datasets");
    if (i == 0) then
      x = []
      txt = ""
      return
    end
  else
    i = varargin(1)
  end 
  //
  apifun_checktype ( "getdata" , i , "i" , 1 , "constant" )
  apifun_checkrange ( "getdata" , i , "i" , 1 , 1 , 23 )
  // TODO : check that i is a floating point integer
  //
  execstr("[x,txt]=datas"+string(i)+"()")
  if ( lhs==1 ) then 
    messagebox(txt)
  end
endfunction
