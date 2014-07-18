
here = pwd();

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



function[fitness] = computeAllFitness(time,taille,weigths,maxiteration,dt)
    disp("time : "+string(time)+" taille : "+string(taille)+' maxiteration : '+string(maxiteration));
    fitness = zeros(size(weigths));
    i = 1;
    while(i <= size(weigths,'c'))
        weigth = weigths(i);
        disp(" weigth : "+string(weigth));
        M_a = computeAverageMatrix('a_send',taille,time,weigth,maxiteration,dt);
        M_b = computeAverageMatrix('b_send',taille,time,weigth,maxiteration,dt);
        M_ab = computeAverageMatrix('ab_send',taille,time,weigth,maxiteration,dt);                
        fitness(i)= computeFitness(M_a,M_b,M_ab);
        i = i+1;
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
        M = read_csv(here+"/data/"+initialisation_packet+"/size"+string(taille)+"/time"+string(time)+"/weigth"+sweigth+"/ReceiveMap_"+string(iteration)+"_"+string(time-dt)+".csv");
        diag_M = diag(strtod(M));
        diagonal = diag_M + diagonal;
    end;
    diagonal = diagonal / maxiteration;
    return double(diagonal);
endfunction

//function[fitness] = computeAllDiagonalFitness(times,tailles,weigths,maxiteration,dt)
function[fitness] = computeAllDiagonalFitness(time,taille,weigths,maxiteration,dt)
    disp("time : "+string(time)+" taille : "+string(taille)+' maxiteration : '+string(maxiteration));
    fitness = zeros(size(weigths));
    i = 1;
    while(i <= size(weigths,'c'))
        weigth = weigths(i);
        disp(" weigth : "+string(weigth));
        d_a = computeAverageDiagonal('a_send',taille,time,weigth,maxiteration,dt);
        d_b = computeAverageDiagonal('b_send',taille,time,weigth,maxiteration,dt);
        d_ab = computeAverageDiagonal('ab_send',taille,time,weigth,maxiteration,dt);
        fitness(i)= computeFitness(d_a,d_b,d_ab);
        i = i+1;
    end
    return fitness
endfunction


function[fitness] = computeFitness(A,B,ABe)
    ABt = A + B;
    M = abs(ABt - ABe)./(ABt + ABe + epsilon);
    fitness = sum(M) / length(A);
    return fitness;
endfunction
x = pwd();
disp(x);

//dt = 0.1;
//tailles = [9];//[9,10,19,20,29,30,39,40,49,50]
//weigths = linspace(0.0,1.0,21);//[0.0,0.1,0.2,0.3,0.4,0.5,0.6,0.7,0.8,0.9];//[0.0,0.1,0.2,0.3,0.4,0.5,0.6,0.7,0.8,0.9]
//times = [1];//[1,2,3,4,5,6,7,8,9,10]
//epsilon = 0.000000001;
////f10 = computeAllDiagonalFitness(1,9,weigths,10,dt);
//// plot(weigths,f10);
////
//disp(size(tailles,'*'))
//disp(size(times,'*'));
//i = 1;
//for taille=tailles
//    for time=times
//       disp('ploting '+string(i)+' ...');    
//       subplot(size(tailles,'*'),size(times,'*'),i);
//       f10 = computeAllDiagonalFitness(time,taille,weigths,10,dt);
//       f100 = computeAllDiagonalFitness(time,taille,weigths,100,dt);
//       f500 = computeAllDiagonalFitness(time,taille,weigths,500,dt);
//       f1000 = computeAllDiagonalFitness(time,taille,weigths,1000,dt);
//       f5000 = computeAllDiagonalFitness(time,taille,weigths,5000,dt);
//       xtitle('Critère d additivité sur diagonale'+' taille : '+string(taille)+' time : '+string(time), 'poids', 'fitness');
//       plot(weigths,f10,'b');
//       plot(weigths,f100,'r');
//       plot(weigths,f500,'c');
//       plot(weigths,f1000,'black');
//       plot(weigths,f5000,'g');
//       legend(['10 iterations','100 itérations','500 itérations','1000 itérations','5000 itérations']);
//       i=i+1;
//   end
//end

dt = 0.1;
tailles = [9,19];//[9,10,19,20,29,30,39,40,49,50]
weigths = [0.0,0.7];//linspace(0.0,1.0,21);//[0.0,0.1,0.2,0.3,0.4,0.5,0.6,0.7,0.8,0.9];//[0.0,0.1,0.2,0.3,0.4,0.5,0.6,0.7,0.8,0.9]
times = [1,2,3];//[1,2,3,4,5,6,7,8,9,10]
epsilon = 0.000000001;

i = 1;
for taille=tailles
    for time=times
       disp('ploting '+string(i)+' ...');    
       subplot(size(tailles,'*'),size(times,'*'),i);
       f10 = computeAllFitness(time,taille,weigths,10,dt);
       f100 = computeAllFitness(time,taille,weigths,100,dt);
//       f500 = computeAllFitness(time,taille,weigths,500,dt);
//       f1000 = computeAllFitness(time,taille,weigths,1000,dt);
//       f5000 = computeAllFitness(time,taille,weigths,5000,dt);
       xtitle('Critère d additivité sur l ensemble du graphe'+' taille : '+string(taille)+' time : '+string(time), 'poids', 'fitness');
       plot(weigths,f10,'b');
       plot(weigths,f100,'r');
//       plot(weigths,f500,'c');
//       plot(weigths,f1000,'black');
//       plot(weigths,f5000,'g');
//       legend(['10 iterations','100 itérations','500 itérations','1000 itérations','5000 itérations']);
        legend(['10 iterations','100 itérations']);
       i=i+1;
   end
end





