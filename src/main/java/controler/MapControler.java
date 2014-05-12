package main.java.controler;

import java.math.BigDecimal;
import java.util.List;

import main.java.maps.Map;
import main.java.maps.MatrixDouble2D;
import main.java.space.Space;
import main.java.view.MapViewAdapter;
import main.java.view.ParamViewAdapter;
import main.java.view.ParameterView;

public class MapControler extends ComputableControler {

	public MapControler(Map param) {
		super(param);
	}
	
	public String displayText() {
		if(getParam() instanceof MatrixDouble2D){
			return getParam().toString();
		}else{
			return getParam().toString();
		}
	}


	@Override
	protected ParamViewAdapter createParamViewAdapter(ParameterView param) {
		return new MapViewAdapter(this,param);
	}

	public Space getSpace() {
		return ((Map)getParam()).getSpace();
	}

	public List getValues() {
		return getParam().getValues();
	}

}
