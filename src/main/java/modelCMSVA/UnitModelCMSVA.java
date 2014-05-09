/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.modelCMSVA;

import main.java.coordinates.NullCoordinateException;
import main.java.coordinates.Space;
import main.java.maps.Parameter;
import main.java.maps.Var;
import main.java.unitModel.UnitModel;



/**
 *
 * @author john
 */
public class UnitModelCMSVA extends UnitModel{
    public final static int MAIN = 0;
    public final static int TAU =1;
    public final static int BASELINE = 2;
    public final static int ALPHA = 3;

    public UnitModelCMSVA(){
            super();
    }

    public UnitModelCMSVA(Var dt, Space space,Parameter main, Parameter tau, Parameter baseline, Parameter alpha, Parameter... convolutions) {
            super(dt, space, convolutions);
            this.addParameters(main);
            this.addParameters(tau);
            this.addParameters(baseline);
            this.addParameters(alpha);
    }
   
   

    @Override
    public double compute() throws NullCoordinateException {
        //int taille = params.size()- tailleConvolutions -1;
        double sommeConvolution = 0;
        for(int i = ALPHA+1 ; i< params.size();i++){
            sommeConvolution = sommeConvolution + params.getIndex(i).getIndex(coord);
        }
        double x = compute2(params.getIndex(MAIN).getIndex(coord), 
                params.getIndex(TAU).getIndex(coord), 
                params.getIndex(BASELINE).getIndex(coord),
                params.getIndex(ALPHA).getIndex(coord),
                sommeConvolution);
        double res = x ;
        if(x>1)
            res=1;
        if(x<0)
            res=0;
        return res;
    }
    
    public double compute2(double main, double tau, double baseline, double alpha, double sommeConvolution){
        return main + (1/tau)*(-(main - baseline)+(1/alpha)*(sommeConvolution));
    }
    
}