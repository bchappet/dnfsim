here = pwd();

function[SOMMES]=computeAverageMatrix(initialisation_packet,taille,time,weigth,maxiteration,dt)
    SOMMES = zeros(taille,taille);
    // on lit le dernier fichier de chaque iteration (time-dt), on sommme les valeurs et on 
    // moyenne à la fin, one xtrait la courbe à la toute fin
    //disp(maxiteration)
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


// tailles times weigths iterations
args = sciargs();
tailles = tokens(args(6),' ');
tailles = strtod(tailles)';
times = tokens(args(7),' ');
times= strtod(times)';
weigths = tokens(args(8),' ');
weigths = strtod(weigths)';
iterations = tokens(args(9),' ');
iterations = strtod(iterations);

//disp('tailles : '+string(tailles));
//disp('times : '+string(times));
//disp('weigths : '+string(weigths));
//disp('iterations : '+string(iterations));

dt = 0.1;
epsilon = 0.000000001;

i = 1;
j = 1;





cmap = autumncolormap(32);

maxIteration = max(iterations);
disp(size(iterations,'*'));

CSV_M = string(zeros(size(iterations,'*')*size(weigths,'*')*size(times,'*')*size(tailles,'*'),5));
for taille=tailles
    for time=times
        subplot(size(tailles,'*'),size(times,'*'),i);
        xtitle('Critère d additivité sur l ensemble du graphe'+' taille : '+string(taille)+' time : '+string(time), 'poids', 'fitness');
        nbMaxIterations = size(iterations,'*');
        fitnesses = zeros(nbMaxIterations,size(weigths,'*')); 
        //disp(fitnesses(2));
        for k=1:nbMaxIterations
            iteration = iterations(k);
            //disp(computeAllFitness(time,taille,weigths,iteration,dt));
            fitnesses(k,:) = computeAllFitness(time,taille,weigths,iteration,dt);
            index = int(size(cmap,'r') * (k/size(iterations,'*')));
            plot2d(weigths,fitnesses(k,:),style=[color(cmap(index,1)*255,cmap(index,2)*255,cmap(index,3)*255)]);
        end

        legend([string(iterations)]+' iterations');
        i=i+1;
        for w=1:size(weigths,'*')
            for k=1:nbMaxIterations
                // iteration poid times taille fitness 
                CSV_M(j,:)=[string(iterations(k)),string(weigths(w)),string(time),string(taille),string(fitnesses(k,w))];
                j = j + 1;
            end
            // iteration poid times taille fitness 
            //          CSV_M(j,:)=["10",string(weigths(w)),string(time),string(taille),string(f10(w))];
            //          j = j + 1;
            //          CSV_M(j,:)=["100",string(weigths(w)),string(time),string(taille),string(f100(w))];
            //          j = j + 1;
            //          CSV_M(j,:)=["500",string(weigths(w)),string(time),string(taille),string(f500(w))];
            //          j = j + 1;
            //          CSV_M(j,:)=["1000",string(weigths(w)),string(time),string(taille),string(f1000(w))];
            //          j = j + 1;
            //          CSV_M(j,:)=["5000",string(weigths(w)),string(time),string(taille),string(f5000(w))];
            //          j = j + 1;
        end
    end
end

arg = strsubst('tailles_'+args(6)+'_times_'+args(7)+'_weigths_'+args(8)+'_iterations_'+args(9),' ',',');

write_csv(CSV_M,'csv_stats/additiviteFitness_'+arg+'.csv',",",".");
//exit




