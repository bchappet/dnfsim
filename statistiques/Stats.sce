
function[SOMMES]=computeAverageMatrix(initialisation_packet,taille,time,weigth,maxiteration,dt)
    SOMMES = zeros(taille,taille);
    // on lit le dernier fichier de chaque iteration (time-dt), on sommme les valeurs et on 
    // moyenne à la fin, one xtrait la courbe à la toute fin
    //iteration = 0;
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
        M = read_csv("~/Loria2014/dnfsim2/statistiques/data/"+initialisation_packet+"/size"+string(taille)+"/time"+string(time)+"/weigth"+sweigth+"/ReceiveMap_"+string(iteration)+"_"+string(time-dt)+".csv");
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
                weigth = weigths(i);
                disp("time : "+string(time)+" taille : "+string(taille)+" weigth : "+string(weigth));
                M_a = computeAverageMatrix('a_send',taille,time,weigth,maxiteration,dt);
                M_b = computeAverageMatrix('b_send',taille,time,weigth,maxiteration,dt);
                M_ab = computeAverageMatrix('ab_send',taille,time,weigth,maxiteration,dt);                
                fitness(i)= computeFitness(M_a,M_b,M_ab);
                i = i+1;
            end
        end
    end
    return fitness
endfunction

function[diagonal]=computeAverageDiagonal(initialisation_packet,taille,time,weigth,maxiteration,dt)
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
        disp("~/Loria2014/dnfsim2/statistiques/data/"+initialisation_packet+"/size"+string(taille)+"/time"+string(time)+"/weigth"+sweigth+"/ReceiveMap_"+string(iteration)+"_"+string(time-dt)+".csv");
        M = read_csv("~/Loria2014/dnfsim2/statistiques/data/"+initialisation_packet+"/size"+string(taille)+"/time"+string(time)+"/weigth"+sweigth+"/ReceiveMap_"+string(iteration)+"_"+string(time-dt)+".csv");
        disp(M);
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
                disp("time : "+string(time)+" taille : "+string(taille)+" weigth : "+string(weigth));
                d_a = computeAverageDiagonal('a_send',taille,time,weigth,maxiteration,dt);
                d_b = computeAverageDiagonal('b_send',taille,time,weigth,maxiteration,dt);
                d_ab = computeAverageDiagonal('ab_send',taille,time,weigth,maxiteration,dt);
                //disp(d_a);
                //disp(d_b);
                fitness(i)= computeFitness(d_a,d_b,d_ab);
                i = i+1;
            end
        end
    end
    return fitness
endfunction


dt = 0.1;
tailles = [9];//[9,10,19,20,29,30,39,40,49,50]
weigths = [0.5];//linspace(0.0,1.0,21);//[0.0,0.1,0.2,0.3,0.4,0.5,0.6,0.7,0.8,0.9];//[0.0,0.1,0.2,0.3,0.4,0.5,0.6,0.7,0.8,0.9]
times = [5,4];//[1,2,3,4,5,6,7,8,9,10]
epsilon = 0.000000001;

//plot(weigths,[computeAllDiagonalFitness(times,tailles,weigths,10,dt) computeAllDiagonalFitness(times,tailles,weigths,100,dt) computeAllDiagonalFitness(times,tailles,weigths,500,dt) computeAllDiagonalFitness(times,tailles,weigths,1000,dt)]);
//f10 = computeAllDiagonalFitness(times,tailles,weigths,10,dt);
//f100 = computeAllDiagonalFitness(times,tailles,weigths,100,dt);
//f500 = computeAllDiagonalFitness(times,tailles,weigths,500,dt);
//f1000 = computeAllDiagonalFitness(times,tailles,weigths,1000,dt);
//f5000 = computeAllDiagonalFitness(times,tailles,weigths,5000,dt);
//
//plot(weigths,f10,'r');
//plot(weigths, f100,'b');
//plot(weigths,f500,'g');
//plot(weigths,f1000,'cyan');
//plot(weigths,f5000,'yellow');
//
//xtitle( 'Critère d additivité sur diagonale', 'poids', 'fitness') ;
//legend('10 iterations','100 itérations','500 itérations','1000 itérations','5000 itérations");
//

f10 = computeAllDiagonalFitness(times,tailles,weigths,1,dt);
//f100 = computeAllDiagonalFitness(times,tailles,weigths,2,dt);


plot(weigths,f10);
plot(weigths, f100);


xtitle( 'Critère d additivité sur diagonale', 'poids', 'fitness') ;
legend('10 iterations');//,'100 itérations');



