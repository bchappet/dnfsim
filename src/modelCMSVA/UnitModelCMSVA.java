/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelCMSVA;

import coordinates.NullCoordinateException;
import coordinates.Space;
import maps.Parameter;
import maps.Var;
import unitModel.UnitModel;



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
            sommeConvolution = sommeConvolution + params.get(i).get(coord);
        }
        double x = compute2(params.get(MAIN).get(coord), 
                params.get(TAU).get(coord), 
                params.get(BASELINE).get(coord),
                params.get(ALPHA).get(coord),
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