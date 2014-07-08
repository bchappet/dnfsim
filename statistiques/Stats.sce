maxiteration = 100
dt = 0.1;
tailles = [9];//[9,10,19,20,29,30,39,40,49,50]
weigths = ['0.0','0.5'];//)[0.1,0.2,0.3,0.4,0.5,0.6,0.7,0.8,0.9]
times = [5];//[1,2,3,4,5,6,7,8,9,10]

function[M]=computeAverageMatrix(initialisation_packet,taille,time,weigth,maxiteration,dt)
    SOMMES = zeros(taille,taille);
    // on lit le dernier fichier de chaque iteration (time-dt), on sommme les valeurs et on 
    // moyenne à la fin, one xtrait la courbe à la toute fin
    //iteration = 0;
    for iteration = 0:maxiteration-1
        M = read_csv("~/Work/Loria2014/dnfsim2/statistiques/data/"+initialisation_packet+"/size"+string(taille)+"/time"+string(time)+"/weigth"+string(weigth)+"/ReceiveMap_"+string(iteration)+"_"+string(time-dt)+".csv");
        M = strtod(M);
        SOMMES = SOMMES + M;
    end;
    SOMMES = SOMMES / maxiteration;
    return M;
endfunction



for(time = times)
    for(taille = tailles)
        for(weigth = weigths)
            M_a = computeAverageMatrix('a_send',taille,time,weigth,maxiteration,dt);
            M_b = computeAverageMatrix('b_send',taille,time,weigth,maxiteration,dt);
            M_ab = computeAverageMatrix('ab_send',taille,time,weigth,maxiteration,dt);
            disp("time : "+string(time)+" taille : "+string(taille)+" weigth : "+string(weigth));
            disp('a_send');
            disp(M_a);
            disp('b_send');
            disp(M_b);
            disp('ab_send');
            disp(M_ab);
        end
    end
end
