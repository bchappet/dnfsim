// Copyright (C) 2010 - DIGITEO - Michael Baudin
// Copyright (C) 1993 - 1995 - Anders Holtsberg
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function [x,txt]=datas5()
  txt=['Brain and Body Weight';
'Source: Jerison, H. J. (1973), Evolution of the Brain and Intelligence, New York: Academic Press.'
'Taken From: Rousseeuw, P. J. and Leroy, A. M. (1987), Robust Regression and Outlier Detection, New York: John Wiley & Sons, p. 57.';
'Dimension: 28 observations on 2 variables';
'Description: The sample was taken from a larger data set. It is to be';
'  determined whether a larger brain is required to govern a';
'  heavier body. The observations represent the following animals:';
' 1 Mountain beaver    2 Cow            3 Gray wolf          4 Goat';
' 5 Guinea pig         6 Diplodocus     7 Asian elephant     8 Donkey';
' 9 Horse             10 Potar monkey  11 Cat               12 Giraffe';
' 13 Gorilla          14 Human         15 African elephant  16 Triceratops';
' 17 Rhesus monkey    18 Kangaroo      19 Hamster           20 Mouse';
' 21 Rabbit           22 Sheep         23 Jaguar            24 Chimpanzee';
' 25 Brachiosaurus    26 Rat           27 Mole              28 Pig';
' Column   Description';
'   1      Body weight in kilograms';
'   2      Brain weight in grams'];


 
 
x = [1.3500000000000001,8.0999999999999996
  465,423
  36.329999999999998,119.5
  27.66,115
  1.04,5.5
  11700,50
  2547,4603
  187.09999999999999,419
  521,655
  10,115
  3.2999999999999998,25.600000000000001
  529,680
  207,406
  62,1320
  6654,5712
  9400,70
  6.7999999999999998,179
  35,56
  0.12,1
  0.023,0.4
  2.5,12.1
  55.5,175
  100,157
  52.159999999999997,440
  87000,154.5
  0.28,1.8999999999999999
  0.122,3
  192,180];
 
endfunction
