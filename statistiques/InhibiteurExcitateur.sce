exec('Utils.sci');
xdel();xdel();



w_inhib = 0.5//0.5;
w_excit = 0.7//0.7;
t_inhib = 3//3;
t_excit = 5;//5//5;

taille = 19;

N = 20;

aE = 4*0.3*(1/7)/N;
aI = 1*0.3*(1/7)/N;
bE = -0.5;
bI = 0;
initialisation_packet = string(taille)+'_a_send_'+string(N);
maxiteration = 1000;


function[VARIANCES]=computeVariance(moyennes)
    //disp('okay : '+string(taille));
    VARIANCES = zeros(taille);
    for iteration = 0:maxiteration-1
        
        M_I = read_csv(here+"/data/"+initialisation_packet+"/size"+string(taille)+"/time"+string(t_inhib)+"/weigth"+w_inhib+"/ReceiveMap_"+string(iteration)+"_"+string(time-dt)+".csv");
        M_I =  getDiag(strtod(M_I));
        
        M_E = read_csv(here+"/data/"+initialisation_packet+"/size"+string(taille)+"/time"+string(t_excit)+"/weigth"+w_excit+"/ReceiveMap_"+string(iteration)+"_"+string(time-dt)+".csv");
        M_E =  getDiag(strtod(M_E));
        
        diffM = (aE*M_E+bE-(aI*M_I+bI)) - moyennes;
        //disp("diffM : "+string(diffM));
        VARIANCES = VARIANCES + diffM .* diffM;
        //disp("V : "+string(VARIANCES));
    end;
    //disp(VARIANCES)
    VARIANCES = VARIANCES ./ maxiteration;
    //disp(VARIANCES)
    return double(VARIANCES);
endfunction


//computeAverageMatrix(initialisation_packet,taille,time,weigth,maxiteration,dt)
inhib = computeAverageDiagonal(initialisation_packet,taille,t_inhib,w_inhib,maxiteration,0.1);
excit = computeAverageDiagonal(initialisation_packet,taille,t_excit,w_excit,maxiteration,0.1);

x = linspace(1,taille,taille);



clf();
plot2d(x, coeffI*inhib+bI,style=[color('blue')]);
plot2d(x, coeffE* excit+bE,style=[color('red')]);
emi = aE*excit+bE-(aI*inhib+bI);
vemi = computeVariance(emi);
plot2d(x, emi);
plot2d(x,vemi);
legend(string(aI)+'*inhibiteur +'+string(bI)+' '+string(w_inhib)+' '+string(t_inhib)+'s',string(aE)+'*excitateur +'+string(bE)+' '+string(w_excit)+' '+string(t_excit)+'s','difference','variance de la difference');

//scf();
//y = x;
//inhib = computeAverageMatrix(string(taille)+'_a_send_'+string(N),taille,t_inhib,w_inhib,1000,0.1);
//excit = computeAverageMatrix(string(taille)+'_a_send_'+string(N),taille,t_excit,w_excit,1000,0.1);
//set(gcf(), "color_map", jetcolormap(64));
//plot3d1(x, y, coeffE*excit+bE- (coeffI*inhib+bI));

