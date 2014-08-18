clear
xdel();xdel();
exec('Utils.sci');
exec('libscilab/stixbox-master/loader.sce');
exec('libscilab/apifun_0.4.1/loader.sce');
exec('libscilab/distfun_0.8/loader.sce');
//exec('Bootstrap.sce');

Ns=[20];



taille = 9;
weight = 0.7;
time = 5;
maxiteration = 1000;
dt = 0.1;
confiance = 0.95
x= linspace(1,taille,taille);
//data = readData(initialisation_packet,taille,time,weight,maxiteration,dt);
//disp(data,'data');
//diags = dataToDiagonals(data);
//disp(diags,'diagonals');
//
//disp(ciboot(diags(1,:),mean,1,0.95,1000));
//disp(ciboot(diags(2,:),mean,1,0.95,1000));
//disp(ciboot(diags(3,:),mean,1,0.95,1000));


cmap = rainbowcolormap(32);
rand('seed',0)

for N=Ns
    initialisation_packet = 'a_send_'+string(N);
    matrices = dataToMatrices(initialisation_packet,taille,time,weight,maxiteration,dt);    
    diags = matricesToDiagonales(matrices); 
    index = size(cmap,'r') * rand(1,1);
    c = color(cmap(index,1)*255,cmap(index,2)*255,cmap(index,3)*255);   
    plot2d(x,variancesDiags(diags),style=[c]);
end

scf();

function[intervalles]=bootstrapDiagonales(diagonales,confiance,fun)//,moyennes)
    s = size(matrices);
    if length(s)==2 then
        nbMatrix = 1
    else
        nbMatrix = s(3);
    end
    taille = s(2);
    intervalles = zeros(taille,3);
    //maxiteration = size(data)(3);
    //disp(maxiteration,'maxiteration');
    for t=1:taille
        intervalles(t,:)=ciboot(diagonales(:,t),fun,1,confiance,1000);//moyennes(t)
       
    end
    return intervalles;
endfunction




rand('seed',0) // on reintialise le random pour avoir les mêmes couleurs qu'au dessus


for N=Ns
    initialisation_packet = 'a_send_'+string(N);
    matrices = dataToMatrices(initialisation_packet,taille,time,weight,maxiteration,dt);    
    diags = matricesToDiagonales(matrices);    
    //disp(diags,'diags');
    bootstarp = bootstrapDiagonales(diags,confiance,variance);
    //disp(bootstarp,'bootstarp');
    index = size(cmap,'r') * rand(1,1);
    c = color(cmap(index,1)*255,cmap(index,2)*255,cmap(index,3)*255);
    plot2d(x,bootstarp(:,1)',style=[c]);
    plot2d(x,bootstarp(:,2)',style=[c]);
    plot2d(x,bootstarp(:,3)',style=[c]);
    //plot2d(linspace(1,taille,taille),variancesDiags(diags));
end
