exec('loader.sce');
xdel();xdel();

// 'tailles' 'times' 'weigths' 'iterations' sommesStimulis 'stimulis1 stimulis2 ...' testing
args = sciargs();

taille = strtod(args(6));
initialisation_packet = args(7);
maxiteration = strtod(args(8));
w_inhib = strtod(args(9));
t_inhib = strtod(args(10));
aI = strtod(args(11));;
bI = strtod(args(12));
w_excit = strtod(args(13));
t_excit = strtod(args(14));
aE = strtod(args(15));
bE = strtod(args(16));


dt = 0.1;



//ci=ciboot(x,T,method,c);

//computeAverageMatrix(initialisation_packet,taille,time,weigth,maxiteration,dt)
inhib = computeAverageDiagonal(initialisation_packet,taille,t_inhib,w_inhib,maxiteration,dt);
excit = computeAverageDiagonal(initialisation_packet,taille,t_excit,w_excit,maxiteration,dt);

x = linspace(1,taille,taille);



clf();
plot2d(x, aI*inhib+bI,style=[color('blue')]);
plot2d(x, aE* excit+bE,style=[color('red')]);
emi = aE*excit+bE-(aI*inhib+bI);
varE = computeVarianceDiagonal(excit,initialisation_packet,taille,t_excit,w_excit,maxiteration,dt);
varI = computeVarianceDiagonal(inhib,initialisation_packet,taille,t_inhib,w_inhib,maxiteration,dt);
vemi = aE * aE * varE + (-aI) * (-aI) * varI;
vemi = sqrt(vemi);
plot2d(x, emi,style=[color('green')]);
plot2d(x,vemi);
legend(string(aI)+'*inhibiteur +'+string(bI)+' '+string(w_inhib)+'w '+string(t_inhib)+'s (moyenne)',string(aE)+'*excitateur +'+string(bE)+' '+string(w_excit)+'w '+string(t_excit)+'s (moyenne)','difference (moyenne)','difference(ecart type)');

//scf();
//y = x;
//inhib = computeAverageMatrix(string(taille)+'_a_send_'+string(N),taille,t_inhib,w_inhib,1000,0.1);
//excit = computeAverageMatrix(string(taille)+'_a_send_'+string(N),taille,t_excit,w_excit,1000,0.1);
//set(gcf(), "color_map", jetcolormap(64));
//plot3d1(x, y, coeffE*excit+bE- (coeffI*inhib+bI));

