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
public class FocusUnitModelCMSVA extends UnitModel{
//focus.addParameters(new Leaf(focus), pTau, pBaseline, palpha, cafti, clftf);
	public final static int FOCUS = 0;
	public final static int TAU =1;
	public final static int BASELINE = 2;
	public final static int ALPHA = 3;
	public final static int CONV_AFF = 4;
        public final static int CONV_LAT = 5;
        
        public FocusUnitModelCMSVA(){
		super();
	}

	public FocusUnitModelCMSVA(Var dt, Space space, Parameter... parameters) {
		super(dt, space, parameters);
	}

    @Override
    public double compute() throws NullCoordinateException {
        double x = compute2(params.get(FOCUS).get(coord), 
                params.get(TAU).get(coord), 
                params.get(BASELINE).get(coord),
                params.get(ALPHA).get(coord),
                params.get(CONV_AFF).get(coord),
                params.get(CONV_LAT).get(coord));
        double res = x ;
        if(x>1)
            res=1;
        if(x<0)
            res=0;
        return res;
    }
    
    public double compute2(double focus, double tau, double baseline, double alpha, double conv_aff, double conv_lat){
        return focus + (1/tau)*(-(focus - baseline)+(1/alpha)*(conv_aff+conv_lat));
    }
    
}
