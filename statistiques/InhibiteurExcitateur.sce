exec('Utils.sci');
xdel();xdel();

w_inhib = 0.5;
w_excit = 0.7;
t_inhib = 3;
t_excit = 5;

//computeAverageMatrix(initialisation_packet,taille,time,weigth,maxiteration,dt)
inhib = computeAverageDiagonal('a_send',9,t_inhib,w_inhib,1000,0.1);
excit = computeAverageDiagonal('a_send',9,t_excit,w_excit,1000,0.1);

x = linspace(1,9,9);

clf();
plot2d(x, inhib,style=[color('blue')]);
plot2d(x, 4* excit,style=[color('red')]);
plot2d(x, 4*excit-inhib);
//legend('inhibiteur '+string(w_inhib)+' '+string(t_inhib)+'s','excitateur '+string(w_excit)+' '+string(t_excit)+'s','difference');

scf();
y = x;
inhib = computeAverageMatrix('a_send',9,t_inhib,w_inhib,1000,0.1);
excit = computeAverageMatrix('a_send',9,t_excit,w_excit,1000,0.1);
set(gcf(), "color_map", jetcolormap(64));
plot3d1(x, y, 4*excit-inhib);

