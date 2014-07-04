package main.java.initRecherche;

import java.util.Arrays;

import main.java.coordinates.NullCoordinateException;
import main.java.maps.VectorMap;
import main.java.unitModel.UnitModel;

public class CombinaisonUnitModel extends UnitModel {

	
	
	public CombinaisonUnitModel() {
		super();
	}
	
	@Override
	public double compute() throws NullCoordinateException {
		PonderationParameter vect = (PonderationParameter) this.getParam(0);
		//System.out.println("combinaison pr�dictions + "+this.getParams().size()+" + "+vect.getVector()[0]);
		
		double[] tab = vect.getVector();
		double sum = 0;
		double div = 0;

		for(int i=ModelCNFTPredictive.NB_PREDICTIONS+1; i<this.getParams().size();i++) {
			//int j = i-ModelCNFTPredictive.NB_PREDICTIONS-1;
//			System.out.println("+++++++++ "+ i +" ++++++ "+this.getParam(i).getName()+" +++++ "+j);
			sum = sum + tab[i-ModelCNFTPredictive.NB_PREDICTIONS-1]*this.getParam(i).getIndex(coord);			
			div = div + tab[i-ModelCNFTPredictive.NB_PREDICTIONS-1];
		}
		if(div == 0){
			System.err.println("!!!!erreur" + Arrays.toString(Thread.currentThread().getStackTrace()));
		//System.out.println(div);
		}
		
		// TODO Auto-generated method stub
		return sum/div;
		//return 0;
	}

}
