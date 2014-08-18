// Copyright (C) 2010 - DIGITEO - Michael Baudin
// Copyright (C) 1993 - 1995 - Anders Holtsberg
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function [x,txt]=datas8()
  txt=['Growth Data';
'Source:     Rawlings (1988), page 325';
'Taken From: Rawlings (1988), Applied Regression Analysis, Wadsworth, p. 325';
'Dimension:  24 observations on 2 variables';
'Description: The growth data taken on four different independent';
'  experimental units at each different ages';
'Column    Description';
'   1     Age in weeks';
'   2     Dry weight in grams'];


 
x = [1,8
  1,10
  1,12
  1,15
  2,35
  2,38
  2,42
  2,48
  3,57
  3,63
  3,68
  3,74
  5,68
  5,76
  5,86
  5,90
  7,76
  7,95
  7,103
  7,105
  9,85
  9,98
  9,105
  9,110];
endfunction
