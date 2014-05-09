package main.java.model;


import java.math.BigDecimal;
import java.util.List;

import main.java.maps.Map;
import main.java.maps.Parameter;
import main.java.maps.Trajectory;
import main.java.maps.UnitMap;
import main.java.maps.Var;
import main.java.space.Space;
import main.java.space.Space2D;
import main.java.unitModel.UMWrapper;
import main.java.unitModel.UnitModel;
import statistics2.Characteristics;
import statistics2.Statistics;


/**
 * Test main.java.model for first developing increment
 * @author benoit
 *
 */
public class ModelSimple  {
	
	private Map root;
	private Characteristics characs;
	private Statistics stats;
	private Var<BigDecimal> dt;

	public ModelSimple() {
	}
	
	public void initModel(){
		Space<Integer> space = new Space2D(2, 2);
		dt = new Var<BigDecimal>("dt",new BigDecimal("0.1"));
		this.root = new UnitMap<Double, Integer>("A",dt, space, new UMWrapper<Double>(2d));
		Trajectory<Double> traj = new Trajectory<Double>("B",new Var<BigDecimal>("dtB",new BigDecimal("0.3")), new UMWrapper<Double>(10d));
		Trajectory<Double> traj2 = new Trajectory<Double>("C",new Var<BigDecimal>("dtC",new BigDecimal("0.35")), new UMWrapper<Double>(10d),new Var<Double>(9d));
		traj.addParameters(traj2);
		this.root.addParameters(traj);
	}
	
	public void initStatistics(){
		Trajectory<Double> som = new Trajectory<Double>(stats.SOM,dt, new UnitModel<Double>(0d) {
			@Override
			protected  Double compute(BigDecimal time, BigDecimal dt,List<Parameter> params){
				double ret = 0;
				for(int i = 0 ; i < root.getSpace().getVolume() ; i++){
					ret += (Double)root.getIndex(i);
				}
				return ret;
			}
		});
		
		this.stats.add(som.getName(), som);
	}
	
	
	public void initCharacteristic(){
		
		Trajectory<Double> som = new Trajectory<Double>(Characteristics.SOMSOM,dt, new UnitModel<Double>(0d) {
			@Override
			protected  Double compute(BigDecimal time, BigDecimal dt,List<Parameter> params){
				Trajectory<Double> som = (Trajectory<Double>) params.get(0);
				double ret = 0;
				for(int i = 0 ; i < som.getNbMemory() ; i++){
					ret += som.getDelay(0,i);
				}
				return ret;
			}
		},stats.get(Statistics.SOM));
		
	}
	
	public Map getRoot(){
		return this.root;
	}

}
