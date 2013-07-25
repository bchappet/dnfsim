/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package input;

import coordinates.NullCoordinateException;
import coordinates.Space;
import maps.Parameter;
import maps.Var;
import unitModel.UnitModel;

/**
 * Unit model pour la lecture vidéo. 
 * Transforme un code RGB en luminosité.
 * @author ncarrara
 */
public class UnitModelInput extends UnitModel{
    private ReaderInput riv;
    
    
    public UnitModelInput(Var dt,Space space,ConfigurationInput dataVideo, Integer typeScaling, Integer typeReader){
        super(dt, space, new Parameter[]{});
        dataVideo.cps = (int)(1/dt.get());
        riv = new ReaderInput(dataVideo, typeScaling, typeReader, space);
    }

    @Override
    public double compute() throws NullCoordinateException {
        Double[] tab = space.discreteProj(coord);
//        System.out.println("compute UMInputVideo time.get: "+time.get());
        int codeRGB = riv.get(time.get(), (int)Math.round(tab[space.X]), (int)Math.round(tab[space.Y]));
        int red = (codeRGB >> 16) & 0xFF;
        int green = (codeRGB >> 8) & 0xFF;
        int blue = codeRGB & 0xFF;
       
        /* renvoi la luminosité du pixel */
        return (0.2126*red) + (0.7152*green) + (0.0722*blue);
    
    }
    @Override
    public void reset(){
        super.reset();
        riv.reset();
    }
    
}
