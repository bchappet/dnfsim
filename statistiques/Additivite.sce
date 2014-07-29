//Permet de tester la propriété d'addivité d'un reseau qui prend en paramètre un poid, une taille, et un ensemble de stimulis
// Pas assez générique pour tester autre chose que le PFModel (ce script est directement lié à la structure des sous dossiers de statistiques, sous 
//dossiers etant crées par la génération de données pour PFModel) mais extensible assez facilement en théorie.
// on doit se trouver sous le dossier statistiques pour lancer de script

here = pwd();

//------------------------------------------------------------------------------
//--------------------------calcul des données en moyenne-----------------------
//------------------------------------------------------------------------------

function[SOMMES]=averageMatrix(initialisation_packet,taille,time,weigth,maxiteration,dt)
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

function[diagonal]=averageDiagonal(initialisation_packet,taille,time,weigth,maxiteration,dt)
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

//------------------------------------------------------------------------------
//--------------------------additivité binaire----------------------------------
//------------------------------------------------------------------------------




function[fitness] = allDiagonalFitBinaire(time,taille,weigths,maxiteration,dt,a,b,ab,eps)
    //disp("time : "+string(time)+" taille : "+string(taille)+' maxiteration : '+string(maxiteration));
    fitness = zeros(size(weigths));
    i = 1;
    while(i <= size(weigths,'c'))

        weigth = weigths(i);
        //disp(" weigth : "+string(weigth));
        d_a = averageDiagonal(a,taille,time,weigth,maxiteration,dt);
        d_b = averageDiagonal(b,taille,time,weigth,maxiteration,dt);
        d_ab = averageDiagonal(ab,taille,time,weigth,maxiteration,dt);
        fitness(i)= fitBinaire(d_a,d_b,d_ab,eps);
        i = i+1;
    end
    return fitness
endfunction
function[fitness] = allFitBinaire(time,taille,weigths,maxiteration,dt,a,b,ab,eps)
    //disp("time : "+string(time)+" taille : "+string(taille)+' maxiteration : '+string(maxiteration));
//    disp("------------- allFitBinaire ---------------")
    fitness = zeros(size(weigths));
    fitnessB = zeros(size(weigths));
    i = 1;
    while(i <= size(weigths,'c'))
//        disp('weigth : '+string(weigths(i)));
        weigth = weigths(i);
        //disp(" weigth : "+string(weigth));
        M_a = averageMatrix(a,taille,time,weigth,maxiteration,dt);
//        disp(M_a);
        M_b = averageMatrix(b,taille,time,weigth,maxiteration,dt);
//        disp(M_b);
        M_ab = averageMatrix(ab,taille,time,weigth,maxiteration,dt);
//        disp('M_ab :');
//        disp(M_ab);             
        fitness(i)= fitBinaire(M_a,M_b,M_ab,eps);//fitBinaire(M_a,M_b,M_ab,eps); ça donne les memes results du coup, donc le prob devrait
        // venir de la diff entre fitNaire et fitBinaire, pour le test nous dit que les fonctions sont équilvalents ...
        // fitness(i)= computeFitnessNaire([M_a,M_b],M_ab); // ok la différence ne vient pas QUE de FitnessNaire/FintessBinaire
        //fitnessB(i)= fitBinaire(M_a,M_b,M_ab,eps);
        i = i+1;
    end
    //    disp('real binaire fitness : ');
    //    disp(fitnessB);
    //    disp('fitness : ');
    //    disp(fitness);
//    disp("-------------------------------------------");
    return fitness
endfunction

function[fitness] = fitBinaire(A,B,ABe,eps)
    ABt = A + B;
    M = abs(ABt - ABe)./(ABt + ABe + eps);
    fitness = sum(M) / size(A,'*');
    return fitness;
endfunction


//------------------------------------------------------------------------------
//------------------------------additivité naire--------------------------------
//------------------------------------------------------------------------------
// S est un tableau de matrice 2D (une matrice 3D donc). Il reprensente l'ensemble 
// des resultats suite aux différents stimulis. SSe represente la sommes expérimentale
// ie la matrice resultante d'un envoi de tous les stimulis simultanement
function[fitness] = fitNaire(S,SSe,eps)
    taille = size(SSe,'r');
    SSt = zeros(taille,taille);
    for k=1:size(S)(3)
        SSt = SSt + S(:,:,k);
    end
    M = abs(SSt - SSe)./(SSt + SSe + eps);
    fitness = sum(M) / size(SSe,'*');
    return fitness;
endfunction


// sd = tableau de nom de dossier des différents stimulis
// ssd = nom du dossier qui represente l'envoie simultané des stimulis
function[fitness] = allFitNaire(time,taille,weigths,maxiteration,dt,sd,ssd,eps)
    //disp("time : "+string(time)+" taille : "+string(taille)+' maxiteration : '+string(maxiteration));
    //disp("------------- allFitNaire ---------------")
    fitness = zeros(size(weigths));
    i = 1;
    //disp(size(sd,'*'));
    while(i <= size(weigths,'c'))
        //disp("weights : "+string(weigths(i)));
        weigth = weigths(i);
        //disp(" weigth : "+string(weigth));
        sdsize = size(sd,'*');
        s = zeros(taille,taille,sdsize);
        for k=1:sdsize            
            s(:,:,k)= averageMatrix(sd(k),taille,time,weigth,maxiteration,dt);
            //disp( s(:,:,k));
        end
        ss = averageMatrix(ssd,taille,time,weigth,maxiteration,dt);    
//        disp('s : ' );
//        disp(s);
//        disp('ss : '); 
//        disp(ss);
        fitness(i)= fitNaire(s,ss,eps);
        i = i+1;
    end
    //disp('fitness : ');
    //disp(fitness);
    //disp("-----------------------------------------");
    return fitness
endfunction




//------------------------------------------------------------------------------
//---------------------- testing, plotting and csv -----------------------------
//------------------------------------------------------------------------------

epsilon = 0.000000001;
dt = 0.1;
i = 1;
j = 1;

// 'tailles' 'times' 'weigths' 'iterations' sommesStimulis 'stimulis1 stimulis2 ...' testing
args = sciargs();
tailles = tokens(args(6),' ');
tailles = strtod(tailles)';
times = tokens(args(7),' ');
times= strtod(times)';
weigths = tokens(args(8),' ');
weigths = strtod(weigths)';
iterations = tokens(args(9),' ');
iterations = strtod(iterations);
s = tokens(args(10));
ss = args(11);
testing = strtod(args(12));

if testing == 0 then
    cmap = rainbowcolormap(32);//autumncolormap(32);
    CSV_M = string(zeros(size(iterations,'*')*size(weigths,'*')*size(times,'*')*size(tailles,'*'),5));
    for taille=tailles
        for time=times
            subplot(size(tailles,'*'),size(times,'*'),i);
            xtitle('Critère d additivité sur l ensemble du graphe'+' taille : '+string(taille)+' time : '+string(time), 'poids', 'fitness');
            nbMaxIterations = size(iterations,'*');
            fitnesses = zeros(nbMaxIterations,size(weigths,'*'));
//            fitnesses2 = zeros(nbMaxIterations,size(weigths,'*'));
            //disp(fitnesses(2));
            for k=1:nbMaxIterations
                iteration = iterations(k);
//                fitnesses(k,:) = allFitBinaire(time,taille,weigths,iteration,dt,'a_send','b_send','ab_send',epsilon);
                fitnesses(k,:) = allFitNaire(time,taille,weigths,iteration,dt,s,ss,epsilon);
//                if fitnesses2(k,:)~=fitnesses(k,:) then
//                    disp("There is an error in the code (allFitBinaire and allFitNaire are differents)");
//                    disp(fitnesses(k,:));
//                    disp(fitnesses2(k,:));
//                    exit();
//                end
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
            end
        end
    end

    arg = strsubst('tailles_'+args(6)+'_times_'+args(7)+'_weigths_'+args(8)+'_iterations_'+args(9),' ',',');

    write_csv(CSV_M,'csv_stats/additiviteFitness_'+arg+'.csv',",",".");

//    disp(fitnesses2==fitnesses);
//    disp(fitnesses);
//    disp(fitnesses2);    
else 
    disp("testing fitBinaire et fitNaire...");
    testOk = %T;
    tmax = 100;
    repetmax =10;
    t = 1;
    while(testOk == %T & t <= tmax)
        disp("--------- taille "+string(t)+"----------");
        repet = 0;
        while(testOk==%T & repet < repetmax)
            A = rand(t,t);
            B = rand(t,t);
            ABe = rand(t,t);
            ABt = A+B;
            s = zeros(t,t,2);
            s(:,:,1)=A;
            s(:,:,2)=B;
            be = fitBinaire(A,B,ABe,epsilon);
            ne = fitNaire(s,ABe,epsilon);
            bt = fitBinaire(A,B,ABt,epsilon);
            nt = fitNaire(s,ABt,epsilon);
            testOk = testOk & (be == ne) & (be > 0) & (ne >0) & (bt == nt) & (bt == 0) & (nt ==0);
            repet = repet + 1;
            if testOk == %F then
                disp('error');
                disp(A,'A :');
                disp(B,'B :');
                disp(s,'s : ');
                disp(be,'be :');
                disp(ne,'ne :');
                disp(bt,'bt :');
                disp(nt,'nt :');
                exit;
            else
                //disp('OK');
            end
        end
        t = t + 1;
    end
    disp("test validé ? "+string(testOk));

    disp("testing allFitBinaire & allFitNaire ...");
    exit;
end





