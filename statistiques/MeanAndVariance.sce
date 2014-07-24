// size 9 , weigth 0.7 iterations 1000 time 5 , faire une coupe sur la diagonale de A vers B (a_send en gros)

here = pwd();
taille = 9;
time = 5;
weigth = 0.7
time = 5
dt = 0.1
maxiteration = 1000

//function[diagonal]=computeAverageDiagonal(initialisation_packet,taille,time,weigth,maxiteration,dt)
//    diagonal = zeros(taille);
//    for iteration = 0:maxiteration-1
//        if weigth == 0.0 then
//            sweigth = "0.0";
//        else
//            if weigth == 1.0 then
//                sweigth = "1.0";
//            else
//                sweigth = string(weigth);
//            end
//        end
//        M = read_csv(here+"/data/"+initialisation_packet+"/size"+string(taille)+"/time"+string(time)+"/weigth"+sweigth+"/ReceiveMap_"+string(iteration)+"_"+string(time-dt)+".csv");
//        diag_M = diag(strtod(M));
//        diagonal = diag_M + diagonal;
//    end;
//    diagonal = diagonal / maxiteration;
//    return double(diagonal);
//endfunction

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


//plot(linspace(1,9,9),exec(computeAverageDiagonal("a_send",taille,time,weigth,maxiteration,dt));
surf(linspace(1,9,9),linspace(1,9,9),exec(computeAverageMatrix("a_send",taille,time,weigth,maxiteration,dt));
