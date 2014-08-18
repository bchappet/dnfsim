// Copyright (C) 2012 - Michael Baudin
//
//
// This file must be used under the terms of the CeCILL.
// This source file is licensed as described in the file COPYING, which
// you should have received as part of this distribution.  The terms
// are also available at
// http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt

// <-- Non-regression test for bug 913 -->
//
// <-- URL -->
// http://forge.scilab.org/index.php/p/distfun/issues/913
//
// <-- Short Description -->
// distfun_unifrnd could produce a zero

// <-- JVM NOT MANDATORY -->

// Found zero in MT
distfun_genset("mt");
S = [];
S(1)=499;
S(2)=1755641559;
S(3)=2668299047;
S(4)=3921570668;
S(5)=1149367040;
S(6)=2072174890;
S(7)=858830862;
S(8)=4177636566;
S(9)=3071492706;
S(10)=1083694530;
S(11)=4069969090;
S(12)=3846645761;
S(13)=2110736592;
S(14)=3365276367;
S(15)=4231376347;
S(16)=367373869;
S(17)=508590414;
S(18)=2148404882;
S(19)=4027017986;
S(20)=2194050132;
S(21)=1870733848;
S(22)=4235407741;
S(23)=1566605772;
S(24)=4187536199;
S(25)=703276903;
S(26)=2238592088;
S(27)=2138577904;
S(28)=2800318567;
S(29)=3607791949;
S(30)=412882851;
S(31)=3485763729;
S(32)=3791935423;
S(33)=3404498915;
S(34)=1274492921;
S(35)=4010656993;
S(36)=689849576;
S(37)=3137060702;
S(38)=4058481793;
S(39)=2609353944;
S(40)=1219404041;
S(41)=3363211282;
S(42)=2742071306;
S(43)=2240701328;
S(44)=3783734546;
S(45)=3966367847;
S(46)=1576885716;
S(47)=3457297803;
S(48)=2864985327;
S(49)=3997505380;
S(50)=2807143237;
S(51)=2012409108;
S(52)=2464000411;
S(53)=4048407391;
S(54)=1677622792;
S(55)=1182324447;
S(56)=2887472016;
S(57)=1510697165;
S(58)=1518247589;
S(59)=3466012827;
S(60)=3831717525;
S(61)=3317451410;
S(62)=1807580291;
S(63)=1568485493;
S(64)=2786703934;
S(65)=725925227;
S(66)=1543041305;
S(67)=1837770562;
S(68)=90333279;
S(69)=3645987978;
S(70)=2896930255;
S(71)=3926275330;
S(72)=2544584065;
S(73)=1147301186;
S(74)=2230105051;
S(75)=351240575;
S(76)=588888533;
S(77)=3927640748;
S(78)=1624951529;
S(79)=1446366434;
S(80)=19119633;
S(81)=3610111254;
S(82)=3981718798;
S(83)=104877395;
S(84)=2413192356;
S(85)=1838217871;
S(86)=1890941491;
S(87)=2077693040;
S(88)=4222977124;
S(89)=1454115125;
S(90)=3034714505;
S(91)=2378592629;
S(92)=325031408;
S(93)=1930572602;
S(94)=1570393692;
S(95)=815008347;
S(96)=1883464784;
S(97)=187623928;
S(98)=3628666152;
S(99)=3403575110;
S(100)=409826613;
S(101)=1969515367;
S(102)=3503333069;
S(103)=1387769874;
S(104)=4171331846;
S(105)=1435626048;
S(106)=2578324736;
S(107)=2211433962;
S(108)=2115237828;
S(109)=2650639432;
S(110)=3819941533;
S(111)=4099327830;
S(112)=3031044799;
S(113)=2213627467;
S(114)=2193202910;
S(115)=4285320216;
S(116)=3114156395;
S(117)=225449044;
S(118)=2494513580;
S(119)=379410473;
S(120)=1708933728;
S(121)=1554378975;
S(122)=2133753763;
S(123)=3723559742;
S(124)=808976606;
S(125)=1425007602;
S(126)=1386803498;
S(127)=2842985616;
S(128)=2189481754;
S(129)=2774275848;
S(130)=2061070533;
S(131)=3569468877;
S(132)=2324538469;
S(133)=648887518;
S(134)=2571065086;
S(135)=2081237408;
S(136)=3409258280;
S(137)=3556695138;
S(138)=1212949715;
S(139)=46607973;
S(140)=755041699;
S(141)=2492189816;
S(142)=2078157122;
S(143)=3131161978;
S(144)=1000955426;
S(145)=1237443538;
S(146)=1460730261;
S(147)=4219312177;
S(148)=1138785059;
S(149)=3900362484;
S(150)=571024923;
S(151)=3199827704;
S(152)=1667158159;
S(153)=1330206719;
S(154)=2016634606;
S(155)=3539614828;
S(156)=2220101092;
S(157)=2820576030;
S(158)=3941291039;
S(159)=1652715285;
S(160)=2975116288;
S(161)=1450310242;
S(162)=1512086625;
S(163)=457709792;
S(164)=1439239715;
S(165)=256886193;
S(166)=3166298859;
S(167)=3105043751;
S(168)=3005400195;
S(169)=3081862723;
S(170)=1788949228;
S(171)=2970427008;
S(172)=2148965647;
S(173)=3793904312;
S(174)=1342295924;
S(175)=872432877;
S(176)=1004580476;
S(177)=205057385;
S(178)=3689363592;
S(179)=966456148;
S(180)=2717489630;
S(181)=2637347065;
S(182)=2998370628;
S(183)=2222303076;
S(184)=1228813085;
S(185)=3334095992;
S(186)=1068884235;
S(187)=1386721981;
S(188)=2917073635;
S(189)=3579355217;
S(190)=2095072053;
S(191)=4179017462;
S(192)=1513775313;
S(193)=3237031408;
S(194)=2613842108;
S(195)=1680043098;
S(196)=1216176197;
S(197)=3198152067;
S(198)=2802937265;
S(199)=561998419;
S(200)=4118222504;
S(201)=3583645563;
S(202)=2893235914;
S(203)=2334840831;
S(204)=467824593;
S(205)=2261730083;
S(206)=1649124075;
S(207)=3547709436;
S(208)=935024726;
S(209)=1306732341;
S(210)=898399585;
S(211)=731249443;
S(212)=2682961672;
S(213)=2953457296;
S(214)=3561281951;
S(215)=1947118301;
S(216)=186067751;
S(217)=492799914;
S(218)=2872360130;
S(219)=1671693374;
S(220)=3268277679;
S(221)=389591213;
S(222)=299676145;
S(223)=2931609048;
S(224)=2478505977;
S(225)=2539401294;
S(226)=3107312549;
S(227)=2064586793;
S(228)=983998777;
S(229)=2899223729;
S(230)=3104349121;
S(231)=366910225;
S(232)=3859303454;
S(233)=1809722704;
S(234)=1420353861;
S(235)=928929215;
S(236)=902563200;
S(237)=1693474273;
S(238)=761320430;
S(239)=2810241946;
S(240)=101081698;
S(241)=2029093877;
S(242)=2521575321;
S(243)=3048076142;
S(244)=2601626055;
S(245)=3051123388;
S(246)=845619677;
S(247)=1725558019;
S(248)=1812613765;
S(249)=1876398390;
S(250)=1634282925;
S(251)=3420282543;
S(252)=3959568623;
S(253)=402484660;
S(254)=3424832751;
S(255)=2177161704;
S(256)=2724805001;
S(257)=2780440165;
S(258)=717608690;
S(259)=740181326;
S(260)=2699790790;
S(261)=1229697280;
S(262)=1227526963;
S(263)=1726616714;
S(264)=895156886;
S(265)=3869836011;
S(266)=3955760949;
S(267)=3778021621;
S(268)=3435837329;
S(269)=1747967055;
S(270)=1241042971;
S(271)=1475780241;
S(272)=3017181452;
S(273)=3759776491;
S(274)=3262244222;
S(275)=4281184691;
S(276)=715397682;
S(277)=1019665986;
S(278)=2116008902;
S(279)=1944512692;
S(280)=2995076973;
S(281)=666934718;
S(282)=2249560883;
S(283)=2837519545;
S(284)=192111847;
S(285)=3274409631;
S(286)=2849754190;
S(287)=592783588;
S(288)=165019205;
S(289)=383870213;
S(290)=1775280365;
S(291)=537422493;
S(292)=106227189;
S(293)=355419756;
S(294)=375193688;
S(295)=3840843006;
S(296)=228425220;
S(297)=1441118505;
S(298)=1842229199;
S(299)=1366943572;
S(300)=4140887326;
S(301)=1630045736;
S(302)=3744784365;
S(303)=1536980311;
S(304)=266594852;
S(305)=2227834941;
S(306)=2395465524;
S(307)=859515988;
S(308)=3392573405;
S(309)=3469506504;
S(310)=4251416563;
S(311)=321527864;
S(312)=1551508529;
S(313)=2604125042;
S(314)=3040274314;
S(315)=2909313173;
S(316)=3195528137;
S(317)=762043846;
S(318)=3049825755;
S(319)=1370412292;
S(320)=1728419442;
S(321)=3430643928;
S(322)=2898384836;
S(323)=2093814130;
S(324)=2727791475;
S(325)=1023804171;
S(326)=3277672277;
S(327)=3575634016;
S(328)=3637817280;
S(329)=3390480079;
S(330)=941521329;
S(331)=2411140953;
S(332)=3081417864;
S(333)=3749214658;
S(334)=3718351975;
S(335)=4044541886;
S(336)=1249604679;
S(337)=4204174499;
S(338)=863001340;
S(339)=370195489;
S(340)=2093221255;
S(341)=2133129919;
S(342)=3600108127;
S(343)=3793710375;
S(344)=2542227361;
S(345)=1282943415;
S(346)=248662940;
S(347)=826357084;
S(348)=1846082284;
S(349)=967706568;
S(350)=3307337108;
S(351)=3042446279;
S(352)=3623549162;
S(353)=4025629863;
S(354)=75626018;
S(355)=3381834896;
S(356)=3454935592;
S(357)=1472202818;
S(358)=57304932;
S(359)=2409866028;
S(360)=1199463911;
S(361)=1432537832;
S(362)=1015690491;
S(363)=1546149747;
S(364)=711834293;
S(365)=1668503114;
S(366)=1758101580;
S(367)=1174194463;
S(368)=3609570527;
S(369)=2732298027;
S(370)=2090479791;
S(371)=2317264511;
S(372)=4124715825;
S(373)=1294740528;
S(374)=3019137019;
S(375)=1288244902;
S(376)=3860192762;
S(377)=3728309249;
S(378)=3872139116;
S(379)=945643819;
S(380)=2916950863;
S(381)=2238209930;
S(382)=4092136321;
S(383)=3147242927;
S(384)=3288367772;
S(385)=2346749543;
S(386)=1156610954;
S(387)=665288488;
S(388)=1806618153;
S(389)=3509134721;
S(390)=2806844123;
S(391)=468181671;
S(392)=323461406;
S(393)=4281795154;
S(394)=500561981;
S(395)=601055047;
S(396)=1037863993;
S(397)=1014015150;
S(398)=4109048815;
S(399)=327310910;
S(400)=895987537;
S(401)=3371223469;
S(402)=3426990522;
S(403)=4218736440;
S(404)=446311140;
S(405)=1828314731;
S(406)=3969873068;
S(407)=274286587;
S(408)=2911955964;
S(409)=1292638437;
S(410)=653363054;
S(411)=1973367084;
S(412)=2390343381;
S(413)=180416015;
S(414)=1806203310;
S(415)=1648153982;
S(416)=3915697024;
S(417)=3168153126;
S(418)=1321628861;
S(419)=4083862545;
S(420)=1033582686;
S(421)=625688926;
S(422)=3645579308;
S(423)=410970825;
S(424)=526531894;
S(425)=3601355564;
S(426)=2999114687;
S(427)=148636187;
S(428)=3638877308;
S(429)=3065143646;
S(430)=3022709743;
S(431)=2683459034;
S(432)=3671089206;
S(433)=3297411708;
S(434)=4049562271;
S(435)=3038656485;
S(436)=3048825603;
S(437)=3274364819;
S(438)=272356979;
S(439)=2942473397;
S(440)=1203239794;
S(441)=3217902851;
S(442)=2314519787;
S(443)=3455744816;
S(444)=1688001342;
S(445)=3822552638;
S(446)=1636878203;
S(447)=1375964126;
S(448)=2510809572;
S(449)=2055626858;
S(450)=2329636409;
S(451)=462504288;
S(452)=3600171505;
S(453)=253857834;
S(454)=567239168;
S(455)=1369737083;
S(456)=1798813926;
S(457)=223387722;
S(458)=3241970884;
S(459)=76764757;
S(460)=1350937724;
S(461)=500993839;
S(462)=84638645;
S(463)=4084344676;
S(464)=349848075;
S(465)=3793714620;
S(466)=608528927;
S(467)=1079501305;
S(468)=1381418194;
S(469)=3453241041;
S(470)=1082240074;
S(471)=1840448587;
S(472)=1203796839;
S(473)=3284417839;
S(474)=1697248978;
S(475)=3524760447;
S(476)=2471031841;
S(477)=815610577;
S(478)=2904048144;
S(479)=4225277722;
S(480)=2618479619;
S(481)=1215660246;
S(482)=2101887672;
S(483)=3686193865;
S(484)=1116525072;
S(485)=3784076650;
S(486)=307146574;
S(487)=2579991997;
S(488)=2723491455;
S(489)=3703664780;
S(490)=2530613790;
S(491)=2000699231;
S(492)=1044321695;
S(493)=413597308;
S(494)=1085548001;
S(495)=2117963518;
S(496)=3339684005;
S(497)=3437904198;
S(498)=508310935;
S(499)=4002478227;
S(500)=2355479624;
S(501)=0;
S(502)=3435877426;
S(503)=1926620319;
S(504)=785505205;
S(505)=4199781731;
S(506)=1351953061;
S(507)=2099995355;
S(508)=1907763360;
S(509)=4084478707;
S(510)=1911904704;
S(511)=1090855499;
S(512)=1020306065;
S(513)=1560339415;
S(514)=1256567198;
S(515)=974798340;
S(516)=563809679;
S(517)=141272254;
S(518)=612533334;
S(519)=2354939763;
S(520)=4203443854;
S(521)=2071222626;
S(522)=2406458976;
S(523)=264224383;
S(524)=3420060337;
S(525)=3140285865;
S(526)=4166395351;
S(527)=2558807739;
S(528)=700538380;
S(529)=1267180105;
S(530)=1067861082;
S(531)=2979591577;
S(532)=540862417;
S(533)=1869014869;
S(534)=994037320;
S(535)=2606164040;
S(536)=3166306236;
S(537)=881927917;
S(538)=1465144516;
S(539)=1392789866;
S(540)=3125427757;
S(541)=594304005;
S(542)=739195841;
S(543)=1125968970;
S(544)=3567509824;
S(545)=4274867299;
S(546)=226924571;
S(547)=2060461107;
S(548)=3351082206;
S(549)=3202783216;
S(550)=3437992808;
S(551)=1400316296;
S(552)=4270570181;
S(553)=4240708285;
S(554)=940987067;
S(555)=3478605058;
S(556)=1611667421;
S(557)=226097385;
S(558)=3278807910;
S(559)=3597327099;
S(560)=1777382089;
S(561)=4111147595;
S(562)=3007899524;
S(563)=3735599925;
S(564)=2667019883;
S(565)=287666822;
S(566)=1488579300;
S(567)=331201128;
S(568)=2897724867;
S(569)=2887492158;
S(570)=1863611164;
S(571)=3378662126;
S(572)=1640200386;
S(573)=4229043130;
S(574)=1544810518;
S(575)=1271592209;
S(576)=3483388412;
S(577)=2573144966;
S(578)=3681364425;
S(579)=3313085594;
S(580)=3362929846;
S(581)=1588333136;
S(582)=3689001037;
S(583)=3691629304;
S(584)=76060963;
S(585)=1529493896;
S(586)=1443724412;
S(587)=4038900775;
S(588)=2158647153;
S(589)=85446229;
S(590)=3627010487;
S(591)=1561443412;
S(592)=941437352;
S(593)=527093502;
S(594)=472667290;
S(595)=1545449865;
S(596)=1823227988;
S(597)=181392798;
S(598)=1554768058;
S(599)=3068514062;
S(600)=2155614892;
S(601)=1110789080;
S(602)=28838432;
S(603)=2963654388;
S(604)=907517129;
S(605)=832209706;
S(606)=2632015236;
S(607)=1511417517;
S(608)=224671609;
S(609)=2077647154;
S(610)=3515185573;
S(611)=3248499486;
S(612)=2710354863;
S(613)=395299979;
S(614)=4237398200;
S(615)=2081718463;
S(616)=2234997716;
S(617)=2135379436;
S(618)=1652449084;
S(619)=3973280447;
S(620)=3432739035;
S(621)=4255308835;
S(622)=3010576023;
S(623)=330593397;
S(624)=2355213295;
S(625)=1504825435;
distfun_seedset(S);
R = distfun_unifrnd(0,1);
assert_checktrue(R>0);
//
// URAND : zero
distfun_genset("urand");
S = [];
S(1)=1093556503.;
distfun_seedset(S);
R=distfun_unifrnd(0,1);
assert_checktrue(R>0);
//
// CLCG2 : zero
distfun_genset("clcg2");
S=[];
S(1)=1.74196765400000000e+009;
S(2)=5.47902410000000000e+008;
distfun_seedset(S(1),S(2));
R=distfun_unifrnd(0,1);
assert_checktrue(R>0);
//
// clcg4 : zero
distfun_genset("clcg4");
S=[];
S(1)=90838772;
S(2)=1189448975;
S(3)=1164539869;
S(4)=1845295655;
distfun_seedset(S(1),S(2),S(3),S(4));
R=distfun_unifrnd(0,1);
assert_checktrue(R>0);
//
// KISS : zero
distfun_genset("kiss");
S=[];
S(1)=1482409747;
S(2)=513556993;
S(3)=3780943645;
S(4)=1686363620;
distfun_seedset(S(1),S(2),S(3),S(4));
R=distfun_unifrnd(0,1);
assert_checktrue(R>0);
//
// FSULTRA : zero
distfun_genset("fsultra");
S=[];
S(1)=30;
S(2)=1;
S(3)=2924323215;
S(4)=3915733036;
S(5)=1084557462;
S(6)=3437269013;
S(7)=1435020197;
S(8)=2322061458;
S(9)=3871557256;
S(10)=285026523;
S(11)=1281980567;
S(12)=250345929;
S(13)=622825441;
S(14)=4106367234;
S(15)=1400067649;
S(16)=27715246;
S(17)=3417420807;
S(18)=1601260901;
S(19)=3167762375;
S(20)=2272555463;
S(21)=118268669;
S(22)=3633841486;
S(23)=3033455436;
S(24)=3144423560;
S(25)=519099106;
S(26)=2844090768;
S(27)=1089695562;
S(28)=3221791024;
S(29)=156870031;
S(30)=2109179119;
S(31)=1089276465;
S(32)=3594876795;
S(33)=620558029;
S(34)=653107843;
S(35)=1083890067;
S(36)=2031083489;
S(37)=3752836326;
S(38)=3450574389;
S(39)=1983311493;
S(40)=1979496326;
distfun_seedset(S);
R=distfun_unifrnd(0,1);
assert_checktrue(R>0);
