clear
xdel();xdel();
exec('loader.sce');

Ns=[1,20,40,60,80];


args = sciargs();


taille = strtod(args(6));//9;
weight = strtod(args(7));//0.7;
time = strtod(args(8));//5;
maxiteration = strtod(args(9));//1000;
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
//disp(cmap);
scmap = size(cmap,'r');
rand('seed',0)

for N=Ns
    initialisation_packet = 'a_send_'+string(N);
    disp(initialisation_packet,'running')
    matrices = dataToMatrices(initialisation_packet,taille,time,weight,maxiteration,dt,0);    
    diags = matricesToDiagonales(matrices); 
    index =  scmap * rand(1,1);    
    if index < 1 then
        index = 1;
    end
    c = color(cmap(index,1)*255,cmap(index,2)*255,cmap(index,3)*255);   
    plot2d(x,variancesDiags(diags),style=[c]);
end
legend(string(Ns));
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

leg = string(zeros(1,3*size(Ns,'*')));

for i=1:size(Ns,'*')
    //N=Ns
    N = Ns(i);    
    initialisation_packet = 'a_send_'+string(N);
    disp(initialisation_packet,'running');
    leg((i-1) * 3 + 1)= 'inf '+ initialisation_packet;
    leg((i-1) * 3 + 2) = initialisation_packet;
    leg((i-1) * 3 + 3) = 'sup '+initialisation_packet;
    matrices = dataToMatrices(initialisation_packet,taille,time,weight,maxiteration,dt,0);    
    diags = matricesToDiagonales(matrices);    
    //disp(diags,'diags');
    bootstarp = bootstrapDiagonales(diags,confiance,variance);
    //disp(bootstarp,'bootstarp');
    index = size(cmap,'r') * rand(1,1);
    if index < 1 then
        index = 1;
    end
    c = color(cmap(index,1)*255,cmap(index,2)*255,cmap(index,3)*255);
    plot2d(x,bootstarp(:,1)',style=[c]);
    plot2d(x,bootstarp(:,2)',style=[c]);
    plot2d(x,bootstarp(:,3)',style=[c]);
    //plot2d(linspace(1,taille,taille),variancesDiags(diags));
end

legend(leg);
