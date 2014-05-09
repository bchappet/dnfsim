/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.lucasKanade;

import Jama.Matrix;
import main.java.coordinates.NullCoordinateException;
import main.java.coordinates.Space;
import main.java.maps.Parameter;
import main.java.maps.Var;
import main.java.unitModel.UnitModel;

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
        boolean isWeighted = (params.getIndex(WEIGHTED).get() == 1);
        boolean isWrapped = (params.getIndex(WRAPPED).get() == 1);
        filtreLucasKanade = new FiltreLucasKanade(
                space,
                (int)params.getIndex(VOISINAGE).get(),
                isWeighted,
                params.getIndex(INTENSITY).get(),
                params.getIndex(WIDTH).get(),
                isWrapped);
    }
    
    
    @Override
    public double compute() throws NullCoordinateException {
        Double[] tab = space.discreteProj(coord);
        Matrix V = filtreLucasKanade.getLocalImageFlowVector((int)Math.round(tab[Space.X]), (int)Math.round(tab[Space.Y]), params.getIndex(MAP));
        return V.norm2();
    }
    
}
