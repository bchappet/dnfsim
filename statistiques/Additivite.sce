//Permet de tester la propriété d'addivité d'un reseau qui prend en paramètre un poid, une taille, et un ensemble de stimulis
// Pas assez générique pour tester autre chose que le PFModel (ce script est directement lié à la structure des sous dossiers de statistiques, sous 
//dossiers etant crées par la génération de données pour PFModel) mais extensible assez facilement en théorie.
// on doit se trouver sous le dossier statistiques pour lancer de script

exec('Utils.sci');

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
        d_a = computeAverageDiagonal(a,taille,time,weigth,maxiteration,dt,0);
        d_b = computeAverageDiagonal(b,taille,time,weigth,maxiteration,dt,0);
        d_ab = computeAverageDiagonal(ab,taille,time,weigth,maxiteration,dt,0);
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
        M_a = computeAverageMatrix(a,taille,time,weigth,maxiteration,dt,0);
        //        disp(M_a);
        M_b = computeAverageMatrix(b,taille,time,weigth,maxiteration,dt,0);
        //        disp(M_b);
        M_ab = computeAverageMatrix(ab,taille,time,weigth,maxiteration,dt,0);
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
// S est un tableau de matrice 2D (une matrice 3D donc). Il represente l'ensemble 
// des resultats suite aux différents stimulis. SSe represente la sommes expérimentale
// ie la matrice resultante d'un envoi de tous les stimulis simultanement
function[fitness] = fitNaire(S,SSe,eps,taille)
    //disp(S);
    taille = size(SSe,'r');
    SSt = zeros(taille,taille);
    for k=1:size(S)(3)
        SSt = SSt + S(:,:,k);
    end
    M = abs(SSt - SSe)./(SSt + SSe + eps);
    fitness = sum(M) / size(SSe,'*');
    return fitness;
endfunction
//
//function[fitness] = ErreurQuadratiqueNaire(S,SSe,eps,taille)
//    //disp(S);
//    taille = size(SSe,'r');
//    SSt = zeros(taille,taille);
//    for k=1:size(S)(3)
//        SSt = SSt + S(:,:,k);
//    end
//    M = abs(SSt - SSe)./(SSt + SSe + eps);
//    fitness = sum(M) / size(SSe,'*');
//    return fitness;
//endfunction




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
            s(:,:,k)= computeAverageMatrix(sd(k),taille,time,weigth,maxiteration,dt,0);
            //disp( s(:,:,k));
        end
        ss = computeAverageMatrix(ssd,taille,time,weigth,maxiteration,dt,0);    
        //        disp('s : ' );
        //        disp(s);
        //        disp('ss : '); 
        //        disp(ss);
        fitness(i)= fitNaire(s,ss,eps,taille);
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

NO_TEST = 0;
TEST_DIFF_BINAIRE_NAIRE = 1;
TEST_AVERAGE_MATRIX_FILES = 2;
TEST_FITNESS_NAIRE = 3;


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


select testing    
case NO_TEST then    

    //------------------------------------------------------------------------------
    //---------------------- plotting ----------------------------------------------
    //------------------------------------------------------------------------------

    cmap = rainbowcolormap(32);//autumncolormap(32);
    CSV_M = string(zeros(size(iterations,'*')*size(weigths,'*')*size(times,'*')*size(tailles,'*'),5));
    for taille=tailles
        for time=times
            subplot(size(tailles,'*'),size(times,'*'),i);
            xtitle('Critère d additivité sur l ensemble du graphe'+' taille : '+string(taille)+' time : '+string(time), 'poids', 'fitness');
            nbMaxIterations = size(iterations,'*');
            fitnesses = zeros(nbMaxIterations,size(weigths,'*'));
            for k=1:nbMaxIterations
                iteration = iterations(k);
                fitnesses(k,:) = allFitNaire(time,taille,weigths,iteration,dt,s,ss,epsilon);
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

    //------------------------------------------------------------------------------
    //----------------------  csv --------------------------------------------------
    //------------------------------------------------------------------------------

    arg = strsubst('tailles_'+args(6)+'_times_'+args(7)+'_weigths_'+args(8)+'_iterations_'+args(9),' ',',');
    write_csv(CSV_M,'csv_stats/additiviteFitness_'+arg+'.csv',",",".");

case TEST_DIFF_BINAIRE_NAIRE then

    //------------------------------------------------------------------------------
    //---------------------- testing -----------------------------------------------
    //------------------------------------------------------------------------------

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
            //disp(A);
            B = rand(t,t);
            //disp(B);
            ABe = rand(t,t);
            ABt = A+B;
            s = zeros(t,t,2);
            s(:,:,1)=A;
            s(:,:,2)=B;
            be = fitBinaire(A,B,ABe,epsilon);
            ne = fitNaire(s,ABe,epsilon,t);
            bt = fitBinaire(A,B,ABt,epsilon );
            nt = fitNaire(s,ABt,epsilon,t);
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
    exit;

case TEST_AVERAGE_MATRIX_FILES then
    
    disp('testing averageMatrixFiles function ...');
    a = here + '/test/a.csv';
    files = [a,a,a,a,a];
    Me = averageMatrixFiles(files,3);
    disp(Me,'Me');
    Mt = [[0,1,2];[3,4,5];[6,7,8]];
    disp(Mt,'Mt')
    disp(string(Me==Mt),'test validé ?');

    files = [a, here + '/test/b.csv'];
    Me = averageMatrixFiles(files,3);
    disp(Me,'Me');
    Mt = [[0.5,1.5,2.5];[3.5,4.5,5.5];[6.5,7.5,8.5]];
    disp(Mt,'Mt')
    disp(string(Me==Mt),'test validé ?');
    exit;
    
case TEST_FITNESS_NAIRE then
    
    disp('testing fitness naire ...")
    S1=[[1,1,1];[1,1,1];[1,1,1]];
    disp(S1,'S1');
    S2=[[1,1,1];[1,1,1];[1,1,1]];
    disp(S2,'S2');
    SS =[[2,2,2];[2,2,2];[2,2,2]];
    disp(SS,'SS');
    s = zeros(3,3,2);
    s(:,:,1)=S1;
    s(:,:,2)=S2;
    f = fitNaire(s,SS,0,3)
    disp(f,'f');
    disp(f == 0,'Test validé ?');
    
    S1=[[1,1,1];[1,1,1];[1,1,1]];
    disp(S1,'S1');
    S2=[[0,0,0];[0,0,0];[0,0,0]];
    disp(S2,'S2');
    SS =[[2,2,2];[2,2,2];[2,2,2]];
    disp(SS,'SS');
    s = zeros(3,3,2);
    s(:,:,1)=S1;
    s(:,:,2)=S2;
    f = fitNaire(s,SS,0,3)
    disp(f,'f');
    disp(f == 1/3,'Test validé ?');

    exit;
end





