// Copyright (C) 2013 - Michael Baudin
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

// Source: [7]
// "Life Expectancy", "Fertility Rate", "Population"
data=[
80.66, 1.67, 33739900
79.84, 1.36, 81902307
78.6,  1.84, 5523095
72.73, 2.78, 79716203
80.05, 2,    61801570
72.49, 1.7,  73137148
68.09, 4.77, 31090763
81.55, 2.96, 7485600
68.6,  1.54, 141850000
78.09, 2.05, 307007000
];
legen=[
'CAN'
'DEU'
'DNK'
'EGY'
'GBR'
'IRN'
'IRQ'
'ISR'
'RUS'
'USA'
];
//
// Just the data
h=scf();
xtitle("","Life Expectancy", "Fertility Rate")
bubblechart(data)
//
// Disable scaling, set isoview on.
h=scf();
xtitle("","Life Expectancy", "Fertility Rate")
bubblechart(data,[],[],[],%f)
h.children.isoview="on";
//
// One single color, with datatips
h=scf();
xtitle("","Life Expectancy", "Fertility Rate")
bubblechart(data,legen,2)
//
// Different colors, with datatips
h=scf();
h.color_map = rainbowcolormap(10);
xtitle("","Life Expectancy", "Fertility Rate")
bubblechart(data,legen,1:10)
//
// With a separate legend
h=scf();
h.color_map = rainbowcolormap(10);
xtitle("","Life Expectancy", "Fertility Rate")
bubblechart(data)
legend(legen,"in_upper_right");

// Change the color depending on the world zone
h=scf();
xtitle("","Life Expectancy", "Fertility Rate")
// 1 = CAN, USA (North America)
// 2 = Europe (DEU, DNK, GBR, RUS)
// 3 = Arab/Persian/Hebrew (EGY,IRN,IRQ,ISR)
h.color_map = rainbowcolormap(3);
fill=[
1
2
2
3
2
3
3
3
2
1
];
bubblechart(data,legen,fill)
//
// Without contours on the bubbles
h=scf();
xtitle("","Life Expectancy", "Fertility Rate")
h.color_map = rainbowcolormap(3);
bubblechart(data,legen,-fill)

// Reference : [3]
data =[
14 12200 15
20 60000 23
18 24400 10
];
h=scf();
xtitle("Industry Market Share Study","Number of products",...
"Sales");
h.color_map = rainbowcolormap(3);
legen=string(1:3);
bubblechart(data,legen)

// Reference : [4]
data =[
10 10 100
5 5 75
8 5 65
3 2 60
5 3 50
1 2 35
];
legen=[
"Smith"
"West"
"Miller"
"Carlson"
"Redmond"
"Dillar"
];
h=scf();
xtitle("Salary Study","Years with Firm",...
"% Salary Increase");
h.color_map = rainbowcolormap(6);
bubblechart(data,legen)
//
// With a separate legend
h=scf();
xtitle("Salary Study","Years with Firm",...
"% Salary Increase");
h.color_map = rainbowcolormap(6);
bubblechart(data)
legend(legen,"in_upper_left");

// Reference [5-6]
legen=[
"Harvard University"
"Yale University"
"University of Texas"
"Princeton University"
"Stanford University"
"MIT"
"University of Michigan"
"Columbia University"
"Northwestern University"
"Texas A&M"
];
data=[
52652  31728080000 21000
52700  19374000000 11875
9816   17148649000 51112
54780  17109508000 7859
52860  16502606000 19945
40460  9712628000  10894
25204  7834752000  42716
59208  7789578000  28221
40223  7182745000  20284
19035  6999517000  49861
];
// Scale the Undergraduate tuition
data(:,1)=data(:,1)/1.e3;
// Scale the endowment funds
data(:,2)=data(:,2)/1.e9;
// Scale the Number of Students
data(:,3)=data(:,3)/1.e3;
// The color is the rank
h=scf();
xtitle("Area of bubble : Number of Students Fall 2011",..
"Undergraduate tuition ($$Thousands)",..
"2011 endowment funds ($ Billions)")
h.color_map = rainbowcolormap(10);
tags=string((1:10)');
bubblechart(data,tags,[],4)
legend(tags+":"+legen,"in_upper_left");
