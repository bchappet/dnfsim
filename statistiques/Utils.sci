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


////////
////////here = pwd();
////////
//////////------------------------------------------------------------------------------
//////////--------------------------calcul des données en moyenne-----------------------
////------------------------------------------------------------------------------

function[SOMMES]=averageMatrixFiles(files,taille)
    SOMMES = zeros(taille,taille);
    for file=files
        M = read_csv(file);
        M = strtod(M);
        SOMMES = SOMMES + M;
    end;
    SOMMES = SOMMES / size(files,'*');
    return double(SOMMES);
endfunction
////////
////////function[SOMMES]=averageMatrix(initialisation_packet,taille,time,weigth,maxiteration,dt)
////////    //    SOMMES = zeros(taille,taille);
////////    //    // on lit le dernier fichier de chaque iteration (time-dt), on sommme les valeurs et on 
////////    //    // moyenne à la fin, one xtrait la courbe à la toute fin
////////    //    //disp(maxiteration)
////////    //    for iteration = 0:maxiteration-1
////////    //        if weigth == 0.0 then
////////    //            sweigth = "0.0";
////////    //        else
////////    //            if weigth == 1.0 then
////////    //                sweigth = "1.0";
////////    //            else
////////    //                sweigth = string(weigth);
////////    //            end
////////    //        end
////////    //        M = read_csv(here+"/data/"+initialisation_packet+"/size"+string(taille)+"/time"+string(time)+"/weigth"+sweigth+"/ReceiveMap_"+string(iteration)+"_"+string(time-dt)+".csv");
////////    //        M = strtod(M);
////////    //        SOMMES = SOMMES + M;
////////    //    end;
////////    //    SOMMES = SOMMES / maxiteration;
////////    //    return double(SOMMES);
////////    //disp(maxiteration);
////////    files = string(zeros(1,maxiteration));
////////    for iteration = 0:maxiteration-1
////////        if weigth == 0.0 then
////////            sweigth = "0.0";
////////        else
////////            if weigth == 1.0 then
////////                sweigth = "1.0";
////////            else
////////                sweigth = string(weigth);
////////            end
////////        end
////////        s = here+"/data/"+initialisation_packet+"/size"+string(taille)+"/time"+string(time)+"/weigth"+sweigth+"/ReceiveMap_"+string(iteration)+"_"+string(time-dt)+".csv";
////////        files((iteration+1)) = s;
////////    end;
////////    SOMMES = averageMatrixFiles(files,taille);
////////endfunction
////////
////////function[diagonal]=averageDiagonal(initialisation_packet,taille,time,weigth,maxiteration,dt)
////////    diagonal = zeros(taille);
////////    for iteration = 0:maxiteration-1
////////        if weigth == 0.0 then
////////            sweigth = "0.0";
////////        else
////////            if weigth == 1.0 then
////////                sweigth = "1.0";
////////            else
////////                sweigth = string(weigth);
////////            end
////////        end
////////        M = read_csv(here+"/data/"+initialisation_packet+"/size"+string(taille)+"/time"+string(time)+"/weigth"+sweigth+"/ReceiveMap_"+string(iteration)+"_"+string(time-dt)+".csv");
////////        diag_M = diag(strtod(M));
////////        diagonal = diag_M + diagonal;
////////    end;
////////    diagonal = diagonal / maxiteration;
////////    return double(diagonal);
////////endfunction
