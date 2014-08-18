here = pwd();

// extrait les diagonales d'un ensemble de matrices carrées
// @pré-condition : matrices est une matrice de taille TxTxN où T est 
// la taille de la ligne/colonne et N est le nombre de matrices representant les
// données.
// @post-condition : retourne diagonals, une matrice de taille NxT
function[diagonals] = matricesToDiagonales(matrices)
    //disp(size(data),'size(data)');
    s = size(matrices);
    if length(s)==2 then
        nbMatrix = 1
    else
        nbMatrix = s(3);
    end
    taille = s(2);
    diagonals = zeros(nbMatrix,taille);
    //disp(diagonals,'diagonals');
    for m=1:nbMatrix         
        //disp(diag(data(:,:,m))');
        diagonals(m,:)=diag(matrices(:,:,m))';
    end
    return diagonals
endfunction


// recupère toutes les matrices de différentes itérations dans un tableau sous le dossier data 
// (doit suivre l'arborescence des sous dossier générés par le fichier generateData.py)
// @return matrices,une matrice de taille TxTxN où T=taille et N=maxiteration
function[matrices]= dataToMatrices(initialisation_packet,taille,time,weigth,maxiteration,dt)
    matrices = zeros(taille,taille,maxiteration);
    //disp(size(M));
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
        matrice = read_csv(here+"/data/"+initialisation_packet+"/size"+string(taille)+"/time"+string(time)+"/weigth"+sweigth+"/ReceiveMap_"+string(iteration)+"_"+string(time-dt)+".csv");
        
        matrice =  strtod(matrice);
        //disp(size(matrice));
        matrices(:,:,iteration+1) = matrice;
    end
    return matrices;
endfunction

// recupère toutes les matrices dans une serie de fichiers files  
// @return matrices,une matrice de taille TxTxN où T=taille et N=maxiteration
function[matrices]=filesToMatrices(files,taille)
    nbMatrices = size(files,'*');
    matrices = zeros(taille,taille,nbMatrices);    
    for m=1:nbMatrices
        matrice = read_csv(file);
        matrices(:,:,m) = strtod(matrice);
    end;
    return matrices;
endfunction

// moyenne point par point de N matrice carré de taille TxT
//@ pré-condition : matrices est une matrice de taille TxTxN 
//@ return une matrice de taille TxT
function[means] = meansMatrices(matrices)
    s = size(matrices);    
    taille = s(2);
    means = zeros(taille,taille);
    for i=1:taille
        for j=1:taille
            means(i,j)=mean(matrices(i,j,:));
        end
    end
    return means;
endfunction

// moyenne point par point de N diagonal de taille T
//@ pré-condition : diags est une matrice de taille NxT
//@ return un vecteur de taille T
function[means] = meansDiags(diags)
   means = mean(diags,'r');
   return means;
endfunction

// variance point par point de N matrice carré de taille TxT
//@ pré-condition : matrices est une matrice de taille TxTxN 
//@ return une matrice de taille TxT
function[variances] = variancesMatrices(matrices)
    s = size(matrices);    
    taille = s(2);
    variances = zeros(taille,taille);
    for i=1:taille
        for j=1:taille
            variances(i,j)=variance(matrices(i,j,:));
        end
    end
    return variances;
endfunction

// variance point par point de N diagonal de taille T
//@ pré-condition : diags est une matrice de taille NxT
//@ return un vecteur de taille T
function[variances] = variancesDiags(diags)
   variances = variance(diags,'r');
   return variances;
endfunction














//@deprecated@deprecated@deprecated@deprecated@deprecated@deprecated@deprecated
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// les fonctions suivantes sont @deprecated
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------


// j'ai quand même changer le corps de toutes les fonctions suivantes pour corriger
// les bugs. Bien entendu les anciens fichiers ne sont plus optimaux mais il fonctionne
// bien quand meme (je fais ça pour eviter d'avoir à changer les anciens fichiers afin
// d'éviter des bugs qu'on aurait pas eu avant)



function[diagonal]=computeAverageDiagonal(initialisation_packet,taille,time,weigth,maxiteration,dt)
    diagonal = meansDiags(matricesToDiagonales(dataToMatrices(initialisation_packet,taille,time,weigth,maxiteration,dt)));
endfunction

function[SOMMES]=computeAverageMatrix(initialisation_packet,taille,time,weigth,maxiteration,dt)
    SOMMES = meansMatrices(dataToMatrices(initialisation_packet,taille,time,weigth,maxiteration,dt));
endfunction

function[VARIANCES]=computeVarianceMatrix(moyennes,initialisation_packet,taille,time,weigth,maxiteration,dt)
    VARIANCES = variancesMatrices(dataToMatrices(initialisation_packet,taille,time,weigth,maxiteration,dt));
endfunction

function[VARIANCES]=computeVarianceDiagonal(moyennes,initialisation_packet,taille,time,weigth,maxiteration,dt)
    VARIANCES = variancesDiags(matricesToDiagonales(dataToMatrices(initialisation_packet,taille,time,weigth,maxiteration,dt)));
endfunction

function[SOMMES]=averageMatrixFiles(files,taille)
    SOMMES = meansMatrices(filesToMatrices(files,taille));
endfunction

function[diagonal]=getDiag(M)
    diagonal = diag(M);
    return diagonal;
endfunction





//function[diagonal]=computeAverageDiagonal(initialisation_packet,taille,time,weigth,maxiteration,dt)
//    //disp('okay : '+string(taille));
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
//        diag_M = getDiag(strtod(M));
//        diagonal = diag_M + diagonal;
//    end;
//    diagonal = diagonal / maxiteration;
//    return double(diagonal);
//endfunction
//
//
//function[SOMMES]=computeAverageMatrix(initialisation_packet,taille,time,weigth,maxiteration,dt)
//    SOMMES = zeros(taille,taille);
//    // on lit le dernier fichier de chaque iteration (time-dt), on sommme les valeurs et on 
//    // moyenne à la fin, one xtrait la courbe à la toute fin
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
//        M = strtod(M);
//        SOMMES = SOMMES + M;
//    end;
//    SOMMES = SOMMES / maxiteration;
//    return double(SOMMES);
//endfunction
//
//function[VARIANCES]=computeVarianceMatrix(moyennes,initialisation_packet,taille,time,weigth,maxiteration,dt)
//    VARIANCES = zeros(taille,taille);
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
//        M =  strtod(M);
//        diffM = M - moyennes;
//        //disp("diffM : "+string(diffM));
//        VARIANCES = VARIANCES + diffM .* diffM;
//        //disp("V : "+string(VARIANCES));
//    end;
//    //disp(VARIANCES)
//    VARIANCES = VARIANCES ./ maxiteration;
//    //disp(VARIANCES)
//    return double(VARIANCES);
//endfunction
//
//function[VARIANCES]=computeVarianceDiagonal(moyennes,initialisation_packet,taille,time,weigth,maxiteration,dt)
//    //disp('okay : '+string(taille));
//    VARIANCES = zeros(taille);
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
//        M =  getDiag(strtod(M));
//        diffM = M - moyennes;
//        //disp("diffM : "+string(diffM));
//        VARIANCES = VARIANCES + diffM .* diffM;
//        //disp("V : "+string(VARIANCES));
//    end;
//    //disp(VARIANCES)
//    VARIANCES = VARIANCES ./ maxiteration;
//    //disp(VARIANCES)
//    return double(VARIANCES);
//endfunction
//
//// on l'utilise car la fonction native diag nous produit des bugs
//function[diagonal]=getDiag(M)
//    //diagonal = diag(M);
//    taille = sqrt(size(M,'*'));
//    diagonal = zeros(taille);
//    for i=1:taille
//        diagonal(i)=M(i,i);
//    end
//    return diagonal;
//endfunction
//
//function[SOMMES]=averageMatrixFiles(files,taille)
//    SOMMES = zeros(taille,taille);
//    for file=files
//        M = read_csv(file);
//        M = strtod(M);
//        SOMMES = SOMMES + M;
//    end;
//    SOMMES = SOMMES / size(files,'*');
//    return double(SOMMES);
//endfunction

