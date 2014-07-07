/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.network.view;

import main.java.controler.ParameterControler;
import main.java.view.ParamViewAdapter;
import main.java.view.ParameterView;
import main.java.view.ViewConfiguration;
import main.java.view.ViewFactory;

/**
 * (inspire toi de MapView2DAdapter) La principale tache de cette classe est de
 * passer les bon parametres du parametre vers la vue à la construction et à
 * l'update de la vue
 *
 * @author CARRARA Nicolas
 */
public class GraphGraphViewAdapter extends ParamViewAdapter {

    
    public GraphGraphViewAdapter(ParameterControler paramControler,ViewFactory vf) {
        super(paramControler,vf);
    }

    @Override
    protected ParameterView constructView(ViewConfiguration vc,ViewFactory vf) {

        return new GraphView(
                getParameterControler().getName(),
                vf,
                ((GraphControler) getParameterControler()).getSpreadingGraphRepresentation());
    }

    @Override
    public void updateView() {
        ((GraphView)getParamView()).update(((GraphControler) getParameterControler()).getSpreadingGraphRepresentation());
    }

//    public MapView2DAdapter(ParameterControler paramControler,
//            ViewConfiguration vc) {
//        super(paramControler, vc);
//    }
//
//    @Override
//    protected ParameterView constructView(ViewConfiguration vc) {
//        return new View2D(getParameterControler().getName(),
//                ((MapControler) getParameterControler()).getArray(), vc.getColorMap());
//    }
//
//    @Override
//    public void updateView() {
//        ((View2D) getParamView()).update(
//                ((MapControler) getParameterControler()).getArray());
//    }
}
