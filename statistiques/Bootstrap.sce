clear
xdel();xdel();
exec('Utils.sci');
//exec('stixbox-master/macros/ciboot.sci');
exec('libscilab/stixbox-master/loader.sce');
exec('libscilab/apifun_0.4.1/loader.sce');
exec('libscilab/distfun_0.8/loader.sce');


// retourne les couples (borne sup moyenne borne inf)
// @deprecated, utiliser la fonction dataToDiagonals et faire un ciboot sur les données
function[intervalles]=bootstrapDiagonale(data,maxiteration,confiance)//,moyennes)
    intervalles = zeros(taille,3);
    //maxiteration = size(data)(3);
    //disp(maxiteration,'maxiteration');
    for t=1:taille
        //disp(data(t,t,:));
        data2 = zeros(1,maxiteration);
        for i=1:maxiteration
            //disp(i,'i')
            //disp(data(t,t,i),'data(t,t,i)');
            data2(1,i)=data(t,t,i);
        end
         //disp(ciboot(data2,variance,1,0.95));
        intervalles(t,:)=ciboot(data2,variance,1,confiance);//moyennes(t)
       
    end
    return intervalles;
endfunction

//function[intervalles]=betterBootstrapDiagonals(data,confiance)//,moyennes)
//    intervalles = zeros(taille,3);
//    //maxiteration = size(data)(3);
//    //disp(maxiteration,'maxiteration');
//    for t=1:taille
//        //disp(data(t,t,:));
//        data2 = zeros(1,maxiteration);
//        for i=1:maxiteration
//            //disp(i,'i')
//            //disp(data(t,t,i),'data(t,t,i)');
//            data2(1,i)=data(t,t,i);
//        end
//         //disp(ciboot(data2,variance,1,0.95));
//        intervalles(t,:)=ciboot(data2,variance,1,confiance);//moyennes(t)
//       
//    end
//    return intervalles;
//endfunction

test = 1;
if test==1 then
    xdel();xdel();
    taille = 9;
    
    N = 20;
    
    weight = 0.7;
    time = 5;
    initialisation_packet = 'a_send_'+string(N);//string(taille)+'_a_send_'+string(N);
    maxiteration = 1000;
    dt = 0.1;
    confiance = 0.95
    
    data = dataToMatrices(initialisation_packet,taille,time,weight,maxiteration,dt);
    //disp(data);
    //disp(size(data),'size(data)')
    moy = computeAverageDiagonal(initialisation_packet,taille,time,weight,maxiteration,dt);
    var = computeVarianceDiagonal(moy,initialisation_packet,taille,time,weight,maxiteration,dt);
    //x=distfun_chi2rnd(3,20,1);
    //disp(x);
    i = bootstrapDiagonale(data,maxiteration,confiance);//,moy);
    disp(i,'i');
    disp(moy,'moy');
    disp(var,'var');
    disp(meansDiags(matricesToDiagonales(data)),'meansDiags');
    x = linspace(1,9,9);
    
    plot2d(x,i);
    legend('borne sup','variance','borne inf');
    xtitle("confiance : "+string(confiance*100)+"%");
    //plot2d(x,moy);
end
