// size 9 , weigth 0.7 iterations 1000 time 5 , faire une coupe sur la diagonale de A vers B (a_send en gros)

here = pwd();


function[diagonal]=computeAverageDiagonal(initialisation_packet,taille,time,weigth,maxiteration,dt)
    //disp('okay : '+string(taille));
    diagonal = zeros(taille);
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
        diag_M = getDiag(strtod(M));
        diagonal = diag_M + diagonal;
    end;
    diagonal = diagonal / maxiteration;
    return double(diagonal);
endfunction


function[SOMMES]=computeAverageMatrix(initialisation_packet,taille,time,weigth,maxiteration,dt)
    SOMMES = zeros(taille,taille);
    // on lit le dernier fichier de chaque iteration (time-dt), on sommme les valeurs et on 
    // moyenne à la fin, one xtrait la courbe à la toute fin
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
        M = strtod(M);
        SOMMES = SOMMES + M;
    end;
    SOMMES = SOMMES / maxiteration;
    return double(SOMMES);
endfunction

function[VARIANCES]=computeVarianceMatrix(moyennes,initialisation_packet,taille,time,weigth,maxiteration,dt)
    VARIANCES = zeros(taille,taille);
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
        M =  strtod(M);
        diffM = M - moyennes;
        //disp("diffM : "+string(diffM));
        VARIANCES = VARIANCES + diffM .* diffM;
        //disp("V : "+string(VARIANCES));
    end;
    //disp(VARIANCES)
    VARIANCES = VARIANCES ./ maxiteration;
    //disp(VARIANCES)
    return double(VARIANCES);
endfunction

function[VARIANCES]=computeVarianceDiagonal(moyennes,initialisation_packet,taille,time,weigth,maxiteration,dt)
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
        diffM = M - moyennes;
        //disp("diffM : "+string(diffM));
        VARIANCES = VARIANCES + diffM .* diffM;
        //disp("V : "+string(VARIANCES));
    end;
    //disp(VARIANCES)
    VARIANCES = VARIANCES ./ maxiteration;
    //disp(VARIANCES)
    return double(VARIANCES);
endfunction

function[diagonal]=getDiag(M)
    //diagonal = diag(M);
    taille = sqrt(size(M,'*'));
    diagonal = zeros(taille);
    for i=1:taille
        diagonal(i)=M(i,i);

    end
    return diagonal;
endfunction

//plot(linspace(1,9,9),exec(computeAverageDiagonal("a_send",taille,time,weigth,maxiteration,dt));
//moy = computeAverageMatrix("a_send",taille,time,weigth,maxiteration,dt);
//var = computeVarianceMatrix(moy,"a_send",taille,time,weigth,maxiteration,dt);

//clf();
//xtitle('Spike reçu jusqu alors.'+' taille : '+string(taille)+' time : '+string(time)+ ' weigth : '+string(weigth), 'x', 'y','nombre de spikes reçus');
//set(gcf(), "color_map", jetcolormap(64));
//x = linspace(1,9,9);
//y = linspace(1,9,9);
//z = moy;
//plot3d1(x, y, z);
//surf(x,y,z);
//surf(linspace(1,9,9),linspace(1,9,9),var);

//moyd = computeAverageDiagonal("a_send",taille,time,weigth,maxiteration,dt);
//vard = computeVarianceDiagonal(moyd,"a_send",taille,time,weigth,maxiteration,dt);

//plot(linspace(1,9,9),vard)

//write_csv([linspace(1,9,9)',vard],"vardiag.csv",",",".");



function[]=goGirl(sender,taille,times,weigths,maxiteration,dt,doSingleView,doDiag,doVar,doEcartype)
    clf();
    x = linspace(1,taille,taille);
    y = linspace(1,taille,taille);
    label ='';
    if doVar ==1 then
        label = 'variance';
    else
        if doEcartype == 1 then
            label = 'ecart type';
        else
            label='moyenne';
        end
    end


    i = 1;
    for weigth=weigths
        for time=times
            if doSingleView==1 then
                clf();
            else
                subplot(size(weigths,'*'),size(times,'*'),i);
            end
            if doDiag==1 then
                xtitle(label+' sender : '+sender+' taille : '+string(taille)+' time : '+string(time)+ ' weigth : '+string(weigth), 'x', label+' des spikes reçus');
                z = computeAverageDiagonal(sender,taille,time,weigth,maxiteration,dt);
                if doVar==1 then
                    z = computeVarianceDiagonal(z,sender,taille,time,weigth,maxiteration,dt);
                end
                if doEcartype == 1 then
                    z = computeVarianceDiagonal(z,sender,taille,time,weigth,maxiteration,dt);
                    z = sqrt(z);
                end
                //disp(size(z,'*'));
                plot2d(x,z);

            else
                xtitle(label+' sender : '+sender+' taille : '+string(taille)+' time : '+string(time)+ ' weigth : '+string(weigth), 'x', 'y',label+' des spikes reçus');
                set(gcf(), "color_map", jetcolormap(64));
                z = computeAverageMatrix(sender,taille,time,weigth,maxiteration,dt);
                if doVar==1 then
                    z = computeVarianceMatrix(z,sender,taille,time,weigth,maxiteration,dt);
                end
                if doEcartype == 1 then
                    z = computeVarianceMatrix(z,sender,taille,time,weigth,maxiteration,dt);
                    z = sqrt(z);
                end
                plot3d1(x, y, z);
            end
            if doSingleView==1 then
                t = 'rapport/images/spike_data'+'_diag'+string(doDiag)+'_'+label+'_init'+sender+'_taille'+string(taille)+'_time'+string(time)+'_weigth'+string(weigth);
                xs2ps(0,t +'.ps');
                xs2png(0,t +'.png');
                xs2pdf(0,t +'.pdf');
            end

            i=i+1;

        end
    end
    if doSingleView==1 then
    else
        t = 'rapport/images/global_spike_data'+'_diag'+string(doDiag)+'_'+label+'_init'+sender+'_taille'+string(taille);
        xs2ps(0,t+'.ps');
        xs2png(0,t+'.png');
        xs2pdf(0,t+'.pdf'); 
    end

endfunction


b =   [1,0];
taille = 9;
times = [1,3,5];
weigths = [0.0,0.3,0.5,0.7,0.9];
dt = 0.1;
maxiteration = 1000;
sender = 'a_send';

for doDiag=b 
    for doSingleView=b
        goGirl(sender,taille,times,weigths,maxiteration,dt,doSingleView,doDiag,0,0);
        goGirl(sender,taille,times,weigths,maxiteration,dt,doSingleView,doDiag,1,0);
        goGirl(sender,taille,times,weigths,maxiteration,dt,doSingleView,doDiag,0,1);

    end
end


//goGirl(sender,taille,times,weigths,maxiteration,dt,0,0,0);






