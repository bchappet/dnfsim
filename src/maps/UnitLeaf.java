package maps;

public class UnitLeaf extends Leaf implements UnitParameter{

	public UnitLeaf(UnitParameter map) {
		super(map);
	}
	
	@Override
	public Unit getUnit(int index) {
		return ((UnitParameter) map).getUnit(index);
	}
	
	


}
