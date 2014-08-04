exec('Utils.sci');

//plot(linspace(1,9,9),exec(computeAverageDiagonal("a_send",taille,time,weigth,maxiteration,dt));
//moy = computeAverageMatrix("a_send",taille,time,weigth,maxiteration,dt);
//var = computeVarianceMatrix(moy,"a_send",taille,time,weigth,maxiteration,dt);

//clf();
//xtitle('Spike reçu jusqu alors.'+' taille : '+string(taille)+' time : '+string(time)+ ' weigth : '+string(weigth), 'x', 'y','nombre de spikes reçus');
//set(gcf(), "color_map", jetcolormap(64));
//x = linspace(1,9,9);
//y = linspace(1,9,9);
//z = moy;
//plot3d1(x, y, z);
//surf(x,y,z);
//surf(linspace(1,9,9),linspace(1,9,9),var);

//moyd = computeAverageDiagonal("a_send",taille,time,weigth,maxiteration,dt);
//vard = computeVarianceDiagonal(moyd,"a_send",taille,time,weigth,maxiteration,dt);

//plot(linspace(1,9,9),vard)

//write_csv([linspace(1,9,9)',vard],"vardiag.csv",",",".");

ECART_TYPE = 0;
MOYENNE = 1;
VARIANCE = 2;
ECART_TYPE_AND_MOYENNE = 3;

function[]=goGirl(sender,taille,times,weigths,maxiteration,dt,doSingleView,doDiag,stats,ouputDir)
    clf();
    x = linspace(1,taille,taille);
    y = linspace(1,taille,taille);
    label ='';
    label2 = '';
    select stats
    case MOYENNE then
        label = 'Moyenne';
    case ECART_TYPE then
        label = 'Ecart_type';
    case VARIANCE then
        label = 'Variance';
    case ECART_TYPE_AND_MOYENNE then
        label = 'Ecart_type';
        label2 = 'Moyenne'
    end

    i = 1;
    for weigth=weigths
        for time=times
            if doSingleView==1 then
                clf();
            else
                subplot(size(weigths,'*'),size(times,'*'),i);
            end
            if doDiag==1 then
                xtitle(label+label2+' sender : '+sender+' taille : '+string(taille)+' time : '+string(time)+ ' weigth : '+string(weigth), 'x', label+label2+' des spikes reçus');
                select stats
                case MOYENNE then
                    z = computeAverageDiagonal(sender,taille,time,weigth,maxiteration,dt);
                    plot2d(x,z);
                case VARIANCE then
                    z = computeAverageDiagonal(sender,taille,time,weigth,maxiteration,dt);
                    z = computeVarianceDiagonal(z,sender,taille,time,weigth,maxiteration,dt);
                    plot2d(x,z);
                case ECART_TYPE then
                    z = computeAverageDiagonal(sender,taille,time,weigth,maxiteration,dt);
                    z = computeVarianceDiagonal(z,sender,taille,time,weigth,maxiteration,dt);
                    z = sqrt(z);
                    plot2d(x,z);
                case ECART_TYPE_AND_MOYENNE then                    
                    z = computeAverageDiagonal(sender,taille,time,weigth,maxiteration,dt);
                    plot2d(x,z,style=[color('red')]);
                    z = computeVarianceDiagonal(z,sender,taille,time,weigth,maxiteration,dt);
                    z = sqrt(z);     
                    plot2d(x,z);
                    legend(label2,label);               
                end                
            else
                xtitle(label+label2+' sender : '+sender+' taille : '+string(taille)+' time : '+string(time)+ ' weigth : '+string(weigth), 'x', 'y',label+label2+' des spikes reçus');
                set(gcf(), "color_map", jetcolormap(64));
                select stats
                case MOYENNE then
                    z = computeAverageMatrix(sender,taille,time,weigth,maxiteration,dt);
                case VARIANCE then
                    z = computeAverageMatrix(sender,taille,time,weigth,maxiteration,dt);
                    z = computeVarianceMatrix(z,sender,taille,time,weigth,maxiteration,dt);
                case ECART_TYPE then
                    z = computeAverageMatrix(sender,taille,time,weigth,maxiteration,dt);
                    z = computeVarianceMatrix(z,sender,taille,time,weigth,maxiteration,dt);
                    z = sqrt(z);
                case ECART_TYPE_AND_MOYENNE then
                    z = computeAverageDiagonal(sender,taille,time,weigth,maxiteration,dt);
                    plot3d1(x, y, z);
                    z = computeVarianceDiagonal(z,sender,taille,time,weigth,maxiteration,dt);
                    z = sqrt(z);
                end
                plot3d1(x, y, z);
            end


            if doSingleView==1 then
                t = outputDir+'/spike_data'+'_diag'+string(doDiag)+'_'+label+label2+'_init'+sender+'_taille'+string(taille)+'_time'+string(time)+'_weigth'+string(weigth);
                xs2ps(0,t +'.ps');
                xs2png(0,t +'.png');
                xs2pdf(0,t +'.pdf');
            end

            i=i+1;

        end
    end
    if doSingleView==1 then
    else
        t = outputDir+'/global_spike_data'+'_diag'+string(doDiag)+'_'+label+label2+'_init'+sender+'_taille'+string(taille);
        xs2ps(0,t+'.ps');
        xs2png(0,t+'.png');
        xs2pdf(0,t+'.pdf'); 
    end

endfunction


// 'tailles' 'times' 'weigths' maxiteration sender outputDir
args = sciargs();
taille = strtod(args(6))';
times = tokens(args(7),' ');
times= strtod(times)';
weigths = tokens(args(8),' ');
weigths = strtod(weigths)';
maxiteration = strtod(args(9));
sender = args(10);
outputDir = args(11);


b =   [1,0];
dt = 0.1;
for doSingleView=b
    for doDiag=b 
        goGirl(sender,taille,times,weigths,maxiteration,dt,doSingleView,doDiag,ECART_TYPE,outputDir);
        goGirl(sender,taille,times,weigths,maxiteration,dt,doSingleView,doDiag,VARIANCE,outputDir);
        goGirl(sender,taille,times,weigths,maxiteration,dt,doSingleView,doDiag,MOYENNE,outputDir);
    end
    goGirl(sender,taille,times,weigths,maxiteration,dt,doSingleView,1,ECART_TYPE_AND_MOYENNE,outputDir);
end

exit;
