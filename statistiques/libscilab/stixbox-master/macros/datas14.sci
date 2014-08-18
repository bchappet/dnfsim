// Copyright (C) 2010 - DIGITEO - Michael Baudin
// Copyright (C) 1993 - 1995 - Anders Holtsberg
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

function [x,txt]=datas14()
  txt=['Phone call';
'Source: Factures de France Telecom';
'Taken From: source  ';
'Dimensions: 64 observations de 3 variables';
'Description:  details de 2 factures de telephone pour deux  periodes';
' non consecutives de deux mois  (excepte les appels';
' locaux).  Sur une ligne est donnee, la date d''un appel ';
' (le jour dans la 1ere colonne et le mois dans la 2eme ';
' colonne) ainsi que sa duree en secondes (3eme colonne) ';
' Les 33 premieres lignes correspondent a des appels dit de';
' voisinage et les 31 dernieres lignes sont des appels dit';
' nationaux.    ';]


x =[      13          12          27
          13          12          23
          13          12         192
          17          12          45
          17          12         306
          17          12         116
          18          12         121
          26          12         411
           3           1          77
           3           1         274
           4           1         104
           4           1          10
           8           1         817
          14           1          68
          16           1         241
          22           1         102
          30           1         357
           1           2         846
          25           5          80
          25           5          97
          27           5          37
           5           6          17
           5           6           8
           5           6         112
           5           6         455
          12           6         124
          13           6           8
          13           6           8
          13           6         107
          20           6          33
          30           6          32
          30           6          16
          30           6          56
          20          12         868
          24          12         880
          26          12         534
          26          12          12
          27          12         286
          29          12         771
          29          12         491
          29          12         952
          31          12        1014
           1           1         208
           1           1         300
           1           1          15
           2           1        1844
          20           1        1437
          30           1         357
          13           2        1257
          14           2         239
           5           3         703
           5           3         703
          24           5        1886
          27           5           7
          31           5          17
          31           5          22
           1           6          26
           4           6         653
          10           6         498
          12           6         996
          13           6         119
 	  16           6         250
          29           6         190
          10           7         116];
endfunction
