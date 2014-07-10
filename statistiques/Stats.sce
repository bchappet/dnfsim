maxiteration = 100;//1000.0; //100
dt = 0.1;
tailles = [9];//[9,10,19,20,29,30,39,40,49,50]
weigths = linspace(0.0,0.95,20);//[0.0,0.1,0.2,0.3,0.4,0.5,0.6,0.7,0.8,0.9];//[0.0,0.1,0.2,0.3,0.4,0.5,0.6,0.7,0.8,0.9]
times = [5];//[1,2,3,4,5,6,7,8,9,10]
epsilon = 0.000000001;

function[SOMMES]=computeAverageMatrix(initialisation_packet,taille,time,weigth,maxiteration,dt)
    SOMMES = zeros(taille,taille);
    // on lit le dernier fichier de chaque iteration (time-dt), on sommme les valeurs et on 
    // moyenne à la fin, one xtrait la courbe à la toute fin
    //iteration = 0;
    for iteration = 0:maxiteration-1
        if weigth == 0.0 then
            sweigth = "0.0";
        else
            sweigth = string(weigth);
        end
        M = read_csv("~/Work/Loria2014/dnfsim2/statistiques/data/"+initialisation_packet+"/size"+string(taille)+"/time"+string(time)+"/weigth"+sweigth+"/ReceiveMap_"+string(iteration)+"_"+string(time-dt)+".csv");
        M = strtod(M);
        SOMMES = SOMMES + M;

    end;
    SOMMES = SOMMES / maxiteration;
    //disp(SOMMES);
    return double(SOMMES);
endfunction


function[fitness] = computeFitness(A,B,ABe)
    ABt = A + B;
    M = abs(ABt - ABe)./(ABt + ABe + epsilon);
    fitness = sum(M) / length(A);
    return fitness;
endfunction

function[fitness] = computeAllFitness(times,tailles,weigths,maxiteration,dt)
    fitness = zeros(size(weigths));
    for(time = times)
        for(taille = tailles)
            i = 1;
            while(i <= size(weigths,'c'))
                //                disp(i);
                weigth = weigths(i);
                //for(weigth = weigths)
                M_a = computeAverageMatrix('a_send',taille,time,weigth,maxiteration,dt);
                M_b = computeAverageMatrix('b_send',taille,time,weigth,maxiteration,dt);
                M_ab = computeAverageMatrix('ab_send',taille,time,weigth,maxiteration,dt);
                disp("time : "+string(time)+" taille : "+string(taille)+" weigth : "+string(weigth));
                //                disp('a_send');
                //                disp(M_a);
                //                disp('b_send');
                //                disp(M_b);
                //                disp('ab_send');
                //                disp(M_ab);
                //                disp('a_send + b_send - ab_send');
                //                disp((M_a+M_b-M_ab));
                //disp(computeFitness(M_a,M_b,M_ab));
                fitness(i)= computeFitness(M_a,M_b,M_ab);
                i = i+1;
            end
        end
    end
    return fitness
endfunction

//fitness = computeAllFitness(times,tailles,weigths,maxiteration,dt);
//plot(weigths,fitness);
//xtitle( 'Critère d additivité', 'poids', 'fitness') ;






function[diagonal]=computeAverageDiagonal(initialisation_packet,taille,time,weigth,maxiteration,dt)
    diagonal = zeros(taille);
    for iteration = 0:maxiteration-1
        if weigth == 0.0 then
            sweigth = "0.0";
        else
            sweigth = string(weigth);
        end
        M = read_csv("~/Work/Loria2014/dnfsim2/statistiques/data/"+initialisation_packet+"/size"+string(taille)+"/time"+string(time)+"/weigth"+sweigth+"/ReceiveMap_"+string(iteration)+"_"+string(time-dt)+".csv");
        diag_M = diag(strtod(M));
        diagonal = diag_M + diagonal;
    end;
    diagonal = diagonal / maxiteration;
    return double(diagonal);
endfunction

function[fitness] = computeAllDiagonalFitness(times,tailles,weigths,maxiteration,dt)
    fitness = zeros(size(weigths));
    for(time = times)
        for(taille = tailles)
            i = 1;
            while(i <= size(weigths,'c'))
                weigth = weigths(i);
                d_a = computeAverageDiagonal('a_send',taille,time,weigth,maxiteration,dt);
                d_b = computeAverageDiagonal('b_send',taille,time,weigth,maxiteration,dt);
                d_ab = computeAverageDiagonal('ab_send',taille,time,weigth,maxiteration,dt);
                disp("time : "+string(time)+" taille : "+string(taille)+" weigth : "+string(weigth));
                fitness(i)= computeFitness(d_a,d_b,d_ab);
                i = i+1;
            end
        end
    end
    return fitness
endfunction

fitness = computeAllDiagonalFitness(times,tailles,weigths,maxiteration,dt);
//plot(weigths,[computeAllDiagonalFitness(times,tailles,weigths,10,dt) computeAllDiagonalFitness(times,tailles,weigths,100,dt) computeAllDiagonalFitness(times,tailles,weigths,500,dt) computeAllDiagonalFitness(times,tailles,weigths,1000,dt)]);
f10 = computeAllDiagonalFitness(times,tailles,weigths,10,dt);
f100 = computeAllDiagonalFitness(times,tailles,weigths,100,dt);
f500 = computeAllDiagonalFitness(times,tailles,weigths,500,dt);
f1000 = computeAllDiagonalFitness(times,tailles,weigths,1000,dt);

plot(weigths,f10,'r');
plot(weigths, f100,'b');
plot(weigths,f500,'g');
plot(weigths,f1000,'cyan');

xtitle( 'Critère d additivité sur diagonale', 'poids', 'fitness') ;
legend('10 iterations','100 itérations','500 itérations','1000 itérations');

