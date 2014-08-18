exec('Utils.sci');

N = 20;
time = 5;
initialisation_packet = 'a_send_'+string(N);//string(taille)+'_a_send_'+string(N);
maxiteration = 1000;
dt = 0.1;
confiance = 0.95
taille = 19;
weight = 0.7;

data = readData(initialisation_packet,taille,time,weight,maxiteration,dt);

diagonales = zeros(maxiteration,taille);

for t=1:taille
    diagonales(:,t)=data(t,t,:);
end

disp(diagonales);

plot2d(linspace(1,taille,taille),diagonales');
xtitle('valeurs des spikes recu sur la diagonale pour '+string(maxiteration)+' expériences');
