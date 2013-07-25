/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lucasKanade;

import Jama.Matrix;
import coordinates.NullCoordinateException;
import coordinates.Space;
import maps.Parameter;
import maps.Var;
import unitModel.UnitModel;

/**
 *
 * @author ncarrara
 */
public class UnitModelLucasKanade extends UnitModel{
    private static int MAP = 0;
    private static int VOISINAGE = 1;
    private static int WEIGHTED = 2;
    private static int INTENSITY = 3;
    private static int WIDTH = 4;
    private static int WRAPPED = 5;
    
    private final FiltreLucasKanade filtreLucasKanade ;
    
    public UnitModelLucasKanade(Var dt,Space space, Parameter... parameters){
        super(dt, space, parameters);
        space.discreteProj(coord);
        boolean isWeighted = (params.get(WEIGHTED).get() == 1);
        boolean isWrapped = (params.get(WRAPPED).get() == 1);
        filtreLucasKanade = new FiltreLucasKanade(
                space,
                (int)params.get(VOISINAGE).get(),
                isWeighted,
                params.get(INTENSITY).get(),
                params.get(WIDTH).get(),
                isWrapped);
    }
    
    
    @Override
    public double compute() throws NullCoordinateException {
        Double[] tab = space.discreteProj(coord);
        Matrix V = filtreLucasKanade.getLocalImageFlowVector((int)Math.round(tab[Space.X]), (int)Math.round(tab[Space.Y]), params.get(MAP));
        return V.norm2();//Math.random();
    }
    
}
