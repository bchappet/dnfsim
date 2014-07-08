
taille = 9;
time = 1;
maxiteration = 100
// pour l'instant on creer juste la courbe de size9 time1

SOMMES = zeros(taille,taille);

t = 0;

dt = 0.1;

//disp("taille : "+string(taille));
//// On lit le fichier CSV qu'on stock dans la matrice M
//while (t < time-dt)
//    disp("time : "+string(t));
//    M = read_csv("~/Work/Loria2014/dnfsim2/data/size"+string(taille)+"/time"+string(time)+"/ReceiveMap_0_"+string(t)+".csv");
//    disp(M);
//    disp("--------------------------------------------------------------------")
//    t = t + dt;    
//end
//
iteration = 0;

// on lit le dernier fichier de chaque iteration, on sommme les valeurs et on 
// moyenne à la fin, one xtrait la courbe à la toute fin
while (iteration < maxiteration -1)
    disp("~/Work/Loria2014/dnfsim2/data/size"+string(taille)+"/time"+string(time)+"/ReceiveMap_"+string(iteration)+"_"+string(time-dt)+".csv");
    M = read_csv("~/Work/Loria2014/dnfsim2/statistiques/data/size"+string(taille)+"/time"+string(time)+"/ReceiveMap_"+string(iteration)+"_"+string(time-dt)+".csv");
    M = strtod(M);
    SOMMES = SOMMES + M;
    disp(M);
    disp(SOMMES);
    disp("--------------------------------------------------------------------");
    iteration = iteration + 1;
end

// PAS OUBLIER DE TESTER SUR DIFFERENTES PROBA AUSSI !!!!!!!!

