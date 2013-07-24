package statistics;

import maps.AbstractMap;
import maps.Leaf;
import maps.Parameter;
import maps.Var;
import model.Model;
import console.CommandLineFormatException;
import coordinates.Space;

public class PreciseStat extends Stat {
	


	public PreciseStat(Var dt, Space space, Model model, Parameter... maps) {
		super(dt, space, model, maps);
	}

	

	public StatMap[] getDefaultStatistics(Leaf leaf,
			Leaf cnftExc, Leaf cnftInh, AbstractMap... tracks) throws CommandLineFormatException{
		StatMap[] tmp = super.getDefaultStatistics(leaf, tracks);
		StatMap[] ret = new StatMap[tmp.length + 1];
		System.arraycopy(tmp, 0, ret, 0, tmp.length);
		ret[ret.length-1] = getMaxWeigth(cnftExc, cnftInh);
		return ret;

	}
	
	/**
	 * Compute the maximal weight on the cnft + or cnft - map
	 * @param leaf
	 * @return
	 */
	protected StatMap getMaxWeigth(Leaf cnftExc,Leaf cnftInh) {
		StatMap wsum = new StatMap(Statistics.MAX_WEIGHT,dt,noDimSpace,cnftExc,cnftInh){

			@Override
			public double computeStatistic() {
				Parameter cnftExc=   this.getParam(0);
				Parameter cnftInh=   this.getParam(1);
				
				int dxExc = cnftExc.getSpace().getDiscreteSize()[Space.X];
				int dyExc = cnftExc.getSpace().getDiscreteSize()[Space.Y];
				int dxInh = cnftInh.getSpace().getDiscreteSize()[Space.X];
				int dyInh = cnftInh.getSpace().getDiscreteSize()[Space.Y];
				double maxExc = Double.MIN_VALUE;
				double minInh = Double.MAX_VALUE; 
				//Max exc
				for(int i = 0 ; i< dxExc ; i++)
				{
					for(int j = 0 ; j < dyExc ; j++)
					{
						double val = cnftExc.get(i + j * dxExc) ;
						if(val > maxExc)
							maxExc = val;
					}
				}
				
				//Min inh
				for(int i = 0 ; i< dxInh ; i++)
				{
					for(int j = 0 ; j < dyInh ; j++)
					{
						double val = cnftExc.get(i + j * dxInh) ;
						if(val < minInh)
							minInh = val;
					}
				}

				return Math.max(maxExc,-minInh);

			}
		};
		return wsum;
	}

}
