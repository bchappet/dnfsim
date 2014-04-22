package initRecherche;

import maps.VectorMap;
import unitModel.UnitModel;
import coordinates.NullCoordinateException;

public class CombinaisonUnitModel extends UnitModel {

	
	
	public CombinaisonUnitModel() {
		super();
	}
	
	@Override
	public double compute() throws NullCoordinateException {
		VectorMap vect = (VectorMap) this.getParam(0);
		//System.out.println("combinaison pr�dictions + "+this.getParams().size()+" + "+vect.getVector()[0]);
		
		double[] tab = vect.getVector();
		double sum = 0;
		double div = 0;

		for(int i=ModelCNFTPredictive.NB_PREDICTIONS+1; i<this.getParams().size();i++) {
			//int j = i-ModelCNFTPredictive.NB_PREDICTIONS-1;
			//System.out.println("+++++++++ "+ i +" ++++++ "+this.getParam(i).getName()+" +++++ "+j);
			sum = sum + tab[i-ModelCNFTPredictive.NB_PREDICTIONS-1]*this.getParam(i).get(coord);			
			div = div + tab[i-ModelCNFTPredictive.NB_PREDICTIONS-1];
		}
		//System.out.println(div);
		
		// TODO Auto-generated method stub
		return sum/div;
		//return 0;
	}

}
