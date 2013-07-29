package maps;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import unitModel.UnitModel;
import coordinates.NullCoordinateException;
import coordinates.Space;

public class Leaf  implements Parameter{
	
	protected Parameter map;
	protected String name;
	protected List<AbstractMap> parents;

	public Leaf(Parameter map) {
		this.map = map;
		this.name = map.getName() + "_leaf";
		parents = new LinkedList<AbstractMap>();
	}

	public Leaf clone() {
		Leaf clone = null;
		try {
			clone = (Leaf) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		clone.map = map; //share
		return clone;
	}
	
	@Override
	public  double get(Double... coord) throws NullCoordinateException {
		return map.get(coord);
	}

	@Override
	public double getFast(int... coord) {
		return map.getFast(coord);
	}

	@Override
	public void reset() {
		//Nothing otherwise recursive

	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Space getSpace() {
		return map.getSpace();
	}

	

	@Override
	public void constructMemory() throws NullCoordinateException {
		//nothing TODO otherwise recursion in NeighborhoodMap construction if
		//it is the same map that is referenced in the Neighborhood...
		//But problematic when constructing 
		map.constructMemory();
	}

	@Override
	public double get(int index) throws NullCoordinateException {
		return map.get(index);
	}

	@Override
	public Parameter getDt() {
		return map.getDt();
	}

	@Override
	public void addParent(AbstractMap updatable) {
		this.parents.add(updatable);
	}

	@Override
	public void signalParents() throws NullCoordinateException {
		for(AbstractMap u : parents)
			u.signalParents();
		
	}

	@Override
	public void delete()
	{
		for(AbstractMap  p : parents){
			p.removeParameter(this);
		}
	}

	@Override
	public double getDelay(int delay, Double... coord) {
		return map.getDelay(delay, coord);
	}

	

	@Override
	public void addMemories(int nb, UnitModel... historic)
			throws NullCoordinateException {
		map.addMemories(nb, historic);
		
	}

	@Override
	public void toParallel() throws NullCoordinateException {
		map.toParallel();
		
	}

	@Override
	public void onLine() throws NullCoordinateException {
		map.onLine();
		
	}

	@Override
	public void compute() throws NullCoordinateException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isStatic() {
		return map.isStatic();
	}

	@Override
	public void toStatic() throws NullCoordinateException {
		//One should use the instance of linked directly 
		
	}

	@Override
	public double[] getValues() {
		return map.getValues();
	}

	@Override
	public void constructAllMemories() {
	//nothing (recursive method)
		
	}

	@Override
	public void compute(int index) {
		// nothing (recursive method)
		
	}

	@Override
	public Var getVar(Double... coor) {
		return map.getVar(coor);
	}

	/**
	 * Return the linked parameter
	 * @return
	 */
	public Parameter getMap() {
		return map;
	}

	@Override
	public void setIndex(int index, double newVal) {
		map.setIndex(index, newVal);
		
	}

	@Override
	public double getDelay(int delay, int index) {
		return map.getDelay(delay, index);
	}

	

	
	

	

	

}
