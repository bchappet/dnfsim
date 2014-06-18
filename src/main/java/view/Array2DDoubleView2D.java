package main.java.view;

import java.math.BigDecimal;

import main.java.controler.ParameterControler;
import main.java.maps.Array2DDouble;

/**
 * Adapt a Array2DDouble to a View2D
 * @author benoit
 *
 */
public class Array2DDoubleView2D extends ParamViewAdapter {

	public Array2DDoubleView2D(ParameterControler param,ViewConfiguration vc) {
		super(param,vc);
	}

	@Override
	protected ParameterView constructView(ParameterControler paramControler,ViewConfiguration vc) {
		Array2DDouble param = (Array2DDouble) paramControler.getParam();
		View2D view = new View2D(paramControler.getName(), param.get2DArrayDouble(), vc.getColorMap());
		return view;
	}


	@Override
	public void updateView() {
		Array2DDouble param = (Array2DDouble) this.getParameterControler().getParam();
		View2D view =(View2D) this.getParamView();
		view.update(param.get2DArrayDouble());
	}



	
}
