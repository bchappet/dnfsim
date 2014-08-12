exec('Utils.sci');
xdel();xdel();

function[VARIANCES]=computeVarianceDiagonalWithLinearFilter(moyennes,initialisation_packet,taille,time,weigth,maxiteration,dt,a,b)
    //disp('okay : '+string(taille));
    VARIANCES = zeros(taille);
    for iteration = 0:maxiteration-1
        if weigth == 0.0 then
            sweigth = "0.0";
        else
            if weigth == 1.0 then
                sweigth = "1.0";
            else
                sweigth = string(weigth);
            end
        end
        M = read_csv(here+"/data/"+initialisation_packet+"/size"+string(taille)+"/time"+string(time)+"/weigth"+sweigth+"/ReceiveMap_"+string(iteration)+"_"+string(time-dt)+".csv");
        M =  getDiag(strtod(M));
        diffM = a*M+b - moyennes;
        //disp("diffM : "+string(diffM));
        VARIANCES = VARIANCES + diffM .* diffM;
        //disp("V : "+string(VARIANCES));
    end;
    //disp(VARIANCES)
    VARIANCES = VARIANCES ./ maxiteration;
    //disp(VARIANCES)
    return double(VARIANCES);
endfunction

w_inhib = 0.5//0.5;
w_excit = 0.7//0.7;
t_inhib = 3//3;
t_excit = 5;//5//5;

taille = 19;

N = 20;

coeffE = 4*0.3*(1/7)/N;
coeffI = 1*0.3*(1/7)/N;
bE = -0.5;
bI = 0;

//computeAverageMatrix(initialisation_packet,taille,time,weigth,maxiteration,dt)
inhib = computeAverageDiagonal(string(taille)+'_a_send_'+string(N),taille,t_inhib,w_inhib,1000,0.1);
excit = computeAverageDiagonal(string(taille)+'_a_send_'+string(N),taille,t_excit,w_excit,1000,0.1);

x = linspace(1,taille,taille);



clf();
plot2d(x, coeffI*inhib+bI,style=[color('blue')]);
plot2d(x, coeffE* excit+bE,style=[color('red')]);
emi = coeffE*excit+bE-(coeffI*inhib+bI);
plot2d(x, emi);
legend(string(coeffI)+'*inhibiteur +'+string(bI)+' '+string(w_inhib)+' '+string(t_inhib)+'s',string(coeffE)+'*excitateur +'+string(bE)+' '+string(w_excit)+' '+string(t_excit)+'s','difference');

//scf();
//y = x;
//inhib = computeAverageMatrix(string(taille)+'_a_send_'+string(N),taille,t_inhib,w_inhib,1000,0.1);
//excit = computeAverageMatrix(string(taille)+'_a_send_'+string(N),taille,t_excit,w_excit,1000,0.1);
//set(gcf(), "color_map", jetcolormap(64));
//plot3d1(x, y, coeffE*excit+bE- (coeffI*inhib+bI));

