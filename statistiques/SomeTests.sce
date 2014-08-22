clear
exec('Utils.sci');

N=20;



taille = 9;
weight = 0.7;
time = 5;
maxiteration = 3;
dt = 0.1;
initialisation_packet = 'a_send_'+string(N);




//------------ tests ----------------
data = dataToMatrices(initialisation_packet,taille,time,weight,maxiteration,dt);

disp('-------------------means---------------------');

//moy1 = mean(data,'m');
moy2 = meansMatrices(data);
moy3 = computeAverageMatrix(initialisation_packet,taille,time,weight,maxiteration,dt);

disp(moy2,'meansMatrices',moy3,'computeAverageMatrix');

disp('----------------------------------------');

moy4 = meansDiags(matricesToDiagonales(data));
moy5 = computeAverageDiagonal(initialisation_packet,taille,time,weight,maxiteration,dt);

disp(moy4,'meanDiag',moy5','computeAverageDiagonal');

disp('-------------------variance---------------------');

var2 = variancesMatrices(data);
var3 = computeVarianceMatrix(moy3,initialisation_packet,taille,time,weight,maxiteration,dt);
var1 = computeVarianceMatrix(moy2,initialisation_packet,taille,time,weight,maxiteration,dt);

disp(var2,'variancesMatrices',var3,'computeVarianceMatrix',var1,'computeVarianceMatrix with meansMatrice');

disp('----------------------------------------');

var4 = variancesDiags(matricesToDiagonales(data));
var5 = computeVarianceDiagonal(moy5,initialisation_packet,taille,time,weight,maxiteration,dt);
var6 = computeVarianceDiagonal(moy4',initialisation_packet,taille,time,weight,maxiteration,dt);

disp(var4,'variancesDiag',var5','computeVarianceDiagonal',var6','computeVariance with meansDiags');

